package minos.scripts;

import minos.model.bean.Dossier;
import minos.model.bean.Requete;
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
		Dossier dossier = dossierDAO.create();
		
//		new Requete(dossier.getId(), idRequerant, idDocument, dateEffet, numeroRole, numeroRG)
//		requeteDAO.create(requete);
		
	}
}
