package java.rmi.client;

import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.common.Interface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@SuppressWarnings("deprecation")
public class Client {

	public static void main(String[] args) {
		if(System.getSecurityManager() == null){
			System.setSecurityManager(new RMISecurityManager());
		}
		if(args.length < 2){
			System.out.println("Usage: ChatClient <server host name> <msg>");
			System.exit(-1);
		}
		
		Interface interf;
		Registry reg;
		
		try{
			reg = LocateRegistry.getRegistry(args[0]);
			interf = (Interface) reg.lookup("Server");
			interf = new ClientCallback();
			interf.communicate(args[1]);
		}
		catch(RemoteException e){
			e.printStackTrace();
		}
		catch(NotBoundException e){
			e.printStackTrace();
		}
	}
}