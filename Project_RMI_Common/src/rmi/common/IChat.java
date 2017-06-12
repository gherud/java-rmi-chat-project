package rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface IChat extends Remote{
	boolean signUp(String nick, String grpName, ICallback icb) throws RemoteException;
	boolean signOut(String nick) throws RemoteException;
	boolean sendToFriend(String from, String to, String message) throws RemoteException;
	boolean sendToGroup(String from, String grpName, String message) throws RemoteException;
	boolean findUser(String nick) throws RemoteException;
	Vector<String> information() throws RemoteException;
}