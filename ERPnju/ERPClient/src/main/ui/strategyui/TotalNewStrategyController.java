package main.ui.strategyui;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.util.ClientSearchingController;
import main.ui.util.GoodsSearchingController;
import main.vo.DiscountStrategyVO;
import main.vo.GiftStrategyVO;
import main.vo.OutGoodsVO;

public class TotalNewStrategyController implements Initializable{

	@FXML Button confirm;
	@FXML Button cancel;
	@FXML JFXTextField leastTotal;
	@FXML MenuButton StrategyType;
	@FXML MenuItem gift;
	@FXML MenuItem Discount;
	@FXML AnchorPane area;
	@FXML Label id;
	@FXML Tab Goods;
	@FXML Tab Clients;
	@FXML DatePicker start;
	@FXML DatePicker end;
	@FXML Button search;
	@FXML Button add;
	
	GiftController gc=null;
	DiscountController dc=null;
	ClientSearchingController csc=null;
	GoodsSearchingController gsc=null;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO 自动生成的方法存根
		Clients.setContent(getClientAnchorPane());
		Clients.setClosable(false);
		Goods.setContent(getGoodsAnchorPane());
		Goods.setClosable(false);
		id.setText(new Numbering().number(2));
		gsc.onInit();
	}
	
	@FXML
	public void setGiftAnchorPane(){
		StrategyType.setText("赠品");
		area.getChildren().clear();
		area.getChildren().add(getGiftAnchorPane());
		dc=null;
	}
	
	@FXML
	public void setDiscountAnchorPane(){
		StrategyType.setText("折扣");
		area.getChildren().clear();
		area.getChildren().add(getDiscountAnchorPane());
		gc=null;
	}
	
	private AnchorPane getDiscountAnchorPane() {
		// TODO 自动生成的方法存根
		AnchorPane ap=new AnchorPane();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(DiscountController.class.getResource("DiscountUi.fxml"));
		try {
			ap=loader.load();
			dc=(DiscountController)loader.getController();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return ap;
	}
	
	private AnchorPane getGiftAnchorPane() {
		// TODO 自动生成的方法存根
		AnchorPane ap=new AnchorPane();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(GiftController.class.getResource("GiftUi.fxml"));
		try {
			ap=loader.load();
			gc=(GiftController)loader.getController();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return ap;
	}
	
	private AnchorPane getClientAnchorPane() {
		// TODO 自动生成的方法存根
		AnchorPane ap=new AnchorPane();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(ClientSearchingController.class.getResource("ClientSearching.fxml"));
		try {
			ap=loader.load();
			csc=(ClientSearchingController)loader.getController();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return ap;
	}
	
	private AnchorPane getGoodsAnchorPane() {
		// TODO 自动生成的方法存根
		AnchorPane ap=new AnchorPane();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(GoodsSearchingController.class.getResource("GoodsSearching.fxml"));
		try {
			ap=loader.load();
			gsc=(GoodsSearchingController)loader.getController();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return ap;
	}
	
	@FXML
	public void confirm(){
		if(start.getValue()==null||end.getValue()==null||leastTotal.getText()==null||(gc==null&&dc==null)){
			Alert nullAlert=new Alert(Alert.AlertType.ERROR,"请输入完整数据");
			nullAlert.setHeaderText(null);
			nullAlert.showAndWait();
		}
		if(start.getValue().isBefore(LocalDate.now())||end.getValue().isBefore(LocalDate.now())||start.getValue().isAfter(end.getValue())){
			Alert nullAlert=new Alert(Alert.AlertType.ERROR,"请输入正确时间");
			nullAlert.setHeaderText(null);
			nullAlert.showAndWait();
			return;
		}
		if(gc!=null&&dc==null){
			ObservableList<Goods> l=gc.goods.getItems();
			if(l.isEmpty()){
				Alert nullAlert=new Alert(Alert.AlertType.ERROR,"请输入完整数据");
				nullAlert.setHeaderText(null);
				nullAlert.showAndWait();
				return;
			}
			Map<OutGoodsVO,Integer> list=new HashMap<>();
			for(Goods g:l){
				OutGoodsVO gv;
				try {
					gv = RemoteHelper.getInstance().getStockblService().getGoods(g.getId());
					list.put(gv,Integer.parseInt( g.getNum()));
				} catch (RemoteException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			GiftStrategyVO gs=new GiftStrategyVO();
			gs.setID(id.getText());
			gs.setList(list);
			gs.setLeastTotal(Double.parseDouble(leastTotal.getText()));
			Calendar startTime=new GregorianCalendar();
			Calendar endTime=new GregorianCalendar();
			try {
				startTime.setTime(new SimpleDateFormat("yyyy-MM-dd").parse((start.getValue().toString())));
				endTime.setTime(new SimpleDateFormat("yyyy-MM-dd").parse((end.getValue().toString())));
			} catch (ParseException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			gs.setStartTime(startTime);
			gs.setEndTime(endTime);
			try {
				if(RemoteHelper.getInstance().getStrategyblService().makeStrategy(gs)){
					ClientRunner.getLogger().record("新建促销策略 "+gs.getID());
					Alert success=new Alert(Alert.AlertType.INFORMATION,"新增成功!");
					success.setHeaderText(null);
					success.showAndWait();
				}
				else{
					Alert nullAlert=new Alert(Alert.AlertType.ERROR,"新增失败!");
					nullAlert.setHeaderText(null);
					nullAlert.showAndWait();
					return;
				}
			} catch (RemoteException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			gc.goods.setItems(null);
		}else if(gc==null&&dc!=null){
			double discount=Double.parseDouble(dc.discount.getText());
			if(discount>=1){
				Alert nullAlert=new Alert(Alert.AlertType.ERROR,"数据不符合要求!");
				nullAlert.setHeaderText(null);
				nullAlert.showAndWait();
				return;
			}
			DiscountStrategyVO ds=new DiscountStrategyVO();
			ds.setID(id.getText());
			ds.setDiscount(discount);
			ds.setLeastTotal(Double.parseDouble(leastTotal.getText()));
			Calendar startTime=new GregorianCalendar();
			Calendar endTime=new GregorianCalendar();
			try {
				startTime.setTime(new SimpleDateFormat("yyyy-MM-dd").parse((start.getValue().toString())));
				endTime.setTime(new SimpleDateFormat("yyyy-MM-dd").parse((end.getValue().toString())));
			} catch (ParseException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			ds.setStartTime(startTime);
			ds.setEndTime(endTime);
			try {
				if(RemoteHelper.getInstance().getStrategyblService().makeStrategy(ds)){
					ClientRunner.getLogger().record("新建促销策略 "+ds.getID());
					Alert success=new Alert(Alert.AlertType.INFORMATION,"新增成功!");
					success.setHeaderText(null);
					success.showAndWait();
				}
				else{
					Alert nullAlert=new Alert(Alert.AlertType.ERROR,"新增失败!");
					nullAlert.setHeaderText(null);
					nullAlert.showAndWait();
					return;
				}
			} catch (RemoteException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		ClientRunner.getLogger().record("新建促销策略"+id.getText());
		cancel();
	}
	
	@FXML
	public void cancel(){
		id.setText(new Numbering().number(2));
		leastTotal.setText(null);
		StrategyType.setText("选择促销策略类型");
		area.getChildren().clear();
		start.setValue(null);
		end.setValue(null);
	}
	
	@FXML
	public void searchGoods(){
		if(gsc!=null){
			gsc.search();
			System.out.println("search ok");
		}
			
	}
	
	@FXML
	public void add(){
		if(gsc.getSelected()!=null&&gc!=null){
			String s[]=gsc.getSelected().split("<>");
			Goods g=new Goods(s[1], s[0],0, Double.parseDouble(s[3]));
			gc.addGoods(g);
		}
	}
}
