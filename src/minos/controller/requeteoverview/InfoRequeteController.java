package minos.controller.requeteoverview;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import minos.model.bean.Dossier;
import minos.model.bean.Jugement;
import minos.model.bean.Requete;
import minos.model.bean.RoleAdresse;
import minos.model.dao.DossierDAO;
import minos.model.dao.RequeteDAO;
import minos.model.service.DossierService;

public class InfoRequeteController implements Initializable {
	@FXML
	private Label numeroAuditorat;
	@FXML
	private Label numeroRG;
	@FXML
	private Label dateCreation;
	@FXML
	private Label tribunalCompetent;
	@FXML
	private Label dateJugement;
	@FXML
	private Label dispositif;

	@FXML
	private Button btnAjoutJugement;

	private Requete requete;
	private Dossier dossier;
	
	private RequeteDAO requeteDAO;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		requeteDAO = new RequeteDAO();
		dateJugement.setText("pas de jugement");
		dispositif.setText("pas de jugement");
	}

	@FXML
	public void modifAuditorat() {
		if(requete != null){
		// ouverture d'une nouvelle fenetre
				AnchorPane modifierRequetePane;
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(InfoRequeteController.class.getResource("/minos/view/ModifierRequete.fxml"));

					modifierRequetePane = (AnchorPane) loader.load();

					ModifierRequeteController modifierRequeteController = loader.getController();
					modifierRequeteController.setDossier(dossier);
					modifierRequeteController.setRequete(requete);
					modifierRequeteController.setInfoRequeteController(this);

					Scene scene = new Scene(modifierRequetePane);
					stage.setScene(scene);
					stage.setTitle("Modifier requête");
					stage.setResizable(false);
					stage.show();

				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		else{
			System.out.println("il faut selectionner une requete");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Modification impossible ");
			alert.setContentText("Il est nécessaire de sélectionner une requête !");
			
			alert.showAndWait();
		}
	}

	@FXML
	public void modifRole() {
		if(requete != null){
		// ouverture d'une nouvelle fenetre
		AnchorPane modifierRequetePane;
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(InfoRequeteController.class.getResource("/minos/view/ModifierRequete.fxml"));

			modifierRequetePane = (AnchorPane) loader.load();

			ModifierRequeteController modifierRequeteController = loader.getController();
			modifierRequeteController.setDossier(dossier);
			modifierRequeteController.setRequete(requete);
			modifierRequeteController.setInfoRequeteController(this);

			Scene scene = new Scene(modifierRequetePane);
			stage.setScene(scene);
			stage.setTitle("Modifier requête");
			stage.setResizable(false);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		};
		}
		else {
			System.out.println("il faut selectionner une requete");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Modification impossible ");
			alert.setContentText("Il est nécessaire de sélectionner une requête !");
			
			alert.showAndWait();
		}
	}

	// pas encore d'actualité
	@FXML
	public void modifAvocat() {
		System.out.println("avocat modifié");
	}

	@FXML
	public void modifDateJugement() {
		System.out.println("date jugement modifiée");
	}

	@FXML
	public void modifDispositif() {
		System.out.println("dispositif modifié");
	}

	public void setRequete(Requete requete) {
		this.requete = requete;
		numeroAuditorat.setText(requete.getNumeroRole());
		numeroRG.setText(requete.getNumeroRG());
		dateCreation.setText(requete.getDateEffet().toString());

	}

	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
		
		RoleAdresse dernierTribunal = DossierService.tribunalCourant(dossier);
		if (dernierTribunal != null){
			tribunalCompetent.setText(dernierTribunal.getNom());
		}
		else {
			tribunalCompetent.setText("pas d'assignation");
		}
		
		rafraichir();
	}

	@FXML
	public void ajoutJugement() {
		if(requete != null){
		// ouverture d'une nouvelle fenetre
		AnchorPane nouveauJugementPane;
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(InfoRequeteController.class.getResource("/minos/view/AjoutJugementDialog.fxml"));

			nouveauJugementPane = (AnchorPane) loader.load();

			AjoutJugementDialogController ajoutJugementDialogController = loader.getController();
			ajoutJugementDialogController.setDossier(dossier);
			ajoutJugementDialogController.setInfoRequeteController(this);

			Scene scene = new Scene(nouveauJugementPane);
			stage.setScene(scene);
			stage.setTitle("Ajout nouveau dossier");
			stage.setResizable(false);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		else{
			System.out.println("il faut selectionner une requete");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Ajout impossible ");
			alert.setContentText("Il est nécessaire de sélectionner une requête !");
			
			alert.showAndWait();
		}
	}
	
	public void rafraichirRequete(){
		dossier = new DossierDAO().find(dossier.getId());
		// ATTENTION j'ai cherche uniquement la derniere requete mais normalement il faudrait modifier que la requete selectionnee
		Requete derniereRequete = DossierService.derniereRequete(dossier);
		if (derniereRequete != null){
			numeroAuditorat.setText(derniereRequete.getNumeroRole());
			numeroRG.setText(derniereRequete.getNumeroRG());
		}

		
//			numeroAuditorat.setText(requete.getNumeroRole());
//			numeroRG.setText(requete.getNumeroRG());
	}

	public void rafraichir() {
		dossier = new DossierDAO().find(dossier.getId());
		Jugement jugementDernier = DossierService.dernierJugement(dossier); // attention, les dossiers ne sont pas automatiquement mis à jour
		if (jugementDernier != null) {
			dispositif.setText(jugementDernier.getRecevable() + " et " + jugementDernier.getFonde());
			dateJugement.setText(jugementDernier.getDateEffet().toString());
		} else {
			dispositif.setText("pas de jugement");
			dateJugement.setText("");
		}
	}
}
