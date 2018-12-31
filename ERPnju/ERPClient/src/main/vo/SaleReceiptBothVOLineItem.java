package main.vo;

import java.io.Serializable;

public class SaleReceiptBothVOLineItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1327848390426035760L;
	private String goodsId;//商品id
	private String goodsName;//商品名
	private String goodsXh;//商品型号
	private long goodsAmount;//商品数量
	private double goodsPrice;//商品单价
	private double subTotal;//本条目总价，自动生成
	private String note;//备注
	/**
	 * @param goodsId  商品id
	 * @param goodsName  商品名字
	 * @param goodsXh  商品型号
	 * @param goodsAmount  商品数量
	 * @param goodsPrice  商品单价
	 * @param note 
	 */
	public SaleReceiptBothVOLineItem(String goodsId, String goodsName, String goodsXh, long goodsAmount,
			double goodsPrice, String note) {
		super();
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsXh = goodsXh;
		this.goodsAmount = goodsAmount;
		this.goodsPrice = goodsPrice;
		this.note = note;
		this.subTotal=this.calculateSubTotal();
	}
	
	
	
	public double getSubTotal() {
		return subTotal;
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



	public long getGoodsAmount() {
		return goodsAmount;
	}



	public void setGoodsAmount(long goodsAmount) {
		this.goodsAmount = goodsAmount;
		this.subTotal=this.calculateSubTotal();
	}



	public double getGoodsPrice() {
		return goodsPrice;
	}



	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
		this.subTotal=this.calculateSubTotal();
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}



	private double calculateSubTotal(){
		return this.goodsAmount*this.goodsPrice;
	}
}
