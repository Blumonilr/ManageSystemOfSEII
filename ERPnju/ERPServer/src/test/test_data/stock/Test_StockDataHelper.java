package test.test_data.stock;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.BeforeClass;
import org.junit.Test;

import main.PO.PurchaseReceipt;
import main.PO.ReceiptPO;
import main.PO.SaleReceipt;
import main.PO.UnderflowReceipt;
import main.businesslogic.util.ReceiptTransformer;
import main.data.stockdata.StockDataHelper;
import main.vo.ReceiptVO;
/**
 * 由于StockDataHelper已经测试过，jbs更改了单据类，故此测试用例已经无用
 * @author qyc
 *
 */
public class Test_StockDataHelper {

	static StockDataHelper sdh;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sdh=new StockDataHelper();
	}

	@Test
	public void test_writeReceipt() {
		
		ReceiptPO r=new ReceiptPO("JHD-20171127-00001", 41, "Tue Nov 28 20:01:34 CST 2017", null);
//		assertEquals(true,sdh.writeReceipt(r));
		assertEquals(false,sdh.writeReceipt(r));
	}

	@Test 
	public void test_readReceipt(){
		ArrayList<ReceiptPO> list=sdh.readReceipt();
		for(ReceiptPO r:list){
			System.out.println(r.getId());
			System.out.println(r.getCreateTime());
			System.out.println(r.getReviseTime());
		}
	}
	
	@Test 
	public void test_readReceiptById(){
		ReceiptPO r=sdh.readReceipt("XSD-20171203-00001");
		assertEquals(31,r.getReceiptType());
		assertEquals("2017*12*03*20*01",r.getCreateTime());
		
		ReceiptPO r1=sdh.readReceipt("JHTHD-20171127-00001");
		assertEquals(null,r1);
	}
	
	@Test 
	public void test_readReceiptByType(){
		ArrayList<ReceiptPO> list=sdh.readReceipt(41);
		for(ReceiptPO r:list){
			System.out.println(r.getId());
			System.out.println(r.getCreateTime());
			System.out.println(r.getReviseTime());
		}
	}
	
	@Test
	public void test_modifyReceipt(){
		ReceiptPO r=sdh.readReceipt("JHD-20171127-00001");
//		assertEquals(null,r.getReviseTime());
		r.setReviseTime("20171130");
		assertEquals(true,sdh.modifyReceipt(r));
		ReceiptPO r2=sdh.readReceipt("JHD-20171127-00001");
		assertEquals("20171130",r2.getReviseTime());
	}
	
	@Test
	public void test_addDraft(){
		PurchaseReceipt pr=new PurchaseReceipt("testadddraft", null, null, null, null, null, null, 0, null);
		assertEquals(false,sdh.addDraft(pr, "testuser"));
		assertEquals(true,sdh.addDraft(pr, "0001"));
		assertEquals(false,sdh.addDraft(pr, "0001"));
	}
	
//	@Test
	public void test_readDraftbyUserId(){
		ArrayList<ReceiptPO>actual=sdh.readDraft("user1");
		for(ReceiptPO r:actual){
			System.out.println(r.getId()+'/'+r.getReceiptType()+'/'+r.getCreateTime());
		}
	}
	
	@Test
	public void test_readDraftbyUserIdAndDraftId(){
		ReceiptPO r=sdh.readDraft("201711301020", "user1");
		ReceiptPO r1=sdh.readDraft("201711301029", "user1");
		assertNull(r1);
		assertEquals("201711301020",r.getCreateTime());
		assertEquals(11,r.getReceiptType());
	}
	
	@Test
	public void test_modifyDraft(){
		UnderflowReceipt ur=new UnderflowReceipt("BSD-20171130-00001", "20171130", null, "goods1", "id1", "xh1", 109, 76786.93);
		sdh.addDraft(ur, "user2");
		UnderflowReceipt ufr=(UnderflowReceipt) sdh.readDraft("BSD-20171130-00001", "user2");
		assertNotNull(ufr);
		ufr.setReviseTime("20171201");
		assertEquals(true,sdh.modifyDraft(ufr, "user2"));
		assertEquals(false,sdh.modifyDraft(ufr, "user1"));
		ufr=(UnderflowReceipt) sdh.readDraft("BSD-20171130-00001", "user2");
		assertEquals("20171201",ufr.getReviseTime());
	}
	
	@Test
	public void test_delDraft(){
		ReceiptPO r=new ReceiptPO("201711301709", 32, "201711301709", null);
		assertEquals(true,sdh.addDraft(r, "user3"));
		assertEquals(true,sdh.delDraft("201711301709", "user3"));
		assertEquals(false,sdh.delDraft("201711301709", "user3"));
	}
	
	@Test
	public void test_addReceipt(){
		Calendar c1=new GregorianCalendar();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		ArrayList<String[]>list=new ArrayList<String[]>();
		String[]token={"digital device20171203202452","iphone x","256g","2","9388","18888",""};
		list.add(token);
		SaleReceipt receipt=new SaleReceipt("XSD-20171219-00001", df.format(c1.getTime()),
				df.format(c1.getTime()), "ccreator", "client", "clerk", "stock", 0, 0, 0, 0, 0, null, list, null, 0, 0, 0);
//		assertEquals(true,sdh.writeReceipt(receipt));
		assertEquals(false,sdh.writeReceipt(receipt));
		
	}
	
	@Test
	public void test_addReceipt2(){
		Calendar c1=new GregorianCalendar();
		c1.set(Calendar.DAY_OF_MONTH,19);
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		ArrayList<String[]>list=new ArrayList<String[]>();
		String[]token={"digital device20171203202452","iphone 8","256g","2","9388","18888",""};
		list.add(token);
		SaleReceipt receipt=new SaleReceipt("XSD-20171219-00002", df.format(c1.getTime()),
				df.format(c1.getTime()), "ccreator", "client", "clerk", "stock", 0, 0, 0, 0, 0, null, list, null, 0, 0, 0);
//		assertEquals(true,sdh.writeReceipt(receipt));
		assertEquals(false,sdh.writeReceipt(receipt));
		
	}
	
	@Test
	public void test_readReceipt1(){
		ReceiptPO receipt=sdh.readReceipt("XSD-20171219-00001");
		assertNotNull(receipt);
		System.out.println(receipt.getReviseTime());
	}
	
	@Test
	public void test_po2vo(){
		
	}
}
