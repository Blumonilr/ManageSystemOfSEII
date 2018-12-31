package main.ui.saleui;

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
import main.businesslogicservice.saleblservice.SaleblService;
import main.rmi.RemoteHelper;
import main.vo.SaleReceiptBothVO;
import main.vo.SaleReceiptBothVOLineItem;
import main.vo.SaleReceiptVO;
import main.vo.SaleReturnReceiptVO;

public class SaleUnrevisedUIController {
	
	SaleblService blservice=RemoteHelper.getInstance().getSaleblService();
	String userId="";
	
	JFXTreeTableColumn<Receipt,String> idCol;
	JFXTreeTableColumn<Receipt,String> typeCol;
	JFXTreeTableColumn<Receipt,String> timeCol;
	JFXTreeTableColumn<Receipt,String> descriptionCol;
	
	ObservableList<Receipt> receiptList=FXCollections.observableArrayList();
	ArrayList<SaleReceiptBothVO> recordList=new ArrayList<SaleReceiptBothVO>();//与observable同步
	
	
	
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
			
			ArrayList<SaleReceiptBothVO> plist=new ArrayList<SaleReceiptBothVO>();
			ArrayList<SaleReceiptBothVO> rlist=new ArrayList<SaleReceiptBothVO>();
			
			try {
				 plist=blservice.showMyUnrevisedReceipts(userId, 31, beginCal, endCal);
				 rlist=blservice.showMyUnrevisedReceipts(userId, 32, beginCal, endCal);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//加入receiptList
			for(SaleReceiptBothVO re:plist){
				System.out.println("*");
				String description="";
				ArrayList<SaleReceiptBothVOLineItem> itemlist=re.getItemList();
				for(SaleReceiptBothVOLineItem i: itemlist){
					description+=i.getGoodsName()+" "+i.getGoodsAmount()+";    ";
				}
				Receipt receipt=new Receipt(re.getId(),"销售单",re.getStringMakeTime(),description);
				recordList.add(re);
				receiptList.add(receipt);
			}
			
			for(SaleReceiptBothVO re:rlist){
				System.out.println("*");
				String description="";
				ArrayList<SaleReceiptBothVOLineItem> itemlist=re.getItemList();
				for(SaleReceiptBothVOLineItem i: itemlist){
					description+=i.getGoodsName()+" "+i.getGoodsAmount()+";    ";
				}
				Receipt receipt=new Receipt(re.getId(),"销售退货单",re.getStringMakeTime(),description);
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
		
		SaleReceiptBothVO receipt=recordList.get(index);
		
		System.out.println(index);
		
		AnchorPane pane=null;
		
		FXMLLoader loader=new FXMLLoader();
		
		if(receipt.getReceiptType()==31){
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
			controller.setReceipt((SaleReceiptVO)receipt);
		}
		
		else if(receipt.getReceiptType()==32){
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
			controller.setReceipt((SaleReturnReceiptVO)receipt);
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
