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

public class JugementDAO {

	public Jugement create(Jugement jugement) {
		PreparedStatement prepareStatement;
		try {
			prepareStatement = MinosConnection.getInstance().prepareStatement(
					"INSERT INTO jugement (id_dossier, id_document, id_juge, date_effet, recevable, fonde) VALUES (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, jugement.getIdDossier());
			prepareStatement.setLong(2, jugement.getIdDocument());
			prepareStatement.setLong(3, jugement.getIdJuge());
			prepareStatement.setDate(4, Date.valueOf(jugement.getDateEffet()));
			prepareStatement.setString(5, jugement.getRecevable());
			prepareStatement.setString(6, jugement.getFonde());
			prepareStatement.execute();
			ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
			generatedKeys.next();
			long id = generatedKeys.getLong(1);
			return find(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Jugement createV2(long idDossier, long idDocument, long idJuge, LocalDate dateEffet, String recevable, String fonde) {
		PreparedStatement prepareStatement;
		try {
			prepareStatement = MinosConnection.getInstance().prepareStatement(
					"INSERT INTO jugement (id_dossier, id_document, id_juge, date_effet, recevable, fonde) VALUES (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, idDossier);
			prepareStatement.setLong(2, idDocument);
			prepareStatement.setLong(3, idJuge);
			prepareStatement.setDate(4, Date.valueOf(dateEffet));
			prepareStatement.setString(5, recevable);
			prepareStatement.setString(6, fonde);
			prepareStatement.execute();
			ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
			generatedKeys.next();
			long id = generatedKeys.getLong(1);
			return find(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	

	public Jugement find(long id) {
		Jugement jugement = null;

		ResultSet result;
		try {
			result = MinosConnection.getInstance()
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM jugement WHERE id = " + id);
			if (result.next()) {
				long idDossier = result.getLong("id_dossier");
				long idDocument = result.getLong("id_document");
				long idJuge = result.getLong("id_juge");
				LocalDate dateEffet = result.getDate("date_effet").toLocalDate();
				String recevable = result.getString("recevable");
				String fonde = result.getString("fonde");
				jugement = new Jugement(id, idDossier, idDocument, idJuge, dateEffet, recevable, fonde);
			}
			return jugement;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Collection<Jugement> findJugementsForDossier(long idDossier) {
		ResultSet result;
		try {
			result = MinosConnection.getInstance()
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT id FROM jugement WHERE id_dossier = " + idDossier);
			Collection <Jugement> jugements = new ArrayList<>();
			while (result.next()){
				long idJugement = result.getLong("id");
				Jugement jugement = find(idJugement);
				jugements.add(jugement);
			}
			return jugements;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
