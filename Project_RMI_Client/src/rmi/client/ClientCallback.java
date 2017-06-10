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

	public void sendToFriend(String nick, String message) throws RemoteException {
		// FIXME ClientCallback.sendToFriend - wiadomo�ci nie docieraj�
		System.out.println("Odebrano komunikat: " + message);		
	}

	public void sendToGroup(String nick, String grpName, String message) throws RemoteException {
		// FIXME ClientCallback.sendToGroup - wiadomo�ci nie docieraj�
		System.out.println("Wiadomo�� do grupy " + grpName + " od u�ytkownika " + nick + ":\n" + message);
		
	}
}