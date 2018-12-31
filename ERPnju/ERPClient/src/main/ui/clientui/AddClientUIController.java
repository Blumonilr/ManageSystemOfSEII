package main.ui.clientui;

import java.rmi.RemoteException;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.businesslogicservice.clientblservice.ClientblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.vo.ClientVO;

public class AddClientUIController {
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
	
	public void onInit(Stage s){
		
		ObservableList list=FXCollections.observableArrayList();
		list.add("供应商");
		list.add("销售商");
		type.setItems(list);
		
		this.stage=s;
		
	}
	
	@FXML
	public void save(){
		String idn=this.id.getText();
		String namen=this.name.getText();
		String typen=this.type.getValue();
		int leveln=0;
		try{
			leveln=Integer.parseInt(this.level.getText());
		}catch(Exception e){
			leveln=0;
		}
		String teln=this.tel.getText();
		String emailn=this.email.getText();
		String postn=this.post.getText();
		String addrn=this.addr.getText();
		double mostn=0.0;
		try{
			mostn=Double.parseDouble(this.most.getText());
		}catch(Exception e){
			mostn=0.0;
		}
		String clerkn=this.clerk.getText();

		ClientVO vo=new ClientVO(namen,idn,typen,leveln,teln,addrn,emailn,postn,mostn,0.0,0.0,clerkn);
		boolean res=false;
		try {
			res=bl.addClient(vo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("提示");
		if(res){
			alert.setContentText("添加成功！");
			ClientRunner.getLogger().record("添加客户成功: "+vo.getClientName());
			alert.showAndWait();
			stage.close();
			
		}else{
			alert.setContentText("添加失败！请检查信息是否已存在！");
			alert.showAndWait();
		}
		
	}
}


