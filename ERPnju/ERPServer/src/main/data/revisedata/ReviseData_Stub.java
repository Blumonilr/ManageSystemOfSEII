package main.data.revisedata;

import java.util.ArrayList;

import main.PO.ReceiptPO;
import main.dataservice.revisedataservice.ReviseDataService;

public class ReviseData_Stub implements ReviseDataService {

	ArrayList<ReceiptPO> receiptList;
	
	public ReviseData_Stub(ArrayList<ReceiptPO> receiptList) {
		super();
		this.receiptList = receiptList;
	}

	@Override
	public ArrayList<ReceiptPO> finds() {//暂不明确具体查找方法，先不实现
		// TODO Auto-generated method stub
		return receiptList;
	}

	@Override
	public ReceiptPO find(String id) {
		// TODO Auto-generated method stub
		for(ReceiptPO r:receiptList){
			if(r.getId().equals(id)){
				return r;
			}
		}
		return null;
	}

	@Override
	public boolean update(ReceiptPO r) {
		// TODO Auto-generated method stub
		for(ReceiptPO receipt:receiptList){
			if(receipt.getId().equals(r.getId())){
				receiptList.remove(receipt);
				receiptList.add(r);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean add(ReceiptPO r) {
		// TODO 自动生成的方法存根
		return false;
	}

}