package minos.model.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import minos.model.bean.Jugement;
import minos.model.bean.Requete;

public class RequeteDAO {

	public Requete create(Requete requete) {
		PreparedStatement prepareStatement;
		try {
			prepareStatement = MinosConnection.getInstance().prepareStatement(
					"INSERT INTO requete (id_dossier, id_requerant, id_document, date_effet, numero_role, numero_rg) VALUES (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, requete.getIdDossier());
			prepareStatement.setLong(2, requete.getIdRequerant());
			prepareStatement.setLong(3, requete.getIdDocument());
			prepareStatement.setDate(4, Date.valueOf(requete.getDateEffet()));
			prepareStatement.setString(5, requete.getNumeroRole());
			prepareStatement.setString(6, requete.getNumeroRG());
			prepareStatement.execute();
			ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
			generatedKeys.next();
			long id = generatedKeys.getLong(1);
			return find(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Requete find(long id) {
		Requete requete = null;

		ResultSet result;
		try {
			result = MinosConnection.getInstance()
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM requete WHERE id = " + id);
			if (result.next()) {
				long idDossier = result.getLong("id_dossier");
				long idRequerant = result.getLong("id_requerant");
				long idDocument = result.getLong("id_document");
				LocalDate dateEffet = result.getDate("date_effet").toLocalDate();
				String numeroRole = result.getString("numero_role");
				String numeroRG = result.getString("numero_rg");
				requete = new Requete(id, idDossier, idRequerant, idDocument, dateEffet, numeroRole, numeroRG);
			}
			return requete;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Collection<Requete> findRequetesForDossier(long idDossier) {
		ResultSet result;
		try {
			result = MinosConnection.getInstance()
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT id FROM requete WHERE id_dossier = " + idDossier);
			Collection <Requete> requetes = new ArrayList<>();
			while (result.next()){
				long idRequete = result.getLong("id");
				Requete requete = find(idRequete);
				requetes.add(requete);
			}
			return requetes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


}
