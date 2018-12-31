package main.businesslogic.revisebl;

import java.util.ArrayList;

import main.vo.ReceiptVO;

public interface ReviseInfo {

	public boolean insertReceipt(ReceiptVO obj);
	public ReceiptVO showReceipt(String id);
	public ArrayList<ReceiptVO> showAllReceipt(String userId);
	
	
}
