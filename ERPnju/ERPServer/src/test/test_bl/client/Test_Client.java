package test.test_bl.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import main.businesslogic.clientbl.Client;
import main.vo.ClientVO;

public class Test_Client {

	static Client client;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		client=new Client();
	}

	@Test
	public void testAddClient() {
		ClientVO c=new ClientVO("qyc", "980204", "2", 1, "110", "addr1", "email1",
				"postcode1", 200.0, 100.0, 50.9, "counterman1");
//		assertEquals(true,client.addClient(c));
		assertEquals(false,client.addClient(c));
		
		ClientVO c1=new ClientVO("client3", "clientid3", "2", 1, "330", "addr3", "email3",
				"postcode3", 200.0, 100.0, 50.9, "counterman3");
		client.addClient(c1);
	}

	@Test
	public void testDelClient() {
		ClientVO c=new ClientVO("client2", "clientid2", "2", 2, "220", "addr2", "email2",
				"postcode2", 200.0, 100.0, 50.9, "counterman2");
		assertEquals(true,client.addClient(c));
		assertEquals(true,client.delClient("GYS-client2"));
		assertEquals(false,client.delClient("GYS-client2"));
	}

//	@Test
	public void testModifyClient() {
		ClientVO c=client.showClient("clientid1");
		assertNotNull(c);
		assertEquals(200.0,c.getCollectTop(),0.1);
		assertEquals(100.0,c.getCollect(),0.1);
		assertEquals(50.9,c.getPay(),0.1);
		
		c.changeCollect(40.1);
		c.changeCollectTop(40.1);
		c.changePay(-50.0);
		assertEquals(true,client.modifyClient(c));
		
		c=client.showClient("clientid1");
		assertNotNull(c);
		assertEquals(240.1,c.getCollectTop(),0.1);
		assertEquals(140.1,c.getCollect(),0.1);
		assertEquals(0.9,c.getPay(),0.1);
	}

	@Test
	public void testShowClient() {
		ClientVO c=client.showClient("clientid1");
		assertNotNull(c);
		assertEquals(240.1,c.getCollectTop(),0.1);
		assertEquals(140.1,c.getCollect(),0.1);
		assertEquals(0.9,c.getPay(),0.1);
		assertEquals("110",c.getPhone());
		assertEquals("client1",c.getClientName());
	}

//	@Test
	public void testShow() {
		ArrayList<ClientVO>list=client.show();
		for(ClientVO c:list)
			System.out.println(c.getID());
	}

	@Test
	public void testSearchClient() {
		System.out.println("--------");
		ArrayList<ClientVO>list=client.searchClient(null, null, "counterman1", 0, 1);
		for(ClientVO c:list)
			System.out.println(c.getID());
		
		System.out.println("--------");
		list=client.searchClient(null, null, null, 0, 1);
		for(ClientVO c:list)
			System.out.println(c.getID());
	}

}
