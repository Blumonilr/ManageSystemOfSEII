package main.businesslogicservice.clientblservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import main.vo.ClientVO;

public interface ClientblService extends Remote{
	public boolean addClient(ClientVO obj)throws RemoteException;	
	public boolean delClient(String id)throws RemoteException;
	public boolean modifyClient(ClientVO obj)throws RemoteException;
	//根据名字、电话、默认业务员、客户类型、客户级别查找客户
	public ArrayList<ClientVO>searchClientbyNameTelCounterTypeLevel(String name,String tel,
			String counterman,int type,int level)throws RemoteException;	
	public ClientVO showClient(String id)throws RemoteException;
	public ArrayList<ClientVO> show()throws RemoteException;
}
