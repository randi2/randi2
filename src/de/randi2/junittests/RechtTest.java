package de.randi2.junittests;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.model.fachklassen.Recht;
import de.randi2.model.fachklassen.Recht.Rechtenamen;

/**
 * TODO Test ist nicht korrekt
 * 
 * 
 * @author Nadine Zwink <nzwink@stud.hs-heilbronn.de>
 * @version $Id$
 */
public class RechtTest {

	private Recht recht;

	
	private Rechtenamen rName;

	/**
	 * HashMap, welche die Instanzen der einzelnen Rechte verwaltet.
	 */
	private static HashMap<Rechtenamen, Recht> rechte = new HashMap<Rechtenamen, Recht>();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp(){
		recht = new Recht(rName);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		recht = null;
	}



	@Test
	public void testGetRechtInstanzVorhanden() {
		
		if (rechte.containsKey(rName)) {
			System.out.println("a");
			System.out.print(rechte.get(rName).toString());
			assertTrue(true);
			
		}
	}

	/**
	 * Test method for {@link de.randi2.model.fachklassen.Recht#getName()}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetName() {

		recht = Recht.getRecht(rName);

		assertTrue(recht.getRechtname().equals(
				Recht.Rechtenamen.ADMINACCOUNTS_VERWALTEN));
		assertTrue(recht.getRechtname().equals(
				Recht.Rechtenamen.ARCHIV_EINSEHEN));
		assertTrue(recht.getRechtname().equals(Recht.Rechtenamen.BK_AENDERN));
		assertTrue(recht.getRechtname().equals(Recht.Rechtenamen.BK_ANSEHEN));
		assertTrue(recht.getRechtname().equals(Recht.Rechtenamen.BK_SPERREN));
		assertTrue(recht.getRechtname().equals(
				Recht.Rechtenamen.GRUPPENNACHRICHT_VERSENDEN));
		assertTrue(recht.getRechtname().equals(
				Recht.Rechtenamen.RANDOMISATION_EXPORTIEREN));
		assertTrue(recht.getRechtname().equals(Recht.Rechtenamen.STAT_EINSEHEN));
		assertTrue(recht.getRechtname()
				.equals(Recht.Rechtenamen.STUDIE_AENDERN));
		assertTrue(recht.getRechtname()
				.equals(Recht.Rechtenamen.STUDIE_ANLEGEN));
		assertTrue(recht.getRechtname().equals(
				Recht.Rechtenamen.STUDIE_LOESCHEN));
		assertTrue(recht.getRechtname().equals(
				Recht.Rechtenamen.STUDIE_PAUSIEREN));
		assertTrue(recht.getRechtname().equals(
				Recht.Rechtenamen.STUDIE_RANDOMISIEREN));
		assertTrue(recht.getRechtname().equals(
				Recht.Rechtenamen.STUDIE_SIMULIEREN));
		assertTrue(recht.getRechtname().equals(
				Recht.Rechtenamen.STUDIEN_EINSEHEN));
		assertTrue(recht.getRechtname().equals(
				Recht.Rechtenamen.STUDIENARM_BEENDEN));
		assertTrue(recht.getRechtname().equals(
				Recht.Rechtenamen.STUDIENTEILNEHMER_HINZUFUEGEN));
		assertTrue(recht.getRechtname().equals(
				Recht.Rechtenamen.STULEIACCOUNTS_VERWALTEN));
		assertTrue(recht.getRechtname()
				.equals(Recht.Rechtenamen.SYSTEM_SPERREN));
		assertTrue(recht.getRechtname().equals(
				Recht.Rechtenamen.ZENTREN_ANZEIGEN));
		assertTrue(recht.getRechtname().equals(
				Recht.Rechtenamen.ZENTRUM_AENDERN));
		assertTrue(recht.getRechtname().equals(
				Recht.Rechtenamen.ZENTRUM_AKTIVIEREN));
		assertTrue(recht.getRechtname().equals(
				Recht.Rechtenamen.ZENTRUM_ANLEGEN));
	}

	/**
	 * Test method for {@link de.randi2.model.fachklassen.Recht#getRechtname()}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetRechtname() {

		recht = Recht.getRecht(rName);

		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.ADMINACCOUNTS_VERWALTEN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.ARCHIV_EINSEHEN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.BK_AENDERN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.BK_ANSEHEN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.BK_SPERREN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.GRUPPENNACHRICHT_VERSENDEN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.RANDOMISATION_EXPORTIEREN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.STAT_EINSEHEN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.STUDIE_AENDERN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.STUDIE_ANLEGEN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.STUDIE_LOESCHEN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.STUDIE_PAUSIEREN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.STUDIE_RANDOMISIEREN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.STUDIE_SIMULIEREN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.STUDIEN_EINSEHEN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.STUDIENARM_BEENDEN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.STUDIENTEILNEHMER_HINZUFUEGEN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.STULEIACCOUNTS_VERWALTEN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.SYSTEM_SPERREN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.ZENTREN_ANZEIGEN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.ZENTRUM_AENDERN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.ZENTRUM_AKTIVIEREN));
		assertTrue(recht.getName().toString().equals(
				Recht.Rechtenamen.ZENTRUM_ANLEGEN));
	}

}
