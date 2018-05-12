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
import minos.model.bean.DocumentMinos;
import minos.model.bean.Dossier;
import minos.model.bean.Personne;
import minos.model.bean.Requete;
import minos.model.bean.TypeDocumentMinos;
import minos.model.dao.DocumentMinosDAO;
import minos.model.dao.PersonneDAO;
import minos.model.dao.RequeteDAO;

public class NouvelleRequeteDialogController implements Initializable {

	@FXML
	private TextField numeroAuditorat;

	@FXML
	private TextField numeroRole;

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

	private Dossier dossier;

	private PersonneOverviewController personneOverviewController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		requeteDAO = new RequeteDAO();
		documentMinosDAO = new DocumentMinosDAO();
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

		requeteDAO.create(new Requete(dossier.getId(), idRequerant, document.getId(), LocalDate.now(), numeroAuditorat.getText(), numeroRole.getText()));
		
		personneOverviewController.rafraichir();
		Stage stage = (Stage) annuler.getScene().getWindow();
		stage.close();
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
