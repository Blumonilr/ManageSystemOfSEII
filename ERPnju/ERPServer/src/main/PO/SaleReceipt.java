package main.PO;

import java.io.Serializable;
import java.util.ArrayList;

public class SaleReceipt extends SaleReceiptPO implements Serializable{


	public SaleReceipt(String id,  String createTime, String reviseTime, String creatorId,
			String clientId, String clerkId, String stockId, double totalBeforeDiscountOrVoucher, double discount,
			double valueOfVoucher, double totalAfterDiscount, double totalAfterVoucher, String note,
			ArrayList<String[]> goodsList,String strategyNote,double reductionOfPackage,double totalAfterPackage,double friendlyDiscount) {
		super(id,31, createTime, reviseTime, creatorId, clientId, clerkId, stockId, totalBeforeDiscountOrVoucher,
				discount, valueOfVoucher, totalAfterDiscount, totalAfterVoucher, note, goodsList,strategyNote,reductionOfPackage, totalAfterPackage,friendlyDiscount);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2357846692809426440L;

	
	
}
