package minos.controller.requeteoverview;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import minos.model.bean.Requete;

public class CalendrierController implements Initializable {

	@FXML
	private DatePicker dpAjoutRdv;
	private Requete requete;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	
	@FXML
	public void ajoutRdv(){
		System.out.println("ajout RDV");
	}

	public void setRequete(Requete requete) {
		this.requete = requete;
	}

}
