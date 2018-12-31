package main.vo;

import java.io.Serializable;

/**
*@Author:王一博
*@Description:
*@Date:2017/11/15 21:14
*/
public class FinanceCashReceiptLineItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8213187607977350050L;
	private String itemName;
	private String note;
	private double amount;
	/**
	 * @param itemName  条目名
	 * @param note  备注
	 * @param amount  金额
	 */
	public FinanceCashReceiptLineItem(String itemName,double amount,String note) {
		super();
		this.itemName = itemName;
		this.note = note;
		this.amount = amount;
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
 