package minos.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import minos.model.bean.Adresse;
import minos.model.bean.Personne;
import minos.model.bean.TypePersonne;

public class PersonneDAO extends DAO<Personne> {

	private AdresseDAO adresseDAO;

	public PersonneDAO(Connection conn) {
		super(conn);
		adresseDAO = new AdresseDAO(conn);
	}

	@Override
	public Personne create(Personne personne) {
		PreparedStatement prepareStatement;

		try {
			if (personne.getTypePersonne().equals(TypePersonne.physique)) {
				if (personne.getAdresse() != null) {
					prepareStatement = this.connect.prepareStatement(
							"INSERT INTO personne (type, nom, prenom, niss, id_adresse) VALUES (?, ?, ?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS);
				} else {
					prepareStatement = this.connect.prepareStatement(
							"INSERT INTO personne (type, nom, prenom, niss) VALUES (?, ?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS);
				}
				prepareStatement.setString(1, personne.getTypePersonne().getDbValue());
				prepareStatement.setString(2, personne.getNom());
				prepareStatement.setString(3, personne.getPrenom());
				prepareStatement.setString(4, personne.getNiss());
				if (personne.getAdresse() != null) {
					prepareStatement.setLong(5, personne.getAdresse().getId());
				}
			} else {
				prepareStatement = this.connect.prepareStatement(
						"INSERT INTO personne (type, nom, id_adresse) VALUES (?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);
				prepareStatement.setString(1, personne.getTypePersonne().getDbValue());
				prepareStatement.setString(2, personne.getNom());
				prepareStatement.setLong(3, personne.getAdresse().getId());
			}

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
	public void delete(Personne obj) {
	}

	@Override
	public Personne update(Personne obj) {
		return null;
	}

	@Override
	public Personne find(long id) {
		Personne personne = null;
		ResultSet result;
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM personne WHERE id = " + id);
			if (result.first()) {
				long idAdresse = result.getLong("id_adresse");
				Adresse adresse = null;
				if (idAdresse != 0) {
					adresse = adresseDAO.find(idAdresse);
				}
				String typeString = result.getString("type");
				TypePersonne typePersonne;
				String nom = result.getString("nom");
				if (typeString.equals(TypePersonne.morale.getDbValue())) {
					typePersonne = TypePersonne.morale;
					personne = new Personne(id, typePersonne, nom, adresse);
				} else {
					typePersonne = TypePersonne.physique;
					String prenom = result.getString("prenom");
					String niss = result.getString("niss");
					personne = new Personne(id, typePersonne, nom, prenom, niss, adresse);
				}
			}
			return personne;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
