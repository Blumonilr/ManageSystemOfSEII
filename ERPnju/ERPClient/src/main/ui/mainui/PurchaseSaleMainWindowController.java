package main.ui.mainui;

import java.io.IOException;
import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.clientui.ClientUIController;
import main.ui.logui.LogUIController;
import main.ui.purchaseui.PurchaseDraftUIController;
import main.ui.purchaseui.PurchaseReturnUIController;
import main.ui.purchaseui.PurchaseUIController;
import main.ui.purchaseui.PurchaseUnrevisedUIController;
import main.ui.saleui.SaleDraftUIController;
import main.ui.saleui.SaleReturnUIController;
import main.ui.saleui.SaleUIController;
import main.ui.saleui.SaleUnrevisedUIController;
import main.ui.util.MessageController;

public class PurchaseSaleMainWindowController {
	
	private String currentUserId=ClientRunner.getUser();//由登录界面传入
	LoginUIController controller;//登录界面
	
	MessageController mc;
	
	/**
	 * 上方导航栏
	 */
	@FXML
	ImageView redBall;
	
	@FXML
	ImageView envelope;

	
	/**
	 * 左方导航栏
	 */
	@FXML
	Button purchase_btn;
	@FXML
	Button purchaseReturn_btn;
	@FXML
	Button purchaseUnrevised_btn;
	@FXML
	Button purchaseDraft_btn;
	
	@FXML
	Button sale_btn;
	@FXML
	Button saleReturn_btn;
	@FXML
	Button saleUnrevised_btn;
	@FXML
	Button saleDraft_btn;
	
	@FXML
	Button clientAdd_btd;
	@FXML
	Button clientDelete_btd;
	@FXML
	Button clientModify_btd;
	@FXML
	Button clientSearch_btd;
	
	/**
	 * 主工作区
	 */
	@FXML
	TabPane mainPane;
	
	/**
	 * 图片url
	 */
	String redBall_url="file:src/resources/mainui_resources/redball.png";//消息红点
	
	/**
	 * 文字标签
	 */
	String purchaseTabText="进货";
	String purchaseReturnTabText="进货退货";
	String purchaseUnrevisedTabText="进货未审批";
	String purchaseDraftTabText="进货草稿";
	
	String saleTabText="销售";
	String saleReturnTabText="销售退货";
	String saleUnrevisedTabText="销售未审批";
	String saleDraftTabText="销售草稿";
	
	String clientAddTabText="客户管理";
	
	String messageTabText="消息";
	String logTabText="日志";
	
	
	/**
	 * 设置当前用户
	 * @param currrentUserId
	 */
	public void setUser(){
		this.currentUserId=ClientRunner.getUser();
	}
	
	/**
	 * 设置返回登录界面的Controller
	 * @param controller
	 */
	public void init(LoginUIController controller){
		this.controller=controller;
	}
	
	
	/**
	 * 左侧导航栏 进货处理
	 */
	@FXML
	public void onPurchase_btn(){
		System.out.println("purchase");
		boolean isopen=false;
		
		for(Tab t:mainPane.getTabs()){
			//判断是否已经打开
			if(t.getText().equals(this.purchaseTabText)){
				isopen=true;
				break;
			}
		}
	
		if(!isopen){
			Tab tab=new Tab(this.purchaseTabText);
			tab.setContent(this.getPurchasePane());
			mainPane.getTabs().add(tab);
			mainPane.getSelectionModel().select(mainPane.getTabs().size()-1);
		}
	}
	
	@FXML
	public void onPurchaseReturn_btn(){
		System.out.println("purchase return");
		boolean isopen=false;
		for(Tab t:mainPane.getTabs()){
			//判断是否已经打开
			if((t.getText()).equals(this.purchaseReturnTabText)){
				isopen=true;
				break;
			}
		}
		
		if(!isopen){
			Tab tab=new Tab(this.purchaseReturnTabText);
			tab.setContent(this.getPurchaseReturnPane());
			mainPane.getTabs().add(tab);
			mainPane.getSelectionModel().select(mainPane.getTabs().size()-1);
		}
	}
	
	@FXML
	public void onPurchaseUnrevised_btn(){
		System.out.println("purchase unrevised");
		boolean isopen=false;
		for(Tab t:mainPane.getTabs()){
			//判断是否已经打开
			if(t.getText().equals(this.purchaseUnrevisedTabText)){
				isopen=true;
				break;
			}
		}
		
		if(!isopen){
			Tab tab=new Tab(this.purchaseUnrevisedTabText);
			tab.setContent(this.getPurchaseUnrevisedPane());
			mainPane.getTabs().add(tab);
			mainPane.getSelectionModel().select(mainPane.getTabs().size()-1);
		}
	}
	
	@FXML
	public void onPurchaseDraft_btn(){
		System.out.println("purchase draft");
		boolean isopen=false;
		for(Tab t:mainPane.getTabs()){
			//判断是否已经打开
			if(t.getText().equals(this.purchaseDraftTabText)){
				isopen=true;
				break;
			}		
		}
		
		if(!isopen){
			Tab tab=new Tab(this.purchaseDraftTabText);
			tab.setContent(this.getPurchaseDraftPane());
			mainPane.getTabs().add(tab);
			mainPane.getSelectionModel().select(mainPane.getTabs().size()-1);
		}
	}
	
	/**
	 * 左侧导航栏 销售处理
	 */
	@FXML
	public void onSale_btn(){
		System.out.println("sale");
		boolean isopen=false;
		for(Tab t:mainPane.getTabs()){
			//判断是否已经打开
			if(t.getText().equals(this.saleTabText)){
				isopen=true;
				break;
			}
		}
		
		if(!isopen){
			Tab tab=new Tab(this.saleTabText);
			tab.setContent(this.getSalePane());
			mainPane.getTabs().add(tab);
			mainPane.getSelectionModel().select(mainPane.getTabs().size()-1);
		}
	}
	
	@FXML
	public void onSaleReturn_btn(){
		System.out.println("sale return");
		boolean isopen=false;
		for(Tab t:mainPane.getTabs()){
			//判断是否已经打开
			if(t.getText().equals(this.saleReturnTabText)){
				isopen=true;
				break;
			}
		}
		
		if(!isopen){
			Tab tab=new Tab(this.saleReturnTabText);
			tab.setContent(this.getSaleReturnPane());
			mainPane.getTabs().add(tab);
			mainPane.getSelectionModel().select(mainPane.getTabs().size()-1);
		}
	}
	
	@FXML
	public void onSaleUnrevised_btn(){
		System.out.println("sale unrevised");
		boolean isopen=false;
		for(Tab t:mainPane.getTabs()){
			//判断是否已经打开
			if(t.getText().equals(this.saleUnrevisedTabText)){
				isopen=true;
				break;
			}
		}
		
		if(!isopen){
			Tab tab=new Tab(this.saleUnrevisedTabText);
			tab.setContent(this.getSaleUnrevisedPane());
			mainPane.getTabs().add(tab);
			mainPane.getSelectionModel().select(mainPane.getTabs().size()-1);
		}
	}
	
	@FXML
	public void onSaleDraft_btn(){
		System.out.println("sale draft");
		boolean isopen=false;
		for(Tab t:mainPane.getTabs()){
			//判断是否已经打开
			if(t.getText().equals(this.saleDraftTabText)){
				isopen=true;
				break;
			}
		}
		
		if(!isopen){
			Tab tab=new Tab(this.saleDraftTabText);
			tab.setContent(this.getSaleDraftPane());
			mainPane.getTabs().add(tab);
			mainPane.getSelectionModel().select(mainPane.getTabs().size()-1);
		}
	}
	
	/**
	 * 左侧导航栏 客户管理
	 */
	@FXML
	public void onAddClient_btn(){
		System.out.println("add client");
		boolean isopen=false;
		for(Tab t:mainPane.getTabs()){
			//判断是否已经打开
			if(t.getText().equals(this.clientAddTabText)){
				isopen=true;
				break;
			}
		}
		
		if(!isopen){
			Tab tab=new Tab(this.clientAddTabText);
			tab.setContent(this.getAddClientPane());
			mainPane.getTabs().add(tab);
			mainPane.getSelectionModel().select(mainPane.getTabs().size()-1);
		}
	}
	
	@FXML
	public void onLog_btn(){
		boolean isopen=false;
		for(Tab t:mainPane.getTabs()){
			//判断是否已经打开
			if(t.getText().equals(this.logTabText)){
				isopen=true;
				break;
			}
		}
		
		if(!isopen){
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(LogUIController.class.getResource("LogUI.fxml"));
			AnchorPane pane=null;
			LogUIController controller=null;
			try {
				pane=loader.load();
				controller=(LogUIController)loader.getController();
				controller.init();
				controller.showLog();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Tab tab=new Tab(this.logTabText);
			tab.setContent(pane);
			mainPane.getTabs().add(tab);
			mainPane.getSelectionModel().select(mainPane.getTabs().size()-1);
		}
		
	}
	
	
	/**
	 * 以下是私有方法 用来返回相应的mainPane上的tagpane
	 * 
	 */
	private AnchorPane getPurchasePane(){
		AnchorPane pane=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(PurchaseUIController.class.getResource("PurchaseUI.fxml"));
		try {
			pane=(AnchorPane)loader.load();
			PurchaseUIController c=loader.getController();
			c.setUser(currentUserId);
			c.onInit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pane;
	}
	
	private AnchorPane getPurchaseReturnPane(){
		AnchorPane pane=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(PurchaseReturnUIController.class.getResource("PurchaseReturnUI.fxml"));
		try {
			pane=(AnchorPane)loader.load();
			PurchaseReturnUIController c=loader.getController();
			c.setUser(currentUserId);
			c.onInit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pane;
	}
	
	private AnchorPane getPurchaseUnrevisedPane(){
		AnchorPane pane=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(PurchaseUnrevisedUIController.class.getResource("PurchaseUnrevisedUI.fxml"));
		try {
			pane=(AnchorPane)loader.load();
			PurchaseUnrevisedUIController c=loader.getController();
			c.setUser(currentUserId);
			c.onInit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pane;
	}
	
	private AnchorPane getPurchaseDraftPane(){
		AnchorPane pane=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(PurchaseDraftUIController.class.getResource("PurchaseDraftUI.fxml"));
		try {
			pane=(AnchorPane)loader.load();
			PurchaseDraftUIController c=loader.getController();
			c.setUser(currentUserId);
			c.onInit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pane;
	}
	
	private AnchorPane getSalePane(){
		AnchorPane pane=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(SaleUIController.class.getResource("SaleUI.fxml"));
		try {
			pane=(AnchorPane)loader.load();
			SaleUIController c=loader.getController();
			c.setUser(currentUserId);
			c.onInit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pane;
	}
	
	private AnchorPane getSaleReturnPane(){
		AnchorPane pane=null;
		FXMLLoader looader=new FXMLLoader();
		looader.setLocation(SaleReturnUIController.class.getResource("SaleReturnUI.fxml"));
		try {
			pane=(AnchorPane)looader.load();
			SaleReturnUIController c=(SaleReturnUIController)looader.getController();
			c.setUser(currentUserId);
			c.onInit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pane;
	}
	
	private AnchorPane getSaleUnrevisedPane(){
		AnchorPane pane=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(SaleUnrevisedUIController.class.getResource("SaleUnrevisedUI.fxml"));
		try {
			pane=(AnchorPane)loader.load();
			SaleUnrevisedUIController c=(SaleUnrevisedUIController)loader.getController();
			c.onInit();
			c.setUser(currentUserId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pane;
	}
	
	private AnchorPane getSaleDraftPane(){
		AnchorPane pane=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(SaleDraftUIController.class.getResource("SaleDraftUI.fxml"));
		try {
			pane=(AnchorPane)loader.load();
			SaleDraftUIController c=(SaleDraftUIController)loader.getController();
			c.setUser(currentUserId);
			c.onInit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pane;
	}
	
	private AnchorPane getAddClientPane(){
		AnchorPane pane=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(ClientUIController.class.getResource("ClientUI.fxml"));
		try {
			pane=(AnchorPane)loader.load();
			ClientUIController c=(ClientUIController)loader.getController();
			c.onInit();
			c.onSearch();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pane;
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
	
	
	//有新消息时，显示小红点redDot.setImage(new Image("resources\\mainui_resources\\redball.png"));
		@FXML
		public void onMessage_btn(){
			ClientRunner.getLogger().record("查看消息");

			boolean isopen=false;
			for(Tab tab:mainPane.getTabs()){
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
				mainPane.getTabs().add(t);
			}
			//去掉小红点
			redBall.setImage(null);
			
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
	
}


