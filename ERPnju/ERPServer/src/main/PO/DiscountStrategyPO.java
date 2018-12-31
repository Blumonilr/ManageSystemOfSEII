package main.PO;

public class DiscountStrategyPO extends StrategyPO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	double leastTotal;//消费下限
	double discount;//折让比例
	
	public DiscountStrategyPO() {
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
