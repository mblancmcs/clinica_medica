package blancoder.biz.clinica_medica.domain.atendimento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Integer> {

    @Query(value = "SELECT at FROM Atendimento at JOIN FETCH at.consulta.paciente WHERE at.consulta.paciente.cpf = :cpf",
            countQuery = "SELECT count(*) FROM Atendimento at JOIN FETCH at.consulta.paciente WHERE at.consulta.paciente.cpf = :cpf",
    nativeQuery = true) // sem o comportamento Eager, para caso haja outras entidades ou relacionamentos futuramente
    Page<Atendimento> antendimentosPorCpf(Long cpf, Pageable paginacao);
}
