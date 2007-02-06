/**
 * 
 */
package de.randi2.junittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.randi2.model.fachklassen.Zentrum;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;

/**
 * @author Tino Noack [tino.noack@web.de]
 * @version $Id
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
	 * Test method for {@link de.randi2.model.fachklassen.Zentrum#Zentrum(de.randi2.model.fachklassen.beans.ZentrumBean)}.
	 */
	@Test
	public void testZentrum() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link de.randi2.model.fachklassen.Zentrum#suchenZentrum(de.randi2.model.fachklassen.beans.ZentrumBean)}.
	 */
	@Test
	public void testSuchenZentrum() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link de.randi2.model.fachklassen.Zentrum#pruefenPasswort(java.lang.String)}.
	 */
	@Test
	public void testPruefenPasswort() {
		
		boolean ergebnis = testZ.pruefenPasswort("test");
		assertTrue(ergebnis);
		
	}

	/**
	 * Test method for {@link de.randi2.model.fachklassen.Zentrum#getZentrumBean()}.
	 */
	@Test
	public void testGetZentrumBean() {
		fail("Not yet implemented");
	}

}
