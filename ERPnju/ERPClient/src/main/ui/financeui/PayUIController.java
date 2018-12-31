package main.ui.financeui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import main.businesslogicservice.financeblservice.FinanceblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.mainui.FinanceMainUIController;
import main.vo.ClientVO;
import main.vo.FinanceActTransLineItem;
import main.vo.FinanceCollectReceiptVO;
import main.vo.FinancePayReceiptVO;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PayUIController{
    @FXML private Label operator;
    @FXML private ChoiceBox<ClientVO> clients;

    @FXML private Button remove;
    @FXML private Button add;
    @FXML private ChoiceBox<String> accounts;
    @FXML private TextField amount;
    @FXML private TextArea note;
    @FXML private Label totalAmount;

    @FXML private Button submit;
    @FXML private Button save;
    @FXML private Button clear;

    @FXML private TableView<FinanceActTransLineItem> table;
    @FXML private TableColumn<FinanceActTransLineItem,String> accountCol;
    @FXML private TableColumn<FinanceActTransLineItem,Double> amountCol;
    @FXML private  TableColumn<FinanceActTransLineItem,String> noteCol;

    private String currentUserId;
    private FinanceblService financeblService;
    private DecimalFormat df=new DecimalFormat("#.00");
    private ObservableList<FinanceActTransLineItem> data;
    private ObservableList<String> accountsList;
    private ObservableList<ClientVO> clientsList;
    private StringConverter<Double> converter;
    private StringConverter<ClientVO> clientConverter;
    private FinanceMainUIController c;

    public void init(FinanceMainUIController financeMainUIController){
        c=financeMainUIController;
        currentUserId=ClientRunner.getUser();
        operator.setText(currentUserId);
        financeblService= RemoteHelper.getInstance().getFinanceblService();

        //获取银行账户列表
        try {
            accountsList=FXCollections.observableArrayList(financeblService.showAccounts());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        accounts.setItems(accountsList);

        //获取客户列表
        try {
            clientsList=FXCollections.observableArrayList(financeblService.showClients());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        clientConverter=new StringConverter<ClientVO>() {
            @Override
            public String toString(ClientVO object) {
                return object.getClientName();
            }

            @Override
            public ClientVO fromString(String string) {
                //choiceBox无编辑动作，此方法无意义
                return null;
            }
        };
        clients.setConverter(clientConverter);
        clients.setItems(clientsList);

        data= FXCollections.observableArrayList();
        converter=new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return df.format(object.doubleValue());
            }
            @Override
            public Double fromString(String string) {
                return Double.valueOf(df.format(Double.valueOf(string)));
            }
        };

        accountCol.setCellValueFactory(new PropertyValueFactory<>("bankAccount"));
        accountCol.setCellFactory(new Callback<TableColumn<FinanceActTransLineItem, String>, TableCell<FinanceActTransLineItem, String>>() {
            @Override
            public TextFieldTableCell<FinanceActTransLineItem, String> call(TableColumn<FinanceActTransLineItem, String> param) {
                return new TextFieldTableCell<FinanceActTransLineItem, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        setConverter(new DefaultStringConverter());
                        super.updateItem(item, empty);
                        setAlignment(Pos.CENTER);
                        setFont(new Font(15));
                    }
                };
            }
        });

        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountCol.setCellFactory(new Callback<TableColumn<FinanceActTransLineItem, Double>, TableCell<FinanceActTransLineItem, Double>>() {
            @Override
            public TextFieldTableCell<FinanceActTransLineItem, Double> call(TableColumn<FinanceActTransLineItem, Double> param) {
                return new TextFieldTableCell<FinanceActTransLineItem, Double>(){
                    @Override
                    public void updateItem(Double item, boolean empty) {
                        setConverter(converter);
                        super.updateItem(item, empty);
                        setAlignment(Pos.CENTER);
                        setFont(new Font(15));
                    }
                };
            }
        });
        amountCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<FinanceActTransLineItem, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<FinanceActTransLineItem, Double> event) {
                ((FinanceActTransLineItem)event.getTableView().getItems().get(event.getTablePosition().getRow())).setAmount(event.getNewValue());
                refreshTotal();
            }
        });

        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
        noteCol.setCellFactory(new Callback<TableColumn<FinanceActTransLineItem, String>, TableCell<FinanceActTransLineItem, String>>() {
            @Override
            public TextFieldTableCell<FinanceActTransLineItem, String> call(TableColumn<FinanceActTransLineItem, String> param) {
                return new TextFieldTableCell<FinanceActTransLineItem, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        setConverter(new DefaultStringConverter());
                        super.updateItem(item, empty);
                        setAlignment(Pos.CENTER_LEFT);
                        setFont(new Font(15));
                    }
                };
            }
        });
        noteCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<FinanceActTransLineItem, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<FinanceActTransLineItem, String> event) {
                ((FinanceActTransLineItem)event.getTableView().getItems().get(event.getTablePosition().getRow())).setNote(event.getNewValue());
                refreshTotal();
            }
        });
        table.setItems(data);

        add.setDisable(true);
        accounts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(!(amount.getText().trim().isEmpty()||note.getText().trim().isEmpty()))
                add.setDisable(newValue==null);
        });
        amount.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!(accounts.getValue()==null||note.getText().trim().isEmpty()))
                add.setDisable(newValue.trim().isEmpty());
        });
        note.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!(accounts.getValue()==null||amount.getText().trim().isEmpty()))
                add.setDisable(newValue.trim().isEmpty());
        });

        //设置右键菜单
        MenuItem delMenuItem=new MenuItem("移除");
        delMenuItem.setOnAction((ActionEvent t)->{
            int index=table.getSelectionModel().getSelectedIndex();
            if(index!=-1&&index<data.size())
                onRemove();
        });
        ContextMenu menu=new ContextMenu();
        menu.getItems().add(delMenuItem);
        table.setContextMenu(menu);
    }

    private void refreshTotal(){
        double total=0;
        for(FinanceActTransLineItem i:data){
            total+=i.getAmount();
        }
        totalAmount.setText(df.format(total));
    }

    @FXML
    public void onAdd() {
        try {
            if (accounts.getSelectionModel().getSelectedItem() != null && amount.getText() != null && note.getText() != null) {
                if (Double.parseDouble(amount.getText())>financeblService.getAccount(accounts.getValue()).getBalance()){
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("输入不合法！");
                    alert.setContentText("付款金额超出账户余额");
                    alert.showAndWait();
                    amount.clear();
                    return;
                }
                    data.add(new FinanceActTransLineItem(accounts.getSelectionModel().getSelectedItem(), Double.parseDouble(amount.getText()), note.getText()));
            }
            accounts.setValue(null);
            amount.clear();
            note.clear();
            refreshTotal();
        }catch (NumberFormatException e){
            System.out.println("input not parsable");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onRemove(){
        data.remove(table.getSelectionModel().getSelectedIndex());
        refreshTotal();
    }

    @FXML
    public void onClear(){
        data.clear();
        clients.setValue(null);
        amount.clear();
        note.clear();
        refreshTotal();
        accounts.setValue(null);
    }

    @FXML
    public void onSave(){
        if(clients.getValue()!=null&&data.size()!=0) {
            String clientName =clients.getValue().getClientName();
            ArrayList<FinanceActTransLineItem> list=new ArrayList<>(data);
            try {
                financeblService.addDraft(new FinancePayReceiptVO(currentUserId,clientName,list),currentUserId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            c.closeTab();
        }
        else{
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("信息不全！");
            alert.setContentText("请重新检查并补全信息");
            alert.showAndWait();
        }
    }

    @FXML
    public void onSubmit(){
        if(clients.getValue()!=null&&data.size()!=0) {
            String clientName =clients.getValue().getClientName();
            ArrayList<FinanceActTransLineItem> list=new ArrayList<>(data);
            try {
                financeblService.makePayReceipt(new FinancePayReceiptVO(currentUserId,clientName,list));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            c.closeTab();
            ClientRunner.getLogger().record("创建付款单");
        }
        else{
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("信息不全！");
            alert.setContentText("请重新检查并补全信息");
            alert.showAndWait();
        }
    }
}
