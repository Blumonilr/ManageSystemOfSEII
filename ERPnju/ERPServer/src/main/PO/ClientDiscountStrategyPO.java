package main.PO;

public class ClientDiscountStrategyPO extends StrategyPO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientDiscountStrategyPO() {
		this.setType(1);
	}

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
}
