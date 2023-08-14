package blancoder.biz.clinica_medica.controller;

import blancoder.biz.clinica_medica.domain.usuario.DadosAutenticacao;
import blancoder.biz.clinica_medica.domain.usuario.Usuario;
import blancoder.biz.clinica_medica.infra.security.DadosTokenJWT;
import blancoder.biz.clinica_medica.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken); // retorna um obj que representa um usuario autenticado

        // caso nao haja algum autenticado, a execucao para e eh retornado o erro 403

        // casting do obj de tipo "Object" devolvido, para um do tipo "Usuario"
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT)); // DTO para retornar com o json a chave "token"
    }

}
