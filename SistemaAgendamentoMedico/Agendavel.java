import java.time.LocalDateTime;

public interface Agendavel {
    boolean podeSerAgendado(LocalDateTime dataHora);
    void agendar(LocalDateTime dataHora);
    void cancelar();
    String getIdentificacao();
}