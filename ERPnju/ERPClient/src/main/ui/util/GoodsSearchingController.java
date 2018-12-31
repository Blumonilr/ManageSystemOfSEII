package main.ui.util;



import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sun.javafx.collections.MappingChange.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.businesslogicservice.stockblservice.StockblService;
import main.rmi.RemoteHelper;
import main.ui.stockui.goodsui.ShowGoodsController;
import main.vo.GoodsQueryItem;
import main.vo.OutGoodsVO;

public class GoodsSearchingController {
	StockblService blservice=RemoteHelper.getInstance().getStockblService();
	@FXML
	AnchorPane pane;
	@FXML
	JFXTextField nameSearch;
	@FXML
	JFXTextField classSearch;
	
	JFXTreeTableColumn<Goods,String> nameCol;
	JFXTreeTableColumn<Goods,String> idCol;
	JFXTreeTableColumn<Goods,String> priceCol;
	JFXTreeTableColumn<Goods,String> amountCol;
	JFXTreeTableColumn<Goods,String> xhCol;
	JFXTreeTableColumn<Goods,String> numCol;
	
	ArrayList<OutGoodsVO> voList=new ArrayList<OutGoodsVO>();
	ObservableList<Goods> goodsList=FXCollections.observableArrayList();
	JFXTreeTableView<Goods> table;
	
	@FXML
	public void onInit(){
		nameSearch.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				search();
			}
			
		});
		
		classSearch.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				search();
			}
			
		});
		
		
		if(pane.getChildren().size()!=0){
			return;
		}
		
		//col
		nameCol=new JFXTreeTableColumn<>("名称");
		nameCol.setPrefWidth(70);
		nameCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Goods,String> param) ->{
			if(nameCol.validateValue(param)) return param.getValue().getValue().name;
			else return nameCol.getComputedValue(param);
		});
		//col
		idCol=new JFXTreeTableColumn<>("编号");
		idCol.setPrefWidth(70);
		idCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Goods,String> param) ->{
			if(idCol.validateValue(param)) return param.getValue().getValue().id;
			else return idCol.getComputedValue(param);
		});
		//col
		priceCol=new JFXTreeTableColumn<>("单价");
		priceCol.setPrefWidth(70);
		priceCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Goods,String> param) ->{
			if(priceCol.validateValue(param)) return param.getValue().getValue().price;
			else return priceCol.getComputedValue(param);
		});
		//col
		amountCol=new JFXTreeTableColumn<>("库存数量");
		amountCol.setPrefWidth(70);
		amountCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Goods,String> param) ->{
			if(amountCol.validateValue(param)) return param.getValue().getValue().amount;
			else return amountCol.getComputedValue(param);
		});
		//col
		xhCol=new JFXTreeTableColumn<>("型号");
		xhCol.setPrefWidth(65);
		xhCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Goods,String> param) ->{
			if(xhCol.validateValue(param)) return param.getValue().getValue().xh;
			else return xhCol.getComputedValue(param);
		});
		//col
		numCol=new JFXTreeTableColumn<>("库存数量");
		numCol.setPrefWidth(65);
		numCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Goods,String> param) ->{
			if(numCol.validateValue(param)) return param.getValue().getValue().num;
			else return numCol.getComputedValue(param);
		});
		
	
		TreeItem<Goods> root=new RecursiveTreeItem<Goods>(goodsList,RecursiveTreeObject::getChildren);
		table=new JFXTreeTableView<Goods>(root);
		table.setMinWidth(350);
		table.setPrefHeight(446);
		table.setShowRoot(false);
		table.setEditable(true);
		table.getColumns().setAll(nameCol,xhCol,numCol,priceCol,amountCol,idCol);
		
		
		
		table.setOnMouseClicked((MouseEvent me)->{
			if(me.getButton().equals(MouseButton.PRIMARY)&&me.getClickCount()==2){
				int index=table.getFocusModel().getFocusedIndex();
				if(index!=-1){
					OutGoodsVO vo=voList.get(index);
					FXMLLoader loader=new FXMLLoader();
					loader.setLocation(ShowGoodsController.class.getResource("ShowGoods.fxml"));
					AnchorPane pane=null;
					Stage s=new Stage();
					try {
						pane=loader.load();
						ShowGoodsController c=(ShowGoodsController)loader.getController();
						Scene scene=new Scene(pane);
						s.setScene(scene);
						c.init(s, vo);
						s.show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		});
		
		pane.getChildren().add(table);
		
		this.search();
	}

	/**
	 * 
	 * @return 返回选中 名称<>id<>类型<>单价 
	 */
	public String getSelected(){
		int index=table.getSelectionModel().getFocusedIndex();
		if(index==-1){
			return null;
		}
		else{
			Goods g=goodsList.get(index);
			return g.name.get()+"<>"+g.id.get()+"<>"+g.xh.get()+"<>"+g.price.get()+"<>"+g.num.get();
		}
	}
	
	public HashMap<String,Integer> getMap(){
		HashMap<String,Integer> map=new HashMap<String,Integer>();
		GoodsQueryItem query=new GoodsQueryItem();
		ArrayList<OutGoodsVO> list=new ArrayList<OutGoodsVO>();
		try {
			list=blservice.findGoods(query);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list!=null){
			for(OutGoodsVO vo:list){
				String id=vo.getId();
				int num=(int) vo.getStockNum();
				map.put(id, num);
			}
		}
		return map;
	}
	
	public void search(){
		voList.clear();
		goodsList.clear();
		String nameInfo=this.nameSearch.getText();
		String classInfo=this.classSearch.getText();
		GoodsQueryItem query=new GoodsQueryItem();
		if(!nameInfo.equals("")){
			query.setName(nameInfo);
		}
		if(!classInfo.equals("")){
			query.setClassName(classInfo);
		}
		System.out.println(query.getName());
		System.out.println(query.getClassName());
		//调用方法查找结果
		//更新table
		
		try {
			voList=blservice.findGoods(query);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(OutGoodsVO g:voList){
			Goods ng=new Goods(g.getName(),g.getId(),String.valueOf(g.getInPrice()),String.valueOf(g.getStockNum()),g.getXh(),g.getStockNum()+"");
			goodsList.add(ng);
		}
	}
	
	class Goods extends RecursiveTreeObject<Goods>{
		StringProperty name;
		StringProperty id;
		StringProperty price;
		StringProperty amount;
		StringProperty xh;
		StringProperty num;
		/**
		 * @param name
		 * @param id
		 * @param price
		 * @param amount
		 */
		public Goods(String name, String id, String price, String amount,String xh,String num) {
			super();
			this.name = new SimpleStringProperty(name);
			this.id =  new SimpleStringProperty(id);
			this.price =  new SimpleStringProperty(price);
			this.amount = new SimpleStringProperty(amount);
			this.xh = new SimpleStringProperty(xh);
			this.num = new SimpleStringProperty(num);
		}
	}
}
