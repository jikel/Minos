package minos.model.service;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import minos.model.dao.RequeteDAO;

public class RequeteService {
	
	public static boolean controlRole(String role){
		RequeteDAO requeteDAO = new RequeteDAO();
		if (requeteDAO.findRequeteWithRole(role)!= null){
			System.out.println("le numero de role de la requete existe deja : pas de doublon");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Num�ro de r�le incorrect");
			alert.setHeaderText("Encodage incorrect");
			alert.setContentText("Le num�ro de r�le existe d�j� : les doublons ne sont pas permis!");
			
			alert.showAndWait();
			return false;
		}
		else{
			System.out.println("le nouveau numero de role est correct");
			return true;
		}
	}
	
	public static boolean controlRG(String rg){
		RequeteDAO requeteDAO = new RequeteDAO();
		if (requeteDAO.findRequeteWithRG(rg)!= null){
			System.out.println("le numero de RG de la requete existe deja : pas de doublon");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Num�ro de RG incorrect");
			alert.setHeaderText("Encodage incorrect");
			alert.setContentText("Le num�ro de RG existe d�j� : les doublons ne sont pas permis!");
			
			alert.showAndWait();
			return false;
		}
		else{
			System.out.println("le nouveau numero de RG est correct");
			return true;
		}	
	}

}
