package de.randi2.junittests;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;
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
	private StudieBean studieBean,studieVergleich;
	


	/**
	 * Das zugehoerige Studie-Objekt.
	 */
	private Studie studie;

	/**
	 * Initialisiert den Logger. Bitte log4j.lcf.pat in log4j.lcf umbenennen und
	 * es funktioniert.
	 * 
	 */
	@BeforeClass
	public static void log() {
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
	@Test
	public void testStaticGetZugehoerigeZentren(){
		fail("ads");
	}

	/**
	 * 
	 * 
	 */
	@Test
	public void testGetZugehoerigeZentren() {

		fail("implementieren, sofort!! dhaehn");

	}
	@Test
	public void testgetZugehoerigeStrata(){
		fail("ads");
	}

	/**
	 * 
	 * 
	 */
	@Test
	public void testGetStrata() {

		fail("implementieren, sofort!! dhaehn");

	}

	/**
	 * 
	 * 
	 */
	@Test
	public void testGetStudie() {

		fail("implementieren, sofort!! dhaehn");

	}

	/**
	 * @throws StudieException 
	 * @throws Exception 
	 * 
	 */
	@Test
	public void testEqualsFalse() throws Exception {
		studieBean = new StudieBean();
		studieBean.setId(122);
		studieBean
				.setBeschreibung("Dies ist eine Beschreibung zu einer Studie.");
		GregorianCalendar startDatum = new GregorianCalendar();
		startDatum.add(Calendar.MONTH, +2);
		GregorianCalendar endDatum = new GregorianCalendar();
		endDatum.add(Calendar.MONTH, +7);
		studieBean.setStudienZeitraum(startDatum, endDatum);
		studieBean.setStudienprotokollPfad("pfad");
		studieBean.setRandomisationId(122);
		studieBean.setStatus(Studie.Status.AKTIV);
		
		studieVergleich = new StudieBean();
		studieVergleich.setId(122);
		studieVergleich
				.setBeschreibung("Dies ist eine Beschreibung zu einer Studie.");
		GregorianCalendar startDatumVergleich = new GregorianCalendar();
		startDatumVergleich.add(Calendar.MONTH, +2);
		GregorianCalendar endDatumVergleich = new GregorianCalendar();
		endDatumVergleich.add(Calendar.MONTH, +7);
		studieVergleich.setStudienZeitraum(startDatumVergleich, endDatumVergleich);
		studieVergleich.setStudienprotokollPfad("pfad");
		studieVergleich.setRandomisationId(122);
		studieVergleich.setStatus(Studie.Status.BEENDET);
		
		//TODO
		
		assertFalse(studieBean.equals(studieVergleich));

	}
	@Test
	public void testEqualsTrue()throws Exception{
		
		studieBean = new StudieBean();
		studieBean.setId(122);
		studieBean
				.setBeschreibung("Dies ist eine Beschreibung zu einer Studie.");
		GregorianCalendar startDatum = new GregorianCalendar();
		startDatum.add(Calendar.MONTH, +2);
		GregorianCalendar endDatum = new GregorianCalendar();
		endDatum.add(Calendar.MONTH, +7);
		studieBean.setStudienZeitraum(startDatum, endDatum);
		studieBean.setStudienprotokollPfad("pfad");
		studieBean.setRandomisationId(122);
		studieBean.setStatus(Studie.Status.AKTIV);
		
		
		assertTrue(studieBean.equals(studieBean));
		
		
	}

	

}
