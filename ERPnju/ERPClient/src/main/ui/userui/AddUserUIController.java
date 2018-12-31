package main.ui.userui;

import java.rmi.RemoteException;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.businesslogicservice.userblservice.UserblService;
import main.rmi.RemoteHelper;
import main.vo.UserVO;

public class AddUserUIController {
	UserblService userblservice=RemoteHelper.getInstance().getUserblService();

	
	private Stage stage;
	@FXML
	JFXTextField id;
	@FXML
	JFXTextField password;
	@FXML
	JFXComboBox<String> type;
	@FXML
	JFXComboBox<Integer> level;
	
	public void onInit(Stage s,String id,String pw,int tp,int lev){
		ObservableList list=FXCollections.observableArrayList();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		level.setItems(list);
		ObservableList list2=FXCollections.observableArrayList();
		list2.add("库存管理人员");
		list2.add("进货销售人员");
		list2.add("财务人员");
		list2.add("总经理");
		type.setItems(list2);
		
		String tpinfo="库存管理人员";
		if(tp==2){
			tpinfo="进货销售人员";
		}
		else if(tp==3){
			tpinfo="财务人员";
		}
		else if(tp==4){
			tpinfo="总经理";
		}
		
		this.stage=s;
		this.id.setText(id);
		this.password.setText(pw);
		this.type.setValue(tpinfo);
		this.level.setValue(lev);
	}
	
	@FXML
	public void onSave(){
		String n_id=this.id.getText();
		String n_pw=this.password.getText();
		int n_type=1;
		String typeInfo=this.type.getValue();
		
		if(typeInfo.equals("库存管理人员")){
			n_type=1;
		}
		else if(typeInfo.equals("进货销售人员")){
			n_type=2;
		}
		else if(typeInfo.equals("财务人员")){
			n_type=3;
		}
		else if(typeInfo.equals("总经理")){
			n_type=4;
		}
		int n_level=this.level.getValue();
		
	    
	    boolean res=false;
	    try {
			res=userblservice.addUser(n_type,n_id,n_pw);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    Alert alert=new Alert(AlertType.INFORMATION);
	    alert.setTitle("提示");
	    alert.setContentText("用户名已存在");
	    alert.setHeaderText("添加失败！");
	    
	    if(res){
	    	alert.setHeaderText(null);
	    	alert.setContentText("添加成功");
	    	alert.showAndWait();
	    	this.stage.close();
	    }
	    else{
	    	
	    	alert.showAndWait();
	    }
	}
	
	@FXML
	public void onCancel(){
		this.stage.close();
	}
}
