package blancoder.biz.clinica_medica.domain.usuario;

public enum UserRole {
    ADMIN("ADMIN"),
    MEDICO("MEDICO"),
    ATENDENTE("ATENDENTE");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
