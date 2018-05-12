package minos.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import minos.model.bean.Personne;
import minos.model.bean.TypePersonne;
import minos.model.dao.PersonneDAO;

public class AjoutPersonneDialogController implements Initializable {
	
	@FXML
	private TextField nom;
	@FXML
	private TextField prenom;
	@FXML
	private ComboBox<TypePersonne> type;
	private ObservableList<TypePersonne> typeData = FXCollections.observableArrayList();
	@FXML
	private Button btnCreer;
	@FXML
	private Button btnAnnuler;
	private PersonneDAO personneDAO;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		personneDAO = new PersonneDAO();
		typeData.add(TypePersonne.juge);
		type.setItems(typeData);
	}
	
	@FXML
	public void annuler() {
		Stage stage = (Stage) btnAnnuler.getScene().getWindow();
		stage.close();
	}
	
	
	@FXML
	public void creer() {
		Personne personne = new Personne(type.getSelectionModel().getSelectedItem(), nom.getText(), prenom.getText(), null, null);
		personne = personneDAO.create(personne);
		Stage stage = (Stage) btnCreer.getScene().getWindow();
		stage.close();
	}

}
