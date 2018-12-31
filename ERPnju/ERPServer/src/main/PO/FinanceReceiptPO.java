package main.PO;

import java.io.Serializable;

//财务类单据爸爸
public abstract class FinanceReceiptPO extends ReceiptPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6896417579475753685L;

	public FinanceReceiptPO(){}

	public FinanceReceiptPO(String id, int receiptType, String createTime, String reviseTime,String creatorId) {
		super(id, receiptType, createTime, reviseTime,creatorId);
	}

}