package main.vo;

import java.io.Serializable;
import java.util.Calendar;

public class StockAlarmReceiptVO extends ReceiptVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 865957537996008377L;
	private String goodsId;
	private String goodsName;
	private String goodsXh;
	private long alarmNumber;
	private long currentNumber;
	
	
/**
	 * @param id
	 * @param receiptType
	 * @param makeTime
	 * @param reviseTime
	 * @param creatorId
	 * @param goodsId
	 * @param goodsName
	 * @param goodsXh
	 * @param alarmNumber
	 * @param currentNumber
	 */
	public StockAlarmReceiptVO(String id, Calendar makeTime, Calendar reviseTime,
			String goodsId, String goodsName, String goodsXh, long alarmNumber, long currentNumber) {
		super(id, 14, makeTime, reviseTime, null);//	报警单由系统生成！！！
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
	public void setAlarmNumber(int alarmNumber) {
		this.alarmNumber = alarmNumber;
	}
	public void setCurrentNumber(int currentNumber) {
		this.currentNumber = currentNumber;
	}
	
	
	
}
