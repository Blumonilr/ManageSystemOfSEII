package main.PO;

import java.io.Serializable;
import java.util.ArrayList;
//进货类单据爸爸
public abstract class PurchaseReceiptPO extends ReceiptPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5070201599151751712L;
	protected String clientId;//供应商
	protected String stockId;//仓库
	protected String note;//备注
	protected double totalValue;//总额
	protected ArrayList<String[]> goodsList;//商品信息列表  (商品id、商品名称、商品型号、数量、单价、该项金额、备注)
	/**
	 * @param id
	 * @param receiptType
	 * @param createTime
	 * @param reviseTime
	 * @param creatorId
	 * @param clientId
	 * @param stockId
	 * @param note
	 * @param totalValue
	 * @param goodsList
	 */
	public PurchaseReceiptPO(String id, int receiptType, String createTime, String reviseTime, String creatorId,
			String clientId, String stockId, String note, double totalValue, ArrayList<String[]> goodsList) {
		super(id, receiptType, createTime, reviseTime, creatorId);
		this.clientId = clientId;
		this.stockId = stockId;
		this.note = note;
		this.totalValue = totalValue;
		this.goodsList = goodsList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getClientId() {
		return clientId;
	}
	public String getStockId() {
		return stockId;
	}
	public String getNote() {
		return note;
	}
	public double getTotalValue() {
		return totalValue;
	}
	public ArrayList<String[]> getGoodsList() {
		return goodsList;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	public void setGoodsList(ArrayList<String[]> goodsList) {
		this.goodsList = goodsList;
	}
	
}
