package main.vo;

import java.io.Serializable;

public class PurchaseReceiptBothVOLineItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6021146888927451663L;
	private String goodsId;//商品编号
	private String goodsName;//商品名称
	private String goodsXh;//商品型号
	private int goodsAmount;//数量
	private double goodsPrice;//单价 默认为商品信息中的进价
	private double subtotal;//金额
	private String note;//备注
	/**
	 * @param goodsId  商品编号
	 * @param goodsName  商品名称
 	 * @param goodsXh  商品型号
	 * @param goodsAmount  数量
	 * @param goodsPrice  单价
	 * @param note  备注
	 */
	public PurchaseReceiptBothVOLineItem(String goodsId, String goodsName, String goodsXh, int goodsAmount,
			double goodsPrice, String note) {
		super();
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsXh = goodsXh;
		this.goodsAmount = goodsAmount;
		this.goodsPrice = goodsPrice;
		this.note = note;
		
		this.subtotal=this.calculateSubTotal();
	}
	
	private double calculateSubTotal(){
		return this.goodsAmount*this.goodsPrice;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsXh() {
		return goodsXh;
	}

	public void setGoodsXh(String goodsXh) {
		this.goodsXh = goodsXh;
	}

	public int getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(int goodsAmount) {
		this.goodsAmount = goodsAmount;
		
		this.subtotal=this.calculateSubTotal();
	}

	public double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
		
		this.subtotal=this.calculateSubTotal();
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public double getSubtotal() {
		return subtotal;
	}

}
