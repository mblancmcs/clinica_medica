package blancoder.biz.clinica_medica.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizarUsuario(
        @NotBlank
        String login,

        String password,

        UserRole role) {
}
