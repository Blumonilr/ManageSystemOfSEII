package test.test_bl.stock;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.BeforeClass;
import org.junit.Test;
import main.businesslogic.stockbl.Stock;
import main.vo.ReceiptVO;
import main.vo.SaleReceiptBothVOLineItem;
import main.vo.SaleReceiptVO;
import main.vo.StockRecordVO;

public class Test_bl_Stock {

	static Stock stock;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		stock=new Stock();
	}

	@Test
	public void testDelDraft() {
		Calendar c=new GregorianCalendar();
		ArrayList<SaleReceiptBothVOLineItem>list=new ArrayList<>();
		SaleReceiptVO sr=new SaleReceiptVO("XSD-20171203-00001", c, c, "creator1", 
				"client1", "clerk1", "stock1", 0.8, 0, "test delDraft", list);
		assertEquals(true,stock.addDraft(sr, "user1"));
		assertEquals(true,stock.delDraft("XSD-20171203-00001", "user1"));
		assertEquals(false,stock.delDraft("XSD-20171203-00001", "user1"));
	}

	@Test
	public void testStockCheck() {
		Calendar start=new GregorianCalendar();
		Calendar end=new GregorianCalendar();
		start.set(2016, 11,3,0,0);
		end.set(2017, 11, 4,0,0);
		StockRecordVO sr=stock.stockCheck("0001", start, end);
		assertEquals(30,sr.getTotal());
		while(sr.hasNext()){
			System.out.println(sr.next());
		}
	}

	@Test
	public void testReadReceiptIntCalendarCalendar() {
		Calendar start=new GregorianCalendar();
		Calendar end=new GregorianCalendar();
		start.set(2017, 11,3,0,0);
		end.set(2017, 11, 4,0,0);
		ArrayList<ReceiptVO>list=stock.readReceipt(31, start, end);
		for(ReceiptVO r:list)
			System.out.println(r.getId()+'/'+r.getCreatorId()+'/'+r.getReceiptType()+'/'+r.getMakeTime());
	}

	@Test
	public void testReadReceiptString() {
		SaleReceiptVO sr=(SaleReceiptVO) stock.readReceipt("XSD-20171203-00001");
		assertNotNull(sr);
		assertEquals("creator1",sr.getCreatorId());
	}

	@Test
	public void testWriteReceipt() {
		Calendar c=new GregorianCalendar();
		ArrayList<SaleReceiptBothVOLineItem>list=new ArrayList<>();
		SaleReceiptVO sr=new SaleReceiptVO("XSD-20171203-00001", c, c, "creator1", 
				"client1", "clerk1", "stock1", 0.8, 0, "test delDraft", list);
//		assertEquals(true,stock.writeReceipt(sr));
		assertEquals(false,stock.writeReceipt(sr));
		SaleReceiptVO sr1=new SaleReceiptVO("XSD-20171203-00003", c, c, "creator2", 
				"client2", "clerk2", "stock1", 0.88, 0, "test addDraft", list);
		stock.writeReceipt(sr1);
	}

	@Test
	public void testReadReceipt() {
		ArrayList<ReceiptVO>list=stock.readReceipt();
		for(ReceiptVO r:list)
			System.out.println(r.getId()+'/'+r.getCreatorId()+'/'+r.getReceiptType()+'/'+r.getMakeTime());
	}

	@Test
	public void testModifyReceipt() {
		SaleReceiptVO sr=(SaleReceiptVO) stock.readReceipt("XSD-20171203-00003");
		assertNotNull(sr);
		
		ArrayList<SaleReceiptBothVOLineItem>list=new ArrayList<>();
		SaleReceiptBothVOLineItem sli=new SaleReceiptBothVOLineItem("0001", "goods1", 
				"xh1", 10, 2.9, "test sale list");
		list.add(sli);
		sr.setItemList(list);
//		assertEquals("client2",sr.getClientId());
		sr.setClientId("client3");
		assertEquals(true,stock.modifyReceipt(sr));
		sr=(SaleReceiptVO) stock.readReceipt("XSD-20171203-00003");
		assertNotNull(sr);
		assertEquals("client3",sr.getClientId());
		
		sr=(SaleReceiptVO) stock.readReceipt("XSD-20171203-00001");
		list=new ArrayList<>();
		sli=new SaleReceiptBothVOLineItem("0001", "goods1", 
				"xh1", 20, 2.9, "test sale list");
		list.add(sli);
		sr.setItemList(list);
		assertEquals(true,stock.modifyReceipt(sr));
	}

	@Test
	public void testAddDraft() {
		Calendar c=new GregorianCalendar();
		ArrayList<SaleReceiptBothVOLineItem>list=new ArrayList<>();
		SaleReceiptVO sr=new SaleReceiptVO("XSD-20171203-00002", c, c, "creator2", 
				"client2", "clerk2", "stock1", 0.88, 0, "test addDraft", list);
		SaleReceiptVO sr1=new SaleReceiptVO("XSD-20171203-00003", c, c, "creator2", 
				"client2", "clerk2", "stock1", 0.88, 0, "test addDraft", list);
//		assertEquals(true,stock.addDraft(sr, "user1"));
		assertEquals(false,stock.addDraft(sr, "user1"));
		stock.addDraft(sr1, "user1");
	}

	@Test
	public void testModifyDraft() {
		SaleReceiptVO sr=(SaleReceiptVO)stock.readDraft("XSD-20171203-00002", "user1");
		assertNotNull(sr);
//		assertEquals("stock1",sr.getStockId());
		sr.setStockId("stock2");
		assertEquals(true,stock.modifyDraft(sr, "user1"));
		sr=(SaleReceiptVO)stock.readDraft("XSD-20171203-00002", "user1");
		assertNotNull(sr);
		assertEquals("stock2",sr.getStockId());
	}

	@Test
	public void testReadDraftStringString() {
		SaleReceiptVO sr=(SaleReceiptVO)stock.readDraft("XSD-20171203-00002", "user1");
		assertNotNull(sr);
		assertEquals(0.0,sr.getTotalAfterDiscount(),0.001);
		assertEquals(0.88,sr.getDiscount(),0.001);
	}

	@Test
	public void testReadDraftString() {
		ArrayList<ReceiptVO>list=stock.readDraft("user1");
		for(ReceiptVO r:list)
			System.out.println(r.getId()+'/'+r.getCreatorId()+'/'+r.getReceiptType()+'/'+r.getMakeTime());
		
	}

}
