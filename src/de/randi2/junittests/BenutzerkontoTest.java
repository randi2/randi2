package de.randi2.junittests;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;
import java.util.Vector;

import org.apache.log4j.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.model.fachklassen.Rolle;

/**
 * @author Katharina Chruscz <kchruscz@stud.hs-heilbronn.de>
 * @version $Id$
 * 
 */
public class BenutzerkontoTest {

	private BenutzerkontoBean bKontoBean;

	private String benutzername, passwort;

	private Rolle rolle;

	// TODO Spaeter wird Titel als eine Enum realisiert werden - deswegen muss
	// man das auch danach anpassen
	private PersonBean benutzer; 

	private PersonBean ansprechpartner; 

	private boolean gesperrt;

	private ZentrumBean zentrum;

	private GregorianCalendar ersterLogin, letzterLogin;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		//PropertyConfigurator.configure("C:/Dokumente und Einstellungen/user/Desktop/workspace Together/RANDI2/WebContent/WEB-INF/log4j.lcf");
		benutzername = "studienleiter";
		passwort = "1$studienleiter";

		gesperrt = false;
		rolle = Rolle.getStudienleiter();

		ersterLogin = new GregorianCalendar(2006, 10, 20);
		letzterLogin = new GregorianCalendar(2006, 11, 30);

		zentrum = new ZentrumBean(1, "institution", "abteilung", "Ort", "plz",
				"Strasse", "Hausnr", ansprechpartner, "Passwort");

		try {
			
				benutzer = new PersonBean("nachname", "vorname",
						"Prof.", 'm', "user@hs-heilbronn.de", "01760099334",
						"017600972487", "01760427424");
		

		
				ansprechpartner = new PersonBean("nachname", "vorname",
						"Prof.", 'm', "user@hs-heilbronn.de", "01760099334",
						"017600972487", "01760427424");
			

			bKontoBean = new BenutzerkontoBean(benutzername, passwort, rolle,
					benutzer, ansprechpartner, gesperrt, zentrum, ersterLogin,
					letzterLogin);
		} catch (BenutzerkontoException e) {
			e.printStackTrace();
		} catch (PersonException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

		bKontoBean = null;

	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#Benutzerkonto(de.randi2.model.fachklassen.beans.BenutzerkontoBean)}.
	 */
	@Test
	public void testBenutzerkonto() {
		try {
			Benutzerkonto bKonto = new Benutzerkonto(bKontoBean);
		} catch (Exception e) {
			fail("Fehler aufgetreten bei testBenutzerkonto");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#suchenBenutzer(de.randi2.model.fachklassen.beans.BenutzerkontoBean)}.
	 */
	@Test
	public void testSuchenBenutzer() {
		try {
			Vector suchErgebnisse = new Vector();
			suchErgebnisse = Benutzerkonto.suchenBenutzer(bKontoBean);
		} catch (Exception e) {
			fail("Fehler ausgelöst bei testSuchenBenutzer");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#anlegenBenutzer(de.randi2.model.fachklassen.beans.BenutzerkontoBean)}.
	 */
	@Test
	public void testAnlegenBenutzer() {
		try {
			Benutzerkonto.anlegenBenutzer(bKontoBean);
		} catch (Exception e) {
			fail("Fehler aufgetreten bei testAnlegenBenutzer");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#getBenutzer(java.lang.String)}.
	 */
	@Test
	public void testGetBenutzer() {
		try {
			Benutzerkonto.getBenutzer("studienleiter");
			Benutzerkonto.getBenutzer("administrator");
			Benutzerkonto.getBenutzer("systemoperator");
			Benutzerkonto.getBenutzer("statistiker");
			Benutzerkonto.getBenutzer("sa@randi2.de");

		} catch (Exception e) {
			fail("Fehler aufgetreten bei testGetBenutzer");
		}

	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#toString()}.
	 */
	@Test
	public void testToString() {
		try {
			Benutzerkonto bKonto = new Benutzerkonto(bKontoBean);
			bKonto.toString();
		} catch (Exception e) {
			fail("Fehler aufgetreten bei testToString");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#equals(de.randi2.model.fachklassen.Benutzerkonto)}.
	 */
	@Test
	public void testEqualsBenutzerkonto() {
		Benutzerkonto aKonto = new Benutzerkonto(bKontoBean);
		Benutzerkonto bKonto = new Benutzerkonto(bKontoBean);
		boolean wert1 = aKonto.equals(bKonto);
		if (wert1 == false) {
			fail("Vergleich von zwei gleichen Benutzerkonten");
		} else {
			;
		}
		String benutzername2 = "Statistiker";
		String passwort2 = "1$statistiker";
		BenutzerkontoBean anderesKontoBean;
		try {
			anderesKontoBean = new BenutzerkontoBean(
					benutzername2, passwort2, rolle, benutzer, ansprechpartner,
					gesperrt, zentrum, ersterLogin, letzterLogin);
			Benutzerkonto cKonto = new Benutzerkonto(anderesKontoBean);
			boolean wert2 = aKonto.equals(cKonto);
			if (wert2 == true) {
				fail("Vergleich von zwei verschiedenen Benutzerkonten liefert ein true zurück");
			} else {
				;
			}
		} catch (BenutzerkontoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#getBenutzerkontobean()}.
	 */
	@Test
	public void testGetBenutzerkontobean() {
		try {
			Benutzerkonto bKonto = new Benutzerkonto(bKontoBean);
			bKonto.getBenutzerkontobean();
		} catch (Exception e) {
			fail("Fehler bei testGetBenutzerkontobean");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#pruefenPasswort(java.lang.String)}.
	 */
	@Test
	public void testPruefenPasswort() {
		try {
			String benutzernameNeu = "Statistiker";
			String passwortNeu = "1$statistiker";
			
			String passwortAktuellerBenutzer = Benutzerkonto.getBenutzer(benutzernameNeu).getPasswort();
			if (passwortAktuellerBenutzer.equalsIgnoreCase(passwortNeu)) {
				; // passiert nichts
			} else {
				fail("Passwort ist falsch");
			}

		} catch (Exception e) {
			fail("unerwarteter Fehler ist aufgetreten!");
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// wird erstmal nicht benoetigt
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// wird erstmal nicht benoetigt
	}

}
