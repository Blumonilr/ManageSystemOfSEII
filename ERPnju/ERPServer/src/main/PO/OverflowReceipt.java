package main.PO;

import java.io.Serializable;

//报溢单
public class OverflowReceipt extends StockReceiptPO implements Serializable{

	public OverflowReceipt(String id,  String createTime, String reviseTime, String creatorId,
			String goodsName, String goodsId, String goodsXh, int amount) {
		super(id,11, createTime, reviseTime, creatorId, goodsName, goodsId, goodsXh, amount);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2338492242310190043L;
	
	
	
}
