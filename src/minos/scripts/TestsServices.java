package minos.scripts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import minos.model.bean.Adresse;
import minos.model.bean.AssignationTribunal;
import minos.model.bean.DocumentMinos;
import minos.model.bean.Dossier;
import minos.model.bean.RoleAdresse;
import minos.model.bean.TypeDocumentMinos;
import minos.model.dao.AdresseDAO;
import minos.model.dao.AssignationTribunalDAO;
import minos.model.dao.DocumentMinosDAO;
import minos.model.dao.DossierDAO;
import minos.model.dao.JugementDAO;
import minos.model.dao.PersonneDAO;
import minos.model.dao.RendezVousDAO;
import minos.model.dao.RequeteDAO;
import minos.model.dao.RoleAdresseDAO;
import minos.model.service.DossierService;

public class TestsServices {
	
	public static void main(String[] args) {
		new TestsServices().testDossierAvecAssignationsTribunal();
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

	public TestsServices() {
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

	private void testDossierAvecAssignationsTribunal() {
		Dossier dossier = dossierDAO.create();

		DocumentMinos document = createDocument();
		Adresse adresse = createAdresse();
		RoleAdresse tribunalDuTravail = createTribunal(adresse, "tribunal du travail");
		RoleAdresse tribunalCoursDAppel = createTribunal(adresse, "cours d'appel");
		
		LocalDate date1 = LocalDate.of(2000, 1, 8);
		AssignationTribunal assignation1 = new AssignationTribunal(dossier.getId(), document.getId(), date1, tribunalDuTravail);
		assignation1 = assignationTribunalDAO.create(assignation1);
		
		LocalDate date2 = LocalDate.now();
		AssignationTribunal assignation2 = new AssignationTribunal(dossier.getId(), document.getId(), date2, tribunalCoursDAppel);
		assignation2 = assignationTribunalDAO.create(assignation2);
		
		// on recharge le dossier pour mettre a jour la collection de requete >> ATTENTION IL FAUDRA EGALEMENT RAFRAICHIR LE DOSSIER POUR LA GUI A CHAQUE FOIS QUE L'ON CREE QQCH
		dossier = dossierDAO.find(dossier.getId()); // !!!!!!!
		
		RoleAdresse tribunalCourant = DossierService.tribunalCourant(dossier);

		System.out.println(tribunalCourant.getNom());
	}

	private DocumentMinos createDocument() {
		DocumentMinos document = new DocumentMinos("la_bible", TypeDocumentMinos.requeteSFP, "blablabla".getBytes(),
				LocalDateTime.now());
		document = documentMinosDAO.create(document);
		return document;
	}

	private Adresse createAdresse() {
		Adresse adresse = new Adresse("chaussee de mons", "65", "truc", "7300", "belgique");
		adresse = adresseDAO.create(adresse);
		return adresse;
	}

	private RoleAdresse createTribunal(Adresse adresse) {
		String nom = "Tribunal de bruxelles";
		String niveauTribunal = "Tribunal du travail";
		RoleAdresse roleAdresseTribunal = new RoleAdresse(adresse, nom, niveauTribunal);
		roleAdresseTribunal = roleAdresseDAO.create(roleAdresseTribunal);
		return roleAdresseTribunal;
	}
	
	private RoleAdresse createTribunal(Adresse adresse, String nom) {
		String niveauTribunal = "Tribunal du travail";
		RoleAdresse roleAdresseTribunal = new RoleAdresse(adresse, nom, niveauTribunal);
		roleAdresseTribunal = roleAdresseDAO.create(roleAdresseTribunal);
		return roleAdresseTribunal;
	}

}
