package blancoder.biz.clinica_medica.domain.consulta;

import blancoder.biz.clinica_medica.domain.paciente.DadosListagemPaciente;
import blancoder.biz.clinica_medica.domain.paciente.Paciente;
import blancoder.biz.clinica_medica.domain.paciente.PacienteRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

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

    public Consulta(DadosCadastroConsulta dados, Paciente paciente) {
        this.data = dados.data();
        this.senha = dados.senha();
        this.motivo = dados.motivo();
        this.planoParticular = dados.planoParticular();
        this.ativo = true;
        this.paciente = paciente;
    }

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

    public DadosListagemPaciente informacoesPaciente() {
        return new DadosListagemPaciente(this.paciente);
    }

}
