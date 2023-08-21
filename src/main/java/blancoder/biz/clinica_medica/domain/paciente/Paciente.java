package blancoder.biz.clinica_medica.domain.paciente;

import blancoder.biz.clinica_medica.util.StringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity(name = "Paciente")
@Table(name = "pacientes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long cpf;
    private String nome;
    private LocalDate dataNascimento;
    private Boolean ativo;

    @Convert(converter = StringListConverter.class)
    @Column(name = "telefone", nullable = false)
    private List<String> telefone = new ArrayList<>();

    public Paciente(DadosCadastroPaciente dados) {
        this.cpf = dados.cpf();
        this.nome = dados.nome();
        this.dataNascimento = dados.dataNascimento();
        this.ativo = true;
        adicionarListaTelefone(dados.telefone());
    }

    public void adicionarListaTelefone(String telefone) {

        if(telefone.contains(";")) {
            String[] listaTelefones = telefone.split(";");

            for(String tel : listaTelefones) {
                this.telefone.add(tel);
            }
        } else  {
            this.telefone.add(telefone);
        }

    }

    public void atualizarDados(DadosAtualizarPaciente dados) {

        if(dados.nome() != null) {
            this.nome = dados.nome();
        }
        if(dados.cpf() != null) {
            this.cpf = dados.cpf();
        }
        if(dados.dataNascimento() != null) {
            this.dataNascimento = dados.dataNascimento();
        }
        if(dados.telefone() != null) {
            String[] listaTelefones = dados.telefone().split(";");
            this.telefone = Arrays.asList(listaTelefones);
        }

    }

    public void exclusaoLogica() {
        this.ativo = false;
    }

}
