package test;

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

	static ClientImpl user1Impl;	// USER #1
	static ClientImpl user2Impl;	// USER #2
	String[] userList = {"User1", "User2", "User3"};

	/**Przed testem nale�y uruchomi� Server oraz utworzy� nowych u�ytkownik�w,
	 * kt�rzy pos�u�� do przeprowadzenia test�w */
	@BeforeClass
	public static void setUpBefore() throws RemoteException, MalformedURLException, NotBoundException,
	AlreadyBoundException, InterruptedException {
		LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		Naming.bind(Server.DEFAULT_NAME, new ServerRunnable());
		Thread.sleep(100);

		user1Impl = new ClientImpl(new ChatUI());
		user2Impl = new ClientImpl(new ChatUI());
	}

	/** Test funkcji sprawdzaj�cej dost�pno�� nicku podczas logowania do chatu */
	@Test
	public void testIsNicknameOkay() {
		new Thread(() -> {
			try {
				user1Impl.isNicknameOkay("User1");
				user2Impl.isNicknameOkay("User2");
			} catch (RemoteException re) {
				re.printStackTrace();
			}
		});
	}
	
	/** Test funkcji aktualizuj�cej list� zalogowanych u�ytkownik�w */
	@Test
	public void testSendUserList() {
		new Thread(() -> {
			try{
				user1Impl.sendUserList(userList);
			} catch (RemoteException re) {
				re.printStackTrace();
			}
		});
	}

	/** Sprawdzenie czy wysy�anie wiadomo�ci jest mo�liwe */
	@Test
	public void testSendMessage() {
		user1Impl.sendMessage("Wiadomo�� od u�ytkownika 1");
		user2Impl.sendMessage("Wiadomo�� od u�ytkownika 2");
	}

	/** wylogowanie obu u�ytkownik�w */
	@AfterClass
	public static void tearDownAfter() {
		user1Impl.logout();
		user2Impl.logout();
	}
}