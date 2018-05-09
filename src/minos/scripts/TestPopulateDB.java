package minos.scripts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import minos.model.bean.Adresse;
import minos.model.bean.AssignationTribunal;
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
import minos.model.dao.AssignationTribunalDAO;
import minos.model.dao.DocumentMinosDAO;
import minos.model.dao.DossierDAO;
import minos.model.dao.JugementDAO;
import minos.model.dao.PersonneDAO;
import minos.model.dao.RendezVousDAO;
import minos.model.dao.RequeteDAO;
import minos.model.dao.RoleAdresseDAO;

public class TestPopulateDB {

	private AdresseDAO adresseDAO;
	private PersonneDAO personneDAO;
	private DocumentMinosDAO documentMinosDAO;
	private DossierDAO dossierDAO;
	private JugementDAO jugementDAO;
	private RequeteDAO requeteDAO;
	private RoleAdresseDAO roleAdresseDAO;
	private RendezVousDAO rendezVousDAO;
	private AssignationTribunalDAO assignationTribunalDAO;

	private String la_bible = "Il était une fois une petite étoile de mer\net elle en avait bu trop de thé.";

	public TestPopulateDB() {
		adresseDAO = new AdresseDAO();
		personneDAO = new PersonneDAO();
		documentMinosDAO = new DocumentMinosDAO();
		dossierDAO = new DossierDAO();
		jugementDAO = new JugementDAO();
		requeteDAO = new RequeteDAO();
		roleAdresseDAO = new RoleAdresseDAO();
		rendezVousDAO = new RendezVousDAO();
		assignationTribunalDAO = new AssignationTribunalDAO();
	}

	public static void main(String[] args) {
		TestPopulateDB populateDB = new TestPopulateDB();
//		populateDB.testAdresseEtPersonne();
		// populateDB.testDocumentDAO();
		// populateDB.testJugementDAO();
		// populateDB.testRequeteDAO();
		// populateDB.testRoleAdresse();
		// populateDB.testRendezVous();
		// populateDB.testDossierAvecDocument();
		// populateDB.testDossierAvecJugements();
		 populateDB.testDossierAvecRequete();
		// populateDB.testAssignationTribunal();
		// populateDB.testDossierAvecAssignationsTribunal();
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
		DocumentMinos document = new DocumentMinos("la_bible", TypeDocumentMinos.jugement, la_bible.getBytes(),
				LocalDateTime.now());
		document = documentMinosDAO.create(document);
		System.out.println(new String(document.getContenu()));
	}

	private void testJugementDAO() {
		Dossier dossier = dossierDAO.create();
		DocumentMinos document = new DocumentMinos("la_bible", TypeDocumentMinos.jugement, la_bible.getBytes(),
				LocalDateTime.now());
		document = documentMinosDAO.create(document);

		Personne juge = new Personne(TypePersonne.physique, "hubain", "roger", null, null);
		juge = personneDAO.create(juge);
		Jugement jugement = new Jugement(dossier.getId(), document.getId(), juge.getId(), LocalDate.now(), "recevable",
				"pas fonde");

		jugement = jugementDAO.create(jugement);
	}

	private void testRequeteDAO() {
		Dossier dossier = dossierDAO.create();
		DocumentMinos document = new DocumentMinos("la_bible", TypeDocumentMinos.jugement, la_bible.getBytes(),
				LocalDateTime.now());
		document = documentMinosDAO.create(document);
		Adresse adresse = new Adresse("chaussee de mons", "65", "truc", "7300", "belgique");
		adresse = adresseDAO.create(adresse);
		Personne simonSA = new Personne(TypePersonne.morale, "SimonSA", adresse);
		simonSA = personneDAO.create(simonSA);
		Requete requete = new Requete(dossier.getId(), simonSA.getId(), document.getId(), LocalDate.now(), "54564/151",
				"RG 56+556");
		requete = requeteDAO.create(requete);
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

	private void testDossierAvecDocument() {
		Dossier dossier = dossierDAO.create();

		Collection<String> nomsDocument = dossier.getNomsDocument().values();
		System.out.println("Documents présents : ");
		for (String nomDocument : nomsDocument) {
			System.out.println(nomDocument);
		}
		System.out.println(" -- ");

		DocumentMinos bibleDocument = new DocumentMinos("la_bible", TypeDocumentMinos.jugement, la_bible.getBytes(),
				LocalDateTime.now());
		bibleDocument = documentMinosDAO.create(bibleDocument);
		dossier.getNomsDocument().put(bibleDocument.getId(), bibleDocument.getNom());

		DocumentMinos journal = new DocumentMinos("journal", TypeDocumentMinos.pieceInventaire,
				"contenu journal".getBytes(), LocalDateTime.now());
		journal = documentMinosDAO.create(journal);
		dossier.getNomsDocument().put(journal.getId(), journal.getNom());

		dossier = dossierDAO.updateAll(dossier);

		nomsDocument = dossier.getNomsDocument().values();
		System.out.println("Documents présents : ");
		for (String nomDocument : nomsDocument) {
			System.out.println(nomDocument);
		}
		System.out.println(" -- ");
	}

	private void testDossierAvecJugements() {
		Dossier dossier = dossierDAO.create();

		Collection<Jugement> jugements = dossier.getJugements();
		System.out.println("collection jugement vide :");
		for (Jugement jugement : jugements) {
			System.out.println(jugement.getId());
		}
		System.out.println(" -- ");

		DocumentMinos document = new DocumentMinos("la_bible", TypeDocumentMinos.jugement, la_bible.getBytes(),
				LocalDateTime.now());
		document = documentMinosDAO.create(document);

		Personne juge = new Personne(TypePersonne.physique, "hubain", "roger", null, null);
		juge = personneDAO.create(juge);
		Jugement jugement156 = new Jugement(dossier.getId(), document.getId(), juge.getId(), LocalDate.now(),
				"recevable", "pas fonde");

		jugement156 = jugementDAO.create(jugement156);
		System.out.println(" -- ");

		// on recharge le dossier pour mettre a jour la collection de jugement
		// >> ATTENTION IL FAUDRA EGALEMENT RAFRAICHIR LE DOSSIER POUR LA GUI A
		// CHAQUE FOIS QUE L'ON CREE QQCH
		dossier = dossierDAO.find(dossier.getId());
		jugements = dossier.getJugements();
		for (Jugement jugement : jugements) {
			System.out.println(jugement.getId());
		}
		System.out.println(" -- ");
	}

	private void testDossierAvecRequete() {
		Dossier dossier = dossierDAO.create();

		Collection<Requete> requetes = dossier.getRequetes();
		System.out.println("collection requete vide :");
		for (Requete requete : requetes) {
			System.out.println(requete.getId());
		}
		System.out.println(" -- ");

		DocumentMinos document = new DocumentMinos("la_bible", TypeDocumentMinos.requeteSFP, la_bible.getBytes(),
				LocalDateTime.now());
		document = documentMinosDAO.create(document);

		Adresse adresse = new Adresse("grand place", "10", "bte 4", "7700", "belgique");
		adresse = adresseDAO.create(adresse);
		Personne requerant = new Personne(TypePersonne.physique, "Dupont", "Jean", "98765432145", adresse);
		requerant = personneDAO.create(requerant);
		Requete requete1 = new Requete(dossier.getId(), requerant.getId(), document.getId(), LocalDate.now(),
				"54564/151", "RG 56+556");

		requete1 = requeteDAO.create(requete1);
		System.out.println(" -- ");

		// on recharge le dossier pour mettre a jour la collection de requete >>
		// ATTENTION IL FAUDRA EGALEMENT RAFRAICHIR LE DOSSIER POUR LA GUI A
		// CHAQUE FOIS QUE L'ON CREE QQCH
		dossier = dossierDAO.find(dossier.getId());
		requetes = dossier.getRequetes();
		for (Requete requete : requetes) {
			System.out.println(requete.getId());
		}
		System.out.println(" -- ");
	}

	private void testAssignationTribunal() {
		Dossier dossier = dossierDAO.create();
		DocumentMinos document = new DocumentMinos("la_bible", TypeDocumentMinos.requeteSFP, la_bible.getBytes(),
				LocalDateTime.now());
		document = documentMinosDAO.create(document);

		Adresse adresse = new Adresse("chaussee de mons", "65", "truc", "7300", "belgique");
		adresse = adresseDAO.create(adresse);
		String nom = "Tribunal de bruxelles";
		String niveauTribunal = "Tribunal du travail";
		RoleAdresse roleAdresseTribunal = new RoleAdresse(adresse, nom, niveauTribunal);
		roleAdresseTribunal = roleAdresseDAO.create(roleAdresseTribunal);

		LocalDate date = LocalDate.now();
		AssignationTribunal assignationTribunal = new AssignationTribunal(dossier.getId(), document.getId(), date,
				roleAdresseTribunal);
		assignationTribunal = assignationTribunalDAO.create(assignationTribunal);
	}

	private void testDossierAvecAssignationsTribunal() {
		Dossier dossier = dossierDAO.create();

		Collection<AssignationTribunal> assignationsTribunal = dossier.getAssignationsTribunal();
		System.out.println("collection requete vide :");
		for (AssignationTribunal assignationTribunal : assignationsTribunal) {
			System.out.println(assignationTribunal.getId());
		}
		System.out.println(" -- ");

		DocumentMinos document = new DocumentMinos("la_bible", TypeDocumentMinos.requeteSFP, la_bible.getBytes(),
				LocalDateTime.now());
		document = documentMinosDAO.create(document);

		Adresse adresse = new Adresse("chaussee de mons", "65", "truc", "7300", "belgique");
		adresse = adresseDAO.create(adresse);
		String nom = "Tribunal de bruxelles";
		String niveauTribunal = "Tribunal du travail";
		RoleAdresse roleAdresseTribunal = new RoleAdresse(adresse, nom, niveauTribunal);
		roleAdresseTribunal = roleAdresseDAO.create(roleAdresseTribunal);
		LocalDate date = LocalDate.now();
		AssignationTribunal assignationTribunalMaintenant = new AssignationTribunal(dossier.getId(), document.getId(),
				date, roleAdresseTribunal);
		assignationTribunalMaintenant = assignationTribunalDAO.create(assignationTribunalMaintenant);

		System.out.println(" -- ");

		dossier = dossierDAO.find(dossier.getId());
		assignationsTribunal = dossier.getAssignationsTribunal();
		System.out.println("collection requete vide :");
		for (AssignationTribunal assignationTribunal : assignationsTribunal) {
			System.out.println(assignationTribunal.getId());
		}
		System.out.println(" -- ");
	}
}
