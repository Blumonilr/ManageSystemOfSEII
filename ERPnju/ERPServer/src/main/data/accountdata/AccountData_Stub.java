package main.data.accountdata;

import java.util.ArrayList;

import main.PO.AccountPO;
import main.dataservice.accountdataservice.AccountDataService;

public class AccountData_Stub implements AccountDataService {

	@Override
	public boolean add(AccountPO account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String accountId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(AccountPO account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AccountPO findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountPO findByNickname(String nickname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<AccountPO> showAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
