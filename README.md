# 🏥 Sistema de Agendamento Médico em Java (POO)

Sistema orientado a objetos para agendamento e gerenciamento de consultas médicas e lembretes, desenvolvido em **Java** com foco na aplicação prática dos pilares da **Programação Orientada a Objetos (POO)**.

---

## 📐 Princípios de POO Aplicados
* **Abstração & Interfaces:** Interface `Agendavel` desacoplando as operações de validação de horário, agendamento e cancelamento.
* **Polimorfismo:** Tratamento uniforme de diferentes eventos agendáveis (`Consulta` e `Lembrete`).
* **Encapsulamento:** Proteção do estado das entidades (`Medico`, `Paciente`, `Consulta`) com métodos de acesso e mutadores controlados.
* **Composição:** A classe `SistemaAgendamento` orquestra entidades interdependentes de forma coesa.

---

## 🛠️ Tecnologias
* **Linguagem:** Java 17+
* **APIs Padrão:** `java.time.LocalDateTime`, `java.time.format.DateTimeFormatter`, `java.util.Scanner`

---

## 📁 Estrutura de Classes
```text
SistemaAgendamentoMedico/
├── Agendavel.java          # Interface comum para eventos agendáveis
├── Consulta.java           # Implementa Agendavel (Médico + Paciente + Data/Hora)
├── Lembrete.java           # Implementa Agendavel (Avisos e notificações)
├── Medico.java             # Entidade Médico com CRM e Especialidade
├── Paciente.java           # Entidade Paciente com CPF e Histórico
├── SistemaAgendamento.java # Gerenciador de regras e persistência em memória
└── Main.java               # Interface interativa de console (CLI)
```

---

## 🚀 Como Compilar e Executar

1. **Acesse o diretório do código:**
   ```bash
   cd SistemaAgendamentoMedico
   ```

2. **Compile as classes:**
   ```bash
   javac *.java
   ```

3. **Execute o sistema:**
   ```bash
   java Main
   ```

---

## 👨‍💻 Autor
Desenvolvido por [Samuel de Almeida Sousa](https://github.com/samualmeid4).
