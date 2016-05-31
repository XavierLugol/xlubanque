package fr.xlu.banque.xlubanque;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

public class ConnexionTest {
	
@Test
	public void testConnexion() {
		Connexion con = new Connexion();
		Connection connec = con.getConnexion();
		con.insertBanque();
		con.close();
		assertTrue(true);
		
	}

}
