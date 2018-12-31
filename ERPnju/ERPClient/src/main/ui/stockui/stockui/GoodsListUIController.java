package main.ui.stockui.stockui;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.rmi.RemoteHelper;
import main.vo.OutGoodsVO;

public class GoodsListUIController {

	@FXML public TableView<OutGoodsVO>table;
	
	private Stage stage;
	private StockCheckController root;
	
	public void init(Stage stage, StockCheckController stockCheckController) {
		// TODO Auto-generated method stub
		this.stage=stage;
		root=stockCheckController;
		ArrayList<OutGoodsVO>goodsList=null;
		try {
			goodsList=RemoteHelper.getInstance().getStockblService().getGoods();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TableColumn<OutGoodsVO,String>idC=new TableColumn<>("id");
		TableColumn<OutGoodsVO,String>nameC=new TableColumn<>("name");
		idC.setCellValueFactory(new PropertyValueFactory<OutGoodsVO,String>("id"));
		nameC.setCellValueFactory(new PropertyValueFactory<OutGoodsVO,String>("name"));
		table.getColumns().addAll(idC,nameC);
		table.setItems(FXCollections.observableArrayList(goodsList));
		
		table.setOnMouseClicked((MouseEvent me)->{
			if(me.getClickCount()==2&&me.getButton().equals(MouseButton.PRIMARY)){
				OutGoodsVO item=table.getSelectionModel().getSelectedItem();
				if(item!=null){
					root.goodsId.setText(item.getId());
					stage.close();
				}
			}
		});
	}

}
