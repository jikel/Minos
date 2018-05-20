package application;
	
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import minos.controller.MainController;
import minos.recherche.MinosIndex;


public class Main extends Application {
	
	public static ResourceBundle minosResourceBundle;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
//			minosResourceBundle = ResourceBundle.getBundle("minos", new Locale("fr", "BE"));

            minosResourceBundle = ResourceBundle.getBundle("minos", new Locale("nl", "BE"));
			
			AnchorPane root = new AnchorPane();
			
			  // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            // ajout d'un ressources bundle pour les tag des versions multilangues
            
            loader.setResources(minosResourceBundle);
            
//            loader.setLocation(MainApp.class.getResource("/minos/view/RootLayout.fxml"));
            loader.setLocation(Main.class.getResource("/minos/view/Main.fxml"));
            root = (AnchorPane) loader.load();
            
           MainController mainController = loader.getController();
           
           // ATTENTION
//            mainController.demo();
            
            MinosIndex.getInstance().chargerIndexLucene();
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Minos");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
