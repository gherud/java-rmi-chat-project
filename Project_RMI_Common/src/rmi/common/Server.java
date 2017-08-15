package rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/** Interfejs udostepniajacy metody dla Serwera
 * @author herud
 */
public interface Server extends Remote{
	public static final String DEFAULT_NAME = "ChatServer";
	
	// Funkcja sprawdzajaca, czy dany uzytkownik ma mozliwosc zalogowania sie
	boolean logIn(String name) throws RemoteException;
	// Wylogowywanie z Chatu
	void logOut(String name) throws RemoteException;
	// Dodawanie nowego uzytkownika
	void addUser(String name, Chat client) throws RemoteException;
	// Wysylanie wiadomosci
	void sendMessage(String name, String message) throws RemoteException;
}
