package blancoder.biz.clinica_medica.domain.consulta;

public enum Horarios {

    HORARIO_ABERTURA(8),
    HORARIO_FECHAMENTO(17),
    HORARIO_INICIAL_AGENDAMENTO(8),
    HORARIO_FINAL_AGENDAMENTO(16),
    HORARIO_ALMOCO(12),
    HORARIO_EXPEDIENTE(8);

    private final int horario;

    Horarios(int horario) {
        this.horario = horario;
    }

    public int getHorario() {
        return this.horario;
    }

}
