
import java.rmi.*;

//Interface do cliente para o servidor
//
//O cliente pode invocar estes métodos remotos no servidor:
//
public interface Chat_Servidor_Interface extends Remote {
    public void juntar(Chat_Cliente_Interface n, String name) throws RemoteException;
    
    public void conversar(String name, String s) throws RemoteException;
    
    public void deixar(Chat_Cliente_Interface n, String name) throws RemoteException;
    
    public void jogar() throws RemoteException;
    
    public void passarBatata(int I) throws RemoteException;
}
