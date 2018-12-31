package main.ui.mainui;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.formui.BusinessListUIController;
import main.ui.formui.BusinessProcessListController;
import main.ui.formui.SaleDetailListController;
import main.ui.logui.LogUIController;
import main.ui.reviseui.ReviseController;
import main.ui.strategyui.ClientNewStrategyController;
import main.ui.strategyui.TotalNewStrategyController;
import main.ui.strategyui.PackageNewStrategyController;
import main.ui.strategyui.ShowStrategyController;
import main.ui.util.MessageController;

public class ManagerMainController implements Initializable{
	
	ReviseController rc=null;
	ClientNewStrategyController cnsc=null;
	TotalNewStrategyController tnsc=null;
	PackageNewStrategyController pnsc=null;
	ShowStrategyController ssc=null;
	SaleDetailListController sdlc=null;
	BusinessProcessListController bplc=null;
	BusinessListUIController blc=null;
	MessageController mc=null;
	LogUIController lc=null;
	LoginUIController mainController;
	
	static String currentUser;
	
	@FXML
	TitledPane RevisePane;
	@FXML
	TitledPane BookPane;
	@FXML
	TitledPane StrategyPane;
	@FXML
	TabPane tab;
	@FXML
	Button ReviseButton;
	@FXML
	Button BookButton1;
	@FXML
	Button BookButton2;
	@FXML
	Button BookButton3;
	@FXML
	Button NewButton1;
	@FXML
	Button NewButton2;
	@FXML
	Button NewButton3;
	@FXML
	Button ShowButton;
	@FXML
	Button message;
	@FXML
	ImageView redBall;
	@FXML
	Button logout;
	@FXML
	Button diary;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		currentUser=ClientRunner.getUser();
	}
	
	public void setController(LoginUIController c){
		this.mainController=c;
	}
	
	@FXML
	public void onLogOut(){
		boolean res = false;
        try {
            res = RemoteHelper.getInstance().getUserblService().userlogout(ClientRunner.getUser());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (res) {
        	try {
				RemoteHelper.getInstance().getChatService().remove(ClientRunner.getUser());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            ClientRunner.setUser(null);
            System.out.println("log out,current user = " + ClientRunner.getUser());
            mainController.reset();
        }
	}
	
	@FXML
	public void addReviseTab(){
		boolean isOpen=false;
		for(Tab t:tab.getTabs())
			if(t.getText().equals("审批单据")){
				isOpen=true;
				break;
			}
		if(!isOpen){
			Tab ReviseTab=new Tab("审批单据");
			ReviseTab.setContent(getReviseAnchorPane());
			ReviseTab.setOnClosed(x->this.rc=null);
			tab.getTabs().add(ReviseTab);
			tab.getSelectionModel().select(tab.getTabs().size()-1);
		}
	}
	
    @FXML
    public void openMsg(){
        ClientRunner.getLogger().record("查看消息");
        boolean isopen=false;
        for(Tab tab:tab.getTabs()){
            if((tab).getText().equals("系统消息")){
                isopen=true;
                break;
            }
        }
        if(!isopen){
            //获得商品分类管理的界面
            Tab t=new Tab("系统消息");
            t.setContent(getMessageAnchorPane());
            t.setOnClosed(x->this.mc=null);
            tab.getTabs().add(t);
            tab.getSelectionModel().select(tab.getTabs().size()-1);
        }
        //去掉小红点
        redBall.setImage(null);
    }
	
	private AnchorPane getMessageAnchorPane() {
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
	public void addDiary(){
		boolean isOpen=false;
		for(Tab t:tab.getTabs())
			if(t.getText().equals("日志")){
				isOpen=true;
				break;
			}
		if(!isOpen){
			Tab d=new Tab("日志");
			d.setContent(getLogAnchorPane());
			d.setOnClosed(x->this.lc=null);
			tab.getTabs().add(d);
			tab.getSelectionModel().select(tab.getTabs().size()-1);
			lc.showLog();
		}
	}
	
	private AnchorPane getLogAnchorPane(){
		AnchorPane layout=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(LogUIController.class.getResource("LogUI.fxml"));
		try {
			layout=loader.load();
			lc=(LogUIController)loader.getController();
			lc.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return layout;
	}

	@FXML
	public void addSaleBookTab(){
		boolean isOpen=false;
		for(Tab t:tab.getTabs())
			if(t.getText().equals("销售明细表")){
				isOpen=true;
				break;
			}
		if(!isOpen){
			Tab SaleBookTab=new Tab("销售明细表");
			SaleBookTab.setContent(getSaleListAnchorPane());
			SaleBookTab.setOnClosed(x->this.sdlc=null);
			tab.getTabs().add(SaleBookTab);
			tab.getSelectionModel().select(tab.getTabs().size()-1);
		}
	}
	
	private AnchorPane getSaleListAnchorPane() {
		AnchorPane ap=new AnchorPane();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(SaleDetailListController.class.getResource("SaleDetailListUI.fxml"));
		try {
			ap=loader.load();
			sdlc=(SaleDetailListController)loader.getController();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return ap;
	}

	@FXML
	public void addBussinessProcessBookTab(){
		boolean isOpen=false;
		for(Tab t:tab.getTabs())
			if(t.getText().equals("经营历程表")){
				isOpen=true;
				break;
			}
		if(!isOpen){
			Tab BussinessProcessBookTab=new Tab("经营历程表");
			BussinessProcessBookTab.setContent(getBusinessProcessAnchorPane());
			BussinessProcessBookTab.setOnClosed(x->this.bplc=null);
			tab.getTabs().add(BussinessProcessBookTab);
			tab.getSelectionModel().select(tab.getTabs().size()-1);
		}
	}
	
	private AnchorPane getBusinessProcessAnchorPane() {
		AnchorPane ap=new AnchorPane();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(BusinessProcessListController.class.getResource("BusinessProcessListUI.fxml"));
		try {
			ap=loader.load();
			bplc=(BusinessProcessListController)loader.getController();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return ap;
	}

	@FXML
	public void addBussinessListBookTab(){
		boolean isOpen=false;
		for(Tab t:tab.getTabs())
			if(t.getText().equals("经营情况表")){
				isOpen=true;
				break;
			}
		if(!isOpen){
			Tab BussinessListBookTab=new Tab("经营情况表");
			BussinessListBookTab.setContent(getBusinessListAnchorPane());
			BussinessListBookTab.setOnClosed(x->this.blc=null);
			tab.getTabs().add(BussinessListBookTab);
			tab.getSelectionModel().select(tab.getTabs().size()-1);
		}
	}
	
	private AnchorPane getBusinessListAnchorPane() {
		AnchorPane ap=new AnchorPane();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(BusinessListUIController.class.getResource("BusinessListUI.fxml"));
		try {
			ap=loader.load();
			blc=(BusinessListUIController)loader.getController();
			blc.init();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return ap;
	}

	@FXML
	public void addClientNewStrategyTab(){
		if(cnsc==null){
			Tab ClientNewStrategyTab=new Tab("新建促销策略");
			ClientNewStrategyTab.setContent(getClientNewStrategyAnchorPane());
			ClientNewStrategyTab.setOnClosed(x->this.cnsc=null);
			tab.getTabs().add(ClientNewStrategyTab);
			tab.getSelectionModel().select(tab.getTabs().size()-1);
		}
	}
	
	@FXML
	public void addTotalNewStrategyTab(){
		if(tnsc==null){
			Tab TotalNewStrategyTab=new Tab("新建促销策略");
			TotalNewStrategyTab.setContent(getTotalNewStrategyAnchorPane());
			TotalNewStrategyTab.setOnClosed(x->this.tnsc=null);
			tab.getTabs().add(TotalNewStrategyTab);
			tab.getSelectionModel().select(tab.getTabs().size()-1);
		}
	}
	
	@FXML
	public void addPackageNewStrategyTab(){
		if(pnsc==null){
			Tab PackageNewStrategyTab=new Tab("新建促销策略");
			PackageNewStrategyTab.setContent(getPackageNewStrategyAnchorPane());
			PackageNewStrategyTab.setOnClosed(x->this.pnsc=null);
			tab.getTabs().add(PackageNewStrategyTab);
			tab.getSelectionModel().select(tab.getTabs().size()-1);
		}
	}
	
	@FXML
	public void addShowStrategyTab(){
		boolean isOpen=false;
		for(Tab t:tab.getTabs())
			if(t.getText().equals("查看促销策略")){
				isOpen=true;
				break;
			}
		if(!isOpen){
			Tab shows=new Tab("查看促销策略");
			shows.setContent(getShowStrategyController());
			shows.setOnClosed(x->this.ssc=null);
			tab.getTabs().add(shows);
			tab.getSelectionModel().select(tab.getTabs().size()-1);
		}
	}
	
	public AnchorPane getReviseAnchorPane(){
		AnchorPane ap=new AnchorPane();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(ReviseController.class.getResource("ReviseUi.fxml"));
		try {
			ap=loader.load();
			rc=(ReviseController)loader.getController();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return ap;
	}
	
	public AnchorPane getClientNewStrategyAnchorPane(){
		AnchorPane ap=new AnchorPane();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(ClientNewStrategyController.class.getResource("ClientNewStrategyUi.fxml"));
		try {
			ap=loader.load();
			cnsc=(ClientNewStrategyController)loader.getController();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return ap;
	}
	
	public AnchorPane getTotalNewStrategyAnchorPane(){
		AnchorPane ap=new AnchorPane();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(TotalNewStrategyController.class.getResource("TotalNewStrategyUi.fxml"));
		try {
			ap=loader.load();
			tnsc=(TotalNewStrategyController)loader.getController();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return ap;
	}
	
	public AnchorPane getPackageNewStrategyAnchorPane(){
		AnchorPane ap=new AnchorPane();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(PackageNewStrategyController.class.getResource("PackageNewStrategyUi.fxml"));
		try {
			ap=loader.load();
			pnsc=(PackageNewStrategyController)loader.getController();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return ap;
	}
	
	private AnchorPane getShowStrategyController(){
		AnchorPane ap=new AnchorPane();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(ShowStrategyController.class.getResource("ShowStrategyUi.fxml"));
		try {
			ap=loader.load();
			ssc=(ShowStrategyController)loader.getController();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return ap;
	}
}
