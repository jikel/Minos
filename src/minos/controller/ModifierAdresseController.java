package minos.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import minos.model.bean.Adresse;
import minos.model.bean.Dossier;
import minos.model.dao.AdresseDAO;

public class ModifierAdresseController implements Initializable{
	
	@FXML
	private TextField rue;
	@FXML
	private TextField numero;
	@FXML
	private TextField boite;
	@FXML
	private TextField codePostal;
	@FXML
	private TextField pays;
	@FXML
	private Button btnModifier;
	@FXML
	private Button btnAnnuler;

	private AdresseDAO adresseDAO;
	
	private Dossier dossier;
	private Adresse adresse;
	private PersonneOverviewController personneOverviewController;	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		adresseDAO = new AdresseDAO();
	}

	@FXML
	public void modifierAdresse(){
		System.out.println("adresse modifiee");
		
		
		adresseDAO.update(adresse, rue.getText(), numero.getText(), boite.getText(), codePostal.getText(), pays.getText());
		
		personneOverviewController.rafraichirAdressePersonne();
		Stage stage = (Stage) btnModifier.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void annuler() {
		Stage stage = (Stage) btnAnnuler.getScene().getWindow();
		stage.close();
	}
	
	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
	}
	
	public void setAdresse(Adresse adresse){
		this.adresse = adresse;
	}
	
	public void setPersonneOverviewController(PersonneOverviewController personneOverviewController) {
		this.personneOverviewController = personneOverviewController;
		
	}
}
