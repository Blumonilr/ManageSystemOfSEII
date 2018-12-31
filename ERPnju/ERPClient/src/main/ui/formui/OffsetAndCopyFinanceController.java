package main.ui.formui;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import main.vo.ClientVO;
import main.vo.FinanceActTransLineItem;
import main.vo.FinanceCollectReceiptVO;
import main.vo.FinancePayReceiptVO;
import main.vo.FinanceReceiptVO;

public class OffsetAndCopyFinanceController {
	@FXML private Label operator;
    @FXML private ChoiceBox<ClientVO> clients;

    @FXML private Button remove;
    @FXML private Button add;
    @FXML private ChoiceBox<String> accounts;
    @FXML private TextField amount;
    @FXML private TextArea note;
    @FXML private Label totalAmount;

    @FXML private Button modify;
    @FXML private Button clear;

    @FXML private TableView<FinanceActTransLineItem> table;
    @FXML private TableColumn<FinanceActTransLineItem,String> accountCol;
    @FXML private TableColumn<FinanceActTransLineItem,Double> amountCol;
    @FXML private  TableColumn<FinanceActTransLineItem,String> noteCol;
    
    @FXML private AnchorPane buttons;

    private String currentUserId;
    private FinanceblService financeblService;
    private DecimalFormat df=new DecimalFormat("#.00");
    private ObservableList<FinanceActTransLineItem> data;
    private ObservableList<String> accountsList;
    private ObservableList<ClientVO> clientsList;
    private StringConverter<Double> converter;
    private StringConverter<ClientVO> clientConverter;
    private FinanceCollectReceiptVO collectReceipt;
    private FinancePayReceiptVO payReceipt;
    private FinanceReceiptVO receipt;

    public void init(FinanceReceiptVO vo){
        currentUserId= ClientRunner.getUser();
        operator.setText(currentUserId);
        financeblService= RemoteHelper.getInstance().getFinanceblService();

        receipt=vo;
        if(vo.getReceiptType()==21) {
            collectReceipt = (FinanceCollectReceiptVO) vo;
            payReceipt=null;
        }
        else if(vo.getReceiptType()==22){
            collectReceipt=null;
            payReceipt=(FinancePayReceiptVO)vo;
        }


        //获取银行账户列表
        try {
            accountsList= FXCollections.observableArrayList(financeblService.showAccounts());
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

        if(vo.getReceiptType()==21) {
        	ArrayList<FinanceActTransLineItem> itemlist=collectReceipt.getItemList();
        	ArrayList<FinanceActTransLineItem> newList=new ArrayList<>();
        	for(FinanceActTransLineItem item:itemlist)
        		newList.add(new FinanceActTransLineItem(item.getBankAccount(),-item.getAmount(),"红冲操作"));
            data = FXCollections.observableArrayList(newList);
            ObservableList<ClientVO> list = clients.getItems();
            int i=0;
            for (i = 0; i < list.size(); i++) {
                if (list.get(i).getClientName().equals(collectReceipt.getClient()))
                    break;
            }
            clients.getSelectionModel().select(i);
        }
        else if(vo.getReceiptType()==22) {
        	ArrayList<FinanceActTransLineItem> itemlist=payReceipt.getItemList();
        	ArrayList<FinanceActTransLineItem> newList=new ArrayList<>();
        	for(FinanceActTransLineItem item:itemlist)
        		newList.add(new FinanceActTransLineItem(item.getBankAccount(),-item.getAmount(),"红冲操作"));
            data = FXCollections.observableArrayList(newList);
            ObservableList<ClientVO> list = clients.getItems();
            int i=0;
            for (i = 0; i < clientsList.size(); i++) {
                if (clientsList.get(i).getClientName().equals(payReceipt.getClient()))
                    break;
            }
            clients.getSelectionModel().select(i);
        }

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
            if (accounts.getSelectionModel().getSelectedItem() != null && amount.getText() != null && note.getText() != null)
                data.add(new FinanceActTransLineItem(accounts.getSelectionModel().getSelectedItem(), Double.parseDouble(amount.getText()), note.getText()));
            accounts.setValue(null);
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
        clients.setValue(null);
        amount.clear();
        note.clear();
        refreshTotal();
        accounts.setValue(null);
    }
    
    @FXML
    public void modify(){
    	if(clients.getValue()!=null&&data.size()!=0) {
            String clientName =clients.getValue().getClientName();
            ArrayList<FinanceActTransLineItem> list=new ArrayList<>(data);
            try {
                if(receipt.getReceiptType()==21) {
                	FinanceCollectReceiptVO fcr=new FinanceCollectReceiptVO(currentUserId,clientName,list);
                	fcr.setId(receipt.getId());
                	fcr.setMakeTime(receipt.getMakeTime());
                    if(RemoteHelper.getInstance().getReviseblService().updateReceipt(fcr)){
                    	Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    	alert.setHeaderText(null);
                    	alert.setContentText("修改成功!");
                    	alert.showAndWait();
                    	table.getScene().getWindow().hide();
                    }else{
                    	Alert alert=new Alert(Alert.AlertType.ERROR);
     		            alert.setHeaderText("信息错误!");
     		            alert.setContentText("请重新检查并修改信息");
     		            alert.showAndWait();
                    }
                }
                else if(receipt.getReceiptType()==22) {
                	FinancePayReceiptVO fpr=new FinancePayReceiptVO(currentUserId,clientName,list);
                	fpr.setId(receipt.getId());
                	fpr.setMakeTime(receipt.getMakeTime());
                    if(RemoteHelper.getInstance().getFormblService().offsetAndCopy(fpr)){
                    	Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    	alert.setHeaderText(null);
                    	alert.setContentText("修改成功!");
                    	alert.showAndWait();
                    	table.getScene().getWindow().hide();
                    }else{
                    	Alert alert=new Alert(Alert.AlertType.ERROR);
     		            alert.setHeaderText("信息错误!");
     		            alert.setContentText("请重新检查并修改信息");
     		            alert.showAndWait();
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else{
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("信息不全！");
            alert.setContentText("请重新检查并补全信息");
            alert.showAndWait();
        }
    }
}
