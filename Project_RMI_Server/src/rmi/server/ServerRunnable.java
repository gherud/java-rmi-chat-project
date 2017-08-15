package rmi.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import rmi.common.Chat;
import rmi.common.Server;

public class ServerRunnable extends UnicastRemoteObject implements Server {

	private static final long serialVersionUID = 1L;
	private Map<String, Chat> user = new HashMap<String, Chat>();

	public ServerRunnable() throws RemoteException {
		super();
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException, 
			AlreadyBoundException, InterruptedException, NotBoundException {
		LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		Naming.bind(Server.DEFAULT_NAME, new ServerRunnable());

		Thread.sleep(100);
	}
	
	@Override
	public synchronized boolean logIn(String name) {
		return !this.user.containsKey(name);
	}

	@Override
	public synchronized void addUser(String name, Chat client) {
		this.user.put(name, client);
		this.sendUserListToClients();
	}

	@Override
	public synchronized void logOut(String name) {
		this.user.remove(name);
		this.sendUserListToClients();
	}

	public synchronized void sendUserListToClients() {
		while (!this.iterateForUserList());
	}

	private boolean iterateForUserList() {
		for(Iterator<Map.Entry<String, Chat>> it = this.user.entrySet().iterator(); it.hasNext();) {
			try	{
				it.next().getValue().sendUserList(this.user.keySet().toArray(new String[0]));
			}
			catch (RemoteException e) {
				it.remove();
				return false;
			}
		}
		return true;
	}

	@Override
	public synchronized void sendMessage(String name, String message) {
		String finalMessage = name + ": " + message;
		boolean bool = false;
		for(Iterator<Map.Entry<String, Chat>> it = this.user.entrySet().iterator(); it.hasNext();) {
			try	{
				it.next().getValue().receiveMessage(finalMessage);
			}
			catch (RemoteException e) {
				it.remove();
				bool = true;
			}
		}
		if(bool) {
			this.sendUserListToClients();
		}
	}
}