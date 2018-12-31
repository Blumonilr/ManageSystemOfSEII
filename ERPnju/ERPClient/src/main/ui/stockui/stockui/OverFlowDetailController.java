package main.ui.stockui.stockui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.vo.StockOverflowReceiptVO;

public class OverFlowDetailController{

	@FXML Label name;
	@FXML Label id;
	@FXML Label xh;
	@FXML Label num;
	@FXML Label name1;
	@FXML Label name2;
	
	StockOverflowReceiptVO r;
	
	public void init() {
		// TODO 自动生成的方法存根
		name1.setText(r.getId());
		name2.setText(r.getCreatorId());
		name.setText(r.getGoodsName());
		id.setText(r.getGoodsId());
		xh.setText(r.getGoodsXh());
		num.setText(r.getAmount()+"");
	}
	
	public void setReceipt(StockOverflowReceiptVO r){
		this.r=r;
	}

}
