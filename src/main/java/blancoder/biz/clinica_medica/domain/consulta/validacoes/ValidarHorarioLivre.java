package blancoder.biz.clinica_medica.domain.consulta.validacoes;

import blancoder.biz.clinica_medica.domain.ValidacaoException;
import blancoder.biz.clinica_medica.domain.consulta.ConsultaRepository;
import blancoder.biz.clinica_medica.domain.consulta.DadosCadastroConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarHorarioLivre implements ValidarAgendamento {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosCadastroConsulta dados) {
        if(consultaRepository.existsByData(dados.data())) {
            throw new ValidacaoException("Ja existe uma consulta agendada para essa data e horario");
        }
    }

}
