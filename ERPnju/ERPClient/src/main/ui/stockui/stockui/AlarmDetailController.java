package main.ui.stockui.stockui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.vo.StockAlarmReceiptVO;

public class AlarmDetailController {
	@FXML Label goodsName;
	@FXML Label goodsID;
	@FXML Label goodsXH;
	@FXML Label goodsAlarm;
	@FXML Label goodsCurrent;
	@FXML Label id;
	@FXML Label creator;
	
	public void init(StockAlarmReceiptVO r){
		goodsName.setText(r.getGoodsName());
		goodsID.setText(r.getGoodsId());
		goodsXH.setText(r.getGoodsXh());
		goodsAlarm.setText(r.getAlarmNumber()+"");
		goodsCurrent.setText(r.getCurrentNumber()+"");
		id.setText(r.getId());
		creator.setText(r.getCreatorId());
	}
}
