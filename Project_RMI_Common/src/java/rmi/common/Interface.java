package java.rmi.common;

import java.rmi.*;

public interface Interface extends Remote{
	void communicate(String text) throws RemoteException;

}
