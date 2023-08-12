package blancoder.biz.clinica_medica.domain.consulta.validacoes;

import blancoder.biz.clinica_medica.domain.ValidacaoException;
import blancoder.biz.clinica_medica.domain.consulta.DadosCadastroConsulta;
import blancoder.biz.clinica_medica.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarPacienteExiste implements ValidarAgendamento {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public void validar(DadosCadastroConsulta dados) {
        if(!pacienteRepository.existsById(dados.paciente().getId())) {
            throw new ValidacaoException("Paciente nao existe");
        }
    }

}
