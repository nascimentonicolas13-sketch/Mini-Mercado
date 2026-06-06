import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Produto> produtos = new ArrayList<>();
    static ArrayList<Cliente> clientes = new ArrayList<>();

    public static void main(String[] args) {
        // Carrega os dados salvos ao iniciar
        produtos = Arquivos.carregarProdutos();
        clientes = Arquivos.carregarClientes();

        int opcao = 0;
        do {
            System.out.println("\n====================================");
            System.out.println("     MINI MERCADO - MENU PRINCIPAL  ");
            System.out.println("====================================");
            System.out.println("  1. Produtos");
            System.out.println("  2. Clientes");
            System.out.println("  3. Realizar Compra");
            System.out.println("  4. Controle de Estoque");
            System.out.println("  0. Sair");
            System.out.println("------------------------------------");
            System.out.print("  Opcao: ");
            opcao = lerInt();

            if (opcao == 1) menuProdutos();
            else if (opcao == 2) menuClientes();
            else if (opcao == 3) realizarCompra();
            else if (opcao == 4) controleEstoque();
            else if (opcao != 0) System.out.println("Opcao invalida!");

        } while (opcao != 0);

        System.out.println("\nSistema encerrado. Ate logo!");
    }

    // ============================================================
    //  MENU PRODUTOS
    // ============================================================

    static void menuProdutos() {
        int opcao = 0;
        do {
            System.out.println("\n====================================");
            System.out.println("         MENU DE PRODUTOS           ");
            System.out.println("====================================");
            System.out.println("  1. Cadastrar Produto");
            System.out.println("  2. Listar Produtos");
            System.out.println("  3. Buscar Produto");
            System.out.println("  4. Alterar Produto");
            System.out.println("  5. Remover Produto");
            System.out.println("  0. Voltar");
            System.out.println("------------------------------------");
            System.out.print("  Opcao: ");
            opcao = lerInt();

            if (opcao == 1) cadastrarProduto();
            else if (opcao == 2) listarProdutos();
            else if (opcao == 3) buscarProduto();
            else if (opcao == 4) alterarProduto();
            else if (opcao == 5) removerProduto();
            else if (opcao != 0) System.out.println("Opcao invalida!");

        } while (opcao != 0);
    }

    static void cadastrarProduto() {
        System.out.println("\n====================================");
        System.out.println("        CADASTRAR PRODUTO           ");
        System.out.println("====================================");

        int codigo = proximoCodigoProduto();
        System.out.println("Codigo gerado: " + codigo);

        System.out.print("Digite o Nome: ");
        String nome = sc.nextLine();

        System.out.print("Digite o Preco: ");
        double preco = lerDouble();

        System.out.print("Digite o Estoque: ");
        int estoque = lerInt();

        produtos.add(new Produto(codigo, nome, preco, estoque));
        Arquivos.salvarProdutos(produtos);
        System.out.println("\nProduto cadastrado com sucesso!");
        pausar();
    }

    static void listarProdutos() {
        System.out.println("\n====================================");
        System.out.println("         LISTA DE PRODUTOS          ");
        System.out.println("====================================");
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            System.out.printf("%-6s %-20s %-10s %-8s%n", "Cod.", "Nome", "Preco", "Estoque");
            System.out.println("------------------------------------");
            for (Produto p : produtos) {
                System.out.printf("%-6d %-20s R$%-8.2f %-8d%n",
                        p.codigo, p.nome, p.preco, p.estoque);
            }
        }
        pausar();
    }

    static void buscarProduto() {
        System.out.println("\n====================================");
        System.out.println("         BUSCAR PRODUTO             ");
        System.out.println("====================================");
        System.out.print("Digite o codigo do produto: ");
        int codigo = lerInt();

        Produto p = encontrarProduto(codigo);
        if (p != null) {
            System.out.println("\nProduto encontrado:");
            System.out.println("Codigo : " + p.codigo);
            System.out.println("Nome   : " + p.nome);
            System.out.println("Preco  : R$ " + String.format("%.2f", p.preco));
            System.out.println("Estoque: " + p.estoque);
        } else {
            System.out.println("Produto nao encontrado.");
        }
        pausar();
    }

    static void alterarProduto() {
        System.out.println("\n====================================");
        System.out.println("         ALTERAR PRODUTO            ");
        System.out.println("====================================");
        System.out.print("Digite o codigo do produto: ");
        int codigo = lerInt();

        Produto p = encontrarProduto(codigo);
        if (p == null) {
            System.out.println("Produto nao encontrado.");
            pausar();
            return;
        }

        System.out.println("Produto atual: " + p.nome + " | R$ " + p.preco + " | Estoque: " + p.estoque);
        System.out.println("(Deixe em branco para manter o valor atual)");

        System.out.print("Novo nome [" + p.nome + "]: ");
        String nome = sc.nextLine();
        if (!nome.isEmpty()) p.nome = nome;

        System.out.print("Novo preco [" + p.preco + "]: ");
        String precoStr = sc.nextLine();
        if (!precoStr.isEmpty()) p.preco = Double.parseDouble(precoStr.replace(",", "."));

        System.out.print("Novo estoque [" + p.estoque + "]: ");
        String estoqueStr = sc.nextLine();
        if (!estoqueStr.isEmpty()) p.estoque = Integer.parseInt(estoqueStr);

        Arquivos.salvarProdutos(produtos);
        System.out.println("\nProduto alterado com sucesso!");
        pausar();
    }

    static void removerProduto() {
        System.out.println("\n====================================");
        System.out.println("         REMOVER PRODUTO            ");
        System.out.println("====================================");
        System.out.print("Digite o codigo do produto: ");
        int codigo = lerInt();

        Produto p = encontrarProduto(codigo);
        if (p == null) {
            System.out.println("Produto nao encontrado.");
            pausar();
            return;
        }

        System.out.println("Produto: " + p.nome);
        System.out.print("Confirmar remocao? (S/N): ");
        String conf = sc.nextLine();
        if (conf.equalsIgnoreCase("S")) {
            produtos.remove(p);
            Arquivos.salvarProdutos(produtos);
            System.out.println("Produto removido com sucesso!");
        } else {
            System.out.println("Operacao cancelada.");
        }
        pausar();
    }

    // ============================================================
    //  MENU CLIENTES
    // ============================================================

    static void menuClientes() {
        int opcao = 0;
        do {
            System.out.println("\n====================================");
            System.out.println("         MENU DE CLIENTES           ");
            System.out.println("====================================");
            System.out.println("  1. Cadastrar Cliente");
            System.out.println("  2. Listar Clientes");
            System.out.println("  3. Buscar Cliente");
            System.out.println("  4. Alterar Cliente");
            System.out.println("  5. Remover Cliente");
            System.out.println("  0. Voltar");
            System.out.println("------------------------------------");
            System.out.print("  Opcao: ");
            opcao = lerInt();

            if (opcao == 1) cadastrarCliente();
            else if (opcao == 2) listarClientes();
            else if (opcao == 3) buscarCliente();
            else if (opcao == 4) alterarCliente();
            else if (opcao == 5) removerCliente();
            else if (opcao != 0) System.out.println("Opcao invalida!");

        } while (opcao != 0);
    }

    static void cadastrarCliente() {
        System.out.println("\n====================================");
        System.out.println("        CADASTRAR CLIENTE           ");
        System.out.println("====================================");

        System.out.print("Digite o CPF: ");
        String cpf = sc.nextLine();

        if (encontrarCliente(cpf) != null) {
            System.out.println("CPF ja cadastrado!");
            pausar();
            return;
        }

        System.out.print("Digite o Nome: ");
        String nome = sc.nextLine();

        System.out.print("Digite o Telefone: ");
        String telefone = sc.nextLine();

        System.out.print("Digite o Email: ");
        String email = sc.nextLine();

        clientes.add(new Cliente(cpf, nome, telefone, email));
        Arquivos.salvarClientes(clientes);
        System.out.println("\nCliente cadastrado com sucesso!");
        pausar();
    }

    static void listarClientes() {
        System.out.println("\n====================================");
        System.out.println("         LISTA DE CLIENTES          ");
        System.out.println("====================================");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            System.out.printf("%-14s %-20s %-15s %-20s%n", "CPF", "Nome", "Telefone", "Email");
            System.out.println("------------------------------------");
            for (Cliente c : clientes) {
                System.out.printf("%-14s %-20s %-15s %-20s%n",
                        c.cpf, c.nome, c.telefone, c.email);
            }
        }
        pausar();
    }

    static void buscarCliente() {
        System.out.println("\n====================================");
        System.out.println("         BUSCAR CLIENTE             ");
        System.out.println("====================================");
        System.out.print("Digite o CPF: ");
        String cpf = sc.nextLine();

        Cliente c = encontrarCliente(cpf);
        if (c != null) {
            System.out.println("\nCliente encontrado:");
            System.out.println("CPF     : " + c.cpf);
            System.out.println("Nome    : " + c.nome);
            System.out.println("Telefone: " + c.telefone);
            System.out.println("Email   : " + c.email);
        } else {
            System.out.println("Cliente nao encontrado.");
        }
        pausar();
    }

    static void alterarCliente() {
        System.out.println("\n====================================");
        System.out.println("         ALTERAR CLIENTE            ");
        System.out.println("====================================");
        System.out.print("Digite o CPF: ");
        String cpf = sc.nextLine();

        Cliente c = encontrarCliente(cpf);
        if (c == null) {
            System.out.println("Cliente nao encontrado.");
            pausar();
            return;
        }

        System.out.println("(Deixe em branco para manter o valor atual)");

        System.out.print("Novo nome [" + c.nome + "]: ");
        String nome = sc.nextLine();
        if (!nome.isEmpty()) c.nome = nome;

        System.out.print("Novo telefone [" + c.telefone + "]: ");
        String tel = sc.nextLine();
        if (!tel.isEmpty()) c.telefone = tel;

        System.out.print("Novo email [" + c.email + "]: ");
        String email = sc.nextLine();
        if (!email.isEmpty()) c.email = email;

        Arquivos.salvarClientes(clientes);
        System.out.println("\nCliente alterado com sucesso!");
        pausar();
    }

    static void removerCliente() {
        System.out.println("\n====================================");
        System.out.println("         REMOVER CLIENTE            ");
        System.out.println("====================================");
        System.out.print("Digite o CPF: ");
        String cpf = sc.nextLine();

        Cliente c = encontrarCliente(cpf);
        if (c == null) {
            System.out.println("Cliente nao encontrado.");
            pausar();
            return;
        }

        System.out.println("Cliente: " + c.nome);
        System.out.print("Confirmar remocao? (S/N): ");
        String conf = sc.nextLine();
        if (conf.equalsIgnoreCase("S")) {
            clientes.remove(c);
            Arquivos.salvarClientes(clientes);
            System.out.println("Cliente removido com sucesso!");
        } else {
            System.out.println("Operacao cancelada.");
        }
        pausar();
    }

    // ============================================================
    //  REALIZAR COMPRA
    // ============================================================

    static void realizarCompra() {
        System.out.println("\n====================================");
        System.out.println("         REALIZAR COMPRA            ");
        System.out.println("====================================");

        // Verifica se e cliente cadastrado
        String nomeCliente = "Nao identificado";
        System.out.print("Voce e cliente cadastrado? (S/N): ");
        String resp = sc.nextLine();
        if (resp.equalsIgnoreCase("S")) {
            System.out.print("Digite seu CPF: ");
            String cpf = sc.nextLine();
            Cliente c = encontrarCliente(cpf);
            if (c != null) {
                nomeCliente = c.nome;
                System.out.println("Bem-vindo, " + c.nome + "!");
            } else {
                System.out.println("CPF nao encontrado. Continuando sem identificacao.");
            }
        }

        // Listas para guardar a compra
        ArrayList<Produto> itensProduto = new ArrayList<>();
        ArrayList<Integer> itensQtd     = new ArrayList<>();

        // Adicionar produtos
        while (true) {
            System.out.println("\n------------------------------------");
            System.out.print("Codigo do produto (0 para finalizar): ");
            int codigo = lerInt();
            if (codigo == 0) break;

            Produto p = encontrarProduto(codigo);
            if (p == null) {
                System.out.println("Produto nao encontrado!");
                continue;
            }

            System.out.println("Produto: " + p.nome + " | Preco: R$ " +
                    String.format("%.2f", p.preco) + " | Estoque: " + p.estoque);

            if (p.estoque == 0) {
                System.out.println("Produto sem estoque!");
                continue;
            }

            System.out.print("Quantidade: ");
            int qtd = lerInt();

            if (qtd <= 0) {
                System.out.println("Quantidade invalida!");
                continue;
            }

            if (qtd > p.estoque) {
                System.out.println("Estoque insuficiente! Disponivel: " + p.estoque);
                continue;
            }

            itensProduto.add(p);
            itensQtd.add(qtd);
            System.out.println("Item adicionado!");
        }

        if (itensProduto.isEmpty()) {
            System.out.println("Nenhum item adicionado. Compra cancelada.");
            pausar();
            return;
        }

        // Exibe cupom
        System.out.println("\n====================================");
        System.out.println("           CUPOM DE COMPRA          ");
        System.out.println("====================================");
        System.out.println("Cliente: " + nomeCliente);
        System.out.println("------------------------------------");
        System.out.printf("%-20s %4s %8s %10s%n", "Produto", "Qtd", "Unit.", "Subtotal");
        System.out.println("------------------------------------");

        double total = 0;
        for (int i = 0; i < itensProduto.size(); i++) {
            Produto p  = itensProduto.get(i);
            int qtd    = itensQtd.get(i);
            double sub = p.preco * qtd;
            total += sub;
            System.out.printf("%-20s %4d R$%6.2f  R$%7.2f%n",
                    p.nome, qtd, p.preco, sub);
        }

        System.out.println("------------------------------------");
        System.out.printf("%-28s R$%7.2f%n", "TOTAL", total);
        System.out.println("====================================");

        // Confirma compra
        System.out.print("\nConfirmar compra? (S/N): ");
        String conf = sc.nextLine();
        if (conf.equalsIgnoreCase("S")) {
            for (int i = 0; i < itensProduto.size(); i++) {
                itensProduto.get(i).estoque -= itensQtd.get(i);
            }
            Arquivos.salvarProdutos(produtos);
            System.out.println("Compra realizada com sucesso! Obrigado!");
        } else {
            System.out.println("Compra cancelada.");
        }
        pausar();
    }

    // ============================================================
    //  CONTROLE DE ESTOQUE
    // ============================================================

    static void controleEstoque() {
        int opcao = 0;
        do {
            System.out.println("\n====================================");
            System.out.println("       CONTROLE DE ESTOQUE          ");
            System.out.println("====================================");
            System.out.println("  1. Ver Estoque Completo");
            System.out.println("  2. Produtos com Estoque Baixo");
            System.out.println("  3. Produtos Sem Estoque");
            System.out.println("  4. Repor Estoque");
            System.out.println("  0. Voltar");
            System.out.println("------------------------------------");
            System.out.print("  Opcao: ");
            opcao = lerInt();

            if (opcao == 1) {
                listarProdutos();
            } else if (opcao == 2) {
                System.out.println("\n--- Estoque Baixo (menos de 5 unidades) ---");
                boolean achou = false;
                for (Produto p : produtos) {
                    if (p.estoque > 0 && p.estoque < 5) {
                        System.out.println(p.nome + " - Estoque: " + p.estoque);
                        achou = true;
                    }
                }
                if (!achou) System.out.println("Nenhum produto com estoque baixo.");
                pausar();
            } else if (opcao == 3) {
                System.out.println("\n--- Produtos Sem Estoque ---");
                boolean achou = false;
                for (Produto p : produtos) {
                    if (p.estoque == 0) {
                        System.out.println(p.nome);
                        achou = true;
                    }
                }
                if (!achou) System.out.println("Todos os produtos tem estoque.");
                pausar();
            } else if (opcao == 4) {
                System.out.print("Codigo do produto: ");
                int cod = lerInt();
                Produto p = encontrarProduto(cod);
                if (p == null) {
                    System.out.println("Produto nao encontrado.");
                } else {
                    System.out.println("Estoque atual de " + p.nome + ": " + p.estoque);
                    System.out.print("Quantidade a repor: ");
                    int qtd = lerInt();
                    p.estoque += qtd;
                    Arquivos.salvarProdutos(produtos);
                    System.out.println("Novo estoque: " + p.estoque);
                }
                pausar();
            } else if (opcao != 0) {
                System.out.println("Opcao invalida!");
            }

        } while (opcao != 0);
    }

    // ============================================================
    //  METODOS AUXILIARES
    // ============================================================

    static Produto encontrarProduto(int codigo) {
        for (Produto p : produtos) {
            if (p.codigo == codigo) return p;
        }
        return null;
    }

    static Cliente encontrarCliente(String cpf) {
        for (Cliente c : clientes) {
            if (c.cpf.equals(cpf)) return c;
        }
        return null;
    }

    static int proximoCodigoProduto() {
        int max = 0;
        for (Produto p : produtos) {
            if (p.codigo > max) max = p.codigo;
        }
        return max + 1;
    }

    static void pausar() {
        System.out.print("\nPressione ENTER para continuar...");
        sc.nextLine();
    }

    static int lerInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Digite um numero valido: ");
            }
        }
    }

    static double lerDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.print("Digite um valor valido (ex: 5.90): ");
            }
        }
    }
}
