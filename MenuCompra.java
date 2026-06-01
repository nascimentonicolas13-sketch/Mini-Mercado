import java.util.ArrayList;
import java.util.List;
 
public class MenuCompra {
    private ProdutoService produtoService;
    private ClienteService clienteService;
 
    public MenuCompra(ProdutoService produtoService, ClienteService clienteService) {
        this.produtoService = produtoService;
        this.clienteService = clienteService;
    }
 
    public void realizar() {
        Console.cabecalho("REALIZAR COMPRA");
 
        Cliente cliente = null;
        if (Console.lerString("Voce e cliente cadastrado? (S/N): ").equalsIgnoreCase("S")) {
            cliente = clienteService.buscarPorCpf(Console.lerString("Digite seu CPF: "));
            if (cliente != null) Console.sucesso("Bem-vindo, " + cliente.getNome() + "!");
            else Console.erro("CPF nao encontrado. Prosseguindo como nao cadastrado.");
        }
 
        List<ItemCompra> itens = new ArrayList<>();
        System.out.println();
 
        while (true) {
            Console.linha();
            int codigo = Console.lerInt("Codigo do produto (0 para finalizar): ");
            if (codigo == 0) break;
 
            Produto produto = produtoService.buscarPorCodigo(codigo);
            if (produto == null) { Console.erro("Produto nao encontrado."); continue; }
 
            Console.info("Produto: " + produto.getNome() +
                    " | Preco: R$ " + String.format("%.2f", produto.getPreco()) +
                    " | Estoque: " + produto.getEstoque());
 
            if (produto.getEstoque() == 0) { Console.erro("Produto sem estoque!"); continue; }
 
            int quantidade = Console.lerInt("Quantidade desejada: ");
            if (quantidade <= 0) { Console.erro("Quantidade invalida."); continue; }
            if (quantidade > produto.getEstoque()) {
                Console.erro("Estoque insuficiente! Disponivel: " + produto.getEstoque());
                continue;
            }
 
            itens.add(new ItemCompra(produto, quantidade));
            Console.sucesso("Item adicionado ao carrinho.");
        }
 
        if (itens.isEmpty()) {
            Console.info("Nenhum item adicionado. Compra cancelada.");
            Console.pausar();
            return;
        }
 
        exibirCupom(cliente, itens);
 
        if (Console.lerString("\n  Confirmar compra? (S/N): ").equalsIgnoreCase("S")) {
            for (ItemCompra item : itens)
                produtoService.reduzirEstoque(item.getProduto().getCodigo(), item.getQuantidade());
            Console.sucesso("Compra realizada com sucesso! Obrigado pela preferencia.");
        } else {
            Console.info("Compra cancelada.");
        }
        Console.pausar();
    }
 
    private void exibirCupom(Cliente cliente, List<ItemCompra> itens) {
        System.out.println("\n====================================");
        System.out.println("         CUPOM DE COMPRA            ");
        System.out.println("====================================");
        if (cliente != null) {
            System.out.println("  Cliente : " + cliente.getNome());
            System.out.println("  CPF     : " + cliente.getCpf());
        } else {
            System.out.println("  Cliente : Nao identificado");
        }
        System.out.println("------------------------------------");
        System.out.printf("  %-20s %5s %9s %10s%n", "Produto", "Qtd", "Unit.", "Subtotal");
        System.out.println("------------------------------------");
 
        double total = 0;
        for (ItemCompra item : itens) {
            double sub = item.getSubtotal();
            total += sub;
            System.out.printf("  %-20s %5d R$%6.2f  R$%7.2f%n",
                    item.getProduto().getNome(), item.getQuantidade(),
                    item.getProduto().getPreco(), sub);
        }
 
        System.out.println("------------------------------------");
        System.out.printf("  %-29s R$%7.2f%n", "TOTAL", total);
        System.out.println("====================================");
    }
}