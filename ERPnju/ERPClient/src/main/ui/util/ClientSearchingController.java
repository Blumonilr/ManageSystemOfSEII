package main.ui.util;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.businesslogicservice.clientblservice.ClientblService;
import main.rmi.RemoteHelper;
import main.ui.clientui.ClientDetailUIController;
import main.vo.ClientVO;

public class ClientSearchingController implements Initializable{
	ClientblService clientblservice=RemoteHelper.getInstance().getClientblService();
	@FXML
	JFXTextField clientName;
	@FXML
	JFXComboBox<String> clientType;
	
	@FXML
	AnchorPane pane;

	ObservableList<Client> clientList=FXCollections.observableArrayList();
	JFXTreeTableView<Client> table;
	
	
	JFXTreeTableColumn<Client,String> nameCol;
	JFXTreeTableColumn<Client,String> idCol;
	JFXTreeTableColumn<Client,String> typeCol;
	JFXTreeTableColumn<Client,String> telCol;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO 自动生成的方法存根
		clientName.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				search();
			}
			
		});
		
		
		
		if(pane.getChildren().size()!=0){
			return;
		}
		
		ObservableList<String> typeList=FXCollections.observableArrayList();
		typeList.add("供应商");//我从他那里买
		typeList.add("销售商");//从我这里买
		clientType.setItems(typeList);
		
		//col
		nameCol=new JFXTreeTableColumn<>("客户名称");
		nameCol.setPrefWidth(88);
		nameCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Client,String> param) ->{
			if(nameCol.validateValue(param)) return param.getValue().getValue().name;
			else return nameCol.getComputedValue(param);
		});
		
		//col
		idCol=new JFXTreeTableColumn<>("客户ID");
		idCol.setPrefWidth(87);
		idCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Client,String> param) ->{
			if(idCol.validateValue(param)) return param.getValue().getValue().id;
			else return idCol.getComputedValue(param);
		});
		
		//col
		typeCol=new JFXTreeTableColumn<>("客户类型");
		typeCol.setPrefWidth(88);
		typeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Client,String> param) ->{
			if(typeCol.validateValue(param)) return param.getValue().getValue().type;
			else return typeCol.getComputedValue(param);
		});
		
		//col
		telCol=new JFXTreeTableColumn<>("客户电话");
		telCol.setPrefWidth(87);
		telCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Client,String> param) ->{
			if(telCol.validateValue(param)) return param.getValue().getValue().telephone;
			else return telCol.getComputedValue(param);
		});
		
		TreeItem<Client> root=new RecursiveTreeItem<Client>(clientList,RecursiveTreeObject::getChildren);
		table=new JFXTreeTableView<Client>(root);
		table.setMinWidth(350);
		table.setPrefHeight(444);
		table.setShowRoot(false);
		table.setEditable(true);
		table.getColumns().setAll(nameCol,idCol,typeCol,telCol);
		
		table.setOnMouseClicked((MouseEvent me)->{
			if(me.getButton().equals(MouseButton.PRIMARY)&&me.getClickCount()==2){
				int index=table.getFocusModel().getFocusedIndex();
				if(index!=-1){
					ClientVO vo=null;
					try {
						vo=clientblservice.showClient(clientList.get(index).id.get());
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					FXMLLoader loader=new FXMLLoader();
				
					AnchorPane pane=new AnchorPane();
					ClientDetailUIController controller=null;
					loader.setLocation(ClientDetailUIController.class.getResource("ClientDetailUI.fxml"));
					try {
						pane = loader.load();
						controller=(ClientDetailUIController)loader.getController();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Stage s=new Stage();
					Scene scene=new Scene(pane);
					s.setScene(scene);
					
					controller.onInit(vo, s);
					
					s.showAndWait();
					
				}
			}
		});
		pane.getChildren().add(table);
		
		this.search();
	}
	
	/**
	 * 
	 * @return 返回选中 名称<>id<>类型<>等级 
	 */
	public String getSelected(){
		int index=table.getSelectionModel().getFocusedIndex();
		if(index==-1){
			return null;
		}
		else{
			Client c=clientList.get(index);
			return c.name.get()+"<>"+c.id.get()+"<>"+c.type.get()+"<>"+c.level.get();
		}
	}
	
	public void search(){
		clientList.clear();
		table.refresh();
		ArrayList<ClientVO> list=new ArrayList<ClientVO>();
		int type=0;
		String name=null;
		String nameInfo=clientName.getText();
		if(!nameInfo.equals("")){
			name=nameInfo;
		}
		String typeInfo=clientType.getValue();
		if(typeInfo==null){
			 type=0;
		}
		else if(typeInfo.equals("销售商")){
			type=1;
		}
		else if(typeInfo.equals("供应商")){
			type=2;
		}
		
		try {
			list=clientblservice.searchClientbyNameTelCounterTypeLevel(name,null ,null, type, 0);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(ClientVO vo:list){
			Client c=new Client(vo.getClientName(),vo.getID(),vo.getType(),vo.getPhone(),vo.getLevel()+"");
			clientList.add(c);
		}
	}
	
	class Client extends RecursiveTreeObject<Client>{
		StringProperty name;
		StringProperty id;
		StringProperty type;
		StringProperty telephone;
		StringProperty level;
		
		public Client(String name, String id, String type, String telephone,String level) {
			super();
			this.name = new SimpleStringProperty(name);
			this.id =  new SimpleStringProperty(id);
			this.type =  new SimpleStringProperty(type);
			this.telephone = new SimpleStringProperty(telephone);
			this.level =  new SimpleStringProperty(level);
		}
	}
}
