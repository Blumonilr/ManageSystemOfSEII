package test.test_bl.user;

import org.junit.BeforeClass;

import main.businesslogic.userbl.User;

import static org.junit.Assert.*;

import org.junit.*;

public class TestUserbl {
	static User user;

	@BeforeClass
	public static void setUpTest(){
		user=new User();
	}
	
//	@Test
//	public void Test(){
//		user.remove("0001");
//		assertEquals(true,user.add(1,"0001","0001"));
//		assertEquals(false,user.add(2,"0001", "0002"));
//		assertEquals("0001",user.showUser("0001").getPassword());
//		assertEquals(true,user.modify(new UserVO("0001","0002",1,1)));
//		assertEquals("0002",user.showUser("0001").getPassword());
//		assertEquals(true,user.login("0001", "0002"));
//		assertEquals(1,user.getUserLevel("0001"));
//		assertEquals(true,user.remove("0001"));
//		assertEquals(false,user.remove("0001"));
//	}
	
}
