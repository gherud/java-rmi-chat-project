package ui.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import javafx.fxml.FXMLLoader;
import rmi.common.ICallback;

public class ClientCallback extends UnicastRemoteObject implements ICallback {

	private static final long serialVersionUID = 4601203039320885872L;
	Scanner input = new Scanner(System.in);

	public ClientCallback() throws RemoteException {
		super();
	}

	public void sendToFriend(String from, String msg) throws RemoteException {
		FXMLLoader fxml = new FXMLLoader(Controller.class.getClass().getResource("UI.fxml"));
		Controller ctrl = fxml.getController();
		ctrl.getTextArea().appendText(msg);
		
		System.out.println("\n" + from + ": " + msg + "\tNOWA WIADOMO��");		
	}

	public void sendToGroup(String nick, String grpName, String message) throws RemoteException {
		System.out.println("\nWiadomo�� do grupy: " + grpName + " od u�ytkownika " + 
				nick + ":\t" + message);
	}

	public boolean askIfAdd(String nick, String group){
		String line;
		System.out.println("\nU�ytkownik " + nick + " zaprasza Ci� do grupy: " + group
				+ "\nWpisz \"t\" lub \"tak\", aby zaakceptowa� pro�b�.");
		if(input.hasNextLine()){
			line = input.nextLine();
			if(line.equals("t") || line.equals("tak")){
				System.out.println("\nZosta�e� pomy�lnie dodany do grupy: " + group);
				return true;
			}
		}
		return false;
	}
}