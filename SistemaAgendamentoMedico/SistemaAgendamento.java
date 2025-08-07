import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class SistemaAgendamento {
    private Map<String, Paciente> pacientes;
    private Map<String, Medico> medicos;
    private Map<Integer, Consulta> consultas;
    private List<Lembrete> lembretes;

    public SistemaAgendamento() {
        this.pacientes = new HashMap<>();
        this.medicos = new HashMap<>();
        this.consultas = new HashMap<>();
        this.lembretes = new ArrayList<>();
    }

    // Métodos para Pacientes
    public void cadastrarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente não pode ser nulo");
        }

        if (pacientes.containsKey(paciente.getCpf())) {
            throw new IllegalArgumentException("Paciente já cadastrado com este CPF");
        }

        pacientes.put(paciente.getCpf(), paciente);
    }

    public Paciente buscarPaciente(String cpf) {
        return pacientes.get(cpf);
    }

    public List<Paciente> listarPacientes() {
        return new ArrayList<>(pacientes.values());
    }

    public void removerPaciente(String cpf) {
        if (!pacientes.containsKey(cpf)) {
            throw new IllegalArgumentException("Paciente não encontrado");
        }

        // Verificar se há consultas agendadas
        boolean temConsultas = consultas.values().stream()
                .anyMatch(c -> c.getPaciente().getCpf().equals(cpf) &&
                              c.getStatus() == Consulta.StatusConsulta.AGENDADA);

        if (temConsultas) {
            throw new IllegalStateException("Não é possível remover paciente com consultas agendadas");
        }

        pacientes.remove(cpf);
    }

    // Métodos para Médicos
    public void cadastrarMedico(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("Médico não pode ser nulo");
        }

        if (medicos.containsKey(medico.getCrm())) {
            throw new IllegalArgumentException("Médico já cadastrado com este CRM");
        }

        medicos.put(medico.getCrm(), medico);
    }

    public Medico buscarMedico(String crm) {
        return medicos.get(crm);
    }

    public List<Medico> listarMedicos() {
        return new ArrayList<>(medicos.values());
    }

    public void removerMedico(String crm) {
        if (!medicos.containsKey(crm)) {
            throw new IllegalArgumentException("Médico não encontrado");
        }

        // Verificar se há consultas agendadas
        boolean temConsultas = consultas.values().stream()
                .anyMatch(c -> c.getMedico().getCrm().equals(crm) &&
                              c.getStatus() == Consulta.StatusConsulta.AGENDADA);

        if (temConsultas) {
            throw new IllegalStateException("Não é possível remover médico com consultas agendadas");
        }

        medicos.remove(crm);
    }

    // Métodos para Consultas
    public Consulta agendarConsulta(String cpfPaciente, String crmMedico, LocalDateTime dataHora, String observacoes) {
        Paciente paciente = buscarPaciente(cpfPaciente);
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente não encontrado");
        }

        Medico medico = buscarMedico(crmMedico);
        if (medico == null) {
            throw new IllegalArgumentException("Médico não encontrado");
        }

        // Verificar conflito de horário para o médico
        boolean conflitoMedico = consultas.values().stream()
                .anyMatch(c -> c.getMedico().getCrm().equals(crmMedico) &&
                              c.getStatus() == Consulta.StatusConsulta.AGENDADA &&
                              c.getDataHora().equals(dataHora));

        if (conflitoMedico) {
            throw new IllegalStateException("Médico já possui consulta agendada neste horário");
        }

        // Verificar conflito de horário para o paciente
        boolean conflitoPaciente = consultas.values().stream()
                .anyMatch(c -> c.getPaciente().getCpf().equals(cpfPaciente) &&
                              c.getStatus() == Consulta.StatusConsulta.AGENDADA &&
                              c.getDataHora().equals(dataHora));

        if (conflitoPaciente) {
            throw new IllegalStateException("Paciente já possui consulta agendada neste horário");
        }

        Consulta consulta = new Consulta(paciente, medico, dataHora, observacoes);
        consultas.put(consulta.getId(), consulta);

        // Criar lembrete
        criarLembrete(consulta);

        return consulta;
    }

    public void cancelarConsulta(int id) {
        Consulta consulta = consultas.get(id);
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não encontrada");
        }

        consulta.cancelar();

        // Remover lembrete
        removerLembrete(id);
    }

    public void realizarConsulta(int id) {
        Consulta consulta = consultas.get(id);
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não encontrada");
        }

        consulta.realizar();

        // Remover lembrete
        removerLembrete(id);
    }

    public void remarcarConsulta(int id, LocalDateTime novaDataHora) {
        Consulta consulta = consultas.get(id);
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não encontrada");
        }

        consulta.remarcar(novaDataHora);

        // Atualizar lembrete
        atualizarLembrete(consulta);
    }

    public List<Consulta> listarConsultas() {
        return new ArrayList<>(consultas.values());
    }

    public List<Consulta> buscarConsultasPorPaciente(String cpf) {
        return consultas.values().stream()
                .filter(c -> c.getPaciente().getCpf().equals(cpf))
                .collect(Collectors.toList());
    }

    public List<Consulta> buscarConsultasPorMedico(String crm) {
        return consultas.values().stream()
                .filter(c -> c.getMedico().getCrm().equals(crm))
                .collect(Collectors.toList());
    }

    public List<Consulta> buscarConsultasPorData(LocalDateTime data) {
        return consultas.values().stream()
                .filter(c -> c.getDataHora().toLocalDate().equals(data.toLocalDate()))
                .collect(Collectors.toList());
    }

    public List<Consulta> buscarConsultasAgendadas() {
        return consultas.values().stream()
                .filter(c -> c.getStatus() == Consulta.StatusConsulta.AGENDADA)
                .sorted(Comparator.comparing(Consulta::getDataHora))
                .collect(Collectors.toList());
    }

    // Métodos para Lembretes
    private void criarLembrete(Consulta consulta) {
        Lembrete lembrete = new Lembrete(consulta);
        lembretes.add(lembrete);
    }

    private void removerLembrete(int idConsulta) {
        lembretes.removeIf(l -> l.getConsulta().getId() == idConsulta);
    }

    private void atualizarLembrete(Consulta consulta) {
        removerLembrete(consulta.getId());
        criarLembrete(consulta);
    }

    public List<Lembrete> listarLembretes() {
        return new ArrayList<>(lembretes);
    }

    public void verificarLembretes() {
        LocalDateTime agora = LocalDateTime.now();
        List<Lembrete> lembretesVencidos = lembretes.stream()
                .filter(l -> l.getDataHoraLembrete().isBefore(agora))
                .collect(Collectors.toList());

        for (Lembrete lembrete : lembretesVencidos) {
            System.out.println("LEMBRETE: " + lembrete.getMensagem());
            lembretes.remove(lembrete);
        }
    }

    // Relatórios
    public void gerarRelatorioConsultas() {
        System.out.println("\n=== RELATÓRIO DE CONSULTAS ===");

        long totalConsultas = consultas.size();
        long consultasAgendadas = consultas.values().stream()
                .filter(c -> c.getStatus() == Consulta.StatusConsulta.AGENDADA)
                .count();
        long consultasRealizadas = consultas.values().stream()
                .filter(c -> c.getStatus() == Consulta.StatusConsulta.REALIZADA)
                .count();
        long consultasCanceladas = consultas.values().stream()
                .filter(c -> c.getStatus() == Consulta.StatusConsulta.CANCELADA)
                .count();

        System.out.println("Total de consultas: " + totalConsultas);
        System.out.println("Consultas agendadas: " + consultasAgendadas);
        System.out.println("Consultas realizadas: " + consultasRealizadas);
        System.out.println("Consultas canceladas: " + consultasCanceladas);

        // Top 3 médicos mais solicitados
        System.out.println("\nTop 3 médicos mais solicitados:");
        Map<String, Long> medicosCount = consultas.values().stream()
                .collect(Collectors.groupingBy(c -> c.getMedico().getNome(), Collectors.counting()));

        medicosCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " consultas"));

        // Próximas consultas
        System.out.println("\nPróximas 5 consultas:");
        consultas.values().stream()
                .filter(c -> c.getStatus() == Consulta.StatusConsulta.AGENDADA)
                .sorted(Comparator.comparing(Consulta::getDataHora))
                .limit(5)
                .forEach(System.out::println);
    }

    // Validações
    public boolean verificarDisponibilidadeMedico(String crm, LocalDateTime dataHora) {
        return consultas.values().stream()
                .noneMatch(c -> c.getMedico().getCrm().equals(crm) &&
                               c.getStatus() == Consulta.StatusConsulta.AGENDADA &&
                               c.getDataHora().equals(dataHora));
    }

    public boolean verificarDisponibilidadePaciente(String cpf, LocalDateTime dataHora) {
        return consultas.values().stream()
                .noneMatch(c -> c.getPaciente().getCpf().equals(cpf) &&
                               c.getStatus() == Consulta.StatusConsulta.AGENDADA &&
                               c.getDataHora().equals(dataHora));
    }





}