package minos.controller.requeteoverview;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import minos.controller.MainController;
import minos.model.bean.Dossier;
import minos.model.bean.Requete;

public class RequeteOverviewController implements Initializable {

	@FXML
	private TabPane tabPane;
	@FXML
	
	private AnchorPane infoRequete;
	@FXML 
	private InfoRequeteController infoRequeteController;
	
	@FXML
	private AnchorPane correspondance;
	@FXML
	private CorrespondanceController correspondanceController;
	
	@FXML
	private AnchorPane notes;
	@FXML
	private NotesController notesController;
	
	@FXML
	private AnchorPane calendrier;
	@FXML
	private CalendrierController calendrierController;
	
	@FXML
	private AnchorPane historique;
	@FXML
	private HistoriqueController historiqueController;
	
	private MainController mainController;
	
	private Requete requete;
	private Dossier dossier;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}

	public void setRequete(Requete requete) {
		this.requete = requete;
		infoRequeteController.setRequete(requete);
		
		calendrierController.setRequete(requete);
		historiqueController.setRequete(requete);
	}
	
	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
		correspondanceController.setDossier(dossier);
		infoRequeteController.setDossier(dossier);
		notesController.setDossier(dossier);
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;

	}


}
