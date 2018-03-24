package minos.model.bean;

public class Adresse {
	private long id;
	private String rue;
	private String numero;
	private String boite;
	private String codePostal;
	private String pays;

	public Adresse(long id, String rue, String numero, String boite, String codePostal, String pays) {
		super();
		this.id = id;
		this.rue = rue;
		this.numero = numero;
		this.boite = boite;
		this.codePostal = codePostal;
		this.pays = pays;
	}

	public Adresse(String rue, String numero, String boite, String codePostal, String pays) {
		super();
		this.rue = rue;
		this.numero = numero;
		this.boite = boite;
		this.codePostal = codePostal;
		this.pays = pays;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBoite() {
		return boite;
	}

	public void setBoite(String boite) {
		this.boite = boite;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

}
