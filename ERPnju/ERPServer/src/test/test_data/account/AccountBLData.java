package test_data.account;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.businesslogic.accountbl.Account;
import main.vo.AccountVO;
/**
 * 
 * @author kate
 *2017/11/27 测试通过
 */
public class AccountBLData {

	static Account account;
	@Before
	public void setUp() throws Exception {
		account=new Account();
	}

	//@Test
	public void test_addAccountWhenBook() {
		AccountVO vo1=new AccountVO("NickName1",666.6,"id1","pw1");
		account.addAccountWhenBook(vo1);
	}
	
	//@Test
	public void test_addAccount() {
		AccountVO vo2=new AccountVO("NickName2",666.6,"id2","pw2");
		account.addAccountWhenBook(vo2);
	}
	
	//@Test
	public void test_findAccountById() {
		AccountVO vo=account.findAccountById("jbs");
		if(vo==null){
			System.out.println("测试结果：找不到对象");
		}
		else{
			System.out.println("测试结果： "+vo.getId()+" "+vo.getPassword()+" "+vo.getBalance());
		}
	}
	
	//@Test
	public void test_findAccountByNickname() {
		AccountVO vo=account.findAccountByNickname("jbs");
		if(vo==null){
			System.out.println("测试结果：找不到对象");
		}
		else{
			System.out.println("测试结果： "+vo.getId()+" "+vo.getPassword()+" "+vo.getBalance());
		}
	}
	
	//@Test
	public void test_deleteAccount() {
		boolean res=account.deleteAccount("id1");
		if(res){
			System.out.println("测试结果： 删除成功");
		}
		else{
			System.out.println("测试结果： 删除失败");
		}
	}
	
	//@Test
	public void test_updateAccount() {
		AccountVO vo2=new AccountVO("NickName2_New",777666.6,"id2","pw2_new");
		account.updateAccount(vo2);
	}
	
	//@Test
	public void test_updateBalance() {
		account.updateBalance("null",80000);
	}
	
	//@Test
	public void test_login() {
		boolean res=account.login("test9","1234");
		if(res){
			System.out.println("测试结果： 登陆成功");
		}
		else{
			System.out.println("测试结果： 登陆失败");
		}
	}
	
	//@Test
	public void test_checkserLevel() {
		boolean res=account.checkUserLevel("whatever");
		if(res){
			System.out.println("测试结果： 合格");
		}
		else{
			System.out.println("测试结果： 不合格");
		}
	}


}
