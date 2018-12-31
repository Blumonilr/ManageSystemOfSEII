package main.ui.util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.businesslogicservice.purchaseblservice.PurchaseblService;
import main.businesslogicservice.saleblservice.SaleblService;
import main.rmi.RemoteHelper;
import main.ui.purchaseui.ShowUnrevisedReceiptUIController;
import main.ui.purchaseui.ShowUnrevisedReturnReceiptUIController;
import main.ui.stockui.stockui.AlarmDetailController;
import main.ui.stockui.stockui.GiftDetailController;
import main.ui.stockui.stockui.OverFlowDetailController;
import main.ui.stockui.stockui.UnderFlowDetailController;
import main.vo.PurchaseReceiptVO;
import main.vo.PurchaseReturnReceiptVO;
import main.vo.ReceiptVO;
import main.vo.SaleReceiptVO;
import main.vo.SaleReturnReceiptVO;
import main.vo.StockAlarmReceiptVO;
import main.vo.StockGiftReceiptVO;
import main.vo.StockOverflowReceiptVO;
import main.vo.StockUnderflowReceiptVO;

public class ShowReceiptUIController {
	
	PurchaseblService purchaseblservice=RemoteHelper.getInstance().getPurchaseblService();
	SaleblService saleblservice=RemoteHelper.getInstance().getSaleblService();
	
	public void showReceipt(ReceiptVO receipt){
		int type=receipt.getReceiptType();
		if(type==31){
			//sale
			main.ui.saleui.ShowUnrevisedReceiptUIController controller=new main.ui.saleui.ShowUnrevisedReceiptUIController();
			AnchorPane pane=null;
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(ShowUnrevisedReceiptUIController.class.getResource("ShowUnrevisedReceiptUI.fxml"));
			
			try {
				pane=loader.load();
				controller=(main.ui.saleui.ShowUnrevisedReceiptUIController)loader.getController();
				controller.onInit();
				controller.setReceipt((SaleReceiptVO)receipt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Stage stage=new Stage();
			Scene scene=new Scene(pane);
			stage.setScene(scene);
			stage.show();
			
		}
		else if(type==32){
			//salereturn
			main.ui.saleui.ShowUnrevisedReturnReceiptUIController controller=new main.ui.saleui.ShowUnrevisedReturnReceiptUIController();
			AnchorPane pane=null;
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(ShowUnrevisedReturnReceiptUIController.class.getResource("ShowUnrevisedReturnReceiptUI.fxml"));
			
			try {
				pane=loader.load();
				controller=(main.ui.saleui.ShowUnrevisedReturnReceiptUIController)loader.getController();
				controller.onInit();
				controller.setReceipt((SaleReturnReceiptVO)receipt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Stage stage=new Stage();
			Scene scene=new Scene(pane);
			stage.setScene(scene);
			stage.show();
		}
		else if(type==41){
			//purchase
			main.ui.purchaseui.ShowUnrevisedReceiptUIController controller=new main.ui.purchaseui.ShowUnrevisedReceiptUIController();
			AnchorPane pane=null;
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(ShowUnrevisedReceiptUIController.class.getResource("ShowUnrevisedReceiptUI.fxml"));
			
			try {
				pane=loader.load();
				controller=(main.ui.purchaseui.ShowUnrevisedReceiptUIController)loader.getController();
				controller.onInit();
				controller.setReceipt((PurchaseReceiptVO)receipt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Stage stage=new Stage();
			Scene scene=new Scene(pane);
			stage.setScene(scene);
			stage.show();
		}
		else if(type==42){
			//purchasereturn 
			main.ui.purchaseui.ShowUnrevisedReturnReceiptUIController controller=new main.ui.purchaseui.ShowUnrevisedReturnReceiptUIController();
			AnchorPane pane=null;
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(main.ui.purchaseui.ShowUnrevisedReturnReceiptUIController.class.getResource("ShowUnrevisedReturnReceiptUI.fxml"));
			
			try {
				pane=loader.load();
				controller=(main.ui.purchaseui.ShowUnrevisedReturnReceiptUIController)loader.getController();
				controller.onInit();
				controller.setReceipt((PurchaseReturnReceiptVO)receipt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Stage stage=new Stage();
			Scene scene=new Scene(pane);
			stage.setScene(scene);
			stage.show();
		}
		else if(type==11){
			main.ui.stockui.stockui.OverFlowDetailController controller=new main.ui.stockui.stockui.OverFlowDetailController();
			AnchorPane pane=null;
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(OverFlowDetailController.class.getResource("OverFlowDetailUI.fxml"));
			try {
				pane=loader.load();
				controller=(OverFlowDetailController)loader.getController();
				controller.setReceipt((StockOverflowReceiptVO)receipt);
				controller.init();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Stage stage=new Stage();
			Scene scene=new Scene(pane);
			stage.setScene(scene);
			stage.show();
		}else if(type==12){
			main.ui.stockui.stockui.UnderFlowDetailController controller=new main.ui.stockui.stockui.UnderFlowDetailController();
			AnchorPane pane=null;
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(UnderFlowDetailController.class.getResource("UnderFlowDetailUI.fxml"));
			try {
				pane=loader.load();
				controller=(UnderFlowDetailController)loader.getController();
				controller.setReceipt((StockUnderflowReceiptVO)receipt);
				controller.init();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Stage stage=new Stage();
			Scene scene=new Scene(pane);
			stage.setScene(scene);
			stage.show();
		}
		else if(type==13){
			main.ui.stockui.stockui.GiftDetailController controller=new main.ui.stockui.stockui.GiftDetailController();
			AnchorPane pane=null;
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(GiftDetailController.class.getResource("GiftDetailUI.fxml"));
			try {
				pane=loader.load();
				controller=(GiftDetailController)loader.getController();
				controller.init((StockGiftReceiptVO)receipt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Stage stage=new Stage();
			Scene scene=new Scene(pane);
			stage.setScene(scene);
			stage.show();
		}else if(type==14){
			main.ui.stockui.stockui.AlarmDetailController controller=new main.ui.stockui.stockui.AlarmDetailController();
			AnchorPane pane=null;
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(AlarmDetailController.class.getResource("AlarmDetailUI.fxml"));
			try {
				pane=loader.load();
				controller=(AlarmDetailController)loader.getController();
				controller.init((StockAlarmReceiptVO)receipt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Stage stage=new Stage();
			Scene scene=new Scene(pane);
			stage.setScene(scene);
			stage.show();
		}
	}
	
}
