package rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChat extends Remote{
	boolean signUp(String nick, String groupName, ICallback icb) throws RemoteException;
	boolean signOut(String nick) throws RemoteException;
	/**
	 * @param from - adresat wiadomo�ci
	 * @param to - odbiorca wiadomo�ci
	 * @param message - tre�� wiadomo�ci
	 */
	boolean sendToFriend(String from, String message) throws RemoteException;	// deleted: String to
	/**
	 * 
	 * @param from - adresat wiadomo�ci
	 * @param groupName - nazwa grupy
	 * @param message - tre�� wiadomo�ci
	 */
//	boolean sendToGroup(String from, String groupName, String message) throws RemoteException;
//	boolean findUser(String nick) throws RemoteException;
//	List<String> information() throws RemoteException;
//	boolean joinGroup(String nick, String groupName) throws RemoteException;
	/**
	 * 
	 * @param from - adresat zaproszenia
	 * @param to - odbiorca zaproszenia
	 * @param groupName - nazwa grupy
	 */
//	boolean askIfAdd(String from, String to, String groupName) throws RemoteException;
}