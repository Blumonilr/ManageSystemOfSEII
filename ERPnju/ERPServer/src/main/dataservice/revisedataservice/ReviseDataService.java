package main.dataservice.revisedataservice;

import java.util.ArrayList;

import main.PO.*;
public interface ReviseDataService {
	
	public ArrayList<ReceiptPO> finds();
	public ReceiptPO find(String id);
	public boolean update(ReceiptPO r);
	public boolean add(ReceiptPO r);
	public boolean delete(String id);
}
