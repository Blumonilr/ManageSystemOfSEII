package test.test_data.client;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import main.PO.ClientPO;
import main.data.clientdata.ClientDataImpl;

public class Test_ClientDataImpl {

	static ClientDataImpl cdi=null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
//		Connection conn=DriverManager.getConnection("jdbc:sqlite:c:\\users\\17678\\Desktop\\MyGit\\ManageSystem\\info.db");
		cdi=new ClientDataImpl();
		
	}

//	@Test
	public void test_addClient() {
		ClientPO obj=new ClientPO("jbstest", "testclient");
		obj.setLevel(5);
		assertEquals(true,cdi.addClient(obj));
		assertEquals(false,cdi.addClient(obj));
	}

//	@Test
	public void test_showClient(){
		ClientPO c=cdi.showClient("0001");
		assertEquals("testclient",c.getName());
		ClientPO c1=cdi.showClient(null);
		assertEquals(null,c1);
	}
	
//	@Test
	public void test_deleteClient(){
		ClientPO obj=new ClientPO("0002", "testclient");
		cdi.addClient(obj);
		;
		assertEquals(true,cdi.deleteClient("0002"));
		assertEquals(false,cdi.deleteClient("0002"));
	}
	
//	@Test
	public void test_show(){
		ClientPO obj=new ClientPO("0001", "testclient");
		ArrayList<ClientPO>list=new ArrayList<>();
		list.add(obj);
		ArrayList<ClientPO>ans=cdi.show();
		for(int i=0;i<list.size();i++){
			System.out.println(ans.get(i));
		}
	}
	
//	@Test 
	public void test_modifyClient1(){
		ClientPO obj=cdi.showClient("0001");
		obj.setTel("102");
		assertEquals(true,cdi.modifyClient(obj));
		assertEquals("102",cdi.showClient("0001").getTel());
	}
	
	@Test
	public void test_modifyClient2(){
		//什么也不修改
		ClientPO obj=cdi.showClient("5c0f84dd9cb8");
		assertEquals(true,cdi.modifyClient(obj));
		
		//修改电话地址
		obj.setTel("666666");
		obj.setAddr("南昌大学");
		assertEquals(true,cdi.modifyClient(obj));
		//修改级别
		obj.setLevel(2);
		assertEquals(true,cdi.modifyClient(obj));
		
		
	}
}
