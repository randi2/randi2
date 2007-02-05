package de.randi2.seleniumtest;

import com.thoughtworks.selenium.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Release 1, SWP2:  Seleniumtest Login und Logout
 *                   mit Aufruf der berechtigten Seite(n)
 *                   des Benutzers
 * @author Nadine Zwink
 * @date 31.01.2007
 */
public class TestLoginLogout{
	
	private final static String SELENIUM_SERVER_HOST = "localhost";
	private final static int SELENIUM_SERVER_PORT = 4444;
	private final static String FIREFOX_LOCATION = "C:/Programme/Mozilla Firefox/firefox.exe";
	private final static String START_URL = "http://localhost:8080/OberflaechenentwurfTest/";
	
	private static Selenium sel;
	
	
	
	@BeforeClass
	public static void setUpBeforeClass(){
         //Selenium wird gestartet
		 sel = new DefaultSelenium(TestLoginLogout.SELENIUM_SERVER_HOST,
				 TestLoginLogout.SELENIUM_SERVER_PORT, "*firefox "+FIREFOX_LOCATION, START_URL);
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
		 sel.open("http://localhost:8080/OberflaechenentwurfTest/index.html");
			 
		 //Überprüfung der Eingaben des Benutzernamens und Passwortes
		 //für Test-Szenarium: admin/admin - Kombination		 
		 
		//Wenn Eingaben richtig
		 sel.type("username", "admin");
		 sel.type("textfield2", "admin");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Willkommen Systemadministrator"));
		 assertTrue(sel.isTextPresent("Herr admin"));
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
		 sel.open("http://localhost:8080/OberflaechenentwurfTest/index.html");
			 
		 //Überprüfung der Eingaben des Benutzernamens und Passwortes
		 //für Test-Szenarium: sl/sl - Kombination		 
		 
		//Wenn Eingaben richtig
		 sel.type("username", "sl");
		 sel.type("textfield2", "sl");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie auswählen"));
		 assertTrue(sel.isTextPresent("Herr sl"));
		 
		 sel.click("link=Aspirin");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie anzeigen"));
		 assertTrue(sel.isTextPresent("Herr sl"));		 
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
		 sel.open("http://localhost:8080/OberflaechenentwurfTest/index.html");
			 
		 //Überprüfung der Eingaben des Benutzernamens und Passwortes
		 //für Test-Szenarium: sl/sl - Kombination
		 		 
		//Wenn Eingaben richtig
		 sel.type("username", "sa");
		 sel.type("textfield2", "sa");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie auswählen"));
		 assertTrue(sel.isTextPresent("Herr sa"));
		 
		 
		 sel.click("link=Aspirin");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie anzeigen"));
		 assertTrue(sel.isTextPresent("Herr sa"));		 
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
		 sel.open("http://localhost:8080/OberflaechenentwurfTest/index.html");
			 
		 //Überprüfung der Eingaben des Benutzernamens und Passwortes
		 //für Test-Szenarium: sysop/sysop - Kombination		 
		 
		//Wenn Eingaben richtig
		 sel.type("username", "sysop");
		 sel.type("textfield2", "sysop");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Willkommen Systemadministrator"));
		 assertTrue(sel.isTextPresent("Herr sysop"));	
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
		 sel.open("http://localhost:8080/OberflaechenentwurfTest/index.html");
			 
		 //Überprüfung der Eingaben des Benutzernamens und Passwortes
		 //für Test-Szenarium: stat/stat - Kombination		 
		 
		//Wenn Eingaben richtig
		 sel.type("username", "stat");
		 sel.type("textfield2", "stat");
		 sel.click("Submit");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie anzeigen"));
		 assertTrue(sel.isTextPresent("Herr stat"));
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
	
	 @AfterClass
	 public static void tearDownAfterClass() 
	   {
         //Stoppen von Selenium
	     sel.stop();
	   }
	
}//SeleniumTestLogin