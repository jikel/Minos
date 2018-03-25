package minos.model.bean;

import java.time.LocalDateTime;

public class RendezVous {
	private long id;
	private long idDossier;
	private long idRoleAdresse;
	private LocalDateTime dateHeure;

	public RendezVous(long id, long idDossier, long idRoleAdresse, LocalDateTime dateHeure) {
		super();
		this.id = id;
		this.idDossier = idDossier;
		this.idRoleAdresse = idRoleAdresse;
		this.dateHeure = dateHeure;
	}

	public RendezVous(long idDossier, long idRoleAdresse, LocalDateTime dateHeure) {
		super();
		this.idDossier = idDossier;
		this.idRoleAdresse = idRoleAdresse;
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

	public long getIdRoleAdresse() {
		return idRoleAdresse;
	}

	public void setIdRoleAdresse(long idRoleAdresse) {
		this.idRoleAdresse = idRoleAdresse;
	}

	public LocalDateTime getDateHeure() {
		return dateHeure;
	}

	public void setDateHeure(LocalDateTime dateHeure) {
		this.dateHeure = dateHeure;
	}

}
