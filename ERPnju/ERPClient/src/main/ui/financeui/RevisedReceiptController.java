package main.ui.financeui;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.businesslogicservice.financeblservice.FinanceblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.financeui.detail.CashDetailController;
import main.ui.financeui.detail.CollectAndPayDetailController;
import main.ui.financeui.edit.EditCashController;
import main.ui.financeui.edit.EditCollectAndPayController;
import main.ui.mainui.FinanceMainUIController;
import main.vo.AccountVO;
import main.vo.FinanceCashReceiptVO;
import main.vo.FinanceReceiptVO;
import main.vo.ReceiptVO;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class RevisedReceiptController {
    @FXML
    private ScrollPane tablePane;

    private JFXTreeTableView table;
    private JFXTreeTableColumn<ReceiptItem,String> targetCol;
    private JFXTreeTableColumn<ReceiptItem,String> typeCol;
    private JFXTreeTableColumn<ReceiptItem,String> timeCol;
    private JFXTreeTableColumn<ReceiptItem,String> desCol;

    private FinanceMainUIController financeMainUIController;
    private String currentUserId;
    private ObservableList<ReceiptItem> receipts;
    private FinanceblService financeblService;

    public void init(FinanceMainUIController financeMainUIController){
        this.financeMainUIController=financeMainUIController;
        currentUserId= ClientRunner.getUser();
        financeblService= RemoteHelper.getInstance().getFinanceblService();
        receipts = FXCollections.observableArrayList();

        targetCol=new JFXTreeTableColumn<>("单据编号");
        targetCol.setPrefWidth(200);
        targetCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ReceiptItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ReceiptItem, String> param) {
                if(targetCol.validateValue(param))
                    return param.getValue().getValue().id;
                else
                    return targetCol.getComputedValue(param);
            }
        });

        typeCol=new JFXTreeTableColumn<>("操作对象");
        typeCol.setPrefWidth(200);
        typeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ReceiptItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ReceiptItem, String> param) {
                if(typeCol.validateValue(param))
                    return param.getValue().getValue().target;
                else
                    return typeCol.getComputedValue(param);
            }
        });

        timeCol=new JFXTreeTableColumn<>("审批时间");
        timeCol.setPrefWidth(200);
        timeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ReceiptItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ReceiptItem, String> param) {
                if(timeCol.validateValue(param))
                    return param.getValue().getValue().revisedTime;
                else
                    return timeCol.getComputedValue(param);
            }
        });

        desCol=new JFXTreeTableColumn<>("摘要");
        desCol.setPrefWidth(300);
        desCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ReceiptItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ReceiptItem, String> param) {
                if(desCol.validateValue(param))
                    return param.getValue().getValue().description;
                else
                    return desCol.getComputedValue(param);
            }
        });

        TreeItem<ReceiptItem> data=new RecursiveTreeItem<ReceiptItem>(receipts, RecursiveTreeObject::getChildren);
        table=new JFXTreeTableView<ReceiptItem>(data);
        table.setMinWidth(900);
        table.setPrefHeight(1000);
        table.setShowRoot(false);
        table.setEditable(false);
        table.getColumns().addAll(targetCol,typeCol,timeCol,desCol);

        ArrayList<ReceiptVO> list=null;
        try {
            list=financeblService.readRevisedReceiptList(currentUserId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for(int i=0;i<list.size();i++){
            receipts.add(new ReceiptItem(list.get(i)));
        }

        table.setOnMouseClicked((MouseEvent me)->{
            if(me.getButton().equals(MouseButton.PRIMARY)
                    &&me.getClickCount()==2){
                onLookUp();
            }
        });

        tablePane.setContent(table);
    }

    @FXML
    public void onLookUp(){
        int index=table.getSelectionModel().getFocusedIndex();
        if(index>=receipts.size()||index==-1){
            return;
        }
        String receiptID= receipts.get(index).id.getValue();

        AnchorPane pane=null;
        FXMLLoader loader=new FXMLLoader();

        ReceiptVO receipt= null;
        try {
            receipt = financeblService.readReceipt(receiptID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if(receipt.getReceiptType()==23){
            CashDetailController c=null;
            loader.setLocation(CashDetailController.class.getResource("CashDetail.fxml"));
            try {
                pane=loader.load();
                c=(CashDetailController)loader.getController();
                c.init((FinanceCashReceiptVO)receipt);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(receipt.getReceiptType()==21||receipt.getReceiptType()==22){
            CollectAndPayDetailController c=null;
            loader.setLocation(CollectAndPayDetailController.class.getResource("CollectAndPayDetail.fxml"));
            try {
                pane=loader.load();
                c= (CollectAndPayDetailController) loader.getController();
                c.init((FinanceReceiptVO)receipt);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Stage stage=new Stage();
        Scene scene=new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("单据详情");
        stage.showAndWait();
    }
}
