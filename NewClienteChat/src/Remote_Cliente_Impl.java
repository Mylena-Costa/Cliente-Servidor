
import java.rmi.*;
import java.rmi.server.*;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Remote_Cliente_Impl extends UnicastRemoteObject implements Chat_Cliente_Interface {

	private javax.swing.JTextArea textArea;
	private String name;
	private int id,rodada,jogada;
	private int batata; 
	
    // *******************************************************************************************
    // Construtor da classe Remote_Cliente_Impl  que recebe uma area de texto para mostrar as mensagens
	// *******************************************************************************************
    public Remote_Cliente_Impl(javax.swing.JTextArea ta)
        throws RemoteException {
    	    textArea = ta;
    }
    
    
    // **************************************************************************************** 
    // Metodo para MOSTRAR na janela deste Cliente a mensagem -> (nome)juntou-se \n
    // ****************************************************************************************
     
    public synchronized void Passar(String name, int I) throws RemoteException {
    	 try {
    	setName(name);
    	this.batata = I ;
	    textArea.append("A batata está com o jogador " +name +" ( "+ I + " )"+ "\n");
	    
	    //JOptionPane.showMessageDialog(null, "A batata está com o jogador " +name +" ( "+ I + " )"+ "\n");
	    }
    	 catch(Exception e){System.out.println("DisplayMessage -> Falha no início do jogo.");
         e.printStackTrace();
     };
     
    }
    
    public synchronized void mensRodada (int I) throws RemoteException {
   	 try {
	    textArea.append("A rodada terá " +I +" rodadas \n");
	    this.rodada=I;
	    }
   	 catch(Exception e){System.out.println("DisplayMessage -> Falha no início do jogo.");
        e.printStackTrace();
        rodada=I;
    };
   }
    
    public synchronized int Rodada () throws RemoteException {
    	return rodada;
    }
    
    public synchronized int Jogadas () throws RemoteException {
    	jogada++;
    	return jogada;
    }
    
    
    public void Id (int I) throws RemoteException{
	    this.id=I;
	    if(I<3) {
    	    textArea.append("Aguardando quantidade mínima de jogadores... \n");
    	    }
    	    else 
    	    	 if(I==3) {
    	    	textArea.append("Começando jogo, aguarde... \n");
    	    	 } else textArea.append("Temos um novo jogador, realocando batata... \n");
    }
    
    public int Numero () throws RemoteException {
   	 return id;
   }
    
    public int Batata () throws RemoteException {
    	 return batata;
    	
    }
    
    
    public void JuntarMensagem(String name, int I)
        throws RemoteException
    {
        try {
        	// Metodo Antigo
    	    //textArea.append(name + " juntou-se \n");
    	    setName(name);
    	    textArea.append(name + " juntou-se na posicao I = " + I + " \n");
    	    //System.out.println("JuntarMensagem= "+ getName());
    	    }
        catch(Exception e){
            System.out.println("DisplayMessage -> Falha no envio da Mensagem");
            e.printStackTrace();
        };
    }
    // **************************************************************************************** 
    // Metodo para MOSTRAR na janela  deste Cliente a mensagem -> (nome)diz (menssagem) \n
    // ****************************************************************************************     
    public void EnviarMensagem(String name, String message) throws RemoteException
    {
        try {
    	    textArea.append(name + " diz : " + message + "\n");
    	}
        catch(Exception e){
            System.out.println("Remote_Cliente_Impl -> Falha no envio da Mesagem");
            e.printStackTrace();
        };
    }
    // **************************************************************************************** 
    // Metodo para MOSTRAR na janela  deste Cliente a mensagem -> (nome) deixou o chat \n
    // ****************************************************************************************        
    public void SairMensagem(String name) throws RemoteException {
        try {
    	    textArea.append(name + " deixou o chat.\n");
            System.out.println("Remote_Cliente_Impl -> O Cliente "+ name+" deixou o chat.\n");
    	}
        catch(Exception e){
            System.out.println("Remote_Cliente_Impl -> Falha no envio da Mesagem");
        };
    }
    // **************************************************************************************** 
    // Mï¿½todo para MOSTRAR na janela  deste Cliente a mensagem -> (nome)diz (menssagem) \n
    // ****************************************************************************************     
    public void MostrarMenuNoCliente(int menu, String esteName, String nameMensagem) throws RemoteException
    {
        try {
            if (!nameMensagem.isEmpty()){
            	textArea.append("Nome: "+ esteName + "  Mensagem: " + nameMensagem + "\n");
            }
        	switch(menu)
        	{
        	case 1:
        		textArea.append("Digite  1 - Para iniciar o jogo.\n");
        		textArea.append("            2 - Para sair do jogo.\n");
        		textArea.append("            3 - Para fazer jogada\n");
        		break;
           	case 2:
        		textArea.append("\n Inicio do jogo.\n");
        		textArea.append("Digite um numero de 0 a 10 ? : ");
        		break;	
           	case 3:
        		textArea.append("Voce acertou! Fim do jogo.\n");
        		break;
          	case 4:
        		textArea.append("Mais Auto.\n");
        		break;
         	case 5:
        		textArea.append("Mais Baixo.\n");
        		break;
        	default:
           		textArea.append("Mostrar Menu default ( " +  menu + " )  nameMensagem : " + nameMensagem + "\n");        		
        	}
    	}
        catch(Exception e){
            System.out.println("MostrarDisplayMessage -> Falha no envio da Mesagem");
            e.printStackTrace();
        };
    }
    
    
    //Note que estes metodo NAO sao chamados remotamente 
    public void setName(String name) {//throws RemoteException {
    	this.name = name;
    }
    
    public String getName() {
    	return name;
    }
}
