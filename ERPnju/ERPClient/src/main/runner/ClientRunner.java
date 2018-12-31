package main.runner;

import java.io.IOException;
import java.rmi.RemoteException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.Chat.Client;
import main.rmi.RemoteHelper;
import main.ui.logui.LogHelper;
import main.ui.mainui.LoginUIController;

public class ClientRunner extends Application {

	private AnchorPane pane;
	private static String currentUser;
	private static LogHelper log;
	private static Client chatClient;
	
	public static void setUser(String userid){
		currentUser=userid;
		log=currentUser==null?null:new LogHelper(currentUser);
	}
	
	public static String getUser(){
		return currentUser;
	}
	
	public static int getUserLevel(){
		try {
			return RemoteHelper.getInstance().getUserblService().showUser(currentUser).getPower();
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return 0;
	}
	
	public static LogHelper getLogger(){
		return log;
	}
	
	public ClientRunner(){}
	
	public static void initChatClient(){
		try {
			chatClient=new Client();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("run the chat client");
		chatClient.run();//暂时在这里跑
	}
	
	public static Client getChatClient(){
		return chatClient;
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader=new FXMLLoader();
		
//		System.out.println(getClass().getResource("/src/main/ui/mainui/LoginUI.fxml"));
		loader.setLocation(getClass().getResource("/main/ui/mainui/LoginUI.fxml"));
//		System.out.println(loader.getLocation());
		
		pane=(AnchorPane)loader.load();
		LoginUIController mc=(LoginUIController)loader.getController();
		
		/**
		 * 在关闭程序时
		 * 保存信息
		 * 关闭socket
		 */
		arg0.setOnCloseRequest((WindowEvent we)->{
			if(chatClient!=null)
				chatClient.onExit();
		});
		
		mc.setStage(arg0);
		Scene scene=new Scene(pane);
		arg0.setScene(scene);
		arg0.show();
		arg0.setResizable(false);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientRunner cr=new ClientRunner();
		launch(args);
	}

}
