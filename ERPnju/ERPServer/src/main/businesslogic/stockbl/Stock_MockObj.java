package main.businesslogic.stockbl;

import java.util.ArrayList;
import java.util.Calendar;
import main.vo.ReceiptVO;
import main.vo.StockRecordVO;
/**
 * 
 * @author qyc
 *
 */
public class Stock_MockObj {

	ArrayList<StockRecordVO>slist;
	ArrayList<ReceiptVO>rlist;
	
	public Stock_MockObj(){
		slist=new ArrayList<>();
		rlist=new ArrayList<>();
		
		ReceiptVO r=new ReceiptVO();
		r.setId("JHD-20171116-00001");
		r.setReceiptType(1);
		rlist.add(r);
		
		StockRecordVO s=new StockRecordVO("tomato","0002-tat");
		Calendar c=Calendar.getInstance();
		c.set(2017, 10, 16);
		s.addItem(c, "进货", 19000, 8765);
		c.set(2017, 11, 01);
		s.addItem(c, "进货", 67464, 767367);
		slist.add(s);
	}
	
	public ArrayList<StockRecordVO>stockCheck(Calendar start,Calendar end){
		return slist;
	}
	
	public ReceiptVO readReceipt(String id){
		return rlist.get(0);
	}
	
	public ArrayList<ReceiptVO>readReceipt(String type,Calendar start,Calendar end){
		return rlist;
	}

	public boolean writeReceipt(ReceiptVO obj){
		return true;
	}
}
