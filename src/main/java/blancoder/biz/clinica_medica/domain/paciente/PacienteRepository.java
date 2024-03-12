package blancoder.biz.clinica_medica.domain.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);
    Paciente findByCpfAndAtivoTrue(Long cpf);

    @Query("SELECT p FROM Paciente p WHERE p.cpf LIKE %:cpf% AND ativo = true")
    Page<Paciente> pacientePorCpf(Long cpf, Pageable paginacao);

    boolean existsByIdAndAtivoTrue(Integer integer);

    boolean existsByCpfAndAtivoTrue(Long cpf);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM Paciente")
    void restartTable();

}
