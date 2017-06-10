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

	public void receivedMessage(String nick, String text) throws RemoteException {
		System.out.println("odebrano komunikat: " + text);		
	}
}