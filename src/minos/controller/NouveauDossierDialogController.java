package minos.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import minos.model.bean.Adresse;
import minos.model.bean.DocumentMinos;
import minos.model.bean.Dossier;
import minos.model.bean.Personne;
import minos.model.bean.Requete;
import minos.model.bean.TypeDocumentMinos;
import minos.model.bean.TypePersonne;
import minos.model.dao.AdresseDAO;
import minos.model.dao.DocumentMinosDAO;
import minos.model.dao.DossierDAO;
import minos.model.dao.PersonneDAO;
import minos.model.dao.RequeteDAO;

public class NouveauDossierDialogController implements Initializable{
	
	@FXML
	private TextField nom;
	@FXML
	private TextField prenom;
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
	private TextField niss;
	@FXML
	private TextField numAudit;
	@FXML
	private TextField numRG;
	@FXML
	private TextField chemin;
	
	@FXML
	private Button btnBrowse;
	
	@FXML
	private Button btnCreer;
	
	@FXML
	private Button btnAnnuler;

	private File file;
	private AdresseDAO adresseDAO;
	private DossierDAO dossierDAO;
	private PersonneDAO personneDAO;
	private RequeteDAO requeteDAO;
	private DocumentMinosDAO documentMinosDAO;
	private MainController mainController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dossierDAO = new DossierDAO();
		adresseDAO = new AdresseDAO();
		personneDAO = new PersonneDAO();
		documentMinosDAO = new DocumentMinosDAO();
		requeteDAO = new RequeteDAO();
	}
	
	
	@FXML
	public void browse(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Sélectionner document");
		file = fileChooser.showOpenDialog(btnBrowse.getScene().getWindow());
		if (file != null) {
			chemin.setText(file.getAbsolutePath());
		} else {
			chemin.setText("");
		}
	}
	
	@FXML
	public void annuler() {
		Stage stage = (Stage) btnAnnuler.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void creer() {
		Dossier dossier = dossierDAO.create();
		
		Adresse adresse = new Adresse(rue.getText(), numero.getText(), boite.getText(), codePostal.getText(), pays.getText());
		adresse = adresseDAO.create(adresse);
		
		Personne personne = new Personne(TypePersonne.physique, nom.getText(), prenom.getText(), niss.getText(), adresse);
		personne = personneDAO.create(personne);
		
		String nomFichier = file.getName();
		byte[] contenuFichier;
		try {
			contenuFichier = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		DocumentMinos document = new DocumentMinos(nomFichier, TypeDocumentMinos.requeteSFP, contenuFichier, LocalDateTime.now());
		document = documentMinosDAO.create(document, dossier);
		
		
		Requete requete = new Requete(dossier.getId(), personne.getId(), document.getId(), LocalDate.now(), numAudit.getText(), numRG.getText());
		requete = requeteDAO.create(requete);
		
		dossier = dossierDAO.find(dossier.getId()); // recharger le dossier avec les informations mises à jour
				
		mainController.setDossier(dossier);
		Stage stage = (Stage) btnCreer.getScene().getWindow();
		stage.close();
	}


	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

}
