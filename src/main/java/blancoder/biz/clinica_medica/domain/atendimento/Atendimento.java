package blancoder.biz.clinica_medica.domain.atendimento;

import blancoder.biz.clinica_medica.domain.consulta.Consulta;
import blancoder.biz.clinica_medica.domain.consulta.DadosListagemConsulta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Atendimento")
@Table(name = "atendimentos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String diagnostico;
    private String receitaRemedios;
    private String solicitacaoRetorno;
    private String complemento;
    private Boolean ativo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_consulta")
    private Consulta consulta;

    public void atualizar(DadosAtualizarAtendimento dados) {
        if(dados.diagnostico() != null) {
            this.diagnostico = dados.diagnostico();
        }
        if(dados.receitaRemedios() != null) {
            this.receitaRemedios = dados.receitaRemedios();
        }
        if(dados.solicitacaoRetorno() != null) {
            this.solicitacaoRetorno = dados.solicitacaoRetorno();
        }
        if(dados.complemento() != null) {
            this.complemento = dados.complemento();
        }
    }

    public void exclusaoLogica() {
        this.ativo = false;
    }

    public DadosListagemConsulta informacoesConsulta() {
        return new DadosListagemConsulta(this.consulta);
    }

}
