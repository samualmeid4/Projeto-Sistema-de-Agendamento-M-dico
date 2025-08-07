public class Paciente {
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private int idade;

    public Paciente(String nome, String cpf, String telefone, String email, int idade) {
        validarDados(nome, cpf, telefone, email, idade);
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.idade = idade;
    }

    private void validarDados(String nome, String cpf, String telefone, String email, int idade) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }
        
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio");
        }
        
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        
        if (idade <= 0 || idade > 150) {
            throw new IllegalArgumentException("Idade inválida");
        }
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { 
        if (nome != null && !nome.trim().isEmpty()) {
            this.nome = nome; 
        }
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { 
        if (cpf != null && !cpf.trim().isEmpty()) {
            this.cpf = cpf; 
        }
    }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { 
        if (telefone != null && !telefone.trim().isEmpty()) {
            this.telefone = telefone; 
        }
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { 
        if (email != null && !email.trim().isEmpty()) {
            this.email = email; 
        }
    }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { 
        if (idade > 0 && idade <= 150) {
            this.idade = idade; 
        }
    }

    @Override
    public String toString() {
        return String.format("Paciente{CPF: %s, Nome: %s, Idade: %d, Telefone: %s, Email: %s}", 
                           cpf, nome, idade, telefone, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Paciente paciente = (Paciente) obj;
        return cpf.equals(paciente.cpf);
    }

    @Override
    public int hashCode() {
        return cpf.hashCode();
    }
}