package minos.model.bean;

import java.time.LocalDate;

public class AssignationTribunal {
	private long id;
	private long idDossier;
	private long idDocument;
	private LocalDate date;
	private RoleAdresse tribunal;

	public AssignationTribunal(long id, long idDossier, long idDocument, LocalDate date, RoleAdresse tribunal) {
		super();
		this.id = id;
		this.idDossier = idDossier;
		this.idDocument = idDocument;
		this.date = date;
		this.tribunal = tribunal;
	}

	public AssignationTribunal(long idDossier, long idDocument, LocalDate date, RoleAdresse tribunal) {
		super();
		this.idDossier = idDossier;
		this.idDocument = idDocument;
		this.date = date;
		this.tribunal = tribunal;
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

	public long getIdDocument() {
		return idDocument;
	}

	public void setIdDocument(long idDocument) {
		this.idDocument = idDocument;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public RoleAdresse getTribunal() {
		return tribunal;
	}

	public void setTribunal(RoleAdresse tribunal) {
		this.tribunal = tribunal;
	}

}
