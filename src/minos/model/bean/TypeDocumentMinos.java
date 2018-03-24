package minos.model.bean;

public enum TypeDocumentMinos {
	pieceInventaire("piece_inventaire"), 
	conclusion("conclusion"), 
	rapportAdministratif("rapport_administratif"), 
	requeteSFP("requete_sfp"), 
	jugement("jugement");
	
	private String dbValue;

	private TypeDocumentMinos(String dbValue) {
		this.dbValue = dbValue;
	}

	public String getDbValue() {
		return dbValue;
	}
}
