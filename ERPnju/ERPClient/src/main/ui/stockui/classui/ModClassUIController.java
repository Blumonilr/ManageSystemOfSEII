package main.ui.stockui.classui;

import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.vo.ClassVO;

public class ModClassUIController {
	
	@FXML public TextField input;
	@FXML public Label classId;
	@FXML public Label father;
	
	private Stage stage;
	private ClassVO obj;
	private String oldName;
	private TreeItem<String>item;
	private ClassUIController cuic;
	
	public void init(Stage stage,TreeItem<String>item,ClassUIController cuic) {
		// TODO Auto-generated method stub
		this.stage=stage;
		oldName=item.getValue();
		this.item=item;
		this.cuic=cuic;
		try {
			obj=RemoteHelper.getInstance().getStockblService().showClass(oldName);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		input.setText(obj.getName());
		classId.setText(obj.getId());
		father.setText(obj.getFather());
	}

	@FXML
	public void onOK(){
		boolean res=false;
		
		if(input.getText()!=null&&!input.getText().equals("")){
			obj.setName(input.getText());
			try {
				res=RemoteHelper.getInstance().getStockblService().modifyClass(obj);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(res){
			cuic.initTreeView();
			ClientRunner.getLogger().record("修改商品分类 "+oldName+" 为 "+obj.getName());
			Alert alert=new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("修改商品分类 "+oldName+" 为 "+obj.getName());
			alert.show();
		}else{
			ClientRunner.getLogger().record("修改商品分类 "+oldName+" 失败");
			Alert alert=new Alert(AlertType.WARNING);
			alert.setHeaderText(null);
			alert.setContentText("修改商品分类 "+oldName+" 失败");
			alert.show();
		}
		stage.close();
	}
}
