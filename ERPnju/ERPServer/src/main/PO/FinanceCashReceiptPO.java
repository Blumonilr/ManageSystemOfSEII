package main.PO;

import main.vo.FinanceCashReceiptLineItem;
import main.vo.FinanceCashReceiptVO;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

//现金费用单
public class FinanceCashReceiptPO extends FinanceReceiptPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5695529427501971119L;
	private String accountName;
	private double totalAmount;
	private ArrayList<String[]>list;//条目结构：条目名 、金额、备注
	
	public FinanceCashReceiptPO(String id, String createTime,String reviseTime,String creatorId, String accountName, double totalAmount, ArrayList<String[]> list) {
		super(id, 23, createTime,reviseTime,creatorId);
		this.accountName = accountName;
		this.totalAmount = totalAmount;
		this.list = list;
	}

	public FinanceCashReceiptPO(FinanceCashReceiptVO vo){
		//初始化父类成员变量
		id=vo.getId();
		receiptType=vo.getReceiptType();
		createTime=vo.getStringMakeTime();
		if(vo.getReviseTime() == null)
			reviseTime=null;
		else
			reviseTime=vo.getStringReviseTime();
		creatorId=vo.getCreatorId();
		//初始化子类
		accountName = vo.getAccountName();
		totalAmount = vo.getTotalAmount();
		list=new ArrayList<>();
		ArrayList<FinanceCashReceiptLineItem> list=vo.getItemList();
		for(int i=0;i<list.size();i++){
			this.list.add(new String[]{list.get(i).getItemName(),Double.toString(list.get(i).getAmount()),list.get(i).getNote()});
		}
	}
	


	public String getBankAccount() {
		return accountName;
	}
	public void setBankAccount(String accountName) {
		this.accountName = accountName;
	}
	public ArrayList<String[]> getList() {
		return list;
	}
	public void setList(ArrayList<String[]> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}
