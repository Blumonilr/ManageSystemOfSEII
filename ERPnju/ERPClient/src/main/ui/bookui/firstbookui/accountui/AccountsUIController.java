package main.ui.bookui.firstbookui.accountui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import main.businesslogicservice.accountblservice.AccountblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.mainui.FinanceMainUIController;
import main.vo.AccountVO;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 待增加功能：右键菜单
 */
public class AccountsUIController {
    @FXML private TextField searchField;
    @FXML private Button search;

    @FXML private TableView<AccountVO> table;
    @FXML private TableColumn<AccountVO,String> nickNameCol;
    @FXML private TableColumn<AccountVO,String> idCol;
    @FXML private TableColumn<AccountVO,Double> amountCol;

    @FXML private Button delete;
    @FXML private Button modify;
    @FXML private TextField nickNameField;
    @FXML private TextField idField;
    @FXML private TextField passwordField;
    @FXML private TextField amountField;
    @FXML private Button add;

    @FXML private Button ok;
    @FXML private Button ret;


    private String currentUserId;
    FinanceMainUIController financeMainUIController;
    AccountblService accountblService;
    ObservableList<AccountVO> data;
    ArrayList<AccountVO> accountList;
    private StringConverter<Double> converter;

    public void init(FinanceMainUIController c){
        add.setDisable(true);
        modify.setDisable(true);
        delete.setDisable(true);

        financeMainUIController=c;
        currentUserId= ClientRunner.getUser();
        accountblService=RemoteHelper.getInstance().getAccountblService();

        try {
            accountList=accountblService.getAccounts();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        data=FXCollections.observableArrayList(accountList);

        converter=new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return object.toString();
            }
            @Override
            public Double fromString(String string) {
                return Double.valueOf(string);
            }
        };

        //设置第一列
        nickNameCol.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        nickNameCol.setCellFactory(new Callback<TableColumn<AccountVO,String>, TableCell<AccountVO,String>>() {
            @Override
            public TextFieldTableCell<AccountVO,String> call(TableColumn<AccountVO,String> param) {
                return new TextFieldTableCell<AccountVO,String>(){
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
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setCellFactory(new Callback<TableColumn<AccountVO,String>, TableCell<AccountVO,String>>() {
            @Override
            public TextFieldTableCell<AccountVO,String> call(TableColumn<AccountVO,String> param) {
                return new TextFieldTableCell<AccountVO,String>(){
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

        //设置第三列
        amountCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        amountCol.setCellFactory(new Callback<TableColumn<AccountVO,Double>, TableCell<AccountVO,Double>>() {
            @Override
            public TextFieldTableCell<AccountVO,Double> call(TableColumn<AccountVO,Double> param) {
                return new TextFieldTableCell<AccountVO,Double>(){
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

        table.setOnMouseClicked((MouseEvent me)->{
            if(me.getButton().equals(MouseButton.PRIMARY)
                    &&me.getClickCount()==2){
                AccountVO selectItem = table.getSelectionModel().getSelectedItem();
                if(selectItem!=null)
                    showAccount(selectItem);
            }
        });
        
        table.setItems(data);

        //设置动态监听，保证信息不为空
        add.setDisable(true);
        nickNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!(idField.getText().trim().isEmpty()||passwordField.getText().trim().isEmpty()||amountField.getText().trim().isEmpty()))
                add.setDisable(newValue.trim().isEmpty());
        });
        idField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!(nickNameField.getText().trim().isEmpty()||passwordField.getText().trim().isEmpty()||amountField.getText().trim().isEmpty()))
                add.setDisable(newValue.trim().isEmpty());
        });
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!(nickNameField.getText().trim().isEmpty()||idField.getText().trim().isEmpty()||amountField.getText().trim().isEmpty()))
                add.setDisable(newValue.trim().isEmpty());
        });
        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!(nickNameField.getText().trim().isEmpty()||idField.getText().trim().isEmpty()||passwordField.getText().trim().isEmpty()))
                add.setDisable(newValue.trim().isEmpty());
        });
    }

    private void showAccount(AccountVO a){
        ClientRunner.getLogger().record("查看银行账户信息:"+a.getNickname());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DetailController.class.getResource("DetailAccount.fxml"));
        AnchorPane window = null;
        try {
            window = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DetailController c = loader.getController();
        c.setText(new String[]{a.getNickname(),a.getId(),a.getPassword(),Double.toString(a.getBalance())});
        Scene scene = new Scene(window);
        Stage stage=new Stage(StageStyle.DECORATED);
        stage.setScene(scene);
        stage.show();
    }

    private void refreshList(){
        try {
            accountList=accountblService.getAccounts();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        data.clear();
        data.addAll(accountList);
    }

    public void onSearch(){
        String index=null;
        data.clear();
        data.addAll(accountList);
        index=searchField.getText();
        if(index==null||index.equals("")){
            refreshList();
        }else{
            for (int i=0;i<data.size();i++) {
                if(!data.get(i).getNickname().toLowerCase().contains(index.toLowerCase())) {
                    data.remove(i);
                    i--;
                }
            }
        }
    }

    public void onRet(){
        if(financeMainUIController!=null)
        financeMainUIController.closeTab();
    }
}

