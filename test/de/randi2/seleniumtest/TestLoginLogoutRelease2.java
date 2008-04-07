package de.randi2.seleniumtest;

import com.thoughtworks.selenium.*;
import de.randi2.utility.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Release 2, SWP3:  Seleniumtest Login und Logout
 *                   mit Aufruf der berechtigten Seite(n)
 *                   des Benutzers
 * @author Nadine Zwink
 * @date 06.06.2007
 */
public class TestLoginLogoutRelease2{
	
	private static Selenium sel;
	
	@BeforeClass
	public static void setUpBeforeClass(){
        //Config laden
		Log4jInit.initDebug();
		//Selenium wird gestartet
		 sel = new DefaultSelenium(Config.getProperty(Config.Felder.DEBUG_SELENIUM_SERVER_HOST),
			 Integer.parseInt(Config.getProperty(Config.Felder.DEBUG_SELENIUM_SERVER_PORT)), "*firefox "+Config.getProperty(Config.Felder.DEBUG_SELENIUM_FIREFOX_LOCATION), Config.getProperty(Config.Felder.DEBUG_SELENIUM_START_URL));
		 sel.start();
	}	
	
	
	/**
	 * Pruefen von Benutzername und Passwort des Administrators.
	 * Pruefen, ob Administrator die für ihn berechtige Seite mit 
	 * Menuepunkte zu sehen bekommt.
	 */
	@Test
    public void pruefeLoginAdmin() {

		 //Seite oeffnen
		 sel.open("http://www.iap.hs-heilbronn.de:8080/randinightly/");
			 
		 //Überprüfung der Eingaben von Benutzername und Passwort
		 //für das Test-Szenarium: hans75/1$dampf75 - Kombination		 
		 
		 //Überprüfung der Eingabe von Benutzername und Passwort
		 sel.type("username", "hans75");
		 sel.type("password", "");
		 sel.click("Submit");
		 assertEquals("Bitte Passwort eingeben\nFehlerhaftes Passwort", sel.getAlert()); 
		
		 sel.type("username", "");
		 sel.type("password", "1$dampf75");
		 sel.click("Submit");
		 assertEquals("Bitte Namen eingeben\nFehlerhafter Benutzername", sel.getAlert());
		  
		 sel.type("username", "xyz");
		 sel.type("password", "1$dampf75");
		 sel.click("Submit");
		 assertEquals("Fehlerhafter Benutzername", sel.getAlert());
		 
		 sel.type("username", "hans75");
		 sel.type("password", "xyz");
		 sel.click("Submit");
		 assertEquals("Fehlerhaftes Passwort", sel.getAlert());
					 
		 sel.type("username", "aaaaaaaaaaaaaaaa");
		 sel.type("password", "1$dampf75");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "hans75");
		 sel.type("password", "hhhhhhhhhhh");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 //Wenn Eingaben richtig
		 sel.type("username", "hans75");
		 sel.type("password", "1$dampf75");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Willkommen Systemadministrator"));
		 assertTrue(sel.isTextPresent("Herr Dampf Hans (ADMIN)"));
		 assertTrue(sel.isTextPresent("Benutzerverwaltung"));
		 assertTrue(sel.isTextPresent("Daten ändern"));
		 assertTrue(sel.isTextPresent("Benutzer anzeigen"));
		 assertTrue(sel.isTextPresent("Zentrenverwaltung"));
		 assertTrue(sel.isTextPresent("Zentren anzeigen"));
		 assertTrue(sel.isTextPresent("Zentrum anlegen"));
		 assertTrue(sel.isTextPresent("Studienverwaltung"));
		 assertTrue(sel.isTextPresent("Systemadministration"));
		 assertTrue(sel.isTextPresent("Studienleiter anlegen"));			
		  
		 //Logout
		 sel.clickAt("logout_link", "Logout");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
				 
  	}//pruefeLoginAdmin()
	
	
	/**
	 * Pruefen von Benutzername und Passwort des Studienleiters.
	 * Pruefen, ob SL die für ihn berechtige Seite mit 
	 * Menuepunkte zu sehen bekommt.
	 */
	@Test
    public void pruefeLoginSL() {

		 //Seite oeffnen
		 sel.open("http://www.iap.hs-heilbronn.de:8080/randinightly/");
			 
		 //Überprüfung der Eingaben des Benutzernamens und Passwortes
		 //für Test-Szenarium: valentin/1$graeff83 - Kombination		 
		 
		 //Überprüfung der Eingabe von Benutzername und Passwort
		 sel.type("username", "valentin");
		 sel.type("password", "");
		 sel.click("Submit");
		 assertEquals("Bitte Passwort eingeben\nFehlerhaftes Passwort", sel.getAlert()); 
					 
		 sel.type("username", "");
		 sel.type("password", "1$graeff83");
		 sel.click("Submit");
		 assertEquals("Bitte Namen eingeben\nFehlerhafter Benutzername", sel.getAlert());
		 
		 sel.type("username", "xyz");
		 sel.type("password", "1$graeff83");
		 sel.click("Submit");
		 assertEquals("Fehlerhafter Benutzername", sel.getAlert());
		 
		 sel.type("username", "valentin");
		 sel.type("password", "xyz");
		 sel.click("Submit");
		 assertEquals("Fehlerhaftes Passwort", sel.getAlert());
		 
		 sel.type("username", "valentin");
		 sel.type("password", "aaaaaaaa");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
				 
		 sel.type("username", "aaaaaaaaaaaaaaaa");
		 sel.type("password", "1$graeff83");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		
		 //Wenn Eingaben richtig
		 sel.type("username", "valentin");
		 sel.type("password", "1$graeff83");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie auswählen"));
		 assertTrue(sel.isTextPresent("Herr Valentin Graeff (STUDIENLEITER)"));
		 
		 sel.click("link=Aspirin");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie ansehen"));
		 assertTrue(sel.isTextPresent("Herr Valentin Graeff (STUDIENLEITER)"));		 
		 assertTrue(sel.isTextPresent("Benutzerverwaltung"));
		 assertTrue(sel.isTextPresent("Daten ändern"));
		 assertFalse(sel.isTextPresent("Benutzer anzeigen"));
		 assertTrue(sel.isTextPresent("Studienärzte anzeigen"));
		 assertTrue(sel.isTextPresent("Zentrenverwaltung"));
		 assertTrue(sel.isTextPresent("Zentren anzeigen"));
		 assertFalse(sel.isTextPresent("Zentrum anlegen"));
		 assertTrue(sel.isTextPresent("Studienverwaltung"));
		 assertTrue(sel.isTextPresent("Studie ansehen"));
		 assertFalse(sel.isTextPresent("Systemadministration"));
		 assertFalse(sel.isTextPresent("Studienleiter anlegen"));
							 
         //Logout
		 sel.clickAt("logout_link", "Logout");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 	
	}//pruefeLoginSL()
	
	
	/**
	 * Pruefen von Benutzername und Passwort des Studienarztes.
	 * Pruefen, ob SA die für ihn berechtige Seite mit 
	 * Menuepunkte zu sehen bekommt.
	 */
	@Test
    public void pruefeLoginSA() {
 
		 //Seite oeffnen
		 sel.open("http://www.iap.hs-heilbronn.de:8080/randinightly/");
			 
		 //Überprüfung der Eingaben des Benutzernamens und Passwortes
		 //für Test-Szenarium: frank80/1$hess80 - Kombination
		 	
		 //Überprüfung der Eingabe von Benutzername und Passwort
		 sel.type("username", "frank80");
		 sel.type("password", "");
		 sel.click("Submit");
		 assertEquals("Bitte Passwort eingeben\nFehlerhaftes Passwort", sel.getAlert()); 
					 
		 sel.type("username", "");
		 sel.type("password", "1$hess80");
		 sel.click("Submit");
		 assertEquals("Bitte Namen eingeben\nFehlerhafter Benutzername", sel.getAlert());
		 
		 sel.type("username", "xyz");
		 sel.type("password", "1$hess80");
		 sel.click("Submit");
		 assertEquals("Fehlerhafter Benutzername", sel.getAlert());
		 
		 sel.type("username", "frank80");
		 sel.type("password", "xyz");
		 sel.click("Submit");
		 assertEquals("Fehlerhaftes Passwort", sel.getAlert());
		 
		 sel.type("username", "frank80");
		 sel.type("password", "aaaaaaa");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		
		 sel.type("username", "aaaaaaaaaaaaaaaa");
		 sel.type("password", "1$hess80");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 //Wenn Eingaben richtig
		 sel.type("username", "frank80");
		 sel.type("password", "1$hess80");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie auswählen"));
		 assertTrue(sel.isTextPresent("Herr Frank Hess"));
		 
		 
		 sel.click("link=Aspirin");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie ansehen"));
		 assertTrue(sel.isTextPresent("Herr Frank Hess"));		 
		 assertTrue(sel.isTextPresent("Benutzerverwaltung"));
		 assertTrue(sel.isTextPresent("Daten ändern"));
		 assertFalse(sel.isTextPresent("Studienärzte anzeigen"));
		 assertFalse(sel.isTextPresent("Benutzer anzeigen"));
		 assertTrue(sel.isTextPresent("Studienverwaltung"));
		 assertTrue(sel.isTextPresent("Studie ansehen"));
		 assertTrue(sel.isTextPresent("Patient hinzufügen"));
		 assertFalse(sel.isTextPresent("Systemadministration"));
		 assertFalse(sel.isTextPresent("Studienleiter anlegen"));
		 assertFalse(sel.isTextPresent("Zentrenverwaltung"));
		 assertFalse(sel.isTextPresent("Zentren anzeigen"));
		 assertFalse(sel.isTextPresent("Zentrum anlegen"));
		 		 
		  //Logout
		 sel.clickAt("logout_link", "Logout");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 
    }//pruefeLoginSA()
	
	
	/**
	 * Pruefen von Benutzername und Passwort des Systemoparators.
	 * Pruefen, ob Sysop die für ihn berechtige Seite mit 
	 * Menuepunkte zu sehen bekommt.
	 */
	@Test
    public void pruefeLoginSysop() {
 
		 //Seite oeffnen
		sel.open("http://www.iap.hs-heilbronn.de:8080/randinightly/");
			 
		 //Überprüfung der Eingaben des Benutzernamens und Passwortes
		 //für Test-Szenarium: nadine/1$zwink83 - Kombination		 
		 
		 //Überprüfung der Eingabe von Benutzername und Passwort
		 sel.type("username", "nadine");
		 sel.type("password", "");
		 sel.click("Submit");
		 assertEquals("Bitte Passwort eingeben\nFehlerhaftes Passwort", sel.getAlert()); 
					 
		 sel.type("username", "");
		 sel.type("password", "1$zwink83");
		 sel.click("Submit");
		 assertEquals("Bitte Namen eingeben\nFehlerhafter Benutzername", sel.getAlert());
		 
		 sel.type("username", "xyz");
		 sel.type("password", "1$zwink83");
		 sel.click("Submit");
		 assertEquals("Fehlerhafter Benutzername", sel.getAlert());
		 
		 sel.type("username", "nadine");
		 sel.type("password", "xyz");
		 sel.click("Submit");
		 assertEquals("Fehlerhaftes Passwort", sel.getAlert());
		 
		 sel.type("username", "nadine");
		 sel.type("password", "aaaaaaa");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "aaaaaaaaaaaaaaaa");
		 sel.type("password", "1$zwink83");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 //Wenn Eingaben richtig
		 sel.type("username", "nadine");
		 sel.type("password", "1$zwink83");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Systemadministration"));
		 assertTrue(sel.isTextPresent("Frau Zwink Nadine (SYSOP)"));	
		 assertTrue(sel.isTextPresent("Benutzerverwaltung"));
		 assertTrue(sel.isTextPresent("Admins anzeigen"));
		 assertFalse(sel.isTextPresent("Daten ändern"));
		 assertFalse(sel.isTextPresent("Studienärzte anzeigen"));
		 assertFalse(sel.isTextPresent("Benutzer anzeigen"));
		 assertTrue(sel.isTextPresent("Systemadministration"));
		 assertTrue(sel.isTextPresent("System sperren"));
		 assertTrue(sel.isTextPresent("Admin anlegen"));
		 assertFalse(sel.isTextPresent("Studienleiter anlegen"));
		 assertFalse(sel.isTextPresent("Zentrenverwaltung"));
		 assertFalse(sel.isTextPresent("Zentren anzeigen"));
		 assertFalse(sel.isTextPresent("Zentrum anlegen"));		 
		 assertFalse(sel.isTextPresent("Studienverwaltung"));		 
		 assertFalse(sel.isTextPresent("Studie ansehen"));
		 assertFalse(sel.isTextPresent("Patient hinzufügen"));
		 
         //Logout
		 sel.clickAt("logout_link", "Logout");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 
	}//pruefeLoginSysop()

	
	
	 @AfterClass
	 public static void tearDownAfterClass() 
	   {
         //Stoppen von Selenium
	     sel.stop();
	   }
	
}//SeleniumTestLoginRelease2
