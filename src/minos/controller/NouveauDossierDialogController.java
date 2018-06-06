package minos.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import minos.model.bean.Adresse;
import minos.model.bean.AssignationTribunal;
import minos.model.bean.DocumentMinos;
import minos.model.bean.Dossier;
import minos.model.bean.Personne;
import minos.model.bean.Requete;
import minos.model.bean.RoleAdresse;
import minos.model.bean.TypeDocumentMinos;
import minos.model.bean.TypePersonne;
import minos.model.dao.AdresseDAO;
import minos.model.dao.AssignationTribunalDAO;
import minos.model.dao.DocumentMinosDAO;
import minos.model.dao.DossierDAO;
import minos.model.dao.PersonneDAO;
import minos.model.dao.RequeteDAO;
import minos.model.dao.RoleAdresseDAO;

public class NouveauDossierDialogController implements Initializable {

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
	private ComboBox<RoleAdresse> comboTribunal;
	private ObservableList<RoleAdresse> comboTribunalData = FXCollections.observableArrayList();
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
	private RoleAdresseDAO roleAdresseDAO;
	private AssignationTribunalDAO assignationTribunalDAO;
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
		roleAdresseDAO = new RoleAdresseDAO();
		assignationTribunalDAO = new AssignationTribunalDAO();

		for (RoleAdresse tribunal : roleAdresseDAO.findTribunals()) {
			comboTribunalData.add(tribunal);
		}
		comboTribunal.setCellFactory(new Callback<ListView<RoleAdresse>, ListCell<RoleAdresse>>() {

			@Override
			public ListCell<RoleAdresse> call(ListView<RoleAdresse> param) {
				return new ListCell<RoleAdresse>() {
					@Override
					protected void updateItem(RoleAdresse item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							setText(item.getNom());
						} else {
							setText(null);
						}

					}
				};
			}
		});

		comboTribunal.setItems(comboTribunalData);

	}

	@FXML
	public void browse() {
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
		if(controlNISS(personne.getNiss())){
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

			// création de la requete
			Requete requete = new Requete(dossier.getId(), personne.getId(), document.getId(), LocalDate.now(), numAudit.getText(), numRG.getText());
			requete = requeteDAO.create(requete);
			
			// créer le lien entre le tribunal compétent et le dossier juridique créé
			RoleAdresse tribunalCompetent = comboTribunal.getSelectionModel().getSelectedItem();
			AssignationTribunal assignationTribunal = new AssignationTribunal(dossier.getId(), document.getId(), LocalDate.now(), tribunalCompetent);
			assignationTribunal = assignationTribunalDAO.create(assignationTribunal);
			

			dossier = dossierDAO.find(dossier.getId()); // recharger le dossier avec les informations mises à jour

			mainController.setDossier(dossier);
			Stage stage = (Stage) btnCreer.getScene().getWindow();
			stage.close();	
		}
		
		// eventuellement, il faudrait supprimer l'adresse creee pour eviter les doublons
		else{
			System.out.println("niss incorrect");
		}
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
	
	// attention ce type de controle ne fonctionne uniquement avec les personnes physiques
	public boolean controlNISS(String niss){
		//verification nombre de chiffres
		if (niss.length()!=11){
			System.out.println("il faut absolument que le NISS comporte 11 chiffres");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("NISS incorrect");
			alert.setHeaderText("Encodage incorrect");
			alert.setContentText("Le NISS doit comporter 11 chiffres!");

			alert.showAndWait();
			return false;
		}
		else if(!niss.matches("[0-9]+")){
			System.out.println("il faut absolument que le NISS ne comporte que des chiffres");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("NISS incorrect");
			alert.setHeaderText("Encodage incorrect");
			alert.setContentText("Le NISS ne doit comporter que des chiffres!");

			alert.showAndWait();
			return false;
		}
		else if(personneDAO.findNISS(niss)!=null){
			System.out.println("le NISS existe déjà");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("NISS incorrect");
			alert.setHeaderText("Encodage incorrect");
			alert.setContentText("Le NISS existe déjà !");

			alert.showAndWait();
			return false;
		}
		else{
			System.out.println("le NISS est correct");
			return true;
		}
	}
	
}
