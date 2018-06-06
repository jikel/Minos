package minos.model.service;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import minos.model.dao.PersonneDAO;

public class PersonneService {
	
	// attention ce type de controle ne fonctionne uniquement avec les personnes physiques
		public static boolean controlNISS(String niss){
			PersonneDAO personneDAO = new PersonneDAO();
			//verification nombre de chiffres
			if (niss.length()!=11){
				System.out.println("il faut absolument que le NISS comporte 11 chiffres");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("NISS incorrect");
				alert.setHeaderText("Encodage incorrect");
				alert.setContentText("Le NISS doit comporter 11 chiffres!");

				alert.showAndWait();
				return false;
			}
			else if(!niss.matches("[0-9]+")){
				System.out.println("il faut absolument que le NISS ne comporte que des chiffres");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("NISS incorrect");
				alert.setHeaderText("Encodage incorrect");
				alert.setContentText("Le NISS ne doit comporter que des chiffres!");

				alert.showAndWait();
				return false;
			}
			else if(personneDAO.findPersonneWithNISS(niss)!=null){
				System.out.println("le NISS existe déjà");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("NISS incorrect");
				alert.setHeaderText("Encodage incorrect");
				alert.setContentText("Le NISS existe déjà !");

				alert.showAndWait();
				return false;
			}
			else{
				System.out.println("le NISS est correct");
				return true;
			}
		}
}
