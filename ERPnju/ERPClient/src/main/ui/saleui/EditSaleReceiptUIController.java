package main.ui.saleui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.businesslogicservice.saleblservice.SaleblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.clientui.UtilClientSearchUIController;
import main.ui.saleui.SaleUIController.TableNode;
import main.ui.util.GoodsSearchingController;
import main.vo.BargainPackageStrategyVO;
import main.vo.ClientCouponStrategyVO;
import main.vo.ClientDiscountStrategyVO;
import main.vo.ClientGiftStrategyVO;
import main.vo.DiscountStrategyVO;
import main.vo.GiftStrategyVO;
import main.vo.OutGoodsVO;
import main.vo.PurchaseReceiptVO;
import main.vo.SaleReceiptBothVOLineItem;
import main.vo.SaleReceiptVO;
import main.vo.SaleReturnReceiptVO;
import main.vo.StockGiftReceiptVO;
import main.vo.StockGiftReceiptVOLineItem;
import main.vo.StrategyVO;

public class EditSaleReceiptUIController {
	SaleblService saleblservice=RemoteHelper.getInstance().getSaleblService();
	String currentUserId="";//由主界面传入
	GoodsSearchingController goodsController;
	UtilClientSearchUIController clientController;
	UtilChooseStrategyUIController strategyController;
	//还有销售策略界面的Controller
	
	Map<String,Integer> record=new HashMap<String,Integer>();
	
	@FXML
	AnchorPane root;
	
	/**
	 * 右侧商品查询界面
	 */
	@FXML
	AnchorPane goodsPane;
	AnchorPane gp;//load时的辅助
	
	@FXML
	Button chooseGoods;
	@FXML
	Button searchGoods;
	
	/**
	 * 右侧客户查询界面
	 */
	@FXML
	AnchorPane clientPane;
	AnchorPane cp;//load时的辅助
	
	@FXML
	Button chooseClient;
	@FXML
	Button searchClient;
	
	/**
	 * 右侧销售策略查询界面
	 */
	@FXML
	AnchorPane strategyPane;
	AnchorPane sp;//load时的辅助
	
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
	@FXML
	JFXButton save;
	@FXML
	JFXButton submit;
	@FXML
	AnchorPane buttons;
	
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
      
		table.setEditable(true);
        goodsAmount.setEditable(true);
        goodsXh.setEditable(true);
        goodsPrice.setEditable(true);
        subNote.setEditable(true);
	
	    table.setItems(goodsList);
		
		operatorId.setText(currentUserId);
		
		//goodsPane
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(GoodsSearchingController.class.getResource("GoodsSearching.fxml"));
			gp=(AnchorPane)loader.load();
			goodsController=loader.getController();
			goodsController.onInit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.goodsPane.getChildren().add(gp);
		
		//clientPane
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(UtilClientSearchUIController.class.getResource("UtilClientSearchUI.fxml"));
			cp=(AnchorPane)loader.load();
			clientController=loader.getController();
			clientController.onInit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.clientPane.getChildren().add(cp);
		
		//strategyPane
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(UtilChooseStrategyUIController.class.getResource("UtilChooseStrategyUI.fxml"));
			sp=(AnchorPane)loader.load();
			strategyController=loader.getController();
			strategyController.onInit();
			strategyController.setService(saleblservice);//传进去blservice
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.strategyPane.getChildren().add(sp);
		
		//其他
		this.operatorId.setText(currentUserId);
		
		
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
				
				record=goodsController.getMap();
	}
	
	@FXML
	public void onSubmit(){
		if(this.checkComplete()){
			if(this.checkNum()){
				System.out.println("Submit");
				SaleReceiptVO receipt=this.getReceiptFromUI();
				SaleReceiptVO res=null;
				try {
					res=saleblservice.makeSaleReceipt(receipt);
					saleblservice.deleteDraft(receipt.getId(), currentUserId);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String info="创建失败！";
				if(res!=null){
					info="创建成功！";
					ClientRunner.getLogger().record("通过草稿创建销售单  id: "+res.getId());
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
	
	@FXML
	public void onSaveDraft(){
		SaleReceiptVO receipt=this.getReceiptFromUI();
		SaleReceiptVO res=null;
		try {
			if(receipt==null){
				System.out.println("Null Pointer");
			}
			res=saleblservice.modifySaleReceiptDraft(receipt, currentUserId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String info="保存失败！";
		if(res!=null){
			info="保存成功！";
			ClientRunner.getLogger().record("修改销售单草稿  id: "+res.getId());
		}
		Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("提示");
		alert.setHeaderText(null);
		alert.setContentText(info);
		
		alert.showAndWait();
	}
	
	@FXML
	public void onClear(){
		goodsList.clear();
		this.clerkId.clear();
		this.clientId.clear();
		this.stockId.clear();
		this.note.clear();
		this.strategyDetailT.clear();
		this.otherT.setText("1.0");
		this.voucherT.setText("0.0");
		this.discountT.setText("1.0");
		this.packageT.setText("0.0");
		this.total.setText("0.0");
		this.finalTotal.setText("0.0");
		table.refresh();
	}
	
	@FXML
	public void onRemove(){
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
	public void onSearchGoods(){
		goodsController.search();
	}
	
	@FXML
	public void onAddGoods(){
		String result=goodsController.getSelected();
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
		clientId.setText(result);
	}
	
	@FXML
	public void onSearchStrategy(){
		SaleReceiptVO receipt=this.getReceiptFromUI();//要传给strategy界面的
		if(receipt==null){
			System.out.println("NULL");
		}
		strategyController.search(receipt);
	}
	
	@FXML
	public void onChooseStrategy(){
		
		Alert alert=new Alert(AlertType.CONFIRMATION);
		alert.setTitle("销售策略");
		alert.setHeaderText("您正在选择销售策略，销售策略不能重复使用");
		alert.setContentText("确定使用吗？");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get()==ButtonType.OK){
			this.clearStrategy();
			
			StrategyVO strategy=strategyController.choose();
			
			int type=strategy.getType();
			
			if(type==2||type==5){
				
				//要显示赠送单
				StockGiftReceiptVO receipt=null;
				Map<OutGoodsVO,Integer> map = null;
				if(type==2){
					//客户等级
					this.strategyDetailT.setText("客户等级赠送策略"+'\n');
					receipt=this.makeClientGiftReceipt((ClientGiftStrategyVO)strategy, currentUserId);
					map=((ClientGiftStrategyVO)strategy).getList();
				}
				else if(type==5){
					this.strategyDetailT.setText("总价赠送策略"+'\n');
					receipt=this.makeGiftReceipt((GiftStrategyVO)strategy, currentUserId);
					map=((GiftStrategyVO)strategy).getGiftlist();
				}
				String strategyDetail="";
				//show the receipt
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(GiftReceiptUIController.class.getResource("GiftReceiptUI.fxml"));
				AnchorPane grp=null;
				try{
					grp=loader.load();
					GiftReceiptUIController controller=(GiftReceiptUIController)loader.getController();
					controller.setUser(currentUserId);
					
					for(OutGoodsVO goods:map.keySet()){
						String tip="商品名称: "+goods.getName()+"   价格: "+goods.getOutPrice()+"   数量: "+map.get(goods);
						controller.add(tip);
						strategyDetail+=tip;
					}
					
					Scene scene=new Scene(grp);
					Stage stage=new Stage();
					stage.setScene(scene);
					stage.show();
				}catch(Exception e){
					e.printStackTrace();
				}
				
				this.strategyDetailT.appendText(strategyDetail);
			}
			else{
				//不要赠送单
				if(type==1){
					this.strategyDetailT.setText("客户等级折扣策略"+'\n');
					ClientDiscountStrategyVO s=(ClientDiscountStrategyVO)strategy;
					//"客户等级折扣";
					this.discountT.setText(s.getDiscount()+"");
				}
				else if(type==3){
					ClientCouponStrategyVO s=(ClientCouponStrategyVO)strategy;
					//"客户等级代金券";
					this.strategyDetailT.setText("发放代金券 "+s.getCoupon()+"元");
				}
				else if(type==4){
					//"总价折扣";
					DiscountStrategyVO s=(DiscountStrategyVO)strategy;
					this.discountT.setText(s.getDiscount()+"");
					this.strategyDetailT.setText("总价折扣策略");
				}
				else if(type==6){
					this.strategyDetailT.setText("使用特价包"+'\n');
					BargainPackageStrategyVO s=(BargainPackageStrategyVO)strategy;
					//"特价包";
					Map<OutGoodsVO, Integer> m=s.getGoodslist();
					String content="";
					double originValue=0.0;
					for(OutGoodsVO g:m.keySet()){
						String gname=g.getName();
						int gnum=m.get(g);
						originValue+=g.getOutPrice()*gnum;
						content+=gname+" "+gnum+"; ";
					}
					this.packageT.setText((originValue-s.getTotal())+"");
					this.strategyDetailT.appendText(content);
				}
			}
			
			
		}
		this.showTotal();
		this.showFinalTotal();
	}
	
	private void clearStrategy(){
		this.strategyDetailT.clear();
		this.discountT.setText("1.0");
		this.packageT.setText("0.0");
		this.voucherT.setText("0.0");
	}
	
	@FXML
	public void onCheckStrategy(){
		//查看策略详情
		StrategyVO vo=strategyController.check();
		int type=vo.getType();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(StrategyDetailUIController.class.getResource("StrategyDetailUI.fxml"));
		StrategyDetailUIController c=null;
		AnchorPane p=null;
		try {
			p=loader.load();
			c=(StrategyDetailUIController)loader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String tp="";
		String start="";
		String end="";
		String condition="";
		String content="";
	
			
	    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd-HH-mm");
	    start=df.format(vo.getStartTime().getTime());
	    end=df.format(vo.getEndTime().getTime());

	    
		if(type==1){
			ClientDiscountStrategyVO s=(ClientDiscountStrategyVO)vo;
			tp="客户等级折扣";
			condition="等级达到: "+s.getLevel();
			content="折扣: "+s.getDiscount();
		}
		else if(type==2){
			ClientGiftStrategyVO s=(ClientGiftStrategyVO)vo;
			tp="客户等级赠品";
			condition="等级达到: "+s.getLevel();
			Map<OutGoodsVO,Integer> m=s.getList();
			for(OutGoodsVO g:m.keySet()){
				String gname=g.getName();
				int gnum=m.get(g);
				content+=gname+" "+gnum+"; ";
			}
		}
		else if(type==3){
			ClientCouponStrategyVO s=(ClientCouponStrategyVO)vo;
			tp="客户等级代金券";
			condition="等级达到: "+s.getLevel();
			content="代金券金额: "+s.getCoupon();
		}
		else if(type==4){
			tp="总价折扣";
			DiscountStrategyVO s=(DiscountStrategyVO)vo;
			condition="总价达到: "+s.getLeastTotal();
			content="折扣: "+s.getDiscount();
		}
		else if(type==5){
			GiftStrategyVO s=(GiftStrategyVO)vo;
			tp="总价赠品";
			condition="总价达到: "+s.getLeastTotal();
			Map<OutGoodsVO,Integer> m=s.getGiftlist();
			for(OutGoodsVO g:m.keySet()){
				String gname=g.getName();
				int gnum=m.get(g);
				content+=gname+" "+gnum+"; ";
			}
			
		}
		else if(type==6){
			BargainPackageStrategyVO s=(BargainPackageStrategyVO)vo;
			tp="特价包";
			Map<OutGoodsVO, Integer> m=s.getGoodslist();
			for(OutGoodsVO g:m.keySet()){
				String gname=g.getName();
				int gnum=m.get(g);
				content+=gname+" "+gnum+"; ";
			}
		}
		c.onInit(tp, start, end,condition, content);
		Scene scene=new Scene(p);
		Stage stage=new Stage();
		stage.setScene(scene);
		stage.show();
	}
	
	
	
	private SaleReceiptVO getReceiptFromUI(){
		SaleReceiptVO vo=null;
		String id=this.receiptId.getText();
		String creatorId=this.operatorId.getText();
		String clientId=this.clientId.getText();
		String clerkId=this.clerkId.getText();
		String stockId=this.stockId.getText();
		double discount=1.0;
		double valueOfVoucher=0.0;
		String note=this.note.getText();
		double friendlyDiscount=1.0;
		double reductionOfPackage=0.0;
		String strategyNote=this.strategyDetailT.getText();
		try{
			discount=Double.parseDouble(this.discountT.getText());
		}catch(Exception e){
			discount=1.0;
			this.discountT.setText("1.0");
		}
		
		try{
			valueOfVoucher=Double.parseDouble(this.voucherT.getText());
		}catch(Exception e){
			valueOfVoucher=0.0;
			this.voucherT.setText("0.0");
		}
		
		try{
			friendlyDiscount=Double.parseDouble(this.otherT.getText());
		}catch(Exception e){
			friendlyDiscount=1.0;
			this.otherT.setText("1.0");
		}
		
		try{
			reductionOfPackage=Double.parseDouble(this.packageT.getText());
		}catch(Exception e){
			reductionOfPackage=0.0;
		}
		
		//lineitem
		ArrayList<SaleReceiptBothVOLineItem> itemList=new ArrayList<SaleReceiptBothVOLineItem>();
		for(TableNode node:goodsList){
			String goodsId=node.getId();
			String goodsName=node.getName();
			String goodsXh=node.getXh();
			long goodsAmount=Long.parseLong(node.getAmount());
			double goodsPrice=Double.parseDouble(node.getPrice());
			String subNote=node.getNote();
			SaleReceiptBothVOLineItem item=new SaleReceiptBothVOLineItem(goodsId,goodsName,goodsXh,goodsAmount,goodsPrice,subNote);
			itemList.add(item);
		}
		
		
		vo=new SaleReceiptVO(id, Calendar.getInstance(),null,creatorId,
				clientId,clerkId,stockId, discount,valueOfVoucher, note,
		        itemList, strategyNote,reductionOfPackage,friendlyDiscount);
		
		return vo;
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
		if(this.clerkId.getText().equals("")||this.clerkId.getText().equals("")||this.stockId.getText().equals("")||this.goodsList.size()==0){
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
	
	private StockGiftReceiptVO makeClientGiftReceipt(ClientGiftStrategyVO strategy,String userId){
		Map<OutGoodsVO,Integer> giftlist=strategy.getList();
		ArrayList<StockGiftReceiptVOLineItem> list=new ArrayList<StockGiftReceiptVOLineItem>();
		Set set=giftlist.entrySet();
		Iterator iterator=set.iterator();
		while(iterator.hasNext()){
			Map.Entry<OutGoodsVO, Integer> entry=(Map.Entry<OutGoodsVO, Integer>) iterator.next();
			OutGoodsVO g=entry.getKey();
			String goodsName=g.getName();
			String goodsXh=g.getXh();
			int amount=entry.getValue();
			double value=amount*g.getOutPrice();
			StockGiftReceiptVOLineItem item=new StockGiftReceiptVOLineItem(goodsName,goodsXh,amount,value,g.getId());
			list.add(item);
		}
		StockGiftReceiptVO giftReceipt=new StockGiftReceiptVO(null/*编号，要调用numbering方法或交给其他人添加*/,Calendar.getInstance(),null/*审批时设置*/,userId,list);
		return giftReceipt;
	}

	private StockGiftReceiptVO makeGiftReceipt(GiftStrategyVO strategy,String userId){
		Map<OutGoodsVO,Integer> giftlist=strategy.getGiftlist();
		ArrayList<StockGiftReceiptVOLineItem> list=new ArrayList<StockGiftReceiptVOLineItem>();
		Set set=giftlist.entrySet();
		Iterator iterator=set.iterator();
		while(iterator.hasNext()){
			Map.Entry<OutGoodsVO, Integer> entry=(Map.Entry<OutGoodsVO, Integer>) iterator.next();
			OutGoodsVO g=entry.getKey();
			String goodsName=g.getName();
			String goodsXh=g.getXh();
			int amount=entry.getValue();
			double value=amount*g.getOutPrice();
			StockGiftReceiptVOLineItem item=new StockGiftReceiptVOLineItem(goodsName,goodsXh,amount,value,g.getId());
			list.add(item);
		}
		StockGiftReceiptVO giftReceipt=new StockGiftReceiptVO(null/*编号，要调用numbering方法或交给其他人添加*/,Calendar.getInstance(),null/*审批时设置*/,userId,list);
		return giftReceipt;
	}
	
	public void setUser(String userId){
		this.currentUserId=userId;
	}
	
	public void setReceipt(SaleReceiptVO receipt){
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
	@FXML
	public void showFinalTotal(){
		double ot=Double.parseDouble(this.total.getText());
	    double discount=Double.parseDouble(this.discountT.getText());
	    double voucher=Double.parseDouble(this.voucherT.getText());
	    double pkg=Double.parseDouble(this.packageT.getText());
	    double friendDis=Double.parseDouble(this.otherT.getText());
	    double res=(ot-voucher-pkg)*discount*friendDis;
	    this.finalTotal.setText(res+"");
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
