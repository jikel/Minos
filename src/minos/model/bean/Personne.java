package minos.model.bean;

public class Personne {
	private long id;
	private TypePersonne typePersonne;
	private String nom;
	private String prenom;
	private String niss;
	private Adresse adresse;

	public Personne(long id, TypePersonne typePersonne, String nom, String prenom, String niss, Adresse adresse) {
		super();
		this.id = id;
		this.typePersonne = typePersonne;
		this.nom = nom;
		this.prenom = prenom;
		this.niss = niss;
		this.adresse = adresse;
	}

	public Personne(long id, TypePersonne typePersonne, String nom, Adresse adresse) {
		super();
		this.id = id;
		this.typePersonne = typePersonne;
		this.nom = nom;
		this.adresse = adresse;
	}

	public Personne(TypePersonne typePersonne, String nom, String prenom, String niss, Adresse adresse) {
		super();
		this.typePersonne = typePersonne;
		this.nom = nom;
		this.prenom = prenom;
		this.niss = niss;
		this.adresse = adresse;
	}

	public Personne(TypePersonne typePersonne, String nom, Adresse adresse) {
		super();
		this.typePersonne = typePersonne;
		this.nom = nom;
		this.adresse = adresse;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TypePersonne getTypePersonne() {
		return typePersonne;
	}

	public void setTypePersonne(TypePersonne typePersonne) {
		this.typePersonne = typePersonne;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNiss() {
		return niss;
	}

	public void setNiss(String niss) {
		this.niss = niss;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	@Override
	public String toString() {
		return (nom + " " + prenom);
	}
	
}
