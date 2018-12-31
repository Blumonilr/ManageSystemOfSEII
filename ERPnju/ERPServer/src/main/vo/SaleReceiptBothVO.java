package main.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class SaleReceiptBothVO extends ReceiptVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2429525228973255316L;
	protected String clientId;//客户id
	protected String clerkId;//业务员id
	protected String stockId;//仓库id
	protected double totalBeforeDiscountOrVoucher;//折让前总额
	protected double discount=1.0;//折让
	protected double valueOfVoucher=0.0;//使用代金券金额//2017/12/1 使用代金券则不可以获得任何销售策略和任何折扣！！！！！！
	protected double totalAfterDiscount;//折让后总额
	protected double totalAfterVoucher;//代金券后总额
	protected double totalAfterPackage;//特价包后总额
	protected double reductionOfPackage=0.0;//特价包减价
	protected String note;//备注
	protected String strategyNote;//系统备注，记录销售策略使用情况
	protected ArrayList<SaleReceiptBothVOLineItem> itemList;//商品清单
	double friendlyDiscount;//业务员给的折扣
	/**
	 * @param id
	 * @param receiptType
	 * @param makeTime
	 * @param reviseTime
	 * @param creatorId
	 * @param clientId
	 * @param clerkId
	 * @param stockId
	 * @param discount
	 * @param valueOfVoucher
	 * @param note
	 * @param itemList
	 */
	public SaleReceiptBothVO(String id, int receiptType, Calendar makeTime, Calendar reviseTime, String creatorId,
			String clientId, String clerkId, String stockId, double discount, double valueOfVoucher, String note,
			ArrayList<SaleReceiptBothVOLineItem> itemList,String strategyNote,double reductionOfPackage,double friendlyDiscount) {
		super(id, receiptType, makeTime, reviseTime, creatorId);
		this.clientId = clientId;
		this.clerkId = clerkId;
		this.stockId = stockId;
		this.discount = discount;
		this.valueOfVoucher = valueOfVoucher;
		this.note = note;
		this.itemList = itemList;
		this.strategyNote=strategyNote;
		this.reductionOfPackage=reductionOfPackage;
		this.friendlyDiscount=friendlyDiscount;
	}
	public double getReductionOfPackage() {
		return reductionOfPackage;
	}
	public void setReductionOfPackage(double reductionOfPackage) {
		this.reductionOfPackage = reductionOfPackage;
	}
	public String getStrategyNote() {
		return strategyNote;
	}
	public void setStrategyNote(String strategyNote) {
		this.strategyNote = strategyNote;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClerkId() {
		return clerkId;
	}
	public void setClerkId(String clerkId) {
		this.clerkId = clerkId;
	}
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getValueOfVoucher() {
		return valueOfVoucher;
	}
	public void setValueOfVoucher(double valueOfVoucher) {
		this.valueOfVoucher = valueOfVoucher;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public ArrayList<SaleReceiptBothVOLineItem> getItemList() {
		return itemList;
	}
	public void setItemList(ArrayList<SaleReceiptBothVOLineItem> itemList) {
		this.itemList = itemList;
	}
	public double getFriendlyDiscount() {
		return friendlyDiscount;
	}
	public void setFriendlyDiscount(double friendlyDiscount) {
		this.friendlyDiscount = friendlyDiscount;
	}
	public double getTotalBeforeDiscountOrVoucher() {
		//cal
		double result=0.0;
		for(SaleReceiptBothVOLineItem item: itemList){
			result+=item.getSubTotal();
		}
		totalBeforeDiscountOrVoucher=result;
	
		return totalBeforeDiscountOrVoucher;
	}
	public double getTotalAfterDiscount() {
		//cal
		totalAfterDiscount=this.getTotalBeforeDiscountOrVoucher()*this.discount;
		
		return totalAfterDiscount*friendlyDiscount;
	}
	public double getTotalAfterVoucher() {
		//cal
		totalAfterVoucher=this.getTotalBeforeDiscountOrVoucher()-this.valueOfVoucher;
		
		return totalAfterVoucher*friendlyDiscount;
	}
	
	public double getTotalAfterPackage() {
		//cal
		totalAfterPackage=this.getTotalBeforeDiscountOrVoucher()-this.reductionOfPackage;
		
		return totalAfterPackage*friendlyDiscount;
	}
	
	
	
}
