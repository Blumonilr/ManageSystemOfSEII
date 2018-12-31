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

import com.jfoenix.controls.JFXButton;

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
import main.vo.ClientCouponStrategyVO;
import main.vo.ClientDiscountStrategyVO;
import main.vo.ClientGiftStrategyVO;
import main.vo.OutGoodsVO;

public class ClientNewStrategyController implements Initializable{
	
	@FXML Button confirm;
	@FXML Button cancel;
	@FXML Tab Goods;
	@FXML Tab Clients;
	@FXML AnchorPane area;
	@FXML MenuButton StrategyType;
	@FXML MenuItem gift;
	@FXML MenuItem coupon;
	@FXML MenuItem discount;
	@FXML MenuButton level;
	@FXML MenuItem one;
	@FXML MenuItem two;
	@FXML MenuItem three;
	@FXML MenuItem four;
	@FXML MenuItem five;
	@FXML Label id;
	@FXML DatePicker start;
	@FXML DatePicker end;
	@FXML JFXButton search;
	@FXML JFXButton add;
	@FXML AnchorPane goodsui;
	@FXML AnchorPane clientui;
	
	GiftController gc=null;
	CouponController cc=null;
	DiscountController dc=null;
	ClientSearchingController csc=null;
	GoodsSearchingController gsc=null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO 自动生成的方法存根
		clientui.getChildren().add(getClientAnchorPane());
		Clients.setClosable(false);
		goodsui.getChildren().add(getGoodsAnchorPane());
		Goods.setClosable(false);
		id.setText(new Numbering().number(1));
		gsc.onInit();
	}
	

	@FXML
	public void setGiftAnchorPane(){
		StrategyType.setText("赠品");
		area.getChildren().clear();
		area.getChildren().add(getGiftAnchorPane());
		cc=null;
		dc=null;
	}
	
	@FXML
	public void setCouponAnchorPane(){
		StrategyType.setText("代金券");
		area.getChildren().clear();
		area.getChildren().add(getCouponAnchorPane());
		gc=null;
		dc=null;
	}
	
	@FXML
	public void setDiscountAnchorPane(){
		StrategyType.setText("折扣");
		area.getChildren().clear();
		area.getChildren().add(getDiscountAnchorPane());
		gc=null;
		cc=null;
	}
	
	@FXML public void setOne(){level.setText("1");};
	@FXML public void setTwo(){level.setText("2");};
	@FXML public void setThree(){level.setText("3");};
	@FXML public void setFour(){level.setText("4");};
	@FXML public void setFive(){level.setText("5");};

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

	private AnchorPane getCouponAnchorPane() {
		// TODO 自动生成的方法存根
		AnchorPane ap=new AnchorPane();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(CouponController.class.getResource("CouponUi.fxml"));
		try {
			ap=loader.load();
			cc=(CouponController)loader.getController();
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
		if(start.getValue()==null||end.getValue()==null||level.getText()==null||(gc==null&&cc==null&&dc==null)){
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
		if(gc!=null&&cc==null&&dc==null){
			ObservableList<Goods> l=gc.list;
			if(l.isEmpty()){
				Alert nullAlert=new Alert(Alert.AlertType.ERROR,"表中无商品");
				nullAlert.setHeaderText(null);
				nullAlert.showAndWait();
				return;
			}
			Map<OutGoodsVO,Integer> list=new HashMap<>();
			for(Goods g:l){
				OutGoodsVO gv;
				try {
					gv = RemoteHelper.getInstance().getStockblService().getGoods(g.getId());
					if(Integer.parseInt(g.getNum())<=0){
						Alert nullAlert=new Alert(Alert.AlertType.ERROR,"商品数量不正确");
						nullAlert.setHeaderText(null);
						nullAlert.showAndWait();
						return;
					}
					list.put(gv,Integer.parseInt(g.getNum()));
				} catch (RemoteException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			ClientGiftStrategyVO cgs=new ClientGiftStrategyVO();
			cgs.setID(id.getText());
			cgs.setList(list);
			cgs.setLevel(Integer.parseInt(level.getText()));
			Calendar startTime=new GregorianCalendar();
			Calendar endTime=new GregorianCalendar();
			try {
				startTime.setTime(new SimpleDateFormat("yyyy-MM-dd").parse((start.getValue().toString())));
				endTime.setTime(new SimpleDateFormat("yyyy-MM-dd").parse((end.getValue().toString())));
			} catch (ParseException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			cgs.setStartTime(startTime);
			cgs.setEndTime(endTime);
			try {
				if(RemoteHelper.getInstance().getStrategyblService().makeStrategy(cgs)){
					ClientRunner.getLogger().record("新建促销策略 "+cgs.getID());
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
		}else if(gc==null&&cc!=null&&dc==null){
			double coupon=Double.parseDouble(cc.coupon.getText());
			ClientCouponStrategyVO ccs=new ClientCouponStrategyVO();
			ccs.setID(id.getText());
			ccs.setCoupon(coupon);
			ccs.setLevel(Integer.parseInt(level.getText()));
			Calendar startTime=new GregorianCalendar();
			Calendar endTime=new GregorianCalendar();
			try {
				startTime.setTime(new SimpleDateFormat("yyyy-MM-dd").parse((start.getValue().toString())));
				endTime.setTime(new SimpleDateFormat("yyyy-MM-dd").parse((end.getValue().toString())));
			} catch (ParseException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			ccs.setStartTime(startTime);
			ccs.setEndTime(endTime);
			try {
				if(RemoteHelper.getInstance().getStrategyblService().makeStrategy(ccs)){
					ClientRunner.getLogger().record("新建促销策略 "+ccs.getID());
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
		}else if(gc==null&&cc==null&&dc!=null){
			double discount=Double.parseDouble(dc.discount.getText());
			if(discount>=1){
				Alert nullAlert=new Alert(Alert.AlertType.ERROR,"数据不符合要求!");
				nullAlert.setHeaderText(null);
				nullAlert.showAndWait();
			}
			ClientDiscountStrategyVO cds=new ClientDiscountStrategyVO();
			cds.setID(id.getText());
			cds.setDiscount(discount);
			cds.setLevel(Integer.parseInt(level.getText()));
			Calendar startTime=new GregorianCalendar();
			Calendar endTime=new GregorianCalendar();
			try {
				startTime.setTime(new SimpleDateFormat("yyyy-MM-dd").parse((start.getValue().toString())));
				endTime.setTime(new SimpleDateFormat("yyyy-MM-dd").parse((end.getValue().toString())));
			} catch (ParseException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			cds.setStartTime(startTime);
			cds.setEndTime(endTime);
			try {
				if(RemoteHelper.getInstance().getStrategyblService().makeStrategy(cds)){
					Alert success=new Alert(Alert.AlertType.INFORMATION,"新增成功!");
					success.setHeaderText(null);
					success.showAndWait();
					ClientRunner.getLogger().record("新建促销策略 "+cds.getID());
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
		id.setText(new Numbering().number(1));
		level.setText("选择等级");
		StrategyType.setText("选择促销策略类型");
		area.getChildren().clear();
		start.setValue(null);
		end.setValue(null);
	}
	
	@FXML
	private void searchGoods(){
		if(gsc!=null)
			gsc.search();
	}
	
	@FXML
	private void add(){
		if(gsc.getSelected()!=null&&gc!=null){
			String s[]=gsc.getSelected().split("<>");
			Goods g=new Goods(s[1], s[0],0, Double.parseDouble(s[3]));
			gc.addGoods(g);
		}
	}
	
	@FXML
	private void searchClient(){
		if(csc!=null)
			csc.search();
	}
}
