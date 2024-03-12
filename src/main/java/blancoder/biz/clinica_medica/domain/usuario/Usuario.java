package blancoder.biz.clinica_medica.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private Boolean ativo;

    public Usuario(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.ativo = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_MEDICO"), new SimpleGrantedAuthority("ROLE_ATENDENTE"));
        else if(this.role == UserRole.MEDICO) return List.of(new SimpleGrantedAuthority("ROLE_MEDICO"));
        else return List.of(new SimpleGrantedAuthority("ROLE_ATENDENTE"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    public Integer getId() { return this.id; }

    public void setPassword(String password) { this.password = password; }

    public void setRole(UserRole role) { this.role = role; }

    public void exclusaoLogica() { this.ativo = false; }

    public void atualizarDados(String senha, UserRole role) {
        if(senha != null) {
            this.password = senha;
        } if(role != null) {
            this.role = role;
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
