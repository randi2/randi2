package de.randi2.junittests;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.StudieException;
import de.randi2.model.fachklassen.Studie;
import de.randi2.model.fachklassen.beans.StrataBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.StudienarmBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
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
	private StudieBean studieBean, studieVergleich;

	private Studie studie2, studie;

	/**
	 * Initialisiert den Logger.
	 * 
	 */
	@BeforeClass
	public static void log() {
		Log4jInit.initDebug();
	}

	/**
	 * StudieBean wird mit Daten gefüllt.
	 * 
	 * @throws Exception,
	 *             Fehler, wenn keine Instanz der Fachklasse Studie erzeugt
	 *             werden kann.
	 */
	@Before
	public void setUp() throws Exception {

		HashMap<Long, String> hash = new HashMap<Long, String>();
		long key = 1;

		hash.put(key, "Weiblich");
		studieBean = new StudieBean();
		studieBean.setId(12);
		studieBean.setName("Studiename");
		studieBean.setStatus(Studie.Status.BEENDET);
		studieBean
				.setBeschreibung("Dies ist eine Beschreibung zu einer Studie.");
		GregorianCalendar startDatum = new GregorianCalendar();
		startDatum.add(Calendar.MONTH, +2);
		GregorianCalendar endDatum = new GregorianCalendar();
		endDatum.add(Calendar.MONTH, +7);
		studieBean.setStudienZeitraum(startDatum, endDatum);
		studieBean.setStudienprotokollPfad("pfad");
		studieBean
				.setRandomisationseigenschaften("Randomisationseigenschaften");

		Vector<ZentrumBean> aTestZentrum = new Vector<ZentrumBean>();

		aTestZentrum
				.add(new ZentrumBean(
						12,
						"Instituition",
						"Abteilung",
						"ort",
						"64668",
						"Strasse",
						"12",
						1,
						"oe?jie3Yiesaoe?jie3Yiesaoe?jie3Yiesaoe?jie3Yiesaoe?jie3Yiesa414a",
						true));
		studieBean.setZentren(aTestZentrum);

		Vector<StrataBean> aTestStrata = new Vector<StrataBean>();

		aTestStrata.add(new StrataBean(12, hash));
		studieBean.setStrata(aTestStrata);

		Vector<StudienarmBean> aTestStudienarm = new Vector<StudienarmBean>();

		aTestStudienarm.add(new StudienarmBean(12, 34, Studie.Status.AKTIV,
				"Bezeichnung", "Beschreibung"));
		studieBean.setStudienarme(aTestStudienarm);

		// Ï
//		 studieBean = new StudieBean();
//		 studieBean.setFilter(true);
//		 studieBean.setId(122);
//		 studieBean
//		 .setBeschreibung("Dies ist eine Beschreibung zu einer Studie.");
//		 GregorianCalendar startDatum = new GregorianCalendar();
//		 startDatum.add(Calendar.MONTH, +2);
//		 GregorianCalendar endDatum = new GregorianCalendar();
//		 endDatum.add(Calendar.MONTH, +7);
//		 studieBean.setStudienZeitraum(startDatum, endDatum);
//		 studieBean.setStudienprotokollPfad("pfad");
//		 studieBean
//		 .setRandomisationseigenschaften("Randomisationseigenschaften");
//		 studieBean.setStatus(Studie.Status.AKTIV);
	}

	/**
	 * Testet, ob eine Instanz der Klasse Studie angelegt werden konnte.
	 * 
	 */
	@Test
	public void testStudie() {
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
		studieBean = null;
	}

	/**
	 * Testet StatusEnum
	 * 
	 */
	@Test
	public void testStudieParser() {

		Studie.Status testeStatus;
		try {
			Studie.Status.parseStatus("Test");

		} catch (Exception e) {
			assertEquals(StudieException.STATUS_UNGUELTIG, e.getMessage());
		}
		for (Studie.Status aStatus : Studie.Status.values()) {
			try {
				testeStatus = Studie.Status.parseStatus(aStatus.toString());
				assertEquals(testeStatus, aStatus);
				assertEquals(testeStatus.toString(), aStatus.toString());
			} catch (StudieException e) {
				fail(e.getMessage());
			}

		}

	}

	/**
	 * Testet, ob die Zentren der Studie zugeordnet werden koennen.
	 * 
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Studie#getZugehoerigeZentren()}.
	 */

	@Test
	public void testGetZugehoerigeZentren() {

		fail("Not yet implemented");

	}

	/**
	 * Testet, ob die Strata der Studie zugeordnet werden koennen.
	 * 
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Studie#getZugehoerigeStrata()}.
	 * 
	 */
	// TODO Klaerung Frank
	@Test
	public void testgetZugehoerigeStrata() {
		fail("Not yet implemented");
	}

	/**
	 * Testet, ob zur Id die richtige Studie gefunden und ausgelesen werden
	 * konnte.
	 * 
	 * Test method for {@link de.randi2.model.fachklassen.Studie#getStudie()}.
	 */

	@Test
	public void testGetStudie() {
		try {

			studieBean = DatenbankFactory.getAktuelleDBInstanz()
					.schreibenObjekt(studieBean);
			StudieBean vergleichStudieBean = Studie.getStudie(studieBean
					.getId());
			assertEquals(vergleichStudieBean, studieBean);
		} catch (DatenbankExceptions e) {
			fail(e.getMessage());

		}
	}

	/**
	 * Testet die Methode getStudieBean.
	 * 
	 */
	@Test
	public void testGetStudieBean() {
		Studie studie = new Studie(studieBean);
		assertEquals(studie.getStudieBean(), studieBean);
	}

	/**
	 * Ueberpruefung, ob eine Statistik nach den vorgegebenen Kriterien
	 * angezeigt wird.
	 * 
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Studie#anzeigenStatistik(int}.
	 * 
	 * 
	 */
	// TODO Klaerung Frank
	@Test
	public void testAnzeigenStatistik() {
		fail("Not yet implemented");
	}

	/**
	 * Ueberpruefung, ob Zentrum gesetzt wurde, um es einer Studie zuzuweisen.
	 * 
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Studie#zuweisenZentrum()}.
	 * 
	 * 
	 */

	@Test
	public void testZuweisenZentrum() {
		fail("Not yet implemented");
	}

	/**
	 * Testet, ob zwei nicht identische Studien auch als nicht identisch erkannt
	 * werden.
	 * 
	 * Test method for {@link de.randi2.model.fachklassen.Studie#equals(Object)}.
	 */
	@Test
	public void testEqualsFalse() {
		try {

			studieVergleich = new StudieBean();
			studieVergleich.setId(122);
			studieVergleich
					.setBeschreibung("Dies ist eine Beschreibung zu einer Studie.");
			GregorianCalendar startDatumVergleich = new GregorianCalendar();
			startDatumVergleich.add(Calendar.MONTH, +3);
			GregorianCalendar endDatumVergleich = new GregorianCalendar();
			endDatumVergleich.add(Calendar.MONTH, +7);
			studieVergleich.setStudienZeitraum(startDatumVergleich,
					endDatumVergleich);
			studieVergleich.setStudienprotokollPfad("pfad");
			studieVergleich.setStatus(Studie.Status.BEENDET);
			studieVergleich
					.setRandomisationseigenschaften("Randomisationseigenschaften");
			assertFalse(studieBean.equals(studieVergleich));
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Testet, ob zwei identische Studien auch als identisch erkannt werden.
	 * 
	 * Test method for {@link de.randi2.model.fachklassen.Studie#equals(Object)}.
	 */
	@Test
	public void testEqualsTrue() {
		try {
			assertTrue(studieBean.equals(studieBean));
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

}
