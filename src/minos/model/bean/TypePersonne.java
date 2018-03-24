package minos.model.bean;

public enum TypePersonne {
	morale("m"), physique("p");
	
	private String dbValue;

	private TypePersonne(String dbValue) {
		this.dbValue = dbValue;
	}

	public String getDbValue() {
		return dbValue;
	}
}
