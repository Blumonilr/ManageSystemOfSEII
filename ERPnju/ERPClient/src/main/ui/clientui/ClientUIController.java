package main.ui.clientui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Optional;

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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.businesslogicservice.clientblservice.ClientblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.clientui.UtilClientSearchUIController.Client;
import main.vo.ClientVO;

public class ClientUIController {
	ClientblService clientblservice=RemoteHelper.getInstance().getClientblService();
	@FXML
	JFXTextField nameInfo;
	@FXML
	JFXTextField telInfo;
	@FXML
	JFXTextField levelInfo;
	@FXML
	JFXTextField clerkIdInfo;
	@FXML
	JFXComboBox<String> typeInfo;
	
	@FXML
	AnchorPane pane;

	ObservableList<Client> clientList=FXCollections.observableArrayList();
	
	JFXTreeTableView<Client> table;
	
	
	JFXTreeTableColumn<Client,String> nameCol;
	JFXTreeTableColumn<Client,String> idCol;
	JFXTreeTableColumn<Client,String> levelCol;
	JFXTreeTableColumn<Client,String> typeCol;
	JFXTreeTableColumn<Client,String> telCol;
	JFXTreeTableColumn<Client,String> clerkCol;
	
	
	public void onSearch(){
		clientList.clear();
		System.out.println("Search");
		ArrayList<ClientVO> list=new ArrayList<ClientVO>();
		String name=this.nameInfo.getText();
		String tp=this.typeInfo.getValue();
		String clerkId=this.clerkIdInfo.getText();
		String lev=this.levelInfo.getText();
		String tel=this.telInfo.getText();
		int level=0;
		int type=0;
		
		try{
			level=Integer.parseInt(lev);
		}catch(Exception e){
			level=0;
		}
		
		if(name.equals("")){
			name=null;
		}
		if(tel.equals("")){
			tel=null;
		}
		if(clerkId.equals("")){
			clerkId=null;
		}
		
		if(tp=="全部"){
			 type=0;
		}
		else if(tp.equals("销售商")){
			type=1;
		}
		else if(tp.equals("供应商")){
			type=2;
		}
		
		System.out.println(name+" "+tel+" "+clerkId+" "+type+" "+level);
		
		try {
			list=clientblservice.searchClientbyNameTelCounterTypeLevel(name,tel ,clerkId, type, level);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(list.size());
		for(ClientVO vo:list){
			Client c=new Client(vo.getClientName(),vo.getID(),vo.getType(),vo.getPhone(),vo.getLevel()+"",vo.getDefaultUserID());
			clientList.add(c);
		}
	}
	
	public void onAdd(){
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(AddClientUIController.class.getResource("AddClientUI.fxml"));
		AnchorPane pane=new AnchorPane();
		AddClientUIController controller=null;
		try {
			pane=loader.load();
			controller =(AddClientUIController)loader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stage stage=new Stage();
		controller.onInit(stage);
		Scene scene=new Scene(pane);
		stage.setScene(scene);
		stage.showAndWait();
		
		this.onSearch();
	}
	
	
	public void onInit(){
		if(pane.getChildren().size()!=0){
			return;
		}
		
		
		ObservableList<String> typeList=FXCollections.observableArrayList();
		typeList.add("供应商");//我从他那里买
		typeList.add("销售商");//从我这里买
		typeList.add("全部");//从我这里买
		typeInfo.setItems(typeList);
		typeInfo.setValue("全部");
		
		//col
		nameCol=new JFXTreeTableColumn<>("客户名称");
		nameCol.setPrefWidth(140);
		nameCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Client,String> param) ->{
			if(nameCol.validateValue(param)) return param.getValue().getValue().name;
			else return nameCol.getComputedValue(param);
		});
		
		//col
		idCol=new JFXTreeTableColumn<>("客户ID");
		idCol.setPrefWidth(140);
		idCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Client,String> param) ->{
			if(idCol.validateValue(param)) return param.getValue().getValue().id;
			else return idCol.getComputedValue(param);
		});
		
		//col
		typeCol=new JFXTreeTableColumn<>("客户类型");
		typeCol.setPrefWidth(140);
		typeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Client,String> param) ->{
			if(typeCol.validateValue(param)) return param.getValue().getValue().type;
			else return typeCol.getComputedValue(param);
		});
		
		//col
		telCol=new JFXTreeTableColumn<>("客户电话");
		telCol.setPrefWidth(140);
		telCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Client,String> param) ->{
			if(telCol.validateValue(param)) return param.getValue().getValue().telephone;
			else return telCol.getComputedValue(param);
		});
		
		//col
		levelCol=new JFXTreeTableColumn<>("客户等级");
		levelCol.setPrefWidth(140);
		levelCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Client,String> param) ->{
			if(levelCol.validateValue(param)) return param.getValue().getValue().level;
			else return levelCol.getComputedValue(param);
		});
		
		//col
		clerkCol=new JFXTreeTableColumn<>("默认业务员");
		clerkCol.setPrefWidth(140);
		clerkCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Client,String> param) ->{
			if(clerkCol.validateValue(param)) return param.getValue().getValue().clerk;
			else return clerkCol.getComputedValue(param);
		});
		
		TreeItem<Client> root=new RecursiveTreeItem<Client>(clientList,RecursiveTreeObject::getChildren);
		table=new JFXTreeTableView<Client>(root);
		table.setMinWidth(850);
		table.setPrefHeight(520);
		table.setShowRoot(false);
		table.setEditable(true);
		table.getColumns().setAll(nameCol,idCol,typeCol,clerkCol,levelCol,telCol);
		
		pane.getChildren().add(table);
		

		//设置上下文菜单
				ContextMenu menu=new ContextMenu();
				MenuItem del=new MenuItem("删除");
				del.setOnAction((ActionEvent ae)->{
					
					//删除
					int index=table.getFocusModel().getFocusedIndex();
					if(index!=-1){
						ClientVO vo=null;
						String id=clientList.get(index).id.get();
						String name=clientList.get(index).name.get();
						try {
							vo=clientblservice.showClient(id);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						double toCollect=vo.getCollect();
						double toPay=vo.getPay();
						
						Alert alert=new Alert(AlertType.CONFIRMATION);
						alert.setContentText("确认删除 "+name+" ?"+'\n'+"该用户应收: "+toCollect+";"+'\n'+"应付: "+toPay+";");
						Optional<ButtonType> res=alert.showAndWait();
						if(res.get()==ButtonType.OK){
							boolean result=false;
							try {
								result=clientblservice.delClient(id);
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(result){
								Alert a2=new Alert(AlertType.INFORMATION);
								a2.setTitle("提示");
								a2.setContentText("删除成功");
								ClientRunner.getLogger().record("删除客户: "+name+" id: "+id);
								a2.showAndWait();
							}
							this.onSearch();
						}
					}
				});
		
		
		MenuItem modify=new MenuItem("修改");
		modify.setOnAction((ActionEvent ae)->{
			
			//修改
			int index=table.getFocusModel().getFocusedIndex();
			if(index!=-1){
				FXMLLoader loader=new FXMLLoader();
				ClientVO vo=null;
				AnchorPane pane=new AnchorPane();
				ModifyClientUIController controller=null;
				try {
					vo = clientblservice.showClient(clientList.get(index).id.get());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				loader.setLocation(ModifyClientUIController.class.getResource("ModifyClientUI.fxml"));
				try {
					pane = loader.load();
					controller=(ModifyClientUIController)loader.getController();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Stage s=new Stage();
				Scene scene=new Scene(pane);
				s.setScene(scene);
				
				controller.onInit(vo, s);
				
				s.showAndWait();
				
				this.onSearch();
				table.refresh();
			}
			
			
		});
		
		
		
		MenuItem detail=new MenuItem("详情");
		detail.setOnAction((ActionEvent ae)->{
			
			//详情
			int index=table.getFocusModel().getFocusedIndex();
			if(index!=-1){
				FXMLLoader loader=new FXMLLoader();
				ClientVO vo=null;
				AnchorPane pane=new AnchorPane();
				ClientDetailUIController controller=null;
				try {
					vo = clientblservice.showClient(clientList.get(index).id.get());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		});
		
		menu.getItems().add(modify);
	
		menu.getItems().add(detail);
		
		menu.getItems().add(del);
		
		table.setContextMenu(menu);
		
		//文本改变监听
		nameInfo.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				onSearch();
			}
			
		});
		telInfo.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				onSearch();
			}
			
		});
		levelInfo.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				onSearch();
			}
			
		});
		clerkIdInfo.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				onSearch();
			}
			
		});
	}
	
	
	class Client extends RecursiveTreeObject<Client>{
		StringProperty name;
		StringProperty id;
		StringProperty type;
		StringProperty telephone;
		StringProperty level;
		StringProperty clerk;
		
		public Client(String name, String id, String type, String telephone,String level,String clerk) {
			super();
			this.name = new SimpleStringProperty(name);
			this.id =  new SimpleStringProperty(id);
			this.type =  new SimpleStringProperty(type);
			this.telephone = new SimpleStringProperty(telephone);
			this.clerk=new SimpleStringProperty(clerk);
			this.level=new SimpleStringProperty(level);
		}
	}
}
