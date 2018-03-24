package minos.model.dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import minos.model.bean.Adresse;
import minos.model.bean.DocumentMinos;
import minos.model.bean.Personne;
import minos.model.bean.TypeDocumentMinos;
import minos.model.bean.TypePersonne;

public class DocumentMinosDAO extends DAO<DocumentMinos> {

	public DocumentMinosDAO(Connection conn) {
		super(conn);
	}

	@Override
	public DocumentMinos create(DocumentMinos document) {
		PreparedStatement prepareStatement;
		try {
			prepareStatement = this.connect.prepareStatement(
					"INSERT INTO document (nom, type, contenu, date_effet, date_reception) VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setString(1, document.getNom());
			prepareStatement.setString(2, document.getType().getDbValue());
			prepareStatement.setBytes(3, document.getContenu());
//			prepareStatement.setBlob(3, new ByteArrayInputStream(document.getContenu())); // pas nécessaire (pour l'instant du moins @ 24/03 - 22h28)
			prepareStatement.setDate(4, Date.valueOf(document.getDateEffet()));
			prepareStatement.setTimestamp(5, Timestamp.valueOf(document.getDateReception()));
			prepareStatement.execute();
			ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
			generatedKeys.next();
			long id = generatedKeys.getLong(1);
			return find(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(DocumentMinos obj) {
	}

	@Override
	public DocumentMinos update(DocumentMinos obj) {
		return null;
	}

	@Override
	public DocumentMinos find(long id) {
		DocumentMinos document = null;
		ResultSet result;
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM document WHERE id = " + id);
			if (result.first()) {
				String nom = result.getString("nom");

				String typeString = result.getString("type");
				TypeDocumentMinos type = null;
				
				if (typeString.equals(TypeDocumentMinos.conclusion.getDbValue())){
					type = TypeDocumentMinos.conclusion;
				}
				else if (typeString.equals(TypeDocumentMinos.jugement.getDbValue())){
					type = TypeDocumentMinos.jugement;
				}
				else if (typeString.equals(TypeDocumentMinos.pieceInventaire.getDbValue())){
					
					type = TypeDocumentMinos.pieceInventaire;
				}
				else if (typeString.equals(TypeDocumentMinos.rapportAdministratif.getDbValue())){
					type = TypeDocumentMinos.rapportAdministratif;
				}
				else if (typeString.equals(TypeDocumentMinos.requeteSFP.getDbValue())){
					type = TypeDocumentMinos.requeteSFP;
				}
				
				byte[] contenu = result.getBytes("contenu");
				LocalDate dateEffet = result.getDate("date_effet").toLocalDate();
				LocalDateTime dateReception = result.getTimestamp("date_reception").toLocalDateTime();
				document = new DocumentMinos(id, nom, type, contenu, dateEffet, dateReception);
			}
			return document;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
