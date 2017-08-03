package ui.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import javax.swing.JOptionPane;

import rmi.common.ICallback;
import rmi.common.IChat;
import rmi.common.User;

public class Client {

	private Scanner input = new Scanner(System.in);
	private static final String HOST = "localhost";
	private String userName;
	String groupName, line;
	static IChat remoteObject;
	ICallback callback;
	static User user;

	public static void main(String[] args){
		new Client();
	}

	public Client(){
		// Okno dialogowe, w którym podajemy nazwê u¿ytkownika
		do{
			userName = JOptionPane.showInputDialog("Podaj nazwê u¿ytkownika:");
			if(userName == null){
				System.exit(-1);
			}

		} while(userName.isEmpty() || !userName.matches(".*\\w.*"));
		user = new User(userName, "all");
		
		Registry reg;	//rejestr nazw obiektów
		try{
			// pobranie referencji do rejestru nazw obiektow
			reg = LocateRegistry.getRegistry(HOST);
			// odszukanie zdalnego obiektu po jego nazwie
			remoteObject = (IChat) reg.lookup("ChatServer");
			callback = new ClientCallback();
			// wywolanie metod zdalnego obiektu
			remoteObject.signUp(user.getUserName(), user.getGroupName(), callback);
			System.out.println("Twoja nazwa: " + user.getUserName() + ", nazwa grupy: " + user.getGroupName());
			new Thread() {
	            @Override
	            public void run() {
	                javafx.application.Application.launch(Main.class);
	            }
	        }.start();
			loggedInLoop();
		}
		catch(RemoteException e){
			e.printStackTrace();
		}
		catch(NotBoundException e){
			e.printStackTrace();
		}
	}

	public static void sendToFriend(String msg){
		try{
			remoteObject.sendToFriend(user.getUserName(), msg);
		}
		catch(RemoteException e){
			e.printStackTrace();
		}
	}

//	public void sendToGroup(){
//		String line;
//		System.out.println("Podaj nazwê grupy, do której chcesz wys³aæ wiadomoœæ:");
//		if(input.hasNextLine()){
//			line = input.nextLine();
//			String groupText;
//			System.out.println("Podaj treœæ wiadomoœci:");
//			if(input.hasNextLine()){
//				groupText = input.nextLine();
//				try{
//					remoteObject.sendToGroup(userName, line, groupText);
//				}
//				catch(RemoteException e){
//					e.printStackTrace();
//				}
//			}
//		}
//	}

//	public void information(){
//		List<String> list = new ArrayList<String>();
//		try{
//			list = remoteObject.information();
//		}
//		catch(RemoteException e){
//			e.printStackTrace();
//		}
//		if(list.size() == 1){
//			System.out.println("Zalogowany jest " + list.size() + " u¿ytkownik:");
//		}
//		else if(list.size() > 1){
//			System.out.println("Zalogowanych jest " + list.size() + " u¿ytkowników:");
//		}
//		for(String s : list){
//			System.out.println(" - " + s);
//		}
//	}

//	public void joinGroup(){
//		boolean added = false;
//		System.out.println("Podaj nazwê grupy do której chcesz do³¹czyæ: ");
//		if(input.hasNextLine()){
//			line = input.nextLine();
//			try{
//				added = remoteObject.joinGroup(userName, line);
//			}
//			catch(RemoteException e){
//				e.printStackTrace();
//			}
//			if(added){
//				System.out.println("Zosta³eœ dodany do grupy " + line);
//			}
//			else{
//				System.out.println("Dodanie do grupy: " + line + " nie powiod³o siê!");
//			}
//		}
//	}

//	public void addToGroup(){
//		String nick;
//		boolean bool = false;
//		System.out.println("Podaj nazwê u¿ytkownika, którego chcesz dodaæ do grupy " + groupName);
//		if(input.hasNextLine()){
//			nick = input.nextLine();
//			try{
//				bool = remoteObject.askIfAdd(userName, nick, groupName);
//			}
//			catch(RemoteException e){
//				e.printStackTrace();
//			}
//			if(bool){
//				System.out.println("U¿ytkownik "+nick+" zosta³ pomyœlnie dodany do grupy " + groupName);
//			}
//			else System.out.println("Dodanie u¿ytkownika "+nick+" do grupy " + groupName 
//					+ " nie powiod³o siê, lub u¿ytkownik odrzuci³ zaproszenie.");
//		}
//	}

	public void loggedInLoop() throws RemoteException{
		while(true){
			System.out.print("U¿ytkowanie:\n\'a\' - dodaj do grupy\t\'f\' - znajdz u¿ytkownika"
					+ "\t\'g\' - wyœlij do grupy\t\'i\' - iloœæ u¿ytkowników online\n"
					+ "\'j\' - do³¹cz do grupy\t\'s\' - wyœlij wiadomoœæ\t\t\'q\' - wyjœcie\n");
			line = input.nextLine();
			if(!line.matches("[agijq]")){// bez 's'
				System.out.println("Niepoprawna komenda!");
				continue;
			}
			switch (line){
//			case "a":
//				addToGroup();
//				break;
//			case "g":
//				sendToGroup();
//				break;
//			case "i":
//				information();
//				break;
//			case "j":
//				joinGroup();
//				break;
//			case "s":
//				sendToFriend();
//				break;
			case "q":
				remoteObject.signOut(userName);
				System.exit(1);
				return;
			}
		}
	}
}