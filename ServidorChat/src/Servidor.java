
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;
import java.lang.*;
import java.util.*;

//*************************************************************************    
//
// O CLIENTE pode invocar ESTES metodos remotos no SERVIDOR:
//
//  	Codigo implementado na classe Servidor.java
//
//		Interface:  ChatInterface.java
//
//  1 - Juntar(Notificar n, String name) - Juntar um cliente ao chat 
//  2 - Conversar(String name, String s) - Conversar com os outros clientes do chat
//  3 - Deixar(Notificar n, String name) - Deixar o chat
//
//
// *************************************************************************    
// *************************************************************************    
//
//  O SERVIDOR pode invocar esses metodos remotos no CLIENTE:
//
//  	Codigo implementado na classe Remote_Cliente_Impl.java do Cliente
//
//		Interface :  Notificar.java
//
//  1 - JuntarMensagem() - Mostrar Mensagem enviadas pelo Servidor ( nome juntou se ) no cliente do chat 
//  2 - EnviarMensagem() - Mostrar Mensagem enviadas pelo Servidor ( nome diz xxx ) no cliente do chat 
//  3 - SairMensagem()   - Mostrar Mensagem enviadas pelo Servidor ( O Cliente "+ name+" deixou o chat.)
//      no cliente do chat 
//	4 - MostrarMenuNoCliente() - Mostrar Mensagem enviadas pelo Servidor ( varias ) no cliente do chat 
// 
//*************************************************************************    
//
public class Servidor extends UnicastRemoteObject implements Chat_Servidor_Interface {
    
	//  Servidor_Thread()
	//
	// Cria uma Inst�ncia da classe Server_Thread para controlar a comunica��o com cada cliente.
	// Quando um cliente junta-se ao Chat (faz login) � criada uma nova thread para controlar a conex�o 
	// deste novo cliente com o servidor. Usa os seguintes m�todos:
	// add(n) - Adiciona uma nova thread ao aplicativo.
	// remove(n) - Remove uma thread do aplicativo.
	// n  - � um inteiro, declarado na classe Servidor_Thread() e usado como �ndice de controle e contador 
	//      da cole��o (classe Collection() ) de threads do aplicativo.
	//     
	// Classe Collection() usada para criar uma cole��o de dados, isto �, um tipo semelhante a um  Vetor que
	// armazena os dados da conex�o de cada cliente. Usa os seguintes m�todos:
	// iterator()   - Indice de controle da posi��o de uma cole��o. Equivalente ao �ndice I do vetor[I]
	// incCounter() - Incrementa o contador da posi��o da cole��o de dados ( incrementa a posi��o do vetor )
	// decCounter() - Decrementa o contador da posi��o da cole��o de dados ( decrementa a posi��o do vetor )
	//
    private Servidor_Thread serverList = new Servidor_Thread();
    
    // Cria uma Inst�ncia da classe Random() para gerar n�meros randomicos
    public Random myRandom = new Random();
    
    // Declara��o das vari�veis utilizadas na classe Servidor()
    //
    public int NumJogadorCont = 0;
    public int NumJogadorConf = 0;  		// N�mero de jogadores - Se zero jogo parado
    public int Num_X,rodada ;
    public Chat_Cliente_Interface[] Jogador = new Chat_Cliente_Interface[10];
    public boolean Acertou = false;
    public boolean IniciaJogo = false;
    public String NomeJogadorVet[] = new String[ 10 ]; 	// Declara e cria o espa�o para o array
    public int 	  NumJogadorVet[] = new int[ 10 ]; 		// Declara e cria o espa�o para o array
    public boolean iniciar = false;
    
    
    // *************************************************************************    
    // Construtor padr�o da classe Servidor()
    // *************************************************************************
    
    Chat_Cliente_Interface cliente;		    
    Chat_Servidor_Interface gameServer; 
    
    
    
    public Servidor() throws RemoteException {
    	// Local ideal para inicia as vari�veis da classe Servidor() quando este � inst�nciado
    	//
    	NumJogadorVet[0] =  0 ;
    	NomeJogadorVet[0] = "";
    }
    
    // ****************************************************
    // My - Descreve o procedimento do m�todo NumAleatorio 
    // ****************************************************
    	
    // ****************************************************
    // My - Descreve o procedimento do m�todo MostrarMenuNoCliente
    // ****************************************************   
    
    public synchronized void jogar() throws RemoteException {
    	NumJogadorConf++;
    	System.out.println (NumJogadorConf+"/n"+NumJogadorCont);
    	if((NumJogadorConf==NumJogadorCont) && NumJogadorCont>=3) {
    		int m = NumJogadorCont;
    	  	Num_X = myRandom.nextInt(m) + 1; 
    	  	rodada = myRandom.nextInt(10)+ 1;
    	  	System.out.println ("A batata est� com o jogador " +NomeJogadorVet[Num_X]+" ("+ Num_X + ")"+ "\n");
    	  	for (Iterator i = serverList.getCollection().iterator();i.hasNext();) {
   			Chat_Cliente_Interface client = (Chat_Cliente_Interface)i.next();
   			client.Passar(NomeJogadorVet[Num_X],Num_X);	
   			client.mensRodada(rodada);
   				//if (i==n) {
   				//client.JuntarMensagem(name, NumJogadorCont );
   				//}
   			}
    	}
    	  	
    	}
    
    public synchronized void passarBatata( int I ) throws RemoteException {
    		Num_X = I;
    		for (Iterator i = serverList.getCollection().iterator();i.hasNext();) {
       			Chat_Cliente_Interface client = (Chat_Cliente_Interface)i.next();
       			client.Passar(NomeJogadorVet[Num_X],Num_X);	
   			
    		}
    	}
    
    public void MostrarMenuCliente()throws RemoteException {
    	
    }
    
    // ****************************************************
    // Descreve o procedimento do m�todo Juntar 
    // ****************************************************
			
      
    
    public void juntar(Chat_Cliente_Interface n, String name) throws RemoteException {
    	
		// My - Incrementa o contador de jogadores -> NumJogador
		
    	NumJogadorCont++; // Contar num de jogadores
    	
       	NumJogadorVet[NumJogadorCont] = NumJogadorCont;  	
    	NomeJogadorVet[NumJogadorCont] = name;
		
		serverList.add(n);	// Valor de n :  
		// proxy[ Notificar, RemoteObj...]	
		// ... iveRef: [endpoint:[192.168.1.2:49313](remote),objID:[180bbf07:1304945573e:-7ffe, -2794881097847212742]]]]]
		// Mostra no servidor name e URL - s� para ver a diferen�a
		System.out.println ("O Cliente "+ name+ " juntou-se em \nURL : "+n);
		serverList.incCounter();
		
		
		
		
		// Informar aos outros clientes que um novo usu�rio esta conectado
		//    Enviando mensagem O cliente ( nome xxx ) juntou-se na posi��o I = (n�mero).
		
		for (Iterator i = serverList.getCollection().iterator();
				 i.hasNext();) {
			Chat_Cliente_Interface client = (Chat_Cliente_Interface)i.next();
			client.JuntarMensagem(name, NumJogadorCont );
			
		}
		
		serverList.decCounter();
		

    }
    
    public void cliente() throws RemoteException {
    	for (Iterator i = serverList.getCollection().iterator();
				 i.hasNext();) {
			Chat_Cliente_Interface client = (Chat_Cliente_Interface)i.next();
			client.Id(NumJogadorCont);
    	}
    }

    // *****************************************************
    // Descreve o procedimento do m�todo conversar
    // *****************************************************
    public void conversar(String name, String s)
            throws RemoteException {
		serverList.incCounter();
		for (Iterator i = serverList.getCollection().iterator();
				 i.hasNext();) {
			Chat_Cliente_Interface client = (Chat_Cliente_Interface)i.next();
			client.EnviarMensagem(name,s);
		}
		serverList.decCounter();
		/*
		if(Integer.parseInt(s) == 99)
		{	
			serverList.incCounter();
			for (Iterator i = serverList.getCollection().iterator();
					 i.hasNext();) {
				Chat_Cliente_Interface client = (Chat_Cliente_Interface)i.next();
				client.EnviarMensagem(name,"e' nove nove");
			    client.MostrarMenuNoCliente(1, name, s);
			}
			serverList.decCounter();
		}
		*/
    }
    // ******************************************************    
    // Descreve o procedimento do m�todo deixar
    // ******************************************************
    public synchronized void deixar(Chat_Cliente_Interface n, String name) throws RemoteException {
    	serverList.remove(n);
    	
    	serverList.incCounter();
    	for (Iterator i = serverList.getCollection().iterator();
				 i.hasNext();) {
			Chat_Cliente_Interface client = (Chat_Cliente_Interface)i.next();
			client.SairMensagem(name);
		}
		serverList.decCounter();
		System.out.println ("O Jogador  "+ name+"  Deixou o Chat\n");
		for(int i=0;i<=NumJogadorCont;i++) {
			System.out.println ("O Jogador  "+ NomeJogadorVet[i]+"  � o n�"+NumJogadorVet[i]+"\n");	
		}
		
		// My - Decrementa numetro de Jogadores
		NumJogadorCont --;
    }
    // *************************************************************************
    //   main()
    // *************************************************************************
    
    
    public static void main(String[] args) {
         	try {
         	// Porta 1099 � a porta default do rmiregistry 
            LocateRegistry.createRegistry(1099);
            // Cria uma inst�ncia da classe Servidor() chamada de server  
			Servidor server = new Servidor();
            
			// Implementa o Servidor �de nome server com o RMI rebind
			Naming.rebind("rmichat", (Remote) server);
			System.out.println ("Servidor Pronto!");
		}
		catch (java.net.MalformedURLException e) {
			System.out.println("nome da URL mal formado para Servidor de Mensagem "
			    + e.toString());
		}
		catch (RemoteException e) {
			System.out.println("Erro de comunicação" + e.toString());
		}
    }
    
}
