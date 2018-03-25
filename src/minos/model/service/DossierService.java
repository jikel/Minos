package minos.model.service;

import java.time.LocalDate;
import java.util.Collection;

import minos.model.bean.AssignationTribunal;
import minos.model.bean.Dossier;
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
}
