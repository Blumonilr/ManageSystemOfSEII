package test.test_bl.log;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import main.businesslogic.userbl.User;
import main.businesslogic.userbl.UserInfo;

public class Test_Log {

	static UserInfo users;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		users=new User();
	}

	@Test
	public void testSaveLog() {
		fail("Not yet implemented");
	}

	@Test
	public void testReadLog() {
		fail("Not yet implemented");
	}

	@Test
	public void testUserInfo(){
		assertNull(users.showUser("user1"));
	}
	
	
}
