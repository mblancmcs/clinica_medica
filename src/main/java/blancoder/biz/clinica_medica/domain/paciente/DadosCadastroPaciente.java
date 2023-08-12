package blancoder.biz.clinica_medica.domain.paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosCadastroPaciente(
        @NotBlank
        String nome,
        @NotNull
        Long cpf,
        @NotNull
        LocalDate dataNascimento,
        @NotBlank
        String telefone
) {
}
