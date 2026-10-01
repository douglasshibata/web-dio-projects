import java.util.Scanner;

/**
 * Control Flow Console Application
 * Prompts user for two integer parameters and counts the sequential iterations.
 */
public class App {

    public static void main(String[] args) {
        // Fix: Use try-with-resources to automatically close Scanner and prevent resource leakage
        try (Scanner terminal = new Scanner(System.in)) {

            System.out.println("Digite o primeiro parâmetro:");
            while (!terminal.hasNextInt()) {
                System.out.println("Entrada inválida! Por favor, digite um número inteiro:");
                terminal.next();
            }
            int parametroUm = terminal.nextInt();

            System.out.println("Digite o segundo parâmetro:");
            while (!terminal.hasNextInt()) {
                System.out.println("Entrada inválida! Por favor, digite um número inteiro:");
                terminal.next();
            }
            int parametroDois = terminal.nextInt();

            try {
                // Call counting logic with provided parameters
                contar(parametroUm, parametroDois);
            } catch (ParametrosInvalidosException exception) {
                // Fix: Print clean error message instead of leaking stack traces via printStackTrace()
                System.out.println(exception.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Erro inesperado no sistema: " + e.getMessage());
        }
    }

    /**
     * Validates input parameters and prints sequential iteration numbers.
     *
     * @param parametroUm First parameter
     * @param parametroDois Second parameter
     * @throws ParametrosInvalidosException thrown if parametroUm is greater than or equal to parametroDois
     */
    static void contar(int parametroUm, int parametroDois) throws ParametrosInvalidosException {
        // Fix: Ensure parametroUm >= parametroDois triggers the exception (including equal values)
        if (parametroUm >= parametroDois) {
            throw new ParametrosInvalidosException("O segundo parâmetro deve ser maior que o primeiro");
        }

        int contagem = parametroDois - parametroUm;

        // Fix: Print sequential counting output (1 to contagem)
        for (int i = 1; i <= contagem; i++) {
            System.out.println("Imprimindo o número " + i);
        }
    }
}
