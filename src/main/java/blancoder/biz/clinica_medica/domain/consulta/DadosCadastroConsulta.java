package blancoder.biz.clinica_medica.domain.consulta;

import blancoder.biz.clinica_medica.domain.paciente.Paciente;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosCadastroConsulta(
        @NotNull
        PlanosParticular planoParticular,
        @Future
        LocalDateTime data,
        String senha,
        @NotNull
        Paciente paciente,
        @NotBlank
        String motivo
) {

}
