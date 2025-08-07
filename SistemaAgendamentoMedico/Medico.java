public class Medico {
    private String nome;
    private String crm;
    private String especialidade;
    private String telefone;

    public Medico(String nome, String crm, String especialidade, String telefone) {
        validarDados(nome, crm, especialidade, telefone);
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
        this.telefone = telefone;
    }

    private void validarDados(String nome, String crm, String especialidade, String telefone) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        
        if (crm == null || crm.trim().isEmpty()) {
            throw new IllegalArgumentException("CRM não pode ser vazio");
        }
        
        if (especialidade == null || especialidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Especialidade não pode ser vazia");
        }
        
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio");
        }
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { 
        if (nome != null && !nome.trim().isEmpty()) {
            this.nome = nome; 
        }
    }

    public String getCrm() { return crm; }
    public void setCrm(String crm) { 
        if (crm != null && !crm.trim().isEmpty()) {
            this.crm = crm; 
        }
    }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { 
        if (especialidade != null && !especialidade.trim().isEmpty()) {
            this.especialidade = especialidade; 
        }
    }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { 
        if (telefone != null && !telefone.trim().isEmpty()) {
            this.telefone = telefone; 
        }
    }

    @Override
    public String toString() {
        return String.format("Médico{CRM: %s, Nome: %s, Especialidade: %s, Telefone: %s}", 
                           crm, nome, especialidade, telefone);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Medico medico = (Medico) obj;
        return crm.equals(medico.crm);
    }

    @Override
    public int hashCode() {
        return crm.hashCode();
    }
}