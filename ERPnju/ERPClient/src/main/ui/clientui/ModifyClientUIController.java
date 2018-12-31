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

public class ModifyClientUIController {
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
		if(leveln<=5){
			try{
				mostn=Double.parseDouble(this.most.getText());
			}catch(Exception e){
				mostn=0.0;
			}
			String clerkn=this.clerk.getText();

			ClientVO vo=new ClientVO(namen,idn,typen,leveln,teln,addrn,emailn,postn,mostn,collectn,payn,clerkn);
			boolean res=false;
			try {
				res=bl.modifyClient(vo);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Alert alert=new Alert(AlertType.INFORMATION);
			alert.setTitle("提示");
			if(res){
				alert.setContentText("修改客户信息成功！");
				ClientRunner.getLogger().record("修改客户信息 客户id: "+vo.getID());
				
				alert.showAndWait();
				stage.close();
			}
			else{
				alert.setContentText("修改失败！");
				alert.showAndWait();
			}
		}
		else{
			Alert alt=new Alert(AlertType.WARNING);
			alt.setTitle("警告");
			alt.setContentText("用户等级最高为5！");
			alt.setHeaderText(null);
			alt.showAndWait();
		}
	}
}


