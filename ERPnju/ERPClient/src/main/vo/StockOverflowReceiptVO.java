package main.vo;

import java.io.Serializable;
import java.util.Calendar;

public class StockOverflowReceiptVO extends StockReceiptVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2236697406692510939L;

	public StockOverflowReceiptVO(String id, Calendar makeTime, Calendar reviseTime, String creatorId,
			String goodsName, String goodsId, String goodsXh, int amount) {
		super(id, 11, makeTime, reviseTime, creatorId, goodsName, goodsId, goodsXh, amount);
		// TODO Auto-generated constructor stub
	}
	
	
}
