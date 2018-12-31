package main.ui.clientui;

import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.businesslogicservice.clientblservice.ClientblService;

public class DeleteClientComfirmUIController {
	@FXML
	Label label;
	private Stage stage;
	ClientblService cbs;
	String id;
	
	public void onInit(String name,Stage stage,ClientblService cbs,String id){
		this.label.setText("确认删除 "+name+" ?");
		this.stage=stage;
		this.cbs=cbs;
		this.id=id;
	}
	
	public void onDelete(){
		try {
			cbs.delClient(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		this.stage.close();
		
	}
	
	public void onCancel(){
		this.stage.close();
	}
}
