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
			System.out.println("Brak argumentów w ustawieniach uruchamiania!");
			System.exit(-1);
		}
		new Client(args[0]);
	}

	public Client(String hostname){
		System.out.println("Podaj nazwê u¿ytkownika oraz grupy, oddziel je podkreœleniem (_):");
		if(input.hasNextLine()){
			tab = input.nextLine().split("_");
		}
		userName = tab[0];
		if(tab.length <= 1){
			groupName = "none";
		}
		else groupName = tab[1];
		Registry reg;	//rejestr nazw obiektów
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
		System.out.println("Podaj nazwê u¿ytkownika, do którego chcesz wys³aæ wiadomoœæ:");
		if(input.hasNextLine()){
			line = input.nextLine();
			String userText;
			System.out.println("Podaj treœæ wiadomoœci:");
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
		System.out.println("Podaj nazwê grupy, do której chcesz wys³aæ wiadomoœæ:");
		if(input.hasNextLine()){
			line = input.nextLine();
			String groupText;
			System.out.println("Podaj treœæ wiadomoœci:");
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
		System.out.println("Podaj nazwê u¿ytkownika, którego szukasz:");
		if (input.hasNextLine()){
			line = input.nextLine();
			try {
				bool = remoteObject.findUser(line);
			}
			catch(RemoteException e){
				e.printStackTrace();
			}
			if (bool){
				System.out.println("U¿ytkownik: " + line + " jest zalogowany!");
			}
			else System.out.println("U¿ytkownik: " + line + " nie jest zalogowany!");
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
			System.out.println("Zalogowany jest " + list.size() + " u¿ytkownik:");
		}
		else if(list.size() > 1){
			System.out.println("Zalogowanych jest " + list.size() + " u¿ytkowników:");
		}
		for(String s : list){
			System.out.println(" - " + s);
		}
	}

	public void joinGroup(){
		boolean added = false;
		System.out.println("Podaj nazwê grupy do której chcesz do³¹czyæ: ");
		if(input.hasNextLine()){
			line = input.nextLine();
			try{
				added = remoteObject.joinGroup(userName, line);
			}
			catch(RemoteException e){
				e.printStackTrace();
			}
			if(added){
				System.out.println("Zosta³eœ dodany do grupy " + line);
			}
			else{
				System.out.println("Dodanie do grupy: " + line + " nie powiod³o siê!");
			}
		}
	}

	public void addToGroup(){
		String nick;
		boolean bool = false;
		System.out.println("Podaj nazwê u¿ytkownika, którego chcesz dodaæ do grupy " + groupName);
		if(input.hasNextLine()){
			nick = input.nextLine();
			try{
				bool = remoteObject.askIfAdd(userName, nick, groupName);
			}
			catch(RemoteException e){
				e.printStackTrace();
			}
			if(bool){
				System.out.println("U¿ytkownik "+nick+" zosta³ pomyœlnie dodany do grupy " + groupName);
			}
			else System.out.println("Dodanie u¿ytkownika "+nick+" do grupy " + groupName 
					+ " nie powiod³o siê, lub u¿ytkownik odrzuci³ zaproszenie.");
		}

	}

	public void loggedInLoop() throws RemoteException{
		while(true){
			System.out.print("Usage:\n\'a\' - dodaj do grupy\t\'f\' - znajdz u¿ytkownika"
					+ "\t\'g\' - wyœlij do grupy\t\'i\' - iloœæ u¿ytkowników online\n"
					+ "\'j\' - do³¹cz do grupy\t\'s\' - wyœlij wiadomoœæ\t\t\'q\' - wyjœcie");
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