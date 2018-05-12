package minos.controller.requeteoverview;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import minos.model.bean.DocumentMinos;
import minos.model.bean.Dossier;
import minos.model.bean.Jugement;
import minos.model.bean.Personne;
import minos.model.bean.TypeDocumentMinos;
import minos.model.dao.DocumentMinosDAO;
import minos.model.dao.DossierDAO;
import minos.model.dao.JugementDAO;
import minos.model.dao.PersonneDAO;

public class AjoutJugementDialogController implements Initializable {

	@FXML
	private DatePicker dateJugementPicker;

	@FXML
	private ComboBox<String> comboRecevable;
	
	private ObservableList<String> comboRecevableData = FXCollections.observableArrayList();
	
	@FXML
	private ComboBox<String> comboFondement;
	
	private ObservableList<String> comboFondementData = FXCollections.observableArrayList();
	
	@FXML
	private ComboBox<Personne> comboJuge;
	
	private ObservableList<Personne> comboJugeData = FXCollections.observableArrayList();
	
	private File fichier;
	
	@FXML
	private TextField chemin;
	
	@FXML
	private Button btnParcourir;
	
	@FXML
	private Button btnAjouter;
	
	@FXML
	private Button btnAnnuler;
	
	private PersonneDAO personneDAO;
	private JugementDAO jugementDAO;

	private DocumentMinosDAO documentMinosDAO;

	private Dossier dossier;

	private InfoRequeteController infoRequeteController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		personneDAO = new PersonneDAO();
		jugementDAO = new JugementDAO();
		documentMinosDAO = new DocumentMinosDAO();

		comboRecevableData.add("recevable");
		comboRecevableData.add("non recevable");
		comboRecevable.setItems(comboRecevableData);
		
		comboFondementData.add("fondé");
		comboFondementData.add("partiellement fondé");
		comboFondementData.add("non fondé");
		comboFondement.setItems(comboFondementData);
		
		for (Personne juge : personneDAO.findJuges()) {
			comboJugeData.add(juge);
		}
		comboJuge.setCellFactory(new Callback<ListView<Personne>, ListCell<Personne>>() {
			@Override
			public ListCell<Personne> call(ListView<Personne> param) {
				return new ListCell<Personne>(){
					@Override
					protected void updateItem(Personne item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							setText(item.getNom() + " " + item.getPrenom());
						} else {
							setText(null);
						}
					}
				};
			}
		});
		comboJuge.setItems(comboJugeData);
	}
	
	@FXML
	public void annuler() {
		Stage stage = (Stage) btnAnnuler.getScene().getWindow();
		stage.close();
	}
	
	
	@FXML
	public void ajouter() {
		DocumentMinos document;
		try {
			//TODO: vérifier existence fichier (sinon crash) --> WarningDialog
			document = new DocumentMinos(fichier.getName(), TypeDocumentMinos.jugement, Files.readAllBytes(fichier.toPath()), LocalDateTime.now());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		document = documentMinosDAO.create(document, dossier);
		
		Personne juge = comboJuge.getSelectionModel().getSelectedItem();
		String recevable = comboRecevable.getSelectionModel().getSelectedItem();
		String fondement = comboFondement.getSelectionModel().getSelectedItem();
		
		Jugement jugement = new Jugement(dossier.getId(), document.getId(), juge.getId(), dateJugementPicker.getValue(), recevable, fondement);
		jugementDAO.create(jugement);
		infoRequeteController.rafraichir();
		Stage stage = (Stage) btnAjouter.getScene().getWindow();
		stage.close();
	}
	
	
	
	@FXML
	public void parcourir(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Sélectionner document");
		fichier = fileChooser.showOpenDialog(btnParcourir.getScene().getWindow());
		if (fichier != null) {
			chemin.setText(fichier.getAbsolutePath());
		} else {
			chemin.setText("");
		}
	}
	
	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
		
	}

	public void setInfoRequeteController(InfoRequeteController infoRequeteController) {
		this.infoRequeteController = infoRequeteController;
	}
}
