import java.io.*;
import java.util.ArrayList;

public class Arquivos {

    public static void salvarProdutos(ArrayList<Produto> lista) {
        try {
            new File("data").mkdirs();
            PrintWriter pw = new PrintWriter(new FileWriter("data/produtos.csv"));
            pw.println("codigo;nome;preco;estoque");
            for (Produto p : lista) {
                pw.println(p.codigo + ";" + p.nome + ";" + p.preco + ";" + p.estoque);
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar produtos.");
        }
    }

    public static ArrayList<Produto> carregarProdutos() {
        ArrayList<Produto> lista = new ArrayList<>();
        File arquivo = new File("data/produtos.csv");
        if (!arquivo.exists()) return lista;
        try {
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            String linha;
            boolean primeiro = true;
            while ((linha = br.readLine()) != null) {
                if (primeiro) { primeiro = false; continue; }
                if (linha.trim().isEmpty()) continue;
                String[] p = linha.split(";");
                int codigo   = Integer.parseInt(p[0].trim());
                String nome  = p[1].trim();
                double preco = Double.parseDouble(p[2].trim());
                int estoque  = Integer.parseInt(p[3].trim());
                lista.add(new Produto(codigo, nome, preco, estoque));
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar produtos.");
        }
        return lista;
    }

    public static void salvarClientes(ArrayList<Cliente> lista) {
        try {
            new File("data").mkdirs();
            PrintWriter pw = new PrintWriter(new FileWriter("data/clientes.csv"));
            pw.println("cpf;nome;telefone;email");
            for (Cliente c : lista) {
                pw.println(c.cpf + ";" + c.nome + ";" + c.telefone + ";" + c.email);
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar clientes.");
        }
    }

    public static ArrayList<Cliente> carregarClientes() {
        ArrayList<Cliente> lista = new ArrayList<>();
        File arquivo = new File("data/clientes.csv");
        if (!arquivo.exists()) return lista;
        try {
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            String linha;
            boolean primeiro = true;
            while ((linha = br.readLine()) != null) {
                if (primeiro) { primeiro = false; continue; }
                if (linha.trim().isEmpty()) continue;
                String[] p = linha.split(";");
                lista.add(new Cliente(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim()));
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar clientes.");
        }
        return lista;
    }
}