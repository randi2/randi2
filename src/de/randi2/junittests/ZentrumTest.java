package de.randi2.junittests;

import static org.junit.Assert.*;

import java.util.Vector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.exceptions.ZentrumException;
import de.randi2.model.fachklassen.Zentrum;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;

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

	@Before
	public void setUp() {
		testZB = new ZentrumBean();
		try {
			testZB.setId(1);
			testZB.setInstitution("HS Heilbronn");
			testZB.setAbteilung("Medizinische Infromatik");
			testZB.setAnsprechpartner(new PersonBean(1, 0, "Mueller", "Martin",
					PersonBean.Titel.DR, 'm', "mmueller@hs-heilbronn.de",
					"07131400500", "0176554422", null));
			testZB.setOrt("Heilbronn");
			testZB.setPlz("74081");
			testZB.setStrasse("Max Planck Strasse");
			testZB.setHausnr("1a");
			testZB.setPasswortKlartext("teig!!eeThu3");
		} catch (ZentrumException e1) {
			fail("Beim Erzeugen eines ZentrumBeans trat ein Fehler auf: "
					+ e1.getMessage());
		} catch (PersonException e2) {
			fail("Beim Erzeugen eines PersonBeans trat ein Fehler auf: "
					+ e2.getMessage());
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
	 * TODO Sobald die Funktionalitaet ein Zentrum zu speichern bzw. zu anlegen
	 * gegeben wird, muss diese Methode umgeschrieben werden. Da dann sollte man
	 * erstmal ein Zentrum erzeugen, speichern und dann danach suchen.
	 * 
	 * {@link de.randi2.model.fachklassen.Zentrum#suchenZentrum(de.randi2.model.fachklassen.beans.ZentrumBean)}.
	 */
	@Test
	public void testSuchenZentrum() {
		testZB = new ZentrumBean();
		testZB.setFilter(true);
		try {
			testZB.setInstitution("Institut1");
			testZB.setAbteilung("Abteilung1");
		} catch (ZentrumException e) {
			fail("Bei der ZentrumBean Klasse trat ein Fehler auf: "
					+ e.getMessage());
		}
		Vector<ZentrumBean> tempVec = new Vector<ZentrumBean>();
		try {
			tempVec = Zentrum.suchenZentrum(testZB);
		} catch (ZentrumException e) {
			e.printStackTrace();
		}
		/*
		 * Da wir wissen, dass sich zur Zeit in der Datenbank ein Zentrum
		 * mit gesuchten Eigenschaften befindet.
		 */
		assertTrue(tempVec.size() == 1);
		assertEquals(tempVec.elementAt(0).getInstitution(), "Institut1");
		assertEquals(tempVec.elementAt(0).getAbteilung(), "Abteilung1");
		/*
		 * Jetzt suchen wir nach einem nicht existierendem Zentrum - Ergebnis:
		 * kein Zentrum wird gefunden.
		 */
		testZB = new ZentrumBean();
		testZB.setFilter(true);
		try {
			testZB.setInstitution("Uni Heidelberg");
		} catch (ZentrumException e) {
			fail("Bei der ZentrumBean Klasse trat ein Fehler auf: "
					+ e.getMessage());
		}
		tempVec = null;
		try {
			tempVec = Zentrum.suchenZentrum(testZB);
		} catch (ZentrumException e) {
			e.printStackTrace();
		}
		assertTrue(tempVec.size() == 0);

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
}