package rmi.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
			System.out.println("Brak argument�w w ustawieniach uruchamiania!");
			System.exit(-1);
		}
		new Client(args[0]);
	}

	public Client(String hostname){
		System.out.println("Podaj nazw� u�ytkownika oraz grupy, oddziel je podkre�leniem (_):");
		if(input.hasNextLine()){
			tab = input.nextLine().split("_");
		}
		userName = tab[0];
		if(tab.length <= 1){
			grpName = "none";
		}
		else grpName = tab[1];
		Registry reg;	//rejestr nazw obiekt�w
		try{
			// pobranie referencji do rejestru nazw obiektow
			reg = LocateRegistry.getRegistry(hostname);
			// odszukanie zdalnego obiektu po jego nazwie
			remoteObject = (IChat) reg.lookup("ChatServer");
			callback = new ClientCallback();
			// wywolanie metod zdalnego obiektu
			remoteObject.signUp(userName, grpName, callback);
			System.out.println("Twoja nazwa: " + userName + ", nazwa grupy: " + grpName);
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
		String to;
		System.out.println("Podaj nazw� u�ytkownika, do kt�rego chcesz wys�a� wiadomo��:");
		if(input.hasNextLine()){
			to = input.nextLine();
			String userText;
			System.out.println("Podaj tre�� wiadomo�ci:");
			if(input.hasNextLine()){
				userText = input.nextLine();
				try{
					remoteObject.sendToFriend(userName, to, userText);
				}
				catch(RemoteException e){
					e.printStackTrace();
				}
			}
		}
	}

	public void sendToGroup(){
		String group;
		System.out.println("Podaj nazw� grupy, do kt�rej chcesz wys�a� wiadomo��:");
		if(input.hasNextLine()){
			group = input.nextLine();
			String grpText;
			System.out.println("Podaj tre�� wiadomo�ci:");
			if(input.hasNextLine()){
				grpText = input.nextLine();
				try{
					remoteObject.sendToGroup(userName, group, grpText);
				}
				catch(RemoteException e){
					e.printStackTrace();
				}
			}
		}
	}

	public void findUser(){
		boolean contains = false;
		String uname;
		System.out.println("Podaj nazw� u�ytkownika, kt�rego szukasz:");
		if (input.hasNextLine()){
			uname = input.nextLine();
			try {
				contains = remoteObject.findUser(uname);
			}
			catch(RemoteException e){
				e.printStackTrace();
			}
			if (contains){
				System.out.println("U�ytkownik: " + uname + " jest zalogowany!");
			}
			else System.out.println("U�ytkownik: " + uname + " nie jest zalogowany!");
		}
	}

	public void information(){
		List<String> list = new ArrayList<String>();
		try{
			list = remoteObject.information();
		}
		catch(RemoteException e){
			e.printStackTrace();
		}
		if(list.size() == 1){
			System.out.println("Zalogowany jest " + list.size() + " u�ytkownik:");
		}
		else if(list.size() > 1){
			System.out.println("Zalogowanych jest " + list.size() + " u�ytkownik�w:");
		}
		for(String s : list){
			System.out.println(" - " + s);
		}
	}

	public void loggedInLoop() throws RemoteException{
		String line;
		while(true){
			System.out.println("Usage:\n\'i\'ilo�� u�ytkownik�w\t\'f\' - wy�lij do przyjaciela\t"
					+ "\'g\' - wy�lij do grupy\t\'s\' - wyszukaj znajomego\t\'q\' - wyj�cie");
			line = input.nextLine();
			if(!line.matches("[ifgsq]")){
				System.out.println("Niepoprawna komenda!");
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
				sendToGroup();
				break;
			case "s":
				findUser();
				break;
			case "q":
				remoteObject.signOut(userName);
				System.exit(1);
				return;
			}
		}
	}
}