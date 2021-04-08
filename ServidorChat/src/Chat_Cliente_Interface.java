
import java.rmi.*;

public interface Chat_Cliente_Interface extends Remote {

	public void JuntarMensagem(String name,  int I) throws RemoteException;
	
    public void EnviarMensagem(String name, String message) throws RemoteException;
    
    public void SairMensagem(String name) throws RemoteException;
    
    public void MostrarMenuNoCliente(int menu, String esteName, String nameMensagem) throws RemoteException;

	public void Passar(String name, int I) throws RemoteException;
	
   public int Numero () throws RemoteException;
	
   public void Id (int I) throws RemoteException;
   
   public int Batata () throws RemoteException;
   
   public int Rodada () throws RemoteException;
   
   public void mensRodada (int I) throws RemoteException;
   
   public int Jogadas () throws RemoteException;
	
}
