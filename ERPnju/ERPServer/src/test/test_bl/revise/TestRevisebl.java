package test.test_bl.revise;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.*;

import main.PO.ReceiptPO;
import main.businesslogic.revisebl.Revise;
import main.businesslogic.stockbl.Stock;
import main.rmi.RemoteHelper;
import main.vo.FinanceCashReceiptVO;
import main.vo.ReceiptVO;
import main.vo.StockGiftReceiptVO;
import main.vo.StockGiftReceiptVOLineItem;

public class TestRevisebl {
	static Revise re;
	static Stock st;
	
	@BeforeClass
	public static void setUpTest(){
		re=new Revise();
		st=new Stock();
	}
	
//	@Test
//	public void Test(){
//		Calendar c1 = new GregorianCalendar(); c1.set(2017, 12,12,0,0);
//		ArrayList<StockGiftReceiptVOLineItem> list=new ArrayList<StockGiftReceiptVOLineItem>();
//		StockGiftReceiptVOLineItem i1=new StockGiftReceiptVOLineItem("灯", "ty01", 1,10.0);
//		StockGiftReceiptVOLineItem i2=new StockGiftReceiptVOLineItem("人","ty666",1,666);
//		list.add(i1); list.add(i2);
//		re.delete("0001");
//		StockGiftReceiptVO r=new StockGiftReceiptVO("0001", c1, null, "1111", list);
//		assertEquals(true,re.insertReceipt(r));
//		assertEquals(false,re.insertReceipt(r));
//	}
	
//	@Test
//	public void test2(){
//		Calendar c1 =Calendar.getInstance();
//		ArrayList<StockGiftReceiptVOLineItem> list=new ArrayList<StockGiftReceiptVOLineItem>();
//		StockGiftReceiptVOLineItem i1=new StockGiftReceiptVOLineItem("灯", "ty01", 1,10.0);
//		StockGiftReceiptVOLineItem i2=new StockGiftReceiptVOLineItem("人","ty666",1,666);
//		list.add(i1); list.add(i2);
//		StockGiftReceiptVO rvo=new StockGiftReceiptVO("0001", c1, null, "1111", list);
//		rvo.setCreatorId("0011");
//		re.insertReceipt(rvo);
//		ArrayList<ReceiptVO> rlist=re.showAllReceipt("0011");
//		for(ReceiptVO r:rlist){
//			System.out.println(r.getId());
//		} 
//	}
	
}