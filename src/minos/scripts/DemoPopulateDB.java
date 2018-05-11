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

public class DemoPopulateDB {
	
	public static void main(String[] args) {
		new DemoPopulateDB().fillDb();
	}
	
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


	public DemoPopulateDB() {
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

	private void fillDb() {
		
		// CREATION DOSSIER AVEC REQUETE
		Dossier dossier = dossierDAO.create();

		// 1ere requete
		DocumentMinos documentRequete = new DocumentMinos("la_bible", TypeDocumentMinos.requeteSFP, la_bible.getBytes(),
				LocalDateTime.now());
		documentRequete = documentMinosDAO.create(documentRequete);
		
		dossier.getNomsDocument().put(documentRequete.getId(), documentRequete.getNom());
		
	

		Adresse adresse = new Adresse("grand place", "10", "bte 4", "7700", "belgique");
		adresse = adresseDAO.create(adresse);
		Personne requerant = new Personne(TypePersonne.physique, "Dupont", "Jean", "98765432145", adresse);
		requerant = personneDAO.create(requerant);
		Requete requete1 = new Requete(dossier.getId(), requerant.getId(), documentRequete.getId(), LocalDate.now(),
				"54564/151", "RG 56+556");

		requete1 = requeteDAO.create(requete1);
		
		// 2eme requete
		DocumentMinos deuxiemeRequete = new DocumentMinos("pas_la_bible", TypeDocumentMinos.requeteSFP, la_bible.getBytes(), LocalDateTime.now());
		deuxiemeRequete = documentMinosDAO.create(deuxiemeRequete);
		Requete requete2 = new Requete(dossier.getId(), requerant.getId(), deuxiemeRequete.getId(), LocalDate.now(), "12364/789", "RG 63978");
		requete2 = requeteDAO.create(requete2);
		
		dossier.getNomsDocument().put(deuxiemeRequete.getId(), deuxiemeRequete.getNom());

		
		// ASSIGNATION TRIBUNAL	
		Adresse adresseTribunal = new Adresse("Place Poelaert", "3", "", "1000", "belgique");
		adresseTribunal = adresseDAO.create(adresseTribunal);
		String nom = "Tribunal de bruxelles";
		String niveauTribunal = "Tribunal du travail";
		RoleAdresse roleAdresseTribunal = new RoleAdresse(adresseTribunal, nom, niveauTribunal);
		roleAdresseTribunal = roleAdresseDAO.create(roleAdresseTribunal);	
		
		LocalDate date = LocalDate.now();
		AssignationTribunal assignationTribunal = new AssignationTribunal(dossier.getId(), documentRequete.getId(), date,
				roleAdresseTribunal);
		assignationTribunal = assignationTribunalDAO.create(assignationTribunal);
		
		// RENDEZ-VOUS TRIBUNAL
		long dossierId = dossier.getId();
		LocalDateTime dateHeure = LocalDateTime.now();
		RendezVous rendezVous = new RendezVous(dossierId, roleAdresseTribunal, dateHeure);
		rendezVous = rendezVousDAO.create(rendezVous);
		
		// ROLE ADRESSE 
		Adresse adresseRole = new Adresse("chaussee de mons", "65", "truc", "7300", "belgique");
		adresseRole = adresseDAO.create(adresseRole);
		String nomAdresse = "domicile marcel";
		String niveauDomicile = null;
		RoleAdresse roleAdresseNonTribunal = new RoleAdresse(adresseRole, nomAdresse, niveauDomicile);
		roleAdresseNonTribunal = roleAdresseDAO.create(roleAdresseNonTribunal);

		Adresse adresseTribMons = new Adresse("Rue de la Station", "112", "b22", "7300", "belgique");
		adresseTribMons = adresseDAO.create(adresseTribMons);
		nomAdresse = "Tribunal de Mons";
		niveauTribunal = "Tribunal du travail";
		RoleAdresse roleAdresseMarcel = new RoleAdresse(adresseTribMons, nomAdresse, niveauTribunal);

		roleAdresseTribunal = roleAdresseDAO.create(roleAdresseMarcel);
		
		// JUGEMENT
		DocumentMinos documentJugement = new DocumentMinos("la_bible", TypeDocumentMinos.jugement, la_bible.getBytes(),
				LocalDateTime.now());
		documentJugement = documentMinosDAO.create(documentJugement);
		
		dossier.getNomsDocument().put(documentJugement.getId(), documentJugement.getNom());

		Personne juge = new Personne(TypePersonne.physique, "hubain", "roger", null, null);
		juge = personneDAO.create(juge);
		Jugement jugement = new Jugement(dossier.getId(), documentJugement.getId(), juge.getId(), LocalDate.now(), "recevable",
				"pas fonde");

		jugement = jugementDAO.create(jugement);
		
		dossier = dossierDAO.updateAll(dossier);
	
//		new Requete(dossier.getId(), idRequerant, idDocument, dateEffet, numeroRole, numeroRG)
//		requeteDAO.create(requete);

		
	}
}
