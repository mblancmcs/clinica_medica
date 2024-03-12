package blancoder.biz.clinica_medica.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    UserDetails findByLogin(String username);

    Page<Usuario> findAllByAtivoTrue(Pageable paginacao);

    @Modifying
    @Transactional
    @Query("DELETE FROM Usuario")
    void restartTable();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "INSERT INTO usuarios(login, password, role)" +
            "VALUES('admin','$2y$10$ANXLozBYocz6iVsC0n7V.eduAQtUkXXbl01TAQ8F9n7aDWDKk8XGm','ADMIN')", nativeQuery = true)
    void insertUserAdmin();
}
