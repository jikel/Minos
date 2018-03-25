package minos.model.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import minos.model.bean.Adresse;
import minos.model.bean.Jugement;
import minos.model.bean.Requete;
import minos.model.bean.RoleAdresse;

public class RoleAdresseDAO {
	
	private AdresseDAO adresseDAO;

	public RoleAdresseDAO() {
		adresseDAO = new AdresseDAO(MinosConnection.getInstance());
	}

	public RoleAdresse create(RoleAdresse roleAdresse) {
		PreparedStatement prepareStatement;
		try {
			prepareStatement = MinosConnection.getInstance().prepareStatement(
					"INSERT INTO role_adresse (id_adresse, nom, niveau_tribunal) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, roleAdresse.getAdresse().getId());
			prepareStatement.setString(2, roleAdresse.getNom());
			prepareStatement.setString(3, roleAdresse.getNiveauTribunal());
			prepareStatement.execute();
			ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
			generatedKeys.next();
			long id = generatedKeys.getLong(1);
			return find(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public RoleAdresse find(long id) {
		RoleAdresse roleAdresse = null;

		ResultSet result;
		try {
			result = MinosConnection.getInstance()
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM role_adresse WHERE id = " + id);
			if (result.next()) {
				long idAdresse = result.getLong("id_adresse");
				Adresse adresse = adresseDAO.find(idAdresse);
				String nom = result.getString("nom");
				String niveauTribunal = result.getString("niveau_tribunal");
				roleAdresse = new RoleAdresse(id, adresse, nom, niveauTribunal);
			}
			return roleAdresse;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}