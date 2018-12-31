package main.ui.stockui.classui;

import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.logui.LogHelper;
import main.vo.ClassVO;

public class AddClassUIController {

	private Stage stage;
	private TreeItem<String>root;//被点击的节点
	
	@FXML public TextField fatherName,fatherId,className;
	
	public void init(Stage s){
		stage=s;
	}
	
	public void setRoot(TreeItem<String>item){
		root=item;
		if(root!=null)
			fatherId.setText(root.getValue());
		String fn=null;
		ClassVO f=null;
		try {
			f=RemoteHelper.getInstance().getStockblService().showClass(root.getValue());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(f!=null)
			fn=f.getName();
		fatherName.setText(fn);
	}
	
	public void setStage(Stage s){
		stage=s;
	}
	
	@FXML
	public void onOK(){
		String input=className.getText();
		String fatherid=fatherId.getText();
		fatherid=fatherid.equals("总商品分类")?null:fatherid;
		
		if(input==null||input.equals(""))
			input="new class";
		//用商品分类名当作id
		String id=input;
		ClassVO c=new ClassVO(id, input, fatherid);
		boolean flag=false;
		try {
			flag=RemoteHelper.getInstance().getStockblService().addClass(c);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(flag){			
			TreeItem<String>item=new TreeItem<>(id);
			root.getChildren().add(item);
			ClientRunner.getLogger().record("新增商品分类"+input);
			
			//提示消息
			Alert alert=new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("商品分类添加成功: "+id);
			alert.show();
		}else{
			ClientRunner.getLogger().record("新增商品分类失败");
			Alert alert=new Alert(AlertType.WARNING);
			alert.setHeaderText(null);
			alert.setContentText("商品分类添加失败: "+id);
			alert.show();
		}
		stage.close();
	}
	
	//由界面根据分类名计算id
	private String calculateId(String name){
		String id="";
		for(char c:name.toCharArray()){
			id+=Integer.toHexString(c);
		}
		return id;
	}
	
	@FXML
	public void onCancel(){
		stage.close();
	}
	
}
