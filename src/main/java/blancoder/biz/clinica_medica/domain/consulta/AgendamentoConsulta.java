package blancoder.biz.clinica_medica.domain.consulta;

import blancoder.biz.clinica_medica.domain.ValidacaoException;
import blancoder.biz.clinica_medica.domain.atendimento.Atendimento;
import blancoder.biz.clinica_medica.domain.atendimento.AtendimentoRepository;
import blancoder.biz.clinica_medica.domain.consulta.validacoes.ValidarAgendamento;
import blancoder.biz.clinica_medica.domain.paciente.Paciente;
import blancoder.biz.clinica_medica.domain.paciente.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class AgendamentoConsulta {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private List<ValidarAgendamento> validacoes;

    public DadosListagemConsulta agendarAntecipado(@Valid DadosCadastroConsulta dados, Paciente paciente) {
        validacoes.forEach(v -> v.validar(dados));
        var consulta = new Consulta(dados, paciente);
        var atendimento = new Atendimento(null, null, null, null, null,  true, consulta);
        consultaRepository.save(consulta);
        atendimentoRepository.save(atendimento);
        return new DadosListagemConsulta(consulta);
    }

    public DadosListagemConsulta agendarNoDia(@Valid DadosCadastroConsulta dados) {
        Integer horaLivre = 0;
        Integer minutos = 0;
        Integer senha = 0;
        List<Consulta> consultasDoDia = consultaRepository.consultasDoDia(LocalDate.now());

        int horarioAtual = LocalDateTime.now().getHour();
        int horarioInicial = Horarios.HORARIO_INICIAL_AGENDAMENTO.getHorario();
        int horarioFinal = Horarios.HORARIO_FINAL_AGENDAMENTO.getHorario();
        int horarioAlmoco = Horarios.HORARIO_ALMOCO.getHorario();
        int horarioExpediente = Horarios.HORARIO_EXPEDIENTE.getHorario();
        Integer[] vetHorariosPreenchidos = new Integer[horarioExpediente + 1];

        if(horarioAtual > horarioFinal) {
            throw new ValidacaoException("Não é possível fazer mais agendamentos hoje.");
        }

        if(!pacienteRepository.existsByIdAndAtivoTrue(dados.idPaciente())) {
            throw new ValidacaoException("Paciente não encontrado ou inativo");
        }

        if(consultasDoDia.size() != 0) {
            int contador = 0;
            // passando os horarios em ordem cronologica referente as consultas agendadas
            for(Consulta consulta : consultasDoDia) {
                vetHorariosPreenchidos[contador] = consulta.getData().getHour();
                if(consulta.getSenha() != null) {
                    senha = consulta.getSenha();
                }
                contador++;
            }
            contador = 0;
            for(int horario = horarioInicial; horario <= horarioFinal; horario++) {
                if(!Arrays.asList(vetHorariosPreenchidos).contains(horario)
                        && LocalDateTime.now().isBefore(LocalDateTime.now().withHour(horario).withMinute(30))
                        && horario != horarioAlmoco ) {
                    horaLivre = horario;
                    senha++;
                    break;
                }
                else if(vetHorariosPreenchidos[horarioExpediente] != null) {
                    throw new ValidacaoException("Não há horário disponível hoje.");
                }
                contador++;
            }
        } else {
            if(LocalDateTime.now().isBefore(LocalDateTime.now().withHour(horarioAtual).withMinute(30)) &&
                    LocalDateTime.now().getHour() != horarioAlmoco) {
                horaLivre = LocalDateTime.now().getHour();
                //minutos = 30;
                senha++;
            } else if(LocalDateTime.now().getHour() + 1 == horarioAlmoco) {
                horaLivre = LocalDateTime.now().getHour() + 2;
                senha++;
            } else {
                horaLivre = LocalDateTime.now().getHour() + 1;
                senha++;
            }
        }

        if(horaLivre == 0) {
            throw new ValidacaoException("Não há horário disponível hoje.");
        }

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var consulta = new Consulta(null, LocalDateTime.now().withHour(horaLivre).withMinute(minutos).withSecond(0).withNano(0),
                senha, dados.motivo(), true,
                dados.planoParticular(), paciente);
        var atendimento = new Atendimento(null, null, null, null, null,  true, consulta);

        consultaRepository.save(consulta);
        atendimentoRepository.save(atendimento);

        return new DadosListagemConsulta(consulta);

    }
}
