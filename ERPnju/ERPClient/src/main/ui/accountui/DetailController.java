package main.ui.accountui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DetailController{
    @FXML private Button detailOK;
    @FXML private Label nickNameLabel;
    @FXML private Label idLabel;
    @FXML private Label passwordLabel;
    @FXML private Label amountLabel;

    public void closeDetail(){
        detailOK.getScene().getWindow().hide();
    }
    public void setText(String[] list){
        nickNameLabel.setText(list[0]);
        idLabel.setText(list[1]);
        passwordLabel.setText(list[2]);
        amountLabel.setText(list[3]);
    }
}
