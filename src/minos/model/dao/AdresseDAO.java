package minos.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import minos.model.bean.Adresse;

public class AdresseDAO {

	public Adresse create(Adresse adresse) {
		PreparedStatement prepareStatement;
		try {
			prepareStatement = MinosConnection.getInstance().prepareStatement(
					"INSERT INTO adresse (rue, numero, boite, code_postal, pays) VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setString(1, adresse.getRue());
			prepareStatement.setString(2, adresse.getNumero());
			prepareStatement.setString(3, adresse.getBoite());
			prepareStatement.setString(4, adresse.getCodePostal());
			prepareStatement.setString(5, adresse.getPays());
			prepareStatement.execute();
			ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
			generatedKeys.next();
			long id = generatedKeys.getLong(1);
			return find(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Adresse find(long id) {
		Adresse adresse = null;
		ResultSet result;
		try {
			result = MinosConnection.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM adresse WHERE id = " + id);
			if (result.first()) {
				String rue = result.getString("rue");
				String numero = result.getString("numero");
				String boite = result.getString("boite");
				String codePostal = result.getString("code_postal");
				String pays = result.getString("pays");
				adresse = new Adresse(id, rue, numero, boite, codePostal, pays);
			}
			return adresse;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
