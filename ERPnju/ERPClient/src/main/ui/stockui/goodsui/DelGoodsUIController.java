package main.ui.stockui.goodsui;

import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.logui.LogHelper;
import main.vo.OutGoodsVO;

public class DelGoodsUIController {

	@FXML public Label goodsName;
	@FXML public Label goodsId;
	@FXML public Label className;
	@FXML public Label classId;
	
	private Stage stage;
	private OutGoodsVO goods;
	private GoodsUIController guic;
	
	public void init(Stage s,OutGoodsVO g,GoodsUIController guic){
		stage=s;
		goods=g;
		this.guic=guic;
		goodsName.setText(goods.getName());
		goodsId.setText(goods.getId());
		className.setText(goods.getClassName());
		classId.setText(goods.getClassId());
	}
	
	@FXML
	public void onDelete(){
		boolean flag=false;
		try {
			flag=RemoteHelper.getInstance().getStockblService().delGoods(goods.getId());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		//结果提示
		System.out.println(flag);
		if(flag){
			ClientRunner.getLogger().record("删除商品"+goods.getName());
			
			Alert alert=new Alert(AlertType.INFORMATION);
			alert.setTitle("提示");
			alert.setHeaderText(null);
			alert.setContentText("删除商品成功: "+goods.getName());
			alert.show();
			
		}else{
			ClientRunner.getLogger().record("删除商品失败"+goods.getName());
			
			Alert alert=new Alert(AlertType.WARNING);
			alert.setTitle("警告");
			alert.setHeaderText(null);
			alert.setContentText("删除商品失败: "+goods.getName());
			alert.show();
		}
		//更新界面
		guic.showGoods();
		stage.close();
	}
	
	@FXML
	public void onCancel(){
		stage.close();
	}
}
