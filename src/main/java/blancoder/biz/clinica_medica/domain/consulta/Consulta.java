package blancoder.biz.clinica_medica.domain.consulta;

import blancoder.biz.clinica_medica.domain.paciente.Paciente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="consultas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "data_consulta")
    private LocalDateTime data;
    private Integer senha;
    private String motivo;
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private PlanosParticular planoParticular;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    public void atualizar(DadosAtualizarConsulta dados) {
        if(dados.data() != null) {
            this.data = dados.data();
        }
        if(dados.senha() != null) {
            this.senha = dados.senha();
        }
        if(dados.planoParticular() != null) {
            this.planoParticular = dados.planoParticular();
        }
        if(dados.motivo() != null) {
            this.motivo = dados.motivo();
        }
    }

    public void exclusaoLogica() {
        this.ativo = false;
    }

}
