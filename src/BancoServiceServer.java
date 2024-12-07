
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BancoServiceServer extends UnicastRemoteObject implements com.gugawag.rpc.banco.BancoServiceIF {
    private List<com.gugawag.rpc.banco.Conta> contas;

    private Map<String, Double> saldoContas;

    public BancoServiceServer() throws RemoteException {
        saldoContas = new HashMap<>();
        saldoContas.put("1", 100.0);
        saldoContas.put("2", 156.0);
        saldoContas.put("3", 950.0);
    }

    @Override
    public double saldo(String numero) throws RemoteException {
        return contas.stream()
                .filter(conta -> conta.getNumero().equals(numero))
                .map(com.gugawag.rpc.banco.Conta::getSaldo)
                .findFirst()
                .orElse(0.0);
    }

    @Override
    public int quantidadeContas() throws RemoteException {
        return contas.size();
    }

    @Override
    public void adicionarConta(String numero, double saldo) throws RemoteException {
        if (contas.stream().noneMatch(conta -> conta.getNumero().equals(numero))) {
            contas.add(new com.gugawag.rpc.banco.Conta(numero, saldo));
            System.out.println("Conta " + numero + " adicionada com sucesso!");
        } else {
            System.out.println("Conta " + numero + " já existe.");
        }
    }

    @Override
    public com.gugawag.rpc.banco.Conta consultaConta(String numero) throws RemoteException {
        return contas.stream()
                .filter(conta -> conta.getNumero().equals(numero))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean removeConta(String numero) throws RemoteException {
        return contas.removeIf(conta -> conta.getNumero().equals(numero));
    }
}
