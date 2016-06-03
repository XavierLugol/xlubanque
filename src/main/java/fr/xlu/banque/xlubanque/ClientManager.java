package fr.xlu.banque.xlubanque;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientManager {

	public Client getClient(String nom, String prenom, int banque_id) {
		Connexion con = new Connexion();
		String selectClient = "SELECT id,nomcomplet" 
				+ " FROM banque.client" 
				+ " WHERE nom = " + "'" + nom + "'"
				+ " AND prenom = " + "'" + prenom + "'" 
				+ " AND banque_id = " + banque_id;
		try {
			Statement st = con.getConnexion().createStatement();
			st.execute(selectClient);
			ResultSet rs = st.getResultSet();
			if (rs.next()) {
				try {
					Client client = new Client(nom, prenom);
					client.setId(rs.getInt("ID"));
					client.setNomComplet(rs.getString("NOMCOMPLET"));
					client.setBanque_id(banque_id);
					return client;
				} catch (BanqueException be) {
					System.out.println(be.getMessage());
					return null;
				}
			} else {
				return null;
			}
		} catch (SQLException e) {
			System.out.println(selectClient);
			System.out.println(e.getMessage());
			return null;
		}

	}

	public boolean ajoutClient(Client client) {
		Connexion con = new Connexion();
		String insertClient = "INSERT INTO banque.client" 
				+ " (nom,prenom,nomcomplet,banque_id)" 
				+ " VALUES ('"	+ client.getNom() + "'" 
				+ "," + "'" + client.getPrenom() + "'" 
				+ "," + "'" + client.getNomComplet() + "'" 
				+ "," + client.getBanque_id() + ")";
		try {
			Statement st = con.getConnexion().createStatement();
			st.execute(insertClient, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			client.setId(rs.getInt("ID"));
			rs.close();
			return true;
		} catch (SQLException e) {
			System.out.println(insertClient);
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean supprimeClient(Client client) {
		Connexion con = new Connexion();
		String deleteClient = "DELETE FROM banque.client" 
				+ " WHERE nom = " + "'" + client.getNom() + "'"
				+ " AND prenom = " + "'" + client.getPrenom() + "'" 
				+ " AND banque_id = " + client.getBanque_id();
		try {
			Statement st = con.getConnexion().createStatement();
			st.execute(deleteClient);
			return this.supprimeComptes(client);
		} catch (SQLException e) {
			// System.out.println(deleteClient);
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean supprimeComptes(Client client) {
		Connexion con = new Connexion();
		String deleteCompte = "DELETE FROM banque.compte" 
				+ " WHERE " + " client_id = " + client.getId()
				+ " AND banque_id = " + client.getBanque_id();
		// System.out.println(deleteCompte);
		try {
			Statement st = con.getConnexion().createStatement();
			return st.execute(deleteCompte);
		} catch (SQLException e) {
			// System.out.println(deleteCompte);
			System.out.println(e.getMessage());
			return false;
		}
	}


}
