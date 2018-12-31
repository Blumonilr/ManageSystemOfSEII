package main.ui.stockui.goodsui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import com.jfoenix.controls.JFXDatePicker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.businesslogicservice.stockblservice.StockblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.logui.LogHelper;
import main.ui.stockui.classui.ClassUIController;
import main.vo.ClassVO;
import main.vo.InGoodsVO;

public class AddGoodsController {

	@FXML public TextField goodsName;
	@FXML public TextField xh;
	@FXML public TextField className;
	@FXML public TextField classId;
	@FXML public TextField inPrice;
	@FXML public TextField outPrice;
	@FXML public TextField iniNum;
	@FXML public TextField alarmNum;
	@FXML public JFXDatePicker day;
	@FXML public AnchorPane tableRoot;
	
	private Stage stage;
	private AnchorPane rootPane;
	private TreeView<String>classTable;
	private ClassUIController cuic;
	private GoodsUIController guic;
	private StockblService goods;
	
	@FXML
	public void onOK(){
		
		String gn=goodsName.getText(),gxh=xh.getText(),
				cn=className.getText(),cid=classId.getText();
		double ip=0,op=0;
		long sn=0,an=0;
		Calendar c=null;
		boolean numberValid=true;
		
		try{
			ip=Double.parseDouble(inPrice.getText());
		}catch(NumberFormatException e){
			numberValid=false;
		}
		try{
			op=Double.parseDouble(outPrice.getText());
		}catch(NumberFormatException e){
			numberValid=false;
		}
		try{
			sn=Long.parseLong(iniNum.getText());
		}catch(NumberFormatException e){
			numberValid=false;
		}
		try{
			an=Long.parseLong(alarmNum.getText());
		}catch(NumberFormatException e){
			numberValid=false;
		}
		try{
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			c=new GregorianCalendar();
			c.setTime(df.parse(day.getEditor().getText()));
		}catch(Exception e){
			c=null;
			System.out.println("date fromat exception");
		}
		if(gn==null||gxh==null||cn==null||cid==null||c==null||!numberValid){
			//输入不完整或者不合法
			//显示警告信息
			Alert alert=new Alert(AlertType.WARNING);
			alert.setHeaderText(null);
			alert.setContentText("请检查输入的完整性与合法性！");
			alert.show();
		}else{
			boolean res=false;
			InGoodsVO g=new InGoodsVO(gn,gxh, cn, cid, ip, op, sn, an);
			g.setDay(c);
			try {
				res=goods.addGoods(g);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//显示提示信息
			if(res){
				ClientRunner.getLogger().record("新增商品"+g.getName());
				
				Alert alert=new Alert(AlertType.INFORMATION);
				alert.setTitle("提示");
				alert.setHeaderText(null);
				alert.setContentText("添加商品成功: "+g.getName());
				alert.show();
				
			}else{
				ClientRunner.getLogger().record("新增商品失败");
				
				Alert alert=new Alert(AlertType.WARNING);
				alert.setTitle("警告");
				alert.setHeaderText("添加商品失败");
				alert.setContentText("商品分类、商品名、型号相同均！");
				alert.show();
			}
			System.out.println("add goods = "+res);
			guic.showGoods();
			stage.close();
		}
	}
	
	@FXML
	public void onCancel(){
		stage.close();
	}

	public void init(Stage s,AnchorPane ap,GoodsUIController g) {
		// TODO Auto-generated method stub
		stage=s;
		rootPane=ap;
		guic=g;
		goods=RemoteHelper.getInstance().getStockblService();
		loadClassUI();
		
	}
	
	private void loadClassUI(){
		AnchorPane layout=null;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(ClassUIController.class.getResource("ClassUI.fxml"));
		try {
			layout=loader.load();
			cuic=(ClassUIController)loader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		classTable=cuic.showClass();
		tableRoot.getChildren().add(classTable);
		//卸载右键菜单
		classTable.getContextMenu().getItems().clear();
		//定义鼠标按下事件
		classTable.setOnMousePressed((MouseEvent me)->{
			TreeItem<String>ti=classTable.getSelectionModel().getSelectedItem();
			if(ti!=null){
				String id=ti.getValue();
				classId.setText(id);
				ClassVO c=null;
				try {
					cuic.updateList();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(ClassVO cv:cuic.list){
					if(cv.getId().equals(id)){
						c=cv;
						break;
					}
				}
				if(c!=null){
					className.setText(c.getName());
				}
			}
		});

	}
}
