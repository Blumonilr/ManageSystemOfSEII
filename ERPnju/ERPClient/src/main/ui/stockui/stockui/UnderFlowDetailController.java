package main.ui.stockui.stockui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.rmi.RemoteHelper;
import main.vo.StockUnderflowReceiptVO;

public class UnderFlowDetailController {

	@FXML Label name;
	@FXML Label id;
	@FXML Label xh;
	@FXML Label num;
	@FXML Label name1;
	@FXML Label name2;
	
	StockUnderflowReceiptVO r;
	
	public void init(){
		name.setText(r.getGoodsName());
		id.setText(r.getGoodsId());
		xh.setText(r.getGoodsXh());
		num.setText(r.getAmount()+"");
		name1.setText(r.getId());
		name2.setText(r.getCreatorId());
	}
	
	public void setReceipt(StockUnderflowReceiptVO r){
		this.r=r;
	}
}
