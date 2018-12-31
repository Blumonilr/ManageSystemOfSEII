package main.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import main.rmi.DataRemoteObject;

public class RemoteHelper {

	DataRemoteObject dataRemoteObject;
	
	public RemoteHelper() {
		initServer();
	}

	private void initServer() {
		// TODO 自动生成的方法存根
		try {
			dataRemoteObject = new DataRemoteObject();
			LocateRegistry.createRegistry(8887);
			Naming.bind("rmi://127.0.0.1:8887/DataRemoteObject",dataRemoteObject);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("rmi server started");
	}

}
