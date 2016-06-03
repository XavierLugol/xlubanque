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
	 * 
	 * Ajoute la banque dont le nom est passé en paramètre dans la base de
	 * données
	 * 
	 * @author Xavier Lugol
	 * @param nom
	 *            : nom de la banque à créer
	 * @return : une instance de la banque créé ou trouvée en base.
	 * @throws BanqueException
	 *
	 */
	public Banque ajoutBanque(String nom) throws BanqueException {
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
		String insertBanque = "INSERT INTO banque.banque (nom)" + " VALUES (" + "'" + nom + "'" + ")";
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


	public Banque getBanque(String nom) {
		Connexion con = new Connexion();
		String selectBanque = "SELECT id,nom" + " FROM banque.banque WHERE " + " nom = " + "'" + nom + "'";
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
