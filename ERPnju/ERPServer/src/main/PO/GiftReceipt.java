package main.PO;

import java.io.Serializable;
import java.util.ArrayList;
//赠送单
public class GiftReceipt extends ReceiptPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1986224984708099660L;
	ArrayList<String[]> goodsList;//商品信息列表(商品名、型号、数量、总价)


	/**
	 * @param id
	 * @param receiptType
	 * @param createTime
	 * @param reviseTime
	 * @param creatorId
	 * @param goodsList
	 */
	public GiftReceipt(String id,  String createTime, String reviseTime, String creatorId,
			ArrayList<String[]> goodsList) {
		super(id, 13, createTime, reviseTime, creatorId);
		this.goodsList = goodsList;
	}

	public ArrayList<String[]> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(ArrayList<String[]> goodsList) {
		this.goodsList = goodsList;
	}
	
	
}
