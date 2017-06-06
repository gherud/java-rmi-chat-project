package java.rmi.client;

import java.rmi.RemoteException;
import java.rmi.common.Interface;
import java.rmi.server.UnicastRemoteObject;

public class ClientCallback extends UnicastRemoteObject implements Interface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4725821013384439527L;

	public ClientCallback() throws RemoteException{
		super();
	}

	@Override
	public void communicate(String text) throws RemoteException {
		System.out.println("Odebrano komunikat: " + text);
	}
}