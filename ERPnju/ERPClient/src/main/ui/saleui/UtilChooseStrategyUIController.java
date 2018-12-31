package main.ui.saleui;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import main.businesslogicservice.saleblservice.SaleblService;
import main.vo.BargainPackageStrategyVO;
import main.vo.ClientCouponStrategyVO;
import main.vo.ClientDiscountStrategyVO;
import main.vo.ClientGiftStrategyVO;
import main.vo.DiscountStrategyVO;
import main.vo.GiftStrategyVO;
import main.vo.OutGoodsVO;
import main.vo.SaleReceiptBothVO;
import main.vo.SaleReceiptVO;
import main.vo.StrategyVO;

public class UtilChooseStrategyUIController {
	SaleblService saleblservice;
	
	@FXML
	JFXCheckBox clientLevel;
	@FXML
	JFXCheckBox totalPrice;
	@FXML
	JFXCheckBox gift;
	@FXML
	JFXCheckBox discount;
	@FXML
	JFXCheckBox bargainBag;
	@FXML
	JFXCheckBox voucher;
	@FXML
	AnchorPane tablePane;

	
	JFXTreeTableColumn<Strategy,String> typeCol;
	JFXTreeTableColumn<Strategy,String> idCol;
	JFXTreeTableColumn<Strategy,String> conditionCol;
	JFXTreeTableColumn<Strategy,String> contentCol;
	
	ObservableList<Strategy> strategyList=FXCollections.observableArrayList();
	JFXTreeTableView<Strategy> table;
	
	/**
	 * 传进来blservice的引用
	 * @param bl
	 */
	public void setService(SaleblService bl){
		saleblservice=bl;
	}
	
	public void onInit(){
		System.out.println("strategy init");
		
		//col
		typeCol=new JFXTreeTableColumn<>("策略类型");
		typeCol.setPrefWidth(80);
		typeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Strategy,String> param) ->{
			if(typeCol.validateValue(param)) return param.getValue().getValue().type;
			else return typeCol.getComputedValue(param);
		});
		//col
		idCol=new JFXTreeTableColumn<>("策略编号");
		idCol.setPrefWidth(80);
		idCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Strategy,String> param) ->{
			if(idCol.validateValue(param)) return param.getValue().getValue().id;
			else return idCol.getComputedValue(param);
		});
		//col
		conditionCol=new JFXTreeTableColumn<>("策略条件");
		conditionCol.setPrefWidth(80);
		conditionCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Strategy,String> param) ->{
			if(conditionCol.validateValue(param)) return param.getValue().getValue().condition;
			else return conditionCol.getComputedValue(param);
		});
		//col
		contentCol=new JFXTreeTableColumn<>("策略内容");
		contentCol.setPrefWidth(85);
		contentCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Strategy,String> param) ->{
			if(contentCol.validateValue(param)) return param.getValue().getValue().content;
			else return contentCol.getComputedValue(param);
		});
		
	
		TreeItem<Strategy> root=new RecursiveTreeItem<Strategy>(strategyList,RecursiveTreeObject::getChildren);
		table=new JFXTreeTableView<Strategy>(root);
		table.setMinWidth(325);
		table.setPrefHeight(470);
		table.setShowRoot(false);
		table.setEditable(true);
		table.getColumns().setAll(typeCol,idCol,conditionCol,contentCol);
		
		tablePane.getChildren().add(table);
		
	}
	
	public void search(SaleReceiptBothVO receipt){
		strategyList.clear();
		ArrayList<StrategyVO> list=new ArrayList<StrategyVO>();
		ArrayList<Strategy> slist=new ArrayList<Strategy>();
		
		try {
			list=saleblservice.showValidStrategy(receipt);
			int size=list.size();
			System.out.println("策略总数："+size);		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(this.clientLevel.isSelected()&&this.gift.isSelected()){
			//客户赠品
			ArrayList<Strategy> sl=this.chooseLevelGift(list);
			for(Strategy s:sl){
				slist.add(s);
			}
		}
		
		if(this.clientLevel.isSelected()&&this.discount.isSelected()){
			//客户折扣
			ArrayList<Strategy> sl=this.chooseLevelDiscount(list);
			for(Strategy s:sl){
				slist.add(s);
			}
		}
		
		if(this.totalPrice.isSelected()&&this.gift.isSelected()){
			//总价赠品
			ArrayList<Strategy> sl=this.chooseTotalGift(list);
			for(Strategy s:sl){
				slist.add(s);
			}

		}
		
		if(this.totalPrice.isSelected()&&this.discount.isSelected()){
			//总价折扣
			ArrayList<Strategy> sl=this.chooseTotalDiscount(list);
			for(Strategy s:sl){
				slist.add(s);
			}
		}
		
		if(this.voucher.isSelected()){
			//代金券
			ArrayList<Strategy> sl=this.chooseLevelVoucher(list);
			for(Strategy s:sl){
				slist.add(s);
			}
		}
		
		if(this.bargainBag.isSelected()){
			//特价包
			ArrayList<Strategy> sl=this.chooseBargainBag(list);
			for(Strategy s:sl){
				slist.add(s);
			}
		}
		
		//添加到observablelist中去
		for(Strategy s:slist){
			strategyList.add(s);
		}
		table.refresh(); 
	}
	
	public StrategyVO choose(){
		int index=table.getFocusModel().getFocusedIndex();
		StrategyVO vo=null;
		if(index==-1){
			return null;
		}
		else{
			String id=strategyList.get(index).id.get();
			try {
				vo=saleblservice.getStrategyById(id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vo;
	}
	
	public StrategyVO check(){
		int index=table.getFocusModel().getFocusedIndex();
		StrategyVO vo=null;
		if(index==-1){
			return null;
		}
		else{
			String id=strategyList.get(index).id.get();
			try {
				vo=saleblservice.getStrategyById(id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vo;
	}
	
	private ArrayList<Strategy> chooseLevelGift(ArrayList<StrategyVO> list){//2
		ArrayList<Strategy> result=new ArrayList<Strategy>();
		for(StrategyVO vo:list){
			if(vo.getType()==2){
				ClientGiftStrategyVO s=(ClientGiftStrategyVO)vo;
				String id=s.getID();
				String condition=""+s.getLevel();
				Map<OutGoodsVO,Integer> m=s.getList();
				String content="";
				for(OutGoodsVO g:m.keySet()){
					String gname=g.getName();
					int gnum=m.get(g);
					content+=gname+" "+gnum+"; ";
				}
				
				Strategy strategy=new Strategy("客户等级赠品",id,condition,content);
				result.add(strategy);
			}
		}
		
		return result;
	}
	
	private ArrayList<Strategy> chooseLevelDiscount(ArrayList<StrategyVO> list){//1
		ArrayList<Strategy> result=new ArrayList<Strategy>();
		
		for(StrategyVO vo:list){
			if(vo.getType()==1){
				ClientDiscountStrategyVO s=(ClientDiscountStrategyVO)vo;
				String id=s.getID();
				String condition=""+s.getLevel();
				String content=""+s.getDiscount();
				
				Strategy strategy=new Strategy("客户等级折扣",id,condition,content);
				result.add(strategy);
			}
		}
		
		return result;
	}
	
	private ArrayList<Strategy> chooseLevelVoucher(ArrayList<StrategyVO> list){//3
		ArrayList<Strategy> result=new ArrayList<Strategy>();
		
		for(StrategyVO vo:list){
			if(vo.getType()==3){
				ClientCouponStrategyVO s=(ClientCouponStrategyVO)vo;
				String id=s.getID();
				String condition=""+s.getLevel();
				String content=""+s.getCoupon();
				
				Strategy strategy=new Strategy("客户等级代金券",id,condition,content);
				result.add(strategy);
			}
		}
		
		return result;
	}
	
	private ArrayList<Strategy> chooseTotalGift(ArrayList<StrategyVO> list){//5
		ArrayList<Strategy> result=new ArrayList<Strategy>();
		
		for(StrategyVO vo:list){
			if(vo.getType()==5){
				GiftStrategyVO s=(GiftStrategyVO)vo;
				String id=s.getID();
				String condition=""+s.getLeastTotal();
				Map<OutGoodsVO,Integer> m=s.getGiftlist();
				String content="";
				for(OutGoodsVO g:m.keySet()){
					String gname=g.getName();
					int gnum=m.get(g);
					content+=gname+" "+gnum+"; ";
				}
				
				Strategy strategy=new Strategy("总价赠品",id,condition,content);
				result.add(strategy);
			}
		}
		
		
		return result;
	}
	
	private ArrayList<Strategy> chooseTotalDiscount(ArrayList<StrategyVO> list){//4
		ArrayList<Strategy> result=new ArrayList<Strategy>();
		
		for(StrategyVO vo:list){
			if(vo.getType()==4){
				DiscountStrategyVO s=(DiscountStrategyVO)vo;
				String id=s.getID();
				String condition=""+s.getLeastTotal();
				String content=""+s.getDiscount();
				
				Strategy strategy=new Strategy("总价折扣",id,condition,content);
				result.add(strategy);
			}
		}
		
		return result;
	}
	
	private ArrayList<Strategy> chooseBargainBag(ArrayList<StrategyVO> list){//6
		ArrayList<Strategy> result=new ArrayList<Strategy>();
		
		for(StrategyVO vo:list){
			if(vo.getType()==6){
				BargainPackageStrategyVO s=(BargainPackageStrategyVO)vo;
				String id=s.getID();
				String condition="";
				Map<OutGoodsVO, Integer> m=s.getGoodslist();
				String content="";
				for(OutGoodsVO g:m.keySet()){
					String gname=g.getName();
					int gnum=m.get(g);
					content+=gname+" "+gnum+"; ";
				}
				
				
				Strategy strategy=new Strategy("特价包",id,condition,content);
				result.add(strategy);
			}
		}
		
		return result;
	}

	public class Strategy extends RecursiveTreeObject<Strategy>{
		StringProperty type;
		StringProperty id;
		StringProperty condition;
		StringProperty content;
		
		public Strategy(String type,String id,String condition,String content){
			this.type=new SimpleStringProperty(type);
			this.id=new SimpleStringProperty(id);
			this.condition=new SimpleStringProperty(condition);
			this.content=new SimpleStringProperty(content);
		}
	}
}
