package minos.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import minos.model.bean.Adresse;
import minos.model.bean.Personne;
import minos.model.bean.Requete;
import minos.model.dao.PersonneDAO;

public class PersonneOverviewController implements Initializable {
	
	private MainController main;

	// info personne
	@FXML
	private Label prenom;
	@FXML
	private Label nom;
	@FXML
	private Label niss;
	@FXML
	private Label etatCivil;
	@FXML
	private Label nationalite;
	@FXML
	private Label adresseGUI;
	
	// info requetes
	@FXML
	private TableView<Requete> requeteTable;
	@FXML
	private TableColumn<Requete, String> roleColonne;
	@FXML
	private TableColumn<Requete, String> dateColonne;
	@FXML
	private TableColumn<Requete, String> etatColonne;

	@FXML
	private Button btnTransferPersonne;
	@FXML
	public Label idPersonne;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// cherche dans la DB
		PersonneDAO personneDAO = new PersonneDAO();
		Personne personne = personneDAO.find(18);
		Adresse adresse = personne.getAdresse();

		// complete les Label
		prenom.setText(personne.getPrenom());
		nom.setText(personne.getNom());
		niss.setText(personne.getNiss());
		etatCivil.setText("Célibataire");
		nationalite.setText("Belge");

		adresseGUI.setText(adresse.getRue() + " " + adresse.getNumero() + " " + adresse.getBoite() + "\n"
				+ adresse.getCodePostal() + "\n " + adresse.getPays());
		

	}

	@FXML
	private void ajoutRequete() {
		// ouverture de la fenetre
		AnchorPane nouvelleRequetePane;
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PersonneOverviewController.class.getResource("../view/NouvelleRequeteDialog.fxml"));

			nouvelleRequetePane = (AnchorPane) loader.load();

			Scene scene = new Scene(nouvelleRequetePane);
			stage.setScene(scene);
			stage.setTitle("Ajout nouvelle requête");
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void btnTransferClicked(ActionEvent event){
		System.out.println("cliqué");
	}
	
	public void init(MainController mainController){
		main = mainController;
	}

}
