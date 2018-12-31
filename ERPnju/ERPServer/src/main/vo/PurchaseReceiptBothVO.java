package main.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class PurchaseReceiptBothVO extends ReceiptVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8315359528928980623L;
	private String clientId;//供应商
	private String stockId;//仓库
	private String note;//备注
	private double totalValue;//总额
	ArrayList<PurchaseReceiptBothVOLineItem> itemList;//入库商品列表
	/**
	 * @param id
	 * @param receiptType
	 * @param makeTime
	 * @param reviseTime
	 * @param creatorId
	 * @param clientId
	 * @param stockId
	 * @param note
	 * @param totalValue
	 * @param itemList
	 */
	public PurchaseReceiptBothVO(String id, int receiptType, Calendar makeTime, Calendar reviseTime, String creatorId,
			String clientId, String stockId, String note,
			ArrayList<PurchaseReceiptBothVOLineItem> itemList) {
		super(id, receiptType, makeTime, reviseTime, creatorId);
		this.clientId = clientId;
		this.stockId = stockId;
		this.note = note;
		this.itemList = itemList;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public ArrayList<PurchaseReceiptBothVOLineItem> getItemList() {
		return itemList;
	}
	public void setItemList(ArrayList<PurchaseReceiptBothVOLineItem> itemList) {
		this.itemList = itemList;
	}
	public double getTotalValue() {
		double result=0.0;
		for(PurchaseReceiptBothVOLineItem item:itemList){
			result+=item.getSubtotal();
		}
		totalValue=result;
		return totalValue;
	}
	
	
	
	
}
