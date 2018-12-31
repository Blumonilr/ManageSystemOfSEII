package main.ui.mainui;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.businesslogicservice.userblservice.UserblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.userui.UserUIController;

public class LoginUIController implements Initializable {
	@FXML JFXTextField id;
	@FXML JFXPasswordField password;
	@FXML JFXButton ok;
	@FXML Label pwValidator;
	
	private UserblService userblservice=RemoteHelper.getInstance().getUserblService();
	private static String currentUser;
	private Stage stage=null;
	private Stage subStage=null;

	
	public void setStage(Stage s){
		stage=s;
		stage.setHeight(550);
		stage.setWidth(400);
		stage.initStyle(StageStyle.TRANSPARENT);
		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setMessage("请输入用户名");
		id.getValidators().add(validator);
		id.focusedProperty().addListener((o,oldVal,newVal)->{
		    if(!newVal) id.validate();
		});
		
		RequiredFieldValidator validator2 = new RequiredFieldValidator();
		validator2.setMessage("请输入密码");
		password.getValidators().add(validator2);
		password.focusedProperty().addListener((o,oldVal,newVal)->{
			pwValidator.setText(null);
		    if(!newVal) password.validate();
		});
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO 自动生成的方法存根
		
	}
	
	@FXML
	public void onLogin(){
		String id=this.id.getText();
		int type=0;
		String pw=this.password.getText();
	    boolean res=false;
	    try {
			res=userblservice.userlogin(id, pw);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    if(res){
	    	//登录成功
	    	currentUser=id;
	    	ClientRunner.setUser(currentUser);
	    	ClientRunner.initChatClient();
	    	try {
				type=userblservice.showUser(id).getType();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
	    	subStage=new Stage();
	    	subStage.setHeight(850);
	    	subStage.setWidth(1205);
	    	AnchorPane pane=null;
	    	FXMLLoader loader=new FXMLLoader();
	    	switch(type){
	    	
	    	case 0:
	    		loader.setLocation(UserUIController.class.getResource("UserUI.fxml"));
	    		try {
					pane=loader.load();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		UserUIController c0=loader.getController();
				c0.setMainController(this);
				c0.onInit();
	    		break;
	    	case 1:
	    		loader.setLocation(getClass().getResource("StockMainUI.fxml"));
	    		try {
					pane=loader.load();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		StockMainUIController controller=loader.getController();
				controller.init(this);
	    		break;
	    	case 2:
				loader.setLocation(getClass().getResource("PurchaseSaleMainWindow.fxml"));
				try {
					pane=loader.load();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PurchaseSaleMainWindowController pscontroller=(PurchaseSaleMainWindowController)loader.getController();
				pscontroller.init(this);
	    		break;
	    	case 3:
				loader.setLocation(getClass().getResource("FinanceMainUI.fxml"));
				try {
					pane=loader.load();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				FinanceMainUIController financeController=loader.getController();
				financeController.init(this);
	    		break;
	    	case 4:
	    		loader.setLocation(getClass().getResource("ManagerMainUI.fxml"));
				try {
					pane=loader.load();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ManagerMainController c=(ManagerMainController)loader.getController();
				c.setController(this);
	    		break;
	    	}
	    	
	    	Scene scene=new Scene(pane);
	    	subStage.setScene(scene);
	    	subStage.show();
	    	this.stage.close();
	    }
	    else{
	    	//失败
	    	if(password.getText().length()>0)
	    		pwValidator.setText("用户名或密码错误");
	    }
	}
	
	@FXML
	public void onCancel(){
		this.stage.close();
		System.exit(0);
	}
	
	/**
	 * 此方法用于登出后，重置界面
	 */
	public void reset(){
		stage.show();
		id.setText(null);
		password.setText(null);
		subStage.close();
		subStage=null;
	}
}
