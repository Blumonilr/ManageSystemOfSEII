package main.ui.financeui.detail;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import main.businesslogicservice.financeblservice.FinanceblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.vo.*;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CollectAndPayDetailController {
    @FXML private Label title;
    @FXML private Label id;
    @FXML private Label operator;
    @FXML private Label client;
    @FXML private Label totalAmount;

    @FXML private TableView<FinanceActTransLineItem> table;
    @FXML private TableColumn<FinanceActTransLineItem,String> accountCol;
    @FXML private TableColumn<FinanceActTransLineItem,Double> amountCol;
    @FXML private  TableColumn<FinanceActTransLineItem,String> noteCol;

    private DecimalFormat df=new DecimalFormat("#.00");
    private ObservableList<FinanceActTransLineItem> data;
    private StringConverter<Double> converter;

    public void init(FinanceReceiptVO vo){
        id.setText(vo.getId());
        operator.setText(vo.getCreatorId());

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

        if(vo.getReceiptType()==21)
            initCollect((FinanceCollectReceiptVO)vo);
        else if(vo.getReceiptType()==22)
            initPay((FinancePayReceiptVO)vo);

        refreshTotal();
        table.setItems(data);
    }

    public void initCollect(FinanceCollectReceiptVO vo){
        title.setText("收款单");
        client.setText(vo.getClient());
        data= FXCollections.observableArrayList(vo.getItemList());
    }

    public void initPay(FinancePayReceiptVO vo){
        title.setText("付款单");
        client.setText(vo.getClient());
        data=FXCollections.observableArrayList(vo.getItemList());
    }

    private void refreshTotal(){
        double total=0;
        for(FinanceActTransLineItem i:data){
            total+=i.getAmount();
        }
        totalAmount.setText(df.format(total));
    }


}
