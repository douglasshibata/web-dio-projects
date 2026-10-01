import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Unit Test suite for App.contar methodology.
 */
public class AppTest {

    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        PrintStream originalOut = System.out;
        System.out.println("Executing AppTest suite...");

        // Test 1: Valid parameter counting (parametroUm < parametroDois)
        try {
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            App.contar(12, 15); // should count 3 iterations: 15 - 12 = 3

            System.setOut(originalOut); // Restore stdout

            String output = outContent.toString();
            if (output.contains("Imprimindo o número 1") &&
                output.contains("Imprimindo o número 2") &&
                output.contains("Imprimindo o número 3")) {
                System.out.println("[PASS] Test 1: Valid range counting (12, 15)");
                passed++;
            } else {
                System.err.println("[FAIL] Test 1: Unexpected output: " + output);
                failed++;
            }
        } catch (Exception e) {
            System.setOut(originalOut);
            System.err.println("[FAIL] Test 1 threw unexpected exception: " + e.getMessage());
            failed++;
        }

        // Test 2: Invalid range (parametroUm > parametroDois)
        try {
            App.contar(30, 12);
            System.err.println("[FAIL] Test 2: Expected ParametrosInvalidosException was not thrown.");
            failed++;
        } catch (ParametrosInvalidosException e) {
            if ("O segundo parâmetro deve ser maior que o primeiro".equals(e.getMessage())) {
                System.out.println("[PASS] Test 2: Exception thrown when parametroUm > parametroDois");
                passed++;
            } else {
                System.err.println("[FAIL] Test 2: Incorrect exception message: " + e.getMessage());
                failed++;
            }
        } catch (Exception e) {
            System.err.println("[FAIL] Test 2: Unexpected exception type: " + e);
            failed++;
        }

        // Test 3: Equal parameters (parametroUm == parametroDois)
        try {
            App.contar(10, 10);
            System.err.println("[FAIL] Test 3: Expected ParametrosInvalidosException was not thrown for equal parameters.");
            failed++;
        } catch (ParametrosInvalidosException e) {
            if ("O segundo parâmetro deve ser maior que o primeiro".equals(e.getMessage())) {
                System.out.println("[PASS] Test 3: Exception thrown when parametroUm == parametroDois");
                passed++;
            } else {
                System.err.println("[FAIL] Test 3: Incorrect exception message: " + e.getMessage());
                failed++;
            }
        } catch (Exception e) {
            System.err.println("[FAIL] Test 3: Unexpected exception type: " + e);
            failed++;
        }

        System.out.println("AppTest results: " + passed + " passed, " + failed + " failed.");
        if (failed > 0) {
            System.exit(1);
        }
    }
}
