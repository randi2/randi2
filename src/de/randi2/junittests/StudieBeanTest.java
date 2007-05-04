package de.randi2.junittests;

import static org.junit.Assert.*;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import de.randi2.model.exceptions.StudieException;
import de.randi2.model.fachklassen.beans.StudieBean;

/**
 * Testklasse fuer die Klasse StudieBean.
 * 
 * @author Susanne Friedrich [sufriedr@stud.hs-heilbronn.de]
 * @author Nadine Zwink [nzwink@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class StudieBeanTest {

	/**
	 * Das zugehörige StudieBean-Objekt.
	 */
	private StudieBean studieBean;

	/**
	 * GregorianCalender-Objekt für das Start- und Enddatum einer Studie.
	 */
	private GregorianCalendar endDatum, startDatum;

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
		try {
			studieBean.setBenutzerkonto(studieBean.getBenutzerkonto());
		} catch (Exception e) {
			fail("[FEHLER]testSetBenutzerkonto() sollte keine Exception auslösen");
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
		fail("Not yet implemented");
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
			studieBean.setName("XY");
			studieBean
					.setName("abcdefghijklmopqrstuvwxyzabcdefghijklmopqrstuvwxyzabcdefxyz");
		} catch (StudieException e) {
			fail("[testSetNameLaenge] Exception, wenn Studienname zu lang oder zu kurz ist.");
		}
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
	@Test
	public void testSetRandomisationseigenschaften() {
		fail("Not yet implemented");
	}

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
			studieBean.setEndDatum(startDatum);
			assertTrue(studieBean.getEndDatum().equals(startDatum));
			assertFalse((new GregorianCalendar(Locale.GERMANY))
					.after(startDatum));
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
	@Test
	public void testSetStudienarme() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for set Studienprotokoll_pfad() Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setStudienprotokoll_pfad(java.lang.String)}.
	 * 
	 * Ueberpruefung, ob der Pfad des Studienprotokolls gesetzt wurde.
	 */
	@Test
	public void testSetStudienprotokoll_pfad() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for setZentrum() Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setZentrum(de.randi2.model.fachklassen.beans.ZentrumBean)}.
	 * 
	 * Ueberpruefung, ob Zentrum gesetzt wurde. Wenn Fehler, soll
	 * StudieException geworfen werden.
	 */
	@Test
	public void testSetZentrum() {
		try {
			studieBean.setZentrum(studieBean.getZentrum());
		} catch (Exception e) {
			fail("[FEHLER]testSetZentrum() sollte keine Exception auslösen");
		}
	}

	/**
	 * Test method for setStatus() Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setStatus(int)}.
	 * 
	 * Ueberpruefung, ob der Status der Studie gesetzt wurde.
	 */
	@Test
	public void testSetStatus() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for setId() Test method for
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setId()}.
	 * 
	 * Ueberpruefung, ob die ID der Studie gesetzt wurde. Wenn Fehler, soll
	 * StudieException geworfen werden.
	 */
	@Test
	public void testSetId() {
		try {
			studieBean.setId(studieBean.getId());
		} catch (Exception e) {
			fail("[FEHLER]testSetId() sollte keine Exception auslösen");
		}
	}

	/**
	 * Test method for setBenutzerkontoId() Test method for
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setBenutzerkontoId()}.
	 * 
	 * Ueberpruefung, ob die ID des Benutzerkontos gesetzt wurde.
	 */
	@Test
	public void testSetBenutzerkontoId() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for setRandomisationId() Test method for
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setRandomisationId()}.
	 * 
	 * Ueberpruefung, ob die ID der Randomisation gesetzt wurde.
	 */
	@Test
	public void testSetRandomisationId() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for setZentumId() Test method for
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setZentrumId()}.
	 * 
	 * Ueberpruefung, ob die ID des Zentrums gesetzt wurde.
	 */
	@Test
	public void testSetZentrumId() {
		fail("Not yet implemented");
	}

	/**
	 * Testet StatusEnum
	 */
//	@Test
//	public void testStudieParser() {
//		StudieBean.Status testeStatus;
//		try {
//			StudieBean.Status.parseStatus("Test");
//			fail("[FEHLER]testStudieParser sollte eine Exception auslösen");
//		} catch (Exception e) {
//
//		}
//
//		for (StudieBean.Status aStatus : StudieBean.Status.values()) {
//			try {
//				testeStatus = StudieBean.Status.parseStatus(aStatus.toString());
//				assertEquals(testeStatus, aStatus);
//				assertEquals(testeStatus.toString(), aStatus.toString());
//			} catch (StudieException e) {
//				fail("[FEHLER]testStudieParser");
//			}
//
//		}
//
//	}

}
