import java.util.ArrayList;
import java.util.List;

public class MenuEstoque {
    private ProdutoService produtoService;

    public MenuEstoque(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    public void exibir() {
        boolean voltar = false;
        while (!voltar) {
            Console.cabecalho("CONTROLE DE ESTOQUE");
            System.out.println("  1. Visualizar Estoque Completo");
            System.out.println("  2. Produtos com Estoque Baixo");
            System.out.println("  3. Produtos Sem Estoque");
            System.out.println("  4. Repor Estoque");
            System.out.println("  0. Voltar");
            Console.linha();
            switch (Console.lerString("  Opcao: ")) {
                case "1": produtoService.imprimirTabela(produtoService.listar()); Console.pausar(); break;
                case "2": estoqueBaixo(); break;
                case "3": semEstoque(); break;
                case "4": reporEstoque(); break;
                case "0": voltar = true; break;
                default: Console.erro("Opcao invalida!");
            }
        }
    }

    private void estoqueBaixo() {
        Console.cabecalho("ESTOQUE BAIXO");
        List<Produto> lista = new ArrayList<>();
        for (Produto p : produtoService.listar())
            if (p.getEstoque() > 0 && p.getEstoque() < 5) lista.add(p);
        if (lista.isEmpty()) Console.info("Nenhum produto com estoque baixo.");
        else produtoService.imprimirTabela(lista);
        Console.pausar();
    }

    private void semEstoque() {
        Console.cabecalho("SEM ESTOQUE");
        List<Produto> lista = new ArrayList<>();
        for (Produto p : produtoService.listar())
            if (p.getEstoque() == 0) lista.add(p);
        if (lista.isEmpty()) Console.info("Todos os produtos possuem estoque.");
        else produtoService.imprimirTabela(lista);
        Console.pausar();
    }

    private void reporEstoque() {
        Console.cabecalho("REPOR ESTOQUE");
        int codigo = Console.lerInt("Codigo do Produto: ");
        Produto p = produtoService.buscarPorCodigo(codigo);
        if (p == null) { Console.erro("Produto nao encontrado."); Console.pausar(); return; }
        Console.info("Estoque atual: " + p.getEstoque());
        int qtd = Console.lerInt("Quantidade a repor: ");
        produtoService.alterar(codigo, "", p.getPreco(), p.getEstoque() + qtd);
        Console.sucesso("Estoque atualizado!");
        Console.pausar();
    }
}