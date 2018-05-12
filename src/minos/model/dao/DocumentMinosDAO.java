package minos.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import minos.model.bean.DocumentMinos;
import minos.model.bean.Dossier;
import minos.model.bean.TypeDocumentMinos;

public class DocumentMinosDAO {
	
	DossierDAO dossierDAO;
	
	public DocumentMinosDAO() {
		dossierDAO = new DossierDAO();
	}

	public DocumentMinos create(DocumentMinos document, Dossier dossier) {
		PreparedStatement prepareStatement;
		try {
			prepareStatement = MinosConnection.getInstance().prepareStatement(
					"INSERT INTO document (nom, type, contenu, date_reception) VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setString(1, document.getNom());
			prepareStatement.setString(2, document.getType().getDbValue());
			prepareStatement.setBytes(3, document.getContenu());
			// prepareStatement.setBlob(3, new
			// ByteArrayInputStream(document.getContenu())); // pas nécessaire
			// (pour l'instant du moins @ 24/03 - 22h28)
			prepareStatement.setTimestamp(4, Timestamp.valueOf(document.getDateReception()));
			prepareStatement.execute();
			ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
			generatedKeys.next();
			long id = generatedKeys.getLong(1);
			DocumentMinos newDocument = find(id);
			dossier.getNomsDocument().put(newDocument.getId(), newDocument.getNom());
			dossierDAO.updateNomsDocument(dossier);
			return newDocument;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public DocumentMinos find(long id) {
		DocumentMinos document = null;
		ResultSet result;
		try {
			result = MinosConnection.getInstance()
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM document WHERE id = " + id);
			if (result.first()) {
				String nom = result.getString("nom");

				String typeString = result.getString("type");
				TypeDocumentMinos type = null;

				if (typeString.equals(TypeDocumentMinos.conclusion.getDbValue())) {
					type = TypeDocumentMinos.conclusion;
				} else if (typeString.equals(TypeDocumentMinos.jugement.getDbValue())) {
					type = TypeDocumentMinos.jugement;
				} else if (typeString.equals(TypeDocumentMinos.pieceInventaire.getDbValue())) {

					type = TypeDocumentMinos.pieceInventaire;
				} else if (typeString.equals(TypeDocumentMinos.rapportAdministratif.getDbValue())) {
					type = TypeDocumentMinos.rapportAdministratif;
				} else if (typeString.equals(TypeDocumentMinos.requeteSFP.getDbValue())) {
					type = TypeDocumentMinos.requeteSFP;
				} else if (typeString.equals(TypeDocumentMinos.note.getDbValue())) {
					type = TypeDocumentMinos.note;
				}

				byte[] contenu = result.getBytes("contenu");
				LocalDateTime dateReception = result.getTimestamp("date_reception").toLocalDateTime();
				document = new DocumentMinos(id, nom, type, contenu, dateReception);
			}
			return document;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public DocumentMinos update(DocumentMinos document) {
		PreparedStatement prepareStatement;
		try {
			prepareStatement = MinosConnection.getInstance().prepareStatement(
					"UPDATE document SET nom = ?, type = ?, contenu = ?, date_reception = ? where id = ?");
			prepareStatement.setString(1, document.getNom());
			prepareStatement.setString(2, document.getType().getDbValue());
			prepareStatement.setBytes(3, document.getContenu());
			prepareStatement.setTimestamp(4, Timestamp.valueOf(document.getDateReception()));
			prepareStatement.setLong(5, document.getId());
			prepareStatement.execute();
			return find(document.getId());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Collection<Long> tousLesDocumentIds() {
		Collection<Long> ids = new ArrayList<>();
		ResultSet result;
		try {
			result = MinosConnection.getInstance()
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT id FROM document");
			while (result.next()) {
				ids.add(result.getLong("id"));
			}
			return ids;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
