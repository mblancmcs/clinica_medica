package blancoder.biz.clinica_medica.domain.paciente;

import java.time.LocalDate;
import java.util.List;

public record DadosListagemPaciente(
        Integer id,
        String nome,
        Long cpf,
        LocalDate dataNascimento,
        List<String> telefone
) {

    public DadosListagemPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getCpf(), paciente.getDataNascimento(), paciente.getTelefone());
    }

}
