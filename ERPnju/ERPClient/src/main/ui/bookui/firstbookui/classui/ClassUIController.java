package main.ui.bookui.firstbookui.classui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.businesslogicservice.stockblservice.StockblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.vo.ClassVO;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

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
	
	public void updateList() throws RemoteException{
		list = classes.showClass();
	}
}
