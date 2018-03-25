package minos.model.bean;

import java.util.Map;

public class Dossier {
	private long id;
	// representation de la relation dossier_document en DB
	private Map<Long, String> nomsDocument; 

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

}
