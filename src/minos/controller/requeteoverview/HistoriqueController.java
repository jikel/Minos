package minos.controller.requeteoverview;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import minos.model.bean.Requete;

public class HistoriqueController implements Initializable {

	private Requete requete;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void setRequete(Requete requete) {
		this.requete = requete;
	}


}
