package main.ui.strategyui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import main.businesslogicservice.stockblservice.StockblService;
import main.ui.purchaseui.PurchaseUIController.TableNode;
import javafx.fxml.Initializable;

public class GiftController implements Initializable{
	@FXML TableView<Goods> goods;
	@FXML TableColumn<Goods,String> id;
	@FXML TableColumn<Goods,String> name;
	@FXML TableColumn<Goods,Double> price;
	@FXML TableColumn num;
	@FXML Button confirm;
	@FXML Label theName;
	@FXML JFXTextField number;
	@FXML Label title;
	
	ObservableList<Goods> list=FXCollections.observableArrayList();
	StockblService stockblservice;
	Goods selectedGoods;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO 自动生成的方法存根
		goods.setEditable(true);
		id.setCellValueFactory(new PropertyValueFactory<Goods,String>("id"));
		name.setCellValueFactory(new PropertyValueFactory<Goods,String>("name"));
		price.setCellValueFactory(new PropertyValueFactory<Goods,Double>("price"));
		num.setCellValueFactory(new PropertyValueFactory<Goods,String>("num"));
		num.setCellFactory(TextFieldTableCell.forTableColumn());
		num.setCellFactory(
				  new PropertyValueFactory<Goods,String>("num"));
		num.setCellFactory(TextFieldTableCell.forTableColumn());
		num.setOnEditCommit(
				new EventHandler<CellEditEvent<Goods,String>>(){
				@Override
				public void handle(CellEditEvent<Goods, String> event) {
					((Goods) event.getTableView().getItems().get(
							event.getTablePosition().getRow())
							).setNum(event.getNewValue());
					}
				}
				);
		showList();
	}

	private void showList() {
		// TODO 自动生成的方法存根
		goods.setItems(list);
	}
	
	public void addGoods(Goods g){
		list.add(g);
		showList();
	}
	
}
