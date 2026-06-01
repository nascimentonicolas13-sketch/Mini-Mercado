import java.util.ArrayList;
import java.util.List;
 
public class ProdutoService {
    private static final String ARQUIVO = "data/produtos.csv";
    private static final String CABECALHO = "codigo;nome;preco;estoque";
    private List<Produto> produtos = new ArrayList<>();
 
    public ProdutoService() { carregar(); }
 
    private void carregar() {
        produtos.clear();
        for (String linha : ArquivoCSV.lerLinhas(ARQUIVO)) {
            try { produtos.add(Produto.fromCSV(linha)); }
            catch (Exception e) { System.out.println("Linha ignorada: " + linha); }
        }
    }
 
    public void salvar() {
        List<String> linhas = new ArrayList<>();
        for (Produto p : produtos) linhas.add(p.toCSV());
        ArquivoCSV.escreverLinhas(ARQUIVO, CABECALHO, linhas);
    }
 
    public boolean cadastrar(Produto produto) {
        if (buscarPorCodigo(produto.getCodigo()) != null) return false;
        produtos.add(produto);
        salvar();
        return true;
    }
 
    public List<Produto> listar() { return new ArrayList<>(produtos); }
 
    public Produto buscarPorCodigo(int codigo) {
        for (Produto p : produtos) if (p.getCodigo() == codigo) return p;
        return null;
    }
 
    public Produto buscarPorNome(String nome) {
        for (Produto p : produtos)
            if (p.getNome().toLowerCase().contains(nome.toLowerCase())) return p;
        return null;
    }
 
    public boolean alterar(int codigo, String novoNome, double novoPreco, int novoEstoque) {
        Produto p = buscarPorCodigo(codigo);
        if (p == null) return false;
        if (!novoNome.isEmpty()) p.setNome(novoNome);
        if (novoPreco >= 0) p.setPreco(novoPreco);
        if (novoEstoque >= 0) p.setEstoque(novoEstoque);
        salvar();
        return true;
    }
 
    public boolean remover(int codigo) {
        Produto p = buscarPorCodigo(codigo);
        if (p == null) return false;
        produtos.remove(p);
        salvar();
        return true;
    }
 
    public boolean reduzirEstoque(int codigo, int quantidade) {
        Produto p = buscarPorCodigo(codigo);
        if (p == null || p.getEstoque() < quantidade) return false;
        p.setEstoque(p.getEstoque() - quantidade);
        salvar();
        return true;
    }
 
    public int proximoCodigo() {
        int max = 0;
        for (Produto p : produtos) if (p.getCodigo() > max) max = p.getCodigo();
        return max + 1;
    }
 
    public void imprimirTabela(List<Produto> lista) {
        if (lista.isEmpty()) { Console.info("Nenhum produto encontrado."); return; }
        String sep = "+--------+---------------------------+------------+----------+";
        System.out.println(sep);
        System.out.printf("| %-6s | %-25s | %-10s | %-8s |%n", "Cod.", "Nome", "Preco", "Estoque");
        System.out.println(sep);
        for (Produto p : lista) System.out.println(p);
        System.out.println(sep);
    }
}