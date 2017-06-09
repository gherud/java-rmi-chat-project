package rmi.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Vector;

import rmi.common.ICallback;
import rmi.common.IChat;

@SuppressWarnings("deprecation")
public class ChatClient {

	private Scanner input = new Scanner(System.in);
	String userName;
	IChat remoteObject;
	ICallback callback;

	public static void main(String[] args) {
		if(args.length < 1){
			System.out.println("Usage: ChatClient <server host name>");
			System.exit(-1);
		}
		new ChatClient(args[0]);		
	}

	public ChatClient(String hostname){
		System.out.println("Input your nickname: ");
		if(input.hasNextLine()){
			userName = input.nextLine();
		}
		Registry reg;	//rejestr nazw obiektów
		try{
			reg = LocateRegistry.getRegistry(hostname);
			remoteObject = (IChat) reg.lookup("Server");
			callback = new ClientCallback();
			remoteObject.signUp(userName, callback);
			loggedInLoop();
		}
		catch(RemoteException e){
			e.printStackTrace();
		}
		catch(NotBoundException e){
			e.printStackTrace();
		}
	}

	private void sendToFriend(){
		System.out.println("Addressee name: ");
		if(input.hasNextLine()){
			userName = input.nextLine();
			String userText;
			System.out.println("Enter text you want to send: ");
			if(input.hasNextLine()){
				userText = input.nextLine();
				try{
					remoteObject.sendToFriend(userName, userText);
				}
				catch(RemoteException e){
					e.printStackTrace();
				}
			}
		}
	}

	public void sendToGroup(String grpName, String text){
		// TODO ChatClient.sendToGroup() - implementacja
	}
	
	public void findUser(){
		// TODO ChatClient.findUser() - implementacja
	}

	public void information(){
		Vector<String> vec = new Vector<String>();
		try{
			vec = remoteObject.information();
		}
		catch(RemoteException e){
			e.printStackTrace();
		}
		System.out.println("There are " + vec.size() + " logged in user(s).");
		for(String s : vec){
			System.out.println(" - " + s);
		}
	}

	public void loggedInLoop() throws RemoteException{
		System.out.println("Usage:\n[i]nformuj\t[f] - wyœlij do przyjaciela\t"
				+ "[g] - wyœlij do grupy\t[s] - wyszukaj znajomego\n[q] - wyjœcie");
		String line;
		boolean loop = true;
		while(loop){
			line = input.nextLine();
			if(!line.matches("[ifgsq]")){
				System.out.println("Invalid character!");
				continue;
			}
			switch (line){
			case "i":
				information();
				break;
			case "f":
				sendToFriend();
				break;
			case "g":
//				sendToGroup();
				break;
			case "s":
				findUser();
				break;
			case "q":
				remoteObject.signOut(userName);
				loop = false;
				break;
			}
		}
	}
}