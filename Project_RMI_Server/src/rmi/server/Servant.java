package rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import rmi.common.ICallback;
import rmi.common.IChat;

public class Servant extends UnicastRemoteObject implements IChat{

	private static final long serialVersionUID = 5562295031945189580L;
	private Map<String, ICallback> present = new HashMap<String, ICallback>();

	public Servant() throws RemoteException{
	}

	// Implementacja funkcji signUp() znajduj¹cej siê w interfejsie IChat
	public boolean signUp(String nick, String grpName, ICallback icb) throws RemoteException {
		System.out.println("Server.signUp(): " + nick);// + ", nazwa grupy: " + grpName);
		if(!present.containsKey(nick)){
			present.put(nick, icb);
			return true;
		}
		return false;
	}

	// Implementacja funkcji signOut() znajduj¹cej siê w interfejsie IChat
	public boolean signOut(String nick) throws RemoteException {
		if(present.remove(nick) != null){
			System.out.println("Server.signOut(): " + nick);
			return true;
		}
		return false;
	}

	// Implementacja funkcji sendToFriend() znajduj¹cej siê w interfejsie IChat
	public boolean sendToFriend(String from, String to, String message) throws RemoteException {
		System.out.println("sendToFriend() from: " + from + " to: " + to + ": "+ message);
		ICallback icb = present.get(to);
		if(icb != null){
			icb.sendToFriend(from, message);
			return true;
		}		
		return false;
	}

	// Implementacja funkcji sendToFriend() znajduj¹cej siê w interfejsie IChat
	public boolean sendToGroup(String from, String grpName, String message) throws RemoteException {
		System.out.println("Server.sendToGroup() wiadomoœæ do grupy: " + grpName
				+ ", wiadomoœæ: "+ message);
		return false;
	}

	// Implementacja funkcji findUser() znajduj¹cej siê w interfejsie IChat
	public boolean findUser(String nick) throws RemoteException {
		System.out.println("Server.findUser(): " + nick);
		Set<String> users = present.keySet();
		Vector<String> vector = new Vector<String>();
		for(String s : users){
			vector.add(s);
		}
		if(vector.contains(nick)){
			return true;
		}
		return false;
	}

	public Vector<String> information() throws RemoteException{
		Set<String> users = present.keySet();
		Vector<String> vector = new Vector<String>();
		for(String s : users){
			vector.add(s);
		}
		return vector;
	}
}