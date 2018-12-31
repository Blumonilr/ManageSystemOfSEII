package main.vo;

import java.io.Serializable;

public class ClientCouponStrategyVO extends StrategyVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3443113274609469685L;
	double coupon;//代金券金额
	int level;//客户等级
	
	public double getCoupon() {
		return coupon;
	}

	public void setCoupon(double coupon) {
		this.coupon = coupon;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ClientCouponStrategyVO() {
		this.setType(3);
	}

}
