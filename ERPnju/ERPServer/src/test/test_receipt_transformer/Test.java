package test.test_receipt_transformer;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;

import main.businesslogic.util.ReceiptTransformer;
import main.vo.*;
import main.PO.*;

public class Test {

	ReceiptTransformer trans=new ReceiptTransformer();
	@Before
	public void setUp() throws Exception {
		
	}

	@org.junit.Test
	public void testvo2po11() {
		StockOverflowReceiptVO vo=new StockOverflowReceiptVO("overflow_00001",Calendar.getInstance(),Calendar.getInstance(),"creator_001","小灯泡","00000001","A型",999);
		OverflowReceipt po=(OverflowReceipt)(trans.vo2po(vo));
		System.out.println(po.getCreateTime());
		assertEquals("overflow_00001",po.getId());
		assertEquals(999,po.getAmount());
	}

	@org.junit.Test
	public void testvo2po12() {
		StockUnderflowReceiptVO vo=new StockUnderflowReceiptVO("underflow_00001",Calendar.getInstance(),Calendar.getInstance(),"creator_003","小灯泡","00000001","B型",999);
		UnderflowReceipt po=(UnderflowReceipt)(trans.vo2po(vo));
		System.out.println(po.getCreateTime());
		assertEquals("underflow_00001",po.getId());
		assertEquals(999,po.getAmount());
		assertEquals(12,po.getReceiptType());
	}
	
	@org.junit.Test
	public void testvo2po13() {
		ArrayList<String[]> list=new ArrayList<String[]>();
		String[] s1={"灯泡1","A","20","399.0"};
		String[] s2={"灯泡2","A","10","499.0"};
		list.add(s1);
		list.add(s2);
		GiftReceipt po=new GiftReceipt("gift_00001","1998*02*04*00*00","1998*08*10*00*01","creator_001",list);
		StockGiftReceiptVO vo=(StockGiftReceiptVO)(trans.po2vo(po));
		GiftReceipt poback=(GiftReceipt)(trans.vo2po(vo));
		assertEquals(poback.getCreateTime(),"1998*02*04*00*00");
	}
	
	@org.junit.Test
	public void testvo2po14() {
		StockAlarmReceiptVO vo=new StockAlarmReceiptVO("alarm_00001",Calendar.getInstance(),Calendar.getInstance(),"id123","大灯泡","A",100,50);
		AlarmReceiptPO po=(AlarmReceiptPO)(trans.vo2po(vo));
		assertEquals(po.getAlarmNumber(),100);
	}
	
	
	@org.junit.Test
	public void testvo2po31() {
		ArrayList<SaleReceiptBothVOLineItem> list1=new ArrayList<SaleReceiptBothVOLineItem>();
		SaleReceiptBothVOLineItem i1=new SaleReceiptBothVOLineItem("0001","灯泡","A",10,4.0,"note");
		SaleReceiptBothVOLineItem i2=new SaleReceiptBothVOLineItem("0001","灯泡","A",20,8.0,"note");
		list1.add(i1);
		list1.add(i2);
		SaleReceiptVO vo=new SaleReceiptVO("sale_001",Calendar.getInstance(),Calendar.getInstance(),"creator1","client1","clerk1","stock1",1.0,20.0,"note",list1);
		SaleReceipt po=(SaleReceipt)(trans.vo2po(vo));
		System.out.println(po.getTotalAfterDiscount());
		System.out.println(po.getTotalAfterVoucher());
	}
	
	@org.junit.Test
	public void testvo2po32() {
	
	}
	
	@org.junit.Test
	public void testvo2po41() {
		ArrayList<PurchaseReceiptBothVOLineItem> list=new ArrayList<PurchaseReceiptBothVOLineItem>();
		PurchaseReceiptBothVOLineItem i1=new PurchaseReceiptBothVOLineItem("0001","手电","B",100,10,"note");
		PurchaseReceiptBothVOLineItem i2=new PurchaseReceiptBothVOLineItem("0002","手电","V",80,20,"note");
		list.add(i1);
		list.add(i2);
		PurchaseReceiptVO vo=new PurchaseReceiptVO("purchase1",Calendar.getInstance(),Calendar.getInstance(),"creator1","client1","stock1","note",list);
		PurchaseReceipt po=(PurchaseReceipt)(trans.vo2po(vo));
		System.out.println(po.getTotalValue());
	}
	
	@org.junit.Test
	public void testvo2po42() {
	
	}
	
	
	
	
	
	
	@org.junit.Test
	public void testpo2vo11() {
		StockOverflowReceiptVO vo=new StockOverflowReceiptVO("overflow_00001",Calendar.getInstance(),Calendar.getInstance(),"creator_001","小灯泡","00000001","A型",999);
		OverflowReceipt po=(OverflowReceipt)(trans.vo2po(vo));
		StockOverflowReceiptVO voback=(StockOverflowReceiptVO)(trans.po2vo(po));
		assertEquals(vo.getCreatorId(),voback.getCreatorId());
		assertEquals(vo.getGoodsName(),voback.getGoodsName());
	}
	
	@org.junit.Test
	public void testpo2vo12() {
		StockUnderflowReceiptVO vo=new StockUnderflowReceiptVO("underflow_00001",Calendar.getInstance(),Calendar.getInstance(),"creator_001","小灯泡","00000001","A型",999);
		UnderflowReceipt po=(UnderflowReceipt)(trans.vo2po(vo));
		StockUnderflowReceiptVO voback=(StockUnderflowReceiptVO)(trans.po2vo(po));
		assertEquals(vo.getCreatorId(),voback.getCreatorId());
		assertEquals(vo.getGoodsName(),voback.getGoodsName());
		assertEquals(voback.getReceiptType(),12);
	}
	
	@org.junit.Test
	public void testpo2vo13() {
		ArrayList<String[]> list=new ArrayList<String[]>();
		String[] s1={"灯泡1","A","20","399.0"};
		String[] s2={"灯泡2","A","10","499.0"};
		list.add(s1);
		list.add(s2);
		GiftReceipt po=new GiftReceipt("gift_00001","1998*02*04*00*00","1998*08*10*00*01","creator_001",list);
		StockGiftReceiptVO vo=(StockGiftReceiptVO)(trans.po2vo(po));
		assertEquals(vo.getCreatorId(),po.getCreatorId());
		assertEquals(vo.getGoodsList().get(1).getGoodsName(),"灯泡2");
	}
	
	@org.junit.Test
	public void testpo2vo14() {
		StockAlarmReceiptVO vo=new StockAlarmReceiptVO("alarm_00001",Calendar.getInstance(),Calendar.getInstance(),"id123","大灯泡","A",100,50);
		AlarmReceiptPO po=(AlarmReceiptPO)(trans.vo2po(vo));
		StockAlarmReceiptVO voback=(StockAlarmReceiptVO)(trans.po2vo(po));
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy*MM*dd*HH*mm");
		
		assertEquals(df.format(vo.getMakeTime().getTime()),df.format(voback.getMakeTime().getTime()));
	}
	
	@org.junit.Test
	public void testpo2vo31() {
		
	}
	
	@org.junit.Test
	public void testpo2vo32() {
		
	}
	
	@org.junit.Test
	public void testpo2vo41() {
		ArrayList<PurchaseReceiptBothVOLineItem> list=new ArrayList<PurchaseReceiptBothVOLineItem>();
		PurchaseReceiptBothVOLineItem i1=new PurchaseReceiptBothVOLineItem("0001","手电","B",100,10,"note");
		PurchaseReceiptBothVOLineItem i2=new PurchaseReceiptBothVOLineItem("0002","手电","V",80,20,"note");
		list.add(i1);
		list.add(i2);
		PurchaseReceiptVO vo=new PurchaseReceiptVO("purchase1",Calendar.getInstance(),Calendar.getInstance(),"creator1","client1","stock1","note",list);
		PurchaseReceipt po=(PurchaseReceipt)(trans.vo2po(vo));
		PurchaseReceiptVO vb=(PurchaseReceiptVO)(trans.po2vo(po));
		System.out.println(vb.getTotalValue());
	}
	
	@org.junit.Test
	public void testpo2vo42() {
		
	}
}
