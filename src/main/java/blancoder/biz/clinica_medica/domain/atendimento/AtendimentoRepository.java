package blancoder.biz.clinica_medica.domain.atendimento;

import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Integer> {

    Page<Atendimento> findAllByConsultaPacienteCpfAndAtivoTrue(Long cpf, Pageable paginacao);

    @Query("SELECT a FROM Atendimento a WHERE a.consulta.paciente.cpf LIKE %:cpf% AND ativo = true")
    Page<Atendimento> atendimentosPorCpf(Long cpf, Pageable paginacao);

    Page<Atendimento> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            SELECT a FROM Atendimento a WHERE DATE(consulta.data) = :data AND ativo = true ORDER BY consulta.data ASC
            """)
    List<Atendimento> atendimentosDoDia(LocalDate data);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM Atendimento")
    void restartTable();

}
