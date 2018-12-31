package main.ui.saleui;

import java.util.ArrayList;

import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import main.vo.SaleReceiptBothVOLineItem;
import main.vo.SaleReceiptVO;
import main.vo.SaleReturnReceiptVO;

public class ShowUnrevisedReturnReceiptUIController {
	
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
	JFXTextField clerkId;
	@FXML
	JFXTextField operatorId;
	@FXML
	JFXTextField discountT;
	@FXML
	JFXTextField voucherT;
	@FXML
	JFXTextField packageT;
	@FXML
	JFXTextField otherT;
	@FXML
	JFXTextField total;//原价
	@FXML
	JFXTextField finalTotal;//活动后总价
	@FXML
	TextArea strategyDetailT;
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
		System.out.println("SaleInit");
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
	
	public void setReceipt(SaleReturnReceiptVO receipt){
		this.clerkId.setText(receipt.getClerkId());
		this.clientId.setText(receipt.getClientId());
		this.operatorId.setText(receipt.getCreatorId());
		this.discountT.setText(receipt.getDiscount()+"");
		this.finalTotal.setText(Math.min(Math.min(receipt.getTotalAfterDiscount(), receipt.getTotalAfterPackage()), receipt.getTotalAfterVoucher())+"");
		this.note.setText(receipt.getNote());
		this.receiptId.setText(receipt.getId());
		this.otherT.setText(receipt.getFriendlyDiscount()+"");
		this.stockId.setText(receipt.getStockId());
		this.voucherT.setText(receipt.getValueOfVoucher()+"");
		this.total.setText(receipt.getTotalBeforeDiscountOrVoucher()+"");
		this.packageT.setText(receipt.getReductionOfPackage()+"");
		this.strategyDetailT.setText(receipt.getStrategyNote());
		
		ArrayList<SaleReceiptBothVOLineItem> itemList=receipt.getItemList();
		for(SaleReceiptBothVOLineItem item:itemList){
			TableNode node=new TableNode(item.getGoodsId(),item.getGoodsName(),item.getGoodsXh(),item.getGoodsAmount()+"",item.getGoodsPrice()+"",item.getSubTotal()+"",item.getNote());
			goodsList.add(node);
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
		public TableNode(String id, String name, String xh, String amount, String price, String total, String note) {
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
