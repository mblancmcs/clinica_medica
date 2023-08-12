package blancoder.biz.clinica_medica.controller;

import blancoder.biz.clinica_medica.domain.ValidacaoException;
import blancoder.biz.clinica_medica.domain.consulta.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private AgendamentoConsulta agendamentoConsulta;

    @PostMapping
    @Transactional
    public ResponseEntity agendarAntecipado(@RequestBody @Valid DadosCadastroConsulta dados, UriComponentsBuilder uriBuilder) {
        var agendamento = agendamentoConsulta.agendarAntecipado(dados);
        var uri = uriBuilder.path("/consulta/{id}").buildAndExpand(agendamento.id()).toUri();
        return ResponseEntity.created(uri).body(agendamento);
    }

    @PostMapping("/no_dia")
    @Transactional
    public ResponseEntity agendarMesmoDia(@RequestBody @Valid DadosCadastroConsulta dados, UriComponentsBuilder uriBuilder) {
        var agendamento = agendamentoConsulta.agendarNoDia(dados);
        var uri = uriBuilder.path("/consulta/{id}").buildAndExpand(agendamento.id()).toUri();
        return ResponseEntity.created(uri).body(agendamento);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemConsulta>> listar(@PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
        var pagConsulta = repository.findAll(paginacao).map(DadosListagemConsulta::new);
        return ResponseEntity.ok(pagConsulta);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Page<DadosListagemConsulta>> listarByCpf(@PageableDefault(size = 10, sort = "nome") Pageable paginacao,
                                                                   @PathVariable Long cpf) {
        var pagConsulta = repository.findAllByCpf(cpf, paginacao).map(DadosListagemConsulta::new);
        return ResponseEntity.ok(pagConsulta);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarConsulta dados) {
        var consulta = repository.getReferenceById(dados.id());
        if(consulta == null) {
            throw new ValidacaoException("Consulta inexistente");
        }
        consulta.atualizar(dados);
        return ResponseEntity.ok(new DadosListagemConsulta(consulta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity exclusaoLogica(@PathVariable Integer id) {
        var consulta = repository.getReferenceById(id);
        if(consulta == null) {
            throw new ValidacaoException("Consulta inexistente");
        }
        consulta.exclusaoLogica();
        return ResponseEntity.noContent().build();
    }

}
