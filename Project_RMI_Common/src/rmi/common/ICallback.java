package rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICallback extends Remote{
	public void sendToFriend(String from, String message) throws RemoteException;
//	public void sendToGroup(String from, String grpName, String message) throws RemoteException;
//	public boolean askIfAdd(String nick, String group) throws RemoteException;
}
