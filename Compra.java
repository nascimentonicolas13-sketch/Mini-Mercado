import java.util.ArrayList;
import java.util.Scanner;

public class Compra {

    static Scanner sc = new Scanner(System.in);

    // Realizar compra
    public static void realizar(ArrayList<Produto> produtos, ArrayList<Cliente> clientes) {
        System.out.println("\n====================================");
        System.out.println("         REALIZAR COMPRA            ");
        System.out.println("====================================");

        // Verificar se e cliente cadastrado
        String nomeCliente = "Nao identificado";
        System.out.print("Voce e cliente cadastrado? (S/N): ");
        String resp = sc.nextLine();

        if (resp.equalsIgnoreCase("S")) {
            System.out.print("Digite seu CPF: ");
            String cpf = sc.nextLine();
            Cliente c = CrudClientes.encontrar(clientes, cpf);
            if (c != null) {
                nomeCliente = c.nome;
                System.out.println("Bem-vindo, " + c.nome + "!");
            } else {
                System.out.println("CPF nao encontrado. Continuando sem identificacao.");
            }
        }

        // Listas para guardar os itens da compra
        ArrayList<Produto> itensProduto = new ArrayList<>();
        ArrayList<Integer> itensQtd     = new ArrayList<>();

        // Adicionar produtos ao carrinho
        while (true) {
            System.out.println("\n------------------------------------");
            System.out.print("Codigo do produto (0 para finalizar): ");
            int codigo = lerInt();
            if (codigo == 0) break;

            Produto p = CrudProdutos.encontrar(produtos, codigo);
            if (p == null) {
                System.out.println("Produto nao encontrado!");
                continue;
            }

            System.out.println("Produto: " + p.nome +
                    " | Preco: R$ " + String.format("%.2f", p.preco) +
                    " | Estoque: " + p.estoque);

            if (p.estoque == 0) {
                System.out.println("Produto sem estoque!");
                continue;
            }

            System.out.print("Quantidade desejada: ");
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
            System.out.println("Item adicionado ao carrinho!");
        }

        if (itensProduto.isEmpty()) {
            System.out.println("Nenhum item adicionado. Compra cancelada.");
            pausar();
            return;
        }

        // Exibir cupom de compra
        System.out.println("\n====================================");
        System.out.println("           CUPOM DE COMPRA          ");
        System.out.println("====================================");
        System.out.println("Cliente: " + nomeCliente);
        System.out.println("------------------------------------");
        System.out.printf("%-20s %4s %9s %10s%n", "Produto", "Qtd", "Unit.", "Subtotal");
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

        // Confirmar compra
        System.out.print("\nConfirmar compra? (S/N): ");
        String conf = sc.nextLine();
        if (conf.equalsIgnoreCase("S")) {
            // Reduzir estoque de cada item
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

    // Controle de estoque
    public static void controleEstoque(ArrayList<Produto> produtos) {
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
                CrudProdutos.listar(produtos);

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
                Produto p = CrudProdutos.encontrar(produtos, cod);
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
}
