package main.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class PurchaseReceiptVO extends PurchaseReceiptBothVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4309688490059507535L;

	public PurchaseReceiptVO(String id,Calendar makeTime, Calendar reviseTime, String creatorId,
			String clientId, String stockId, String note, 
			ArrayList<PurchaseReceiptBothVOLineItem> itemList) {
		super(id, 41, makeTime, reviseTime, creatorId, clientId, stockId, note, itemList);
		// TODO Auto-generated constructor stub
	}
	
}
