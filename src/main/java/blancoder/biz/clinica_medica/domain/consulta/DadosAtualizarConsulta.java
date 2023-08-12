package blancoder.biz.clinica_medica.domain.consulta;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAtualizarConsulta(
        @NotNull
        Integer id,
        LocalDateTime data,
        Integer senha,
        PlanosParticular planoParticular,
        String motivo
) {
}
