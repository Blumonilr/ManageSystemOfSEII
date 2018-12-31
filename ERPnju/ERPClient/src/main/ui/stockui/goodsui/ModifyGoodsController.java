package main.ui.stockui.goodsui;

import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.vo.OutGoodsVO;

public class ModifyGoodsController {

	@FXML public Label name;
	@FXML public Label id;
	@FXML public Label xh;
	@FXML public Label className;
	@FXML public Label classId;
	@FXML public Label day;
	@FXML public TextField inPrice;
	@FXML public TextField outPrice;
	@FXML public Label latestip;
	@FXML public Label latestop;
	@FXML public TextField stockNum;
	@FXML public TextField alarmNum;
	
	private Stage stage;
	private OutGoodsVO goods;
	private GoodsUIController guic;
	
	
	public void init(Stage s,OutGoodsVO obj,GoodsUIController g){
		stage=s;
		goods=obj;
		guic=g;
		
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
		
	}
	
	@FXML
	public void onModify(){
		//输入的合法性检查

		
		try{
			double d=Double.parseDouble(inPrice.getText());
			goods.setInPrice(d);
		}catch(NumberFormatException e){}
		try{
			double d=Double.parseDouble(outPrice.getText());
			goods.setOutPrice(d);
		}catch(NumberFormatException e){}
		try{
			long l=Long.parseLong(stockNum.getText());
			goods.setStockNum(l);
		}catch(NumberFormatException e){}
		try{
			long l=Long.parseLong(alarmNum.getText());
			goods.setAlarmNum(l);
		}catch(NumberFormatException e){}
		
		boolean flag=false;
		try {
			flag=RemoteHelper.getInstance().getStockblService().modifyGoods(goods);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		guic.showGoods();
		//提示界面
		if(flag){
			ClientRunner.getLogger().record("修改商品成功"+goods.getName());
			
			Alert alert=new Alert(AlertType.INFORMATION);
			alert.setTitle("提示");
			alert.setHeaderText(null);
			alert.setContentText("修改商品成功: "+goods.getName());
			alert.show();
		}else{
			ClientRunner.getLogger().record("修改商品失败"+goods.getName());
			
			Alert alert=new Alert(AlertType.WARNING);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("修改商品失败: "+goods.getName());
			alert.show();
		}
		stage.close();
	}
}
