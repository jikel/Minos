package minos.model.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import minos.model.bean.Dossier;

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
}
