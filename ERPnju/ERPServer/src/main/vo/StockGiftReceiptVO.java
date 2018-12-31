package main.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class StockGiftReceiptVO extends ReceiptVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2487901971825711149L;
	private ArrayList<StockGiftReceiptVOLineItem> goodsList;

	/**
	 * @param id
	 * @param makeTime
	 * @param reviseTime
	 * @param creatorId
	 * @param goodsList
	 */
	public StockGiftReceiptVO(String id,Calendar makeTime, Calendar reviseTime, String creatorId,
			ArrayList<StockGiftReceiptVOLineItem> goodsList) {
		super(id,13, makeTime, reviseTime, creatorId);
		this.goodsList = goodsList;
	}

	/**
	 * 
	 * @return 赠品单
	 */
	public ArrayList<StockGiftReceiptVOLineItem> getGoodsList() {
		return goodsList;
	}

	/**
	 * 设置赠品单
	 * @param goodsList
	 */
	public void setGoodsList(ArrayList<StockGiftReceiptVOLineItem> goodsList) {
		this.goodsList = goodsList;
	}

	public double getTotal(){
		double result=0;
		for(int i=0;i<goodsList.size();i++){
			result+=goodsList.get(i).getValue();
		}
		return result;
	}

}
