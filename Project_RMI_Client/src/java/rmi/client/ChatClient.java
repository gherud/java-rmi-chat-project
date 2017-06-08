package java.rmi.client;

import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.common.ICallback;
import java.rmi.common.IChat;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Vector;

@SuppressWarnings("deprecation")
public class ChatClient {

	private Scanner input = new Scanner(System.in);
	String userName;
	IChat remoteObject;
	ICallback callback;

	public static void main(String[] args) {
		if(System.getSecurityManager() == null){
			System.setSecurityManager(new RMISecurityManager());
		}
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
			remoteObject = (IChat) reg.lookup("ChatServer");
			callback = new ClientCallback();
		}
		catch(RemoteException e){
			e.printStackTrace();
		}
		catch(NotBoundException e){
			e.printStackTrace();
		}
	}

	private void communicate(){
		String userName;
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
		Registry reg;
	}
	
	public void sendToGroup(String grpName, String text){
		// TODO implementacja
	}

	public void information(){
		// TODO implementacja
		String line;
		if(input.hasNextLine()){
			line = input.nextLine();
			Vector<String> vec = null;
		}
		else System.out.println("There are no logged in users.");
	}

	public void loggedInLoop(){
		// TODO implementacja
	}
}