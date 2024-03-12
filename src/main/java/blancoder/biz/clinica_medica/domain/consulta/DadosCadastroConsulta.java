package blancoder.biz.clinica_medica.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosCadastroConsulta(
        @NotNull
        PlanosParticular planoParticular,
        @Future
        LocalDateTime data,
        @NotNull
        Integer idPaciente,
        @NotBlank
        String motivo
) {

}
