package blancoder.biz.clinica_medica.controller;

import blancoder.biz.clinica_medica.domain.consulta.AgendamentoConsulta;
import blancoder.biz.clinica_medica.domain.consulta.DadosCadastroConsulta;
import blancoder.biz.clinica_medica.domain.consulta.DadosListagemConsulta;
import blancoder.biz.clinica_medica.domain.consulta.PlanosParticular;
import blancoder.biz.clinica_medica.domain.paciente.DadosListagemPaciente;
import blancoder.biz.clinica_medica.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest // subindo o contexto completo do Spring para simular uma classe controller
@AutoConfigureMockMvc // para injetar o MockMvc
@AutoConfigureJsonTesters // para injetar o JacksonTester, e facilitar o envio e recebimento de jsons)
class ConsultaControllerTest {

    // teste de unidade - mocks para testar o controller de forma isolada

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosCadastroConsulta> dadosCadastroConsultaJson;

    @Autowired
    private JacksonTester<DadosListagemConsulta> dadosListagemConsultaJson;

    @MockBean // mock para usar o banco de dados de teste
    private AgendamentoConsulta agendamentoConsulta;

    @Test
    @DisplayName("Deve devolver o codigo 400 quando houver informacoes invalidas")
    @WithMockUser // simulando um usuario logado para o Spring Security
    void agendarCenario1() throws Exception {
        var response = mockMvc.perform(post("/consulta"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve devolver o codigo 201 quando o agendamento antecipado for valido")
    @WithMockUser
    void agendarCenario2() throws Exception {
        var planosParticular = PlanosParticular.PARTICULAR;
        // mudar abaixo de acordo com o horario da consulta esperado
        var horarioConsulta = LocalDateTime.now().withDayOfMonth(10).withMonth(12).withYear(2023)
                .withHour(14).withMinute(0).withSecond(0);

        List<String> telefone = new ArrayList<>();
        telefone.add("+5521912345678");
        var dataNascimento = LocalDate.now().withYear(2000).withMonth(6).withDayOfMonth(10);
        var paciente = new Paciente(1, 11122233344l, "Paciente teste", dataNascimento, true, telefone);
        var dadosListagemPaciente = new DadosListagemPaciente(paciente);

        var dadosListagemConsulta = new DadosListagemConsulta(null, horarioConsulta, null, planosParticular,
                "Dor de cabeca", dadosListagemPaciente);

        when(agendamentoConsulta.agendarAntecipado(any(), any())).thenReturn(dadosListagemConsulta);

        var response = mockMvc.perform(
                post("/consulta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroConsultaJson
                                .write(new DadosCadastroConsulta(planosParticular, horarioConsulta, null, 1, "Dor de cabeca"))
                                .getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var jsonEsperado = dadosListagemConsultaJson.write(new DadosListagemConsulta(null, horarioConsulta, null,
                planosParticular, "Dor de cabeca", dadosListagemPaciente)).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deve devolver o codigo 201 quando o agendamento no dia for valido")
    @WithMockUser
    void agendarCenario3() throws Exception {
        var planosParticular = PlanosParticular.UNIMED;
        // mudar abaixo de acordo com o horario da consulta esperado
        var horarioConsulta = LocalDateTime.now().withHour(11).withMinute(0).withSecond(0);

        List<String> telefone = new ArrayList<>();
        telefone.add("+5521912345678");
        var dataNascimento = LocalDate.now().withYear(2000).withMonth(6).withDayOfMonth(10);
        var paciente = new Paciente(1, 11122233344l, "Paciente teste", dataNascimento, true, telefone);
        var dadosListagemPaciente = new DadosListagemPaciente(paciente);

        var dadosListagemConsulta = new DadosListagemConsulta(null, horarioConsulta, 1, planosParticular,
                "Dor de cabeca", dadosListagemPaciente);

        when(agendamentoConsulta.agendarNoDia(any())).thenReturn(dadosListagemConsulta);

        var response = mockMvc.perform(
                post("/consulta/no_dia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroConsultaJson
                                .write(new DadosCadastroConsulta(planosParticular, null, null, 1, "Dor de cabeca"))
                                .getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var jsonEsperado = dadosListagemConsultaJson.write(new DadosListagemConsulta(null, horarioConsulta, 1,
                planosParticular, "Dor de cabeca", dadosListagemPaciente)).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

}