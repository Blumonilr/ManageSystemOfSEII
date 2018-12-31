package main.ui.saleui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.businesslogicservice.purchaseblservice.PurchaseblService;
import main.businesslogicservice.saleblservice.SaleblService;
import main.rmi.RemoteHelper;
import main.vo.PurchaseReceiptBothVO;
import main.vo.PurchaseReceiptBothVOLineItem;
import main.vo.PurchaseReceiptVO;
import main.vo.PurchaseReturnReceiptVO;
import main.vo.SaleReceiptBothVO;
import main.vo.SaleReceiptBothVOLineItem;
import main.vo.SaleReceiptVO;
import main.vo.SaleReturnReceiptVO;

public class SaleDraftUIController {
	SaleblService blservice=RemoteHelper.getInstance().getSaleblService();
	String userId="";
	JFXTreeTableColumn<Draft,String> idCol;
	JFXTreeTableColumn<Draft,String> typeCol;
	JFXTreeTableColumn<Draft,String> timeCol;
	JFXTreeTableColumn<Draft,String> descriptionCol;
	
	ObservableList<Draft> draftList=FXCollections.observableArrayList();
	
	@FXML
	AnchorPane tablePane;
	JFXTreeTableView<Draft> table;
	
	
	@FXML
	public void onInit(){
		if(tablePane.getChildren().size()!=0){
			return;
		}
		//col
		idCol=new JFXTreeTableColumn<>("草稿编号");
		idCol.setPrefWidth(200);
		idCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Draft,String> param) ->{
			if(idCol.validateValue(param)) return param.getValue().getValue().id;
			else return idCol.getComputedValue(param);
		});
		//col
		typeCol=new JFXTreeTableColumn<>("草稿类型");
		typeCol.setPrefWidth(200);
		typeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Draft,String> param) ->{
			if(typeCol.validateValue(param)) return param.getValue().getValue().type;
			else return typeCol.getComputedValue(param);
		});
		//col
		timeCol=new JFXTreeTableColumn<>("上次修改时间");
		timeCol.setPrefWidth(200);
		timeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Draft,String> param) ->{
			if(timeCol.validateValue(param)) return param.getValue().getValue().time;
			else return timeCol.getComputedValue(param);
		});
		//col
		descriptionCol=new JFXTreeTableColumn<>("草稿摘要");
		descriptionCol.setPrefWidth(300);
		descriptionCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Draft,String> param) ->{
			if(descriptionCol.validateValue(param)) return param.getValue().getValue().description;
			else return descriptionCol.getComputedValue(param);
		});
		
		//
		TreeItem<Draft> root=new RecursiveTreeItem<Draft>(draftList,RecursiveTreeObject::getChildren);
		table=new JFXTreeTableView<Draft>(root);
		table.setMinWidth(900);
		table.setPrefHeight(500);
		table.setShowRoot(false);
		table.setEditable(true);
		table.getColumns().setAll(idCol,typeCol,timeCol,descriptionCol);
		
		//拿到草稿
		ArrayList<SaleReceiptVO> plist=new ArrayList<SaleReceiptVO>();
		ArrayList<SaleReturnReceiptVO> rlist=new ArrayList<SaleReturnReceiptVO>();
		try {
			if(blservice.showMySaleReceiptDrafts(userId)!=null){
				plist=blservice.showMySaleReceiptDrafts(userId);
				rlist=blservice.showMySaleReturnReceiptDrafts(userId);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(SaleReceiptVO vo:plist){
			String d_id=vo.getId();
			String d_time=vo.getStringMakeTime();
			String d_description="";
			ArrayList<SaleReceiptBothVOLineItem> itemlist=vo.getItemList();
			for(SaleReceiptBothVOLineItem i:itemlist){
				d_description+=i.getGoodsName()+"  ";
			}
			Draft draft=new Draft(d_id,"销售单草稿",d_time,d_description);
			draftList.add(draft);
		}
		for(SaleReturnReceiptVO vo:rlist){
			String d_id=vo.getId();
			String d_time=vo.getStringMakeTime();
			String d_description="";
			ArrayList<SaleReceiptBothVOLineItem> itemlist=vo.getItemList();
			for(SaleReceiptBothVOLineItem i:itemlist){
				d_description+=i.getGoodsName()+"  ";
			}
			Draft draft=new Draft(d_id,"销售退货单草稿",d_time,d_description);
			draftList.add(draft);
		}
		
		//draftList.add(new ReceiptItem("0001","进货","2017/11/13","lalalalalal"));
		
		tablePane.getChildren().add(table);
	}
	
	
	@FXML
	public void onOpen(){
		//编辑草稿 跳出来窗口
		int index=table.getSelectionModel().getFocusedIndex();
		if(index==-1){
			return;
		}
		String draftId=draftList.get(index).id.get();
		AnchorPane pane=null;
	
		FXMLLoader loader=new FXMLLoader();
		
		SaleReceiptBothVO draft=null;
		boolean found=false;
		ArrayList<SaleReceiptVO> plist=new ArrayList<SaleReceiptVO>();
		ArrayList<SaleReturnReceiptVO> rlist=new ArrayList<SaleReturnReceiptVO>();
		try {
			plist=blservice.showMySaleReceiptDrafts(userId);
			rlist=blservice.showMySaleReturnReceiptDrafts(userId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(SaleReceiptVO vo:plist){
			if(vo.getId().equals(draftId)){
				draft=vo;
				found=true;
				break;
			}
		}
		for(SaleReturnReceiptVO vo:rlist){
			if(found){
				break;
			}
			if(vo.getId().equals(draftId)){
				draft=vo;
				break;
			}
		}
		
		if(draft.getReceiptType()==31){
			//purchase
			EditSaleReceiptUIController controller=null;
			loader.setLocation(EditSaleReceiptUIController.class.getResource("EditSaleReceiptUI.fxml"));
			try {
				pane=loader.load();
				controller=(EditSaleReceiptUIController)loader.getController();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			controller.onInit();
			controller.setUser(userId);
			controller.setReceipt((SaleReceiptVO)draft);
		}
		else if(draft.getReceiptType()==32){
			//purchasereturn
			EditSaleReturnReceiptUIController controller=null;
			loader.setLocation(EditSaleReturnReceiptUIController.class.getResource("EditSaleReturnReceiptUI.fxml"));
			try {
				pane=loader.load();
				controller=(EditSaleReturnReceiptUIController)loader.getController();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			controller.onInit();
			controller.setUser(userId);
			controller.setReceipt((SaleReturnReceiptVO)draft);

		}
		
		
		Stage stage=new Stage();
		Scene scene=new Scene(pane);
		stage.setScene(scene);
		stage.showAndWait();
		
		this.onRefresh();
	}
	
	@FXML
	public void onDelete(){
		//删除草稿
		int index=table.getSelectionModel().getFocusedIndex();
		if(index==-1){
			return;
		}
		String draftId=draftList.get(index).id.get();
		try {
			
			blservice.deleteDraft(draftId, userId);
			draftList.remove(index);
			table.refresh();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void onRefresh(){
		draftList.clear();
		ArrayList<SaleReceiptVO> plist=new ArrayList<SaleReceiptVO>();
		ArrayList<SaleReturnReceiptVO> rlist=new ArrayList<SaleReturnReceiptVO>();
		try {
			plist=blservice.showMySaleReceiptDrafts(userId);
			rlist=blservice.showMySaleReturnReceiptDrafts(userId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(SaleReceiptVO vo:plist){
			String d_id=vo.getId();
			String d_time=vo.getStringMakeTime();
			String d_description="";
			ArrayList<SaleReceiptBothVOLineItem> itemlist=vo.getItemList();
			for(SaleReceiptBothVOLineItem i:itemlist){
				d_description+=i.getGoodsName()+"  ";
			}
			Draft draft=new Draft(d_id,"销售单草稿",d_time,d_description);
			draftList.add(draft);
		}
		for(SaleReturnReceiptVO vo:rlist){
			String d_id=vo.getId();
			String d_time=vo.getStringMakeTime();
			String d_description="";
			ArrayList<SaleReceiptBothVOLineItem> itemlist=vo.getItemList();
			for(SaleReceiptBothVOLineItem i:itemlist){
				d_description+=i.getGoodsName()+"  ";
			}
			Draft draft=new Draft(d_id,"销售退货单草稿",d_time,d_description);
			draftList.add(draft);
		}
		
		table.refresh();
	}
	
	public void setUser(String userId){
		this.userId=userId;
	}
	
	
	public class Draft extends RecursiveTreeObject<Draft>{
		StringProperty id;
		StringProperty type;
		StringProperty time;
		StringProperty description;
		/**
		 * @param name
		 * @param id
		 * @param price
		 * @param amount
		 */
		public Draft(String id, String type, String time, String description) {
			super();
			this.id = new SimpleStringProperty(id);
			this.type =  new SimpleStringProperty(type);
			this.time =  new SimpleStringProperty(time);
			this.description = new SimpleStringProperty(description);
		}
	}
}
