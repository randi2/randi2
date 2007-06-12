package de.randi2.junittests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.StudienarmException;
import de.randi2.model.fachklassen.Studie;
import de.randi2.model.fachklassen.beans.PatientBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.StudienarmBean;
import de.randi2.randomisation.VollstaendigeRandomisation;
import de.randi2.utility.Log4jInit;

/**
 * Diese Klasse stellt einen Test fuer das StudienarmBean zur Verfuegung.
 * 
 * 
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public class StudienarmBeanTest {

	// das zu testende Objekt
	private StudienarmBean aStudienarmBean = null;

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
		aStudienarmBean = new StudienarmBean();

		aStudienarmBean.setId(21424);
		aStudienarmBean.setBeschreibung("Testbeschreibung");
		aStudienarmBean.setBezeichnung("Testbezeichnung");
		aStudienarmBean.setStatus(Studie.Status.AKTIV);
		aStudienarmBean.setStudieId(3434);
		aStudienarmBean.setStudie(new StudieBean(2323, "","test studie",123, null, null, "",
				VollstaendigeRandomisation.NAME,Studie.Status.AKTIV));

		Vector<PatientBean> aTestdaten = new Vector<PatientBean>();

		aTestdaten.add(new PatientBean(213, "adsd", 'm', new GregorianCalendar(
				2003, 3, 17), 3, new GregorianCalendar(2003, 3, 17), 3,
				aStudienarmBean.getId(), 190));
		aTestdaten.add(new PatientBean(214, "adsd", 'm', new GregorianCalendar(
				2003, 3, 17), 3, new GregorianCalendar(2003, 3, 17), 3,
				aStudienarmBean.getId(), 190));
		aTestdaten.add(new PatientBean(215, "adsd", 'm', new GregorianCalendar(
				2003, 3, 17), 3, new GregorianCalendar(2003, 3, 17), 3,
				aStudienarmBean.getId(), 190));

		aStudienarmBean.setPatienten(aTestdaten);
	}

	/**
	 * Method tearDown() Dem StudieBean-Objekt wird der Wert "null" zugewiesen.
	 * 
	 * @throws Exception,
	 *             wenn die Testklasse nicht beendet werden kann.
	 */
	@After
	public void tearDown() throws Exception {
		aStudienarmBean = null;
	}

	/**
	 * Testet, ob die toString() Methode einen String zurueckgibt.
	 * 
	 * TestMethode fuer
	 * {@link de.randi2.model.fachklassen.beans.StudienarmBean#toString()}.
	 * 
	 */
	@Test
	public void testToString() {
		assertFalse(aStudienarmBean.toString().equals(null));

	}

	/**
	 * Testet, ob zwei identische StudienarmBeans auch als identisch erkannt
	 * werden.
	 * 
	 * TestMethode fuer
	 * {@link de.randi2.model.fachklassen.beans.StudienarmBean#equals(Object)}.
	 * 
	 */
	@Test
	public void testEquals() {

		StudienarmBean sa_null1 = null;

		assertFalse(aStudienarmBean.equals(sa_null1));

		StudienarmBean beanZuvergleichen = aStudienarmBean;
		assertTrue(aStudienarmBean.equals(beanZuvergleichen));

	}

	/**
	 * Testet, ob alle Werte der Status-Enum als Status des Studienarms gesetzt
	 * werden koennen.
	 * 
	 * TestMethode fuer
	 * {@link de.randi2.model.fachklassen.beans.StudienarmBean#setStatus(de.randi2.model.fachklassen.Studie.Status)}.
	 */
	@Test
	public void testSetStatus() {

		try {

			for (Studie.Status aStatus : Studie.Status.values()) {

				aStudienarmBean.setStatus(aStatus);
				assertTrue(aStudienarmBean.getStatus() == aStatus);

			}

		} catch (Exception e) {

			fail(e.getMessage());
		}

	}

	/**
	 * Testet, ob eine Beschreibung erfolgreich fuer den Studienarm gesetzt
	 * werden kann.
	 * 
	 * TestMethode fuer
	 * {@link de.randi2.model.fachklassen.beans.StudienarmBean#setBeschreibung(String)}.
	 */
	@Test
	public void testSetBeschreibung() {

		// gueltige Beschreibungen
		String[] testBeschreibungen = {
				"",
				"dies ist eine testbeschreibung",
				"didsg sdg dgdsugudshg dshgudshgudsugohdshg dgh ighidhgidhgidshg dsighidhgidshgodshg dgihdighdihgidhsgihsdighdsighd sgdighdsighdishgidhihdighdsihg disghidghsiodg" };

		for (int i = 0; i < testBeschreibungen.length; i++) {
			aStudienarmBean.setBeschreibung(testBeschreibungen[i]);
			assertTrue(aStudienarmBean.getBeschreibung().equals(
					testBeschreibungen[i]));
		}

	}

	/**
	 * Testet, ob eine Bezeichnung erfolgreich fuer den Studienarm gesetzt
	 * werden kann.
	 * 
	 * TestMethode fuer
	 * {@link de.randi2.model.fachklassen.beans.StudienarmBean#setBezeichnung(String)}.
	 */
	@Test
	public void testSetBezeichnung() {

		// gueltige Beschreibungen
		String[] testBezeichnungen = {
				"",
				"dies ist eine testbezeichnung",
				"didsg sdg dgdsugudshg dshgudshgudsugohdshg dgh ighidhgidhgidshg dsighidhgidshgodshg dgihdighdihgidhsgihsdighdsighd sgdighdsighdishgidhihdighdsihg disghidghsiodg" };

		for (int i = 0; i < testBezeichnungen.length; i++) {
			aStudienarmBean.setBeschreibung(testBezeichnungen[i]);
			assertTrue(aStudienarmBean.getBeschreibung().equals(
					testBezeichnungen[i]));
		}

	}

	/**
	 * Testet, ob Patienten erfolgreich dem Studienarm zugeordnet werden
	 * koennen.
	 * 
	 * TestMethode fuer
	 * {@link de.randi2.model.fachklassen.beans.StudienarmBean#setPatienten(Vector)}.
	 */
	@Test
	public void testSetPatienten() {

		Vector<PatientBean> aPatienten = new Vector<PatientBean>();

		for (int i = 0; i < 10; i++) {

			try {

				aPatienten.add(new PatientBean(i, "abc" + i, 'm',
						new GregorianCalendar(1983, 3, i), 3,
						new GregorianCalendar(1983, 3, i), 5, aStudienarmBean
								.getId(), 190));

			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				fail("Patient wurde nicht zugeordnet!");
			}

		}

		try {
			aStudienarmBean.setPatienten(aPatienten);
			assertTrue(aStudienarmBean.getPatienten() == aPatienten);
			assertTrue(aStudienarmBean.getPatAnzahl() == aPatienten.size());
		} catch (StudienarmException e) {

			fail(e.getMessage());

		} catch (DatenbankExceptions e){
			fail(e.getMessage());
		}

	}

	/**
	 * Testet, ob eine Studie erfolgreich im Studienarm gesetzt werden kann.
	 * 
	 * TestMethode fuer
	 * {@link de.randi2.model.fachklassen.beans.StudienarmBean#setStudie(StudieBean)}.
	 */
	@Test
	public void testSetStudie() {
		try {
			StudieBean aStudie = new StudieBean(2323, "","test studie",123, new GregorianCalendar(2000,11,30), new GregorianCalendar(2007,11,30), "",
					VollstaendigeRandomisation.NAME,Studie.Status.AKTIV);

			aStudienarmBean.setStudie(aStudie);

			assertTrue(aStudienarmBean.getStudie().equals(aStudie));

		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail("Studie konnte nicht angelegt werden!");
		}

	}

	/**
	 * Testet, ob eine StudieId erfolgreich im Studienarm gesetzt werden kann.
	 * 
	 * TestMethode fuer
	 * {@link de.randi2.model.fachklassen.beans.StudienarmBean#setStudieId(long)}.
	 */
	@Test
	public void testSetStudieId() {
		Random zufall = new Random();
		long aId = zufall.nextLong();

		try {

			aStudienarmBean.setStudieId(aId);

			assertTrue(aStudienarmBean.getStudieId() == aId);

		} catch (Exception e) {

			fail(e.getMessage());

		}

	}

	/**
	 * Testet, ob eine Id erfolgreich im Studienarm gesetzt werden kann.
	 * 
	 * TestMethode fuer
	 * {@link de.randi2.model.fachklassen.beans.StudienarmBean#setId(long)}.
	 */
	@Test
	public void testSetId() {

		Random zufall = new Random();
		long aId = zufall.nextLong();

		try {
			aStudienarmBean.setId(aId);
		} catch (DatenbankExceptions e) {
			fail(e.getMessage());
		}

		assertTrue(aStudienarmBean.getId() == aId);

	}

}
