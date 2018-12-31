package main.businesslogic.stockbl;

import java.util.ArrayList;
import java.util.Calendar;

import main.vo.PurchaseReceiptBothVOLineItem;
import main.vo.PurchaseReceiptVO;
import main.vo.ReceiptVO;
/**
 * w
 * @author qyc
 *
 */
public class ReceiptInfo_MockObj implements ReceiptInfo {

	ArrayList<ReceiptVO>list;
	
	public ReceiptInfo_MockObj(){
		list=new ArrayList<>();
		ArrayList<PurchaseReceiptBothVOLineItem> goodsList=new ArrayList<>();
		PurchaseReceiptBothVOLineItem pr1=new PurchaseReceiptBothVOLineItem("0001", "iphone X", "256g dark grey", 100, 878800, "");
		PurchaseReceiptBothVOLineItem pr2=new PurchaseReceiptBothVOLineItem("0002", "iphone 8plus", "128g silver", 150, 1093200, "");
		goodsList.add(pr1);
		goodsList.add(pr2);
		PurchaseReceiptVO p1=new PurchaseReceiptVO("GYS-0001","CK-01","CZY-01","第一批iphone X",goodsList);
		list.add(p1);
	}
	@Override
	public ArrayList<ReceiptVO> readReceipt(String type, Calendar start, Calendar end) {
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public ReceiptVO readReceipt(String id) {
		// TODO Auto-generated method stub
		return list.get(0);
	}

	@Override
	public boolean writeReceipt(ReceiptVO obj) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public ArrayList<ReceiptVO> readReceipt() {
		// TODO Auto-generated method stub
		return list;
	}
	@Override
	public boolean delReceipt(String id) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean modifyReceipt(ReceiptVO obj) {
		// TODO Auto-generated method stub
		return true;
	}

}
