package main.dataservice.accountdataservice;

import java.util.ArrayList;

import main.PO.*;

public interface AccountDataService {

	public boolean add(AccountPO account);
	
	public boolean delete(String accountId);
	
	public boolean updateAccount(AccountPO account);
	
	public boolean updateBalance(String accountId,double newBalance);
	
	public AccountPO findById(String id);
	
	public AccountPO findByNickname(String nickname);
	
	public ArrayList<AccountPO> showAll();
}
