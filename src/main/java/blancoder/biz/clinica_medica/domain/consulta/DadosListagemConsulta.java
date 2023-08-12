package blancoder.biz.clinica_medica.domain.consulta;

import blancoder.biz.clinica_medica.domain.paciente.Paciente;

import java.time.LocalDateTime;

public record DadosListagemConsulta(
        Integer id,
        LocalDateTime data,
        Integer senha,
        PlanosParticular planoParticular,
        Paciente paciente
) {

    public DadosListagemConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getData(), consulta.getSenha(), consulta.getPlanoParticular(), consulta.getPaciente());
    }
}
