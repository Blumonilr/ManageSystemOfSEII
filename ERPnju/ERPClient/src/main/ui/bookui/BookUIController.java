package main.ui.bookui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import main.businesslogicservice.bookblservice.BookblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.accountui.AccountsUIController;
import main.ui.clientui.ClientUIController;
import main.ui.mainui.FinanceMainUIController;
import main.ui.stockui.classui.ClassUIController;
import main.ui.stockui.goodsui.GoodsUIController;
import main.vo.BookVO;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Optional;

public class BookUIController{
    @FXML private ScrollPane classPane;
    @FXML private ScrollPane goodsPane;
    @FXML private ScrollPane clientPane;
    @FXML private ScrollPane accountsPane;

    @FXML private Button submit;
    @FXML private Button save;
    @FXML private Button cancel;

    private FinanceMainUIController c;
    private BookblService bookblService;

    public void init(FinanceMainUIController financeMainUIController){
        c=financeMainUIController;
        bookblService= RemoteHelper.getInstance().getBookblService();
        try {
            bookblService.creatingBook();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        //初始化四个界面
        classPane.setContent(getClassPane());
        goodsPane.setContent(getGoodsPane());
        clientPane.setContent(getClientPane());
        accountsPane.setContent(getAccountPane());
    }

    @FXML
    public void onSubmit(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("是否确定提交该账目？");

        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK) {
            try {
                bookblService.initBook();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            c.closeTab();
            Alert newAlert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("建账成功");
            alert.showAndWait();
        }
        ClientRunner.getLogger().record("期初建账");
    }

    @FXML
    public void onSave(){
        try {
            bookblService.saveDraft();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        c.closeTab();
    }

    @FXML
    public void onCancel(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("您所做更改还没有保存！");
        alert.setContentText("是否保存到草稿？");

        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK) {
            onSave();
        }else {
            try {
                bookblService.cancelChange();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        c.closeTab();
    }

    private AnchorPane getClassPane(){
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation( ClassUIController.class.getResource("ClassUI.fxml"));
        try {
            pane = (AnchorPane) loader.load();
            ClassUIController c = loader.getController();
            c.showClass();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private AnchorPane getGoodsPane(){
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GoodsUIController.class.getResource("GoodsUI.fxml"));
        try {
            pane = (AnchorPane) loader.load();
            GoodsUIController c = loader.getController();
            c.showGoods();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private AnchorPane getClientPane(){
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation( ClientUIController.class.getResource("ClientUI.fxml"));
        try {
            pane = (AnchorPane) loader.load();
            ClientUIController c = loader.getController();
            c.onInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private AnchorPane getAccountPane(){
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AccountsUIController.class.getResource("AccountsUI.fxml"));
        try {
            pane = (AnchorPane) loader.load();
            AccountsUIController c = loader.getController();
            c.init(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }
}
