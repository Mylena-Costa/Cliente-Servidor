import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JPanel;
import javax.swing.JLabel;

import javax.swing.JOptionPane;
import java.util.Scanner;


// Importando classes RMI
import java.rmi.*;
import java.rmi.server.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;

public class NewClienteChat {
// ****************************************************************************************	
//                Descricao resumida das FASES da aplicacao NewClienteChat
//
// Primeira fase - dentro do metodo main()       -> Criar thread para executar a aplicacao
// Segunda  fase - dentro do metodo main()       -> Instanciar e criar a janela da aplicacao
// Terceira fase - dentro do metodo initialize() -> Obter um socket Cliente RMI -> invocacando  
//                                                  o metodo lookup("rmichat") da classe Naming RMI
// Quarta   fase - dentro do metodo initialize() -> Incluir os métodos OUVINTE para a janela ou  
//													componentes especificos (botao, mouse teclado)
//													Teremos vários ouvintes, um para cada evento que 
// 													sera incluido proximo ao componente
// Quinta   fase - iniciar a classe da sua aplicacao
// **************************************************************************************** 

	// DECLARACAO DOS CAMPOS DA CLASSE NewClienteChat
	//
	//       Campos usados para criar a JANELA da aplicacao
	//
	private JFrame frmNewClienteChat;
	private JTextArea otherText; //= new JTextArea();
	private JTextArea myText ;
	private JButton btnLogin;
	private JButton btnMeuBotao_1; 
	private JButton direita;
	private JButton esquerda; 
	private JButton teste;
	private int id;
	private int batata = 0;
	private int novaBatata;
	private int rodada;
	private int jogada;
	
	//       Campos usados para a aplicacao funcionar
	//
    private String textString = "";
    private boolean primeiraMensagem = true;  // Usada para processar a PRIMEIRA MENSAGEM com o servidor
    private static String name = null;    // se name = null o cliente não está associado
    private boolean conectado = false;    // Sentinela de conexao do cliente com o servidor
    private boolean troca = true;  // Sentinela de conexao do cliente com o servidor
    
    Chat_Cliente_Interface displayChat;		    // Instancia da interface Cliente  - servidor  (invoca metodos no) -> cliente 
    Chat_Servidor_Interface chatServer;         // Instancia da interface Servidor - cliente   (invoca metodos no) -> servidor     


    // *************************************************************************
    // Metodo Construtor da classe NewClienteChat()
    // *************************************************************************
	public NewClienteChat() {
		// Invoca o método initialize() para iniciar os componentes da classe NewClienteChat
		initialize();

	} // Fim do metodo construtor  da classe NewClienteChat()

	// ************************************************************************
	// Metodo para iniciar os componentes da classe (aplicacao NewClienteChat)
	// isto e' criar a janela, caixa de texto, botao ...
    // *************************************************************************	
	private void initialize() {
		frmNewClienteChat = new JFrame();
        // ****************************************************************************************
    	// Quarta fase: Invoca METODO OUVINTE de evento   OBS.: Teremos VARIOS OUVINTES um p cada evevto
		// 4º - A
		// Evento de FECHAR a aplicacao -> windowClosing(WindowEvent arg0) 
		//
    	// Metodo para adiciona um OUVINTE para a janela ou componente especifico da aplicacao.
        // O ouvinte receber os eventos a partir desta janela. Se for nulo, nenhuma exceção é lançada  
        // e nenhuma ação é executada.
		//
        // WindowAdapter() -> Classe abstrada do adaptador para receber os eventos da janela
        // windowClosing(WindowEvent e) -> Método invocado quando a janela está no processo de fechar 
        // ****************************************************************************************           	   			
		frmNewClienteChat.addWindowListener(new WindowAdapter() {
						
			@Override
			public void windowClosing(WindowEvent arg0) {
				try {
					if (name != null) {
					    chatServer.deixar(displayChat, name);
					    conectado = false;
					}
			}
			catch (Exception ex) {
				 otherText.append("Sair falhou.");
				 conectado = false;
			}
			System.exit(0);
				
				
			}
		});		
		
		frmNewClienteChat.setTitle("Jogo da Batata Quente");
		frmNewClienteChat.setBounds(350, 100, 663, 547);
		frmNewClienteChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ****************************************************************************************		
		// Trata os eventos do botao Login
		//
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				  
				if (primeiraMensagem) { 
				
	        	   try {
	        		   // Se for a PRIMEIRA MENSAGEM enviada do cliente para o servidor
	        		   // será armazenado este texto em nome do cliente e o cliente será 
	        		   // associado ao chat. e a váriavel firstMessage é colocada com falso  
	        		  
	        		       name = JOptionPane.showInputDialog("Digite seu nome: ");
	        		       chatServer.juntar(displayChat,name);
	        		       id=displayChat.Numero();
	        		       batata=displayChat.Batata();
	        		       myText.append("Seu número é "+id+'\n');
	        			   primeiraMensagem = false;
	        			   conectado = true; 
	        			   troca = true;
						   btnMeuBotao_1.setVisible(true);
						   teste.setVisible(true);
	        	   }
	        	   catch (Exception ie) {
	        		   otherText.append("Falha no envio de mensagem.");
	        	   }
	        	   myText.setEditable(true);
	        	   myText.append("Digite sua mensagem:\n");
				} // Fim do if (firstMessage)
			}
		});
        // ****************************************************************************************		
		// Trata os eventos do botao MeuBotao_1
		//
		btnMeuBotao_1 = new JButton("Instruções");
		btnMeuBotao_1.setVisible(false);
		btnMeuBotao_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//
				JOptionPane.showMessageDialog(null,"O jogo escolherá um jogador aleatório para estar com a  batata.\nUtilize os botões 'Esquerda' e 'Direita' para passar a batata para outro jogador.\nQuando o tempo acabar, a pessoa que estiver com a batata será eliminada do jogo. \n\n O jogo só irá iniciar quando todos os jogadores clicarem em  'Iniciar'.\n\n* Utilize 'ENTER' para enviar mensagens");
		}});
		

		direita = new JButton("Direita");
		direita.setVisible(false);
		direita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Codigo do evento Meu Botao
				try {
					chatServer.passarBatata(id+1);
					batata++;
					esquerda.setVisible(false);
					direita.setVisible(false);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		esquerda = new JButton("Esquerda");
		esquerda.setVisible(false);
		esquerda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Codigo do evento Meu Botao
				try {
					chatServer.passarBatata(id-1);
					batata--;
					esquerda.setVisible(false);
					direita.setVisible(false);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		teste = new JButton("Iniciar");
		teste.setVisible(false);
		teste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Codigo do evento Meu Botao
				try {
					chatServer.jogar();

					teste.setVisible(false);
					
					do {
						
						try {
							batata=displayChat.Batata();
							System.out.println(batata);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}while((batata==0));
					
					rodada=displayChat.Rodada();
					while(rodada<=jogada) {
					novaBatata=batata;
					if((id==novaBatata) && (batata!=0)) {
						esquerda.setVisible(true);
						direita.setVisible(true);
						displayChat.Jogadas();
					}
						while(novaBatata!=batata) {
							try {
								batata=displayChat.Batata();
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	
						}
					}	
						myText.append("nº da batata: "+batata);
					if(batata==0) {
					myText.append("Você iniciou o jogo.\nAguardando outros jogadores iniciarem ...\n");
					}else
						{
						myText.append("Entrando no jogo ...\n");	
						}
					teste.setVisible(false);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel panel = new JPanel();
		
		GroupLayout groupLayout = new GroupLayout(frmNewClienteChat.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED))
						.addComponent(esquerda)
						.addComponent(teste)
						.addComponent(btnLogin))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(direita)
						.addComponent(btnMeuBotao_1)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)))
					.addContainerGap())
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.CENTER)	
							.addComponent(esquerda)
							.addComponent(direita))
					        .addComponent(teste)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)	
						.addComponent(btnLogin)
						.addComponent(btnMeuBotao_1))
					.addContainerGap())
		);
		
		JLabel mensagem = new JLabel("Inclua sua aplica\u00E7\u00E3o neste componente");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.CENTER)
				.addGroup(Alignment.CENTER, gl_panel.createSequentialGroup()
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(mensagem, GroupLayout.PREFERRED_SIZE, 178, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.CENTER)
				.addGroup(Alignment.CENTER, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(mensagem, GroupLayout.PREFERRED_SIZE, 9, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		
		// Alterar o local da declaração original do windowBuilder para que o escopo do objeto otherText
		// seja visivel em toda classe NewClienteChat
		//final JTextArea otherText = new JTextArea(); 
		
		otherText = new JTextArea();
		otherText.setEditable(false);
		scrollPane_1.setViewportView(otherText);
		
		JLabel lblReceber_1 = new JLabel("Receber");
		scrollPane_1.setColumnHeaderView(lblReceber_1);
		
		// Alterar o local da declaração original do windowBuilder para que o escopo do objeto otherText
		// seja visivel em toda classe NewClienteChat 
		// JTextArea myText = new JTextArea();
		myText = new JTextArea();
		//myText.setFocusCycleRoot(true);
		myText.setEditable(false);
   	    // *********************************************************************************
   	   	// Método de myText.addKeyListener() 
   	    //      Adiciona um OUVINTE para a janela especificada, para receber os eventos a partir
        //  desta janela. 
   	    //  Usa o método    textTyped(evt) -> para processar os caracteresdigitados 
   	    //
  	    // *********************************************************************************
		myText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				
				textTyped(arg0);
			}
		});
		scrollPane.setViewportView(myText);
		//myText.append("Digite seu nome:\n");
		
		JLabel lblTransmitir_1 = new JLabel("Transmitir");
		scrollPane.setColumnHeaderView(lblTransmitir_1);
		frmNewClienteChat.getContentPane().setLayout(groupLayout);
		
		

// ****************************************************************************************
// Terceira fase: 
// Obter um socket Cliente RMI, invocacando o método lookup("rmichat") da classe Naming RMI
// ****************************************************************************************        		
        try {
            Remote remoteObject = Naming.lookup("rmichat");
            
            // Se interface esta OK
			if (remoteObject instanceof Chat_Servidor_Interface) {
				// Obtem interface remota
				chatServer = (Chat_Servidor_Interface)remoteObject ;
				
				// Instânciando a classe DisplayMessage() p/ q o ClienteCht possa usar 
				displayChat = new Remote_Cliente_Impl(otherText);
				conectado = true;
			} else {
				System.out.println("O Servidor nao e´ um Servidor de Chat");
				System.exit(0);
	            conectado = false;
			}
        }
        catch(Exception e){
            System.out.println("Pesquisando exceção de RMI - Lookup Exception");
            System.exit(0);
            conectado = false;
        };// Fim do try Remote remoteObject
			

        
        
	}// Fim do Método initialize()
	
    // *************************************************************************
    // Método para processar os textos digitados pelo cliente 
    // *************************************************************************    
    private void textTyped(java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if (c == '\n'){
        	   try {
        		   // Se for a PRIMEIRA MENSAGEM enviada do cliente para o servidor
        		   // será armazenado este texto em nome do cliente e o cliente será 
        		   // associado ao chat. e a váriavel firstMessage é colocada com falso  
        		   if (primeiraMensagem) { 
        		   	   name = textString;
        		       chatServer.juntar(displayChat,name);
        			   primeiraMensagem = false;
        			   conectado = true;
        			   //jButton1.setVisible(true);  
        		   } else {
        			   // *******************************************************************
        			   // Obs:  1
        			   // Todas as OUTRAS mensagens sÃ£o enviadas para o servidor por este 
        			   // mÃ©todo e CERTAS AÃ‡Ã•ES podem ser tratadas aqui ou no mÃ©todo conversar().
        			   //        		       												
        			   //
        			   // *******************************************************************
        			   
        			   chatServer.conversar(name, textString);
        		   }
        	   }
        	   catch (Exception ie) {
        		   otherText.append("Falha no envio de mensagem.");
        	   }
            textString = "";
        } else {
            textString = textString + c;
        }
    } // Fim do método textTyped	
	
// *************************************************************************   
// Metodo main() - Metodo  que executa a classe  NewClienteChat()
// *************************************************************************
	public static void main(String[] args) {

        // ****************************************************************************************
    	// Primeira fase: 
    	// Invoca o METODO invokeLater da classe Thread para obter uma thread para executar
    	// a classe NewClienteChat (a aplicacao -> criar a janela, caixa de texto, botao ...) como 
        // ****************************************************************************************		
		//
		// 1º A - Cria uma thread para controlar a execucao da aplicacao
		
		EventQueue.invokeLater(new Runnable() {
			
		// 1º B - Faz a thread executar ( metodo-> public void run() ) e ...
		// *****************************************************************************
			public void run() {
				try {
					// *****************************************************************************
			    	// Segunda fase: Descricao do comando-> NewClienteChat window = new NewClienteChat();
					//
					// 2º A - Cria uma instancia da classe NewClienteChat (comando-> NewClienteChat window)
					// 
					// 2º B - INVOCA o metodo construtor da classe NewClienteChat() usando o 
					//  operador "new" ( comando ->   new NewClienteChat();)
					//
					// 2º C - O metodo construtor invoca o metodo initialize() para INICIAR os 
					// componentes da aplicacao isto e', criar a janela com seus componentes 
					// tais como: caixa de textos, botao, menu, barras etc....
					// *****************************************************************************
					NewClienteChat window = new NewClienteChat();
					window.frmNewClienteChat.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});// fim do metodo invokeLater()
	}// Fim do main()
	
	
} // Fim da classe NewClienteChat
