package main.ui.mainui;

import java.io.IOException;
import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.logui.LogUIController;
import main.ui.stockui.classui.ClassUIController;
import main.ui.stockui.goodsui.GoodsUIController;
import main.ui.stockui.stockui.OverflowReceiptUIController;
import main.ui.stockui.stockui.StockCheckController;
import main.ui.stockui.stockui.StockInventoryController;
import main.ui.stockui.stockui.UnderflowReceiptUIController;
import main.ui.util.MessageController;

public class StockMainUIController {

	private LoginUIController controller;
	private ClassUIController cuic=null;
	private GoodsUIController guic=null;
	private StockCheckController scc=null;
	private StockInventoryController sic=null;
	private LogUIController luic=null;
	private MessageController mc=null;
	private OverflowReceiptUIController overc=null;
	private UnderflowReceiptUIController underc=null;
	@FXML public TabPane tabPane;
	@FXML public ImageView redDot;
	

	public void init(LoginUIController controller){
		this.controller=controller;
	}
	
	@FXML
	public void showClass(){
		boolean isopen=false;
		for(Tab tab:tabPane.getTabs()){
			if((tab).getText().equals("商品分类管理")){
				isopen=true;
				break;
			}
		}
		if(!isopen){		
			//获得商品分类管理的界面
			Tab t=new Tab("商品分类管理");
			t.setContent(loadClassUI());
			//关闭窗口时注销相应的控制器
			t.setOnClosed(x->this.cuic=null);
			tabPane.getTabs().add(t);
			tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
			cuic.showClass();
		}
	}
	
	private AnchorPane loadClassUI(){
		AnchorPane layout=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(ClassUIController.class.getResource("ClassUI.fxml"));
		try {
			layout=loader.load();
			cuic=(ClassUIController)loader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return layout;
		
	}
	
	@FXML
	public void showGoods(){
		boolean isopen=false;
		for(Tab tab:tabPane.getTabs()){
			if((tab).getText().equals("商品管理")){
				isopen=true;
				break;
			}
		}
		if(!isopen){			
			//获得商品管理的界面
			Tab t=new Tab("商品管理");
			t.setContent(loadGoodsUI());
			t.setOnClosed(x->this.guic=null);
			tabPane.getTabs().add(t);
			tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
			guic.showGoods();
		}
	}
	
	@FXML
	public void overflowUI(){
		boolean isopen=false;
		for(Tab tab:tabPane.getTabs()){
			if((tab).getText().equals("报溢单")){
				isopen=true;
				break;
			}
		}
		if(!isopen){			
			//获得报溢单的界面
			Tab t=new Tab("报溢单");
			t.setContent(loadOverflowUI());
			t.setOnClosed(x->this.overc=null);
			tabPane.getTabs().add(t);
			tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
			overc.onInit();
		}
	}
	
	private AnchorPane loadOverflowUI(){
		AnchorPane layout=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(OverflowReceiptUIController.class.getResource("OverflowReceiptUI.fxml"));
		try {
			layout=loader.load();
			overc=(OverflowReceiptUIController)loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return layout;
	}
	
	@FXML
	public void underflowUI(){
		boolean isopen=false;
		for(Tab tab:tabPane.getTabs()){
			if((tab).getText().equals("报损单")){
				isopen=true;
				break;
			}
		}
		if(!isopen){			
			//获得报溢单的界面
			Tab t=new Tab("报损单");
			t.setContent(loadUnderflowUI());
			t.setOnClosed(x->this.underc=null);
			tabPane.getTabs().add(t);
			tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
			underc.onInit();
		}
	}
	
	private AnchorPane loadUnderflowUI(){
		AnchorPane layout=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(UnderflowReceiptUIController.class.getResource("UnderflowReceiptUI.fxml"));
		try {
			layout=loader.load();
			underc=(UnderflowReceiptUIController)loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return layout;
	}
	
	private AnchorPane loadGoodsUI(){
		AnchorPane layout=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(GoodsUIController.class.getResource("GoodsUI.fxml"));
		try {
			layout=loader.load();
			guic=(GoodsUIController)loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return layout;
		
	}
	
	@FXML
	public void stockCheck(){
		boolean isopen=false;
		for(Tab tab:tabPane.getTabs()){
			if((tab).getText().equals("库存查看")){
				isopen=true;
				break;
			}
		}
		if(!isopen){			
			//获得商品管理的界面
			Tab t=new Tab("库存查看");
			t.setContent(loadStockCheck());
			t.setOnClosed(x->this.scc=null);
			tabPane.getTabs().add(t);
			tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
			scc.stockCheck();
		}
	}
	
	private AnchorPane loadStockCheck(){
		AnchorPane layout=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(StockCheckController.class.getResource("StockCheck.fxml"));
		try {
			layout=loader.load();
			scc=(StockCheckController)loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return layout;
	}
	
	@FXML
	public void stockInventory(){
		boolean isopen=false;
		for(Tab tab:tabPane.getTabs()){
			if((tab).getText().equals("库存盘点")){
				isopen=true;
				break;
			}
		}
		if(!isopen){			
			//获得商品管理的界面
			Tab t=new Tab("库存盘点");
			t.setContent(loadStockInventory());
			t.setOnClosed(x->this.sic=null);
			tabPane.getTabs().add(t);
			tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
			sic.stockInventory();
		}
	}
	
	private AnchorPane loadStockInventory(){
		AnchorPane layout=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(StockCheckController.class.getResource("StockInventory.fxml"));
		try {
			layout=loader.load();
			sic=(StockInventoryController)loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return layout;
	}
	
	@FXML
	public void openLog(){

		boolean isopen=false;
		for(Tab tab:tabPane.getTabs()){
			if((tab).getText().equals("操作日志")){
				isopen=true;
				break;
			}
		}
		if(!isopen){		
			//获得商品分类管理的界面
			Tab t=new Tab("操作日志");
			t.setContent(loadLogUI());
			//关闭窗口时注销相应的控制器
			t.setOnClosed(x->this.luic=null);
			tabPane.getTabs().add(t);
			tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
			luic.showLog();
		}
		
	}
	
	private AnchorPane loadLogUI(){
		AnchorPane layout=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(LogUIController.class.getResource("LogUI.fxml"));
		try {
			layout=loader.load();
			luic=(LogUIController)loader.getController();
			luic.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return layout;
	}
	
	//有新消息时，显示小红点redDot.setImage(new Image("resources\\mainui_resources\\redball.png"));
	@FXML
	public void openMsg(){
		ClientRunner.getLogger().record("查看消息");

		boolean isopen=false;
		for(Tab tab:tabPane.getTabs()){
			if((tab).getText().equals("系统消息")){
				isopen=true;
				break;
			}
		}
		if(!isopen){		
			//获得商品分类管理的界面
			Tab t=new Tab("系统消息");
			t.setContent(loadMessageUI());
			//关闭窗口时注销相应的控制器
			t.setOnClosed(x->{this.mc=null;ClientRunner.getChatClient().setController(null);});
			tabPane.getTabs().add(t);
			tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
		}
		//去掉小红点
		redDot.setImage(null);
		
	}
	
	private AnchorPane loadMessageUI(){
		AnchorPane layout=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MessageController.class.getResource("MessageUI.fxml"));
		try {
			layout=loader.load();
			mc=(MessageController)loader.getController();
			mc.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return layout;
	}
	
	@FXML
	public void onLogOut(){
		boolean res=false;
		try {
			res=RemoteHelper.getInstance().getUserblService().userlogout(ClientRunner.getUser());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(res){
			try {
				RemoteHelper.getInstance().getChatService().remove(ClientRunner.getUser());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ClientRunner.setUser(null);
			System.out.println("log out,current user = "+ClientRunner.getUser());
			controller.reset();
		}
	}
}
