package minos.model.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import minos.model.bean.Jugement;

public class JugementDAO {

	public Jugement create(Jugement jugement) {
		PreparedStatement prepareStatement;
		try {
			prepareStatement = MinosConnection.getInstance().prepareStatement(
					"INSERT INTO jugement (id_dossier, id_document, date_effet, recevable, fonde) VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, jugement.getIdDossier());
			prepareStatement.setLong(2, jugement.getIdDocument());
			prepareStatement.setDate(3, Date.valueOf(jugement.getDateEffet()));
			prepareStatement.setString(4, jugement.getRecevable());
			prepareStatement.setString(5, jugement.getFonde());
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
				long idDocument = result.getLong("id_document");
				LocalDate dateEffet = result.getDate("date_effet").toLocalDate();
				String recevable = result.getString("recevable");
				String fonde = result.getString("fonde");
				new Jugement(id, idDocument, dateEffet, recevable, fonde);
			}
			return jugement;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
