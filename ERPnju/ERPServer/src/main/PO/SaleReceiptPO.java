package main.PO;

import java.io.Serializable;
import java.util.ArrayList;

import main.vo.SaleReceiptBothVOLineItem;
//销售类单据爸爸
public abstract class SaleReceiptPO extends ReceiptPO implements Serializable{
	public double getReductionOfPackage() {
		return reductionOfPackage;
	}
	public void setReductionOfPackage(double reductionOfPackage) {
		this.reductionOfPackage = reductionOfPackage;
	}
	public double getTotalAfterPackage() {
		return totalAfterPackage;
	}
	public void setTotalAfterPackage(double totalAfterPackage) {
		this.totalAfterPackage = totalAfterPackage;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 487260387260541389L;
	protected String clientId;//客户id
	protected String clerkId;//业务员id
	protected String stockId;//仓库id
	protected double totalBeforeDiscountOrVoucher;//折让前总额
	protected double discount;//折让
	protected double reductionOfPackage=0.0;//特价包减价
	protected double valueOfVoucher;//使用代金券金额
	protected double totalAfterDiscount;//折让后总额
	protected double totalAfterVoucher;//折让后总额
	protected double totalAfterPackage;//特价包后总额
	protected String note;//备注
	protected ArrayList<String[]> goodsList;//商品信息清单(商品id、商品名、商品型号、商品数量、商品单价、本条目总价、备注)
	protected String strategyNote;//销售策略的备注 系统生成
	double friendlyDiscount;//销售人员折让
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @param id
	 * @param receiptType
	 * @param createTime
	 * @param reviseTime
	 * @param creatorId
	 * @param clientId
	 * @param clerkId
	 * @param stockId
	 * @param totalBeforeDiscountOrVoucher
	 * @param discount
	 * @param valueOfVoucher
	 * @param totalAfterDiscount
	 * @param totalAfterVoucher
	 * @param note
	 * @param goodsList
	 */
	public SaleReceiptPO(String id, int receiptType, String createTime, String reviseTime, String creatorId,
			String clientId, String clerkId, String stockId, double totalBeforeDiscountOrVoucher, double discount,
			double valueOfVoucher, double totalAfterDiscount, double totalAfterVoucher, String note,
			ArrayList<String[]> goodsList,String strategyNote,double reductionOfPackage,double totalAfterPackage,double friendlyDiscount) {
		super(id, receiptType, createTime, reviseTime, creatorId);
		this.clientId = clientId;
		this.clerkId = clerkId;
		this.stockId = stockId;
		this.totalBeforeDiscountOrVoucher = totalBeforeDiscountOrVoucher;
		this.discount = discount;
		this.valueOfVoucher = valueOfVoucher;
		this.totalAfterDiscount = totalAfterDiscount;
		this.totalAfterVoucher = totalAfterVoucher;
		this.note = note;
		this.goodsList = goodsList;
		this.strategyNote=strategyNote;
		this.reductionOfPackage=reductionOfPackage;
		this.friendlyDiscount=friendlyDiscount;
	}
	public double getFriendlyDiscount() {
		return friendlyDiscount;
	}
	public void setFriendlyDiscount(double friendlyDiscount) {
		this.friendlyDiscount = friendlyDiscount;
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
	public String getClerkId() {
		return clerkId;
	}
	public String getStockId() {
		return stockId;
	}
	public double getTotalBeforeDiscountOrVoucher() {
		return totalBeforeDiscountOrVoucher;
	}
	public double getDiscount() {
		return discount;
	}
	public double getValueOfVoucher() {
		return valueOfVoucher;
	}
	public double getTotalAfterDiscount() {
		return totalAfterDiscount;
	}
	public double getTotalAfterVoucher() {
		return totalAfterVoucher;
	}
	public String getNote() {
		return note;
	}
	public ArrayList<String[]> getGoodsList() {
		return goodsList;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public void setClerkId(String clerkId) {
		this.clerkId = clerkId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public void setTotalBeforeDiscountOrVoucher(double totalBeforeDiscountOrVoucher) {
		this.totalBeforeDiscountOrVoucher = totalBeforeDiscountOrVoucher;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public void setValueOfVoucher(double valueOfVoucher) {
		this.valueOfVoucher = valueOfVoucher;
	}
	public void setTotalAfterDiscount(double totalAfterDiscount) {
		this.totalAfterDiscount = totalAfterDiscount;
	}
	public void setTotalAfterVoucher(double totalAfterVoucher) {
		this.totalAfterVoucher = totalAfterVoucher;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setGoodsList(ArrayList<String[]> goodsList) {
		this.goodsList = goodsList;
	}
	
	
}
