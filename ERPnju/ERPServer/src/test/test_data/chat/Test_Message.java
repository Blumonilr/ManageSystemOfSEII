package test.test_data.chat;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import main.data.chatdata.ChatData;
import main.dataservice.chatdataservice.ChatDataService;

public class Test_Message {

	static ChatDataService cds;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cds=new ChatData();
	}

	@Test
	public void test_saveMsg() {
//		assertEquals(true,cds.saveMsg("0001", "test_add"));
	}
	
	@Test
	public void test_readMsg(){
		ArrayList<String>list=null;
		list=cds.readMsg("0001");
		assertNotNull(list);
		for(String s:list)
			System.out.println(s);
		list=cds.readMsg("hjhjkh");
		assertEquals(0,list.size());
		list=cds.readMsg(null);
		assertEquals(0,list.size());
	}

}
