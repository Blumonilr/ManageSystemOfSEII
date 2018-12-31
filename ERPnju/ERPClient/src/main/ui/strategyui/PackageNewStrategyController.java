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
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.util.ClientSearchingController;
import main.ui.util.GoodsSearchingController;
import main.vo.BargainPackageStrategyVO;
import main.vo.OutGoodsVO;

public class PackageNewStrategyController implements Initializable{

	@FXML Button confirm;
	@FXML Button cancel;
	@FXML AnchorPane area;
	@FXML Tab Goods;
	@FXML Tab Clients;
	@FXML Label id;
	@FXML DatePicker start;
	@FXML DatePicker end;
	@FXML Button search;
	@FXML Button add;
	@FXML JFXTextField total;
	
	GiftController gc=null;
	ClientSearchingController csc=null;
	GoodsSearchingController gsc=null;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		AnchorPane gift=getGiftAnchorPane();
		area.getChildren().add(gift);
		Clients.setContent(getClientAnchorPane());
		Clients.setClosable(false);
		Goods.setContent(getGoodsAnchorPane());
		Goods.setClosable(false);
		id.setText(new Numbering().number(3));
		gsc.onInit();
	}
	
	private AnchorPane getGiftAnchorPane() {
		// TODO 自动生成的方法存根
		AnchorPane ap=new AnchorPane();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(GiftController.class.getResource("GiftUi.fxml"));
		try {
			ap=loader.load();
			gc=(GiftController)loader.getController();
			gc.title.setVisible(false);
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
		if(gc!=null){
			ObservableList<Goods> l=gc.goods.getItems();
			if(l.isEmpty()){
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
			BargainPackageStrategyVO cgs=new BargainPackageStrategyVO();
			cgs.setID(id.getText());
			cgs.setGoodslist(list);
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
			cgs.setTotal(Double.parseDouble(total.getText()));
			try {
				if(RemoteHelper.getInstance().getStrategyblService().makeStrategy(cgs)){
					ClientRunner.getLogger().record("新建促销策略 "+cgs.getID());
					Alert success=new Alert(Alert.AlertType.INFORMATION,"新增成功!");
					success.setHeaderText(null);
					success.showAndWait();
					ClientRunner.getLogger().record("新建促销策略"+id.getText());
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
		}
		cancel();
	}
	
	@FXML
	public void cancel(){
		id.setText(new Numbering().number(3));
		area.getChildren().clear();
		start.setValue(null);
		end.setValue(null);
		total.setText("");
	}
	
	@FXML
	public void searchGoods(){
		if(gsc!=null)
			gsc.search();
	}
	
	@FXML
	public void add(){
		if(gsc.getSelected()!=null&&gc!=null){
			String s[]=gsc.getSelected().split("<>");
			Goods g=new Goods(s[1], s[2],0, Double.parseDouble(s[3]));
			gc.addGoods(g);
		}
	}
}
