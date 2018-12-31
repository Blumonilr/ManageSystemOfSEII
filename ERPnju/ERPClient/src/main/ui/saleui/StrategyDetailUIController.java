package main.ui.saleui;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class StrategyDetailUIController {
	@FXML
	JFXTextField type;
	@FXML
	JFXTextField startTime;
	@FXML
	JFXTextField endTime;
	@FXML
	JFXTextField condition;
	@FXML
	TextArea content;
	
	public void onInit(String type,String start,String end,String condition,String content){
		this.type.setText(type);
		this.startTime.setText(start);
		this.endTime.setText(end);
		this.condition.setText(condition);
		this.content.setText(content);
	}
}
