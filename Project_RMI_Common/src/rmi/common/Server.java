package rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
	public static final String DEFAULT_NAME = "ChatServer";

	boolean logIn(String name) throws RemoteException;
	void logOut(String name) throws RemoteException;
	void addUser(String name, Chat client) throws RemoteException;
	void sendMessage(String name, String message) throws RemoteException;
}