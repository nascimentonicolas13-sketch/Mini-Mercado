public class Main {
    public static void main(String[] args) {
        ProdutoService produtoService = new ProdutoService();
        ClienteService clienteService = new ClienteService();
        MenuProdutos menuProdutos = new MenuProdutos(produtoService);
        MenuClientes menuClientes = new MenuClientes(clienteService);
        MenuCompra menuCompra = new MenuCompra(produtoService, clienteService);
        MenuEstoque menuEstoque = new MenuEstoque(produtoService);
        boolean rodando = true;
        while (rodando) {
            Console.cabecalho("MINI MERCADO - MENU PRINCIPAL");
            System.out.println("  1. Produtos");
            System.out.println("  2. Clientes");
            System.out.println("  3. Realizar Compra");
            System.out.println("  4. Controle de Estoque");
            System.out.println("  0. Sair");
            Console.linha();
            switch (Console.lerString("  Opcao: ")) {
                case "1": menuProdutos.exibir(); break;
                case "2": menuClientes.exibir(); break;
                case "3": menuCompra.realizar(); break;
                case "4": menuEstoque.exibir(); break;
                case "0": Console.cabecalho("ATE LOGO!"); rodando = false; break;
                default: Console.erro("Opcao invalida!");
            }
        }
    }
}