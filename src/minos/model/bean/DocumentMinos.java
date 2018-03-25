package minos.model.bean;

import java.time.LocalDateTime;

public class DocumentMinos {
	private long id;
	private String nom;
	private TypeDocumentMinos type;
	private byte[] contenu;
	private LocalDateTime dateReception;

	public DocumentMinos(long id, String nom, TypeDocumentMinos type, byte[] contenu, LocalDateTime dateReception) {
		super();
		this.id = id;
		this.nom = nom;
		this.type = type;
		this.contenu = contenu;
		this.dateReception = dateReception;
	}

	public DocumentMinos(String nom, TypeDocumentMinos type, byte[] contenu, LocalDateTime dateReception) {
		super();
		this.nom = nom;
		this.type = type;
		this.contenu = contenu;
		this.dateReception = dateReception;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public TypeDocumentMinos getType() {
		return type;
	}

	public void setType(TypeDocumentMinos type) {
		this.type = type;
	}

	public byte[] getContenu() {
		return contenu;
	}

	public void setContenu(byte[] contenu) {
		this.contenu = contenu;
	}

	public LocalDateTime getDateReception() {
		return dateReception;
	}

	public void setDateReception(LocalDateTime dateReception) {
		this.dateReception = dateReception;
	}

}
