package de.randi2.seleniumtest;

import com.thoughtworks.selenium.*;

import de.randi2.utility.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author Vale
 * 
 */
public class TestFunktionalitaetStudienleiter {

	private static Selenium sel;

	@BeforeClass
	public static void setUpBeforeClass() {
		// Config laden
		Log4jInit.initDebug();
		// Selenium wird gestartet
		sel = new DefaultSelenium(
				"localhost",
				4444,
				"*firefox /Applications/Firefox.app/Contents/MacOS/firefox-bin",
				"http://www.iap.hs-heilbronn.de:8080/randi/");
		sel.start();
	}

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

}
