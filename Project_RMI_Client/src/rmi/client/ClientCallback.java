package rmi.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import rmi.common.ICallback;

public class ClientCallback extends UnicastRemoteObject implements ICallback {

	private static final long serialVersionUID = 4601203039320885872L;
	Scanner input = new Scanner(System.in);

	public ClientCallback() throws RemoteException {
		super();
	}

	public void sendToFriend(String from, String message) throws RemoteException {
		System.out.println("Wiadomoœæ od " + from + ": " + message);		
	}

	public void sendToGroup(String nick, String grpName, String message) throws RemoteException {
		System.out.println("Wiadomoœæ do grupy: " + grpName + " od u¿ytkownika " + nick + ":\t" + message);
		
	}
	
	public boolean askIfAdd(String nick, String group){
		String line;
		System.out.println("\nU¿ytkownik " + nick + " zaprasza Ciê do grupy: " + group
				+ "\nWpisz \"y\" lub \"yes\", aby zaakceptowaæ proœbê.");
		if(input.hasNextLine()){
			line = input.nextLine();
			if(line.equals("y") || line.equals("yes")){
				System.out.println("Zosta³eœ pomyœlnie dodany do grupy: " + group);
				return true;
			}
		}
		return false;
	}
}