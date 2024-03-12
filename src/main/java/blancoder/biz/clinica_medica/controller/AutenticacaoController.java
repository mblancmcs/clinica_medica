package blancoder.biz.clinica_medica.controller;

import blancoder.biz.clinica_medica.domain.atendimento.AtendimentoRepository;
import blancoder.biz.clinica_medica.domain.consulta.ConsultaRepository;
import blancoder.biz.clinica_medica.domain.paciente.PacienteRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Timer;
import java.util.TimerTask;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    private Timer timer = new Timer();

    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) throws InterruptedException {

        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.password());
        var authentication = manager.authenticate(authenticationToken); // retorna um obj que representa um usuario autenticado

        // caso nao haja algum autenticado, a execucao para e eh retornado o erro 403

        // casting do obj de tipo "Object" devolvido, para um do tipo "Usuario"
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        this.timer.cancel();
        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                usuarioRepository.restartTable();
                atendimentoRepository.restartTable();
                consultaRepository.restartTable();
                pacienteRepository.restartTable();
                usuarioRepository.insertUserAdmin();
            }
        }, 1200000);

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT)); // DTO para retornar com o json a chave "token"
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<DadosListagemUsuarios>> listarUsuarios(@PageableDefault(size = 10, sort = {"role", "login"})
                                                                      Pageable paginacao) {
        var pag = usuarioRepository.findAllByAtivoTrue(paginacao).map(DadosListagemUsuarios::new);
        return ResponseEntity.ok(pag);
    }

    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid RegisterDTO dados) {
        if(usuarioRepository.findByLogin(dados.login()) != null) return ResponseEntity.badRequest().build();

        String senhaCriptografada = new BCryptPasswordEncoder().encode(dados.password());
        Usuario novoUsuario = new Usuario(dados.login(), senhaCriptografada, dados.role());

        usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/atualizar")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarUsuario dados) {
        var usuario = (Usuario) usuarioRepository.findByLogin(dados.login());
        if(usuario == null) return ResponseEntity.badRequest().build();

        String senhaCriptografada = new BCryptPasswordEncoder().encode(dados.password());
        usuario.atualizarDados(senhaCriptografada, dados.role());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/login={login}")
    @Transactional
    public ResponseEntity exclusaoLogica(@PathVariable String login) {
        var usuario = (Usuario) usuarioRepository.findByLogin(login);
        if(usuario != null && usuario.getAtivo() == true) {
            usuario.exclusaoLogica();
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("Usuário inativo ou não encontrado");
        }
    }

}
