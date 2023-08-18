package blancoder.biz.clinica_medica.domain.consulta.validacoes;

import blancoder.biz.clinica_medica.domain.ValidacaoException;
import blancoder.biz.clinica_medica.domain.consulta.DadosCadastroConsulta;
import blancoder.biz.clinica_medica.domain.consulta.Horarios;
import org.springframework.stereotype.Component;

@Component
public class ValidarHorarioAlmoco implements ValidarAgendamento{

    @Override
    public void validar(DadosCadastroConsulta dados) {
        if(dados.data().getHour() == Horarios.HORARIO_ALMOCO.getHorario()) {
            throw new ValidacaoException("Horário de almoço, por favor solicite um novo agendamento.");
        }
    }
}
