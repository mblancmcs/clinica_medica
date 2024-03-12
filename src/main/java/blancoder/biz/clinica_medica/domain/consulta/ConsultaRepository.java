package blancoder.biz.clinica_medica.domain.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {

    @Query("""
            SELECT c FROM Consulta c WHERE DATE(data) = :data AND ativo = true ORDER BY data ASC
            """)
    List<Consulta> consultasDoDia(LocalDate data);

    boolean existsByData(LocalDateTime data);

    Page<Consulta> findAllByPacienteCpf(Long cpf, Pageable paginacao);

    @Query("SELECT c FROM Consulta c WHERE c.paciente.cpf LIKE %:cpf% AND ativo = true")
    Page<Consulta> consultaPorCpf(Long cpf, Pageable paginacao);

    Page<Consulta> findAllByAtivoTrue(Pageable paginacao);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM Consulta")
    void restartTable();
}
