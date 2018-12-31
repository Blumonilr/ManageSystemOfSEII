package main.vo;

import java.io.Serializable;
import java.util.Map;

public class GiftStrategyVO extends StrategyVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5184544469867489534L;
	double leastTotal;//消费下限
	Map<OutGoodsVO,Integer> giftlist;//赠品列表
	
	public GiftStrategyVO() {
		this.setType(5);
	}

	public double getLeastTotal() {
		return leastTotal;
	}

	public void setLeastTotal(double leastTotal) {
		this.leastTotal = leastTotal;
	}

	public Map<OutGoodsVO, Integer> getGiftlist() {
		return giftlist;
	}
	
	public void setList(Map<OutGoodsVO,Integer> list){
		giftlist=list;
	}
	
	public void add(OutGoodsVO goods,int n){
		giftlist.put(goods, n);
	}
}
