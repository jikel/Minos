package minos.controller;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import minos.controller.requeteoverview.InfoRequeteController;
import minos.controller.requeteoverview.RequeteOverviewController;
import minos.model.bean.Dossier;
import minos.model.bean.Personne;
import minos.model.bean.Requete;
import minos.model.dao.DossierDAO;
import minos.model.dao.PersonneDAO;

public class MainController {

	@FXML
	private MenuItem nouveauDossier;
	@FXML
	PersonneOverviewController personneOverviewController;
	@FXML
	RequeteOverviewController requeteOverviewController;
	private Dossier dossier;
	private Requete requete;
	
	

	@FXML
	public void initialize() {
		personneOverviewController.setMainController(this);
		requeteOverviewController.setMainController(this);
	}
	

	@FXML
	public void ajoutNouveauDossier() {
		// ouverture d'une nouvelle fenetre
		AnchorPane nouvelleRequetePane;
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource("/minos/view/NouveauDossierDialog.fxml"));

			nouvelleRequetePane = (AnchorPane) loader.load();
			
			NouveauDossierDialogController nouveauDossierDialogController = loader.getController();
			nouveauDossierDialogController.setMainController(this);

			Scene scene = new Scene(nouvelleRequetePane);
			stage.setScene(scene);
			stage.setTitle("Ajout nouveau dossier");
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	public void ajoutPersonne() {
		// ouverture d'une nouvelle fenetre
		AnchorPane nouvelleRequetePane;
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setResources(Main.minosResourceBundle);
			loader.setLocation(MainController.class.getResource("/minos/view/AjoutPersonneDialog.fxml"));
			
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
	public void chercherDossierPersonne() {
		System.out.println("chercher dossier");
		AnchorPane nouvelleRequetePane;
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource("/minos/view/ChercherDossierPersonne.fxml"));
		
			nouvelleRequetePane = (AnchorPane) loader.load();
			ChercherDossierPersonneController chercherDossierPersonneController = loader.getController();
			chercherDossierPersonneController.setMainController(this);
			Scene scene = new Scene(nouvelleRequetePane);
			stage.setScene(scene);
			stage.setTitle("Chercher dossier");
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
		personneOverviewController.setDossier(dossier);
		requeteOverviewController.setDossier(dossier);
	}


	// ATTENTION NE PAS OUBLIER DE COMMENTER CETTE PARTIE DU CODE POUR EVITER QUE LE PROGRAMME UTILISE DES DONNEES NON FIABLES
	public void demo() {
		setDossier(new DossierDAO().find(8));
	}



	public void setRequete(Requete requete) {
		this.requete = requete;
		requeteOverviewController.setRequete(requete);
	}
	
	@FXML
	public void chercherDocument() {
		AnchorPane nouvelleRecherchePane;
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource("/minos/view/ChercherDocument.fxml"));
		
			nouvelleRecherchePane = (AnchorPane) loader.load();
			Scene scene = new Scene(nouvelleRecherchePane);
			stage.setScene(scene);
			stage.setTitle("Chercher document");
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
