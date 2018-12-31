package main.ui.stockui.classui;

import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.logui.LogHelper;

public class DelClassUIController {

	private Stage stage;
	private TreeItem<String>root;//需要被删除的节点
	
	@FXML public Label className;
	
	public void init(Stage s){
		stage=s;
	}
	
	public void setStage(Stage s){
		stage=s;
	}
	
	public void setRoot(TreeItem<String>item){
		root=item;
		className.setText(root.getValue());
	}
	
	@FXML
	public void onOK(){
		//调用delClass接口
		boolean flag=false;
		try {
			flag=RemoteHelper.getInstance().getStockblService().delClass(root.getValue());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(flag){			
			ClientRunner.getLogger().record("删除商品分类"+root.getValue());
			root.getChildren().clear();
			if(root.getParent()!=null)
				root.getParent().getChildren().remove(root);			
		}else{
			ClientRunner.getLogger().record("删除商品分类失败");

			//提示界面
			Alert alert=new Alert(AlertType.WARNING);
			alert.setHeaderText(null);
			alert.setContentText("删除商品分类失败: "+root.getValue());
			alert.show();
		}
		stage.close();
	}
	
	@FXML
	public void onCancel(){
		stage.close();
	}
}
