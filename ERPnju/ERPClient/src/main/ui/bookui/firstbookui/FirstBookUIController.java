package main.ui.bookui.firstbookui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.businesslogicservice.bookblservice.BookblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.bookui.firstbookui.accountui.AccountsUIController;
import main.ui.bookui.firstbookui.clientui.ClientUIController;
import main.ui.mainui.FinanceMainUIController;
import main.ui.bookui.firstbookui.classui.ClassUIController;
import main.ui.bookui.firstbookui.goodsui.GoodsUIController;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Optional;

public class FirstBookUIController{
    @FXML
    private ScrollPane classPane;
    @FXML private ScrollPane goodsPane;
    @FXML private ScrollPane clientPane;
    @FXML private ScrollPane accountsPane;

    @FXML private Button submit;
    @FXML private Button cancel;

    private FinanceMainUIController c;

    public void init(FinanceMainUIController financeMainUIController){
        c=financeMainUIController;

        //初始化四个界面
        classPane.setContent(getClassPane());
        goodsPane.setContent(getGoodsPane());
        clientPane.setContent(getClientPane());
        accountsPane.setContent(getAccountPane());
    }

    @FXML
    public void onSubmit(){
        c.closeTab();
    }

    @FXML
    public void onCancel(){
        c.closeTab();
    }

    private AnchorPane getClassPane(){
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(main.ui.bookui.firstbookui.classui.ClassUIController.class.getResource("ClassUI.fxml"));
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
        loader.setLocation(main.ui.bookui.firstbookui.goodsui.GoodsUIController.class.getResource("GoodsUI.fxml"));
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
        loader.setLocation(main.ui.bookui.firstbookui.clientui.ClientUIController.class.getResource("ClientUI.fxml"));
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
        loader.setLocation(main.ui.bookui.firstbookui.accountui.AccountsUIController.class.getResource("AccountsUI.fxml"));
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
