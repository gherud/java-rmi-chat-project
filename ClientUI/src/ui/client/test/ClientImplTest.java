package ui.client.test;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import rmi.common.Server;
import rmi.server.ServerRunnable;
import ui.client.ChatUI;
import ui.client.ClientImpl;

public class ClientImplTest {

	static ChatUI user1;
	static ClientImpl user1Impl;	// USER #1
	static ChatUI user2;
	static ClientImpl user2Impl;	// USER #2

	@BeforeClass
	public static void setUpBefore() throws RemoteException, MalformedURLException, NotBoundException,
	AlreadyBoundException, InterruptedException {
		LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		Naming.bind(Server.DEFAULT_NAME, new ServerRunnable());
		Thread.sleep(100);

		user1Impl = new ClientImpl(user1 = new ChatUI());
		user2Impl = new ClientImpl(user2 = new ChatUI());
	}
	
	@Test
	public void testIsNicknameOkay() {
		new Thread(() -> {
			try {
				user1Impl.isNicknameOkay("User1");
				user2Impl.isNicknameOkay("User2");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
	}


	@Test
	public void testSendMessage() {
		user1Impl.sendMessage("Wiadomoœæ od u¿ytkownika 1");
		user2Impl.sendMessage("Wiadomoœæ od u¿ytkownika 2");
	}

	@AfterClass
	public static void tearDownAfter() {
		user1Impl.logout();
		user2Impl.logout();
	}
}