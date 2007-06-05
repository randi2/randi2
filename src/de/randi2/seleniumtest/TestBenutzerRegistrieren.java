package de.randi2.seleniumtest;

import com.thoughtworks.selenium.*;
import de.randi2.utility.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Release 1, SWP2:  Seleniumtest Benutzer registrieren
 * @author Thomas Ganszky
 * @date 05.06.2007
 */
public class TestBenutzerRegistrieren{
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
		 sel.open("http://localhost:8080/RANDI2/index.jsp");
			 
		 //Überprüfung der Eingaben von Benutzername und Passwort
		 //für das Test-Szenarium: administrator/1$administrator - Kombination		 
		 
		 //Überprüfung der Eingabe von Benutzername und Passwort
		 sel.type("username", "administrator");
		 sel.type("password", "");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "");
		 sel.type("password", "1$administrator");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));		 
		 
		 sel.type("username", "xyz");
		 sel.type("password", "1$administrator");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "administrator");
		 sel.type("password", "xyz");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "a");
		 sel.type("password", "1$administrator");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "administrator");
		 sel.type("password", "a");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "aaaaaaaaaaaaaaaa");
		 sel.type("password", "1$administrator");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 //Wenn Eingaben richtig
		 sel.type("username", "administrator");
		 sel.type("password", "1$administrator");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Willkommen Systemadministrator"));
		 assertTrue(sel.isTextPresent("Frau Trude Wurst (ADMIN)"));
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
		 sel.open("http://localhost:8080/RANDI2/index.jsp");
			 
		 //Überprüfung der Eingaben des Benutzernamens und Passwortes
		 //für Test-Szenarium: studienleiter/1$studienleiter - Kombination		 
		 
		 //Überprüfung der Eingabe von Benutzername und Passwort
		 sel.type("username", "studienleiter");
		 sel.type("password", "");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "");
		 sel.type("password", "1$studienleiter");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));		 
		 
		 sel.type("username", "xyz");
		 sel.type("password", "1$studienleiter");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "studienleiter");
		 sel.type("password", "xyz");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "a");
		 sel.type("password", "1$studienleiter");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "studienleiter");
		 sel.type("password", "a");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "aaaaaaaaaaaaaaaa");
		 sel.type("password", "1$studienleiter");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 //Wenn Eingaben richtig
		 sel.type("username", "studienleiter");
		 sel.type("password", "1$studienleiter");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie auswählen"));
		 assertTrue(sel.isTextPresent("Frau Birgit Wurst (STUDIENLEITER)"));
		 
		 sel.click("link=Aspirin");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie ansehen"));
		 assertTrue(sel.isTextPresent("Frau Birgit Wurst (STUDIENLEITER)"));		 
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
		 sel.open("http://localhost:8080/RANDI2/index.jsp");
			 
		 //Überprüfung der Eingaben des Benutzernamens und Passwortes
		 //für Test-Szenarium: sa@randi2.de/1$studienarzt - Kombination
		 	
		 //Überprüfung der Eingabe von Benutzername und Passwort
		 sel.type("username", "sa@randi2.de");
		 sel.type("password", "");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "");
		 sel.type("password", "1$studienarzt");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));		 
		 
		 sel.type("username", "xyz");
		 sel.type("password", "1$studienarzt");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "sa@randi2.de");
		 sel.type("password", "xyz");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "a");
		 sel.type("password", "1$studienarzt");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "sa@randi2.de");
		 sel.type("password", "a");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "aaaaaaaaaaaaaaaa");
		 sel.type("password", "1$studienarzt");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 //Wenn Eingaben richtig
		 sel.type("username", "sa@randi2.de");
		 sel.type("password", "1$studienarzt");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie auswählen"));
		 assertTrue(sel.isTextPresent("Herr Hans Wurst"));
		 
		 
		 sel.click("link=Aspirin");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie ansehen"));
		 assertTrue(sel.isTextPresent("Herr Hans Wurst"));		 
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
		sel.open("http://localhost:8080/RANDI2/index.jsp");
			 
		 //Überprüfung der Eingaben des Benutzernamens und Passwortes
		 //für Test-Szenarium: systemoperator/systemoperator - Kombination		 
		 
		 //Überprüfung der Eingabe von Benutzername und Passwort
		 sel.type("username", "systemoperator");
		 sel.type("password", "");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "");
		 sel.type("password", "1$systemoperator");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));		 
		 
		 sel.type("username", "xyz");
		 sel.type("password", "1$systemoperator");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "systemoperator");
		 sel.type("password", "xyz");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "a");
		 sel.type("password", "1$systemoperator");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "systemoperator");
		 sel.type("password", "a");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "aaaaaaaaaaaaaaaa");
		 sel.type("password", "1$systemoperator");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		//Wenn Eingaben richtig
		 sel.type("username", "systemoperator");
		 sel.type("password", "1$systemoperator");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Willkommen Systemadministrator"));
		 assertTrue(sel.isTextPresent("Herr Hans Maulwurf (SYSOP)"));	
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

	
	/**
	 * Pruefen von Benutzername und Passwort des Statistikers.
	 * Pruefen, ob Stat die für ihn berechtige Seite mit 
	 * Menuepunkte zu sehen bekommt.
	 */
	@Test
    public void pruefeLoginStat() {

	     //Seite oeffnen
		sel.open("http://localhost:8080/RANDI2/index.jsp");
			 
		 //Überprüfung der Eingaben des Benutzernamens und Passwortes
		 //für Test-Szenarium: statistiker/1$statistiker - Kombination		 
		 
		 //Überprüfung der Eingabe von Benutzername und Passwort
		 sel.type("username", "statistiker");
		 sel.type("password", "");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "");
		 sel.type("password", "1$statistiker");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));		 
		 
		 sel.type("username", "xyz");
		 sel.type("password", "1$statistiker");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "statistiker");
		 sel.type("password", "xyz");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "a");
		 sel.type("password", "1$statistiker");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "statistiker");
		 sel.type("password", "a");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 sel.type("username", "aaaaaaaaaaaaaaaa");
		 sel.type("password", "1$statistiker");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 assertTrue(sel.isTextPresent("Loginfehler"));
		 
		 
		 //Wenn Eingaben richtig
		 sel.type("username", "statistiker");
		 sel.type("password", "1$statistiker");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie ansehen"));
		 assertTrue(sel.isTextPresent("Frau Irene Kaese (STATISTIKER)"));
		 assertTrue(sel.isTextPresent("Studienverwaltung"));
		 assertTrue(sel.isTextPresent("Studie ansehen"));
		 assertFalse(sel.isTextPresent("Patient hinzufügen"));		 
		 assertFalse(sel.isTextPresent("Benutzerverwaltung"));
		 assertFalse(sel.isTextPresent("Admins anzeigen"));
		 assertFalse(sel.isTextPresent("Daten ändern"));
		 assertFalse(sel.isTextPresent("Studienärzte anzeigen"));
		 assertFalse(sel.isTextPresent("Benutzer anzeigen"));
		 assertFalse(sel.isTextPresent("Systemadministration"));
		 assertFalse(sel.isTextPresent("System sperren"));
		 assertFalse(sel.isTextPresent("Admin anlegen"));
		 assertFalse(sel.isTextPresent("Studienleiter anlegen"));
		 assertFalse(sel.isTextPresent("Zentrenverwaltung"));
		 assertFalse(sel.isTextPresent("Zentren anzeigen"));
		 assertFalse(sel.isTextPresent("Zentrum anlegen"));		 
		 		 
		 //Logout
		 sel.clickAt("logout_link", "Logout");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 	
	}//pruefeLoginStat()
	
	/**
	 * Test Benutzer anlegen
	 */
	@Test
	public void testBenutzerAnlegen(){
		 //Seite oeffnen
		sel.open("http://localhost:8080/RANDI2/index.jsp");
		
	    sel.click("//input[@value='Benutzer registrieren']");
	    sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Benutzer anlegen"));
		assertTrue(sel.isTextPresent("Haftungsausschluss"));
		
		/**sel.click("//input[@value='Akzeptieren']");
		sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Benutzer anlegen"));
		assertTrue(sel.isTextPresent("Zentrum suchen"));
		
		sel.click("Filtern");
		sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Benutzer anlegen"));
		assertTrue(sel.isTextPresent("Zentrum suchen"));
		
		sel.click("abbrechen");
		sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Benutzer anlegen"));
		assertTrue(sel.isTextPresent("Haftungsausschluss"));
		*/
		sel.click("abbrechen");
		sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Herzlich Willkommen"));
	}
	
	
	/**
	 * Test Aufruf Impressum
	 */
	@Test
	public void testImpressum(){
		//Seite oeffnen
		sel.open("http://localhost:8080/RANDI2/index.jsp");
		
		sel.click("logout_link");
		sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Impressum"));
		
		sel.click("ok");
		sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Herzlich Willkommen"));
	}

	
	
	 @AfterClass
	 public static void tearDownAfterClass() 
	   {
         //Stoppen von Selenium
	     sel.stop();
	   }
	
}//SeleniumTestLogin
