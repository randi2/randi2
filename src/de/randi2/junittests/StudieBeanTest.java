/**
 * 
 */
package de.randi2.junittests;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.randi2.model.exceptions.PersonException;
import de.randi2.model.exceptions.StudieException;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.StudieBean;

/**
 * @author Susanne Friedrich [sufriedr@stud.hs-heilbronn.de]
 * @author Nadine Zwink [nzwink@stud.hs-heilbronn.de]
 * @version $Id: $
 */
public class StudieBeanTest {

	private StudieBean studie;
	private GregorianCalendar endDatum, startDatum;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		studie = new StudieBean();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		studie = null;
	}

	
	/**
	 * Test method for setBenutzerkoto()
	 * Test method for {@link de.randi2.model.fachklassen.beans.StudieBean#setBenutzerkonto(de.randi2.model.fachklassen.beans.BenutzerkontoBean)}.
	 * 
	 * Ueberpruefung, ob Zentrum gesetzt wurde.
	 * Wenn Fehler, soll StudieException geworfen werden.
	 */
	@Test
	public void testSetBenutzerkonto() {
		try {
			studie.setBenutzerkonto(studie.getBenutzerkonto());
		} catch (Exception e) {
			fail("[FEHLER]testSetBenutzerkonto() sollte keine Exception auslösen");
		}
	}


	/**
	 * Test method for {@link de.randi2.model.fachklassen.beans.StudieBean#setBeschreibung(java.lang.String)}.
	 */
	@Test
	public void testSetBeschreibung() {
		fail("Not yet implemented");
	}

	
	
	/**
	 * Test method for setEndDatum()
	 * Test method for {@link de.randi2.model.fachklassen.beans.StudieBean#setEndDatum(java.util.GregorianCalendar)}.
	 *  
	 * Ueberpruefung Enddatum.
	 * Wenn Fehler (Datum befindet sich in der Zukunft), soll 
	 * StudieException geworfen werden.
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
			studie.setEndDatum(endDatum);
			assertTrue(studie.getEndDatum().equals(endDatum));
		    assertFalse((new GregorianCalendar(Locale.GERMANY))
					.before(endDatum));
		} catch (Exception e) {
			fail("[testSetEndDatum]Exception, wenn Zeit des Enddatums in der Zukunft liegt.");
		}
	}

	
	/**
	 * Test method for setName()
	 * Test method for {@link de.randi2.model.fachklassen.beans.StudieBean#setName(java.lang.String)}.
	 * 
	 * Ueberpruefung Studienname.
	 * Wenn kein Studienname vorhanden ist, wird Fehler in der  
	 * StudieException geworfen.
	 */
	@Test
	public void testSetNameLeer() {
		try {
			studie.setFilter(true);
			studie.setName("");
		} catch (Exception e) {
			fail("[testSetNameLeer] Exception, wenn Studienname fehlt.");
		}
	}
	
	/**
	 * Test method for setName()
	 * Test method for {@link de.randi2.model.fachklassen.beans.StudieBean#setName(java.lang.String)}.
	 * 
	 * Ueberpruefung Studienname.
	 * Wenn Studienname zu kurz ist (weniger als 3 Zeichen) oder zu lang (mehr als 50 Zeichen), 
	 * wird Fehler in der StudieException geworfen.
	 */
	@Test
	public void testSetNameLaenge() {
		try {
			studie.setFilter(true);
			studie.setName("XY");
			studie.setName("abcdefghijklmopqrstuvwxyzabcdefghijklmopqrstuvwxyzabcdefxyz");
		} catch (StudieException e) {
			fail("[testSetNameLaenge] Exception, wenn Studienname zu lang oder zu kurz ist.");
	   }
	}

	
	/**
	 * Test method for setName()
	 * Test method for {@link de.randi2.model.fachklassen.beans.StudieBean#setName(java.lang.String)}.
	 * 
	 * Ueberpruefung Studienname.
	 * Wenn Studienname richtig, darf kein Fehler in der 
	 * StudieException geworfen werden.
	 */
	@Test
	public void testSetNameOk() {
		try {
			studie.setFilter(false);
			studie.setName("Studienname");
		} catch (StudieException e) {
			fail("[testSetNameOk] Es darf keine Exception geworfen werden.");
	   }
	}

	/**
	 * Test method for {@link de.randi2.model.fachklassen.beans.StudieBean#setRandomisationseigenschaften(de.randi2.model.fachklassen.beans.RandomisationBean)}.
	 */
	@Test
	public void testSetRandomisationseigenschaften() {
		fail("Not yet implemented");
	}

	
	/**
	 * Test method for setStartDatum()
	 * Test method for {@link de.randi2.model.fachklassen.beans.StudieBean#setStartDatum(java.util.GregorianCalendar)}.
	 * 
	 * Ueberpruefung Startdatum.
	 * Wenn Fehler (Datum befindet sich in der Zukunft), soll 
	 * StudieException geworfen werden.
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
			studie.setEndDatum(startDatum);
			assertTrue(studie.getEndDatum().equals(startDatum));
		    assertFalse((new GregorianCalendar(Locale.GERMANY))
					.before(startDatum));
		} catch (Exception e) {
			fail("[testSetStartDatum]Exception, wenn Zeit des Enddatums in der Zukunft liegt.");
		}
	}



	/**
	 * Test method for {@link de.randi2.model.fachklassen.beans.StudieBean#setStudienarme(java.util.Vector)}.
	 */
	@Test
	public void testSetStudienarme() {
		fail("Not yet implemented");
	}

	

	/**
	 * Test method for {@link de.randi2.model.fachklassen.beans.StudieBean#setStudienprotokoll_pfad(java.lang.String)}.
	 */
	@Test
	public void testSetStudienprotokoll_pfad() {
		fail("Not yet implemented");
	}



	/**
	 * Test method for setZentrum()
	 * Test method for {@link de.randi2.model.fachklassen.beans.StudieBean#setZentrum(de.randi2.model.fachklassen.beans.ZentrumBean)}.
	 * 
	 * Ueberpruefung, ob Zentrum gesetzt wurde.
	 * Wenn Fehler, soll StudieException geworfen werden.
	 */
	@Test
	public void testSetZentrum() {
		try {
			studie.setZentrum(studie.getZentrum());
		} catch (Exception e) {
			fail("[FEHLER]testSetZentrum() sollte keine Exception auslösen");
		}
	}

	

	/**
	 * Test method for {@link de.randi2.model.fachklassen.beans.StudieBean#setStatus(int)}.
	 */
	@Test
	public void testSetStatus() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test method for setId
	 * Test method for {@link de.randi2.model.fachklassen.BenutzerkontoBean#setId()}.
	 * 
	 * Ueberpruefung, ob ID der Studie gesetzt wurde.
	 * Wenn Fehler, soll StudieException geworfen werden.
	 */
	@Test
	public void testSetId() {
		try {
			studie.setId(studie.getId());
		} catch (Exception e) {
			fail("[FEHLER]testSetId() sollte keine Exception auslösen");
		}
	}

}
