package main.PO;

import java.io.Serializable;
import java.util.ArrayList;

//进货单
public class PurchaseReceipt extends PurchaseReceiptPO implements Serializable{

	public PurchaseReceipt(String id, String createTime, String reviseTime, String creatorId,
			String clientId, String stockId, String note, double totalValue, ArrayList<String[]> goodsList) {
		super(id,41, createTime, reviseTime, creatorId, clientId, stockId, note, totalValue, goodsList);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6438779598810238770L;

	
}
