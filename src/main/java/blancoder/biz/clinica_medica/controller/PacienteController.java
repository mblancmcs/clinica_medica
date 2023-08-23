package blancoder.biz.clinica_medica.controller;

import blancoder.biz.clinica_medica.domain.paciente.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/paciente")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
        var paciente = new Paciente(dados);
        repository.save(paciente);

        var uri = uriBuilder.path("/paciente/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(page);
    }

    @Secured("ROLE_ATENDENTE")
    @GetMapping("/{id}")
    public ResponseEntity listarById(@PathVariable Integer id) {
        if(repository.existsById(id)) {
            var paciente = repository.getReferenceById(id);
            return ResponseEntity.ok(new DadosListagemPaciente(paciente));
        } else {
            return ResponseEntity.badRequest().body("Paciente nao encontrado");
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarPaciente dados) {
        if(repository.existsById(dados.id())) {
            var paciente = repository.getReferenceById(dados.id());
            paciente.atualizarDados(dados);
            return ResponseEntity.ok(new DadosListagemPaciente(paciente));
        } else {
            return ResponseEntity.badRequest().body("Paciente nao encontrado");
        }
    }

    @Secured("ROLE_ATENDENTE")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity exclusaoLogica(@PathVariable Integer id) {
        var paciente = repository.getReferenceById(id);
        if(paciente != null && paciente.getAtivo() == true) {
            paciente.exclusaoLogica();
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("Paciente nao encontrado");
        }
    }
}
