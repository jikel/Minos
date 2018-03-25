package minos.scripts;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;

import minos.model.bean.Adresse;
import minos.model.bean.DocumentMinos;
import minos.model.bean.Dossier;
import minos.model.bean.Jugement;
import minos.model.bean.Personne;
import minos.model.bean.RendezVous;
import minos.model.bean.Requete;
import minos.model.bean.RoleAdresse;
import minos.model.bean.TypeDocumentMinos;
import minos.model.bean.TypePersonne;
import minos.model.dao.AdresseDAO;
import minos.model.dao.DocumentMinosDAO;
import minos.model.dao.DossierDAO;
import minos.model.dao.JugementDAO;
import minos.model.dao.MinosConnection;
import minos.model.dao.PersonneDAO;
import minos.model.dao.RendezVousDAO;
import minos.model.dao.RequeteDAO;
import minos.model.dao.RoleAdresseDAO;

public class TestPopulateDB {
	
	private Connection conn;
	private AdresseDAO adresseDAO;
	private PersonneDAO personneDAO;
	private DocumentMinosDAO documentMinosDAO;
	private DossierDAO dossierDAO;
	private JugementDAO jugementDAO;
	private RequeteDAO requeteDAO;
	private RoleAdresseDAO roleAdresseDAO;
	
	private String la_bible = "Il était une fois une petite étoile de mer\net elle en avait bu trop de thé.";
	private RendezVousDAO rendezVousDAO;
	
	public TestPopulateDB() {
		conn = MinosConnection.getInstance();
		adresseDAO = new AdresseDAO(conn);
		personneDAO = new PersonneDAO(conn);
		documentMinosDAO = new DocumentMinosDAO(conn);
		dossierDAO = new DossierDAO();
		jugementDAO = new JugementDAO();
		requeteDAO = new RequeteDAO();
		roleAdresseDAO = new RoleAdresseDAO();
		rendezVousDAO = new RendezVousDAO();
	}
	
	
	public static void main(String[] args) {
		TestPopulateDB populateDB = new TestPopulateDB();
//		populateDB.testAdresseEtPersonne();
//		populateDB.testDocumentDAO();
//		populateDB.testDossierAvecDocument();
//		populateDB.testJugementDAO();
//		populateDB.testRequeteDAO();
//		populateDB.testRoleAdresse();
		populateDB.testRendezVous();
	}

	private void testAdresseEtPersonne() {
		Adresse adresse = new Adresse("chaussee de mons", "65", "truc", "7300", "belgique");
		adresse = adresseDAO.create(adresse);
		Personne simon = new Personne(TypePersonne.physique, "Dubois", "Simon", "12345678901", adresse);
		
		personneDAO.create(simon);
		Personne simonSA = new Personne(TypePersonne.morale, "SimonSA", adresse);
		personneDAO.create(simonSA);
	}
	
	private void testDocumentDAO() {
		DocumentMinos document = new DocumentMinos("la_bible", TypeDocumentMinos.jugement, la_bible.getBytes(), LocalDateTime.now());
		document = documentMinosDAO.create(document);
		System.out.println(new String(document.getContenu()));
	}
	
	private void testDossierAvecDocument(){
		Dossier dossier = dossierDAO.create();
		System.out.println(dossier);
		DocumentMinos document = new DocumentMinos("la_bible", TypeDocumentMinos.jugement, la_bible.getBytes(), LocalDateTime.now());
		document = documentMinosDAO.create(document);
	}
	
	private void testJugementDAO() {
		Dossier dossier = dossierDAO.create();
		DocumentMinos document = new DocumentMinos("la_bible", TypeDocumentMinos.jugement, la_bible.getBytes(), LocalDateTime.now());
		document = documentMinosDAO.create(document);
		
		Personne juge = new Personne(TypePersonne.physique, "hubain", "roger", null, null);
		juge = personneDAO.create(juge);
		Jugement jugement = new Jugement(dossier.getId(), document.getId(), juge.getId(), LocalDate.now(), "recevable", "pas fonde");

		jugementDAO.create(jugement);
	}
	
	private void testRequeteDAO() {
		Dossier dossier = dossierDAO.create();
		DocumentMinos document = new DocumentMinos("la_bible", TypeDocumentMinos.jugement, la_bible.getBytes(), LocalDateTime.now());
		document = documentMinosDAO.create(document);
		Adresse adresse = new Adresse("chaussee de mons", "65", "truc", "7300", "belgique");
		adresse = adresseDAO.create(adresse);
		Personne simonSA = new Personne(TypePersonne.morale, "SimonSA", adresse);
		simonSA = personneDAO.create(simonSA);
		Requete requete = new Requete(dossier.getId(), simonSA.getId(), document.getId(), LocalDate.now(), "54564/151", "RG 56+556");
		requeteDAO.create(requete);
	}
	
	private void testRoleAdresse() {
		Adresse adresse = new Adresse("chaussee de mons", "65", "truc", "7300", "belgique");
		adresse = adresseDAO.create(adresse);
		String nom = "domicile marcel";
		String niveauTribunal = null;
		RoleAdresse roleAdresseNonTribunal = new RoleAdresse(adresse, nom, niveauTribunal);
		roleAdresseNonTribunal = roleAdresseDAO.create(roleAdresseNonTribunal);
		
		nom = "Tribunal de bruxelles";
		niveauTribunal = "Tribunal du travail";
		RoleAdresse roleAdresseTribunal = new RoleAdresse(adresse, nom, niveauTribunal);
		roleAdresseTribunal = roleAdresseDAO.create(roleAdresseTribunal);	
	}
	
	private void testRendezVous() {
		Dossier dossier = dossierDAO.create();
		long dossierId = dossier.getId();

		Adresse adresse = new Adresse("chaussee de mons", "65", "truc", "7300", "belgique");
		adresse = adresseDAO.create(adresse);
		String nom = "Tribunal de bruxelles";
		String niveauTribunal = "Tribunal du travail";
		RoleAdresse roleAdresseTribunal = new RoleAdresse(adresse, nom, niveauTribunal);
		roleAdresseTribunal = roleAdresseDAO.create(roleAdresseTribunal);
		
		
		LocalDateTime dateHeure = LocalDateTime.now();
		RendezVous rendezVous = new RendezVous(dossierId, roleAdresseTribunal, dateHeure);
		rendezVous = rendezVousDAO.create(rendezVous);
	}
	

}
