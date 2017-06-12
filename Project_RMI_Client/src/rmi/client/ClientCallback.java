package rmi.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import rmi.common.ICallback;

public class ClientCallback extends UnicastRemoteObject implements ICallback {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4601203039320885872L;

	public ClientCallback() throws RemoteException {
		super();
	}

	public void sendToFriend(String from, String message) throws RemoteException {
		System.out.println("Wiadomość od " + from + ": " + message);		
	}

	public void sendToGroup(String nick, String grpName, String message) throws RemoteException {
		// FIXME ClientCallback.sendToGroup - wiadomości nie docierają
		System.out.println("Wiadomość do grupy " + grpName + " od użytkownika " + nick + ":\n" + message);
		
	}
}