package minos.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import minos.MainApp;
import minos.model.bean.Adresse;
import minos.model.bean.Personne;
import minos.model.dao.AdresseDAO;
import minos.model.dao.PersonneDAO;

public class PersonneOverviewController implements Initializable {

	@FXML
	private Label prenom;

	@FXML
	private Label nom;

	@FXML
	private Label niss;

	@FXML
	private Label adresseGUI;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// cherche dans la DB
		PersonneDAO personneDAO = new PersonneDAO();
		Personne personne = personneDAO.find(1);
		AdresseDAO adresseDAO = new AdresseDAO();
		Adresse adresse = personne.getAdresse();
		
		// complete les Label
		prenom.setText(personne.getPrenom());
		nom.setText(personne.getNom());
		niss.setText(personne.getNiss());
		
		adresseGUI.setText(adresse.getRue() + " "+adresse.getNumero()+ " "+adresse.getBoite()+"\n"+adresse.getCodePostal()+ "\n"+adresse.getPays());
		
	}

	@FXML
	private void ajoutRequete() {
		// ouverture de la fenetre
		AnchorPane nouvelleRequetePane;
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/NouvelleRequeteDialog.fxml"));

			nouvelleRequetePane = (AnchorPane) loader.load();

			Scene scene = new Scene(nouvelleRequetePane);
			stage.setScene(scene);
			stage.setTitle("Ajout nouvelle requête");
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
