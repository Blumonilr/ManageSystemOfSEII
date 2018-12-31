package main.ui.util;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InfomationUIController {
	@FXML
	Label information;
	private Stage stage;
	
	public void onInit(String info,Stage s){
		this.information.setText(info);
		this.stage=s;
	}
	
	@FXML
	public void onClick(){
		this.stage.close();
	}
	
}
