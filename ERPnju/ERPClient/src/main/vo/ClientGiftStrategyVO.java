package main.vo;

import java.io.Serializable;
import java.util.Map;

public class ClientGiftStrategyVO extends StrategyVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2389517117390252402L;
	private Map<OutGoodsVO,Integer> giftlist;//赠品列表
	private int level;//客户等级
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ClientGiftStrategyVO(){
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
