package java.rmi.client;

import java.rmi.RemoteException;
import java.rmi.common.ICallback;
import java.rmi.server.UnicastRemoteObject;

public class ClientCallback extends UnicastRemoteObject implements ICallback{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4725821013384439527L;

	public ClientCallback() throws RemoteException{
		super();
	}

	@Override
	public void sendToFriend(String nick, String text) throws RemoteException {
		System.out.println(nick + " send message: " + text);		
	}

	@Override
	public void sendToGroup(String nick, String grpName, String text) throws RemoteException {
		// TODO implementacja
		
	}
}