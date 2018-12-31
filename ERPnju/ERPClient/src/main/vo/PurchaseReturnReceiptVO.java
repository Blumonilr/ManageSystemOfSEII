package main.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class PurchaseReturnReceiptVO extends PurchaseReceiptBothVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1885265361847383714L;

	public PurchaseReturnReceiptVO(String id, Calendar makeTime, Calendar reviseTime, String creatorId,
			String clientId, String stockId, String note,
			ArrayList<PurchaseReceiptBothVOLineItem> itemList) {
		super(id, 42, makeTime, reviseTime, creatorId, clientId, stockId, note, itemList);
		// TODO Auto-generated constructor stub
	}
	
}
