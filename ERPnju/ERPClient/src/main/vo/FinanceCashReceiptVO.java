package main.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
*@Author:王一博
*@Description:
*@Date:2017/11/15 21:13
*/
public class FinanceCashReceiptVO extends FinanceReceiptVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7713832681645889813L;
	private String accountName;//银行账户名
	private double totalAmount;//总额
	private ArrayList<FinanceCashReceiptLineItem> itemList;


	public FinanceCashReceiptVO() {//默认构造函数
		makeTime=Calendar.getInstance();
		receiptType=23;
		reviseTime=null;
	}

	/**
	 * @param creatorId  操作人员id
	 * @param accountName 账户名
	 * @param itemList    条目清单
	 */
	public FinanceCashReceiptVO(String creatorId, String accountName,
								ArrayList<FinanceCashReceiptLineItem> itemList) {
		this();
		this.creatorId = creatorId;
		this.accountName = accountName;
		this.itemList = itemList;
		calculateTotal();
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public ArrayList<FinanceCashReceiptLineItem> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<FinanceCashReceiptLineItem> itemList) {
		this.itemList = itemList;
		calculateTotal();
	}

	private void calculateTotal() {
		totalAmount = 0;
		for (int i = 0; i < itemList.size(); i++) {
			totalAmount += itemList.get(i).getAmount();
		}
	}
}
