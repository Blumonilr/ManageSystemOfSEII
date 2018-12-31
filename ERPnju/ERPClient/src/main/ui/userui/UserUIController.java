package main.ui.userui;

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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.businesslogicservice.userblservice.UserblService;
import main.rmi.RemoteHelper;
import main.runner.ClientRunner;
import main.ui.mainui.LoginUIController;
import main.vo.UserVO;

public class UserUIController {
	UserblService userblservice=RemoteHelper.getInstance().getUserblService();
	@FXML
	JFXTextField id;
	@FXML
	JFXComboBox<String> typeInfo;
	
	LoginUIController mainController;
	
	public void setMainController(LoginUIController c){
		mainController=c;
	}
	
	@FXML
	AnchorPane pane;

	ObservableList<User> userList=FXCollections.observableArrayList();
	
	JFXTreeTableView<User> table;
	
	JFXTreeTableColumn<User,String> idCol;
	JFXTreeTableColumn<User,String> typeCol;
	
	public void onInit(){
		if(pane.getChildren().size()!=0){
			return;
		}
		
		//选择框
		ObservableList<String> typeList=FXCollections.observableArrayList();
		typeList.add("全部");
		typeList.add("库存管理人员");
		typeList.add("进货销售人员");
		typeList.add("财务人员");
		typeList.add("总经理");
		typeInfo.setItems(typeList);
		typeInfo.setValue("全部");
		
		
		//col
		idCol=new JFXTreeTableColumn<>("用户ID");
		idCol.setPrefWidth(420);
		idCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<User,String> param) ->{
			if(idCol.validateValue(param)) return param.getValue().getValue().id;
			else return idCol.getComputedValue(param);
		});
		
		//col
		typeCol=new JFXTreeTableColumn<>("用户类型");
		typeCol.setPrefWidth(420);
		typeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<User,String> param) ->{
			if(typeCol.validateValue(param)) return param.getValue().getValue().type;
			else return typeCol.getComputedValue(param);
		});
		
		TreeItem<User> root=new RecursiveTreeItem<User>(userList,RecursiveTreeObject::getChildren);
		table=new JFXTreeTableView<User>(root);
		table.setMinWidth(840);
		table.setPrefHeight(610);
		table.setShowRoot(false);
		table.setEditable(true);
		table.getColumns().setAll(typeCol,idCol);
		
		pane.getChildren().add(table);
		
		//上下文菜单
		ContextMenu menu=new ContextMenu();
		
		
		MenuItem del=new MenuItem("删除");
		del.setOnAction((ActionEvent ae)->{
			
			//删除
			int index=table.getFocusModel().getFocusedIndex();
			if(index!=-1){
				String id=userList.get(index).id.get();
				Alert alert=new Alert(AlertType.CONFIRMATION);
				alert.setContentText("确认删除 "+id+" ?");
				Optional<ButtonType> res=alert.showAndWait();
				if(res.get()==ButtonType.OK){
					boolean result=false;
					try {
						result=userblservice.removeUser(id);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(result){
						Alert a2=new Alert(AlertType.INFORMATION);
						a2.setTitle("提示");
						a2.setContentText("删除成功");
						ClientRunner.getLogger().record("删除用户  id: "+id);
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
				String id=userList.get(index).id.get();
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(getClass().getResource("/src/main/ui/userui/EditUserUI.fxml"));
				AnchorPane pane=null;
				try {
					UserVO vo=userblservice.showUser(id);
					pane=loader.load();
					EditUserUIController controller=(EditUserUIController)loader.getController();
					Stage stage=new Stage();
					Scene scene=new Scene(pane);
					stage.setScene(scene);
					controller.onInit(stage,vo.getId(),vo.getPassword(),vo.getType(),vo.getPower());
					
					stage.show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		MenuItem detail=new MenuItem("详情");
		detail.setOnAction((ActionEvent ae)->{
			
			//详情
			int index=table.getFocusModel().getFocusedIndex();
			if(index!=-1){
				String id=userList.get(index).id.get();
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(getClass().getResource("/src/main/ui/userui/UserDetailUI.fxml"));
				AnchorPane pane=null;
				try {
					UserVO vo=userblservice.showUser(id);
					pane=loader.load();
					UserDetailUIController controller=(UserDetailUIController)loader.getController();
					Stage stage=new Stage();
					Scene scene=new Scene(pane);
					stage.setScene(scene);
					controller.onInit(stage,vo.getId(),vo.getPassword(),vo.getType(),vo.getPower());
					
					stage.show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menu.getItems().add(detail);
		menu.getItems().add(modify);
		menu.getItems().add(del);
		table.setContextMenu(menu);
		
		id.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				onSearch();
			}
			
		});
	}
	
	@FXML
	public void onAdd(){
		
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("/src/main/ui/userui/AddUserUI.fxml"));
		AnchorPane pane=null;
		try {
			pane=loader.load();
			AddUserUIController c=(AddUserUIController)loader.getController();
			Stage s=new Stage();
			Scene scene=new Scene(pane);
			s.setScene(scene);
			c.onInit(s, "", "",1, 1);
			s.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		this.onSearch();
	}
	
	@FXML
	public void onLogout(){
		boolean res=false;
		try {
			res=RemoteHelper.getInstance().getUserblService().userlogout(ClientRunner.getUser());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(res){
			ClientRunner.setUser(null);
			System.out.println("log out,current user = "+ClientRunner.getUser());
			mainController.reset();
		}
	}
	
	@FXML
	public void onSearch(){
		this.userList.clear();
		
		String idinfo=this.id.getText();
		
		ArrayList<UserVO> list=new ArrayList<UserVO>();
		try {
			list=userblservice.showUsers(idinfo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String tp=this.typeInfo.getValue();
		int filter=-1;
		if(tp.equals("库存管理人员")){
			filter=1;
		}
		else if(tp.equals("进货销售人员")){
			filter=2;
		}
		else if(tp.equals("财务人员")){
			filter=3;
		}
		else if(tp.equals("总经理")){
			filter=4;
		}
		
		
		for(UserVO vo:list){
			if(filter==-1&&vo.getType()!=0){
				String t=" ";
				if(vo.getType()==2){
					t="进货销售人员";
				}
				else if(vo.getType()==1){
					t="库存管理人员";
				}
				else if(vo.getType()==3){
					t="财务人员";
				}
				else if(vo.getType()==4){
					t="总经理";
				}
				User u=new User(vo.getId(),t,vo.getPower()+"",vo.getPassword());
				
				userList.add(u);
			}
			else{
				if(vo.getType()==filter){
					String t=" ";
					if(vo.getType()==2){
						t="进货销售人员";
					}
					else if(vo.getType()==1){
						t="库存管理人员";
					}
					else if(vo.getType()==3){
						t="财务人员";
					}
					else if(vo.getType()==4){
						t="总经理";
					}
					User u=new User(vo.getId(),t,vo.getPower()+"",vo.getPassword());
					
					userList.add(u);
				}
			}
		}
	}
	
	@FXML
	public void modifyPW(){
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("/src/main/ui/userui/ModifyPWUI.fxml"));
		AnchorPane pane=null;
		try {
			pane=loader.load();
			ModifyPWUIController c=(ModifyPWUIController)loader.getController();
			Stage s=new Stage();
			Scene scene=new Scene(pane);
			s.setScene(scene);
			c.setStage(s);
			s.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	class User extends RecursiveTreeObject<User>{
		StringProperty id;
		StringProperty type;
		StringProperty power;
		StringProperty pw;

		public User(String id, String type, String power,String pw) {
			super();
			this.id =  new SimpleStringProperty(id);
			this.type =  new SimpleStringProperty(type);
			this.power=new SimpleStringProperty(power);
			this.pw =  new SimpleStringProperty(pw);
		}
	}

}
