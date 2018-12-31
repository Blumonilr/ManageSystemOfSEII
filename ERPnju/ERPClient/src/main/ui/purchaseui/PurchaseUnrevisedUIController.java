package main.ui.purchaseui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.jfoenix.controls.JFXDatePicker;
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
import main.rmi.RemoteHelper;
import main.ui.purchaseui.PurchaseDraftUIController.Draft;
import main.vo.PurchaseReceiptBothVO;
import main.vo.PurchaseReceiptBothVOLineItem;
import main.vo.PurchaseReceiptVO;
import main.vo.PurchaseReturnReceiptVO;

public class PurchaseUnrevisedUIController {
	
	PurchaseblService blservice=RemoteHelper.getInstance().getPurchaseblService();
	String userId="";
	
	JFXTreeTableColumn<Receipt,String> idCol;
	JFXTreeTableColumn<Receipt,String> typeCol;
	JFXTreeTableColumn<Receipt,String> timeCol;
	JFXTreeTableColumn<Receipt,String> descriptionCol;
	
	ObservableList<Receipt> receiptList=FXCollections.observableArrayList();
	ArrayList<PurchaseReceiptBothVO> recordList=new ArrayList<PurchaseReceiptBothVO>();//与observable同步
	
	
	
	@FXML
	JFXDatePicker beginTime;
	@FXML
	JFXDatePicker endTime;
	
	@FXML
	AnchorPane tablePane;
	JFXTreeTableView<Receipt> table;
	
	public void setUser(String userId){
		this.userId=userId;
	}
	
	
	public void onInit(){
		if(tablePane.getChildren().size()!=0){
			return;
		}
		//col
		idCol=new JFXTreeTableColumn<>("单据编号");
		idCol.setPrefWidth(200);
		idCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Receipt,String> param) ->{
			if(idCol.validateValue(param)) return param.getValue().getValue().id;
			else return idCol.getComputedValue(param);
		});
		//col
		typeCol=new JFXTreeTableColumn<>("单据类型");
		typeCol.setPrefWidth(200);
		typeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Receipt,String> param) ->{
			if(typeCol.validateValue(param)) return param.getValue().getValue().type;
			else return typeCol.getComputedValue(param);
		});
		//col
		timeCol=new JFXTreeTableColumn<>("提交时间");
		timeCol.setPrefWidth(200);
		timeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Receipt,String> param) ->{
			if(timeCol.validateValue(param)) return param.getValue().getValue().time;
			else return timeCol.getComputedValue(param);
		});
		//col
		descriptionCol=new JFXTreeTableColumn<>("单据摘要");
		descriptionCol.setPrefWidth(300);
		descriptionCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Receipt,String> param) ->{
			if(descriptionCol.validateValue(param)) return param.getValue().getValue().description;
			else return descriptionCol.getComputedValue(param);
		});
		
		//
		TreeItem<Receipt> root=new RecursiveTreeItem<Receipt>(receiptList,RecursiveTreeObject::getChildren);
		table=new JFXTreeTableView<Receipt>(root);
		table.setMinWidth(900);
		table.setPrefHeight(500);
		table.setShowRoot(false);
		table.setEditable(true);
		table.getColumns().setAll(idCol,typeCol,timeCol,descriptionCol);
		
		//拿到草稿
		
		
		
		tablePane.getChildren().add(table);
	}
	
	@FXML
	public void onSearch(){
		receiptList.clear();
		recordList.clear();
		System.out.println("Search");
		try{
			LocalDate localBeginDate=beginTime.getValue();
			Instant instantBegin = Instant.from(localBeginDate.atStartOfDay(ZoneId.systemDefault()));
			Date dateBegin = Date.from(instantBegin);
			Calendar beginCal=Calendar.getInstance();
			beginCal.setTime(dateBegin);
			
			LocalDate localEndDate=endTime.getValue();
			Instant instantEnd = Instant.from(localEndDate.atStartOfDay(ZoneId.systemDefault()));
			Date dateEnd = Date.from(instantEnd);
			Calendar endCal=Calendar.getInstance();
			endCal.setTime(dateEnd);
			
			System.out.println(this.getStringTime(beginCal));
			System.out.println(this.getStringTime(endCal));
			
			ArrayList<PurchaseReceiptBothVO> plist=new ArrayList<PurchaseReceiptBothVO>();
			ArrayList<PurchaseReceiptBothVO> rlist=new ArrayList<PurchaseReceiptBothVO>();
			
			try {
				 plist=blservice.showMyUnrevisedReceipts_p(userId, 41, beginCal, endCal);
				 rlist=blservice.showMyUnrevisedReceipts_p(userId, 42, beginCal, endCal);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//加入receiptList
			for(PurchaseReceiptBothVO re:plist){
				System.out.println("*");
				String description="";
				ArrayList<PurchaseReceiptBothVOLineItem> itemlist=re.getItemList();
				for(PurchaseReceiptBothVOLineItem i: itemlist){
					description+=i.getGoodsName()+" "+i.getGoodsAmount()+";    ";
				}
				Receipt receipt=new Receipt(re.getId(),"进货单",re.getStringMakeTime(),description);
				recordList.add(re);
				receiptList.add(receipt);
			}
			
			for(PurchaseReceiptBothVO re:rlist){
				System.out.println("*");
				String description="";
				ArrayList<PurchaseReceiptBothVOLineItem> itemlist=re.getItemList();
				for(PurchaseReceiptBothVOLineItem i: itemlist){
					description+=i.getGoodsName()+" "+i.getGoodsAmount()+";    ";
				}
				Receipt receipt=new Receipt(re.getId(),"进货退货单",re.getStringMakeTime(),description);
				receiptList.add(receipt);
				recordList.add(re);
			}
			table.refresh();
		}catch(Exception e){
			//以后可以完善，比如给用户一个提示
			e.printStackTrace();
		}
		
	}
	
	
	@FXML
	public void onOpen(){
		//查看单据内容
		int index=table.getSelectionModel().getFocusedIndex();
		if(index==-1){
			return;
		}
		
		PurchaseReceiptBothVO receipt=recordList.get(index);
		
		System.out.println(index);
		
		AnchorPane pane=null;
		
		FXMLLoader loader=new FXMLLoader();
		
		if(receipt.getReceiptType()==41){
			//purchase
			ShowUnrevisedReceiptUIController controller=null;
			loader.setLocation(ShowUnrevisedReceiptUIController.class.getResource("ShowUnrevisedReceiptUI.fxml"));
			try {
				pane=loader.load();
				controller=(ShowUnrevisedReceiptUIController)loader.getController();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			controller.onInit();
			controller.setReceipt((PurchaseReceiptVO)receipt);
		}
		else if(receipt.getReceiptType()==42){
			//purchasereturn
			ShowUnrevisedReturnReceiptUIController controller=null;
			loader.setLocation(ShowUnrevisedReturnReceiptUIController.class.getResource("ShowUnrevisedReturnReceiptUI.fxml"));
			try {
				pane=loader.load();
				controller=(ShowUnrevisedReturnReceiptUIController)loader.getController();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			controller.onInit();
			controller.setReceipt((PurchaseReturnReceiptVO)receipt);
		}
		
		
		Stage stage=new Stage();
		Scene scene=new Scene(pane);
		stage.setScene(scene);
		stage.show();
		
	}
	
	public class Receipt extends RecursiveTreeObject<Receipt>{
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
		public Receipt(String id, String type, String time, String description) {
			super();
			this.id = new SimpleStringProperty(id);
			this.type =  new SimpleStringProperty(type);
			this.time =  new SimpleStringProperty(time);
			this.description = new SimpleStringProperty(description);
		}
	}
	
	private String getStringTime(Calendar c){
		if(c==null){
			return "*";
		}
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd   HH:mm");
		return df.format(c.getTime());
	}
	
}
