package minos.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MinosConnection {
	// url de connexion
	private static String url = "jdbc:mysql://localhost/minos";
	// nom user
	private static String user = "root";
	// mot de passe de l'utilisateur
	private static String passwd = "MerRouge7964";
	// objet Connection
	private static Connection connect;
	
	//Constructeur privé
	  private MinosConnection(){
	    try {
	      connect = DriverManager.getConnection(url, user, passwd);
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	  }
	   
	  //Méthode qui va nous retourner notre instance et la créer si elle n'existe pas
	  public static Connection getInstance(){
		  if(connect == null){
		    new MinosConnection();
		    System.out.println("INSTANCIATION DE LA CONNEXION SQL ! ");
		  }
		  else{
		    System.out.println("CONNEXION SQL EXISTANTE ! ");
		  }
		  return connect;   
		}


}