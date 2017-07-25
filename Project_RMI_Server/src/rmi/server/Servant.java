package rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.scene.control.TextArea;
import rmi.common.ICallback;
import rmi.common.IChat;
import rmi.common.User;

public class Servant extends UnicastRemoteObject implements IChat{

	private static final long serialVersionUID = 5562295031945189580L;
	private Map<User, ICallback> present = new HashMap<User, ICallback>();

	public Servant() throws RemoteException{}

	// Implementacja funkcji signUp() znajduj¹cej siê w interfejsie IChat
	public boolean signUp(String nick, String groupName, ICallback icb) throws RemoteException {
		String com = "Server.signUp(): " + nick + ", nazwa grupy: " + groupName;
		User user = new User(nick, groupName);
		if(present.isEmpty()){
			present.put(user, icb);
			System.out.println(com);
			return true;
		}
		else{
			for(User u : present.keySet()){
				if(!u.getUserName().equals(nick)){
					present.put(user, icb);
					System.out.println(com);
					return true;
				}
			}
		}
		return false;
	}

	// Implementacja funkcji signOut() znajduj¹cej siê w interfejsie IChat
	public boolean signOut(String nick) throws RemoteException {
		for(User u : present.keySet()){
			if(u.getUserName().equals(nick)){
				present.remove(u);
				System.out.println("Server.signOut(): " + nick);
				return true;
			}
		}
		return false;
	}

	// Implementacja funkcji sendToFriend() znajduj¹cej siê w interfejsie IChat
	public boolean sendToFriend(String from, String message, TextArea ta) throws RemoteException {
		// func param: String 'to' usuniête
		ICallback icb = null;
		for(User u : present.keySet()){
			icb = present.get(u);
			if(icb != null){
				icb.sendToFriend(from, message, ta);
				return true;
			}
		}
		System.out.println("Server.sendToFriend(): " + from + ": " + message);
		return false;	
	}

	// Implementacja funkcji sendToGroup() znajduj¹cej siê w interfejsie IChat
	public boolean sendToGroup(String from, String groupName, String message) throws RemoteException {
		List<ICallback> l = new ArrayList<ICallback>();
		for(User u : present.keySet()){
			if(u.getGroupName().equals(groupName)){
				l.add(present.get(u));
			}
		}
		for(ICallback icb : l){
			if(icb != null){
				icb.sendToGroup(from, groupName, message);
				System.out.println("Server.sendToGroup(): wiadomoœæ do grupy: " + 
						(groupName.isEmpty() ? "none" : groupName) + ", OD " + from + ": " + message);
				return true;
			}
		}
		return false;
	}

	// Implementacja funkcji findUser() znajduj¹cej siê w interfejsie IChat
	public boolean findUser(String nick) throws RemoteException {
		Set<User> users = present.keySet();
		List<String> list = new ArrayList<String>();
		for(User s : users){
			list.add(s.getUserName());
		}
		if(list.contains(nick)){
			System.out.println("Server.findUser(): " + nick);
			return true;
		}
		return false;
	}

	// Implementacja funkcji information() znajduj¹cej siê w interfejsie IChat
	public List<String> information() throws RemoteException{
		System.out.println("Server.information()");
		Set<User> users = present.keySet();
		List<String> list = new ArrayList<String>();
		for(User s : users){
			list.add(s.getUserName());
		}
		return list;
	}

	// Implementacja funkcji joinGroup() znajduj¹cej siê w interfejsie IChat
	public boolean joinGroup(String nick, String groupName){
		for(User u : present.keySet()){
			if(u.getUserName().equals(nick)){
				u.setGroupName(groupName);
				System.out.println("Server.joinGroup(): " + nick + " do³¹cza do grupy: " + groupName);
				return true;
			}
		}
		return false;
	}

	// Implementacja funkcji addToGroup() znajduj¹cej siê w interfejsie IChat
	public boolean askIfAdd(String from, String to, String groupName) throws RemoteException{
		ICallback cb = null;
		for(User u : present.keySet()){
			if(u.getUserName().equals(to)){
				cb = present.get(u);
			}
		}
		if(cb != null){
			System.out.println("Server.addToGroup(): " + to + " zosta³ dodany do grupy: " + groupName);
			return cb.askIfAdd(from, groupName);
		}
		return false;
	}
}