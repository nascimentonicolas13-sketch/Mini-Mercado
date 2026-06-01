import java.io.*;
import java.util.ArrayList;
import java.util.List;
 
public class ArquivoCSV {
 
    public static List<String> lerLinhas(String caminho) {
        List<String> linhas = new ArrayList<>();
        File arquivo = new File(caminho);
        if (!arquivo.exists()) return linhas;
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeiro = true;
            while ((linha = br.readLine()) != null) {
                if (primeiro) { primeiro = false; continue; }
                if (!linha.trim().isEmpty()) linhas.add(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler: " + caminho);
        }
        return linhas;
    }
 
    public static void escreverLinhas(String caminho, String cabecalho, List<String> linhas) {
        try {
            new File(caminho).getParentFile().mkdirs();
            try (PrintWriter pw = new PrintWriter(new FileWriter(caminho))) {
                pw.println(cabecalho);
                for (String l : linhas) pw.println(l);
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + caminho);
        }
    }
}