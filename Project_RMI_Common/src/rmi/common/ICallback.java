package rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICallback extends Remote{
	public void sendToFriend(String nick, String message) throws RemoteException;
	public void sendToGroup(String nick, String grpName, String message) throws RemoteException;
}
