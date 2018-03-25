package minos.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import minos.model.bean.RendezVous;
import minos.model.bean.RoleAdresse;

public class RendezVousDAO {
	
	private RoleAdresseDAO roleAdresseDAO;

	public RendezVousDAO() {
		roleAdresseDAO = new RoleAdresseDAO();
	}
	
	public RendezVous create(RendezVous rendezVous) {
		PreparedStatement prepareStatement;
		try {
			prepareStatement = MinosConnection.getInstance().prepareStatement(
					"INSERT INTO rendez_vous (id_dossier, id_role_adresse, date_heure) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, rendezVous.getIdDossier());
			prepareStatement.setLong(2, rendezVous.getRoleAdresse().getId());
			prepareStatement.setTimestamp(3, Timestamp.valueOf(rendezVous.getDateHeure()));
			prepareStatement.execute();
			ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
			generatedKeys.next();
			long id = generatedKeys.getLong(1);
			return find(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public RendezVous find(long id) {
		RendezVous rendezVous = null;
		ResultSet result;
		try {
			result = MinosConnection.getInstance()
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM rendez_vous WHERE id = " + id);
			if (result.next()) {
				long idDossier = result.getLong("id_dossier");
				long idRoleAdresse = result.getLong("id_role_adresse");
				RoleAdresse roleAdresse = roleAdresseDAO.find(idRoleAdresse);
				LocalDateTime dateHeure = result.getTimestamp("date_heure").toLocalDateTime();
				rendezVous = new RendezVous(id, idDossier, roleAdresse, dateHeure);
			}
			return rendezVous;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
