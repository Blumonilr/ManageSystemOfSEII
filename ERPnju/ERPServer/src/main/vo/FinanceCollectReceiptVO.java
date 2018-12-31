package main.vo;

import main.PO.FinanceCollectReceiptPO;

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
public class FinanceCollectReceiptVO extends FinanceReceiptVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7140904489768371432L;
	private double totalAmount;//总额
	private String client;//客户
	private ArrayList<FinanceActTransLineItem> itemList;//条目清单

	public FinanceCollectReceiptVO(){//默认构造方法
		this.makeTime=Calendar.getInstance();
		this.receiptType=21;
		this.reviseTime=null;
	}
	
	/**
	 * @param creatorId  操作员id
	 * @param itemList  转账列表
	 */
	public FinanceCollectReceiptVO(String creatorId,String client,
			ArrayList<FinanceActTransLineItem> itemList) {
		this();
		this.creatorId = creatorId;
		this.client=client;
		this.itemList = itemList;
		calculateTotal();
	}
	public FinanceCollectReceiptVO(FinanceCollectReceiptPO po){
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
		client=po.getClient();
		itemList=new ArrayList<>();
		ArrayList<String[]> list=po.getList();
		String[] temp;
		for(int i=0;i<list.size();i++){
			temp=list.get(i);
			itemList.add(new FinanceActTransLineItem(temp[0],Double.parseDouble(temp[1]),temp[2]));

		}
		calculateTotal();
	}


	public double getTotalAmount() {
		return totalAmount;
	}

	public ArrayList<FinanceActTransLineItem> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<FinanceActTransLineItem> itemList) {
		this.itemList = itemList;
		calculateTotal();
	}

	private void calculateTotal(){//计算总额
		totalAmount = 0;
		for (int i = 0; i < itemList.size(); i++) {
			totalAmount += itemList.get(i).getAmount();
		}
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
}
