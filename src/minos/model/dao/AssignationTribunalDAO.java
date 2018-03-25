package minos.model.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;

import minos.model.bean.Adresse;
import minos.model.bean.AssignationTribunal;
import minos.model.bean.RoleAdresse;

public class AssignationTribunalDAO {
	
	private RoleAdresseDAO roleAdresseDAO;

	public AssignationTribunalDAO() {
		roleAdresseDAO = new RoleAdresseDAO();
	}

	public AssignationTribunal create(AssignationTribunal assignationTribunal) {
		PreparedStatement prepareStatement;
		try {
			prepareStatement = MinosConnection.getInstance().prepareStatement(
					"INSERT INTO assignation_tribunal (id_dossier, id_document, date, id_tribunal) VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, assignationTribunal.getIdDossier());
			prepareStatement.setLong(2, assignationTribunal.getIdDocument());
			prepareStatement.setDate(3, Date.valueOf(assignationTribunal.getDate()));
			prepareStatement.setLong(4, assignationTribunal.getTribunal().getId());

			prepareStatement.execute();
			ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
			generatedKeys.next();
			long id = generatedKeys.getLong(1);
			return find(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public AssignationTribunal find(long id) {
		AssignationTribunal assignationTribunal = null;
		ResultSet result;
		try {
			result = MinosConnection.getInstance()
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM assignation_tribunal WHERE id = " + id);
			if (result.first()) {
				long idDossier = result.getLong("id_dossier");
				long idDocument = result.getLong("id_document");
				LocalDate date = result.getDate("date").toLocalDate();
				long idTribunal = result.getLong("id_tribunal");
				RoleAdresse tribunal = roleAdresseDAO.find(idTribunal);
				assignationTribunal = new AssignationTribunal(id, idDossier, idDocument, date, tribunal);
			}
			return assignationTribunal;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
