package main.PO;

import java.io.Serializable;

public class AlarmReceiptPO extends ReceiptPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4356428645080202612L;
	private String goodsId;
	private String goodsName;
	private String goodsXh;
	private long alarmNumber;
	private long currentNumber;
	/**
	 * @param goodsId  商品id
	 * @param goodsName  商品名称
	 * @param goodsXh  商品型号
	 * @param alarmNumber  报警数量
	 * @param currentNumber  现有库存
	 */
	public AlarmReceiptPO(String id, String createTime, String reviseTime,String goodsId,
			String goodsName, String goodsXh, long alarmNumber, long currentNumber) {
		super(id,14,createTime, reviseTime,null);
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsXh = goodsXh;
		this.alarmNumber = alarmNumber;
		this.currentNumber = currentNumber;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public String getGoodsXh() {
		return goodsXh;
	}
	public long getAlarmNumber() {
		return alarmNumber;
	}
	public long getCurrentNumber() {
		return currentNumber;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public void setGoodsXh(String goodsXh) {
		this.goodsXh = goodsXh;
	}
	public void setAlarmNumber(long alarmNumber) {
		this.alarmNumber = alarmNumber;
	}
	public void setCurrentNumber(long currentNumber) {
		this.currentNumber = currentNumber;
	}
	
}
