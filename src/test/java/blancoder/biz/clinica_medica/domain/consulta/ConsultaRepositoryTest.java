package blancoder.biz.clinica_medica.domain.consulta;

import blancoder.biz.clinica_medica.domain.paciente.DadosCadastroPaciente;
import blancoder.biz.clinica_medica.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // para nao substituir o banco de dados
@ActiveProfiles("test") // para ler o "application-test.properties"
class ConsultaRepositoryTest {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deve retornar as consultas que tiverem no dia")
    void consultasDoDiaCenario1() {
        var dataNascimento = LocalDate.now().withYear(2000).withMonth(6).withDayOfMonth(10);
        var paciente = new Paciente(new DadosCadastroPaciente("Paciente teste", 11122233344l, dataNascimento, "+5521912345678"));
        em.persist(paciente);

        var planosParticular = PlanosParticular.PARTICULAR;
        var horarioConsulta = LocalDateTime.now().withDayOfMonth(10).withMonth(12).withYear(2023)
                .withHour(14).withMinute(0).withSecond(0);
        var consulta = new Consulta(new DadosCadastroConsulta(planosParticular, horarioConsulta, 1,  "Dor de cabeca"), paciente);
        em.persist(consulta);

        assertThat(consultaRepository.consultasDoDia(horarioConsulta.toLocalDate())).isNotNull();
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia caso não hava consultas no dia")
    void consultasDoDiaCenario2() {
        assertThat(consultaRepository.consultasDoDia(LocalDate.now())).isEmpty();
    }
}