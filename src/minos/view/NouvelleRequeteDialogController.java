package minos.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NouvelleRequeteDialogController implements Initializable {

	@FXML
	private TextField numeroAuditorat;

	@FXML
	private TextField numeroRole;

	@FXML
	private TextField serviceBureau;
	
	@FXML
	private Button ok;
	
	@FXML
	private Button annuler;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@FXML
	private boolean ajoutRequeteDB() {
		System.out.println("ajout requete");
		if (numeroAuditorat != null) {
			System.out.println(numeroAuditorat.getText());
			System.out.println(numeroRole.getText());
			System.out.println(serviceBureau.getText());
		} else
			System.out.println("null");
		return true;
	}

	@FXML
	private void annulerRequete() {
		System.out.println("annuler");
		Stage stage = (Stage) annuler.getScene().getWindow();
		stage.close();
	}

	

}
