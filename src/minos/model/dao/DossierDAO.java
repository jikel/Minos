package minos.model.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import minos.model.bean.Adresse;
import minos.model.bean.Dossier;
import minos.model.bean.Personne;
import minos.model.bean.TypePersonne;

public class DossierDAO {

	public Dossier create (){
		PreparedStatement prepareStatement;
		try {
			prepareStatement = MinosConnection.getInstance().prepareStatement(
					"INSERT INTO dossier () VALUES ()",
					Statement.RETURN_GENERATED_KEYS);
			prepareStatement.execute();
			ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
			generatedKeys.next();
			long id = generatedKeys.getLong(1);
			return new Dossier(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Dossier update (Dossier dossier){

		return dossier;
	}
	
	public Dossier find (long id){
		Dossier dossier  = new Dossier(id);

		ResultSet result;
		try {
			result = MinosConnection.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM dossier_document WHERE id_dossier =id " + id);
			// a finir demain car requete de la mort
			MinosConnection.getInstance().prepareStatement("SELECT document.id, document.nom FROM document, dossier_document WHERE dossier_document.id_dossier = ?");
			while (result.next()) {

			}
			return dossier;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
}
