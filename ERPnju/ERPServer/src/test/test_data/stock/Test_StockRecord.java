package test.test_data.stock;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import main.vo.StockRecordVO;

public class Test_StockRecord {

	StockRecordVO sr;
	
	void setup(){
		sr=new StockRecordVO("iphone x","ip1661");
		Calendar d1=Calendar.getInstance();
		d1.set(2017, 10, 14);
		sr.addItem(d1, "销售", -15, 323412.99);
		d1.set(2017, 10, 18);
		sr.addItem(d1, "进货", 50, 699889.76);
	}
	
	
	@Test
	public void test_getTotal() {
		setup();
		assertEquals(35,sr.getTotal());
	}
	
	@Test
	public void test_print_lineItem(){
		setup();
		while(sr.hasNext())
			System.out.println(sr.next());
	}

}
