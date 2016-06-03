package fr.xlu.banque.xlubanque;

public class Compte {
	private String num;
	private double solde;
	private int id;
	private int client_id;
	private int banque_id;
	private Client client;


	public int getClient_id() {
		return client_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}

	public int getBanque_id() {
		return banque_id;
	}

	public void setBanque_id(int banque_id) {
		this.banque_id = banque_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Compte(Client client) {
//		super();
		new Compte(client,0d);
	}
	
	public Compte(Client client,double depot) {
//		super();
		this.setClient(client);
		this.setSolde(depot);
		this.setNum(client.getNomComplet() + client.nouveauNumero());
	}
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public double getSolde() {
		return solde;
	}
	public void setSolde(double solde) {
		this.solde = solde;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	public void credite(double montant){
		this.setSolde(montant + this.getSolde());
	}
	
	public void debite(double montant) throws BanqueException{
		if (this.getSolde() - montant < 0) {
			throw new BanqueException("Debit impossible. Compte insuffisamment approvisionné.");
		}
		this.setSolde(montant - this.getSolde());
	}

	@Override
	public String toString() {
		return "Compte [num=" + num + ", solde=" + solde + ", client=" + client + "]";
	}
	
//	private static String toSHA1(String chaine) {
//	    MessageDigest md = null;
//	    try {
//	    	byte[] mesBits = chaine.getBytes("UTF-8");
//	        md = MessageDigest.getInstance("SHA-1");
//		    return new String(md.digest(mesBits));
//	    }
//	    catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
//	        e.printStackTrace();
//	    } 
//	    return "";
//	}
	
}
