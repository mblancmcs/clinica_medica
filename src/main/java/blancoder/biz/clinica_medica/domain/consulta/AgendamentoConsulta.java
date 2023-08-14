package blancoder.biz.clinica_medica.domain.consulta;

import blancoder.biz.clinica_medica.domain.atendimento.Atendimento;
import blancoder.biz.clinica_medica.domain.atendimento.AtendimentoRepository;
import blancoder.biz.clinica_medica.domain.consulta.validacoes.ValidarAgendamento;
import blancoder.biz.clinica_medica.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public DadosListagemConsulta agendarAntecipado(DadosCadastroConsulta dados) {
        return null;
    }

    public DadosListagemConsulta agendarNoDia(DadosCadastroConsulta dados) {
        Integer horaLivre = 0;
        Integer senha = 0;
        List<Consulta> consultasDoDia = consultaRepository.findFirst8ByDataOrderByDataAsc(LocalDate.now());

        int horario = 8;
        for(Consulta consulta : consultasDoDia) {
            if(horario >= 17) {
                break;
            }
            if(consulta.getData().getHour() != horario && horario >= LocalDateTime.now().getHour()) {
                horaLivre = horario;
                for(Consulta consultaSenha : consultasDoDia) {
                    senha = Integer.valueOf(consultaSenha.getSenha()) + 1;
                }
                break;
            }
            horario++;
        }

        var consulta = new Consulta(null, LocalDateTime.now().withHour(horaLivre), senha, dados.motivo(), true,
                dados.planoParticular(), dados.paciente());
        new Atendimento(null, null, null, null, null,  true, consulta);

        return new DadosListagemConsulta(consulta);
    }
}
