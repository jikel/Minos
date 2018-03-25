package minos.model.bean;

import java.time.LocalDate;

public class Requete {
	private long id;
	private long idDossier;
	private long idRequerant;
	private long idDocument;
	private LocalDate dateEffet;
	private String numeroRole;
	private String numeroRG;

	public Requete(long id, long idDossier, long idRequerant, long idDocument, LocalDate dateEffet, String numeroRole,
			String numeroRG) {
		super();
		this.id = id;
		this.idDossier = idDossier;
		this.idRequerant = idRequerant;
		this.idDocument = idDocument;
		this.dateEffet = dateEffet;
		this.numeroRole = numeroRole;
		this.numeroRG = numeroRG;
	}

	public Requete(long idDossier, long idRequerant, long idDocument, LocalDate dateEffet, String numeroRole,
			String numeroRG) {
		super();
		this.idDossier = idDossier;
		this.idRequerant = idRequerant;
		this.idDocument = idDocument;
		this.dateEffet = dateEffet;
		this.numeroRole = numeroRole;
		this.numeroRG = numeroRG;
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

	public long getIdRequerant() {
		return idRequerant;
	}

	public void setIdRequerant(long idRequerant) {
		this.idRequerant = idRequerant;
	}

	public long getIdDocument() {
		return idDocument;
	}

	public void setIdDocument(long idDocument) {
		this.idDocument = idDocument;
	}

	public LocalDate getDateEffet() {
		return dateEffet;
	}

	public void setDateEffet(LocalDate dateEffet) {
		this.dateEffet = dateEffet;
	}

	public String getNumeroRole() {
		return numeroRole;
	}

	public void setNumeroRole(String numeroRole) {
		this.numeroRole = numeroRole;
	}

	public String getNumeroRG() {
		return numeroRG;
	}

	public void setNumeroRG(String numeroRG) {
		this.numeroRG = numeroRG;
	}

}
