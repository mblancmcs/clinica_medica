package blancoder.biz.clinica_medica.domain.atendimento;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest // para testar camadas de persistencia e carregar o Entity Manager e configuracoes de persistencia do projeto
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // para nao substituir o banco de dados
@ActiveProfiles("test") // para ler o "application-test.properties"
class AtendimentoRepositoryTest {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deve retornar o código 400 quando não for possivel atualizar o atendimento")
    void atualizarAtendimentoCenario1() {
    }

    @Test
    @DisplayName("Deve retornar o código 200 quando for possivel atualizar o atendimento")
    void atualizarAtendimentoCenario2() {
    }
}