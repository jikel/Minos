package minos.controller.requeteoverview;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import minos.model.bean.Personne;
import minos.model.bean.Requete;
import minos.model.dao.PersonneDAO;
import minos.model.dao.RequeteDAO;

public class InfoRequeteController implements Initializable{
	@FXML
	private Label numeroAuditorat;
	@FXML
	private Label numeroRole;
	@FXML
	private Label dateCreation;
	@FXML
	private Label etat;
	@FXML
	private Label avocat;
	@FXML
	private Label dateJugement;
	@FXML
	private Label dispositif;
	private Requete requete;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		PersonneDAO personneDAO = new PersonneDAO();
//		Personne personne = personneDAO.find(18);
//		RequeteDAO requeteDAO = new RequeteDAO();
//		Requete requete = requeteDAO.findRequeteWithPersonne(personne);
//		numeroAuditorat.setText(requete.getNumeroRole());
//		numeroRole.setText(requete.getNumeroRG());
//		dateCreation.setText(requete.getDateEffet().toString());
		
		etat.setText("en cours");
		avocat.setText("pas d'avocat");
		dateJugement.setText("pas de jugement");
		dispositif.setText("pas de jugement");
		
	}
	
	@FXML
	public void modifAuditorat(){
		System.out.println("auditorat modifié");
	}
	
	@FXML
	public void modifRole(){
		System.out.println("role modifié");
	}
	
	@FXML
	public void modifEtat(){
		System.out.println("état modifié");
	}
	
	@FXML
	public void modifAvocat(){
		System.out.println("avocat modifié");
	}
	
	@FXML
	public void modifDateJugement(){
		System.out.println("date jugement modifiée");
	}
	
	@FXML
	public void modifDispositif(){
		System.out.println("dispositif modifié");
	}

	public void setRequete(Requete requete) {
		this.requete = requete;
		numeroAuditorat.setText(requete.getNumeroRole());
		numeroRole.setText(requete.getNumeroRG());
		dateCreation.setText(requete.getDateEffet().toString());

		//TODO: avocat (?), jugement
		
	}
}
