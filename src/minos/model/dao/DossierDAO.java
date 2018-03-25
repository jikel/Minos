package minos.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import minos.model.bean.Dossier;

public class DossierDAO {

	public Dossier create() {
		PreparedStatement prepareStatement;
		try {
			prepareStatement = MinosConnection.getInstance().prepareStatement("INSERT INTO dossier () VALUES ()",
					Statement.RETURN_GENERATED_KEYS);
			prepareStatement.execute();
			ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
			generatedKeys.next();
			long id = generatedKeys.getLong(1);
			return find(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Dossier update(Dossier dossier) {
		try {
			PreparedStatement deleteStatement = MinosConnection.getInstance()
					.prepareStatement("DELETE FROM corr_dossier_document WHERE id_dossier = ?");
			deleteStatement.setLong(1, dossier.getId());
			deleteStatement.execute();

			// pour le keyset, on recupere l'equivalent de tous les numeros de tiroir
			for (long idDocument : dossier.getNomsDocument().keySet()) {
				PreparedStatement insertStatement = MinosConnection.getInstance()
						.prepareStatement("INSERT INTO corr_dossier_document (id_dossier, id_document) VALUES (?, ?)");
				insertStatement.setLong(1, dossier.getId());
				insertStatement.setLong(2, idDocument);
				insertStatement.execute();

			}
			return find(dossier.getId());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Dossier find(long idDossier) {
		Dossier dossier = new Dossier(idDossier);

		ResultSet result;
		try {
			PreparedStatement prepareStatement = MinosConnection.getInstance()
					.prepareStatement("SELECT document.id, document.nom FROM document, corr_dossier_document "
							+ "WHERE document.id = corr_dossier_document.id_document AND corr_dossier_document.id_dossier = ?");
			prepareStatement.setLong(1, idDossier);
			result = prepareStatement.executeQuery();
			Map<Long, String> documents = new HashMap<>();
			while (result.next()) {
				long idDocument = result.getLong("id");
				String nom = result.getString("nom");
				documents.put(idDocument, nom);
			}
			dossier.setNomsDocument(documents);
			return dossier;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
