package de.randi2.junittests;

import static org.junit.Assert.*;

import java.util.Vector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.fachklassen.Person;
import de.randi2.model.fachklassen.beans.PersonBean;

/**
 * Die Test-Klasse fuer die Klasse Person.
 * 
 * @author Thomas Willert [twillert@stud.hs-heilbronn.de]
 * @version $Id: PersonTest.java 2426 2007-05-06 17:34:32Z twillert $
 */
public class PersonTest {

	private PersonBean testPB;

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
	 * {@link de.randi2.model.fachklassen.Person#suchen(de.randi2.model.fachklassen.beans.PersonBean)}.
	 */
	@Test
	public void testSuchenZentrum() {
		testPB = new PersonBean();
		testPB.setFilter(true);
		try {
			testPB.setNachname("Obdenhoevel");
			testPB.setVorname("Oliver");
		} catch (PersonException e) {
			fail("Bei der PersonBean Klasse trat ein Fehler auf: "
					+ e.getMessage());
		}
		Vector<PersonBean> tempVec = null;
		try {
			tempVec = Person.suchen(testPB);
		} catch (PersonException e) {
			e.printStackTrace();
		}
		/*
		 * Da wir wissen, dass sich zur Zeit in der Datebank 1 Person mit den
		 * gesuchten Eigenschaften befindet.
		 */
		assertTrue(tempVec.size() == 1);
		assertEquals(tempVec.elementAt(0).getNachname(), "Obdenhoevel");
		assertEquals(tempVec.elementAt(0).getVorname(), "Oliver");
		/*
		 * Suchen nach einer nicht existierenden Person - Ergebnis: keine Person
		 * wird gefunden.
		 */
		testPB = new PersonBean();
		testPB.setFilter(true);
		try {
			testPB.setNachname("Moooshammer");
		} catch (PersonException e) {
			fail("Bei der PersonBean Klasse trat ein Fehler auf: "
					+ e.getMessage());
		}
		tempVec = null;
		try {
			tempVec = Person.suchen(testPB);
		} catch (PersonException e) {
			e.printStackTrace();
		}
		assertTrue(tempVec.size() == 0);

	}
}