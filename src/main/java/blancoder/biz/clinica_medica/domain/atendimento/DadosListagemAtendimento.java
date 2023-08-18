package blancoder.biz.clinica_medica.domain.atendimento;

import blancoder.biz.clinica_medica.domain.consulta.Consulta;
import jakarta.validation.constraints.NotNull;

public record DadosListagemAtendimento(
        Integer id,
        String diagnostico,
        String receitaRemedios,
        String solicitacaoRetorno,
        String complemento,
        Integer idConsulta
) {
    public DadosListagemAtendimento(Atendimento atendimento) {
        this(atendimento.getId(), atendimento.getDiagnostico(), atendimento.getReceitaRemedios(),
                atendimento.getSolicitacaoRetorno(), atendimento.getComplemento(), atendimento.getConsulta().getId());
    }
}
