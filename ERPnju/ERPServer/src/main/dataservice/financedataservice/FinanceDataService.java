package main.dataservice.financedataservice;

import main.PO.*;

public interface FinanceDataService {

	public boolean addFinanceReceipt(FinanceReceiptPO list);
	
	public FinanceReceiptPO find(String info);
}
