package fr.xlu.banque.xlubanque;

import java.util.List;

public class EchangeBanques {
	
	// Transfert le montant du num1 sur la banque 1 vers le num2
	// de la banque 2
	public static boolean transfertMontantCompte(int banque_id1,int banque_id2, String num1, String num2, double montant) {
		Banque banque1 = new Banque();
		Banque banque2 = new Banque();
		banque1.setId(banque_id1);
		if (banque_id1 == banque_id2) {
			banque2 = banque1;
		}
		try {
			Client client1 = new Client("Lugol","Xavier");
			Client client2 = new Client("Dupond","Jean");
			Compte compte1 = new Compte(client1);
			Compte compte2 = new Compte(client2);
			client1.addCompte(compte1);
			client2.addCompte(compte2);
			banque1.ajouteClient(client1);
			banque2.ajouteClient(client2);
			List<Compte> comptes1 = banque1.getComptes();
			comptes1.add(compte1);
			banque1.setComptes(comptes1);
			List<Compte> comptes2 = banque2.getComptes();
			comptes2.add(compte2);
			banque2.setComptes(comptes2);
			compte1.debite(montant);
			compte2.credite(montant);	
		}
		catch (BanqueException be) {
			System.out.println(be.getMessage());		
		}
			
		return true;
	}

}
