package main.ui.purchaseui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import main.businesslogicservice.purchaseblservice.PurchaseblService;
import main.rmi.RemoteHelper;
import main.ui.purchaseui.EditPurchaseReceiptUIController.TableNode;
import main.ui.util.GoodsSearchingController;
import main.vo.PurchaseReceiptBothVOLineItem;
import main.vo.PurchaseReceiptVO;

public class ShowUnrevisedReceiptUIController {
	
	/**
	 * 进货单项
	 */
	@FXML
	JFXTextField receiptId;
	@FXML
	JFXTextField clientId;
	@FXML
	JFXTextField stockId;
	@FXML
	JFXTextField operatorId;
	@FXML
	Label total;
	@FXML
	TextArea note;
	
	
	/**
	 * 商品列表
	 */
	@FXML
	TableColumn goodsId;
	@FXML
	TableColumn goodsName;
	@FXML
	TableColumn goodsXh;
	@FXML
	TableColumn goodsAmount;
	@FXML
	TableColumn goodsPrice;
	@FXML
	TableColumn subTotal;
	@FXML
	TableColumn subNote;
	@FXML
	TableView<TableNode> table;
	/**
	 * 与table绑定的list
	 */
	private ObservableList<TableNode> goodsList;
	
	public void onInit(){
		
		goodsList=FXCollections.observableArrayList();
		goodsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        goodsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        goodsXh.setCellValueFactory(new PropertyValueFactory<>("xh"));
        goodsAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        goodsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        subTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        subNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        
        table.setItems(goodsList);
		
	}
	
	
	public void setReceipt(PurchaseReceiptVO vo){
		//设置初始值
		this.goodsList.clear();
		String rid=vo.getId();
		String cid=vo.getClientId();
		String sid=vo.getStockId();
		String oid=vo.getCreatorId();
		this.receiptId.setText(rid);
		this.clientId.setText(cid);
		this.stockId.setText(sid);
		this.operatorId.setText(oid);//也可以从currentUserId拿
		this.total.setText(String.valueOf(vo.getTotalValue()));
		this.note.setText(vo.getNote());
		this.total.setText(vo.getTotalValue()+"");
//		this.operatorId.setText(currentUserId);
		
		ArrayList<PurchaseReceiptBothVOLineItem> list=vo.getItemList();
		for(PurchaseReceiptBothVOLineItem item : list){
			String itid=item.getGoodsId();
			String itname=item.getGoodsName();
			String itxh=item.getGoodsXh();
			String itamount=String.valueOf(item.getGoodsAmount());
			String itprice=String.valueOf(item.getGoodsPrice());
			String ittotal=String.valueOf(item.getSubtotal());
			String itnote=item.getNote();
			TableNode node=new TableNode(itid,itname,itxh,itamount,itprice,ittotal,itnote);
			this.goodsList.add(node);
		}
		
		table.refresh();
	}
	
	
	/**
	 * 商品列表一行
	 * @author Kate
	 *要标注为public！！
	 */
	public class TableNode{
		private String id;
		private String name;
		private String xh;
		private String amount;
		private String price;
		private String total;
		private String note;
		
		private int am;
		private double pr,tot;
		
		/**
		 * @param id
		 * @param name
		 * @param xh
		 * @param amount
		 * @param price
		 * @param total
		 * @param note
		 */
		private TableNode(String id, String name, String xh, String amount, String price, String total, String note) {
			super();
			this.id = id;
			this.name = name;
			this.xh = xh;
			this.amount = amount;
			this.price = price;
			this.total = total;
			this.note = note;
			try{
				pr=Double.parseDouble(price);
			}catch(Exception e){
				pr=0.0;
			}
			
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getXh() {
			return xh;
		}
		public void setXh(String xh) {
			this.xh = xh;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
			try{
				am=Integer.parseInt(amount);
			}catch(Exception e){
				am=0;
				this.amount="";
				tot=am*pr;
				total=String.valueOf(tot);
				table.refresh();
			}
			tot=am*pr;
			total=String.valueOf(tot);
			table.refresh();
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
			try{
				pr=Double.parseDouble(price);
			}catch(Exception e){
				this.price="";
				pr=0.0;
				tot=am*pr;
				total=String.valueOf(tot);
				table.refresh();
			}
			tot=am*pr;
			total=String.valueOf(tot);
			table.refresh();
		}
		public String getTotal() {
			return total;
		}
		public void setTotal(String total) {
			this.total = total;
		}
		public String getNote() {
			return note;
		}
		public void setNote(String note) {
			this.note = note;
		}
	}
}
