package main.ui.stockui.classui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.businesslogicservice.stockblservice.StockblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.logui.LogHelper;
import main.vo.ClassVO;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import javafx.event.ActionEvent;

/**
 * 
 * @author qyc
 *
 * 此类处理商品分类有关事物
 */
public class ClassUIController {

	@FXML public AnchorPane root;
	@FXML public TreeView<String>treeView;
	private StockblService classes=RemoteHelper.getInstance().getStockblService();
	public ArrayList<ClassVO> list;
	
	public TreeView<String> showClass(){
		initTreeView();
		return treeView;
	}
	
	public void initTreeView(){
		
		TreeItem<String>rootClass=new TreeItem<>("总商品分类");
		
		//读取商品分类列表
		try {
			updateList();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		//加载商品分类列表
		//先加载基类
		Iterator<ClassVO>it=list.iterator();
		while(it.hasNext()){
			ClassVO c=it.next();
			if(c.getFather()==null){
				TreeItem<String>son=new TreeItem<String>(c.getId());
				rootClass.getChildren().add(son);
				it.remove();
			}
		}
		//再加载子类
		for(TreeItem<String>ti:rootClass.getChildren())
			makeTree(ti,list);
		
		rootClass.setExpanded(true);
		treeView.setRoot(rootClass);
		
		
		//设置组件大小、位置
		treeView.setLayoutX(0);
		treeView.setLayoutY(0);
		treeView.setPrefWidth(1002);
		treeView.setPrefHeight(722);
		
		//添加右键菜单
		MenuItem addMenuItem=new MenuItem("新增商品分类");
		addMenuItem.setOnAction((ActionEvent t)->{
			TreeItem<String> selectItem = treeView.getSelectionModel().getSelectedItem();
			if(selectItem==null)
				selectItem=rootClass;
			addClass(selectItem);
		});
		
		MenuItem delMenuItem=new MenuItem("删除商品分类");
		delMenuItem.setOnAction((ActionEvent t)->{
			TreeItem<String> selectItem = treeView.getSelectionModel().getSelectedItem();
			if(selectItem!=null){
				delClass(selectItem);
			}
		});
		
		MenuItem modMenuItem=new MenuItem("修改商品分类");
		modMenuItem.setOnAction((ActionEvent ae)->{
			TreeItem<String> selectItem = treeView.getSelectionModel().getSelectedItem();
			if(selectItem!=null){
				modifyClass(selectItem);
			}
		});
		ContextMenu menu=new ContextMenu();
		menu.getItems().add(addMenuItem);
		menu.getItems().add(delMenuItem);
		menu.getItems().add(modMenuItem);
		treeView.setContextMenu(menu);
		
		//为treeView组件设置鼠标双击事件
		treeView.setOnMouseClicked((MouseEvent ae)->{
			if(ae.getButton().equals(MouseButton.PRIMARY)
					&&ae.getClickCount()==2){
				TreeItem<String>selectItem=treeView.getSelectionModel().getSelectedItem();
				if(selectItem==null)
					return;
				String id=selectItem.getValue();
				ClassVO obj=null;
				try {
					obj=RemoteHelper.getInstance().getStockblService().showClass(id);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				//显示商品分类信息
				if(obj!=null){
					loadShowClassUI(obj);
				}
			}
		});
	}
	
	public void loadShowClassUI(ClassVO obj){
		
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("ShowClassUI.fxml"));
		try {
			AnchorPane ap=(AnchorPane)loader.load();
			ShowClassUIController scuic=(ShowClassUIController)loader.getController();
			Scene scene=new Scene(ap);
			Stage stage=new Stage();
			stage.setScene(scene);
			scuic.init(stage,obj);
			scuic.setClassContr(this);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void makeTree(TreeItem<String>root,ArrayList<ClassVO>nodes){
		
		boolean foundson=false;
		Iterator<ClassVO>it=nodes.iterator();
		while(it.hasNext()){
			ClassVO node=it.next();
			if(node.getFather()!=null&&node.getFather().equals(root.getValue())){
				TreeItem<String>son=new TreeItem<>(node.getId());
				root.getChildren().add(son);
				it.remove();
				foundson=true;
			}
		}
		if(foundson){
			for(TreeItem<String>ti:root.getChildren())
				makeTree(ti,nodes);
		}else
			return;
	}
	
	public void addClass(TreeItem<String>root){
		//调用add Class界面,由add Class界面向root添加Tree Item
		loadAddClassUI(root);
	}
	
	private void loadAddClassUI(TreeItem<String>root){
		
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("AddClassUI.fxml"));
		try {
			AnchorPane ap=(AnchorPane)loader.load();
			AddClassUIController acuic=(AddClassUIController)loader.getController();
			Scene scene=new Scene(ap);
			Stage stage=new Stage();
			stage.setScene(scene);
			acuic.init(stage);
			acuic.setRoot(root);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void modifyClass(TreeItem<String>item){
		
		
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("ModClassUI.fxml"));
		try {
			AnchorPane ap=(AnchorPane)loader.load();
			ModClassUIController mcuic=(ModClassUIController)loader.getController();
			Scene scene=new Scene(ap);
			Stage stage=new Stage();
			stage.setScene(scene);
			mcuic.init(stage,item,this);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void delClass(TreeItem<String>root){

		Alert alert=new Alert(AlertType.CONFIRMATION);
		alert.setTitle("删除商品分类");
		alert.setHeaderText(null);
		alert.setContentText("确认删除商品分类: "+root.getValue());
		
		Optional<ButtonType>result=alert.showAndWait();
		if(result.get()==ButtonType.OK){
			boolean flag=false;
			try {
				flag=RemoteHelper.getInstance().getStockblService().delClass(root.getValue());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			if(flag){			
				ClientRunner.getLogger().record("删除商品分类"+root.getValue());
				root.getChildren().clear();
				if(root.getParent()!=null)
					root.getParent().getChildren().remove(root);			
			}else{
				ClientRunner.getLogger().record("删除商品分类失败");

				//提示界面
				Alert warn=new Alert(AlertType.WARNING);
				warn.setHeaderText(null);
				warn.setContentText("删除商品失败: "+root.getValue());
				warn.show();
			}
		}
		
	}
	
	public void updateList() throws RemoteException{
		list = classes.showClass();
	}
}
