package main.PO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//库存类单据(overflow/underflow)爸爸
public class StockReceiptPO extends ReceiptPO implements Serializable{
	
	private static final long serialVersionUID = 6331944557592766918L;
	
	protected String goodsName;//商品名称
	protected String goodsId;//商品id
	protected String goodsXh;//商品型号
	protected int amount;//商品数量
	/**
	 * @param id
	 * @param receiptType
	 * @param createTime
	 * @param reviseTime
	 * @param creatorId
	 * @param goodsName
	 * @param goodsId
	 * @param goodsXh
	 * @param amount
	 */
	public StockReceiptPO(String id, int receiptType, String createTime, String reviseTime, String creatorId,
			String goodsName, String goodsId, String goodsXh, int amount) {
		super(id, receiptType, createTime, reviseTime, creatorId);
		this.goodsName = goodsName;
		this.goodsId = goodsId;
		this.goodsXh = goodsXh;
		this.amount = amount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public String getGoodsXh() {
		return goodsXh;
	}
	public int getAmount() {
		return amount;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public void setGoodsXh(String goodsXh) {
		this.goodsXh = goodsXh;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
