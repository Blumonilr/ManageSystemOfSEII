package main.vo;

import java.io.Serializable;

public class ClientDiscountStrategyVO extends StrategyVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3205121939370057079L;
	double discount;//折让比例
	int level;//客户等级

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public ClientDiscountStrategyVO() {
		super();
		this.setType(1);
	}
	

}
