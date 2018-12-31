package main.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class SaleReceiptVO extends SaleReceiptBothVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5774066906480921904L;

	public SaleReceiptVO(String id, Calendar makeTime, Calendar reviseTime, String creatorId,
			String clientId, String clerkId, String stockId, double discount, double valueOfVoucher, String note,
			ArrayList<SaleReceiptBothVOLineItem> itemList,String strategyNote,double reductionOfPackage,double friendlyDiscount) {
		super(id, 31, makeTime, reviseTime, creatorId, clientId, clerkId, stockId, discount, valueOfVoucher, note,
				itemList,strategyNote,reductionOfPackage,friendlyDiscount);
		// TODO Auto-generated constructor stub
	}


}
