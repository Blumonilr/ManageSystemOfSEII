package main.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class StockCheckResultVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5240066327142138994L;
	private ArrayList<StockRecordVO>list=new ArrayList<>();
	private String goodsName,goodsId;
	private long totalNum;

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public long getTotalNum() {
		totalNum=calculateTotal();
		return totalNum;
	}

	public void add(StockRecordVO sr){
		list.add(sr);
	}
	
	public ArrayList<StockRecordVO>getList(){
		return list;
	}
	private long calculateTotal(){
		long res=0;
		for(StockRecordVO sr:list)
			res+=sr.getNum();
		return res;
	}
}
