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
		// Okno dialogowe, w kt�rym podajemy nazw� u�ytkownika
		do{
			userName = JOptionPane.showInputDialog("Podaj nazw� u�ytkownika:");
			if(userName == null){
				System.exit(-1);
			}

		} while(userName.isEmpty() || !userName.matches(".*\\w.*"));
		user = new User(userName, "all");
		
		Registry reg;	//rejestr nazw obiekt�w
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
//		System.out.println("Podaj nazw� grupy, do kt�rej chcesz wys�a� wiadomo��:");
//		if(input.hasNextLine()){
//			line = input.nextLine();
//			String groupText;
//			System.out.println("Podaj tre�� wiadomo�ci:");
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
//			System.out.println("Zalogowany jest " + list.size() + " u�ytkownik:");
//		}
//		else if(list.size() > 1){
//			System.out.println("Zalogowanych jest " + list.size() + " u�ytkownik�w:");
//		}
//		for(String s : list){
//			System.out.println(" - " + s);
//		}
//	}

//	public void joinGroup(){
//		boolean added = false;
//		System.out.println("Podaj nazw� grupy do kt�rej chcesz do��czy�: ");
//		if(input.hasNextLine()){
//			line = input.nextLine();
//			try{
//				added = remoteObject.joinGroup(userName, line);
//			}
//			catch(RemoteException e){
//				e.printStackTrace();
//			}
//			if(added){
//				System.out.println("Zosta�e� dodany do grupy " + line);
//			}
//			else{
//				System.out.println("Dodanie do grupy: " + line + " nie powiod�o si�!");
//			}
//		}
//	}

//	public void addToGroup(){
//		String nick;
//		boolean bool = false;
//		System.out.println("Podaj nazw� u�ytkownika, kt�rego chcesz doda� do grupy " + groupName);
//		if(input.hasNextLine()){
//			nick = input.nextLine();
//			try{
//				bool = remoteObject.askIfAdd(userName, nick, groupName);
//			}
//			catch(RemoteException e){
//				e.printStackTrace();
//			}
//			if(bool){
//				System.out.println("U�ytkownik "+nick+" zosta� pomy�lnie dodany do grupy " + groupName);
//			}
//			else System.out.println("Dodanie u�ytkownika "+nick+" do grupy " + groupName 
//					+ " nie powiod�o si�, lub u�ytkownik odrzuci� zaproszenie.");
//		}
//	}

	public void loggedInLoop() throws RemoteException{
		while(true){
			System.out.print("U�ytkowanie:\n\'a\' - dodaj do grupy\t\'f\' - znajdz u�ytkownika"
					+ "\t\'g\' - wy�lij do grupy\t\'i\' - ilo�� u�ytkownik�w online\n"
					+ "\'j\' - do��cz do grupy\t\'s\' - wy�lij wiadomo��\t\t\'q\' - wyj�cie\n");
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