package de.randi2.junittests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.exceptions.StudieException;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.StudienarmBean;
import de.randi2.utility.Log4jInit;
import de.randi2.utility.NullKonstanten;

/**
 * Testklasse fuer die Klasse StudieBean.
 * 
 * @author Susanne Friedrich [sufriedr@stud.hs-heilbronn.de]
 * @author Nadine Zwink [nzwink@stud.hs-heilbronn.de]
 * @version $Id: StudieBeanTest.java 2505 2007-05-09 07:23:41Z lplotni $
 */
public class StudieBeanTest {

	/**
	 * Das zugehoerige Benutzerkonto-Objekt.
	 */
	private BenutzerkontoBean aBenutzer;

	/**
	 * Das zugehoerige StudieBean-Objekt.
	 */
	private StudieBean studieBean;

	/**
	 * Das zugehoerige Rolle-Objekt.
	 */
	private Rolle rolle;

	/**
	 * GregorianCalender-Objekt für das Start- und Enddatum einer Studie.
	 */
	private GregorianCalendar endDatum, startDatum, ersterLogin, letzterLogin;

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
	 * Method setUp() Erzeugt eine neue Instanz der Klasse StudieBean.
	 * 
	 * @throws Exception,
	 *             Fehler, wenn keine Instanz der Klasse StudieBean erzeugt
	 *             werden kann.
	 */
	@Before
	public void setUp() throws Exception {

		studieBean = new StudieBean();
		studieBean.setId(12);
		studieBean.setBeschreibung("abcdefg");

		String day = "15";
		int tag = Integer.parseInt(day);
		String month = "11";
		int monat = Integer.parseInt(month);
		String year = "2006";
		int jahr = Integer.parseInt(year);
		studieBean.setStartDatum(new GregorianCalendar(jahr, monat - 1, tag));
		studieBean.setEndDatum(new GregorianCalendar(jahr, monat - 1, tag));
		studieBean.setStudienprotokollPfad("pfad");
		studieBean.setRandomisationId(122);
	}

	/**
	 * Method tearDown() Dem StudieBean-Objekt wird der Wert "null" zugewiesen.
	 * 
	 * @throws Exception,
	 *             wenn die Testklasse nicht beendet werden kann.
	 */
	@After
	public void tearDown() throws Exception {
		studieBean = null;
	}

	/**
	 * Test method for setBenutzerkoto() Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setBenutzerkonto(de.randi2.model.fachklassen.beans.BenutzerkontoBean)}.
	 * 
	 * Ueberpruefung, ob Benutzerkonto gesetzt wurde. Wenn Fehler, soll
	 * StudieException geworfen werden.
	 */
	@Test
	public void testSetBenutzerkonto() {

		long benutzerId = 12;
		rolle = Rolle.getAdmin();
		letzterLogin = new GregorianCalendar(2006, 11, 1);
		ersterLogin = new GregorianCalendar(2006, 11, 1);

		try {
			aBenutzer = new BenutzerkontoBean(NullKonstanten.NULL_LONG,
					"Benutzername", "abcdefg", 12, rolle, 32, false,
					ersterLogin, letzterLogin);

			studieBean.setBenutzerkonto(aBenutzer);

			assertTrue(studieBean.getBenutzerkonto().equals(aBenutzer));
		} catch (BenutzerkontoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Test method for setBeschreibung Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setBeschreibung(java.lang.String)}.
	 * 
	 * Ueberpruefung, ob die Beschreibung einer Studie gesetzt wurde.
	 */
	@Test
	public void testSetBeschreibung() {
		// gueltige Beschreibung
		String[] testeStudieBezeichnung = { "", "Studie Bezeichnung",
				"abscadaskdjaslkjdklasjdklasjd" };

		for (int i = 0; i < testeStudieBezeichnung.length; i++) {
			studieBean.setBeschreibung(testeStudieBezeichnung[i]);
			assertTrue(studieBean.getBeschreibung().equals(
					testeStudieBezeichnung[i]));
		}
	}

	/**
	 * Test method for setEndDatum() Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setEndDatum(java.util.GregorianCalendar)}.
	 * 
	 * Ueberpruefung des Enddatums einer Studie. Wenn Fehler (Datum befindet
	 * sich in der Zukunft), soll StudieException geworfen werden.
	 */
	@Test
	public void testSetEndDatum() {
		try {
			String day = "1";
			int tag = Integer.parseInt(day);
			String month = "2";
			int monat = Integer.parseInt(month);
			String year = "2006";
			int jahr = Integer.parseInt(year);
			endDatum = new GregorianCalendar(jahr, monat - 1, tag);
			studieBean.setEndDatum(endDatum);
			assertTrue(studieBean.getEndDatum().equals(endDatum));
			assertFalse((new GregorianCalendar(Locale.GERMANY))
					.before(endDatum));
		} catch (Exception e) {
			fail("[testSetEndDatum]Exception, wenn Zeit des Enddatums in der Zukunft liegt.");
		}
	}

	/**
	 * Test method for setName() Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setName(java.lang.String)}.
	 * 
	 * Ueberpruefung des Studiennames. Wenn kein Studienname vorhanden ist, wird
	 * Fehler in der StudieException geworfen.
	 */
	@Test
	public void testSetNameLeer() {
		try {
			studieBean.setFilter(true);
			studieBean.setName("");
		} catch (Exception e) {
			fail("[testSetNameLeer] Exception, wenn Studienname fehlt.");
		}
	}

	/**
	 * Test method for setName() Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setName(java.lang.String)}.
	 * 
	 * Ueberpruefung der Laenge des Studiennames. Wenn Studienname zu kurz
	 * (weniger als 3 Zeichen) oder zu lang (mehr als 50 Zeichen) ist, wird
	 * Fehler in der StudieException geworfen.
	 */
	@Test
	public void testSetNameLaenge() {
		try {
			studieBean.setFilter(true);
			studieBean.setName("X");
			studieBean.setName("rstxyzabcdefghijklmopqrstuvwxyzabcdefxyz"
					+ "asd");
		} catch (StudieException e) {
			fail("[testSetNameLaenge] Exception, wenn Studienname zu lang oder zu kurz ist.");
		}
	}
	/***
	 * 
	 * Test method for setName() Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setName(java.lang.String)}.
	 * 
	 * @throws StudieException
	 */
	@Test(expected = StudieException.class)
	public void testSetNameZuKurz() throws StudieException {
		studieBean.setName("a");
		assertTrue(studieBean.getName().equals("a"));
	}

	/**
	 * Test method for setName() Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setName(java.lang.String)}.
	 * 
	 * Ueberpruefung des korrekten Studiennames. Wenn Studienname richtig, darf
	 * kein Fehler in der StudieException geworfen werden.
	 */
	@Test
	public void testSetNameOk() {
		try {
			studieBean.setFilter(false);
			studieBean.setName("Studienname");
		} catch (StudieException e) {
			fail("[testSetNameOk] Es darf keine Exception geworfen werden.");
		}
	}

	/**
	 * Test method for setRandomisationseigenschaften() Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setRandomisationseigenschaften(de.randi2.model.fachklassen.beans.RandomisationBean)}.
	 * 
	 * Ueberpruefung der randomisationseigenschaften einer Studie.
	 */
	//TODO
	// @Test
	// public void testSetRandomisationseigenschaften() {
	// fail("Not yet implemented");
	// }
	/**
	 * Test method for setStartDatum() Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setStartDatum(java.util.GregorianCalendar)}.
	 * 
	 * Ueberpruefung Startdatum. Wenn Fehler (Datum befindet sich in der
	 * Vergangenheit), soll StudieException geworfen werden.
	 */
	@Test
	public void testSetStartDatum() {
		try {
			String day = "1";
			int tag = Integer.parseInt(day);
			String month = "2";
			int monat = Integer.parseInt(month);
			String year = "2006";
			int jahr = Integer.parseInt(year);
			startDatum = new GregorianCalendar(jahr, monat - 1, tag);
			studieBean.setStartDatum(startDatum);
			assertTrue(studieBean.getStartDatum().equals(startDatum));

		} catch (Exception e) {
			fail("[testSetStartDatum]Exception, wenn Zeit des Enddatums in der Zukunft liegt.");
		}

	}

	/**
	 * Test method for setStudienarme() Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setStudienarme(java.util.Vector)}.
	 * 
	 * Ueberpruefung Anzahl der Studienarme.
	 */
	//TODO
	// @Test
	// public void testSetStudienarme() {
	// fail("Not yet implemented");
	// }
	/**
	 * Test method for set Studienprotokoll_pfad() Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setStudienprotokoll_pfad(java.lang.String)}.
	 * 
	 * Ueberpruefung, ob der Pfad des Studienprotokolls gesetzt wurde.
	 */
	 @Test
	 public void testSetStudienprotokoll_pfad() {
		 try {
				studieBean.setStudienprotokollPfad(studieBean.getStudienprotokollpfad());
			} catch (Exception e) {
				fail("[FEHLER]testSetStudienprotokoll_pfad() sollte keine Exception auslosen");
			}
	 
	 }
	/**
	 * Test method for setStatus() Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setStatus(int)}.
	 * 
	 * Ueberpruefung, ob der Status der Studie gesetzt wurde.
	 */
	// @Test
	// public void testSetStatus() {
	// fail("Not yet implemented");
	// }
	/**
	 * Test method for setId() Test method for
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setId()}.
	 * 
	 * Ueberpruefung, ob die ID der Studie gesetzt wurde. Wenn Fehler, soll
	 * StudieException geworfen werden.
	 */
	@Test
	public void testSetId() {
		try {
		studieBean.setId(110);
		assertTrue(studieBean.getId()==110);
		} catch (Exception e) {
			fail("[FEHLER]testSetId() sollte keine Exception ausloesen");
		}
	}
	

	/**
	 * Test method for setBenutzerkontoId() Test method for
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setBenutzerkontoId()}.
	 * 
	 * Ueberpruefung, ob die ID des Benutzerkontos gesetzt wurde.
	 */
	@Test
	public void testSetBenutzerkontoId() {
		try {
			studieBean.setBenutzerkontoId(134);
			assertTrue(studieBean.getBenutzerkontoId()==134);
		} catch (Exception e) {
			fail("[FEHLER]testSetBenutzerkontoId() sollte keine Exception ausloesen");
		}
	}

	/**
	 * Test method for setRandomisationId() Test method for
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setRandomisationId()}.
	 * 
	 * Ueberpruefung, ob die ID der Randomisation gesetzt wurde.
	 */
	@Test
	public void testSetRandomisationId() {
		try {
			studieBean.setRandomisationId(145);
			assertTrue(studieBean.getRandomisationId()==145);
		} catch (Exception e) {
			fail("[FEHLER]testSetRandomisationId() sollte keine Exception ausloesen");
		}

	}


	/**
	 * Testet, ob die toString() Methode einen String zurueckgibt.
	 * 
	 * TestMethode fuer
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#toString()}.
	 * 
	 */
	@Test
	public void testToString() {
		assertFalse(studieBean.toString().equals(null));
	}

	/**
	 * Testet, ob zwei identische Studien auch als identisch erkannt werden.
	 * 
	 * TestMethode fuer
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#equals(Object)}.
	 * 
	 */
	@Test
	public void testEquals() {

		assertFalse(studieBean.equals(null));

		StudieBean beanZuvergleichen = studieBean;
		assertTrue(studieBean.equals(beanZuvergleichen));

	}

}
