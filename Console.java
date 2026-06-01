import java.util.Scanner;
 
public class Console {
    private static final Scanner scanner = new Scanner(System.in);
 
    public static void cabecalho(String titulo) {
        System.out.println();
        System.out.println("====================================");
        int esp = (36 - titulo.length()) / 2;
        
        // Correção para funcionar no Java 8+ sem o .repeat()
        if (esp > 0) {
            System.out.printf("%" + esp + "s%s%n", "", titulo);
        } else {
            System.out.println(titulo);
        }
        
        System.out.println("====================================");
        System.out.println();
    }
 
    public static void linha() {
        System.out.println("------------------------------------");
    }
 
    public static void pausar() {
        System.out.print("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }
 
    public static String lerString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
 
    public static int lerInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Digite um numero inteiro valido.");
            }
        }
    }
 
    public static double lerDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("  [!] Digite um numero decimal valido (ex: 5.90).");
            }
        }
    }
 
    public static void sucesso(String msg) { System.out.println("\n  [OK] " + msg); }
    public static void erro(String msg)    { System.out.println("\n  [ERRO] " + msg); }
    public static void info(String msg)    { System.out.println("  [i] " + msg); }
}