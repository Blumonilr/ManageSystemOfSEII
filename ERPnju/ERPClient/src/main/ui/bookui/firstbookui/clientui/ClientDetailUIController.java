package main.ui.bookui.firstbookui.clientui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import main.businesslogicservice.clientblservice.ClientblService;
import main.rmi.RemoteHelper;
import main.vo.ClientVO;

public class ClientDetailUIController {
	ClientblService bl=RemoteHelper.getInstance().getClientblService();
	Stage stage;
	@FXML
	JFXTextField id;
	@FXML
	JFXTextField name;
	@FXML
	JFXComboBox<String> type;
	@FXML
	JFXTextField level;
	@FXML
	JFXTextField tel;
	@FXML
	JFXTextField email;
	@FXML
	JFXTextField post;
	@FXML
	JFXTextField addr;
	@FXML
	JFXTextField most;
	@FXML
	JFXTextField collect;
	@FXML
	JFXTextField pay;
	@FXML
	JFXTextField clerk;
	String idn="";
	double collectn=0.0;
	double payn=0.0;
	
	public void onInit(ClientVO c,Stage s){
		ObservableList list=FXCollections.observableArrayList();
		list.add("供应商");
		list.add("销售商");
		type.setItems(list);
		
		this.stage=s;
		this.id.setText(c.getID());
		idn=c.getID();
		this.name.setText(c.getClientName());
		if(c.getType()!=null){
			this.type.setValue(c.getType());
		}
		this.level.setText(c.getLevel()+"");
		this.tel.setText(c.getPhone());
		this.email.setText(c.getEmail());
		this.post.setText(c.getPostcode());
		this.addr.setText(c.getAddress());
		this.most.setText(c.getCollectTop()+"");
		this.pay.setText(c.getPay()+"");
		payn=c.getPay();
		this.collect.setText(c.getCollect()+"");
		collectn=c.getCollect();
		this.clerk.setText(c.getDefaultUserID());
	}
	
	@FXML
	public void onClose(){
	
		
		stage.close();
	}
}
