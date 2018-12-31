package test.test_data.user;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import main.PO.UserPO;
import main.data.userdata.UserData;
import main.data.util.ReviseCaller;

public class TestUser {
	static UserData userData;
	static ReviseCaller caller;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userData=new UserData();
		caller=new ReviseCaller();
	}
	
	
	@Test
	public void test0(){
		UserPO User=new UserPO(0, "admin", "admin", 1);
		assertEquals(true,userData.insert(User));
	}
	
	//@Test
	public void test(){
		UserPO User=new UserPO(1, "0001", "1234", 2);
		UserPO User2=new UserPO(2,"0002","1234",1);
		UserPO User3=new UserPO(3,"0003","1234",1);
		UserPO User4=new UserPO(4,"0004","1234",5);
		userData.delete(User);
		assertEquals(true,userData.insert(User));
		userData.delete(User2);
		assertEquals(true,userData.insert(User2));
		userData.delete(User3);
		assertEquals(true,userData.insert(User3));
		userData.delete(User4);
		assertEquals(true,userData.insert(User4));
//		assertEquals("1234",userData.find("0001").getPassword());
//		assertEquals(false,userData.insert(User));
//		User.setPassword("1235");
//		assertEquals(true,userData.update(User));
//		assertEquals(true,userData.delete(User));
//		assertEquals(null,userData.find("0001"));
	}

	//@Test
	public void TestNotFound(){
		UserPO User=new UserPO(1, "0005", "1111", 2);
		userData.delete(User);
		assertEquals(null,userData.find("0005"));
	}
	
	//@Test
	public void TestFinds(){
		ArrayList<UserPO> list=userData.finds(null);
		for(UserPO u:list){
			System.out.println(u.getId());
		}
	}
	
	@Test
	public void TestMessage(){
		ArrayList<String> arr=caller.messageCall("0001");
		for(String s:arr){
			System.out.println(s);
		}
	}
}
