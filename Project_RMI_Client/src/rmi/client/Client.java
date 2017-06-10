package rmi.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Vector;

import rmi.common.ICallback;
import rmi.common.IChat;

public class Client {

	private Scanner input = new Scanner(System.in);
	String[] tab;
	String userName;
	String grpName;
	IChat remoteObject;
	ICallback callback;

	public static void main(String[] args) {
		if(args.length < 1){
			System.out.println("No arguments in run configuration!");
			System.exit(-1);
		}
		new Client(args[0]);
	}

	public Client(String hostname){
		System.out.println("Input your nick- and groupname, separate it with dot (.): ");
		if(input.hasNextLine()){
			tab = input.nextLine().split(".");
		}
		userName = tab[0];
		if(tab.length > 1){
			grpName = tab[1];
		}
		Registry reg;	//rejestr nazw obiektów
		try{
			reg = LocateRegistry.getRegistry(hostname);
			remoteObject = (IChat) reg.lookup("Server");
			callback = new ClientCallback();
			remoteObject.signUp(userName, grpName, callback);
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
		String uname;
		System.out.println("Addressee name: ");
		if(input.hasNextLine()){
			uname = input.nextLine();
			String userText;
			System.out.println("Enter text you want to send: ");
			if(input.hasNextLine()){
				userText = input.nextLine();
				try{
					remoteObject.sendToFriend(uname, userText);
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
		if(vec.size() == 1){
			System.out.println("There is " + vec.size() + " logged in user:");
		}
		else if(vec.size() > 1){
			System.out.println("There are " + vec.size() + " logged in users:");
		}
		for(String s : vec){
			System.out.println(" - " + s);
		}
	}

	public void loggedInLoop() throws RemoteException{
		String line;
		boolean loop = true;
		while(loop){
			System.out.println("Usage:\n\'i\'iloœæ u¿ytkowników\t\'f\' - wyœlij do przyjaciela\t"
					+ "\'g\' - wyœlij do grupy\t\'s\' - wyszukaj znajomego\t\'q\' - wyjœcie");
			line = input.nextLine();
			if(!line.matches("[ifgsq]")){
				System.out.println("Invalid command!");
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
				return;
			}
		}
	}
}