package rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rmi.common.ICallback;
import rmi.common.IChat;
import rmi.common.User;

public class Servant extends UnicastRemoteObject implements IChat{

	private static final long serialVersionUID = 5562295031945189580L;
	private Map<User, ICallback> present = new HashMap<User, ICallback>();

	public Servant() throws RemoteException{
	}

	// Implementacja funkcji signUp() znajduj¹cej siê w interfejsie IChat
	public boolean signUp(String nick, String grpName, ICallback icb) throws RemoteException {
		System.out.println("Server.signUp(): " + nick);// + ", nazwa grupy: " + grpName);
		User user = new User(nick, grpName);
		if(present.isEmpty()){
			present.put(user, icb);
			return true;
		}
		else{
			for(User u : present.keySet()){
				if(!u.getNick().equals(nick)){
					present.put(user, icb);
					return true;
				}
			}
		}
		return false;
	}

	// Implementacja funkcji signOut() znajduj¹cej siê w interfejsie IChat
	public boolean signOut(String nick) throws RemoteException {
		for(User u : present.keySet()){
			if(u.getNick().equals(nick)){
				present.remove(u);
				System.out.println("Server.signOut(): " + nick);
				return true;
			}
		}
		return false;
	}

	// Implementacja funkcji sendToFriend() znajduj¹cej siê w interfejsie IChat
	public boolean sendToFriend(String from, String to, String message) throws RemoteException {
		System.out.println("sendToFriend() from: " + from + " to: " + to + ": "+ message);
		ICallback icb = null;
		for(User u : present.keySet()){
			if(u.getNick().equals(to)){
				icb = present.get(u);
			}
		}
		if(icb != null){
			icb.sendToFriend(from, message);
			return true;
		}		
		return false;
	}

	// Implementacja funkcji sendToFriend() znajduj¹cej siê w interfejsie IChat
	public boolean sendToGroup(String from, String grpName, String message) throws RemoteException {
		System.out.println("Server.sendToGroup() wiadomoœæ do grupy: " + 
				(grpName.isEmpty() ? "none" : grpName) + ", wiadomoœæ: " + message);
		List<ICallback> l = new ArrayList<ICallback>();
		for(User u : present.keySet()){
			if(u.getGrpName().equals(grpName)){
				l.add(present.get(u));
			}
		}
		for(ICallback icb : l){
			if(icb != null){
				icb.sendToGroup(from, grpName, message);
			}
		}
		return false;
	}
	

	// Implementacja funkcji findUser() znajduj¹cej siê w interfejsie IChat
	public boolean findUser(String nick) throws RemoteException {
		System.out.println("Server.findUser(): " + nick);
		Set<User> users = present.keySet();
		List<String> list = new ArrayList<String>();
		for(User s : users){
			list.add(s.getNick());
		}
		if(list.contains(nick)){
			return true;
		}
		return false;
	}

	public List<String> information() throws RemoteException{
		Set<User> users = present.keySet();
		List<String> list = new ArrayList<String>();
		for(User s : users){
			list.add(s.getNick());
		}
		return list;
	}
}