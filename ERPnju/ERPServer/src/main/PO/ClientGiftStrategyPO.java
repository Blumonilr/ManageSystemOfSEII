package main.PO;

import java.util.Map;

import main.vo.OutGoodsVO;

public class ClientGiftStrategyPO extends StrategyPO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<OutGoodsVO,Integer> giftlist;//赠品列表
	private int level;//客户等级
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ClientGiftStrategyPO(){
		this.setType(2);
	}

	public Map<OutGoodsVO, Integer> getList() {
		return giftlist;
	}
	
	public void setList(Map<OutGoodsVO,Integer> list){
		giftlist=list;
	}
	
	public void add(OutGoodsVO goods,int n){
		giftlist.put(goods,n);
	}
}
