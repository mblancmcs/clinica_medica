package blancoder.biz.clinica_medica.domain.consulta;

import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {

    @Query("""
            SELECT c FROM Consulta c WHERE DATE(data) = :data ORDER BY data ASC
            """)
    List<Consulta> consultasDoDia(LocalDate data);

    boolean existsByData(LocalDateTime data);

    Page<Consulta> findAllByPacienteCpf(Long cpf, Pageable paginacao);

    Page<Consulta> findAllByAtivoTrue(Pageable paginacao);
}
