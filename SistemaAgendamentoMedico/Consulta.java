import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consulta {
    private static int contadorId = 1;
    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dataHora;
    private String observacoes;
    private StatusConsulta status;

    public enum StatusConsulta {
        AGENDADA("Agendada"),
        REALIZADA("Realizada"),
        CANCELADA("Cancelada"),
        REMARCADA("Remarcada");

        private final String descricao;

        StatusConsulta(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    public Consulta(Paciente paciente, Medico medico, LocalDateTime dataHora, String observacoes) {
        validarDados(paciente, medico, dataHora);
        this.id = contadorId++;
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.observacoes = observacoes != null ? observacoes : "";
        this.status = StatusConsulta.AGENDADA;
    }

    private void validarDados(Paciente paciente, Medico medico, LocalDateTime dataHora) {
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente não pode ser nulo");
        }
        
        if (medico == null) {
            throw new IllegalArgumentException("Médico não pode ser nulo");
        }
        
        if (dataHora == null) {
            throw new IllegalArgumentException("Data e hora não podem ser nulas");
        }
        
        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data e hora não podem ser no passado");
        }
    }

    // Getters e Setters
    public int getId() { return id; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { 
        if (paciente != null) {
            this.paciente = paciente; 
        }
    }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { 
        if (medico != null) {
            this.medico = medico; 
        }
    }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { 
        if (dataHora != null && dataHora.isAfter(LocalDateTime.now())) {
            this.dataHora = dataHora; 
        }
    }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { 
        this.observacoes = observacoes != null ? observacoes : ""; 
    }

    public StatusConsulta getStatus() { return status; }
    public void setStatus(StatusConsulta status) { 
        if (status != null) {
            this.status = status; 
        }
    }

    public void cancelar() {
        if (this.status == StatusConsulta.AGENDADA) {
            this.status = StatusConsulta.CANCELADA;
        } else {
            throw new IllegalStateException("Apenas consultas agendadas podem ser canceladas");
        }
    }

    public void realizar() {
        if (this.status == StatusConsulta.AGENDADA) {
            this.status = StatusConsulta.REALIZADA;
        } else {
            throw new IllegalStateException("Apenas consultas agendadas podem ser realizadas");
        }
    }

    public void remarcar(LocalDateTime novaDataHora) {
        if (this.status == StatusConsulta.AGENDADA) {
            if (novaDataHora != null && novaDataHora.isAfter(LocalDateTime.now())) {
                this.dataHora = novaDataHora;
                this.status = StatusConsulta.REMARCADA;
            } else {
                throw new IllegalArgumentException("Nova data e hora devem ser no futuro");
            }
        } else {
            throw new IllegalStateException("Apenas consultas agendadas podem ser remarcadas");
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("Consulta{ID: %d, Paciente: %s, Médico: %s, Data/Hora: %s, Status: %s, Observações: %s}", 
                           id, paciente.getNome(), medico.getNome(), 
                           dataHora.format(formatter), status.getDescricao(), observacoes);
    }
}