package main.businesslogic.accountbl;

import java.util.ArrayList;
import main.PO.AccountPO;
import main.businesslogic.accountbl.AccountInfo;
import main.businesslogic.userbl.UserInfo;
import main.data.accountdata.AccountData;
import main.dataservice.accountdataservice.AccountDataService;
import main.vo.AccountVO;

/**
 * 
 * @author Kate
 *
 */
public class Account implements AccountInfo {
	//似乎界面层知道谁登录就好了 String currentAccountId;//登录的账户id
	UserInfo userInfo;
	AccountDataService accountDataService;
	static final int REQUIRED_LEVEL=1;//执行操作的最低等级
	
	

	/**测试用
	 */
	public Account() {
		super();
		this.accountDataService =new AccountData();
	}

	/**
	 * AccountInfo的方法，提供所有账户的昵称
	 */
	@Override
	public ArrayList<String> showAccounts() {//done
		// TODO Auto-generated method stub
		ArrayList<String> result=new ArrayList<String>();
		ArrayList<AccountPO> accountList=accountDataService.showAll();
		for(AccountPO ac:accountList){
			String temp=ac.getNickname();
			result.add(temp);
		}
		return result;
	}
	
	/**
	 *  AccountInfo的方法，不需要用户等级的增加银行账户
	 */
	@Override
	public AccountVO addAccountWhenBook(AccountVO account) {//done
		// TODO Auto-generated method stub
		//将vo转化成po
		AccountPO po=this.voToPO(account);
		boolean result=accountDataService.add(po);
		if(result){
			return account;
		}
		else{
			return null;
		}
	}


	public AccountVO addAccount(AccountVO obj) {//done
		AccountPO po=this.voToPO(obj);
		boolean result=accountDataService.add(po);
		if(result){
			return obj;
		}
		else{
			return null;
		}
	}

	
	public AccountVO findAccountById(String id) {//done
		AccountPO po=accountDataService.findById(id);
		if(po==null){
			//没找到
			return null;
		}
		else{
			AccountVO vo=this.poToVO(po);
			return vo;
		}
	}

	public AccountVO findAccountByNickname(String nickname){//done
		AccountPO po=accountDataService.findByNickname(nickname);
		if(po==null){
			//没找到
			return null;
		}
		else{
			AccountVO vo=this.poToVO(po);
			return vo;
		}
	}
	
	public boolean deleteAccount(String accountId) {//done
		
		return accountDataService.delete(accountId);
	}
	
	public boolean updateAccount(AccountVO obj){//done
		AccountPO po=this.voToPO(obj);
		boolean result=accountDataService.updateAccount(po);
		if(result){
			System.out.println("bl层发出消息： 已更新");
		}
		else{
			System.out.println("bl层发出消息： 更新失败");
		}
		return result;
	}
	
	public boolean updateBalance(String accountId,double difference){//done
		//检查账户存在
		AccountPO po=accountDataService.findById(accountId);
		if(po==null){
			System.out.println("bl层消息： 账户不存在");
			return false;
		}
		
		//检查余额
		double oldBalance=po.getBalance();
		double newBalance=oldBalance+difference;
		if(newBalance<0){
			System.out.println("bl层消息： 余额小于0");
			return false;
		}
		
		//更新
		accountDataService.updateBalance(accountId, newBalance);
		
		return true;
	}

	
	public boolean login(String id, String password) {//done
		AccountPO accountPo=accountDataService.findById(id);
		if(accountPo==null){
			return false;
		}
		else{
			if(accountPo.getPassword().equals(password)){
				return true;
			}
			else{
				return false;
			}
		}
	}
	
	private  AccountPO voToPO(AccountVO vo){
		String nickName=vo.getNickname();
		String id=vo.getId();
		String password=vo.getPassword();
		double balance=vo.getBalance();
		AccountPO po=new AccountPO(false,id,password,balance,nickName);
		return po;		
	}
	
	private AccountVO poToVO(AccountPO po){
		String nickname=po.getNickname();
		String id=po.getId();
		String password=po.getPassword();
		double balance=po.getBalance();
		AccountVO vo=new AccountVO(nickname,balance,id,password);
		return vo;
	}
	
	
	public boolean checkUserLevel(String userId){//done
		int userLevel=userInfo.getUserLevel(userId);
		if(userLevel>=REQUIRED_LEVEL){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public AccountVO getAccountById(String accountId) {//done
		// TODO Auto-generated method stub
		return this.findAccountById(accountId);
	}

	@Override
	public boolean updateAccountBalance(String accountId, double difference) {//done
		// TODO Auto-generated method stub
		return this.updateBalance(accountId, difference);
	}

	@Override
	public ArrayList<AccountVO> getAccounts() {
		// TODO Auto-generated method stub
		ArrayList<AccountPO> list = accountDataService.showAll();
		ArrayList<AccountVO> result=new ArrayList<AccountVO>();
		for(AccountPO po:list){
			result.add(this.poToVO(po));
		}
		return result;
	}
}
