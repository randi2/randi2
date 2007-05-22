package de.randi2.junittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.model.exceptions.StudieException;
import de.randi2.model.fachklassen.Studie;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.utility.Log4jInit;

/**
 * Testklasse fuer die Fachklasse Studie.
 * 
 * @author Susanne Friedrich [sufriedr@stud.hs-heilbronn.de]
 * @author Nadine Zwink [nzwink@stud.hs-heilbronn.de]
 * @version $Id: StudieTest.java 2507 2007-05-09 07:25:40Z lplotni $
 */
public class StudieTest {


	/**
	 * Das zugehoerige StudieBean-Objekt.
	 */
	private StudieBean studieBean;

	/**
	 * Das zugehoerige Studie-Objekt.
	 */
	private Studie studie;

	 /**
	     * Initialisiert den Logger. Bitte log4j.lcf.pat in log4j.lcf umbenennen und es funktioniert.
	     *
	     */
	    @BeforeClass
	    public static void log(){
		Log4jInit.initDebug();
	    }
	/**
	 * Method setUp() Erzeugt eine neue Instanz der Fachklasse Studie.
	 * 
	 * @throws Exception,
	 *             Fehler, wenn keine Instanz der Fachklasse Studie erzeugt
	 *             werden kann.
	 */
	@Before
	public void setUp() throws Exception {
		studie = new Studie(studieBean);
	}

	/**
	 * Method tearDown() Dem Studien-Objekt wird der Wert "null" zugewiesen.
	 * 
	 * @throws Exception,
	 *             wenn die Testklasse nicht beendet werden kann.
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for anzeigenStatistik() Test method for
	 * {@link de.randi2.model.fachklassen.Studie#anzeigenStatistik(int)}.
	 * 
	 * Ueberpruefung, ob eine Statistik nach den vorgegebenen Kriterien
	 * angezeigt wird.
	 */
	@Test
	public void testAnzeigenStatistik() {
		fail("Not yet implemented");
	}

	

	/**
	 * Test method for zuweisenZentrum() Test method for
	 * {@link de.randi2.model.fachklassen.Studie#zuweisenZentrum()}.
	 * 
	 * Ueberpruefung, ob Zentrum gesetzt wurde, um es einer Studie zuzuweisen.
	 */
	@Test
	public void testZuweisenZentrum() {
		// try{
		// studie.zuweisenZentrum(studie.getZentrum());
		// }
		// catch(Exception e){
		// fail("[]");
		// }
	}

	/**
	 * Test method for hinzufuegenPatient() Test method for
	 * {@link de.randi2.model.fachklassen.Studie#hinzufuegenPatient()}.
	 * 
	 * Ueberpruefung, ob ein Patient einer Studie zugewiesen wird.
	 */
	@Test
	public void testHinzufuegenPatient() {
		fail("Not yet implemented");
	}

	/**
	 * Testet StatusEnum
	 */
	@Test
	public void testStudieParser() {

		Studie.Status testeStatus;
		try {
			Studie.Status.parseStatus("Test");
			fail("[FEHLER]testStudieParser sollte eine Exception auslösen");
		} catch (Exception e) {

		}
		for (Studie.Status aStatus : Studie.Status.values()) {
			try {
				testeStatus = Studie.Status.parseStatus(aStatus.toString());
				assertEquals(testeStatus, aStatus);
				assertEquals(testeStatus.toString(), aStatus.toString());
			} catch (StudieException e) {
				fail("[FEHLER]testStudieParser");
			}

		}

	}
	
	
	/**
	 * 
	 *
	 */
	@Test
	 public void testGetZugehoerigeZentren(){
		 
		fail("implementieren, sofort!! dhaehn");
				
		
		
	 }

	
	/**
	 * 
	 *
	 */
	@Test
	public void testGetStrata(){
		
		fail("implementieren, sofort!! dhaehn");
				
		
		
	}
	
	
	/**
	 * 
	 *
	 */
	@Test
	public void testGet(){
		
		fail("implementieren, sofort!! dhaehn");
				
		
		
	}
	
	/**
	 * 
	 */
	@Test
	public void testEquals(){
		
		fail("implementieren, sofort!! dhaehn");
		
	}
}
