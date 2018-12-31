package main.ui.stockui.stockui;

import java.util.ArrayList;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import main.ui.util.Client;
import main.vo.StockGiftReceiptVO;
import main.vo.StockGiftReceiptVOLineItem;

public class GiftDetailController {
	@FXML JFXTreeTableView<Goods> goods; 
	@FXML Label id;
	@FXML Label creator;
	JFXTreeTableColumn<Goods,String> name;
	JFXTreeTableColumn<Goods,String> xh;
	JFXTreeTableColumn<Goods,String> price;
	JFXTreeTableColumn<Goods,String> num;
	ObservableList<Goods> list=FXCollections.observableArrayList();
	
	public void init(StockGiftReceiptVO r){
		id.setText(r.getId());
		creator.setText(r.getCreatorId());
		
		name=new JFXTreeTableColumn<Goods,String>("商品名称");
		name.setPrefWidth(200);
		name.setCellValueFactory((TreeTableColumn.CellDataFeatures<Goods, String> param) ->{
			if(name.validateValue(param)) return param.getValue().getValue().name;
			else return name.getComputedValue(param);
		});
		
		xh=new JFXTreeTableColumn<Goods,String>("商品型号");
		xh.setPrefWidth(100);
		xh.setCellValueFactory((TreeTableColumn.CellDataFeatures<Goods, String> param) ->{
			if(xh.validateValue(param)) return param.getValue().getValue().xh;
			else return xh.getComputedValue(param);
		});
		
		price=new JFXTreeTableColumn<>("商品单价");
		price.setPrefWidth(200);
		price.setCellValueFactory((TreeTableColumn.CellDataFeatures<Goods, String> param) ->{
			if(price.validateValue(param)) return param.getValue().getValue().price;
			else return price.getComputedValue(param);
		});
		
		num=new JFXTreeTableColumn<Goods,String>("商品数量");
		num.setPrefWidth(100);
		num.setCellValueFactory((TreeTableColumn.CellDataFeatures<Goods, String> param) ->{
			if(num.validateValue(param)) return param.getValue().getValue().num;
			else return num.getComputedValue(param);
		});
		
		ArrayList<StockGiftReceiptVOLineItem> arr=r.getGoodsList();
		for(StockGiftReceiptVOLineItem item:arr){
			Goods g=new Goods(item);
			list.add(g);
		}
		
		TreeItem<Goods> root=new RecursiveTreeItem<Goods>(list,RecursiveTreeObject::getChildren);
		goods=new JFXTreeTableView<>(root);
		goods.setEditable(false);
		goods.setShowRoot(false);
		goods.getColumns().setAll(name,xh,price,num);
	}
	
	class Goods extends RecursiveTreeObject<Goods>{
		SimpleStringProperty name;
		SimpleStringProperty price;
		SimpleStringProperty num;
		SimpleStringProperty xh;
		
		public Goods(StockGiftReceiptVOLineItem g){
			name=new SimpleStringProperty(g.getGoodsName());
			xh=new SimpleStringProperty(g.getGoodsXh());
			price=new SimpleStringProperty(g.getValue()+"");
			num=new SimpleStringProperty(g.getAmount()+"");
		}
	}
}
