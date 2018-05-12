
package minos.model.service;

import java.time.LocalDate;
import java.util.Collection;

import minos.model.bean.AssignationTribunal;
import minos.model.bean.Dossier;
import minos.model.bean.Jugement;
import minos.model.bean.Requete;
import minos.model.bean.RoleAdresse;

public class DossierService {

	public static RoleAdresse tribunalCourant(Dossier dossier) {
		Collection<AssignationTribunal> assignationsTribunal = dossier.getAssignationsTribunal();
		RoleAdresse tribunalLePlusRecent = null;
		LocalDate datePlusRecente = null;
		for (AssignationTribunal assignationTribunal : assignationsTribunal) {
			LocalDate dateAssignation = assignationTribunal.getDate();
			if (datePlusRecente == null || datePlusRecente.isBefore(dateAssignation)) {
				datePlusRecente = dateAssignation;
				tribunalLePlusRecent = assignationTribunal.getTribunal();
			}
		}
		return tribunalLePlusRecent;
	}

	public static Requete derniereRequete(Dossier dossier) {
		Requete derniereRequete = null;
		for (Requete requete : dossier.getRequetes()) {
			if (derniereRequete == null || derniereRequete.getDateEffet().isBefore(requete.getDateEffet())) {
				derniereRequete = requete;
			}
		}
		return derniereRequete;
	}

	public static Jugement dernierJugement(Dossier dossier) {
		Jugement jugementDernier = null;
		for (Jugement jugement : dossier.getJugements()) {
			if (jugementDernier == null || jugementDernier.getDateEffet().isBefore(jugement.getDateEffet()))
				jugementDernier = jugement;
		}
		return jugementDernier;
	}
}
