package fr.xlu.banque.xlubanque;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CompteTest {
	private Compte compte ;
	
@Before
	public void setup() {
		// Given
		try {
			Client xlu = new Client("Lugol","Xavier");
			Client xlu2 = new Client("Lugol2","Xavier2");
			compte = new Compte(xlu,0);
			compte = new Compte(xlu2,0);
		}
		catch (BanqueException be) {			
		}
	}

@Test
public void chercheNum() {
		// When
	String num = compte.getNum();
	//Then
	assertEquals(num,"2");
	} 

}
