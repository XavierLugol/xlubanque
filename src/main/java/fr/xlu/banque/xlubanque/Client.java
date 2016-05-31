package fr.xlu.banque.xlubanque;

import java.util.ArrayList;
import java.util.List;

public class Client {
	private String nom;
	private String prenom;
	private String nomComplet;
	private int id;
	private int banque_id;
	private List<Compte> comptes = new ArrayList<>();
	
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
	public Client(String nom,String prenom) throws BanqueException{
		StringBuffer message = new StringBuffer();
		if (nom == null || nom.length() < 2) {
			message.append("Saisir au moins 2 caractères pour le nom"); 
		}
		if (prenom == null || prenom.length() < 2) {
			message.append("Saisir au moins 2 caractères pour le prénom"); 			
		}
		if (message.length() > 0) {
			throw new BanqueException("Saisir au moins 2 caractères pour le nom");
		}
		
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setNomComplet(nom+prenom);
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNomComplet() {
		return nomComplet;
	}
	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}
	public List<Compte> getComptes() {
		return comptes;
	}
	public void setComptes(List comptes) {
		this.comptes = comptes;
	}
	public void addCompte(Compte compte) {
		this.comptes.add(compte);
	}

	public void removeCompte(Compte compte) {
		this.comptes.remove(compte);
	}

	public int numberComptes() {
		return this.comptes.size();
	}
	
	@Override
	public String toString() {
		return "Client [nom=" + nom + ", prenom=" + prenom + "]";
	}
	
	// Return the next numéro de compte pour le client actuel
	public String nouveauNumero() {
		int maxNum = 0;
		for (Compte compte:this.getComptes()){
			int index = (this.getNomComplet().length());
			String num = compte.getNum().substring(index); 
			if (Integer.parseInt(num) > maxNum) {
				maxNum = Integer.parseInt(num);
			}
		}
		return new String(new Integer(maxNum + 1).toString());
		
	}

}
