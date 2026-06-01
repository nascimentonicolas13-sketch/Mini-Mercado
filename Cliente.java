public class Cliente {
    private String cpf;
    private String nome;
    private String telefone;
    private String email;
 
    public Cliente(String cpf, String nome, String telefone, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }
 
    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }
 
    public void setNome(String nome) { this.nome = nome; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEmail(String email) { this.email = email; }
 
    public String toCSV() {
        return cpf + ";" + nome + ";" + telefone + ";" + email;
    }
 
    public static Cliente fromCSV(String linha) {
        String[] p = linha.split(";");
        return new Cliente(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim());
    }
 
    @Override
    public String toString() {
        return String.format("| %-14s | %-25s | %-16s | %-25s |",
                cpf, nome, telefone, email);
    }
}