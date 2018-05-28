package minos.model.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Dossier {
	private long id;
	// representation de la relation dossier_document en DB
	private Map<Long, String> nomsDocument = new HashMap<>();
	private Collection<Jugement> jugements = new ArrayList<>();
	private Collection<Requete> requetes = new ArrayList<>();
	private Collection<AssignationTribunal> assignationsTribunal = new ArrayList<>();
	
	private Collection<RendezVous> rendezVous = new ArrayList<>();

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

	public Collection<AssignationTribunal> getAssignationsTribunal() {
		return assignationsTribunal;
	}

	public void setAssignationsTribunal(Collection<AssignationTribunal> assignationsTribunal) {
		this.assignationsTribunal = assignationsTribunal;
	}
	
	public Collection<RendezVous> getRendezVous() {
		return rendezVous;
	}

	public void setRendezVous(Collection<RendezVous> rendezVous) {
		this.rendezVous = rendezVous;
	}
}
