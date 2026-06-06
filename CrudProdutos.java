import java.util.ArrayList;
import java.util.Scanner;

public class CrudProdutos {

    static Scanner sc = new Scanner(System.in);

    // Menu de Produtos
    public static void menu(ArrayList<Produto> produtos) {
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

            if (opcao == 1)      cadastrar(produtos);
            else if (opcao == 2) listar(produtos);
            else if (opcao == 3) buscar(produtos);
            else if (opcao == 4) alterar(produtos);
            else if (opcao == 5) remover(produtos);
            else if (opcao != 0) System.out.println("Opcao invalida!");

        } while (opcao != 0);
    }

    // CREATE - Cadastrar produto
    static void cadastrar(ArrayList<Produto> produtos) {
        System.out.println("\n====================================");
        System.out.println("        CADASTRAR PRODUTO           ");
        System.out.println("====================================");

        int codigo = proximoCodigo(produtos);
        System.out.println("Codigo gerado automaticamente: " + codigo);

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

    // READ - Listar produtos
    public static void listar(ArrayList<Produto> produtos) {
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

    // READ - Buscar produto por codigo
    static void buscar(ArrayList<Produto> produtos) {
        System.out.println("\n====================================");
        System.out.println("         BUSCAR PRODUTO             ");
        System.out.println("====================================");
        System.out.print("Digite o Codigo do Produto: ");
        int codigo = lerInt();

        Produto p = encontrar(produtos, codigo);
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

    // UPDATE - Alterar produto
    static void alterar(ArrayList<Produto> produtos) {
        System.out.println("\n====================================");
        System.out.println("         ALTERAR PRODUTO            ");
        System.out.println("====================================");
        System.out.print("Digite o Codigo do Produto: ");
        int codigo = lerInt();

        Produto p = encontrar(produtos, codigo);
        if (p == null) {
            System.out.println("Produto nao encontrado.");
            pausar();
            return;
        }

        System.out.println("Produto atual: " + p.nome + " | R$ " + p.preco + " | Estoque: " + p.estoque);
        System.out.println("(Deixe em branco para manter o valor atual)");

        System.out.print("Novo Nome [" + p.nome + "]: ");
        String nome = sc.nextLine();
        if (!nome.isEmpty()) p.nome = nome;

        System.out.print("Novo Preco [" + p.preco + "]: ");
        String precoStr = sc.nextLine();
        if (!precoStr.isEmpty()) p.preco = Double.parseDouble(precoStr.replace(",", "."));

        System.out.print("Novo Estoque [" + p.estoque + "]: ");
        String estStr = sc.nextLine();
        if (!estStr.isEmpty()) p.estoque = Integer.parseInt(estStr);

        Arquivos.salvarProdutos(produtos);
        System.out.println("\nProduto alterado com sucesso!");
        pausar();
    }

    // DELETE - Remover produto
    static void remover(ArrayList<Produto> produtos) {
        System.out.println("\n====================================");
        System.out.println("         REMOVER PRODUTO            ");
        System.out.println("====================================");
        System.out.print("Digite o Codigo do Produto: ");
        int codigo = lerInt();

        Produto p = encontrar(produtos, codigo);
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

    // Metodo auxiliar para encontrar produto pelo codigo
    public static Produto encontrar(ArrayList<Produto> produtos, int codigo) {
        for (Produto p : produtos) {
            if (p.codigo == codigo) return p;
        }
        return null;
    }

    // Gera o proximo codigo disponivel
    static int proximoCodigo(ArrayList<Produto> produtos) {
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
