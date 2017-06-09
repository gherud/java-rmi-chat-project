package rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import rmi.common.ICallback;
import rmi.common.IChat;

@SuppressWarnings("serial")
public class Servant extends UnicastRemoteObject implements IChat{

	private Map<String, ICallback> present = new HashMap<String, ICallback>();

	public Servant() throws RemoteException{
	}

	// Implementacja funkcji signUp() znajduj¹cej siê w interfejsie IChat
	public boolean signUp(String nick, ICallback icb) throws RemoteException {
		System.out.println("Server.signUp(): " + nick);
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
	public boolean sendToFriend(String nick, String text) throws RemoteException {
		System.out.println("Server.sendToFriend() with: " + nick
				+ ", message: "+ text);
		return false;
	}

	// Implementacja funkcji sendToFriend() znajduj¹cej siê w interfejsie IChat
	public boolean sendToGroup(String grpName, String text) throws RemoteException {
		System.out.println("Server.sendToGroup() message to Group: " + grpName
				+ ", message: "+ text);
		return false;
	}

	// Implementacja funkcji findUser() znajduj¹cej siê w interfejsie IChat
	public boolean findUser(String nick) throws RemoteException {
		if(present.containsKey(nick)){
			System.out.println("Server.findUser(); User " + nick + " is logged in.");
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