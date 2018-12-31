package main.ui.strategyui;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.vo.StrategyVO;

public class ShowStrategyController implements Initializable{

	@FXML TableView<Strategy> Strategy;
	@FXML TableColumn<Strategy,String> id;
	@FXML TableColumn<Strategy,String> type;
	@FXML TableColumn<Strategy,String> intro;
	@FXML TableColumn<Strategy,String> time;
	@FXML MenuItem delete;
	@FXML JFXButton refresh;
	
	ObservableList<Strategy> list;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO 自动生成的方法存根
		list=FXCollections.observableArrayList();
		try {
			ArrayList<StrategyVO> strategys=RemoteHelper.getInstance().getStrategyblService().findAllStrategy();
			strategys.sort(new Comparator<StrategyVO>(){
				@Override
				public int compare(StrategyVO s1, StrategyVO s2) {
					// TODO 自动生成的方法存根
					if(s1.getStartTime().after(s2.getStartTime()))
						return 1;
					else
						return -1;
				}
			});
			for(StrategyVO s:strategys){
					list.add(new Strategy(s));
			}
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		id.setCellValueFactory(new PropertyValueFactory<Strategy,String>("id"));
		type.setCellValueFactory(new PropertyValueFactory<Strategy,String>("type"));
		intro.setCellValueFactory(new PropertyValueFactory<Strategy,String>("intro"));
		time.setCellValueFactory(new PropertyValueFactory<Strategy,String>("time"));
		Strategy.setItems(list);
	}
	
	@FXML
	public void refresh(){
		list.clear();
		try {
			ArrayList<StrategyVO> strategys=RemoteHelper.getInstance().getStrategyblService().findAllStrategy();
			strategys.sort(new Comparator<StrategyVO>(){
				@Override
				public int compare(StrategyVO s1, StrategyVO s2) {
					// TODO 自动生成的方法存根
					if(s1.getStartTime().after(s2.getStartTime()))
						return 1;
					else
						return -1;
				}
			});
			for(StrategyVO s:strategys){
					list.add(new Strategy(s));
			}
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Strategy.setItems(list);
	}
	
	@FXML
	public void delete(){
		Strategy str=Strategy.getSelectionModel().getSelectedItem();
		if(str==null){
			Alert nullAlert=new Alert(Alert.AlertType.ERROR,"未选中任何目标!");
			nullAlert.setHeaderText(null);
			nullAlert.showAndWait();
			return;
		}
		else{
			try {
				if(RemoteHelper.getInstance().getStrategyblService().removeStrategy(str.getId())){
					Alert nullAlert=new Alert(Alert.AlertType.INFORMATION,"删除成功!");
					nullAlert.setHeaderText(null);
					nullAlert.showAndWait();
					refresh();
				}
				else{
					Alert nullAlert=new Alert(Alert.AlertType.ERROR,"删除失败!");
					nullAlert.setHeaderText(null);
					nullAlert.showAndWait();
					return;
				}
			} catch (RemoteException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		ClientRunner.getLogger().record("删除促销策略"+str.getId());
	}
}
