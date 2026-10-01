import java.util.Locale;
import java.util.Scanner;

/**
 * Terminal Banking Application
 * Handles account creation input safely with proper exception handling, resource cleanup, and formatting.
 */
public class ContaTerminal {

    public static void main(String[] args) {
        // Fix: Use try-with-resources to automatically close Scanner and prevent resource leaks.
        // Fix: Set Locale to US to ensure consistent floating point parsing with decimal dot ('.').
        try (Scanner scanner = new Scanner(System.in).useLocale(Locale.US)) {

            // Input validation for Account Number
            System.out.println("Por favor, digite o número da conta:");
            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida! Digite um número inteiro para a conta:");
                scanner.next(); // Consume invalid token
            }
            int conta = scanner.nextInt();
            scanner.nextLine(); // Clear newline character from buffer

            // Input Agency
            System.out.println("Por favor, digite o número da Agência (ex: 067-8):");
            String agencia = scanner.nextLine().trim();

            // Input Customer Name (Fix: use nextLine to allow full names with spaces)
            System.out.println("Por favor, digite o seu Nome Completo:");
            String nome = scanner.nextLine().trim();

            // Input validation for Balance
            System.out.println("Por favor, digite o seu Saldo:");
            while (!scanner.hasNextDouble()) {
                System.out.println("Entrada inválida! Digite um valor numérico para o saldo (ex: 237.48):");
                scanner.next(); // Consume invalid token
            }
            double saldo = scanner.nextDouble();

            // Display success message
            System.out.println("Olá " + nome + ", obrigado por criar uma conta em nosso banco, sua agência é " + agencia
                    + ", conta " + conta + " e seu saldo " + String.format(Locale.US, "%.2f", saldo) + " já está disponível para saque.");

        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao processar os dados da conta: " + e.getMessage());
        }
    }
}
