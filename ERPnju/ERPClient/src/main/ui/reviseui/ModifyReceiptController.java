package main.ui.reviseui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.runner.ClientRunner;
import main.ui.financeui.edit.EditCashController;
import main.ui.financeui.edit.EditCollectAndPayController;
import main.ui.purchaseui.EditPurchaseReceiptUIController;
import main.ui.purchaseui.EditPurchaseReturnReceiptUIController;
import main.ui.reviseui.ReviseController;
import main.ui.saleui.EditSaleReceiptUIController;
import main.ui.saleui.EditSaleReturnReceiptUIController;
import main.vo.FinanceCashReceiptVO;
import main.vo.FinanceReceiptVO;
import main.vo.PurchaseReceiptVO;
import main.vo.PurchaseReturnReceiptVO;
import main.vo.ReceiptVO;
import main.vo.SaleReceiptVO;
import main.vo.SaleReturnReceiptVO;

public class ModifyReceiptController {
	
	public void modifyReceipt(ReceiptVO r,ReviseController revise){
		switch(r.getReceiptType()){
		case 21:case 22:
			ModifyCollectAndPayController cap=new ModifyCollectAndPayController();
			AnchorPane pane212=null;
			FXMLLoader loader212=new FXMLLoader();
			loader212.setLocation(ModifyCollectAndPayController.class.getResource("ModifyCollectAndPayUI.fxml"));
			try {
				pane212=loader212.load();
				cap=(ModifyCollectAndPayController)loader212.getController();
				cap.init((FinanceReceiptVO)r);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Stage stage212=new Stage();
			Scene scene212=new Scene(pane212);
			stage212.setScene(scene212);
			stage212.show();
			break;
		case 23:
			ModifyCashController cc=new ModifyCashController();
			AnchorPane pane23=null;
			FXMLLoader loader23=new FXMLLoader();
			loader23.setLocation(ModifyCashController.class.getResource("ModifyCashUI.fxml"));
			try {
				pane23=loader23.load();
				cc=(ModifyCashController)loader23.getController();
				cc.init((FinanceCashReceiptVO)r);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Stage stage23=new Stage();
			Scene scene23=new Scene(pane23);
			stage23.setScene(scene23);
			stage23.show();
			break;
		case 31:
			ModifySaleController src=new ModifySaleController();
			AnchorPane pane31=null;
			FXMLLoader loader31=new FXMLLoader();
			loader31.setLocation(ModifySaleController.class.getResource("ModifySaleReceiptUI.fxml"));
			try {
				pane31=loader31.load();
				src=(ModifySaleController)loader31.getController();
				src.onInit();
				src.setReceipt((SaleReceiptVO)r);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Stage stage31=new Stage();
			Scene scene31=new Scene(pane31);
			stage31.setScene(scene31);
			stage31.show();
			break;
		case 32:
			ModifySaleReturnController srrc=new ModifySaleReturnController();
			AnchorPane pane32=null;
			FXMLLoader loader32=new FXMLLoader();
			loader32.setLocation(ModifySaleReturnController.class.getResource("ModifySaleReturnReceiptUI.fxml"));
			try {
				pane32=loader32.load();
				srrc=(ModifySaleReturnController)loader32.getController();
				srrc.onInit();
				srrc.setReceipt((SaleReturnReceiptVO)r);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Stage stage32=new Stage();
			Scene scene32=new Scene(pane32);
			stage32.setScene(scene32);
			stage32.show();
			break;
		case 41:
			ModifyPurchaseController prc=new ModifyPurchaseController();
			AnchorPane pane41=null;
			FXMLLoader loader41=new FXMLLoader();
			loader41.setLocation(ModifyPurchaseController.class.getResource("ModifyPurchaseUI.fxml"));
			try {
				pane41=loader41.load();
				prc=(ModifyPurchaseController)loader41.getController();
				prc.onInit();
				prc.setReceipt((PurchaseReceiptVO)r);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Stage stage41=new Stage();
			Scene scene41=new Scene(pane41);
			stage41.setScene(scene41);
			stage41.show();
			break;
		case 42:
			ModifyPurchaseReturnController prrc=new ModifyPurchaseReturnController();
			AnchorPane pane42=null;
			FXMLLoader loader42=new FXMLLoader();
			loader42.setLocation(ModifyPurchaseReturnController.class.getResource("ModifyPurchaseReturnUI.fxml"));
			try {
				pane42=loader42.load();
				prrc=(ModifyPurchaseReturnController)loader42.getController();
				prrc.onInit();
				prrc.setReceipt((PurchaseReturnReceiptVO)r);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Stage stage42=new Stage();
			Scene scene42=new Scene(pane42);
			stage42.setScene(scene42);
			stage42.show();
			break;
		}
		ClientRunner.getLogger().record("修改单据 "+r.getId());
		revise.intiList();
	}
}
