package minos.model.bean;

import java.time.LocalDateTime;

public class RendezVous {
	private long id;
	private long idDossier;
	private RoleAdresse roleAdresse;
	private LocalDateTime dateHeure;

	public RendezVous(long id, long idDossier, RoleAdresse roleAdresse, LocalDateTime dateHeure) {
		super();
		this.id = id;
		this.idDossier = idDossier;
		this.roleAdresse = roleAdresse;
		this.dateHeure = dateHeure;
	}

	public RendezVous(long idDossier, RoleAdresse roleAdresse, LocalDateTime dateHeure) {
		super();
		this.idDossier = idDossier;
		this.roleAdresse = roleAdresse;
		this.dateHeure = dateHeure;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdDossier() {
		return idDossier;
	}

	public void setIdDossier(long idDossier) {
		this.idDossier = idDossier;
	}

	public RoleAdresse getRoleAdresse() {
		return roleAdresse;
	}

	public void setRoleAdresse(RoleAdresse roleAdresse) {
		this.roleAdresse = roleAdresse;
	}

	public LocalDateTime getDateHeure() {
		return dateHeure;
	}

	public void setDateHeure(LocalDateTime dateHeure) {
		this.dateHeure = dateHeure;
	}

}
