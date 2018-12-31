package main.vo;

import java.io.Serializable;
import java.util.Calendar;

public class StockReceiptVO extends ReceiptVO implements Serializable{//overflow&underflow爸爸

	/**
	 * 
	 */
	private static final long serialVersionUID = -4732121834465825045L;
	protected String goodsName;//商品名称
	protected String goodsId;//商品id
	protected String goodsXh;//商品型号
	protected int amount;//商品数量
	/**
	 * @param id
	 * @param receiptType
	 * @param makeTime
	 * @param reviseTime
	 * @param creatorId
	 * @param goodsName
	 * @param goodsId
	 * @param goodsXh
	 * @param amount
	 */
	public StockReceiptVO(String id, int receiptType, Calendar makeTime, Calendar reviseTime, String creatorId,
			String goodsName, String goodsId, String goodsXh, int amount) {
		super(id, receiptType, makeTime, reviseTime, creatorId);
		this.goodsName = goodsName;
		this.goodsId = goodsId;
		this.goodsXh = goodsXh;
		this.amount = amount;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsXh() {
		return goodsXh;
	}
	public void setGoodsXh(String goodsXh) {
		this.goodsXh = goodsXh;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
