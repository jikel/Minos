package minos.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

public class CalendrierController {

	@FXML
	private DatePicker dpAjoutRdv;
	
	@FXML
	public void ajoutRdv(){
		System.out.println("ajout RDV");
	}
}
