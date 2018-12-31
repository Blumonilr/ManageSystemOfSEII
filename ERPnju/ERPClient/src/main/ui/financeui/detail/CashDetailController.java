package main.ui.financeui.detail;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import main.vo.FinanceCashReceiptLineItem;
import main.vo.FinanceCashReceiptVO;

import java.text.DecimalFormat;

public class CashDetailController {
    @FXML private Label id;
    @FXML private Label operator;
    @FXML private Label account;
    @FXML private Label totalAmount;


    @FXML private TableView<FinanceCashReceiptLineItem> table;
    @FXML private TableColumn<FinanceCashReceiptLineItem,String> itemNameCol;
    @FXML private TableColumn<FinanceCashReceiptLineItem,Double> amountCol;
    @FXML private  TableColumn<FinanceCashReceiptLineItem,String> noteCol;

    private DecimalFormat df=new DecimalFormat("#.00");
    private ObservableList<FinanceCashReceiptLineItem> data;
    private StringConverter<Double> converter;

    public void init(FinanceCashReceiptVO vo){
        id.setText(vo.getId());
        operator.setText(vo.getCreatorId());
        account.setText(vo.getAccountName());

        data= FXCollections.observableArrayList(vo.getItemList());
        refreshTotal();

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

        table.setItems(data);
    }

    private void refreshTotal(){
        double total=0;
        for(FinanceCashReceiptLineItem i:data){
            total+=i.getAmount();
        }
        totalAmount.setText(df.format(total));
    }
}
