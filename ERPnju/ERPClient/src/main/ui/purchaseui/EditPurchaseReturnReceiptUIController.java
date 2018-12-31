package main.ui.purchaseui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import main.businesslogicservice.purchaseblservice.PurchaseblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.clientui.UtilClientSearchUIController;
import main.ui.purchaseui.EditPurchaseReceiptUIController.TableNode;
import main.ui.util.GoodsSearchingController;
import main.vo.PurchaseReceiptBothVOLineItem;
import main.vo.PurchaseReceiptVO;
import main.vo.PurchaseReturnReceiptVO;

public class EditPurchaseReturnReceiptUIController {
	PurchaseblService purchaseblservice=RemoteHelper.getInstance().getPurchaseblService();
	String currentUserId="";
	GoodsSearchingController controller;
	UtilClientSearchUIController clientController;
	Map<String,Integer> record=new HashMap<String,Integer>();
	
	@FXML
	AnchorPane root;
	
	/**
	 * 右侧查询界面
	 */
	@FXML
	AnchorPane goodsPane;
	AnchorPane p;
	/**
	 * 右侧客户查询界面
	 */
	@FXML
	AnchorPane clientPane;
	AnchorPane pp;//load时的辅助
	
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
	@FXML
	AnchorPane buttons;
	@FXML
	JFXButton save;
	@FXML
	JFXButton submit;
	
	
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
	
	/**
	 * 设置当前用户id
	 * @param currentUserId
	 */
	public void setUser(String currentUserId){
		this.currentUserId=currentUserId;
	}
	
	/**
	 * 确认提交
	 */
	@FXML
	public void onSubmit(){
		if(this.checkComplete()){
			if(this.checkNum()){
				System.out.println("submit");
				PurchaseReturnReceiptVO purchaseReturnReceipt=null;
				String clientId=this.clientId.getText();
				String stockId=this.stockId.getText();
				String operatorId=this.operatorId.getText();
				String note=this.note.getText();
				ArrayList<PurchaseReceiptBothVOLineItem> itemList=new ArrayList<PurchaseReceiptBothVOLineItem>();
				for(TableNode g:goodsList){
					PurchaseReceiptBothVOLineItem item=null;
					String g_id=g.getId();
					String g_name=g.getName();
					String g_xh=g.getXh();
					String g_note=g.getNote();
					int g_amount=0;
					double g_price=0.0;
					double g_total=0.0;
					try{
						g_amount=Integer.parseInt(g.getAmount());
					}catch(Exception e){
						g_amount=0;
					}
					try{
						g_price=Double.parseDouble(g.getPrice());
					}catch(Exception e){
						g_price=0.0;
					}
					try{
						g_total=Double.parseDouble(g.getTotal());
					}catch(Exception e){
						g_total=0.0;
					}
					/**
					 * @param goodsId  商品编号
					 * @param goodsName  商品名称
				 	 * @param goodsXh  商品型号
					 * @param goodsAmount  数量
					 * @param goodsPrice  单价
					 * @param note  备注
					 */
					
					item=new PurchaseReceiptBothVOLineItem(g_id,g_name,g_xh,g_amount,g_price,g_note);
					itemList.add(item);
				}
				purchaseReturnReceipt=new PurchaseReturnReceiptVO(null,Calendar.getInstance(),null,operatorId,clientId,stockId,note,itemList);
				 PurchaseReturnReceiptVO res=null;
				try {
					res=purchaseblservice.makePurchaseReturnList(purchaseReturnReceipt);
					purchaseblservice.deleteDraft_p(this.receiptId.getText(), currentUserId);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String info="创建失败！";
				if(res!=null){
					info="创建成功！";
					ClientRunner.getLogger().record("通过草稿创建进货退货单  id: "+res.getId());
				}
				Alert alert=new Alert(AlertType.INFORMATION);
				alert.setTitle("提示");
				alert.setHeaderText(null);
				alert.setContentText(info);
				
				alert.showAndWait();
			}
			else{
				Alert numAlert=new Alert(AlertType.WARNING);
				numAlert.setContentText("商品数量超过库存数量！");
				numAlert.setTitle("警告");
				numAlert.setHeaderText(null);
				numAlert.showAndWait();
			}
		}
	}
	
	/**
	 * 存为草稿
	 */
	@FXML
	public void onSaveDraft(){
		System.out.println("save draft");
		PurchaseReturnReceiptVO purchaseReturnReceiptDraft=null;
		String clientId=this.clientId.getText();
		String stockId=this.stockId.getText();
		String operatorId=this.operatorId.getText();
		String note=this.note.getText();
		ArrayList<PurchaseReceiptBothVOLineItem> itemList=new ArrayList<PurchaseReceiptBothVOLineItem>();
		for(TableNode g:goodsList){
			PurchaseReceiptBothVOLineItem item=null;
			String g_id=g.getId();
			String g_name=g.getName();
			String g_xh=g.getXh();
			int g_amount=0;
			double g_price=0.0;
			double g_total=0.0;
			try{
				g_amount=Integer.parseInt(g.getAmount());
			}catch(Exception e){
				g_amount=0;
			}
			try{
				g_price=Double.parseDouble(g.getPrice());
			}catch(Exception e){
				g_price=0.0;
			}
			try{
				g_total=Double.parseDouble(g.getTotal());
			}catch(Exception e){
				g_total=0.0;
			}
	
			String g_note=g.getNote();
			item=new PurchaseReceiptBothVOLineItem(g_id,g_name,g_xh,g_amount,g_price,g_note);
			
			itemList.add(item);
		}

		purchaseReturnReceiptDraft=new PurchaseReturnReceiptVO(this.receiptId.getText(),Calendar.getInstance(),null,operatorId,clientId,stockId,note,itemList);
		PurchaseReturnReceiptVO res=null;
		try {
			res=purchaseblservice.modifyPurchaseReturnReceiptDraft(purchaseReturnReceiptDraft, currentUserId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String info="保存失败！";
		if(res!=null){
			info="保存成功！";
			ClientRunner.getLogger().record("修改进货退货单草稿  id: "+res.getId());
		}
		Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("提示");
		alert.setHeaderText(null);
		alert.setContentText(info);
		
		alert.showAndWait();
		
	}
	
	/**
	 * 清空
	 */
	@FXML
	public void onClear(){
		System.out.println("clear");
		goodsList.clear();
		this.clientId.clear();
		this.stockId.clear();
		this.note.clear();
		this.total.setText("0.0");
		
		table.refresh();
	}
	
	/**
	 * 移除已经添加的商品
	 */
	@FXML
	public void onRemoveGoods(){
		int index=table.getFocusModel().getFocusedIndex();
		if(index==-1){
			return;
		}
		else{
			System.out.println(index);
			goodsList.remove(index);
			table.refresh();
		}
	}
	
	
	@FXML
	public void onInit(){
		if(p!=null){
			return;
		}
		table.setEditable(true);
		goodsList=FXCollections.observableArrayList();
		goodsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        goodsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        goodsXh.setCellValueFactory(new PropertyValueFactory<>("xh"));
        goodsAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        goodsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        subTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        subNote.setCellValueFactory(new PropertyValueFactory<>("note"));
		
        //col
		  subNote.setCellFactory(
				  new PropertyValueFactory<TableNode,String>("note"));
		  subNote.setCellFactory(TextFieldTableCell.forTableColumn());
		  subNote.setOnEditCommit(
				  new EventHandler<CellEditEvent<TableNode,String>>(){
					@Override
					public void handle(CellEditEvent<TableNode, String> event) {
						// TODO Auto-generated method stub
						((TableNode) event.getTableView().getItems().get(
								event.getTablePosition().getRow())
								).setNote(event.getNewValue());
						showTotal();
					}
				  }
				  );
		  
		  //col
		  goodsXh.setCellFactory(
				  new PropertyValueFactory<TableNode,String>("xh"));
		  goodsXh.setCellFactory(TextFieldTableCell.forTableColumn());
		  goodsXh.setOnEditCommit(
				  new EventHandler<CellEditEvent<TableNode,String>>(){
					@Override
					public void handle(CellEditEvent<TableNode, String> event) {
						// TODO Auto-generated method stub
						((TableNode) event.getTableView().getItems().get(
								event.getTablePosition().getRow())
								).setXh(event.getNewValue());
						showTotal();
					}
				  }
				  );
		  
		  //col
		  goodsAmount.setCellFactory(
				  new PropertyValueFactory<TableNode,String>("amount"));
		  goodsAmount.setCellFactory(TextFieldTableCell.forTableColumn());
		  goodsAmount.setOnEditCommit(
				  new EventHandler<CellEditEvent<TableNode,String>>(){
					@Override
					public void handle(CellEditEvent<TableNode, String> event) {
						// TODO Auto-generated method stub
						((TableNode) event.getTableView().getItems().get(
								event.getTablePosition().getRow())
								).setAmount(event.getNewValue());
						showTotal();
					}
				  }
				  );
		  //col
		  goodsPrice.setCellFactory(
				  new PropertyValueFactory<TableNode,String>("price"));
		  goodsPrice.setCellFactory(TextFieldTableCell.forTableColumn());
		  goodsPrice.setOnEditCommit(
				  new EventHandler<CellEditEvent<TableNode,String>>(){
					@Override
					public void handle(CellEditEvent<TableNode,String> event) {
						// TODO Auto-generated method stub
						((TableNode) event.getTableView().getItems().get(
								event.getTablePosition().getRow())
								).setPrice(event.getNewValue());
						showTotal();
						
					}
				  }
				  );
        
        goodsAmount.setEditable(true);
        goodsXh.setEditable(true);
        goodsPrice.setEditable(true);
        subNote.setEditable(true);
	
		table.setItems(goodsList);
		
		operatorId.setText(currentUserId);
		//goods
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(GoodsSearchingController.class.getResource("GoodsSearching.fxml"));
			p=(AnchorPane)loader.load();
			controller=loader.getController();
			controller.onInit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.goodsPane.getChildren().add(p);
		
		//client
		//客户查找界面
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(UtilClientSearchUIController.class.getResource("UtilClientSearchUI.fxml"));
			pp=(AnchorPane)loader.load();
			clientController=loader.getController();
			clientController.onInit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		this.clientPane.getChildren().add(pp);
		
		//上下文菜单
				ContextMenu menu=new ContextMenu();
				MenuItem del=new MenuItem("删除");
				del.setOnAction((ActionEvent ae)->{
					
					//删除
					int index=table.getFocusModel().getFocusedIndex();
					if(index!=-1){
						Alert alert=new Alert(AlertType.CONFIRMATION);
						alert.setTitle("警告");
						alert.setHeaderText("您将要删除该项商品");
						alert.setContentText("是否确认删除？");
						
						Optional<ButtonType> res=alert.showAndWait();
						if(res.get()==ButtonType.OK){
							goodsList.remove(index);
							table.refresh();
						}
					}
				});
				
				menu.getItems().add(del);
				table.setContextMenu(menu);
				
				record=controller.getMap();

	}
	
	
	@FXML
	public void onSearch(){
		
		controller.search();
	}
	
	@FXML
	public void onAdd(){
	
		String result=controller.getSelected();
		System.out.println(result);
		String[] list=result.split("<>");//名称<>id<>型号<>单价
		System.out.println(list[0]);
		System.out.println(list[1]);
		
		TableNode node=new TableNode(list[1],list[0],list[2],"",list[3],"","");
		goodsList.add(node);
	}
	
	@FXML
	public void onSearchClient(){
		clientController.search();
	}
	
	@FXML
	public void onAddClient(){
		String result=clientController.getSelected();
		this.clientId.setText(result);
	}
	
	
	public void setReceipt(PurchaseReturnReceiptVO vo){
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
	}
	
	/**
	 * 刷新总价
	 */
	private void showTotal(){
		double total=0.0;
		for(TableNode node:goodsList){
			total+=node.tot;
		}
		this.total.setText(String.valueOf(total));
	}
	
	private boolean checkComplete(){
		if(this.clientId.getText().equals("")||this.stockId.getText().equals("")||this.goodsList.size()==0){
			Alert alert=new Alert(AlertType.WARNING);
			alert.setTitle("警报");
			alert.setHeaderText("信息填写不完整！");
			alert.setContentText("请完整填写单据信息");
			
			alert.showAndWait();
			
			return false;
		}
		
		return true;
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
				if(am<0){
					am=0;
					this.amount="";
				}
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
	
	private boolean checkNum(){
		//检查是否超过库存数量
		for(TableNode node:goodsList){
			String id=node.id;
			int num=Integer.parseInt(node.amount);
			int stock=record.get(id);
			if(stock<num){
				return false;
			}
		}
		return true;
	}
}
