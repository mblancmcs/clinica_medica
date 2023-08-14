package blancoder.biz.clinica_medica.domain.consulta.validacoes;

import blancoder.biz.clinica_medica.domain.ValidacaoException;
import blancoder.biz.clinica_medica.domain.consulta.Consulta;
import blancoder.biz.clinica_medica.domain.consulta.ConsultaRepository;
import blancoder.biz.clinica_medica.domain.consulta.DadosCadastroConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ValidarHorarioLivreNoDia {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosCadastroConsulta dados) {
        Integer horaLivre = 0;
        List<Consulta> consultasDoDia = consultaRepository.findFirst8ByDataOrderByDataAsc(LocalDate.now());

        int horario = 8;
        for(Consulta consulta : consultasDoDia) {
            if(horario >= 17) {
                break;
            }
            if(consulta.getData().getHour() != horario && horario >= LocalDateTime.now().getHour()) {
                horaLivre = horario;
            }
            horario++;
        }

        if(horaLivre == 0) {
            throw new ValidacaoException("Nao ha horario livre no dia");
        }

    }

}
