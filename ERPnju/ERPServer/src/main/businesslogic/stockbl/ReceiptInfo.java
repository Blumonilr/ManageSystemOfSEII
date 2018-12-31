package main.businesslogic.stockbl;

import java.util.ArrayList;
import java.util.Calendar;
import main.vo.ReceiptVO;

/**
 * 还应有写单据草稿的接口！
 * @author 17678
 *
 */
public interface ReceiptInfo {

	public ArrayList<ReceiptVO>readReceipt(int type,Calendar start,Calendar end);
	public ReceiptVO readReceipt(String id);
	public boolean writeReceipt(ReceiptVO obj);
	public ArrayList<ReceiptVO> readReceipt();
	public boolean modifyReceipt(ReceiptVO obj);
	public boolean addDraft(ReceiptVO obj,String userId);
	public boolean delDraft(String draftId,String userId);
	public boolean modifyDraft(ReceiptVO obj,String userId);
	public ReceiptVO readDraft(String draftId,String userId);
	public ArrayList<ReceiptVO>readDraft(String userId);
	
}
