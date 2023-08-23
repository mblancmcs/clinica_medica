package blancoder.biz.clinica_medica.domain.usuario;

public record DadosListagemUsuarios(String login, UserRole role) {

    public DadosListagemUsuarios(Usuario usuario) {
        this(usuario.getLogin(), usuario.getRole());
    }
}
