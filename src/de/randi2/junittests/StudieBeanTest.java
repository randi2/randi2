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
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.StudieException;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.Studie;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.StrataBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.utility.Log4jInit;

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
	private StudieBean studieBean, studieVergleich;

	/**
	 * Das zugehoerige Rolle-Objekt.
	 */
	private Rolle rolle;

	/**
	 * GregorianCalender-Objekt für den ersten und letzten Login einer Studie.
	 */
	private GregorianCalendar ersterLogin, letzterLogin;

	/**
	 * Initialisiert den Logger.
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
		studieBean
				.setBeschreibung("Dies ist eine Beschreibung zu einer Studie.");
		GregorianCalendar startDatum = new GregorianCalendar();
		startDatum.add(Calendar.MONTH, +2);
		GregorianCalendar endDatum = new GregorianCalendar();
		endDatum.add(Calendar.MONTH, +7);
		studieBean.setStudienZeitraum(startDatum, endDatum);
		studieBean.setStudienprotokollPfad("pfad");
		studieBean.setRandomisationId(122);
		studieBean
				.setRandomisationseigenschaften("Randomisationseigenschaften");
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
	 * 
	 * Ueberpruefung, ob Benutzerkonto gesetzt wurde. Wenn Fehler, soll
	 * Exception geworfen werden.
	 * 
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setBenutzerkonto(de.randi2.model.fachklassen.beans.BenutzerkontoBean)}.
	 * 
	 */
	@Test
	public void testSetBenutzerkonto() {
		rolle = Rolle.getAdmin();
		letzterLogin = new GregorianCalendar(2006, 11, 1);
		ersterLogin = new GregorianCalendar(2006, 11, 1);

		try {
			aBenutzer = new BenutzerkontoBean("benutzername", "Hassswsdasdasd",
					new PersonBean(12, 1, "Nahme", "Vorname",
							PersonBean.Titel.DR, 'm', "mail@susanne.de",
							"06231987656", "06231987656", "06231987656"));

			studieBean.setBenutzerkonto(aBenutzer);
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Ueberpruefung, ob die Beschreibung einer Studie gesetzt wurde und richtig
	 * ausgegeben wurde.
	 * 
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setBeschreibung(java.lang.String)}.
	 * 
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
		assertFalse(studieBean.getBeschreibung().equals("test"));
	}

	/**
	 * 
	 * Ueberpruefung des Studiennames. Wenn kein Studienname vorhanden ist, wird
	 * Fehler in der StudieException geworfen.
	 * 
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setNameLeer(java.lang.String)}.
	 * 
	 */
	@Test
	public void testSetNameLeer() {
		try {

			studieBean.setName("");
		} catch (Exception e) {
			assertEquals(StudieException.STUDIENNAME_FEHLT, e.getMessage());
		}
	}

	/**
	 * Ueberpruefung der Laenge des Studiennames. Wenn Studienname zu lang (mehr
	 * als 50 Zeichen) ist, wird Fehler in der StudieException geworfen.
	 * 
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setName(java.lang.String)}.
	 * 
	 */
	@Test
	public void testSetNameLaenge() {
		try {

			studieBean
					.setName("rstxyasasasaszabcdefghijklmopqrstuvwxyzabcdefxyzaasdasdsadad");
		} catch (StudieException e) {

			assertEquals(StudieException.STUDIENNAME_UNGUELTIG, e.getMessage());
		}
	}

	/**
	 * Ueberpruefung der Laenge des Studiennames. Wenn Studienname zu kurz
	 * (weniger als 3 Zeichen) ist, wird Fehler in der StudieException geworfen.
	 * 
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setName(java.lang.String)}.
	 * 
	 * @throws StudieException,
	 *             wenn Test auf zu kurzen Namen nicht funktioniert.
	 */
	@Test(expected = StudieException.class)
	public void testSetNameZuKurz() throws StudieException {
		studieBean.setName("a");

	}

	/**
	 * Ueberpruefung des korrekten Studiennames. Wenn Studienname richtig, darf
	 * kein Fehler in der StudieException geworfen werden.
	 * 
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setName(java.lang.String)}.
	 * 
	 */
	@Test
	public void testSetNameOk() {
		try {
			studieBean.setFilter(false);
			studieBean.setName("Studienname");
		} catch (StudieException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Ueberpruefung des korrekten Studienzeitraumes
	 * 
	 */
	@Test
	public void testStudienzeitraum() {
		// 1. Startdatum in Vergangenheit und Enddatum in Zukunft
		GregorianCalendar startDatum = new GregorianCalendar();
		startDatum.add(Calendar.MONTH, -2);
		GregorianCalendar endDatum = new GregorianCalendar();
		endDatum.add(Calendar.MONTH, +7);
		try {
			studieBean.setStudienZeitraum(startDatum, endDatum);
		} catch (StudieException e) {
			assertEquals(StudieException.DATUM_FEHLER, e.getMessage());
		}
		// 2. Enddatum in Vergangenheit und Startdatum in Zukunft
		startDatum.add(Calendar.MONTH, +4);
		endDatum.add(Calendar.MONTH, -10);
		try {
			studieBean.setStudienZeitraum(startDatum, endDatum);
		} catch (StudieException e) {
			assertEquals(StudieException.DATUM_FEHLER, e.getMessage());
		}
		// 3. sowohl Startdatum als auch Enddatum in Zukunft, aber Startdatum
		// nach Enddatum
		startDatum.add(Calendar.MONTH, +1);
		endDatum.add(Calendar.MONTH, +4);
		try {
			studieBean.setStudienZeitraum(startDatum, endDatum);
		} catch (StudieException e) {
			assertEquals(StudieException.DATUM_FEHLER, e.getMessage());
		}
		// 4. alles okay
		endDatum.add(Calendar.MONTH, +4);
		try {
			studieBean.setStudienZeitraum(startDatum, endDatum);
		} catch (StudieException e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Ueberpruefung, ob der Pfad des Studienprotokolls gesetzt wurde.
	 * 
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setStudienprotokoll_pfad(java.lang.String)}.
	 * 
	 * 
	 */
	@Test
	public void testSetStudienprotokoll_pfad() {

		try {
			studieBean.setStudienprotokollPfad(studieBean
					.getStudienprotokollpfad());
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Wenn kein Status vorhanden ist, wird Fehler in der StudieException
	 * geworfen. Die Methode prueft, ob richtige Exception geworfen wird.
	 * 
	 * 
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setStatus(int)}.
	 * 
	 * 
	 */
	@Test
	public void testSetStatus() {
		try {

			Studie.Status status = null;
		} catch (Exception e) {
			assertEquals(StudieException.STATUSFEHLER, e.getMessage());
		}
	}

	/**
	 * Ueberpruefung, ob die ID der Studie gesetzt wurde.
	 * 
	 * Test method for
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setId()}.
	 * 
	 * 
	 */
	@Test
	public void testSetId() {
		try {
			studieBean.setId(110);
			assertTrue(studieBean.getId() == 110);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Ueberpruefung, ob die ID des Benutzerkontos gesetzt wurde.
	 * 
	 * Test method for
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setBenutzerkontoId()}.
	 * 
	 * 
	 */
	@Test
	public void testSetBenutzerkontoId() {
		try {
			studieBean.setBenutzerkontoId(134);
			assertTrue(studieBean.getBenutzerkontoId() == 134);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Ueberpruefung, ob die ID der Randomisation gesetzt wurde.
	 * 
	 * Test method for
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setRandomisationId()}.
	 * 
	 * 
	 */
	@Test
	public void testSetRandomisationId() {
		try {
			studieBean.setRandomisationId(145);
			assertTrue(studieBean.getRandomisationId() == 145);
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Testet, ob zwei nicht identische Studien auch als nicht identisch erkannt
	 * werden.
	 * 
	 * TestMethode fuer
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#equals(Object)}.
	 * 
	 */
	@Test
	public void testEqualsFalse() {
		try {
			studieVergleich = new StudieBean();
			studieVergleich.setId(13);
			studieVergleich
					.setBeschreibung("Dies ist eine Beschreibung der zu vergleichenden Studie.");
			GregorianCalendar startVergleich = new GregorianCalendar();
			startVergleich.add(Calendar.MONTH, +1);
			GregorianCalendar endVergleich = new GregorianCalendar();
			endVergleich.add(Calendar.MONTH, +6);
			studieVergleich.setStudienZeitraum(startVergleich, endVergleich);
			studieVergleich.setStudienprotokollPfad("pfad Vergleich");
			studieVergleich.setRandomisationId(124);
			studieVergleich
					.setRandomisationseigenschaften("Randomisationseigenschaften Vergleich");
			assertFalse(studieBean.equals(studieVergleich));
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Testet, ob zwei identische Studien auch als identisch erkannt werden.
	 * 
	 * TestMethode fuer
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#equals(Object)}.
	 * 
	 */
	@Test
	public void testEqualsTrue() {

		try {
			assertTrue(studieBean.equals(studieBean));
		} catch (Exception e) {
			fail(e.getMessage());

		}

	}

	/**
	 * Testet, ob das Zentrum hinzugefuegt wurde.
	 * 
	 * TestMethode fuer
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setZentren(Vector)}.
	 * 
	 */
	public void testsetZentren() {
		Vector<ZentrumBean> zentrum = new Vector<ZentrumBean>();

		for (int i = 0; i < 10; i++) {

			try {
				zentrum.add(new ZentrumBean(i, "Instituition", "Abteilung",
						"ort", "plz", "Strasse", "12", i, "PasswortHashsdf",
						true));
			} catch (Exception e) {
				fail(e.getMessage());
			}
		}
		try {
			studieBean.setZentren(zentrum);
			assertTrue(studieBean.getZentren() == zentrum);
			assertTrue(studieBean.getAnzahlZentren() == zentrum.size());
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Testet, ob Strata hinzugefuegt wurde.
	 * 
	 * TestMethode fuer
	 * {@link de.randi2.model.fachklassen.beans.StudieBean#setStrata(Vector)}.
	 * 
	 */
	@Test
	public void testsetStrata() {
		Vector<StrataBean> strata = new Vector<StrataBean>();

		HashMap<Long, String> hash = new HashMap<Long, String>();
		long key = 1;

		hash.put(key, "Weiblich");

		for (long i = 1; i < 10; i++) {

			try {

				strata.add(new StrataBean(i, hash));

			} catch (Exception e) {

				fail(e.getMessage());
			}

		}

		try {
			studieBean.setStrata(strata);
			assertTrue(studieBean.getStrata() == strata);
			assertTrue(studieBean.getAnzahlStrata() == strata.size());
		} catch (StudieException e) {

			fail(e.getMessage());

		} catch (DatenbankFehlerException e) {
			fail(e.getMessage());
		}

	}

}
