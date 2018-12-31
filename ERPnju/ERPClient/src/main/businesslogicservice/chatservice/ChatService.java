package main.businesslogicservice.chatservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ChatService extends Remote {

	/**
	 * 此方法用于注销客户端
	 * @param name
	 * @throws RemoteException
	 */
	public void remove(String name)throws RemoteException;
	
	public void uploadTempMsg(String name,ArrayList<String>msgs)throws RemoteException;
}
