package minos.model.bean;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public class Dossier {
	private long id;
	// representation de la relation dossier_document en DB
	private Map<Long, String> nomsDocument;
	private Collection<Jugement> jugements;
	private Collection<Requete> requetes;

	public Dossier(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Map<Long, String> getNomsDocument() {
		return nomsDocument;
	}

	public void setNomsDocument(Map<Long, String> documents) {
		this.nomsDocument = documents;
	}

	public Collection<Jugement> getJugements() {
		return jugements;
	}

	public void setJugements(Collection<Jugement> jugements) {
		this.jugements = jugements;
	}
	public Collection<Requete> getRequetes() {
		return requetes;
	}
	public void setRequetes(Collection<Requete> requetes) {
		this.requetes = requetes;
	}
	
	
}
