package minos.controller.requeteoverview;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import minos.controller.MainController;
import minos.controller.NouveauDossierDialogController;
import minos.model.bean.Dossier;
import minos.model.bean.Jugement;
import minos.model.bean.Requete;
import minos.model.dao.DossierDAO;
import minos.model.service.DossierService;

public class InfoRequeteController implements Initializable {
	@FXML
	private Label numeroAuditorat;
	@FXML
	private Label numeroRG;
	@FXML
	private Label dateCreation;
	@FXML
	private Label dateJugement;
	@FXML
	private Label dispositif;

	@FXML
	private Button btnAjoutJugement;

	private Requete requete;
	private Dossier dossier;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dateJugement.setText("pas de jugement");
		dispositif.setText("pas de jugement");
	}

	@FXML
	public void modifAuditorat() {
		System.out.println("auditorat modifié");
	}

	@FXML
	public void modifRole() {
		System.out.println("role modifié");
	}

	@FXML
	public void modifEtat() {
		System.out.println("état modifié");
	}

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

		// TODO: avocat (?), 

	}

	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
		rafraichir();
	}

	@FXML
	public void ajoutJugement() {
		// ouverture d'une nouvelle fenetre
		AnchorPane nouvelleRequetePane;
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(InfoRequeteController.class.getResource("../../view/AjoutJugementDialog.fxml"));

			nouvelleRequetePane = (AnchorPane) loader.load();

			AjoutJugementDialogController ajoutJugementDialogController = loader.getController();
			ajoutJugementDialogController.setDossier(dossier);
			ajoutJugementDialogController.setInfoRequeteController(this);

			Scene scene = new Scene(nouvelleRequetePane);
			stage.setScene(scene);
			stage.setTitle("Ajout nouveau dossier");
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
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
