package minos.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CorrespondanceController {
	@FXML
	private Button btnAjoutDoc;
	
	@FXML
	public void ajouterDocument(){
		System.out.println("ajouter document a correspondance");
	}

}
