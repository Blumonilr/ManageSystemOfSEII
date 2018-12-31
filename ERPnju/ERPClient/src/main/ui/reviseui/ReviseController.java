package main.ui.reviseui;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

import com.sun.webkit.ContextMenuItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.financeui.detail.CashDetailController;
import main.ui.financeui.detail.CollectAndPayDetailController;
import main.ui.util.ShowReceiptUIController;
import main.vo.FinanceCashReceiptVO;
import main.vo.FinanceReceiptVO;
import main.vo.ReceiptVO;

public class ReviseController implements Initializable{

	@FXML Button ReviseButton;
	@FXML Button ModifyButton;
	@FXML TableView<Receipt> ReceiptList;
	@FXML TableColumn<Receipt,String> time;
	@FXML TableColumn<Receipt,String> id;
	@FXML TableColumn<Receipt,String> type;
	@FXML MenuItem modify;
	
	ObservableList<Receipt> list=FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO 自动生成的方法存根
		intiList();
		ReceiptList.setOnMouseClicked((MouseEvent me)->{
			if(me.getButton().equals(MouseButton.PRIMARY)&&me.getClickCount()==2){
				Receipt vo=ReceiptList.getSelectionModel().getSelectedItem();
				if(vo!=null){
					showReceipt(vo.getId());
				}
			}
		});
	}

	private void showReceipt(String id) {
		// TODO 自动生成的方法存根
		ReceiptVO vo=null;
		try {
			vo=RemoteHelper.getInstance().getReviseblService().showReceipt(id);
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		int type=vo.getReceiptType();
		if(type==31||type==32||type==41||type==42||type==11||type==12||type==13||type==14){
			ShowReceiptUIController show=new ShowReceiptUIController();
			show.showReceipt(vo);
		}else if(type==21||type==22){
			CollectAndPayDetailController cap=new CollectAndPayDetailController();
			AnchorPane pane=null;
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("financeui/detail/CollectAndPayDetail.fxml"));
			try {
				pane=loader.load();
				cap=(CollectAndPayDetailController)loader.getController();
				cap.init((FinanceReceiptVO)vo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Stage stage=new Stage();
			Scene scene=new Scene(pane);
			stage.setScene(scene);
			stage.show();
		}else if(type==23){
			CashDetailController ca=new CashDetailController();
			AnchorPane pane=null;
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("/src/main/ui/financeui/detail/CashDetail.fxml"));
			try {
				pane=loader.load();
				ca=(CashDetailController)loader.getController();
				ca.init((FinanceCashReceiptVO)vo);
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

	public void intiList() {
		list.clear();
		ArrayList<ReceiptVO> rlist;
		try {
			rlist = RemoteHelper.getInstance().getReviseblService().showAllReceipt(null);
			rlist.sort(new Comparator<ReceiptVO>(){
				@Override
				public int compare(ReceiptVO r1, ReceiptVO r2) {
					// TODO 自动生成的方法存根
					if(r1.getMakeTime().before(r2.getMakeTime()))
						return 1;
					return -1;
				}
			});
			if(!rlist.isEmpty())
				for(ReceiptVO r:rlist){
					list.add(new Receipt(r.getId(),r.getReceiptType(),r.getMakeTime()));
				}
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		time.setCellValueFactory(new PropertyValueFactory<Receipt,String>("time"));
		id.setCellValueFactory(new PropertyValueFactory<Receipt,String>("id"));
		type.setCellValueFactory(new PropertyValueFactory<Receipt,String>("type"));
		if(!list.isEmpty())
			ReceiptList.setItems(list);
		else{
			Alert alert=new Alert(Alert.AlertType.WARNING,"没有待审批单据!");
			alert.setHeaderText(null);
			alert.showAndWait();
		}
		ReceiptList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	@FXML
	public void revise(){
		if(!ReceiptList.getSelectionModel().getSelectedItems().isEmpty()){
			for(Receipt r:ReceiptList.getSelectionModel().getSelectedItems()){
				ReceiptVO re;
				try {
					re = RemoteHelper.getInstance().getReviseblService().showReceipt(r.getId());
					if(RemoteHelper.getInstance().getReviseblService().revise(re, 1)){
						list.remove(list.indexOf(r));
						ClientRunner.getLogger().record("审批单据 "+re.getId());
						Alert success=new Alert(Alert.AlertType.INFORMATION,"单据审批成功!");
						success.setHeaderText(null);
						success.showAndWait();
					}
					else{
						Alert success=new Alert(Alert.AlertType.ERROR,"单据审批失败!");
						success.setHeaderText(null);
						success.showAndWait();
						return;
					}
				} catch (RemoteException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		ReceiptList.setItems(list);
	}
	
	@FXML
	public void modifyReturn(){
		if(!ReceiptList.getSelectionModel().getSelectedItems().isEmpty()){
			for(Receipt r:ReceiptList.getSelectionModel().getSelectedItems()){
				ReceiptVO re;
				try {
					re = RemoteHelper.getInstance().getReviseblService().showReceipt(r.getId());
					if(RemoteHelper.getInstance().getReviseblService().revise(re, 2)){
						list.remove(list.indexOf(r));
						ClientRunner.getLogger().record("要求返回修改单据 "+re.getId());
						Alert success=new Alert(Alert.AlertType.INFORMATION,"单据返回成功!");
						success.setHeaderText(null);
						success.showAndWait();
					}
					else{
						Alert success=new Alert(Alert.AlertType.ERROR,"单据返回失败!");
						success.setHeaderText(null);
						success.showAndWait();
						return;
					}
				} catch (RemoteException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		ReceiptList.setItems(list);
	}
	
	@FXML
	public void modify(){
		int index=ReceiptList.getSelectionModel().getSelectedIndex();
		if(index==-1){
			Alert alert=new Alert(Alert.AlertType.ERROR,"未选定单据!");
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}else{
			Receipt r=list.get(index);
			ReceiptVO receipt=null;
			try {
				receipt=RemoteHelper.getInstance().getReviseblService().showReceipt(r.getId());
			} catch (RemoteException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			if(receipt==null){
				Alert alert=new Alert(Alert.AlertType.ERROR,"单据不存在!");
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			else if(receipt.getReceiptType()==11||receipt.getReceiptType()==12||receipt.getReceiptType()==13||receipt.getReceiptType()==14){
				Alert alert=new Alert(Alert.AlertType.WARNING,"单据无法被修改!");
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			else{
				ModifyReceiptController controller=new ModifyReceiptController();
				controller.modifyReceipt(receipt,this);
			}
		}
	}

}
