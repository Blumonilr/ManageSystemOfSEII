package main.dataservice.purchasedataservice;

import java.util.ArrayList;

import main.PO.*;

public interface PurchaseDataService {

	public boolean addReceipt(PurchaseReceiptPO receipt);
	
	public ArrayList<PurchaseReceipt> findsPurchaseReceipt();
	
	public ArrayList<PurchaseReturnReceipt> findsPurchaseReturnReceipt();
}
