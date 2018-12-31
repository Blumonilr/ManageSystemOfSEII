package main.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * 
 * @author qyc
 * 17/11/08
 * 此类用于bl层向ui层传递库存记录，一条库存记录只应记录一种商品
 * 
 *                 已测试！
 */
public class StockRecordVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6033908314866765720L;
	private String time,receiptId,type;
	private long num;//商品数量
	private double price;//总价
	
	public String getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		this.time = df.format(time.getTime());
	}

	public String getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setNum(long num) {
		this.num = num;
	}

	
	public long getNum() {
		return num;
	}

	public StockRecordVO(Calendar time, String receiptId, String type, long num,double price) {
		super();
		setTime(time);
		this.receiptId = receiptId;
		this.type = type;
		this.num = num;
		this.price=price;
	}
	
	public double getPrice(){		
		return price;
	}
}
