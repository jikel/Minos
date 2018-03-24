package minos.scripts;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;

import minos.model.bean.Adresse;
import minos.model.bean.DocumentMinos;
import minos.model.bean.Dossier;
import minos.model.bean.Personne;
import minos.model.bean.TypeDocumentMinos;
import minos.model.bean.TypePersonne;
import minos.model.dao.AdresseDAO;
import minos.model.dao.DocumentMinosDAO;
import minos.model.dao.DossierDAO;
import minos.model.dao.MinosConnection;
import minos.model.dao.PersonneDAO;

public class PopulateDB {
	
	private Connection conn;
	private AdresseDAO adresseDAO;
	private PersonneDAO personneDAO;
	private DocumentMinosDAO documentMinosDAO;
	private DossierDAO dossierDAO;
	
	public PopulateDB() {
		conn = MinosConnection.getInstance();
		adresseDAO = new AdresseDAO(conn);
		personneDAO = new PersonneDAO(conn);
		documentMinosDAO = new DocumentMinosDAO(conn);
		dossierDAO = new DossierDAO();
	}
	
	
	public static void main(String[] args) {
		PopulateDB populateDB = new PopulateDB();
//		populateDB.test1();
//		populateDB.test2();
		populateDB.test3();
	}


	private void test1() {
		Adresse adresse = new Adresse("chaussee de mons", "65", "truc", "7300", "belgique");
		adresse = adresseDAO.create(adresse);
		Personne simon = new Personne(TypePersonne.physique, "Dubois", "Simon", "12345678901", adresse);
		
		personneDAO.create(simon);
		Personne simonSA = new Personne(TypePersonne.morale, "SimonSA", adresse);
		personneDAO.create(simonSA);
	}
	
	private void test2() {
		String la_bible = "Il était une fois une petite étoile de mer\net elle en avait bu trop de thé.";
		DocumentMinos document = new DocumentMinos("la_bible", TypeDocumentMinos.jugement, la_bible.getBytes(), LocalDate.of(1200, 1, 3), LocalDateTime.now());
		document = documentMinosDAO.create(document);
		System.out.println(new String(document.getContenu()));
	}
	
	private void test3(){
		Dossier create = dossierDAO.create();
		System.out.println(create);
	}
}
