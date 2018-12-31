package main.dataservice.saledataservice;

import java.util.ArrayList;

import main.PO.*;

public interface SaleDataService {

	public boolean addSaleReceipt(SaleReceiptPO list);
	
	public ArrayList<SaleReceipt> findsSaleReceipt();
	
	public ArrayList<SaleReturnReceipt> findsSaleReturnReceipt();
}
