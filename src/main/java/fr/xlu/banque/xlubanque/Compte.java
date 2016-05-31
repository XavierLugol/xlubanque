package fr.xlu.banque.xlubanque;

public class Compte {
	private String num;
	private double solde;
	private int id;
	private Client client;


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
