package main.vo;

import java.io.Serializable;

public class DiscountStrategyVO extends StrategyVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9206474987236275148L;
	double leastTotal;//消费下限
	double discount;//折让比例
	
	public DiscountStrategyVO() {
		this.setType(4);
	}

	public double getLeastTotal() {
		return leastTotal;
	}

	public void setLeastTotal(double leastTotal) {
		this.leastTotal = leastTotal;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

}
