package main.ui.stockui.goodsui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import com.jfoenix.controls.JFXDatePicker;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.businesslogicservice.stockblservice.StockblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.logui.LogHelper;
import main.vo.ClassVO;
import main.vo.GoodsQueryItem;
import main.vo.OutGoodsVO;

public class GoodsUIController {

	@FXML public TableView<OutGoodsVO> goodsTable;
	@FXML public TextField className;
	@FXML public TextField name;
	@FXML public TextField lowerPrice;
	@FXML public TextField upperPrice;
	@FXML public JFXDatePicker startTime;
	@FXML public JFXDatePicker endTime;
	@FXML public Label priceValidator;
	@FXML public Label timeValidator;
	
	private StockblService goods=RemoteHelper.getInstance().getStockblService();
	
	public void showGoods(){
		
		/**
		 * 给文本域添加输入监听
		 */
		className.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				onFind();
			}
			
		});
		name.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				onFind();
			}
			
		});
		lowerPrice.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(!lowerPrice.getText().matches("^\\d+(\\.\\d+)?$"))
					priceValidator.setText("价格格式不正确！");
				onFind();
			}
			
		});
		upperPrice.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {				
				if(!upperPrice.getText().matches("^\\d+(\\.\\d+)?$"))
					priceValidator.setText("价格格式不正确！");
				onFind();
			}
			
		});
		startTime.getEditor().textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(!startTime.getEditor().getText().matches("^\\d{4}-\\d{1,2}-\\d{1,2}$"))
					timeValidator.setText("日期格式不正确！");
				onFind();
			}
			
		});
		endTime.getEditor().textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {				
				if(!endTime.getEditor().getText().matches("^\\d{4}-\\d{1,2}-\\d{1,2}$"))
					timeValidator.setText("日期格式不正确！");
				onFind();
			}
			
		});
		//获得商品列表
		ArrayList<OutGoodsVO>gl=new ArrayList<>();
		try {
			gl=goods.getGoods();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		initTableView(gl);
	}
	
	private void initTableView(ArrayList<OutGoodsVO>gl){
		
		goodsTable.getColumns().clear();
		//添加右键菜单
		MenuItem addMenuItem=new MenuItem("添加商品");
		addMenuItem.setOnAction((ActionEvent t)->{
			addGoods();
		});
				
		MenuItem delMenuItem=new MenuItem("删除商品");
		delMenuItem.setOnAction((ActionEvent t)->{
			OutGoodsVO selectItem = goodsTable.getSelectionModel().getSelectedItem();
			if(selectItem!=null){
				delGoods(selectItem);
			}
		});
		
		MenuItem modMenuItem=new MenuItem("修改商品");
		modMenuItem.setOnAction((ActionEvent t)->{
			OutGoodsVO selectItem = goodsTable.getSelectionModel().getSelectedItem();
			if(selectItem!=null)
				modifyGoods(selectItem);
		});
		ContextMenu menu=new ContextMenu();
		menu.getItems().add(addMenuItem);
		menu.getItems().add(delMenuItem);
		menu.getItems().add(modMenuItem);
		goodsTable.setContextMenu(menu);
		
		//设置双击事件
		goodsTable.setOnMouseClicked((MouseEvent me)->{
			if(me.getButton().equals(MouseButton.PRIMARY)
					&&me.getClickCount()==2){
				OutGoodsVO selectItem = goodsTable.getSelectionModel().getSelectedItem();
				if(selectItem!=null)
					showGoods(selectItem);
			}
		});
		
		//设置每一列，总的商品列表只显示商品概要信息（商品名、id、型号、分类名、分类id）
		TableColumn<OutGoodsVO,String>nameC=new TableColumn<>("商品名");
		TableColumn<OutGoodsVO,String>idC=new TableColumn<>("商品编号");
		TableColumn<OutGoodsVO,String>xhC=new TableColumn<>("型号");
		TableColumn<OutGoodsVO,String>classNameC=new TableColumn<>("分类名");
		TableColumn<OutGoodsVO,String>classIdC=new TableColumn<>("分类编号");
		
		//为每一列设置映射
		nameC.setCellValueFactory((TableColumn.CellDataFeatures<OutGoodsVO,String> p)->
		new ReadOnlyStringWrapper(p.getValue().getName()));
		idC.setCellValueFactory((TableColumn.CellDataFeatures<OutGoodsVO,String>p)->
		new ReadOnlyStringWrapper(p.getValue().getId()));
		xhC.setCellValueFactory((TableColumn.CellDataFeatures<OutGoodsVO,String>p)->
		new ReadOnlyStringWrapper(p.getValue().getXh()));
		classNameC.setCellValueFactory((TableColumn.CellDataFeatures<OutGoodsVO,String>p)->
		new ReadOnlyStringWrapper(p.getValue().getClassName()));
		classIdC.setCellValueFactory((TableColumn.CellDataFeatures<OutGoodsVO,String>p)->
		new ReadOnlyStringWrapper(p.getValue().getClassId()));
		
		
		goodsTable.setItems(FXCollections.observableArrayList(gl));
		goodsTable.getColumns().addAll(classIdC,classNameC,idC,nameC,xhC);
	}
	
	@FXML
	public void onFind(){
		String cn=null,gn=null;
		double lp=0.0,up=Double.MAX_VALUE;
		Calendar st=null,et=null;
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		
		cn=className.getText();
		gn=name.getText();
					
		try{
			lp=Double.parseDouble(lowerPrice.getText());
			priceValidator.setText("");
		}catch(NumberFormatException |NullPointerException n){
			lp=0.0;
		}
		try{
			up=Double.parseDouble(upperPrice.getText());
			priceValidator.setText("");
		}catch(NumberFormatException |NullPointerException e){
			up=Double.MAX_VALUE;
		}
		
		try {
			st=new GregorianCalendar();
			st.setTime(df.parse(startTime.getEditor().getText()));
			timeValidator.setText("");
		} catch (ParseException |NullPointerException e) {
			st=null;
		}
		try {
			et=new GregorianCalendar();
			et.setTime(df.parse(endTime.getEditor().getText()));
			timeValidator.setText("");
		} catch (ParseException |NullPointerException e) {
			et=null;
		}
		
		System.out.println(startTime.getEditor().getText());
		
		GoodsQueryItem query=new GoodsQueryItem();
		query.setLower(Double.toString(lp));
		query.setUpper(Double.toString(up));
		query.setClassName(cn);
		query.setName(gn);
		query.setStart(st);
		query.setEnd(et);
		
		ArrayList<OutGoodsVO> res=null;
		try {
			res=goods.findGoods(query);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ClientRunner.getLogger().record("搜索商品");
		//显示搜索结果
		initTableView(res);
	}
	
	@FXML
	public void onRefresh(){
		className.setText("");
		name.setText("");
		lowerPrice.setText("");
		upperPrice.setText("");
		priceValidator.setText("");
		timeValidator.setText("");
		startTime.getEditor().setText("");
		endTime.getEditor().setText("");
		showGoods();
	}
	
	private void addGoods(){
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(this.getClass().getResource("AddGoods.fxml"));
		try {
			AnchorPane ap=(AnchorPane)loader.load();
			AddGoodsController agc=(AddGoodsController)loader.getController();
			Scene scene=new Scene(ap);
			Stage s=new Stage();
			s.setScene(scene);
			agc.init(s,ap,this);
			s.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void delGoods(OutGoodsVO obj){
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(this.getClass().getResource("DelGoodsUI.fxml"));
		try {
			AnchorPane ap=(AnchorPane)loader.load();
			DelGoodsUIController dgc=(DelGoodsUIController)loader.getController();
			Scene scene=new Scene(ap);
			Stage s=new Stage();
			s.setScene(scene);
			dgc.init(s, obj,this);
			s.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void modifyGoods(OutGoodsVO obj){
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(this.getClass().getResource("ModifyGoods.fxml"));
		try {
			AnchorPane ap=(AnchorPane)loader.load();
			ModifyGoodsController mgc=(ModifyGoodsController)loader.getController();
			Scene scene=new Scene(ap);
			Stage s=new Stage();
			s.setScene(scene);
			mgc.init(s, obj,this);
			s.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void showGoods(OutGoodsVO obj){
		
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(this.getClass().getResource("ShowGoods.fxml"));
		try {
			AnchorPane ap=(AnchorPane)loader.load();
			ShowGoodsController sgc=(ShowGoodsController)loader.getController();
			Scene scene=new Scene(ap);
			Stage s=new Stage();
			s.setScene(scene);
			sgc.init(s, obj);
			s.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
