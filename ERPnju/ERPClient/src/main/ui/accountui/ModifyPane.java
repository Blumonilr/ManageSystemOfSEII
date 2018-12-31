package main.ui.accountui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ModifyPane{
    @FXML private Label idLabel;
    @FXML private TextField nickNameField;
    @FXML private TextField passwordField;
    @FXML private Label amountLabel;

    private ModifyPane c;
    public AnchorPane load(Dialog<String[]> dialog){
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ModifyPane.class.getResource("ModifyPane.fxml"));
        try {
            pane = (AnchorPane) loader.load();
            c=loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Node okButton=dialog.getDialogPane().lookupButton(dialog.getDialogPane().getButtonTypes().get(0));
        c.getNickNameField().textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });
        c.getPasswordField().textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });
        return pane;
    }

    public ModifyPane getController(){
        return c;
    }

    public String[] getText(){
        return new String[]{nickNameField.getText(),passwordField.getText()};
    }

    public void setText(String[] list){
        nickNameField.setText(list[0]);
        idLabel.setText(list[1]);
        passwordField.setText(list[2]);
        amountLabel.setText(list[3]);
    }

    public TextField getNickNameField() {
        return nickNameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }
}

