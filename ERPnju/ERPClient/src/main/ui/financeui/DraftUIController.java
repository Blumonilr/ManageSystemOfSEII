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
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.businesslogicservice.financeblservice.FinanceblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.financeui.edit.EditCashController;
import main.ui.financeui.edit.EditCollectAndPayController;
import main.ui.mainui.FinanceMainUIController;
import main.vo.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Optional;

public class DraftUIController {
    @FXML private ScrollPane tablePane;

    private JFXTreeTableView table;
    private JFXTreeTableColumn<ReceiptItem,String> targetCol;
    private JFXTreeTableColumn<ReceiptItem,String> typeCol;
    private JFXTreeTableColumn<ReceiptItem,String> timeCol;
    private JFXTreeTableColumn<ReceiptItem,String> desCol;

    FinanceMainUIController financeMainUIController;
    private String currentUserId;
    private ObservableList<ReceiptItem> drafts;
    private FinanceblService financeblService;

    public void init(FinanceMainUIController financeMainUIController){
        this.financeMainUIController=financeMainUIController;
        currentUserId= ClientRunner.getUser();
        financeblService= RemoteHelper.getInstance().getFinanceblService();
        drafts= FXCollections.observableArrayList();

        targetCol=new JFXTreeTableColumn<>("操作对象");
        targetCol.setPrefWidth(200);
        targetCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ReceiptItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ReceiptItem, String> param) {
                if(targetCol.validateValue(param))
                    return param.getValue().getValue().target;
                else
                    return targetCol.getComputedValue(param);
            }
        });

        typeCol=new JFXTreeTableColumn<>("单据类型");
        typeCol.setPrefWidth(200);
        typeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ReceiptItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ReceiptItem, String> param) {
                if(typeCol.validateValue(param))
                    return param.getValue().getValue().type;
                else
                    return typeCol.getComputedValue(param);
            }
        });

        timeCol=new JFXTreeTableColumn<>("上次修改时间");
        timeCol.setPrefWidth(200);
        timeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ReceiptItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ReceiptItem, String> param) {
                if(timeCol.validateValue(param))
                    return param.getValue().getValue().time;
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

        TreeItem<ReceiptItem> data=new RecursiveTreeItem<ReceiptItem>(drafts,RecursiveTreeObject::getChildren);
        table=new JFXTreeTableView<ReceiptItem>(data);
        table.setMinWidth(900);
        table.setPrefHeight(1000);
        table.setShowRoot(false);
        table.setEditable(false);
        table.getColumns().addAll(targetCol,typeCol,timeCol,desCol);

        ArrayList<ReceiptVO> list=null;
        try {
            list=financeblService.readDraftList(currentUserId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for(int i=0;i<list.size();i++){
            drafts.add(new ReceiptItem(list.get(i)));
        }
        tablePane.setContent(table);

        table.setOnMouseClicked((MouseEvent me)->{
            if(me.getButton().equals(MouseButton.PRIMARY)
                    &&me.getClickCount()==2){
                onEdit();
            }
        });
    }

    @FXML
    public void onEdit(){
        int index=table.getSelectionModel().getFocusedIndex();
        if(index>=drafts.size()||index==-1){
            return;
        }
        String draftId=drafts.get(index).id.getValue();

        AnchorPane pane=null;
        FXMLLoader loader=new FXMLLoader();

        ReceiptVO draft= null;
        try {
            draft = financeblService.readDraft(draftId,currentUserId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if(draft.getReceiptType()==23){
            EditCashController c=null;
            loader.setLocation(EditCashController.class.getResource("EditCash.fxml"));
            try {
                pane=loader.load();
                c=(EditCashController)loader.getController();
                c.init((FinanceCashReceiptVO)draft);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(draft.getReceiptType()==21||draft.getReceiptType()==22){
            EditCollectAndPayController c=null;
            loader.setLocation(EditCollectAndPayController.class.getResource("EditCollectAndPay.fxml"));
            try {
                pane=loader.load();
                c=(EditCollectAndPayController) loader.getController();
                c.init((FinanceReceiptVO)draft);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Stage stage=new Stage();
        Scene scene=new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("修改单据");
        stage.showAndWait();
    }

    @FXML
    public void onDelete() {
        int index = table.getSelectionModel().getFocusedIndex();
        if (index == -1) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("警告！");
        alert.setContentText("确定要删除该账户吗？");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                financeblService.delDraft(drafts.get(index).id.getValue(), currentUserId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        onRefresh();
    }

    @FXML
    public void onRefresh(){
        ArrayList<ReceiptVO> list=null;
        try {
            list=financeblService.readDraftList(currentUserId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        drafts.clear();
        for(int i=0;i<list.size();i++){
            drafts.add(new ReceiptItem(list.get(i)));
        }
    }
}
