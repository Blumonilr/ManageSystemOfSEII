package main.data.purchasedata;

import java.util.ArrayList;

import main.PO.PurchaseReceipt;
import main.PO.PurchaseReceiptPO;
import main.PO.PurchaseReturnReceipt;
import main.dataservice.purchasedataservice.PurchaseDataService;

public class PurchaseData_Stub implements PurchaseDataService {

	ArrayList<PurchaseReceiptPO> receiptList;
	
	public PurchaseData_Stub(ArrayList<PurchaseReceiptPO> receiptList) {
		super();
		this.receiptList = receiptList;
	}

	@Override
	public boolean addReceipt(PurchaseReceiptPO receipt) {
		// TODO Auto-generated method stub
		for(PurchaseReceiptPO r:receiptList){
			if(r.getId().equals(receipt.getId())){
				return false;
			}
		}
		receiptList.add(receipt);
		return true;
	}

	@Override
	public ArrayList<PurchaseReceipt> findsPurchaseReceipt() {
		// TODO Auto-generated method stub
		ArrayList<PurchaseReceipt> result=new ArrayList<PurchaseReceipt>();
		for(PurchaseReceiptPO r:receiptList){
			if(r.getType()==1){
				result.add((PurchaseReceipt)r);
			}
		}
		return result;
	}

	@Override
	public ArrayList<PurchaseReturnReceipt> findsPurchaseReturnReceipt(){
		// TODO Auto-generated method stub
		ArrayList<PurchaseReturnReceipt> result=new ArrayList<PurchaseReturnReceipt>();
		for(PurchaseReceiptPO r:receiptList){
			if(r.getType()==2){
				result.add((PurchaseReturnReceipt)r);
			}
		}
		return result;
	}

}
