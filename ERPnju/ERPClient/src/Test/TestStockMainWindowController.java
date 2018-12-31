package Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TestStockMainWindowController extends Application {

	private Stage primaryStage;
	private AnchorPane window;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage p) throws Exception {
		// TODO Auto-generated method stub
		this.primaryStage=p;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(TestStockMainWindowController.class.getResource("TestStockMainWindow.fxml"));
		window=(AnchorPane)loader.load();
	//	TestStockMainWindowController controller=loader.getController();
		Scene scene=new Scene(window);
		primaryStage.setScene(scene);
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		primaryStage.setX(primaryScreenBounds.getMinX());
		primaryStage.setY(primaryScreenBounds.getMinY());
		primaryStage.setWidth(primaryScreenBounds.getWidth());
		primaryStage.setHeight(primaryScreenBounds.getHeight());
		
		primaryStage.show();
		
	}

}
