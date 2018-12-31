package main.ui.userui;

import java.rmi.RemoteException;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.businesslogicservice.userblservice.UserblService;
import main.rmi.RemoteHelper;
import main.vo.UserVO;

public class ModifyPWUIController {
	UserblService bls=RemoteHelper.getInstance().getUserblService();
	@FXML
	JFXPasswordField old;
	@FXML
	JFXTextField pw1;
	@FXML
	JFXTextField pw2;
	
	Stage stage;
	
	@FXML
	public void onSave(){
		String oldPw=old.getText();
		String new1=pw1.getText();
		String new2=pw2.getText();
		boolean confirm=false;
		try {
			confirm=bls.userlogin("admin",oldPw);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(confirm){
			if(new1.equals(new2)){
				UserVO vo=new UserVO("admin",new1,0,0);
				boolean res=false;
				try {
					res=bls.modifyUser(vo);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(res){
					Alert a3=new Alert(AlertType.INFORMATION);
					a3.setTitle("提示");
					a3.setContentText("修改成功！");
					a3.showAndWait();
					stage.close();
				}
				else{
					Alert a4=new Alert(AlertType.INFORMATION);
					a4.setTitle("提示");
					a4.setContentText("修改失败！");
					a4.showAndWait();
				
				}
			}
			else{
				Alert a2=new Alert(AlertType.WARNING);
				a2.setTitle("警告");
				a2.setHeaderText("密码输入不一致！");
				a2.setContentText("请重新输入");
				
				a2.showAndWait();
				this.pw1.clear();
				this.pw2.clear();
			}
		}
		else{
			Alert a1=new Alert(AlertType.WARNING);
			a1.setHeaderText("密码输入错误！");
		    a1.setTitle("警告");
		    a1.setContentText("请正确输入密码");
		    a1.showAndWait();
		    this.old.clear();
		    this.pw1.clear();
		    this.pw2.clear();
		}
	}
	
	@FXML
	public void onCancel(){
		this.stage.close();
	}
	
	public void setStage(Stage s){
		stage=s;
	}
}
