package main.PO;

import java.io.Serializable;
import java.util.ArrayList;
//进货退货单
public class PurchaseReturnReceipt extends PurchaseReceiptPO implements Serializable{

	public PurchaseReturnReceipt(String id,  String createTime, String reviseTime, String creatorId,
			String clientId, String stockId, String note, double totalValue, ArrayList<String[]> goodsList) {
		super(id, 42, createTime, reviseTime, creatorId, clientId, stockId, note, totalValue, goodsList);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6340870651691715051L;

	
}
