package main.ui.userui;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.stage.Stage;

public class UserDetailUIController {

	
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
	public void onCancel(){
		this.stage.close();
	}
}
