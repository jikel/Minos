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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import minos.model.bean.AssignationTribunal;
import minos.model.bean.DocumentMinos;
import minos.model.bean.Dossier;
import minos.model.bean.Personne;
import minos.model.bean.Requete;
import minos.model.bean.RoleAdresse;
import minos.model.bean.TypeDocumentMinos;
import minos.model.dao.AdresseDAO;
import minos.model.dao.AssignationTribunalDAO;
import minos.model.dao.DocumentMinosDAO;
import minos.model.dao.PersonneDAO;
import minos.model.dao.RequeteDAO;
import minos.model.dao.RoleAdresseDAO;
import minos.model.service.RequeteService;

public class NouvelleRequeteDialogController implements Initializable {

	@FXML
	private TextField numeroAuditorat;

	@FXML
	private TextField numeroRole;

	@FXML
	private ComboBox<RoleAdresse> comboTribunal;
	private ObservableList<RoleAdresse> comboTribunalData = FXCollections.observableArrayList();
	
	@FXML
	private TextField chemin;

	File fichier;

	@FXML
	private Button ok;

	@FXML
	private Button btnParcourir;

	@FXML
	private Button annuler;

	private RequeteDAO requeteDAO;

	private DocumentMinosDAO documentMinosDAO;
	
	private AdresseDAO adresseDAO;
	private RoleAdresseDAO roleAdresseDAO;
	private AssignationTribunalDAO assignationTribunalDAO;

	private Dossier dossier;

	private PersonneOverviewController personneOverviewController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		requeteDAO = new RequeteDAO();
		documentMinosDAO = new DocumentMinosDAO();
		
		adresseDAO = new AdresseDAO();
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
	private void ajoutRequeteDB() {
		long idRequerant = dossier.getRequetes().iterator().next().getIdRequerant();

		byte[] contenuFichier;
		try {
			contenuFichier = Files.readAllBytes(fichier.toPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		DocumentMinos document = new DocumentMinos(fichier.getName(), TypeDocumentMinos.requeteSFP, contenuFichier, LocalDateTime.now());
		document = documentMinosDAO.create(document, dossier);

		if(RequeteService.controlRole(numeroAuditorat.getText()) && RequeteService.controlRG(numeroRole.getText())){
			Requete requete = new Requete(dossier.getId(), idRequerant, document.getId(), LocalDate.now(), numeroAuditorat.getText(), numeroRole.getText());
			requete = requeteDAO.create(requete);
//			requeteDAO.create(new Requete(dossier.getId(), idRequerant, document.getId(), LocalDate.now(), numeroAuditorat.getText(), numeroRole.getText()));
			
			// créer le lien entre le tribunal compétent et le dossier juridique créé
			RoleAdresse tribunalCompetent = comboTribunal.getSelectionModel().getSelectedItem();
			AssignationTribunal assignationTribunal = new AssignationTribunal(dossier.getId(), document.getId(), LocalDate.now(), tribunalCompetent);
			assignationTribunal = assignationTribunalDAO.create(assignationTribunal);
			
			personneOverviewController.rafraichir();
			Stage stage = (Stage) annuler.getScene().getWindow();
			stage.close();	
		}
		else{
			System.out.println("pas de creation de nouvelle requete dans la DB");
		}
		
	}

	@FXML
	private void annulerRequete() {
		System.out.println("annuler");
		Stage stage = (Stage) annuler.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void parcourir() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Sélectionner document");
		fichier = fileChooser.showOpenDialog(btnParcourir.getScene().getWindow());
		if (fichier != null) {
			chemin.setText(fichier.getAbsolutePath());
		}
	}

	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
	}
	
	public void setPersonneOverviewController(PersonneOverviewController personneOverviewController) {
		this.personneOverviewController = personneOverviewController;
		
	}

}
