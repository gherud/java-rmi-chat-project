package java.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	Registry reg; //rejestr nazw obiektów
	Servant servant; //klasa us³ugowa

	public static void main(String[] args) {
		try{
			new Server();
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	protected Server() throws RemoteException{
		try{
			reg = LocateRegistry.createRegistry(1099); //utworzenie rejestru nazw
			servant = new Servant();				//utworzenie zdalnego obiektu
			reg.rebind("Server", (Remote) servant); //zwiazanie nazwy z obiektem
		}
		catch(RemoteException e){
			e.printStackTrace();
			throw e;
		}
	}
}