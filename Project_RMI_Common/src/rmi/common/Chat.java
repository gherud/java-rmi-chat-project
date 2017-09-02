package rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat extends Remote {

	public static final String DEFAULT_NAME = "ChatClient";

	void sendUserList(String[] userList) throws RemoteException;
	void receiveMessage(String message) throws RemoteException;
}