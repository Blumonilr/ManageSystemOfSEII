package main.PO;

import main.vo.FinanceActTransLineItem;
import main.vo.FinanceCollectReceiptVO;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

//收款单
public class FinanceCollectReceiptPO extends FinanceReceiptPO implements Serializable{



	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6297781876563809355L;
	private String client;
	private double totalAmount;
	private ArrayList<String[]>list;//条目结构：银行账户、转账金额、备注
	
	public FinanceCollectReceiptPO(String id, String createTime,String reviseTime,String creatorId, String client, double totalAmount, ArrayList<String[]> list) {
		super(id, 21, createTime,reviseTime,creatorId);
		this.client = client;
		this.totalAmount = totalAmount;
		this.list = list;
	}

	public FinanceCollectReceiptPO(FinanceCollectReceiptVO vo) {
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
		client = vo.getClient();
		totalAmount = vo.getTotalAmount();
		list=new ArrayList<>();
		ArrayList<FinanceActTransLineItem> list=vo.getItemList();
		for(int i=0;i<list.size();i++){
			this.list.add(new String[]{list.get(i).getBankAccount(),Double.toString(list.get(i).getAmount()),list.get(i).getNote()});
		}
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
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
}
