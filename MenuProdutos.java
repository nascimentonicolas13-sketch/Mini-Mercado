import java.util.Arrays;
 
public class MenuProdutos {
    private ProdutoService produtoService;
 
    public MenuProdutos(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }
 
    public void exibir() {
        boolean voltar = false;
        while (!voltar) {
            Console.cabecalho("MENU DE PRODUTOS");
            System.out.println("  1. Cadastrar Produto");
            System.out.println("  2. Listar Produtos");
            System.out.println("  3. Buscar Produto");
            System.out.println("  4. Alterar Produto");
            System.out.println("  5. Remover Produto");
            System.out.println("  0. Voltar");
            Console.linha();
            switch (Console.lerString("  Opcao: ")) {
                case "1": cadastrar(); break;
                case "2": listar(); break;
                case "3": buscar(); break;
                case "4": alterar(); break;
                case "5": remover(); break;
                case "0": voltar = true; break;
                default: Console.erro("Opcao invalida!");
            }
        }
    }
 
    private void cadastrar() {
        Console.cabecalho("CADASTRAR PRODUTO");
        int codigo = produtoService.proximoCodigo();
        Console.info("Codigo gerado automaticamente: " + codigo);
        String nome = Console.lerString("Digite o Nome: ");
        if (nome.isEmpty()) { Console.erro("Nome nao pode ser vazio."); Console.pausar(); return; }
        double preco = Console.lerDouble("Digite o Preco (ex: 5.90): ");
        int estoque = Console.lerInt("Digite a Quantidade em Estoque: ");
        if (produtoService.cadastrar(new Produto(codigo, nome, preco, estoque)))
            Console.sucesso("Produto cadastrado com sucesso!");
        else
            Console.erro("Erro ao cadastrar produto.");
        Console.pausar();
    }
 
    private void listar() {
        Console.cabecalho("LISTA DE PRODUTOS");
        produtoService.imprimirTabela(produtoService.listar());
        Console.pausar();
    }
 
    private void buscar() {
        Console.cabecalho("BUSCAR PRODUTO");
        System.out.println("  1. Por Codigo\n  2. Por Nome");
        Produto p = null;
        String op = Console.lerString("  Opcao: ");
        if (op.equals("1")) p = produtoService.buscarPorCodigo(Console.lerInt("Codigo: "));
        else if (op.equals("2")) p = produtoService.buscarPorNome(Console.lerString("Nome: "));
        else { Console.erro("Invalido."); Console.pausar(); return; }
        if (p != null) produtoService.imprimirTabela(Arrays.asList(p));
        else Console.erro("Produto nao encontrado.");
        Console.pausar();
    }
 
    private void alterar() {
        Console.cabecalho("ALTERAR PRODUTO");
        int codigo = Console.lerInt("Codigo do Produto: ");
        Produto p = produtoService.buscarPorCodigo(codigo);
        if (p == null) { Console.erro("Produto nao encontrado."); Console.pausar(); return; }
        produtoService.imprimirTabela(Arrays.asList(p));
        System.out.println("  (Deixe em branco para manter)");
        String novoNome = Console.lerString("Novo Nome [" + p.getNome() + "]: ");
        String precoStr = Console.lerString("Novo Preco [" + p.getPreco() + "]: ");
        String estStr   = Console.lerString("Novo Estoque [" + p.getEstoque() + "]: ");
        double novoPreco  = precoStr.isEmpty()  ? p.getPreco()   : Double.parseDouble(precoStr.replace(",","."));
        int novoEstoque   = estStr.isEmpty()     ? p.getEstoque() : Integer.parseInt(estStr);
        if (produtoService.alterar(codigo, novoNome, novoPreco, novoEstoque))
            Console.sucesso("Produto alterado com sucesso!");
        else Console.erro("Erro ao alterar.");
        Console.pausar();
    }
 
    private void remover() {
        Console.cabecalho("REMOVER PRODUTO");
        int codigo = Console.lerInt("Codigo do Produto: ");
        Produto p = produtoService.buscarPorCodigo(codigo);
        if (p == null) { Console.erro("Produto nao encontrado."); Console.pausar(); return; }
        produtoService.imprimirTabela(Arrays.asList(p));
        if (Console.lerString("\n  Confirmar remocao? (S/N): ").equalsIgnoreCase("S")) {
            produtoService.remover(codigo);
            Console.sucesso("Produto removido com sucesso!");
        } else Console.info("Operacao cancelada.");
        Console.pausar();
    }
}