package blancoder.biz.clinica_medica.domain.consulta.validacoes;

import blancoder.biz.clinica_medica.domain.ValidacaoException;
import blancoder.biz.clinica_medica.domain.consulta.DadosCadastroConsulta;
import blancoder.biz.clinica_medica.domain.consulta.Horarios;
import org.springframework.stereotype.Component;

@Component
public class ValidarHorario implements ValidarAgendamento {

    @Override
    public void validar(DadosCadastroConsulta dados) {
        if(!(dados.data().getHour() >= Horarios.HORARIO_INICIAL_AGENDAMENTO.getHorario()
                && dados.data().getHour() <= Horarios.HORARIO_FINAL_AGENDAMENTO.getHorario())) {
            throw new ValidacaoException("Não é possível agendar para o horário informado, apenas de 8 as 16 horas.");
        }
    }
}
