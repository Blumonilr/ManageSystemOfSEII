package test_data.account;

import main.PO.AccountPO;
import main.data.accountdata.AccountData;

public class Test_AccountData {
	public static void main(String[] args){
		AccountData accountData=new AccountData();
		//accountData.findById("qyc");
		
		//AccountPO account=new AccountPO(false,"test4","123",888.9,"kate");
		//accountData.add(account);
		
		//accountData.delete("test1");
		
		accountData.updateBalance("jbs", 5555.5);
	}
}
