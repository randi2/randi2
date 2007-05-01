package de.randi2.junittests;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import de.randi2.model.fachklassen.Studie;
import de.randi2.model.fachklassen.beans.RandomisationBean;
import de.randi2.model.fachklassen.beans.StudieBean;


/**
 * @author Susanne Friedrich [sufriedr@stud.hs-heilbronn.de]
 * @author Nadine Zwink [nzwink@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class StudieTest {
	
	private Studie studie;
	private RandomisationBean radomisationBean;
	private StudieBean studieBean;
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		studie = new Studie(studieBean, radomisationBean);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		studie = null;
	}

	/**
	 * Test method for {@link de.randi2.model.fachklassen.Studie#anzeigenStatistik(int)}.
	 */
	@Test
	public void testAnzeigenStatistik() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link de.randi2.model.fachklassen.Studie#getStudienprotokoll()}.
	 */
	@Test
	public void testGetStudienprotokoll() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for zuweisenZentrum()
	 * Test method for {@link de.randi2.model.fachklassen.Studie#zuweisenZentrum()}.
	 * 
	 * Ueberpruefung, ob Zentrum gesetzt wurde, um es einer Studie zuzuweisen.
	 * Wenn Fehler, soll StudieException geworfen werden.
	 */
	@Test
	public void testZuweisenZentrum() {
		try{
			studie.zuweisenZentrum(studie.getZentrum());
		}
		catch(Exception e){
			fail("[]");
		}
		
	}

	/**
	 * Test method for {@link de.randi2.model.fachklassen.Studie#konfiguriereRandomisation()}.
	 */
	@Test
	public void testKonfiguriereRandomisation() {
		fail("Not yet implemented");
	}
	
	@Test 
	
	public void testehinzufuegenPatient(){
		
		
	}


}
	