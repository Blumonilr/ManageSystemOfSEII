package main.data.saledata;

import java.util.ArrayList;

import main.PO.SaleReceipt;
import main.PO.SaleReceiptPO;
import main.PO.SaleReturnReceipt;
import main.dataservice.saledataservice.SaleDataService;
//两个find方法返回值调整一下
public class SaleData_Stub implements SaleDataService {

	
	ArrayList<SaleReceiptPO> receiptList;
	
	public SaleData_Stub(ArrayList<SaleReceiptPO> receiptList) {
		super();
		this.receiptList = receiptList;
	}

	@Override
	public boolean addSaleReceipt(SaleReceiptPO list) {
		// TODO Auto-generated method stub
		for(SaleReceiptPO r:receiptList){
			if(r.getId().equals(list.getId())){
				return false;
			}
		}
		receiptList.add(list);
		return true;
	}

	@Override
	public ArrayList<SaleReceipt> findsSaleReceipt() {
		// TODO Auto-generated method stub
		ArrayList<SaleReceipt> result=new ArrayList<SaleReceipt>();
		for(SaleReceiptPO r:receiptList){
			if(r.getType()==1){
				result.add((SaleReceipt)r);
			}
		}
		return result;
	}

	@Override
	public ArrayList<SaleReturnReceipt> findsSaleReturnReceipt() {
		// TODO Auto-generated method stub
		ArrayList<SaleReturnReceipt> result=new ArrayList<SaleReturnReceipt>();
		for(SaleReceiptPO r:receiptList){
			if(r.getType()==2){
				result.add((SaleReturnReceipt)r);
			}
		}
		return result;
	}

}
