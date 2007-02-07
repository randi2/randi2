/**
 * 
 */
package de.randi2.junittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.exceptions.ZentrumException;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.fachklassen.Zentrum;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;

/**
 * @author Tino Noack <tnoack@stud.hs-heilbronn.de>
 * @version $Id$
 */
public class ZentrumTest {
	
	private ZentrumBean testZB;
	private Zentrum testZ;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testZB = new ZentrumBean();
		testZ = new Zentrum(testZB);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		testZB = null;
		testZ = null;
	}

	/**
	 * Test method for {@link de.randi2.model.fachklassen.Zentrum#suchenZentrum(de.randi2.model.fachklassen.beans.ZentrumBean)}.
	 */
	@Test
	public void testSuchenZentrum() {
		
		try{
		assertEquals(Zentrum.suchenZentrum(testZB),
				DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(testZB));
		}catch(Exception e){
			e.printStackTrace();
			fail("Sollte keine Exception werfen.");
		}
	}
	

	/**
	 * Test method for {@link de.randi2.model.fachklassen.Zentrum#pruefenPasswort(java.lang.String)}.
	 */
	@Test
	public void testPruefenPasswortKorrekt() {
		try{
		boolean ergebnis = testZ.pruefenPasswort("$aBcDe12345%");
		assertTrue(ergebnis);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method for {@link de.randi2.model.fachklassen.Zentrum#pruefenPasswort(java.lang.String)}.
	 */
	@Test
	public void testPruefenPasswortFalsch() {
		
		boolean ergebnis = testZ.pruefenPasswort("falschesPW");
		ergebnis = testZ.pruefenPasswort("sf");
		assertFalse(ergebnis);
		ergebnis = testZ.pruefenPasswort("qwertzuihdjkjbgf");
		assertFalse(ergebnis);
		ergebnis = testZ.pruefenPasswort("12345678909876");
		assertFalse(ergebnis);
		ergebnis = testZ.pruefenPasswort("!§$%&/()=?!§$%");
		assertFalse(ergebnis);
		
	}

	/**
	 * Test method for {@link de.randi2.model.fachklassen.Zentrum#getZentrumBean()}.
	 */
	@Test
	public void testGetZentrumBean() {
		assertEquals(testZ.getZentrumBean(),testZB);
		
	}

}
