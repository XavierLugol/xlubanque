package fr.xlu.banque.xlubanque;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BanqueBase extends Banque {
	private Banque banque = new Banque();

	public BanqueBase(Banque banque) {
		super();
		this.banque = banque;
	}

	public Banque getBanque() {
		return banque;
	}

	public void setBanque(Banque banque) {
		this.banque = banque;
	}

	public boolean ouvreCompteBase (Compte compte){
		Connexion con = new Connexion();
		Client client = this.getClientBase(compte.getClient().getNom(),compte.getClient().getPrenom());
		String insertCompte = "INSERT INTO \"COMPTE\" (\"NUM\",\"CLIENT_ID\",\"BANQUE_ID\")"
				+ " VALUES (" 
				+ "'" + compte.getNum() + "'" + "," 
				+ "'" + client.getId() + "'" + "," 
				+ this.getBanque().getBanqueId() + ")";
		try {
			Statement st = con.getConnexion().createStatement();
			st.execute(insertCompte, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = st.getGeneratedKeys();	
			rs.next();
			compte.setId(rs.getInt("ID"));
			rs.close();
			return true;
		} catch (SQLException e) {
			System.out.println(insertCompte);
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean ajouteClientBase(Client client) {
		Connexion con = new Connexion();
		String insertClient = "INSERT INTO \"CLIENT\" (\"NOM\",\"PRENOM\",\"NOMCOMPLET\",\"BANQUE_ID\")"
				+ " VALUES (" 
				+ "'" + client.getNom() + "'" + "," 
				+ "'" + client.getPrenom() + "'" + "," 
				+ "'" + client.getNomComplet() + "'" + "," 
				+ this.getBanque().getBanqueId() + ")";
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
	
	public boolean supprimeClientBase(Client client) {
		Connexion con = new Connexion();
		String deleteClient = "DELETE FROM \"CLIENT\" WHERE "
				+ " \"NOM\" = " + "'" + client.getNom() + "'" 
				+ " AND \"PRENOM\" = " + "'" + client.getPrenom() + "'" 
				+ " AND \"BANQUE_ID\" = "  + this.getBanque().getBanqueId();
		System.out.println(deleteClient);
		try {
			Statement st = con.getConnexion().createStatement();
			st.execute(deleteClient);
			return this.supprimeCompteBase(client);
		} catch (SQLException e) {
//			System.out.println(deleteClient);
			System.out.println(e.getMessage());
			return false;
		}		
	}

	public boolean supprimeCompteBase(Client client) {
		Connexion con = new Connexion();
		String deleteCompte = "DELETE FROM \"COMPTE\" WHERE "
				+ " \"CLIENT_ID\" = " + client.getId()
				+ " AND \"BANQUE_ID\" = "  + this.getBanque().getBanqueId();
//		System.out.println(deleteCompte);
		try {
			Statement st = con.getConnexion().createStatement();
			return st.execute(deleteCompte);
		} catch (SQLException e) {
//			System.out.println(deleteCompte);
			System.out.println(e.getMessage());
			return false;
		}		
	}

	public Client getClientBase(String nom, String prenom) {
		Connexion con = new Connexion();
		String selectClient = "SELECT \"ID\" FROM \"CLIENT\" WHERE "
				+ " \"NOM\" = " + "'" + nom + "'" 
				+ " AND \"PRENOM\" = " + "'" + prenom + "'" 
				+ " AND \"BANQUE_ID\" = "  + this.getBanque().getBanqueId();
		try {
			Statement st = con.getConnexion().createStatement();
			st.execute(selectClient);
			ResultSet rs = st.getResultSet();
			if (rs.next()) {
				try {
					Client client = new Client(nom,prenom);
					client.setId(rs.getInt("ID"));
					return client;
				}
				catch (BanqueException be) {
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

}
