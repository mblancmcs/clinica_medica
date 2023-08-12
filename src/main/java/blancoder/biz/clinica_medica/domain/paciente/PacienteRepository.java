package blancoder.biz.clinica_medica.domain.paciente;

import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);
}
