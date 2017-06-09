package rmi.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import rmi.common.ICallback;

public class ClientCallback extends UnicastRemoteObject implements ICallback{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4725821013384439527L;

	public ClientCallback() throws RemoteException{
		super();
	}

	public void sendToFriend(String nick, String text) throws RemoteException {
		System.out.println(nick + " send message: " + text);
	}

	public void sendToGroup(String nick, String grpName, String text) throws RemoteException {
		System.out.println(nick + " send message to your group: " + grpName + " content: " + text);
	}
}