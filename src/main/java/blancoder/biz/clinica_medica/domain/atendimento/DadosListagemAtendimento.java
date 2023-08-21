package blancoder.biz.clinica_medica.domain.atendimento;

import blancoder.biz.clinica_medica.domain.consulta.Consulta;
import blancoder.biz.clinica_medica.domain.consulta.DadosListagemConsulta;
import jakarta.validation.constraints.NotNull;

public record DadosListagemAtendimento(
        Integer id,
        String diagnostico,
        String receitaRemedios,
        String solicitacaoRetorno,
        String complemento,
        DadosListagemConsulta consulta
) {
    public DadosListagemAtendimento(Atendimento atendimento) {
        this(atendimento.getId(), atendimento.getDiagnostico(), atendimento.getReceitaRemedios(),
                atendimento.getSolicitacaoRetorno(), atendimento.getComplemento(), atendimento.informacoesConsulta());
    }
}
