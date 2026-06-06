import java.util.ArrayList;
import java.util.Scanner;

public class CrudClientes {

    static Scanner sc = new Scanner(System.in);

    // Menu de Clientes
    public static void menu(ArrayList<Cliente> clientes) {
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

            if (opcao == 1)      cadastrar(clientes);
            else if (opcao == 2) listar(clientes);
            else if (opcao == 3) buscar(clientes);
            else if (opcao == 4) alterar(clientes);
            else if (opcao == 5) remover(clientes);
            else if (opcao != 0) System.out.println("Opcao invalida!");

        } while (opcao != 0);
    }

    // CREATE - Cadastrar cliente
    static void cadastrar(ArrayList<Cliente> clientes) {
        System.out.println("\n====================================");
        System.out.println("        CADASTRAR CLIENTE           ");
        System.out.println("====================================");

        System.out.print("Digite o CPF: ");
        String cpf = sc.nextLine();

        if (encontrar(clientes, cpf) != null) {
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

    // READ - Listar clientes
    static void listar(ArrayList<Cliente> clientes) {
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

    // READ - Buscar cliente por CPF
    static void buscar(ArrayList<Cliente> clientes) {
        System.out.println("\n====================================");
        System.out.println("         BUSCAR CLIENTE             ");
        System.out.println("====================================");
        System.out.print("Digite o CPF: ");
        String cpf = sc.nextLine();

        Cliente c = encontrar(clientes, cpf);
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

    // UPDATE - Alterar cliente
    static void alterar(ArrayList<Cliente> clientes) {
        System.out.println("\n====================================");
        System.out.println("         ALTERAR CLIENTE            ");
        System.out.println("====================================");
        System.out.print("Digite o CPF: ");
        String cpf = sc.nextLine();

        Cliente c = encontrar(clientes, cpf);
        if (c == null) {
            System.out.println("Cliente nao encontrado.");
            pausar();
            return;
        }

        System.out.println("(Deixe em branco para manter o valor atual)");

        System.out.print("Novo Nome [" + c.nome + "]: ");
        String nome = sc.nextLine();
        if (!nome.isEmpty()) c.nome = nome;

        System.out.print("Novo Telefone [" + c.telefone + "]: ");
        String tel = sc.nextLine();
        if (!tel.isEmpty()) c.telefone = tel;

        System.out.print("Novo Email [" + c.email + "]: ");
        String email = sc.nextLine();
        if (!email.isEmpty()) c.email = email;

        Arquivos.salvarClientes(clientes);
        System.out.println("\nCliente alterado com sucesso!");
        pausar();
    }

    // DELETE - Remover cliente
    static void remover(ArrayList<Cliente> clientes) {
        System.out.println("\n====================================");
        System.out.println("         REMOVER CLIENTE            ");
        System.out.println("====================================");
        System.out.print("Digite o CPF: ");
        String cpf = sc.nextLine();

        Cliente c = encontrar(clientes, cpf);
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

    // Metodo auxiliar para encontrar cliente pelo CPF
    public static Cliente encontrar(ArrayList<Cliente> clientes, String cpf) {
        for (Cliente c : clientes) {
            if (c.cpf.equals(cpf)) return c;
        }
        return null;
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
