import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Lembrete {
    private Consulta consulta;
    private LocalDateTime dataHoraLembrete;
    private String mensagem;

    public Lembrete(Consulta consulta) {
        this.consulta = consulta;
        // Lembrete 24 horas antes da consulta
        this.dataHoraLembrete = consulta.getDataHora().minusHours(24);
        this.mensagem = gerarMensagem();
    }

    private String gerarMensagem() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("Lembrete: Você tem uma consulta amanhã às %s com %s. " +
                           "Paciente: %s. Local: Clínica Médica", 
                           consulta.getDataHora().format(formatter),
                           consulta.getMedico().getNome(),
                           consulta.getPaciente().getNome());
    }

    public Consulta getConsulta() { return consulta; }
    public LocalDateTime getDataHoraLembrete() { return dataHoraLembrete; }
    public String getMensagem() { return mensagem; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("Lembrete{Consulta ID: %d, Data/Hora: %s, Mensagem: %s}", 
                           consulta.getId(), dataHoraLembrete.format(formatter), mensagem);
    }
}