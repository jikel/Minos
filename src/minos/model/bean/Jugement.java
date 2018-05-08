package minos.model.bean;

import java.time.LocalDate;

public class Jugement {
	private long id;
	private long idDossier;
	private long idDocument;
	private long idJuge;
	private LocalDate dateEffet;
	private String recevable;
	private String fonde;

	public Jugement(long id, long idDossier, long idDocument, long idJuge, LocalDate dateEffet, String recevable,
			String fonde) {
		super();
		this.id = id;
		this.idDossier = idDossier;
		this.idDocument = idDocument;
		this.idJuge = idJuge;
		this.dateEffet = dateEffet;
		this.recevable = recevable;
		this.fonde = fonde;
	}

	// à remplacer par (dans JugementDAO): Jugement create(long idDossier, long idDocument, long idJuge, LocalDate dateEffet, String recevable, String fonde)" 
	public Jugement(long idDossier, long idDocument, long idJuge, LocalDate dateEffet, String recevable, String fonde) {
		super();
		this.idDossier = idDossier;
		this.idDocument = idDocument;
		this.idJuge = idJuge;
		this.dateEffet = dateEffet;
		this.recevable = recevable;
		this.fonde = fonde;
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

	public long getIdJuge() {
		return idJuge;
	}

	public void setIdJuge(long idJuge) {
		this.idJuge = idJuge;
	}

	public LocalDate getDateEffet() {
		return dateEffet;
	}

	public void setDateEffet(LocalDate dateEffet) {
		this.dateEffet = dateEffet;
	}

	public String getRecevable() {
		return recevable;
	}

	public void setRecevable(String recevable) {
		this.recevable = recevable;
	}

	public String getFonde() {
		return fonde;
	}

	public void setFonde(String fonde) {
		this.fonde = fonde;
	}

}
