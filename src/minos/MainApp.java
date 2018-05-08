package minos;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private AnchorPane testLayout;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Minos");
		
		initRootLayout();
		
		showPersonneOverview();
	}
	
	public void initRootLayout(){
		  try {
	            // Load root layout from fxml file.
	            FXMLLoader loader = new FXMLLoader();
	            // ajout d'un ressources bundle pour les tag des versions multilangues
	            
	          //  loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
	            loader.setLocation(MainApp.class.getResource("view/TestLayout.fxml"));
	            
	          //  rootLayout = (BorderPane) loader.load();
	            testLayout = (AnchorPane) loader.load();

	            // Show the scene containing the root layout.
	           // Scene scene = new Scene(rootLayout);
	            Scene scene = new Scene(testLayout);
	            primaryStage.setScene(scene);
	            primaryStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
	
	public void showPersonneOverview(){
		 try {
	            // Load person overview.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/PersonneOverview.fxml"));
	            AnchorPane personneOverview = (AnchorPane) loader.load();

	            // Set person overview into the center of root layout.
	           // rootLayout.setCenter(personneOverview);
	            
	            
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }	
	}
	
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}
}