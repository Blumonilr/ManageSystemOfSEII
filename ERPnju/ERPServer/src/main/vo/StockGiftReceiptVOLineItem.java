package main.vo;

import java.io.Serializable;

public class StockGiftReceiptVOLineItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7529657061271471182L;
	private String goodsName;
	private String goodsXh;
	private int amount;
	private double value;
	private String goodsId;
	/**
	 * @param goodsName  商品名
	 * @param goodsXh  商品型号
	 * @param amount  商品数量
	 * @param value   总价
	 * @param goodsId 商品编号
	 */
	public StockGiftReceiptVOLineItem(String goodsName, String goodsXh, int amount, double value,String goodsId) {
		super();
		this.goodsName = goodsName;
		this.goodsXh = goodsXh;
		this.amount = amount;
		this.value = value;
		this.goodsId=goodsId;
	}
	public String getGoodsId(){
		return goodsId;
	}
	
	public void setGoodsId(String id){
		goodsId=id;
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	
	
}
