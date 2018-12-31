package main.PO;

import java.util.Map;

import main.vo.OutGoodsVO;

public class BargainPackageStrategyPO extends StrategyPO {
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

	public BargainPackageStrategyPO() {
		this.setType(6);
	}
}
