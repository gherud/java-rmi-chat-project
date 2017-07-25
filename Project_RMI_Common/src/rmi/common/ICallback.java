package rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javafx.scene.control.TextArea;

public interface ICallback extends Remote{
	public void sendToFriend(String from, String message, TextArea ta) throws RemoteException;
	public void sendToGroup(String from, String grpName, String message) throws RemoteException;
	public boolean askIfAdd(String nick, String group) throws RemoteException;
}
