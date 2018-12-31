package main.PO;

import java.io.Serializable;
import java.util.ArrayList;
//报损单
/**
 * 
 * @author kate
 *
 */
public class UnderflowReceipt extends StockReceiptPO implements Serializable{

	public UnderflowReceipt(String id, String createTime, String reviseTime, String creatorId,
			String goodsName, String goodsId, String goodsXh, int amount) {
		super(id, 12, createTime, reviseTime, creatorId, goodsName, goodsId, goodsXh, amount);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 300187852239767637L;

	
	
}
