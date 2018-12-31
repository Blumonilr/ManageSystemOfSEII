package main.ui.mainui;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.accountui.AccountsUIController;
import main.ui.bookui.BookUIController;
import main.ui.bookui.firstbookui.FirstBookUIController;
import main.ui.financeui.*;
import main.ui.formui.BusinessProcessListController;
import main.ui.formui.BusinessListUIController;
import main.ui.formui.SaleDetailListController;
import main.ui.logui.LogUIController;
import main.ui.util.MessageController;

import java.io.IOException;
import java.rmi.RemoteException;

public class FinanceMainUIController extends Application {
    @FXML
    private Button message;
    @FXML
    private ImageView redDot;

    @FXML
    private Button cash;
    @FXML
    private Button collect;
    @FXML
    private Button pay;
    @FXML
    private Button draft;
    @FXML
    private Button revised;
    @FXML
    private Button unrevised;

    @FXML
    private Button book;
    @FXML
    private Button firstBook;

    @FXML
    private Button saleList;
    @FXML
    private Button recordList;
    @FXML
    private Button bussinessList;

    @FXML
    private Button accounts;

    @FXML
    private TabPane tabPane;

    BookUIController c;

    String redBall_url = "file:src/resources/mainui_resources/redball.png";//消息红点

    String cashTabText = "制定现金费用单";
    String collectTabText = "制定收款单";
    String payTabText = "制定付款单";
    String draftTabText = "草稿箱";
    String revisedTabText = "已审批单据";
    String unrevisedTabText = "待审批单据";
    String bookTabText = "期初建账";
    String firstBookTabText = "帐初状态";
    String saleListTabText = "销售明细表";
    String recordListTabText = "经营历程表";
    String bussinessListTabText = "经营情况表";
    String accountsTabText = "账户管理";

    private LoginUIController mainController;

    public void init(LoginUIController controller) { this.mainController = controller; }

    @FXML
    public void onCashBut() {
        boolean isOpen = false;
        //判断是否已打开
        for (Tab t : tabPane.getTabs()) {
            if (t.getText().equals(cashTabText)) {
                isOpen = true;
                tabPane.getSelectionModel().select(t);
                break;
            }
        }
        if (!isOpen) {
            Tab tab = new Tab(cashTabText);
            tab.setContent(this.getCashPane());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
        }
    }

    @FXML
    public void onCollectBut() {
        boolean isOpen = false;
        //判断是否已打开
        for (Tab t : tabPane.getTabs()) {
            if (t.getText().equals(collectTabText)) {
                isOpen = true;
                tabPane.getSelectionModel().select(t);
                break;
            }
        }
        if (!isOpen) {
            Tab tab = new Tab(collectTabText);
            tab.setContent(this.getCollectPane());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
        }
    }

    @FXML
    public void onPayBut() {
        boolean isOpen = false;
        //判断是否已打开
        for (Tab t : tabPane.getTabs()) {
            if (t.getText().equals(payTabText)) {
                isOpen = true;
                tabPane.getSelectionModel().select(t);
                break;
            }
        }
        if (!isOpen) {
            Tab tab = new Tab(payTabText);
            tab.setContent(this.getPayPane());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
        }
    }

    @FXML
    public void onBookBut() {
        boolean isOpen = false;
        //判断是否已打开
        for (Tab t : tabPane.getTabs()) {
            if (t.getText().equals(bookTabText)) {
                isOpen = true;
                tabPane.getSelectionModel().select(t);
                break;
            }
        }
        if (!isOpen) {
            Tab tab = new Tab(bookTabText);
            tab.setContent(this.getBookPane());
            tab.setOnCloseRequest(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    c.onCancel();
                }
            });
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
        }
    }

    @FXML
    public void onFirstBookBut() {
        boolean isOpen = false;
        //判断是否已打开
        for (Tab t : tabPane.getTabs()) {
            if (t.getText().equals(firstBookTabText)) {
                isOpen = true;
                tabPane.getSelectionModel().select(t);
                break;
            }
        }
        if (!isOpen) {
            Tab tab = new Tab(firstBookTabText);
            tab.setContent(this.getFirstBookPane());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
        }
    }

    @FXML
    public void onBussinessListBut() {
        boolean isOpen = false;
        //判断是否已打开
        for (Tab t : tabPane.getTabs()) {
            if (t.getText().equals(bussinessListTabText)) {
                isOpen = true;
                tabPane.getSelectionModel().select(t);
                break;
            }
        }
        if (!isOpen) {
            Tab tab = new Tab(bussinessListTabText);
            tab.setContent(getBusinessListPane());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
            ClientRunner.getLogger().record("查看经营情况表");
        }
    }

    @FXML
    public void onSaleListBut() {
        boolean isOpen = false;
        //判断是否已打开
        for (Tab t : tabPane.getTabs()) {
            if (t.getText().equals(saleListTabText)) {
                isOpen = true;
                tabPane.getSelectionModel().select(t);
                break;
            }
        }
        if (!isOpen) {
            Tab tab = new Tab(saleListTabText);
            tab.setContent(getSaleListPane());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
            ClientRunner.getLogger().record("查看销售明细表");
        }
    }

    @FXML
    public void onRecordListBut() {
        boolean isOpen = false;
        //判断是否已打开
        for (Tab t : tabPane.getTabs()) {
            if (t.getText().equals(recordListTabText)) {
                isOpen = true;
                tabPane.getSelectionModel().select(t);
                break;
            }
        }
        if (!isOpen) {
            Tab tab = new Tab(recordListTabText);
            tab.setContent(getRecordListPane());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
            ClientRunner.getLogger().record("查看经营历程表");
        }
    }

    @FXML
    public void onAccountsBut() {
        boolean isOpen = false;
        //判断是否已打开
        for (Tab t : tabPane.getTabs()) {
            if (t.getText().equals(accountsTabText)) {
                isOpen = true;
                tabPane.getSelectionModel().select(t);
                break;
            }
        }
        if (!isOpen) {
            Tab tab = new Tab(accountsTabText);
            tab.setContent(this.getAccountsPane());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
        }
    }

    @FXML
    public void onLogOut() {
        boolean res = false;
        try {
            res = RemoteHelper.getInstance().getUserblService().userlogout(ClientRunner.getUser());
        } catch (RemoteException e) {
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
            System.out.println("log out,current user = " + ClientRunner.getUser());
            mainController.reset();
        }
    }

    @FXML
    public void onDraft(){
        boolean isOpen = false;
        //判断是否已打开
        for (Tab t : tabPane.getTabs()) {
            if (t.getText().equals(draftTabText)) {
                isOpen = true;
                tabPane.getSelectionModel().select(t);
                break;
            }
        }
        if (!isOpen) {
            Tab tab = new Tab(draftTabText);
            tab.setContent(this.getDraftPane());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
        }
    }

    @FXML
    public void onRevisedReceipt(){
        boolean isOpen = false;
        //判断是否已打开
        for (Tab t : tabPane.getTabs()) {
            if (t.getText().equals(revisedTabText)) {
                isOpen = true;
                tabPane.getSelectionModel().select(t);
                break;
            }
        }
        if (!isOpen) {
            Tab tab = new Tab(revisedTabText);
            tab.setContent(this.getRevisedReceiptPane());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
        }
    }

    @FXML
    public void onUnrevisedReceipt(){
        boolean isOpen = false;
        //判断是否已打开
        for (Tab t : tabPane.getTabs()) {
            if (t.getText().equals(unrevisedTabText)) {
                isOpen = true;
                tabPane.getSelectionModel().select(t);
                break;
            }
        }
        if (!isOpen) {
            Tab tab = new Tab(unrevisedTabText);
            tab.setContent(this.getUnrevisedReceiptPane());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
        }
    }

    @FXML
    public void openMsg(){
        ClientRunner.getLogger().record("查看消息");

        boolean isopen=false;
        for(Tab tab:tabPane.getTabs()){
            if((tab).getText().equals("系统消息")){
                isopen=true;
                tabPane.getSelectionModel().select(tab);
                break;
            }
        }
        if(!isopen){
            Tab t=new Tab("系统消息");
            t.setContent(this.loadMessageUI());
            tabPane.getTabs().add(t);
            tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
        }
        //去掉小红点
        redDot.setImage(null);
    }


    @FXML
    public void openLog(){
        boolean isopen=false;
        for(Tab tab:tabPane.getTabs()){
            if((tab).getText().equals("操作日志")){
                isopen=true;
                tabPane.getSelectionModel().select(tab);
                break;
            }
        }
        if(!isopen){
            Tab t=new Tab("操作日志");
            t.setContent(loadLogUI());
            tabPane.getTabs().add(t);
            tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
        }
    }

    private AnchorPane loadLogUI(){
        AnchorPane layout=null;
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(LogUIController.class.getResource("LogUI.fxml"));
        try {
            layout=loader.load();
            LogUIController luic=(LogUIController)loader.getController();
            luic.init();
            luic.showLog();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return layout;
    }

    public void closeTab() {
        try {
            tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedIndex());
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    private AnchorPane loadMessageUI(){
        AnchorPane layout=null;
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(MessageController.class.getResource("MessageUI.fxml"));
        try {
            layout=loader.load();
            MessageController mc=(MessageController)loader.getController();
            mc.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return layout;
    }

    private AnchorPane getCashPane() {
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CashUIController.class.getResource("CashUI.fxml"));
        try {
            pane = (AnchorPane) loader.load();
            CashUIController c = loader.getController();
            c.init(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private AnchorPane getCollectPane() {
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CollectUIController.class.getResource("CollectUI.fxml"));
        try {
            pane = (AnchorPane) loader.load();
            CollectUIController c = loader.getController();
            c.init(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private AnchorPane getPayPane() {
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PayUIController.class.getResource("PayUI.fxml"));
        try {
            pane = (AnchorPane) loader.load();
            PayUIController c = loader.getController();
            c.init(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private AnchorPane getBookPane() {
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BookUIController.class.getResource("BookUI.fxml"));
        try {
            pane = (AnchorPane) loader.load();
            c = loader.getController();
            c.init(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private AnchorPane getFirstBookPane() {
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FirstBookUIController.class.getResource("FirstBookUI.fxml"));
        try {
            pane = (AnchorPane) loader.load();
            FirstBookUIController c = loader.getController();
            c.init(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private AnchorPane getBusinessListPane() {
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BusinessListUIController.class.getResource("BusinessListUI.fxml"));
        try {
            pane = (AnchorPane) loader.load();
            BusinessListUIController c = loader.getController();
            c.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private AnchorPane getSaleListPane() {
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SaleDetailListController.class.getResource("SaleDetailListUI.fxml"));
        try {
            pane = (AnchorPane) loader.load();
            SaleDetailListController c = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private AnchorPane getRecordListPane() {
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BusinessProcessListController.class.getResource("BusinessProcessListUI.fxml"));
        try {
            pane = (AnchorPane) loader.load();
            BusinessProcessListController c = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private AnchorPane getAccountsPane() {
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AccountsUIController.class.getResource("AccountsUI.fxml"));
        try {
            pane = (AnchorPane) loader.load();
            AccountsUIController c = loader.getController();
            c.init(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private AnchorPane getDraftPane(){
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DraftUIController.class.getResource("DraftUI.fxml"));
        try {
            pane = (AnchorPane) loader.load();
            DraftUIController c = loader.getController();
            c.init(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private AnchorPane getRevisedReceiptPane(){
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RevisedReceiptController.class.getResource("RevisedReceipt.fxml"));
        try {
            pane = (AnchorPane) loader.load();
            RevisedReceiptController c = loader.getController();
            c.init(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private AnchorPane getUnrevisedReceiptPane(){
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UnrevisedReceiptController.class.getResource("UnrevisedReceipt.fxml"));
        try {
            pane = (AnchorPane) loader.load();
            UnrevisedReceiptController c = loader.getController();
            c.init(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FinanceMainUIController.class.getResource("FinanceMainUI.fxml"));
        AnchorPane window = (AnchorPane) loader.load();
        FinanceMainUIController c = loader.getController();
        c.init(null);
        Scene scene = new Scene(window);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
