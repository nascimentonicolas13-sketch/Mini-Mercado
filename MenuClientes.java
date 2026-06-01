import java.util.Arrays;
 
public class MenuClientes {
    private ClienteService clienteService;
 
    public MenuClientes(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
 
    public void exibir() {
        boolean voltar = false;
        while (!voltar) {
            Console.cabecalho("MENU DE CLIENTES");
            System.out.println("  1. Cadastrar Cliente");
            System.out.println("  2. Listar Clientes");
            System.out.println("  3. Buscar Cliente");
            System.out.println("  4. Alterar Cliente");
            System.out.println("  5. Remover Cliente");
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
        Console.cabecalho("CADASTRAR CLIENTE");
        String cpf = Console.lerString("Digite o CPF: ");
        if (cpf.isEmpty()) { Console.erro("CPF nao pode ser vazio."); Console.pausar(); return; }
        if (clienteService.buscarPorCpf(cpf) != null) { Console.erro("CPF ja cadastrado."); Console.pausar(); return; }
        String nome = Console.lerString("Digite o Nome: ");
        if (nome.isEmpty()) { Console.erro("Nome nao pode ser vazio."); Console.pausar(); return; }
        String tel   = Console.lerString("Digite o Telefone: ");
        String email = Console.lerString("Digite o Email: ");
        if (clienteService.cadastrar(new Cliente(cpf, nome, tel, email)))
            Console.sucesso("Cliente cadastrado com sucesso!");
        else Console.erro("Erro ao cadastrar cliente.");
        Console.pausar();
    }
 
    private void listar() {
        Console.cabecalho("LISTA DE CLIENTES");
        clienteService.imprimirTabela(clienteService.listar());
        Console.pausar();
    }
 
    private void buscar() {
        Console.cabecalho("BUSCAR CLIENTE");
        System.out.println("  1. Por CPF\n  2. Por Nome");
        Cliente c = null;
        String op = Console.lerString("  Opcao: ");
        if (op.equals("1")) c = clienteService.buscarPorCpf(Console.lerString("CPF: "));
        else if (op.equals("2")) c = clienteService.buscarPorNome(Console.lerString("Nome: "));
        else { Console.erro("Invalido."); Console.pausar(); return; }
        if (c != null) clienteService.imprimirTabela(Arrays.asList(c));
        else Console.erro("Produto nao encontrado.");
        Console.pausar();
    }
 
    private void alterar() {
        Console.cabecalho("ALTERAR CLIENTE");
        String cpf = Console.lerString("CPF do Cliente: ");
        Cliente c = clienteService.buscarPorCpf(cpf);
        if (c == null) { Console.erro("Cliente nao encontrado."); Console.pausar(); return; }
        clienteService.imprimirTabela(Arrays.asList(c));
        System.out.println("  (Deixe em branco para manter)");
        String novoNome = Console.lerString("Novo Nome [" + c.getNome() + "]: ");
        String novoTel  = Console.lerString("Novo Telefone [" + c.getTelefone() + "]: ");
        String novoEmail= Console.lerString("Novo Email [" + c.getEmail() + "]: ");
        if (clienteService.alterar(cpf, novoNome, novoTel, novoEmail))
            Console.sucesso("Cliente alterado com sucesso!");
        else Console.erro("Erro ao alterar.");
        Console.pausar();
    }
 
    private void remover() {
        Console.cabecalho("REMOVER CLIENTE");
        String cpf = Console.lerString("CPF do Cliente: ");
        Cliente c = clienteService.buscarPorCpf(cpf);
        if (c == null) { Console.erro("Cliente nao encontrado."); Console.pausar(); return; }
        clienteService.imprimirTabela(Arrays.asList(c));
        if (Console.lerString("\n  Confirmar remocao? (S/N): ").equalsIgnoreCase("S")) {
            clienteService.remover(cpf);
            Console.sucesso("Cliente removido com sucesso!");
        } else Console.info("Operacao cancelada.");
        Console.pausar();
    }
}
