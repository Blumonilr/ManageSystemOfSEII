package main.ui.util;

import java.rmi.RemoteException;
import java.util.ArrayList;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.businesslogicservice.userblservice.UserblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.vo.UserVO;

public class MessageController {
	
	@FXML JFXListView<String> receive;
	@FXML TextArea input;
	@FXML TextField targetUser;
	@FXML JFXListView<String>userList;
	@FXML Label warnings;
	
	private main.Chat.Client chatClient;
	private ArrayList<UserVO>list;
	
	@FXML
	public void onSend(){
		if(targetUser.getText()==null||targetUser.getText().equals("")){
			warnings.setText("请输入接收人！");
		}else{
			warnings.setText(null);
			if(input.getText()==null||input.getText().equals("")){}else{
				System.out.print("input : "+(int)input.getText().charAt(0));
				
				if(targetUser.getText().equals("所有人")){
					String str=ClientRunner.getUser()+" : "+input.getText();
					for(UserVO u:list){
						String line=u.getId()+"_"+str;
						chatClient.getInput(line);
					}
					input.setText(null);
				}else{					
					String line=targetUser.getText()+"_"+ClientRunner.getUser()+" : "+input.getText();
					chatClient.getInput(line);
					input.setText(null);
//					input.fo
				}
			}
		}
	}

	public void init() {
		
		//给text area绑定回车键
		input.setOnKeyPressed((KeyEvent ke)->{
			if(ke.getCode().equals(KeyCode.ENTER)){
				onSend();
			}
		});
		
		
		/**
		 * 将消息模块与对应的controller绑定
		 * @param mc
		 */
		chatClient=ClientRunner.getChatClient();
		chatClient.setController(this);
		
		initReceive();
		
		initUserList();
	}
	
	/**
	 * 初始化消息列表
	 */
	private void initReceive(){
		ObservableList<String>ol=FXCollections.observableArrayList(chatClient.getTempMsg());
		showMsg(ol);
		
		receive.setOnMouseClicked((MouseEvent me)->{
			if(me.getButton().equals(MouseButton.PRIMARY)&&me.getClickCount()==2){
				String item=receive.getSelectionModel().getSelectedItem();
				if(item!=null){
					receive.getItems().remove(item);
				}
			}
		});
	}
	
	private void showMsg(ObservableList<String>msgs){
		receive.setItems(msgs);
	}
	
	/**
	 * 初始化用户列表
	 */
	private void initUserList(){
		UserblService users=RemoteHelper.getInstance().getUserblService();
		list=new ArrayList<>();
		ObservableList<String> ol=FXCollections.observableArrayList();
		ol.add("所有人");
		try {
			list=users.showUsers(null);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		for(UserVO u:list){
			ol.add(u.getId());
		}
		userList.setItems(ol);
		
		userList.setOnMouseClicked((MouseEvent me)->{
			if(me.getButton().equals(MouseButton.PRIMARY)&&me.getClickCount()==2){
				String item=userList.getSelectionModel().getSelectedItem();
				if(item!=null){					
					targetUser.setText(item);
				}
			}
		});
	}
	
	public void showMsg(String msg){
		ObservableList<String>ol=receive.getItems();
		//安全的更新ui内容
		Platform.runLater(new Runnable(){

			@Override
			public void run() {			
				ol.add(msg);
			}
			
		});
	}
}
