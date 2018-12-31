package main.PO;

import java.util.Map;

import main.vo.OutGoodsVO;

public class GiftStrategyPO extends StrategyPO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	double leastTotal;//消费下限
	Map<OutGoodsVO,Integer> giftlist;//赠品列表
	
	public GiftStrategyPO() {
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
