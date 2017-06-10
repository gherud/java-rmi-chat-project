package rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import rmi.common.ICallback;
import rmi.common.IChat;

public class Servant extends UnicastRemoteObject implements IChat{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5562295031945189580L;
	private Map<List<String>, ICallback> present = new HashMap<List<String>, ICallback>();

	public Servant() throws RemoteException{
	}

	// Implementacja funkcji signUp() znajduj¹cej siê w interfejsie IChat
	public boolean signUp(String nick, String grpName, ICallback icb) throws RemoteException {
		System.out.println("Server.signUp(): " + nick + ", name of group: " + grpName);
		List<String> list = new ArrayList<String>();
		list.add(nick);
		if(grpName != null) list.add(grpName);
		if(!present.containsKey(nick)){
			present.put(list, icb);
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
		System.out.println("Server.sendToFriend() to: " + nick
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
			System.out.println("Server.findUser() User " + nick + " is logged in.");
		}
		return false;
	}

	public Vector<String> information() throws RemoteException{
		Set<List<String>> users = present.keySet();
		Vector<String> vector = new Vector<String>();
		for(List<String> s : users){
			vector.add(s.get(0).split(" ")[0]);
		}
		return vector;
	}
}