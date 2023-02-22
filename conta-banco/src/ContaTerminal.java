import java.util.Scanner;

public class ContaTerminal {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Por favor, digite o número da conta");
        Integer conta = scanner.nextInt();
        System.out.println("Por favor, digite a número Agência");
        String agencia = scanner.next();
        System.out.println("Por favor, digite o seu Nome");
        String nome = scanner.next();
        System.out.println("Por favor, digite o seu Saldo");
        Double saldo = scanner.nextDouble();
        System.out.println("Olá " + nome + ", obrigado por criar uma conta em nosso banco, sua agência é " + agencia
                + ", conta " + conta + " e seu saldo " + saldo + " já está disponível para saque");

    }
}
