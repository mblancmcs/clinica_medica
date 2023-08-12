package blancoder.biz.clinica_medica.domain.paciente;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosAtualizarPaciente(
        @NotNull
        Integer id,
        String nome,
        Long cpf,
        LocalDate dataNascimento,
        String telefone
) {
}
