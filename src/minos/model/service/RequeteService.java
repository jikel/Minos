package minos.model.service;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import minos.model.bean.Requete;
import minos.model.dao.RequeteDAO;

public class RequeteService {
	
	public static boolean controlRole(String role){
		RequeteDAO requeteDAO = new RequeteDAO();
		if (requeteDAO.findRequeteWithRole(role)!= null){
			System.out.println("le numero de role de la requete existe deja : pas de doublon");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Numéro de rôle incorrect");
			alert.setHeaderText("Encodage incorrect");
			alert.setContentText("Le numéro de rôle existe déjà : les doublons ne sont pas permis!");
			
			alert.showAndWait();
			return false;
		}
		else{
			System.out.println("le nouveau numero de role est correct");
			return true;
		}
	}
	
	// cette methode permet d'ignorer les doublons pour la requete selectionnee >> utiliser dans le fxml infoRequete pour changer le numero de la requete
	public static boolean controleAuditoratRequeteActive(Requete requeteActive, String numAuditorat){
		RequeteDAO requeteDAO = new RequeteDAO();
		Requete requeteAController = requeteDAO.findRequeteWithRole(numAuditorat);
		if (requeteAController != null && (requeteAController.getId()!= requeteActive.getId())){
			System.out.println("le numero de role de la requete existe deja : pas de doublon");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Numéro de rôle incorrect");
			alert.setHeaderText("Encodage incorrect");
			alert.setContentText("Le numéro de rôle existe déjà : les doublons ne sont pas permis!");
			
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
			alert.setTitle("Numéro de RG incorrect");
			alert.setHeaderText("Encodage incorrect");
			alert.setContentText("Le numéro de RG existe déjà : les doublons ne sont pas permis!");
			
			alert.showAndWait();
			return false;
		}
		else{
			System.out.println("le nouveau numero de RG est correct");
			return true;
		}	
	}

	// cette methode permet d'ignorer les doublons pour la requete selectionnee >> utiliser dans le fxml infoRequete pour changer le numero de la requete
	public static boolean controleRGRequeteActive(Requete requeteActive, String numRG){
		RequeteDAO requeteDAO = new RequeteDAO();
		Requete requeteAController = requeteDAO.findRequeteWithRG(numRG);
		if (requeteAController != null && (requeteAController.getId()!= requeteActive.getId())){
			System.out.println("le numero de RG de la requete existe deja : pas de doublon");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Numéro de RG incorrect");
			alert.setHeaderText("Encodage incorrect");
			alert.setContentText("Le numéro de RG existe déjà : les doublons ne sont pas permis!");
			
			alert.showAndWait();
			return false;
		}
		else{
			System.out.println("le nouveau numero de RG est correct");
			return true;
		}
	}
}
