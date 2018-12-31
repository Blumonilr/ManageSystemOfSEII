package main.ui.bookui.firstbookui.goodsui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.runner.ClientRunner;
import main.vo.OutGoodsVO;

public class ShowGoodsController {

	@FXML public Label name;
	@FXML public Label id;
	@FXML public Label xh;
	@FXML public Label className;
	@FXML public Label classId;
	@FXML public Label day;
	@FXML public Label inPrice;
	@FXML public Label outPrice;
	@FXML public Label latestip;
	@FXML public Label latestop;
	@FXML public Label stockNum;
	@FXML public Label alarmNum;
	
	private Stage stage;
	private OutGoodsVO goods;
	
	public void init(Stage s,OutGoodsVO obj){
		stage=s;
		goods=obj;
		
		name.setText(goods.getName());
		id.setText(goods.getId());
		xh.setText(goods.getXh());
		className.setText(goods.getClassName());
		classId.setText(goods.getClassId());
		day.setText(goods.getStringDay());
		inPrice.setText(Double.toString(goods.getInPrice()));
		outPrice.setText(Double.toString(goods.getOutPrice()));
		latestip.setText(Double.toString(goods.getLatestip()));
		latestop.setText(Double.toString(goods.getLatestop()));
		stockNum.setText(Long.toString(goods.getStockNum()));
		alarmNum.setText(Long.toString(goods.getAlarmNum()));
		
		ClientRunner.getLogger().record("查看商品"+goods.getName());
	}
	
	@FXML
	public void onOK(){
		stage.close();
	}
}
