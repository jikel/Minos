package minos.scripts;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;

import minos.model.bean.Adresse;
import minos.model.bean.DocumentMinos;
import minos.model.bean.Dossier;
import minos.model.bean.Jugement;
import minos.model.bean.Personne;
import minos.model.bean.TypeDocumentMinos;
import minos.model.bean.TypePersonne;
import minos.model.dao.AdresseDAO;
import minos.model.dao.DocumentMinosDAO;
import minos.model.dao.DossierDAO;
import minos.model.dao.JugementDAO;
import minos.model.dao.MinosConnection;
import minos.model.dao.PersonneDAO;

public class PopulateDB {
	
	private Connection conn;
	private AdresseDAO adresseDAO;
	private PersonneDAO personneDAO;
	private DocumentMinosDAO documentMinosDAO;
	private DossierDAO dossierDAO;
	private JugementDAO jugementDAO;
	
	private String la_bible = "Il était une fois une petite étoile de mer\net elle en avait bu trop de thé.";
	
	public PopulateDB() {
		conn = MinosConnection.getInstance();
		adresseDAO = new AdresseDAO(conn);
		personneDAO = new PersonneDAO(conn);
		documentMinosDAO = new DocumentMinosDAO(conn);
		dossierDAO = new DossierDAO();
		jugementDAO = new JugementDAO();
	}
	
	
	public static void main(String[] args) {
		PopulateDB populateDB = new PopulateDB();
//		populateDB.test1();
//		populateDB.test2();
//		populateDB.test3();
		populateDB.test4();
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
		DocumentMinos document = new DocumentMinos("la_bible", TypeDocumentMinos.jugement, la_bible.getBytes(), LocalDateTime.now());
		document = documentMinosDAO.create(document);
		System.out.println(new String(document.getContenu()));
	}
	
	private void test3(){
		Dossier dossier = dossierDAO.create();
		System.out.println(dossier);
		DocumentMinos document = new DocumentMinos("la_bible", TypeDocumentMinos.jugement, la_bible.getBytes(), LocalDateTime.now());
		document = documentMinosDAO.create(document);
	}
	
	private void test4() {
		Dossier dossier = dossierDAO.create();
		DocumentMinos document = new DocumentMinos("la_bible", TypeDocumentMinos.jugement, la_bible.getBytes(), LocalDateTime.now());
		document = documentMinosDAO.create(document);
		Jugement jugement = new Jugement(dossier.getId(), document.getId(), LocalDate.now(), "recevable", "pas fonde");
		jugementDAO.create(jugement);
	}

}
