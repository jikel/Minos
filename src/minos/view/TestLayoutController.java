package minos.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import minos.MainApp;

public class TestLayoutController implements Initializable{
	
	@FXML
	private AnchorPane personnePane;
	@FXML
	private AnchorPane requetePane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/PersonneOverview.fxml"));

//		AnchorPane personneLayout = new AnchorPane();
		AnchorPane personneLayout = (AnchorPane) loader.load();
		
		// il est nécessaire d'avoir un FXMLLoader pour chaque vue
		FXMLLoader loaderRequete = new FXMLLoader();
		loaderRequete.setLocation(MainApp.class.getResource("view/RequeteOverview.fxml"));
		AnchorPane requeteLayout = (AnchorPane) loaderRequete.load();

		
		// ajout de PersonneOverview dans la partie gauche de TestLayout
		personnePane.getChildren().add(personneLayout);
		
		// ajout de RequeteOverview dans la partie gauche de TestLayout
		requetePane.getChildren().add(requeteLayout);
		
		} catch (IOException e){
			e.printStackTrace();
		}
	}

}
