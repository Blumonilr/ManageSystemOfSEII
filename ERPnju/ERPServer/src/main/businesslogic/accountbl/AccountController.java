package main.businesslogic.accountbl;

import main.businesslogicservice.accountblservice.AccountblService;
import main.vo.AccountVO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * 
 * @author Kate
 *
 */
public class AccountController implements AccountblService {
	Account account=new Account();

	@Override
	public AccountVO addAccount(AccountVO obj) {
		// TODO Auto-generated method stub
		return account.addAccount(obj);
	}

	@Override
	public AccountVO findAccount(String id) {
		// TODO Auto-generated method stub
		return account.findAccountById(id);
	}

	@Override
	public boolean deleteAccount(String accountId) {
		// TODO Auto-generated method stub
		return account.deleteAccount(accountId);
	}

	@Override
	public boolean login(String id, String password) {
		// TODO Auto-generated method stub
		return account.login(id, password);
	}

	@Override
	public boolean checkUserLevel(String userId) {
		// TODO Auto-generated method stub
		return account.checkUserLevel(userId);
	}

	@Override
	public boolean updateAccount(AccountVO obj) {
		// TODO Auto-generated method stub
		return account.updateAccount(obj);
	}

	@Override
	public boolean updateBalance(String accountId, double difference) {
		// TODO Auto-generated method stub
		return account.updateBalance(accountId, difference);
	}

	@Override
	public ArrayList<AccountVO> getAccounts() throws RemoteException {
		return account.getAccounts();
	}

}
