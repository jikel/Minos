package minos.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import minos.model.bean.Dossier;
import minos.model.dao.DossierDAO;

public class ChercherDossierPersonneController implements Initializable{
	@FXML
	private TextField nom;
	@FXML
	private TextField prenom;
	@FXML
	private TextField niss;
	private MainController mainController;
	@FXML
	private Button btnChercher;
	@FXML
	private Button annuler;

	private DossierDAO dossierDAO;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dossierDAO = new DossierDAO();
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	@FXML
	public void chercherDossier(){
		System.out.println("chercher dossier");
		Dossier dossier = dossierDAO.find(Long.parseLong(nom.getText()));
		mainController.setDossier(dossier);
		Stage stage = (Stage) btnChercher.getScene().getWindow();
		stage.close();
	}
}
