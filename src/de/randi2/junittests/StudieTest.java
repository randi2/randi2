package de.randi2.junittests;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
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

		// TODO an neues Strata anpassen
		// aTestStrata.add(new StrataBean(12, hash));
		studieBean.setStrata(aTestStrata);

		Vector<StudienarmBean> aTestStudienarm = new Vector<StudienarmBean>();

		aTestStudienarm.add(new StudienarmBean(12, 34, Studie.Status.AKTIV,
				"Bezeichnung", "Beschreibung"));
		studieBean.setStudienarme(aTestStudienarm);
	}

	/**
	 * Testet, ob eine Instanz der Klasse Studie angelegt werden konnte.
	 * 
	 */
	@Test
	public void testStudie() {
		Studie studie = new Studie(studieBean);

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

		Vector<StudieBean> studieBean2 = new Vector<StudieBean>();

		try {
			DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(studieBean);

			studieBean2 = DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(
					studieBean);
			Vector<ZentrumBean> zentren = studieBean.getZentren();
			Iterator<ZentrumBean> itDb = zentren.iterator();
			Iterator<ZentrumBean> itaktuell = studieBean.getZentren()
					.iterator();
			assertEquals(zentren.size(), studieBean.getZentren().size());
			while (itDb.hasNext()) {
				while (itaktuell.hasNext()) {
					assertEquals(itDb.next(), itaktuell.next());
				}
			}
		} catch (DatenbankExceptions e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Testet, ob die Strata der Studie zugeordnet werden koennen.
	 * 
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Studie#getZugehoerigeStrata()}.
	 * 
	 */
	@Test
	public void testgetZugehoerigeStrata() {

		Vector<StudieBean> studieBeanStrata = new Vector<StudieBean>();

		try {
			DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(studieBean);

			studieBeanStrata = DatenbankFactory.getAktuelleDBInstanz()
					.suchenObjekt(studieBean);
			Vector<StrataBean> strata = studieBean.getStrata();
			Iterator<StrataBean> itDb = strata.iterator();
			Iterator<StrataBean> itaktuell = studieBean.getStrata().iterator();
			assertEquals(strata.size(), studieBean.getStrata().size());
			while (itDb.hasNext()) {
				while (itaktuell.hasNext()) {
					assertEquals(itDb.next(), itaktuell.next());
				}
			}
		} catch (DatenbankExceptions e) {
			fail(e.getMessage());
		}

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
		try {
			// Test 1. Neue Zentrum hinzufuegen--> Vektorlaenge +1
			Studie studie = new Studie(studieBean);
			ZentrumBean aZentrumBean = new ZentrumBean();
			int anzahlZentren = studie.getZugehoerigeZentren().size();

			aZentrumBean.setId(34);
			aZentrumBean.setInstitution("Institut");
			aZentrumBean.setAbteilung("AbteilungBla");
			aZentrumBean.setOrt("OrtZotzenbach");
			aZentrumBean.setPlz("68342");
			aZentrumBean.setStrasse("Ahornweg");
			aZentrumBean.setHausnr("23a");
			aZentrumBean.setAnsprechpartnerId(56);
			aZentrumBean.setPasswort("Hasdbasdasdasasdas");
			aZentrumBean.setIstAktiviert(true);
			//TODO Verbesserung notwendig
			//studie.zuweisenZentrum(aZentrumBean);

			assertEquals(studie.getZugehoerigeZentren().size(),
					anzahlZentren + 1);

			// Test 2. Zentrum versuchen in den Vector zu schreiben, obwohl es
			// schon vorhanden ist.
			// --> Vectorlaenge muss gleich bleiben
			anzahlZentren = studie.getZugehoerigeZentren().size();
			//TODO Verbeserung notwendig
			//studie.zuweisenZentrum(aZentrumBean);

			assertEquals(studie.getZugehoerigeZentren().size(), anzahlZentren);

		} catch (Exception e) {
			fail(e.getMessage());
		}
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
	// TODO spaeter Klaerung Frank
	@Test
	public void testAnzeigenStatistik() {
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
