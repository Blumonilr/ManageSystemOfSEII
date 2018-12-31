package main.ui.stockui.stockui;

import java.io.IOException;
import java.util.Calendar;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import main.businesslogicservice.stockblservice.StockblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.util.GoodsSearchingController;
import main.vo.StockOverflowReceiptVO;
import main.vo.StockUnderflowReceiptVO;

public class UnderflowReceiptUIController {
	GoodsSearchingController goodsController;
	StockblService bls=RemoteHelper.getInstance().getStockblService();
	/**
	 * 右侧商品查询界面
	 */
	@FXML
	AnchorPane goodsPane;
	AnchorPane p;//load时的辅助
	
	@FXML
	JFXTextField name;
	@FXML
	JFXTextField id;
	@FXML
	JFXTextField xh;
	@FXML
	JFXTextField number;
	
	public void onInit(){
		//商品查找界面
		try {
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(GoodsSearchingController.class.getResource("GoodsSearching.fxml"));
				p=(AnchorPane)loader.load();
				goodsController=loader.getController();
				goodsController.onInit();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			this.goodsPane.getChildren().add(p);
	}
	
	@FXML
	public void onSearch(){
		goodsController.search();
	}
	
	@FXML
	public void onAdd(){
		String s=goodsController.getSelected();
		String[] info=s.split("<>");
		String name=info[0];
		String id=info[1];
		String xh=info[2];
		this.name.setText(name);
		this.id.setText(id);
		this.xh.setText(xh);
	}
	
	@FXML
	public void onMake(){
		String goodsName=this.name.getText();
		String goodsId=this.id.getText();
		String goodsXh=this.xh.getText();
		String num=this.number.getText();
		int amount=0;
		try{
			amount=Integer.parseInt(num);
			
			if(goodsName.equals("")){
				Alert alert=new Alert(AlertType.WARNING);
				alert.setTitle("警告");
				alert.setHeaderText(null);
				alert.setContentText("请选择报溢商品！");
				
				alert.showAndWait();
			}else{
				//开始创建
				
				StockUnderflowReceiptVO vo=new StockUnderflowReceiptVO(null,Calendar.getInstance(),null,ClientRunner.getUser(),goodsName,goodsId,goodsXh,amount);
				boolean res=false;
				try{
					res = bls.addReceipt(vo);
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
				if(res){
					Alert a3=new Alert(AlertType.INFORMATION);
					a3.setTitle("消息");
					a3.setHeaderText(null);
					a3.setContentText("提交成功！");
					
					a3.showAndWait();
					this.onCancel();
				}
				else{
					Alert a3=new Alert(AlertType.WARNING);
					a3.setTitle("警告");
					a3.setHeaderText(null);
					a3.setContentText("提交失败！");
					
					a3.showAndWait();
				}
			}
			
		}catch(Exception e){
			Alert a2=new Alert(AlertType.WARNING);
			a2.setTitle("警告");
			a2.setHeaderText(null);
			a2.setContentText("请正确输入数量！");
			a2.showAndWait();
			this.number.setText("");
		}
		
	}
	
	@FXML
	public void onCancel(){
		this.name.setText("");
		this.id.setText("");
		this.xh.setText("");
		this.number.setText("");
	}
	
}
