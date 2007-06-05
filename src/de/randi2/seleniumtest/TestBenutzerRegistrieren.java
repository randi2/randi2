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
	 * Test Benutzer anlegen
	 */
	@Test
	public void testBenutzerAnlegen(){
		
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
	
	
	
	 @AfterClass
	 public static void tearDownAfterClass() 
	   {
         //Stoppen von Selenium
	     sel.stop();
	   }
	
}//SeleniumTestLogin
