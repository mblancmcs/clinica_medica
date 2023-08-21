package blancoder.biz.clinica_medica.domain.atendimento;

import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Integer> {

    Page<Atendimento> findAllByConsultaPacienteCpfAndAtivoTrue(Long cpf, Pageable paginacao);

    Page<Atendimento> findAllByAtivoTrue(Pageable paginacao);
}
