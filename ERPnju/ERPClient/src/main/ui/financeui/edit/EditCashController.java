package main.ui.financeui.edit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import main.businesslogicservice.financeblservice.FinanceblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.vo.FinanceCashReceiptLineItem;
import main.vo.FinanceCashReceiptVO;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EditCashController {
    @FXML private AnchorPane root;
    @FXML private Label operator;
    @FXML private ChoiceBox<String> accounts;

    @FXML private Button remove;
    @FXML private Button add;
    @FXML private TextField itemName;
    @FXML private TextField amount;
    @FXML private TextArea note;
    @FXML private Label totalAmount;

    @FXML private Button submit;
    @FXML private Button save;
    @FXML private Button clear;

    @FXML private TableView<FinanceCashReceiptLineItem> table;
    @FXML private TableColumn<FinanceCashReceiptLineItem,String> itemNameCol;
    @FXML private TableColumn<FinanceCashReceiptLineItem,Double> amountCol;
    @FXML private  TableColumn<FinanceCashReceiptLineItem,String> noteCol;
    
    @FXML private AnchorPane buttons;

    private String currentUserId;
    private FinanceblService financeblService;
    private DecimalFormat df=new DecimalFormat("#.00");
    private ObservableList<FinanceCashReceiptLineItem> data;
    private ObservableList<String> accountsList;
    private StringConverter<Double> converter;
    private FinanceCashReceiptVO vo;

    public void init(FinanceCashReceiptVO cashReceipt){
        vo=cashReceipt;
        currentUserId= ClientRunner.getUser();
        operator.setText(currentUserId);
        financeblService= RemoteHelper.getInstance().getFinanceblService();

        //获取银行账户列表
        try {
            accountsList= FXCollections.observableArrayList(financeblService.showAccounts());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        data= FXCollections.observableArrayList(vo.getItemList());
        refreshTotal();

        accounts.setItems(accountsList);
        accounts.getSelectionModel().select(vo.getAccountName());

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

        //设置第一列
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemNameCol.setCellFactory(new Callback<TableColumn<FinanceCashReceiptLineItem, String>, TableCell<FinanceCashReceiptLineItem, String>>() {
            @Override
            public TextFieldTableCell<FinanceCashReceiptLineItem, String> call(TableColumn<FinanceCashReceiptLineItem, String> param) {
                return new TextFieldTableCell<FinanceCashReceiptLineItem, String>(){
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
        itemNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<FinanceCashReceiptLineItem,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<FinanceCashReceiptLineItem,String> t) {
                ((FinanceCashReceiptLineItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setItemName(t.getNewValue());
                refreshTotal();
            }
        });

        //设置第二列
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountCol.setCellFactory(new Callback<TableColumn<FinanceCashReceiptLineItem, Double>, TableCell<FinanceCashReceiptLineItem, Double>>() {
            @Override
            public TextFieldTableCell<FinanceCashReceiptLineItem, Double> call(TableColumn<FinanceCashReceiptLineItem, Double> param) {
                return new TextFieldTableCell<FinanceCashReceiptLineItem, Double>(){
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
        amountCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<FinanceCashReceiptLineItem, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<FinanceCashReceiptLineItem, Double> event) {
                ((FinanceCashReceiptLineItem)event.getTableView().getItems().get(event.getTablePosition().getRow())).setAmount(event.getNewValue());
                refreshTotal();
            }
        });

        //设置第三列
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
        noteCol.setCellFactory(new Callback<TableColumn<FinanceCashReceiptLineItem, String>, TableCell<FinanceCashReceiptLineItem, String>>() {
            @Override
            public TextFieldTableCell<FinanceCashReceiptLineItem, String> call(TableColumn<FinanceCashReceiptLineItem, String> param) {
                return new TextFieldTableCell<FinanceCashReceiptLineItem, String>(){
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
        noteCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<FinanceCashReceiptLineItem, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<FinanceCashReceiptLineItem, String> event) {
                ((FinanceCashReceiptLineItem)event.getTableView().getItems().get(event.getTablePosition().getRow())).setNote(event.getNewValue());
                refreshTotal();
            }
        });
        table.setItems(data);

        add.setDisable(true);
        itemName.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!(amount.getText().trim().isEmpty()||note.getText().trim().isEmpty()))
                add.setDisable(newValue.trim().isEmpty());
        });
        amount.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!(itemName.getText().trim().isEmpty()||note.getText().trim().isEmpty()))
                add.setDisable(newValue.trim().isEmpty());
        });
        note.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!(itemName.getText().trim().isEmpty()||amount.getText().trim().isEmpty()))
                add.setDisable(newValue.trim().isEmpty());
        });
    }

    @FXML
    public void onAdd() {
        try {
            if (itemName.getText() != null && amount.getText() != null && note.getText() != null)
                data.add(new FinanceCashReceiptLineItem(itemName.getText(), Double.parseDouble(amount.getText()), note.getText()));
            itemName.clear();
            amount.clear();
            note.clear();
            refreshTotal();
        }catch (NumberFormatException e){
            System.out.println("input not parsable");
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
        itemName.clear();
        amount.clear();
        note.clear();
        refreshTotal();
        accounts.setValue(null);
    }

    @FXML
    public void onSave(){
        if(accounts.getValue()!=null&&data.size()!=0) {
            vo.setAccountName(accounts.getValue());
            vo.setItemList(new ArrayList<>(data));
            vo.setCreatorId(currentUserId);
            try {
                vo.setMakeTime(Calendar.getInstance());
                financeblService.modifyDraft(vo,currentUserId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            root.getScene().getWindow().hide();
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
        if(accounts.getValue()!=null&&data.size()!=0) {
            String accountName =accounts.getValue();
            ArrayList<FinanceCashReceiptLineItem> list=new ArrayList<>(data);
            try {
                financeblService.makeCashReceipt(new FinanceCashReceiptVO(currentUserId,accountName,list));
                financeblService.delDraft(vo.getId(),currentUserId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            root.getScene().getWindow().hide();
            ClientRunner.getLogger().record("创建现金费用单");
        }
        else{
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("信息不全！");
            alert.setContentText("请重新检查并补全信息");
            alert.showAndWait();
        }
    }

    private void refreshTotal(){
        double total=0;
        for(FinanceCashReceiptLineItem i:data){
            total+=i.getAmount();
        }
        totalAmount.setText(df.format(total));
    }
}
