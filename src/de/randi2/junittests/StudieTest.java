package de.randi2.junittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.randi2.model.exceptions.StudieException;
import de.randi2.model.fachklassen.Studie;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.RandomisationBean;
import de.randi2.model.fachklassen.beans.StudieBean;

/**
 * Testklasse fuer die Fachklasse Studie.
 * 
 * @author Susanne Friedrich [sufriedr@stud.hs-heilbronn.de]
 * @author Nadine Zwink [nzwink@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class StudieTest {

	/**
	 * Das zugehoerige RandomisationBean-Objekt.
	 */
	private RandomisationBean radomisationBean;

	/**
	 * Das zugehoerige StudieBean-Objekt.
	 */
	private StudieBean studieBean;

	/**
	 * Das zugehoerige Studie-Objekt.
	 */
	private Studie studie;

	/**
	 * Method setUp() Erzeugt eine neue Instanz der Fachklasse Studie.
	 * 
	 * @throws Exception,
	 *             Fehler, wenn keine Instanz der Fachklasse Studie erzeugt
	 *             werden kann.
	 */
	@Before
	public void setUp() throws Exception {
		studie = new Studie(studieBean, radomisationBean);
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
	 * Test method for getStudienprotokoll() Test method for
	 * {@link de.randi2.model.fachklassen.Studie#getStudienprotokoll()}.
	 * 
	 * Ueberpruefung, ob die URL des Studienprotokolls zurückgeliefert wird.
	 */
	@Test
	public void testGetStudienprotokoll() {
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
	


 

}
