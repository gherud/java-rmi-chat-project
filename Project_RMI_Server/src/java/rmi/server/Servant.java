package java.rmi.server;

import java.rmi.RemoteException;
import java.rmi.common.Interface;

@SuppressWarnings("serial")
public class Servant extends UnicastRemoteObject implements Interface{
	
	public Servant() throws RemoteException{
		
	}

	@Override
	public void communicate(String text) throws RemoteException {
		System.out.println("Server.communicate(): " + text);
	}
}