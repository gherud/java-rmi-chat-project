package rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICallback extends Remote{
	public void receivedMessage(String nick, String text) throws RemoteException;
//	public void sendToGroup(String nick, String grpName, String text) throws RemoteException;
}
