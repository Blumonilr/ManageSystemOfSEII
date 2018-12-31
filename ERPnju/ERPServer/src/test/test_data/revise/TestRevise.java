package test.test_data.revise;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

import main.PO.GiftReceipt;
import main.data.revisedata.ReviseData;

public class TestRevise {
	ReviseData reviseData;
	
	public TestRevise(){
		reviseData=new ReviseData();
	}
	@Test
	public void testRevise(){
		String c1="2017*12*06";
		String c2="2017*12*07";
		ArrayList<String[]> list=new ArrayList<String[]>();
		list.add(new String[]{"Light","01","10", "50"});
		GiftReceipt r=new GiftReceipt("0011",c1,c2,"0001",list);
		assertEquals(true,reviseData.add(r));
		assertEquals("2017*12*06",reviseData.find("0011").getCreateTime());
		r.setCreateTime("2017*12*05");
		assertEquals(true,reviseData.update(r));
		assertEquals("2017*12*05",reviseData.find("0011").getCreateTime());
		assertEquals(true,reviseData.delete("0011"));
	}

	
	@Test
	public void TestNotHas(){
		reviseData.delete("0002");
		assertEquals(null,reviseData.find("0002"));
	}
}
