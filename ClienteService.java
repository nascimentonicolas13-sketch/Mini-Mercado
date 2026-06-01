import java.util.ArrayList;
import java.util.List;
 
public class ClienteService {
    private static final String ARQUIVO = "data/clientes.csv";
    private static final String CABECALHO = "cpf;nome;telefone;email";
    private List<Cliente> clientes = new ArrayList<>();
 
    public ClienteService() { carregar(); }
 
    private void carregar() {
        clientes.clear();
        for (String linha : ArquivoCSV.lerLinhas(ARQUIVO)) {
            try { clientes.add(Cliente.fromCSV(linha)); }
            catch (Exception e) { System.out.println("Linha ignorada: " + linha); }
        }
    }
 
    public void salvar() {
        List<String> linhas = new ArrayList<>();
        for (Cliente c : clientes) linhas.add(c.toCSV());
        ArquivoCSV.escreverLinhas(ARQUIVO, CABECALHO, linhas);
    }
 
    public boolean cadastrar(Cliente cliente) {
        if (buscarPorCpf(cliente.getCpf()) != null) return false;
        clientes.add(cliente);
        salvar();
        return true;
    }
 
    public List<Cliente> listar() { return new ArrayList<>(clientes); }
 
    public Cliente buscarPorCpf(String cpf) {
        String limpo = cpf.replaceAll("[^0-9]", "");
        for (Cliente c : clientes)
            if (c.getCpf().replaceAll("[^0-9]", "").equals(limpo)) return c;
        return null;
    }
 
    public Cliente buscarPorNome(String nome) {
        for (Cliente c : clientes)
            if (c.getNome().toLowerCase().contains(nome.toLowerCase())) return c;
        return null;
    }
 
    public boolean alterar(String cpf, String novoNome, String novoTel, String novoEmail) {
        Cliente c = buscarPorCpf(cpf);
        if (c == null) return false;
        if (!novoNome.isEmpty()) c.setNome(novoNome);
        if (!novoTel.isEmpty()) c.setTelefone(novoTel);
        if (!novoEmail.isEmpty()) c.setEmail(novoEmail);
        salvar();
        return true;
    }
 
    public boolean remover(String cpf) {
        Cliente c = buscarPorCpf(cpf);
        if (c == null) return false;
        clientes.remove(c);
        salvar();
        return true;
    }
 
    public void imprimirTabela(List<Cliente> lista) {
        if (lista.isEmpty()) { Console.info("Nenhum cliente encontrado."); return; }
        String sep = "+----------------+---------------------------+------------------+---------------------------+";
        System.out.println(sep);
        System.out.printf("| %-14s | %-25s | %-16s | %-25s |%n", "CPF", "Nome", "Telefone", "Email");
        System.out.println(sep);
        for (Cliente c : lista) System.out.println(c);
        System.out.println(sep);
    }
}