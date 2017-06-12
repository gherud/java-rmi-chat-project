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
	String groupName;
	String line;
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
			groupName = "none";
		}
		else groupName = tab[1];
		Registry reg;	//rejestr nazw obiekt�w
		try{
			// pobranie referencji do rejestru nazw obiektow
			reg = LocateRegistry.getRegistry(hostname);
			// odszukanie zdalnego obiektu po jego nazwie
			remoteObject = (IChat) reg.lookup("ChatServer");
			callback = new ClientCallback();
			// wywolanie metod zdalnego obiektu
			remoteObject.signUp(userName, groupName, callback);
			System.out.println("Twoja nazwa: " + userName + ", nazwa grupy: " + groupName);
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
		String line;
		System.out.println("Podaj nazw� u�ytkownika, do kt�rego chcesz wys�a� wiadomo��:");
		if(input.hasNextLine()){
			line = input.nextLine();
			String userText;
			System.out.println("Podaj tre�� wiadomo�ci:");
			if(input.hasNextLine()){
				userText = input.nextLine();
				try{
					remoteObject.sendToFriend(userName, line, userText);
				}
				catch(RemoteException e){
					e.printStackTrace();
				}
			}
		}
	}

	public void sendToGroup(){
		String line;
		System.out.println("Podaj nazw� grupy, do kt�rej chcesz wys�a� wiadomo��:");
		if(input.hasNextLine()){
			line = input.nextLine();
			String groupText;
			System.out.println("Podaj tre�� wiadomo�ci:");
			if(input.hasNextLine()){
				groupText = input.nextLine();
				try{
					remoteObject.sendToGroup(userName, line, groupText);
				}
				catch(RemoteException e){
					e.printStackTrace();
				}
			}
		}
	}

	public void findUser(){
		String line;
		boolean bool = false;
		System.out.println("Podaj nazw� u�ytkownika, kt�rego szukasz:");
		if (input.hasNextLine()){
			line = input.nextLine();
			try {
				bool = remoteObject.findUser(line);
			}
			catch(RemoteException e){
				e.printStackTrace();
			}
			if (bool){
				System.out.println("U�ytkownik: " + line + " jest zalogowany!");
			}
			else System.out.println("U�ytkownik: " + line + " nie jest zalogowany!");
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

	public void joinGroup(){
		boolean added = false;
		System.out.println("Podaj nazw� grupy do kt�rej chcesz do��czy�: ");
		if(input.hasNextLine()){
			line = input.nextLine();
			try{
				added = remoteObject.joinGroup(userName, line);
			}
			catch(RemoteException e){
				e.printStackTrace();
			}
			if(added){
				System.out.println("Zosta�e� dodany do grupy " + line);
			}
			else{
				System.out.println("Dodanie do grupy: " + line + " nie powiod�o si�!");
			}
		}
	}

	public void addToGroup(){
		String nick;
		boolean bool = false;
		System.out.println("Podaj nazw� u�ytkownika, kt�rego chcesz doda� do grupy " + groupName);
		if(input.hasNextLine()){
			nick = input.nextLine();
			try{
				bool = remoteObject.askIfAdd(userName, nick, groupName);
			}
			catch(RemoteException e){
				e.printStackTrace();
			}
			if(bool){
				System.out.println("U�ytkownik "+nick+" zosta� pomy�lnie dodany do grupy " + groupName);
			}
			else System.out.println("Dodanie u�ytkownika "+nick+" do grupy " + groupName 
					+ " nie powiod�o si�, lub u�ytkownik odrzuci� zaproszenie.");
		}

	}

	public void loggedInLoop() throws RemoteException{
		while(true){
			System.out.print("Usage:\n\'a\' - dodaj do grupy\t\'f\' - znajdz u�ytkownika"
					+ "\t\'g\' - wy�lij do grupy\t\'i\' - ilo�� u�ytkownik�w online\n"
					+ "\'j\' - do��cz do grupy\t\'s\' - wy�lij wiadomo��\t\t\'q\' - wyj�cie");
			line = input.nextLine();
			if(!line.matches("[afgijsq]")){
				System.out.println("Niepoprawna komenda!");
				continue;
			}
			switch (line){
			case "a":
				addToGroup();
				break;
			case "f":
				findUser();
				break;
			case "g":
				sendToGroup();
				break;
			case "i":
				information();
				break;
			case "j":
				joinGroup();
				break;
			case "s":
				sendToFriend();
				break;
			case "q":
				remoteObject.signOut(userName);
				System.exit(1);
				return;
			}
		}
	}
}