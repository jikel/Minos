package minos.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

	@FXML
	private MenuItem nouveauDossier;
	@FXML
	PersonneOverviewController personneOverviewController;
	@FXML
	InfoRequeteController infoRequeteController;

	@FXML
	public void initialize() {
		System.out.println("application started");
	}

	@FXML
	public void ajoutNouveauDossier() {
		// ouverture d'une nouvelle fenetre
		AnchorPane nouvelleRequetePane;
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource("../view/NouveauDossierDialog.fxml"));

			nouvelleRequetePane = (AnchorPane) loader.load();

			Scene scene = new Scene(nouvelleRequetePane);
			stage.setScene(scene);
			stage.setTitle("Ajout nouveau dossier");
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void quitterMinos() {
		System.exit(0);
	}
	
	@FXML
	public void chercherDossierPersonne(){
		System.out.println("chercher dossier");
		AnchorPane nouvelleRequetePane;
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource("../view/ChercherDossierPersonne.fxml"));

			nouvelleRequetePane = (AnchorPane) loader.load();

			Scene scene = new Scene(nouvelleRequetePane);
			stage.setScene(scene);
			stage.setTitle("Chercher dossier");
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
