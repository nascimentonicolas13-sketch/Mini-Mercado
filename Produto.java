public class Produto {
    private int codigo;
    private String nome;
    private double preco;
    private int estoque;

    public Produto(int codigo, String nome, double preco, int estoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    public int getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getEstoque() { return estoque; }

    public void setNome(String nome) { this.nome = nome; }
    public void setPreco(double preco) { this.preco = preco; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    public String toCSV() {
        return codigo + ";" + nome + ";" + preco + ";" + estoque;
    }

    public static Produto fromCSV(String linha) {
        String[] p = linha.split(";");
        return new Produto(Integer.parseInt(p[0].trim()), p[1].trim(),
                Double.parseDouble(p[2].trim()), Integer.parseInt(p[3].trim()));
    }

    @Override
    public String toString() {
        return String.format("| %-6d | %-25s | R$ %-8.2f | %-8d |",
                codigo, nome, preco, estoque);
    }
}