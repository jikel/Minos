package minos.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import minos.MainApp;

public class PersonneOverviewController implements Initializable{

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void ajoutRequete(){
		// ouverture de la fenetre
		AnchorPane nouvelleRequetePane;
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/NouvelleRequeteDialog.fxml"));
			
			nouvelleRequetePane = (AnchorPane) loader.load();
			
			Scene scene = new Scene(nouvelleRequetePane);
			stage.setScene(scene);
			stage.setTitle("Ajout nouvelle requête");
			stage.show();
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
