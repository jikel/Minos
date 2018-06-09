package minos.controller.requeteoverview;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import minos.model.bean.Dossier;
import minos.model.bean.Requete;
import minos.model.dao.RequeteDAO;
import minos.model.service.RequeteService;

public class ModifierRequeteController implements Initializable{
	
	@FXML
	private TextField numeroAuditorat;
	@FXML
	private TextField numeroRG;
	@FXML
	private Button btnAnnuler;
	@FXML
	private Button btnModifier;

	private Dossier dossier;
	private Requete requete;
	private InfoRequeteController infoRequeteController;
	private RequeteDAO requeteDAO;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		requeteDAO = new RequeteDAO();
//		numeroAuditorat.setText(requete.getNumeroRole());;
//		numeroRG.setText(requete.getNumeroRG());
	}
	
	@FXML
	public void modifierRequete(){
		System.out.println("modifier requete");
		// PROBLEME CAR LA REQUETE EST CONSIDEREE COMME ETANT VIDE POUR INFO ESSAYER DE FUSIONNER LES METHODES UPDATE
		
		// verifier que les TextField ne soient pas vides
		if (RequeteService.controlRole(numeroAuditorat.getText()) && RequeteService.controlRG(numeroRG.getText())){
			requeteDAO.update(requete, numeroAuditorat.getText(), numeroRG.getText());
			
			infoRequeteController.rafraichirRequete();
			Stage stage = (Stage) btnModifier.getScene().getWindow();
			stage.close();	
		}
		else{
			System.out.println("requete incorrecte: aucune modification dans la DB");
		}
	}

	@FXML
	public void annuler() {
		Stage stage = (Stage) btnAnnuler.getScene().getWindow();
		stage.close();
	}
	
	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
	}
	
	public void setRequete(Requete requete){
		this.requete = requete;
	}

	public void setInfoRequeteController(InfoRequeteController infoRequeteController) {
		this.infoRequeteController = infoRequeteController;
	}



}
