package main.businesslogic.purchasebl;

import java.util.ArrayList;
import java.util.Calendar;

import main.businesslogic.stockbl.ReceiptInfo;
import main.vo.ReceiptVO;

public class Mock_ReceiptInfo implements ReceiptInfo {

	ArrayList<ReceiptVO> receiptList;
	/**
	 * @param receiptList
	 */
	public Mock_ReceiptInfo(ArrayList<ReceiptVO> receiptList) {
		super();
		this.receiptList = receiptList;
	}

	@Override
	public ArrayList<ReceiptVO> readReceipt(int type, Calendar start, Calendar end) {
		// TODO Auto-generated method stub
		ArrayList<ReceiptVO> list=new ArrayList<ReceiptVO>();
		for(ReceiptVO r: receiptList){
			if(r.getReceiptType()==type){
				if(r.getMakeTime().after(start)&&r.getMakeTime().before(end)){
					list.add(r);
				}
			}
		}
		return list;
	}

	@Override
	public ReceiptVO readReceipt(String id) {
		// TODO Auto-generated method stub
		for(ReceiptVO r: receiptList){
			if(r.getId().equals(id)){
				return r;
			}
		}
		return null;
	}

	@Override
	public boolean writeReceipt(ReceiptVO obj) {
		// TODO Auto-generated method stub
		receiptList.add(obj);
		return true;
	}

	@Override
	public ArrayList<ReceiptVO> readReceipt() {
		// TODO Auto-generated method stub
		return receiptList;
	}

	@Override
	public boolean delReceipt(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyReceipt(ReceiptVO obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
