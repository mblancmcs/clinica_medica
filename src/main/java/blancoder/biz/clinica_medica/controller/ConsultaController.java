package blancoder.biz.clinica_medica.controller;

import blancoder.biz.clinica_medica.domain.ValidacaoException;
import blancoder.biz.clinica_medica.domain.consulta.*;
import blancoder.biz.clinica_medica.domain.paciente.PacienteRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@RestController
@RequestMapping("/consulta")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private AgendamentoConsulta agendamentoConsulta;

    @Secured("ROLE_ATENDENTE")
    @PostMapping
    @Transactional
    public ResponseEntity agendarAntecipado(@RequestBody @Valid DadosCadastroConsulta dados, UriComponentsBuilder uriBuilder) {
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var agendamento = agendamentoConsulta.agendarAntecipado(dados, paciente);
        var uri = uriBuilder.path("/consulta/id={id}").buildAndExpand(agendamento.id()).toUri();
        return ResponseEntity.created(uri).body(agendamento);
    }

    @Secured("ROLE_ATENDENTE")
    @PostMapping("/no_dia")
    @Transactional
    public ResponseEntity agendarMesmoDia(@RequestBody @Valid DadosCadastroConsulta dados, UriComponentsBuilder uriBuilder) {
        var agendamento = agendamentoConsulta.agendarNoDia(dados);
        var uri = uriBuilder.path("/consulta/id={id}").buildAndExpand(agendamento.id()).toUri();
        return ResponseEntity.created(uri).body(agendamento);
    }

    @Secured({"ROLE_MEDICO", "ROLE_ATENDENTE"})
    @GetMapping
    public ResponseEntity<Page<DadosListagemConsulta>> listar(@PageableDefault(size = 10, sort = "data", direction = Sort.Direction.DESC)
                                                                  Pageable paginacao) {
        var pagConsulta = consultaRepository.findAllByAtivoTrue(paginacao).map(DadosListagemConsulta::new);
        return ResponseEntity.ok(pagConsulta);
    }

    @Secured({"ROLE_MEDICO", "ROLE_ATENDENTE"})
    @GetMapping("/cpf={cpf}")
    public ResponseEntity<Page<DadosListagemConsulta>> listarPorCpf(@PageableDefault(size = 10, sort = "data", direction = Sort.Direction.DESC)
                                                                       Pageable paginacao, @PathVariable Long cpf) {
        var pagConsulta = consultaRepository.consultaPorCpf(cpf, paginacao).map(DadosListagemConsulta::new);
        return ResponseEntity.ok(pagConsulta);
    }

    @Secured({"ROLE_ATENDENTE", "ROLE_MEDICO"})
    @GetMapping("/data={data}")
    public ResponseEntity<Page<DadosListagemConsulta>> listarPorData(@PageableDefault(size = 10, sort = "data", direction = Sort.Direction.ASC)
                                                                      Pageable paginacao, @PathVariable LocalDate data) {
        // convertendo de List<Consulta> para Page<DadosListagemConsulta>
        var consultasDoDia = consultaRepository.consultasDoDia(data);
        int start = (int) paginacao.getOffset();
        int end = Math.min((start + paginacao.getPageSize()), consultasDoDia.size());
        Page<DadosListagemConsulta> pagConsulta = new PageImpl<>(consultasDoDia.subList(start, end), paginacao, consultasDoDia.size()).map(DadosListagemConsulta::new);
        return ResponseEntity.ok(pagConsulta);
    }

    @Secured("ROLE_ATENDENTE")
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarConsulta dados) {
        var consulta = consultaRepository.getReferenceById(dados.id());
        if(consulta == null) {
            throw new ValidacaoException("Consulta inexistente");
        }
        consulta.atualizar(dados);
        return ResponseEntity.ok(new DadosListagemConsulta(consulta));
    }

    @Secured("ROLE_ATENDENTE")
    @DeleteMapping("/id={id}")
    @Transactional
    public ResponseEntity exclusaoLogica(@PathVariable Integer id) {
        var consulta = consultaRepository.getReferenceById(id);
        if(consulta == null) {
            throw new ValidacaoException("Consulta inexistente");
        }
        consulta.exclusaoLogica();
        return ResponseEntity.noContent().build();
    }

}
