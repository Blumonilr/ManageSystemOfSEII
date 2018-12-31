package main.businesslogicservice.accountblservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import main.businesslogic.accountbl.Account;
import main.vo.AccountVO;

public interface AccountblService extends Remote{
	
	public AccountVO addAccount(AccountVO obj)throws RemoteException;
	
	public AccountVO findAccount(String id)throws RemoteException;
	
	public boolean deleteAccount(String accountId)throws RemoteException;
	
	public boolean login(String id,String password)throws RemoteException;
	
	public boolean checkUserLevel(String userId)throws RemoteException;
	
	public boolean updateAccount(AccountVO obj)throws RemoteException;
	
	public boolean updateBalance(String accountId,double difference)throws RemoteException;

	public ArrayList<AccountVO> getAccounts() throws RemoteException;
}
