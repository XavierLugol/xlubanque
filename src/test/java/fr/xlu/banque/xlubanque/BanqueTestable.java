package fr.xlu.banque.xlubanque;

public class BanqueTestable extends Banque{
	
	public boolean existeClient(String nom, String prenom) {
		return false;
	}
	
	public Client ajouteClient(Client client) {
		if (!this.existeClient(client.getNom(), client.getPrenom())) {
			this.getClients().add(client);
		}
		return client;
	}

	public boolean supprimeClient(Client client) {

		if (!(client == null)) {
			for (Compte compte : client.getComptes()) {
				this.getComptes().remove(compte);
			}
		this.getClients().remove(client);
		}
		return true;
	}

}
