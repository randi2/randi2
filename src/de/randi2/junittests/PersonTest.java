package de.randi2.junittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.fachklassen.Person;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.utility.Log4jInit;

/**
 * Die Test-Klasse fuer die Klasse Person.
 * 
 * @author Thomas Willert [twillert@stud.hs-heilbronn.de]
 * @version $Id: PersonTest.java 2426 2007-05-06 17:34:32Z twillert $
 */
public class PersonTest {

	private PersonBean testPB;

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
		testPB = new PersonBean();
		try {
			testPB.setId(1);
			testPB.setStellvertreterId(1);
			testPB.setNachname("Hitzelmeier");
			testPB.setVorname("Antonius");
			testPB.setTitel(PersonBean.Titel.DR);
			testPB.setGeschlecht('m');
			testPB.setEmail("antonhitzel@gmx.net");
			testPB.setTelefonnummer("04738-4839278");
			testPB.setHandynummer("017394837263");
			testPB.setFax("05748/38291");
		} catch (PersonException e) {
			fail("Beim Erzeugen eines PersonBeans trat ein Fehler auf: "
					+ e.getMessage());
		}
	}

	@After
	public void tearDown() {
		testPB = null;
	}

	/**
	 * Test Methode fuer suchen(PersonBean gesuchtesBean). Sie sucht nach einem
	 * PersonBean(mit Filter=true).
	 * 
	 * {@link de.randi2.model.fachklassen.Person#suchenPerson(de.randi2.model.fachklassen.beans.PersonBean)}.
	 */
	@Test
	public void testSuchenPerson() {
		testPB = new PersonBean();
		testPB.setFilter(true);
		try {
			testPB.setNachname("Obdenhoevel");
			testPB.setVorname("Oliver");
			testPB.setGeschlecht('m');
			testPB.setEmail("blablabla@web.de");
			testPB.setTelefonnummer("072383984738");
		} catch (PersonException e) {
			fail("Bei der PersonBean Klasse trat ein Fehler auf: "
					+ e.getMessage());
		}
		try {
			// Speichern in der Datenbank
			DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(testPB);
			// Initialisierung eines Vektors
			Vector<PersonBean> tempVec = new Vector<PersonBean>();
			// Holen aus der Datenbank
			tempVec = Person.suchenPerson(testPB);
			/*
			 * Da wir wissen, dass sich zur Zeit in der Datenbank eine Person
			 * mit den gesuchten Eigenschaften befindet.
			 */
			assertTrue(tempVec.size() == 1);
			assertEquals(tempVec.elementAt(0).getNachname(), "Obdenhoevel");
			assertEquals(tempVec.elementAt(0).getVorname(), "Oliver");
			assertEquals(tempVec.elementAt(0).getGeschlecht(), 'm');
			assertEquals(tempVec.elementAt(0).getEmail(), "blablabla@web.de");
			assertEquals(tempVec.elementAt(0).getTelefonnummer(),
					"072383984738");
			/*
			 * Suchen nach einer nicht existierenden Person - Ergebnis: keine
			 * Person wird gefunden.
			 */
			testPB = new PersonBean();
			testPB.setFilter(true);
			try {
				testPB.setNachname("Moooshammer");
				testPB.setVorname("Alfred");
				testPB.setGeschlecht('m');
				testPB.setEmail("klingkling@web.de");
				testPB.setTelefonnummer("07238330271");
			} catch (PersonException e) {
				fail("Bei der PersonBean Klasse trat ein Fehler auf: "
						+ e.getMessage());
			}
			tempVec = new Vector<PersonBean>();
			// wird nicht gefunden, da nicht geschrieben wurde
			tempVec = Person.suchenPerson(testPB);
			assertTrue(tempVec.size() == 0);
		} catch (DatenbankFehlerException e) {
			fail("In der Datenbank trat ein Fehler auf: " + e.getMessage());
		}
	}

	/**
	 * Test Methode fuer get(long id).
	 * 
	 * {@link de.randi2.model.fachklassen.Person#suchen(de.randi2.model.fachklassen.beans.PersonBean)}.
	 */
	@Test
	public void testGet() {
		testPB = new PersonBean();
		testPB.setFilter(true);
		try {
			testPB.setNachname("Meisner");
			testPB.setVorname("Albert");
			testPB.setGeschlecht('m');
			testPB.setEmail("blubblub@web.de");
			testPB.setTelefonnummer("07238323498");
		} catch (PersonException e) {
			fail("Bei der PersonBean Klasse trat ein Fehler auf: "
					+ e.getMessage());
		}
		try {
			// schreiben in die Datenbank
			testPB = DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(
					testPB);
			Vector<PersonBean> tempVec = new Vector<PersonBean>();
			// Holen aus der Datenbank
			tempVec = Person.suchenPerson(testPB);
			// Suchen mit der ID des neuen Datenbankeintrags
			PersonBean vergleichPB = Person.get(tempVec.elementAt(0).getId());
			assertEquals(vergleichPB, testPB);
		} catch (DatenbankFehlerException e) {
			fail("In der Datenbank trat ein Fehler auf: " + e.getMessage());
		}
	}
}