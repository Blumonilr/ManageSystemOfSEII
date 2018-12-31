package main.ui.saleui;


import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GiftReceiptUIController {
	@FXML
	JFXTextField operatorId;
	@FXML
	VBox box;
	Stage stage;
	
	public void setStage(Stage s){
		stage=s;
	}
	
	public void add(String tip){
		System.out.println(tip);
		Label label=new Label(tip);
		label.setFont(Font.font(16.0));
		label.setWrapText(true);
		box.getChildren().add(label);
		Label space=new Label("");
		box.getChildren().add(space);
	}
	
	public void setUser(String userId){
		this.operatorId.setText(userId);
	}
	
	@FXML
	public void onClose(){
		this.stage.close();
	}
}
