package rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	Registry reg; //rejestr nazw obiekt�w
	Servant servant; //klasa us�ugowa

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
			reg.rebind("Server", servant); //zwiazanie nazwy z obiektem
			System.out.println("!!! CHAT READY TO USE !!!");
		}
		catch(RemoteException e){
			e.printStackTrace();
			throw e;
		}
	}
}