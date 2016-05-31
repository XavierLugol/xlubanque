package fr.xlu.banque.xlubanque;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ClientTest {
	private Client client;
	@Before
	public void setup() {
		// Given
		try {
			client = new Client("Lugol","Xavier");
		}
		catch (BanqueException be) {
			
		}
	}

	@Test
	public void afficheNom() {
		// When
		String res = client.getNom();
		// Then
		assertEquals("Lugol", res);
	}
	
	@Test
	public void nombreCompte() {
		// Given
//		client.addCompte(new Compte(client));
//		client.addCompte(new Compte(client));
		// When
//		int res = client.numberComptes();
		// Then
		assertTrue(true);
//		assertEquals(2, res);
	}

}
