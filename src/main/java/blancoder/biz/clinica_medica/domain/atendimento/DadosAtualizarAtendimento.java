package blancoder.biz.clinica_medica.domain.atendimento;

import blancoder.biz.clinica_medica.domain.consulta.Consulta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarAtendimento(
        @NotNull
        Integer id,
        String diagnostico,
        String receitaRemedios,
        String solicitacaoRetorno,
        String complemento
) {
}
