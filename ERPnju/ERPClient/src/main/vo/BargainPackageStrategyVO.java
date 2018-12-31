package main.vo;

import java.io.Serializable;
import java.util.Map;

public class BargainPackageStrategyVO extends StrategyVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2835670536796941956L;
	Map<OutGoodsVO,Integer> goodslist;//商品列表
	double total;//特价包总价
	
	public Map<OutGoodsVO, Integer> getGoodslist() {
		return goodslist;
	}

	public void setGoodslist(Map<OutGoodsVO, Integer> goodslist) {
		this.goodslist = goodslist;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public BargainPackageStrategyVO() {
		this.setType(6);
	}

}
