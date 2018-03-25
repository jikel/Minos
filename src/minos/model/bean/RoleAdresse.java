package minos.model.bean;

public class RoleAdresse {
	private long id;
	private Adresse adresse;
	private String nom;
	private String niveauTribunal;

	public RoleAdresse(long id, Adresse adresse, String nom, String niveauTribunal) {
		super();
		this.id = id;
		this.adresse = adresse;
		this.nom = nom;
		this.niveauTribunal = niveauTribunal;
	}

	public RoleAdresse(Adresse adresse, String nom, String niveauTribunal) {
		super();
		this.adresse = adresse;
		this.nom = nom;
		this.niveauTribunal = niveauTribunal;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNiveauTribunal() {
		return niveauTribunal;
	}

	public void setNiveauTribunal(String niveauTribunal) {
		this.niveauTribunal = niveauTribunal;
	}
}
