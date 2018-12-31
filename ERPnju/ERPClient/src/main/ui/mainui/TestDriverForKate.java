package main.ui.mainui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TestDriverForKate extends Application {
	
	private Stage primaryStage;
	private AnchorPane window;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		this.primaryStage=primaryStage;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(TestDriverForKate.class.getResource("PurchaseSaleMainWindow.fxml"));
		window=(AnchorPane)loader.load();
		Scene scene=new Scene(window);
		this.primaryStage.setScene(scene);
		
		this.primaryStage.show();
		
	}
	


}
