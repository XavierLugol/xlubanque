package fr.xlu.banque.xlubanque;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BanqueManager {

	private Banque banque = new Banque();

	public BanqueManager(Banque banque) {
		super();
		this.banque = banque;
	}

	public BanqueManager() {
		super();
	}

	public Banque getBanque() {
		return banque;
	}

	public void setBanque(Banque banque) {
		this.banque = banque;
	}

/**
 * @author Xavier Lugol
 * @param nom : nom de la banque à créer 
 * @return : une instance de la banque créé ou trouvée en base.
 * @throws BanqueException
 *
 */
	public Banque ajoutBanque(String nom) throws BanqueException{
		Connexion con = new Connexion();
		// Vérification que le nom de la banque est suffisamment rempli
		if ((nom == null) || nom.length() < 2) {
			throw new BanqueException("Le nom de la banque doit avoir 2 caractères");
		}
		// Vérification que la banque n'existe pas déjà
		Banque banque = this.getBanque(nom);
		if (!(banque == null)) {
			return banque;
		} else {
			banque = new Banque();
		}
		
		// Insertion de la banque en base
		String insertBanque = "INSERT INTO banque.banque (nom)"
				+ " VALUES (" 
				+ "'" + nom + "'" + ")";
		try {
			Statement st = con.getConnexion().createStatement();
			st.execute(insertBanque, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = st.getGeneratedKeys();	
			rs.next();
			banque.setId(rs.getInt("ID"));
			banque.setNom(nom);
			rs.close();
			return banque;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}

	public boolean ouvreCompteBase (Compte compte){
		Connexion con = new Connexion();
		Client client = this.getClientBase(compte.getClient().getNom(),compte.getClient().getPrenom());
		String insertCompte = "INSERT INTO banque.compte" 
				+ " (num,client_id,banque_id)"
				+ " VALUES (" 
				+ "'" + compte.getNum() + "'" + "," 
				+ "'" + client.getId() + "'" + "," 
				+ this.getBanque().geId() + ")";
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
		String insertClient = "INSERT INTO banque.client (nom,prenom,nom_complet,banque_id)"
				+ " VALUES (" 
				+ "'" + client.getNom() + "'" + "," 
				+ "'" + client.getPrenom() + "'" + "," 
				+ "'" + client.getNomComplet() + "'" + "," 
				+ this.getBanque().geId() + ")";
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
		String deleteClient = "DELETE FROM banque.client WHERE "
				+ " nom = " + "'" + client.getNom() + "'" 
				+ " AND prenom = " + "'" + client.getPrenom() + "'" 
				+ " AND banque_id = "  + this.getBanque().geId();
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
		String deleteCompte = "DELETE FROM banque.compte WHERE "
				+ " client_id = " + client.getId()
				+ " AND banque_id = "  + this.getBanque().geId();
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
		String selectClient = "SELECT id,nomcomplet" 
				+ " FROM banque.client WHERE "
				+ " nom = " + "'" + nom + "'" 
				+ " AND prenom = " + "'" + prenom + "'" 
				+ " AND banque_id = "  + this.getBanque().geId();
		try {
			Statement st = con.getConnexion().createStatement();
			st.execute(selectClient);
			ResultSet rs = st.getResultSet();
			if (rs.next()) {
				try {
					Client client = new Client(nom,prenom);
					client.setId(rs.getInt("ID"));
					client.setNomComplet(rs.getString("NOMCOMPLET"));
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

	public Banque getBanque(String nom) {
		Connexion con = new Connexion();
		String selectBanque = "SELECT id,nom" 
				+ " FROM banque.banque WHERE "
				+ " nom = " + "'" + nom + "'" ;
		try {
			Statement st = con.getConnexion().createStatement();
			st.execute(selectBanque);
			ResultSet rs = st.getResultSet();
			if (rs.next()) {
				Banque banque = new Banque();
				banque.setId(rs.getInt("id"));
				banque.setNom(rs.getString("nom"));
				return banque;
			} else {
				return null;
			}
		} catch (SQLException e) {
			System.out.println(selectBanque);
			System.out.println(e.getMessage());
			return null;
		}		
	}

}
