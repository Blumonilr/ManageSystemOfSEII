package main.vo;

import main.PO.FinanceCashReceiptPO;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	public FinanceCashReceiptVO(FinanceCashReceiptPO po){
		//初始化父类成员变量
		id=po.getId();
		receiptType=po.getReceiptType();
		makeTime=po.getCreateTimebyCalendar();
		if(po.getReviseTime() == null)
            reviseTime=null;
        else
            reviseTime=po.getReviseTimebyCalendar();
		//初始化子类成员变量
		creatorId=po.getCreatorId();
		accountName=po.getAccountName();
		itemList=new ArrayList<>();
		ArrayList<String[]> list=po.getList();
		String[] temp;
		for(int i=0;i<list.size();i++){
			temp=list.get(i);
			itemList.add(new FinanceCashReceiptLineItem(temp[0],Double.parseDouble(temp[1]),temp[2]));
		}
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
