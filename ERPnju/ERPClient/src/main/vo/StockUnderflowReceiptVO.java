package main.vo;

import java.io.Serializable;
import java.util.Calendar;

public class StockUnderflowReceiptVO extends StockReceiptVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4971767837733147775L;

	public StockUnderflowReceiptVO(String id,  Calendar makeTime, Calendar reviseTime, String creatorId,
			String goodsName, String goodsId, String goodsXh, int amount) {
		super(id, 12, makeTime, reviseTime, creatorId, goodsName, goodsId, goodsXh, amount);
		// TODO Auto-generated constructor stub
	}
	
}
