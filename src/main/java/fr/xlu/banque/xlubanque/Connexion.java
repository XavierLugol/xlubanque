package fr.xlu.banque.xlubanque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connexion {
	private static final String URL = "jdbc:postgresql://localhost:5432/BANQUE";
	private static final String LOGIN = "postgres";
	private static final String PASSWD = "hb";

	private static Connection connexion;

	public static Connection getConnexion() {
		if (connexion == null) {
			try {
				Connexion.connexion = DriverManager.getConnection(URL, LOGIN, PASSWD);
				System.out.println("Connexion OK");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return connexion;
	}

	public void close() {
		if (!(connexion == null)) {
			try {
				Connexion.getConnexion().close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		connexion = null;

	}

	public void setConnexion(Connection connexion) {
		this.connexion = connexion;
	}

	public void insertBanque() {
		Connection con = Connexion.getConnexion();
		try {
			Statement st = con.createStatement();
			// String req = 'INSERT INTO "BANQUE" (""NOM"") values ('toto')';
			st.execute("INSERT INTO \"BANQUE\" (\"NOM\") VALUES ('Banque du Midi')");
			st.execute("INSERT INTO \"BANQUE\" (\"NOM\") VALUES ('Banque du Nord')");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
