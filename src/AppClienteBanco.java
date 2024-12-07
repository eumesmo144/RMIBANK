
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class AppClienteBanco {

    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.out.println("Por favor, forneça o IP do servidor. Exemplo: java AppClienteBanco 10.0.1.4");
                return;
            }

            String serverIp = args[0];
            Registry registry = LocateRegistry.getRegistry(serverIp, 1099);
            com.gugawag.rpc.banco.BancoServiceIF banco = (com.gugawag.rpc.banco.BancoServiceIF) registry.lookup("BancoService");

            menu();
            Scanner entrada = new Scanner(System.in);
            int opcao = entrada.nextInt();

            while (opcao != 9) {
                switch (opcao) {
                    case 1 -> {
                        System.out.println("Digite o número da conta:");
                        String conta = entrada.next();
                        System.out.println("Saldo: R$ " + banco.saldo(conta));
                    }
                    case 2 -> System.out.println("Quantidade de contas: " + banco.quantidadeContas());
                    case 3 -> {
                        System.out.println("Digite o número da nova conta:");
                        String numero = entrada.next();
                        System.out.println("Digite o saldo inicial:");
                        double saldo = entrada.nextDouble();
                        banco.adicionarConta(numero, saldo);
                    }
                    case 4 -> {
                        System.out.println("Digite o número da conta a pesquisar:");
                        String numero = entrada.next();
                        com.gugawag.rpc.banco.Conta conta = banco.consultaConta(numero);
                        System.out.println(conta != null ? conta : "Conta não encontrada.");
                    }
                    case 5 -> {
                        System.out.println("Digite o número da conta a remover:");
                        String numero = entrada.next();
                        if (banco.removeConta(numero)) {
                            System.out.println("Conta removida com sucesso!");
                        } else {
                            System.out.println("Conta não encontrada.");
                        }
                    }
                    default -> System.out.println("Opção inválida!");
                }
                menu();
                opcao = entrada.nextInt();
            }
            System.out.println("Encerrando...");
        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void menu() {
        System.out.println("\n=== BANCO RMI ===");
        System.out.println("1 - Saldo da conta");
        System.out.println("2 - Quantidade de contas");
        System.out.println("3 - Adicionar conta");
        System.out.println("4 - Pesquisar conta");
        System.out.println("5 - Remover conta");
        System.out.println("9 - Sair");
    }
}