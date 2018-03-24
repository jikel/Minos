package minos.scripts;

import java.sql.Connection;

import minos.model.bean.Adresse;
import minos.model.bean.Personne;
import minos.model.bean.TypePersonne;
import minos.model.dao.AdresseDAO;
import minos.model.dao.MinosConnection;
import minos.model.dao.PersonneDAO;

public class PopulateDB {
	public static void main(String[] args) {
		Connection conn = MinosConnection.getInstance();
		AdresseDAO adresseDAO = new AdresseDAO(conn);
		Adresse adresse = new Adresse("chaussee de mons", "65", "truc", "7300", "belgique");
		adresse = adresseDAO.create(adresse);
		Personne simon = new Personne(TypePersonne.physique, "Dubois", "Simon", "12345678901", adresse);
		PersonneDAO personneDAO = new PersonneDAO(conn);
		personneDAO.create(simon);
	}
}
