import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;

public class Main {
    private static SistemaAgendamento sistema = new SistemaAgendamento();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE AGENDAMENTO DE CONSULTAS MÉDICAS ===");

        // Adicionar alguns dados de exemplo
        adicionarDadosExemplo();

        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarPaciente();
                    break;
                case 2:
                    cadastrarMedico();
                    break;
                case 3:
                    agendarConsulta();
                    break;
                case 4:
                    listarConsultas();
                    break;
                case 5:
                    cancelarConsulta();
                    break;
                case 6:
                    listarPacientes();
                    break;
                case 7:
                    listarMedicos();
                    break;
                case 8:
                    buscarConsultasPorPaciente();
                    break;
                case 9:
                    buscarConsultasPorMedico();
                    break;
                case 10:
                    gerarRelatorioConsultas();
                    break;
                case 11:
                    removerMedico();
                    break;

                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Cadastrar Paciente");
        System.out.println("2. Cadastrar Médico");
        System.out.println("3. Agendar Consulta");
        System.out.println("4. Listar Consultas");
        System.out.println("5. Cancelar Consulta");
        System.out.println("6. Listar Pacientes");
        System.out.println("7. Listar Médicos");
        System.out.println("8. Buscar Consultas por Paciente");
        System.out.println("9. Buscar Consultas por Médico");
        System.out.println("10. Gerar Relatório de Consultas");
        System.out.println("11. Remover Médico");
        System.out.println("0. Sair");
    }


    private static void removerMedico() {
        System.out.println("\n=== REMOVER MÉDICO ===");

        listarMedicos(); // Lista os médicos disponíveis
        String crm = lerString("Informe o CRM do médico a ser removido: ");

        try {
            sistema.removerMedico(crm);
            System.out.println("Médico removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao remover médico: " + e.getMessage());
        }
    }











    private static void cadastrarPaciente() {
        System.out.println("\n=== CADASTRAR PACIENTE ===");

        String nome = lerString("Nome: ");
        String cpf = lerString("CPF: ");
        String telefone = lerString("Telefone: ");
        String email = lerString("Email: ");
        int idade = lerInteiro("Idade: ");

        try {
            Paciente paciente = new Paciente(nome, cpf, telefone, email, idade);
            sistema.cadastrarPaciente(paciente);
            System.out.println("Paciente cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void cadastrarMedico() {
        System.out.println("\n=== CADASTRAR MÉDICO ===");

        String nome = lerString("Nome: ");
        String crm = lerString("CRM: ");
        String especialidade = lerString("Especialidade: ");
        String telefone = lerString("Telefone: ");

        try {
            Medico medico = new Medico(nome, crm, especialidade, telefone);
            sistema.cadastrarMedico(medico);
            System.out.println("Médico cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void agendarConsulta() {
        System.out.println("\n=== AGENDAR CONSULTA ===");

        // Listar pacientes
        System.out.println("Pacientes disponíveis:");
        sistema.listarPacientes().forEach(p -> System.out.println(p.getCpf() + " - " + p.getNome()));

        String cpfPaciente = lerString("CPF do paciente: ");

        // Listar médicos
        System.out.println("Médicos disponíveis:");
        sistema.listarMedicos().forEach(m -> System.out.println(m.getCrm() + " - " + m.getNome() + " (" + m.getEspecialidade() + ")"));

        String crmMedico = lerString("CRM do médico: ");

        String dataHoraStr = lerString("Data e hora (dd/MM/yyyy HH:mm): ");

        try {
            LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);
            String observacoes = lerString("Observações (opcional): ");

            Consulta consulta = sistema.agendarConsulta(cpfPaciente, crmMedico, dataHora, observacoes);
            System.out.println("Consulta agendada com sucesso! ID: " + consulta.getId());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarConsultas() {
        System.out.println("\n=== LISTAR CONSULTAS ===");
        sistema.listarConsultas().forEach(System.out::println);
    }

    private static void cancelarConsulta() {
        System.out.println("\n=== CANCELAR CONSULTA ===");

        listarConsultas();
        int id = lerInteiro("ID da consulta a cancelar: ");

        try {
            sistema.cancelarConsulta(id);
            System.out.println("Consulta cancelada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarPacientes() {
        System.out.println("\n=== LISTAR PACIENTES ===");
        sistema.listarPacientes().forEach(System.out::println);
    }

    private static void listarMedicos() {
        System.out.println("\n=== LISTAR MÉDICOS ===");
        sistema.listarMedicos().forEach(System.out::println);
    }

    private static void buscarConsultasPorPaciente() {
        System.out.println("\n=== BUSCAR CONSULTAS POR PACIENTE ===");
        String cpf = lerString("CPF do paciente: ");

        try {
            List<Consulta> consultas = sistema.buscarConsultasPorPaciente(cpf);
            if (consultas.isEmpty()) {
                System.out.println("Nenhuma consulta encontrada para este paciente.");
            } else {
                consultas.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void buscarConsultasPorMedico() {
        System.out.println("\n=== BUSCAR CONSULTAS POR MÉDICO ===");
        String crm = lerString("CRM do médico: ");

        try {
            List<Consulta> consultas = sistema.buscarConsultasPorMedico(crm);
            if (consultas.isEmpty()) {
                System.out.println("Nenhuma consulta encontrada para este médico.");
            } else {
                consultas.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void gerarRelatorioConsultas() {
        System.out.println("\n=== RELATÓRIO DE CONSULTAS ===");
        sistema.gerarRelatorioConsultas();
    }

   private static void adicionarDadosExemplo() {
    try {
        // Cadastrar pacientes de exemplo com CPFs válidos
        sistema.cadastrarPaciente(new Paciente("João Silva", "123.456.789-09", "(11) 99999-1111", "joao@email.com", 30));
        sistema.cadastrarPaciente(new Paciente("Maria Santos", "987.654.321-00", "(11) 99999-2222", "maria@email.com", 25));
        sistema.cadastrarPaciente(new Paciente("Pedro Costa", "111.222.333-96", "(11) 99999-3333", "pedro@email.com", 45));

        // Cadastrar médicos de exemplo
        sistema.cadastrarMedico(new Medico("Dr. Carlos Oliveira", "12345-SP", "Cardiologia", "(11) 88888-1111"));
        sistema.cadastrarMedico(new Medico("Dra. Ana Paula", "54321-SP", "Dermatologia", "(11) 88888-2222"));
        sistema.cadastrarMedico(new Medico("Dr. Roberto Lima", "67890-SP", "Ortopedia", "(11) 88888-3333"));
        sistema.cadastrarMedico(new Medico("Dr. Joao", "4444-SP", "Pé", "11"));
        sistema.cadastrarMedico(new Medico("Dr. Jose", "5555-SP", "Pé", "12"));

        //sistema.removerMedico("4444-SP");

        System.out.println("Dados de exemplo carregados com sucesso!");
    } catch (Exception e) {
        System.out.println("Erro ao carregar dados de exemplo: " + e.getMessage());
        e.printStackTrace(); // Para ver o erro completo
    }
}

    private static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
    }





}