import java.lang.*;
import java.util.*;

class Servidor_Thread {
	
	private Collection<Chat_Cliente_Interface> threadList =
	            new ArrayList<Chat_Cliente_Interface>();
	private int counter = 0;
	
    // Obter o bloqueio no theadList, e aguardar at� que o contador seja zero - isto �,
	// n�o l� (o que estava ocorrendo). Ent�o, 
	// quando for zero, � seguro para adicionar a lista de discuss�o.
	public synchronized void add(Chat_Cliente_Interface item) {
		try {
			while (counter > 0) {
				wait();
			}
			threadList.add(item);
			System.out.println(item);
	    }
	    catch (InterruptedException e) {
	    	System.out.println("Addition interrupted.");
	    }
	    finally{
	        notifyAll();
	    }
	}
	
	
	
	// Do mesmo modo para remover
	public synchronized void remove(Chat_Cliente_Interface item) {
	    try {
			while (counter > 0) {
				wait();
			}
			threadList.remove(item);
		}
	    catch (InterruptedException e) {
	    	System.out.println("Removal interrupted.");
	    }
	    finally {
	        notifyAll();
	    }
	}
	
	// Do mesmo modo para modificar o contador 
	public synchronized void incCounter() {
		counter++;
		notifyAll();
	}
	
	public synchronized void decCounter() {
		counter--;
		notifyAll();
	}
	
	//  Isso ocorre porque seria muito esfor�o para fazer esta classe implementar
	//  uma Cole��o (Collection), devolv�-lo da pr�pria Iterator etc .. etc \
	//  Note que * n�o * � um erro que n�o est� sincronizado
	public Collection getCollection() {
		return threadList;
	}
}
