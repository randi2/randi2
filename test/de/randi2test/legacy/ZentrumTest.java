package de.randi2test.legacy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.ZentrumException;
import de.randi2.model.fachklassen.Zentrum;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.utility.KryptoUtil;
import de.randi2.utility.Log4jInit;

/**
 * Die Test-Klasse fuer die Klasse Zentrum.
 * 
 * @author Tino Noack [tnoack@stud.hs-heilbronn.de]
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @version $Id: ZentrumTest.java 2426 2007-05-06 17:34:32Z twillert $
 */
public class ZentrumTest {

	private ZentrumBean testZB;

	private Zentrum testZ;

	/**
	 * Initialisiert den Logger. Bitte log4j.lcf.pat in log4j.lcf umbenennen und
	 * es funktioniert.
	 * 
	 */
	@BeforeClass
	public static void log() {
		Log4jInit.initDebug();
	}

	@Before
	public void setUp() {
		testZB = new ZentrumBean();
		testZB.setFilter(true);
		try {
			testZB.setInstitution("HS Heilbronn");
			testZB.setAbteilung("Medizinische Informatik");
			testZB.setAnsprechpartnerId(3);
			testZB.setOrt("Heilbronn");
			testZB.setPlz("74081");
			testZB.setStrasse("Max Planck Strasse");
			testZB.setHausnr("1a");
			testZB.setPasswortKlartext("teig!!eeThu3");
			testZB.setIstAktiviert(true);
		} catch (ZentrumException e1) {
			fail("Beim Erzeugen eines ZentrumBeans trat ein Fehler auf: "
					+ e1.getMessage());
		}
	}

	@After
	public void tearDown() {
		testZB = null;
	}

	/**
	 * Test Methode, die den Konstruktor der Klasse Zentrum testet. Sie erzeugt
	 * eine Instanz dieser Klasse, in dem sie auch ein ZentrumBean an den
	 * Konstruktor uebergibt. Danach versucht sie dieses Bean zu holen, in dem
	 * sie das neue Objekt anspricht und die getZentrumBean() Methode aufruft.
	 */
	@Test
	public void testErzeugenZentrum() {
		testZ = new Zentrum(testZB);
		assertEquals(testZB, testZ.getZentrumBean());
	}

	/**
	 * Test Methode fuer suchenZentrum(). Sie sucht nach einem ZentrumBean(mit
	 * Filter=true).
	 * 
	 * {@link de.randi2.model.fachklassen.Zentrum#suchenZentrum(de.randi2.model.fachklassen.beans.ZentrumBean)}.
	 */
	@Test
	public void testSuchenZentrum() {
		try {
			// Speichern in der Datenbank
			DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(testZB);
			// Initialisierung des Vektors
			Vector<ZentrumBean> tempVec = new Vector<ZentrumBean>();
			// Holen aus der Datenbank
			tempVec = Zentrum.suchenZentrum(testZB);
			/*
			 * Da wir wissen, dass sich zur Zeit in der Datenbank ein Zentrum
			 * mit gesuchten Eigenschaften befindet.
			 */
			for(int i = 0; i < tempVec.size(); i++) {
				assertEquals(tempVec.elementAt(i).getInstitution(), "HS Heilbronn");
				assertEquals(tempVec.elementAt(i).getAbteilung(), "Medizinische Informatik");
				assertTrue(tempVec.elementAt(i).getAnsprechpartnerId() == 3);
				assertEquals(tempVec.elementAt(i).getOrt(), "Heilbronn");
				assertEquals(tempVec.elementAt(i).getPlz(), "74081");
				assertEquals(tempVec.elementAt(i).getStrasse(), "Max Planck Strasse");
				assertEquals(tempVec.elementAt(i).getHausnr(), "1a");
				assertEquals(tempVec.elementAt(i).getPasswort(), KryptoUtil.getInstance().hashPasswort("teig!!eeThu3"));
				assertEquals(tempVec.elementAt(i).getIstAktiviert(), true);
			}
			/*
			 * Jetzt suchen wir nach einem nicht existierendem Zentrum -
			 * Ergebnis: kein Zentrum wird gefunden.
			 */
			ZentrumBean atestZB = new ZentrumBean();
			atestZB.setFilter(true);
			try {
				atestZB.setInstitution("Uni Heidelberg");
			} catch (ZentrumException e) {
				fail("Bei der ZentrumBean Klasse trat ein Fehler auf: "
						+ e.getMessage());
			}
			tempVec = new Vector<ZentrumBean>();
			// wird nicht gefunden, da nicht geschrieben wurde
			tempVec = Zentrum.suchenZentrum(atestZB);
			assertTrue(tempVec.size() == 0);
		} catch (DatenbankExceptions e) {
			fail("In der Datenbank trat ein Fehler auf: " + e.getMessage());
		}
	}

	/**
	 * Test Methode fuer prufenPasswort()
	 * {@link de.randi2.model.fachklassen.Zentrum#pruefenPasswort(java.lang.String)}.
	 */
	@Test
	public void testPruefen() {
		testZ = new Zentrum(testZB);
		assertTrue(testZ.pruefenPasswort("teig!!eeThu3"));
		assertFalse(testZ.pruefenPasswort("falschesPW"));
	}

	/**
	 * Test Methode fuer getZentrumBean()
	 * {@link de.randi2.model.fachklassen.Zentrum#getZentrumBean()}.
	 */
	@Test
	public void testGetZentrumBean() {
		testZ = new Zentrum(testZB);
		assertTrue(testZ.getZentrumBean().equals(testZB));
	}

	/**
	 * Test Methode fuer getZentrum(long zentrumId)
	 * {@link de.randi2.model.fachklassen.Zentrum#getZentrum(long)}.
	 */
	@Test
	public void testGetZentrum() {
		try {
			// schreiben in die Datenbank
			testZB = DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(
					testZB);
			ZentrumBean vergleichZB = Zentrum.getZentrum(testZB.getId());
			assertEquals(vergleichZB, testZB);
		} catch (DatenbankExceptions e) {
			fail("In der Datenbank trat ein Fehler auf: " + e.getMessage());
		}
	}
}