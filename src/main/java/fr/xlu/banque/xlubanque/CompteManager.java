package fr.xlu.banque.xlubanque;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CompteManager {
	
	public boolean ajoutCompte(Compte compte) {
		Connexion con = new Connexion();
//		Client client = this.getClient(compte.getClient().getNom(), compte.getClient().getPrenom());
		String insertCompte = "INSERT INTO banque.compte" 
				+ " (num,client_id,banque_id,solde)" 
				+ " VALUES (" + "'"	+ compte.getNum() + "'" 
				+ "," + compte.getClient_id()
				+ "," + compte.getBanque_id() 
				+ "," + compte.getSolde() + ")";
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

}
