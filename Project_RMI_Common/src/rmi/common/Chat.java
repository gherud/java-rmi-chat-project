package rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/** Interfejs udostepniajacy metody dla Chatu
 * @author herud
 */
public interface Chat extends Remote{	
	public static final String DEFAULT_NAME = "ChatClient";

	// Lista uzytkownikow
	void sendUserList(String[] userList) throws RemoteException;
	// Odbieranie wiadomosci
	void receiveMessage(String message) throws RemoteException;
}