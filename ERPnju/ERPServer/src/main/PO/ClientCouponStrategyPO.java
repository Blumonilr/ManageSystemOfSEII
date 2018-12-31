package main.PO;

public class ClientCouponStrategyPO extends StrategyPO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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

	public ClientCouponStrategyPO() {
		this.setType(3);
	}

}
