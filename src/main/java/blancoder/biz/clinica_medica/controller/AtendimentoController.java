package blancoder.biz.clinica_medica.controller;

import blancoder.biz.clinica_medica.domain.ValidacaoException;
import blancoder.biz.clinica_medica.domain.atendimento.AtendimentoRepository;
import blancoder.biz.clinica_medica.domain.atendimento.DadosAtualizarAtendimento;
import blancoder.biz.clinica_medica.domain.atendimento.DadosListagemAtendimento;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atendimento")
public class AtendimentoController {

    @Autowired
    private AtendimentoRepository repository;

    @GetMapping
    public ResponseEntity<Page<DadosListagemAtendimento>> listar(@PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
        var pagAtendimento = repository.findAll(paginacao).map(DadosListagemAtendimento::new);
        return ResponseEntity.ok(pagAtendimento);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Page<DadosListagemAtendimento>> listarByCpf(@PathVariable Long cpf, @PageableDefault(size = 10,
    sort = "id", direction = Sort.Direction.ASC) Pageable paginacao) {
        var pagAtendimento = repository.antendimentosPorCpf(cpf, paginacao).map(DadosListagemAtendimento::new);
        return ResponseEntity.ok(pagAtendimento);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarAtendimento dados) {
        var atendimento = repository.getReferenceById(dados.id());
        if(!repository.existsById(dados.id()) && atendimento.getAtivo() == false) {
            throw new ValidacaoException("Atendimento inexistente ou inativo");
        }
        atendimento.atualizar(dados);
        return ResponseEntity.ok(new DadosListagemAtendimento(atendimento));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity exclusaoLogica(@PathVariable Integer id) {
        var atendimento = repository.getReferenceById(id);
        if(!repository.existsById(id) && atendimento.getAtivo() == false){
            throw new ValidacaoException("Atendimento inexistente ou inativo");
        }
        atendimento.exclusaoLogica();
        return ResponseEntity.noContent().build();
    }

}
