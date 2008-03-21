package de.randi2.seleniumtest;

import com.thoughtworks.selenium.*;

import de.randi2.utility.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Release 2/3 Diese Klasse enthaelt die Selenium Test Funktionalitaeten zur
 * Ueberpruefung des Studienleiter Accounts. Die Anmeldung wird als
 * abgeschlossen angesehen, da diese schon bei Release1 getestet wurde
 * 
 * @author Valentin Graeff
 * 
 */
public class TestFunktionalitaetStudienleiter {

	private static Selenium sel;

	/**
	 * Starten des Seleniumservers, sowie Angabe
	 * der fuer den Test notwendigen Pfade eines
	 * Browsers und der Webapplikation.
	 *
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		// Config laden
		Log4jInit.initDebug();
		// Selenium wird gestartet
		sel = new DefaultSelenium(
				"localhost",
				4444,
				"*firefox /Applications/Firefox.app/Contents/MacOS/firefox-bin", // Hier
																					// muss
																					// der
																					// fuer
																					// das
																					// Betriebssystem
																					// spezifische
																					//	Firefox Pfad
				"http://www.iap.hs-heilbronn.de:8080/randi/"); 
																// und die
																// App-Adresse
																// angegeben
																// werden
		sel.start();
	}

	/**
	 * Ueberpruefung, ob der Studienleiter 
	 * auf die Seite StudieAnsehen gelangt
	 * und diese korrekt angezeigt wird
	 *
	 */
	@Test
	public void pruefeStudieAnsehen() {

		sel.open("http://www.iap.hs-heilbronn.de:8080/randi/");
		sel.type("username", "studienleiter");
		sel.type("password", "1$studienleiter");

		sel.click("Submit");
		sel.waitForPageToLoad("3000");

		sel.click("Link=Aspirin");
		sel.waitForPageToLoad("3000");
		assertTrue(sel.isTextPresent("Studie ansehen"));
	}

	
	/**
	 * Ueberpruefung der Funktion BenutzerdatenAendern.
	 * Es werden saemtliche Fehlermoeglichkeite ueberprueft
	 * und getestet, ob auf Fehleingaben korrekt reagiert wird.
	 */
	
	public void pruefeBenutzerAendern() {
		
		sel.open("http://www.iap.hs-heilbronn.de:8080/randi/");
		sel.type("username", "studienleiter");
		sel.type("password", "1$studienleiter");
		sel.click("Submit");
		sel.waitForPageToLoad("3000");
		sel.click("link=Aspirin");
		sel.waitForPageToLoad("3000");
		assertTrue(sel.isTextPresent("Studie ansehen"));
		
		 sel.click("link=Benutzerdatenaten Ändern");
		 assertTrue(sel.isTextPresent("Titel"));
		 assertTrue(sel.isTextPresent("Nachname"));
		 assertTrue(sel.isTextPresent("Vorname"));
		 assertTrue(sel.isTextPresent("Geschlecht"));
		 assertTrue(sel.isTextPresent("Institut"));
		 assertTrue(sel.isTextPresent("E-Mail"));
		 assertTrue(sel.isTextPresent("Telefonnummer"));
		 assertTrue(sel.isTextPresent("Handynummer"));
		 assertTrue(sel.isTextPresent("Fax"));
		 assertTrue(sel.isTextPresent("Nachname Ansprechpartner"));
		 assertTrue(sel.isTextPresent("Vorname Ansprechpartner"));
		 assertTrue(sel.isTextPresent("Telefonnummer Ansprechpartner"));
		
	}
	
	
	/**
	 * Ueberpruefung, ob der Studienleiter 
	 * auf die Seite StudiePausieren gelangt
	 * und diese korrekt angezeigt wird
	 *
	 */
	@Test
	public void pruefeStudiePausieren() {

		sel.open("http://www.iap.hs-heilbronn.de:8080/randi/");
		sel.type("username", "studienleiter");
		sel.type("password", "1$studienleiter");
		sel.click("Submit");
		sel.waitForPageToLoad("3000");
		sel.click("link=Aspirin");
		sel.waitForPageToLoad("3000");
		assertTrue(sel.isTextPresent("Studie ansehen"));

		sel.click("studie_pausieren");
		sel.waitForPageToLoad("3000");
		assertTrue(sel.isTextPresent("Studie pausieren"));

		sel.click("entsp_nein");
		sel.waitForPageToLoad("3000");
		assertTrue(sel.isTextPresent("Studie ansehen"));

	}

	/**
	 * Ueberpruefung, ob der Studienleiter 
	 * auf die Seite StudienaerzteAnzeigen gelangt
	 * und diese korrekt angezeigt wird
	 *
	 */
	@Test
	public void pruefeStudienärzteAnzeigen() {

		sel.open("http://www.iap.hs-heilbronn.de:8080/randi/");
		sel.type("username", "studienleiter");
		sel.type("password", "1$studienleiter");
		sel.click("Submit");
		sel.waitForPageToLoad("3000");
		sel.click("link=Aspirin");
		sel.waitForPageToLoad("3000");
		assertTrue(sel.isTextPresent("Studie ansehen"));

		sel.click("link=Studienärzte anzeigen");
		sel.waitForPageToLoad("3000");
		assertTrue(sel.isTextPresent("Studienärzte - Liste"));

		sel.click("zurueck");
		sel.waitForPageToLoad("3000");
		assertTrue(sel.isTextPresent("Studie ansehen"));
	}

	/**
	 * Ueberpruefung, ob der Studienleiter 
	 * auf die Seite StudieArmAuswaehlen gelangt
	 * und diese korrekt angezeigt wird
	 *
	 */
	@Test
	public void pruefeArmAuswaehlen() {

		sel.open("http://www.iap.hs-heilbronn.de:8080/randi/");
		sel.type("username", "studienleiter");
		sel.type("password", "1$studienleiter");
		sel.click("Submit");
		sel.waitForPageToLoad("3000");
		sel.click("link=Aspirin");
		sel.waitForPageToLoad("3000");
		assertTrue(sel.isTextPresent("Studie ansehen"));

		sel.click("arm");
		sel.waitForPageToLoad("3000");
		assertTrue(sel.isTextPresent("Studienarm beenden"));

		sel.click("entsp_ja");
		sel.waitForPageToLoad("3000");
		assertTrue(sel.isTextPresent("Studie ansehen"));
	}

	/**
	 * Ueberpruefung, ob der Studienleiter 
	 * auf die Seite StatistikAnzeigen gelangt
	 * und diese korrekt angezeigt wird
	 *
	 */
	@Test
	public void pruefeStatistikAnzeigen() {
		sel.open("http://www.iap.hs-heilbronn.de:8080/randi/");
		sel.type("username", "studienleiter");
		sel.type("password", "1$studienleiter");
		sel.click("Submit");
		sel.waitForPageToLoad("3000");
		sel.click("link=Aspirin");
		sel.waitForPageToLoad("3000");
		assertTrue(sel.isTextPresent("Studie ansehen"));

		sel.click("statistik");
		sel.waitForPageToLoad("3000");
		assertTrue(sel.isTextPresent("Auswertungsmethoden zu einer Statistik"));

		sel.click("EineCheckBox1");
		sel.click("EineCheckBox2");
		sel.click("EineCheckBox3");
		sel.click("EineCheckBox4");

		sel.click("entsp_ja");
		sel.waitForPageToLoad("3000");
		assertTrue(sel.isTextPresent("Studie ansehen"));

	}

	/**
	 * Ueberpruefung, ob der Studienleiter auf 
	 * die Seite Randomisationsergebnisse gelangt
	 * und diese korrekt angezeigt wird
	 *
	 */
	@Test
	public void pruefeRandomisationsergebnisse() {
		sel.open("http://www.iap.hs-heilbronn.de:8080/randi/");
		sel.type("username", "studienleiter");
		sel.type("password", "1$studienleiter");
		sel.click("Submit");
		sel.waitForPageToLoad("3000");
		sel.click("link=Aspirin");
		sel.waitForPageToLoad("3000");
		assertTrue(sel.isTextPresent("Studie ansehen"));

		sel.click("randomisation");
		sel.waitForPageToLoad("3000");
		assertTrue(sel.isTextPresent("Studienergebnisse"));

		sel.click("entsp_ja");
		sel.waitForPageToLoad("3000");
		assertTrue(sel.isTextPresent("Studie ansehen"));

	}

	/**
	 * Herunterfahren des Seleniumservers
	 *
	 */
	@AfterClass
	public static void tearDownAfterClass() {
		// Stoppen von Selenium
		sel.stop();
	}

}