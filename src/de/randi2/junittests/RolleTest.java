package de.randi2.junittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.randi2.model.fachklassen.Rolle;

/**
 * @author $eyma Yazgan [syazgan@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class RolleTest {

	// Objekte der Klasse Rolle
	private Rolle testSARolle;
	private Rolle testSLRolle;
	private Rolle testAdminRolle;
	private Rolle testSYSOPRolle;
	private Rolle testSTATRolle;
	
	/**
	 * Den Rolle-Objekten wird der Wert "null" zugewiesen.
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		testSARolle = null;
		testSLRolle = null;
		testAdminRolle = null;
		testSYSOPRolle = null;
		testSTATRolle = null;
	}

	/**
	 * Den Rolle-Objekten wird der Wert "null" zugewiesen.
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
		testSARolle = null;
		testSLRolle = null;
		testAdminRolle = null;
		testSYSOPRolle = null;
		testSTATRolle = null;
	}

	/**
	 * Testmethode fuer den Studienarzt.
	 */
	@Test
	public void testGetStudienarzt() {

		testSARolle = Rolle.getStudienarzt();
		if (testSARolle == null
				|| testSARolle.getRollenname().equals(Rolle.Rollen.STUDIENLEITER)				
				|| testSARolle.getRollenname().equals(Rolle.Rollen.ADMIN)
				|| testSARolle.getRollenname().equals(Rolle.Rollen.SYSOP)
				|| testSARolle.getRollenname().equals(Rolle.Rollen.STATISTIKER)) {
			fail("Zugewiesene Rolle nicht eindeutig!");
		}
		assertTrue(testSARolle.getRollenname().equals(Rolle.Rollen.STUDIENARZT));
	}

	/**
	 * Testmethode fuer den Studienleiter.
	 */
	@Test
	public void testGetStudienleiter() {
		
		testSLRolle = Rolle.getStudienleiter();
		if (testSLRolle == null
			|| testSLRolle.getRollenname().equals(Rolle.Rollen.STUDIENARZT)
			|| testSLRolle.getRollenname().equals(Rolle.Rollen.ADMIN)
			|| testSLRolle.getRollenname().equals(Rolle.Rollen.SYSOP)			
			|| testSLRolle.getRollenname().equals(Rolle.Rollen.STATISTIKER)) {
			fail("Zugewiesene Rolle nicht eindeutig!");
		}
		assertTrue(testSLRolle.getRollenname().equals(Rolle.Rollen.STUDIENLEITER));
	}
	
	/**
	 * Testmethode fuer den Admin.
	 */
	@Test
	public void testGetAdmin() {
		
		testAdminRolle = Rolle.getAdmin();
		if (testAdminRolle == null
			|| testAdminRolle.getRollenname().equals(Rolle.Rollen.STUDIENARZT)
			|| testAdminRolle.getRollenname().equals(Rolle.Rollen.STUDIENLEITER)
			|| testAdminRolle.getRollenname().equals(Rolle.Rollen.SYSOP)
			|| testAdminRolle.getRollenname().equals(Rolle.Rollen.STATISTIKER)) {
			fail("Zugewiesene Rolle nicht eindeutig!");
		}
		assertTrue(testAdminRolle.getRollenname().equals(Rolle.Rollen.ADMIN));
	}
	
	/**
	 * Testmethode fuer den Sysop.
	 */
	@Test
	public void testGetSysop() {
		
		testSYSOPRolle = Rolle.getSysop();
		if (testSYSOPRolle == null
			|| testSYSOPRolle.getRollenname().equals(Rolle.Rollen.STUDIENARZT)
			|| testSYSOPRolle.getRollenname().equals(Rolle.Rollen.STUDIENLEITER)			
			|| testSYSOPRolle.getRollenname().equals(Rolle.Rollen.ADMIN)
			|| testSYSOPRolle.getRollenname().equals(Rolle.Rollen.STATISTIKER)) {
			fail("Zugewiesene Rolle nicht eindeutig!");
		}
		assertTrue(testSYSOPRolle.getRollenname().equals(Rolle.Rollen.SYSOP));
	}
	
	/**
	 * Testmethode fuer den Statistiker.
	 */
	@Test
	public void testGetStatistiker() {
		
		testSTATRolle = Rolle.getStatistiker();
		if (testSTATRolle == null
			|| testSTATRolle.getRollenname().equals(Rolle.Rollen.STUDIENARZT)
			|| testSTATRolle.getRollenname().equals(Rolle.Rollen.STUDIENLEITER)			
			|| testSTATRolle.getRollenname().equals(Rolle.Rollen.ADMIN)
			|| testSTATRolle.getRollenname().equals(Rolle.Rollen.SYSOP)) {
			fail("Zugewiesene Rolle nicht eindeutig!");
		}
		assertTrue(testSTATRolle.getRollenname().equals(Rolle.Rollen.STATISTIKER));
	}

	/**
	 * Test method for {@link de.randi2.model.fachklassen.Rolle#getName()}.
	 */
	 @Test 
	 public void testGetName() {
		 
		 testGetStudienarzt();
		 assertTrue(testSARolle.getName().equals("STUDIENARZT"));
		 
		 testGetStudienleiter();
		 assertTrue(testSLRolle.getName().equals("STUDIENLEITER"));		 
		 
		 testGetAdmin();
		 assertTrue(testAdminRolle.getName().equals("ADMIN"));
		 
		 testGetSysop();
		 assertTrue(testSYSOPRolle.getName().equals("SYSOP"));
		 
		 testGetStatistiker();
		 assertTrue(testSTATRolle.getName().equals("STATISTIKER"));
	 }
	 
	/**
	 * Test method for {@link de.randi2.model.fachklassen.Rolle#getRollenname()}.
	 */
	@Test 
	public void testGetRollenname() { 
		
		testGetStudienarzt();
		testGetAdmin();
		testGetStudienleiter();
		testGetStatistiker();
		testGetSysop();
	}
	 
	/**
	 * Test method for {@link de.randi2.model.fachklassen.Rolle#toString()}.
	 */
	@Test
	public void testToString() {
		
		testGetStudienarzt();
		testSARolle.toString().equals("Rolle: STUDIENARZT");
		
		testGetStudienleiter();
		testSLRolle.toString().equals("Rolle: STUDIENLEITER");		
		
		testGetAdmin();
		testAdminRolle.toString().equals("Rolle: ADMIN");
		
		testGetSysop();
		testSYSOPRolle.toString().equals("Rolle: SYSOP");
		
		testGetStatistiker();
		testSTATRolle.toString().equals("Rolle: STATISTIKER");
	}

}
