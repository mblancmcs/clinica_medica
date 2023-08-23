package blancoder.biz.clinica_medica.controller;

import blancoder.biz.clinica_medica.domain.usuario.*;
import blancoder.biz.clinica_medica.infra.security.DadosTokenJWT;
import blancoder.biz.clinica_medica.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.password());
        var authentication = manager.authenticate(authenticationToken); // retorna um obj que representa um usuario autenticado

        // caso nao haja algum autenticado, a execucao para e eh retornado o erro 403

        // casting do obj de tipo "Object" devolvido, para um do tipo "Usuario"
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT)); // DTO para retornar com o json a chave "token"
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO dados) {
        if(usuarioRepository.findByLogin(dados.login()) != null) return ResponseEntity.badRequest().build();

        String senhaCriptografada = new BCryptPasswordEncoder().encode(dados.password());
        Usuario novoUsuario = new Usuario(dados.login(), senhaCriptografada, dados.role());

        usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<DadosListagemUsuarios>> listarUsuarios(@PageableDefault(size = 10, sort = {"role", "login"})
                                                                          Pageable paginacao) {
        var pag = usuarioRepository.findAll(paginacao).map(DadosListagemUsuarios::new);
        return ResponseEntity.ok(pag);
    }

}
