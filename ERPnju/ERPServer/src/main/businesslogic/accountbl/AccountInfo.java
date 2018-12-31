package main.businesslogic.accountbl;

import java.util.ArrayList;

import main.vo.AccountVO;

/**
 * 11/19 层内调用的接口
 * @author Kate
 *
 */
public interface AccountInfo {
	public ArrayList<String> showAccounts();
	public AccountVO addAccountWhenBook(AccountVO account);
	public AccountVO getAccountById(String accountId);
	public AccountVO findAccountByNickname(String nickname);
	public boolean updateAccountBalance(String accountId,double difference);
	public ArrayList<AccountVO> getAccounts();
}
