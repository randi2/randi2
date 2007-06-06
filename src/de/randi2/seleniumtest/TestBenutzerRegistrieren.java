package de.randi2.seleniumtest;

import com.thoughtworks.selenium.*;

//import de.randi2.utility.*;
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
         //Selenium wird gestartet
		 sel = new DefaultSelenium("localhost",4444, "*iexplore C:/Programme/Internet Explorer/iexplore.exe", "http://www.iap.hs-heilbronn.de:8080/randinightly/");
		 sel.start();
	}	
	
	
	
	/**
	 * Test Benutzer anlegen
	 */
	@Test
	public void testBenutzerAnlegen(){
		
		sel.open("http://www.iap.hs-heilbronn.de:8080/randinightly/index.jsp");
		
	    sel.click("//input[@value='Benutzer registrieren']");
	    sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Benutzer anlegen"));
		assertTrue(sel.isTextPresent("Haftungsausschluss"));
		
		sel.click("//input[@value='Akzeptieren']");
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
		
		sel.click("abbrechen");
		sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Herzlich Willkommen"));
	}
	
	@Test
	public void falschesZentrumspasswort(){
		sel.open("http://www.iap.hs-heilbronn.de:8080/randinightly/index.jsp");
		
		sel.click("//input[@value='Benutzer registrieren']");
		sel.waitForPageToLoad("30000");
	
		sel.click("//input[@value='Akzeptieren']");
		sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Benutzer anlegen"));
		assertTrue(sel.isTextPresent("Zentrum suchen"));
		
		sel.type("zentrum_passwort1", "asdasd");
	
		sel.click("bestaetigen1");
		sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Falsches Zentrumpasswort"));
		
		sel.click("abbrechen");
		sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Benutzer anlegen"));
		assertTrue(sel.isTextPresent("Haftungsausschluss"));
		
		sel.click("abbrechen");
		sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		
	}
	
	@Test
	public void korrektesZentrumspasswort(){
		sel.open("http://www.iap.hs-heilbronn.de:8080/randinightly/index.jsp");
		
		sel.click("//input[@value='Benutzer registrieren']");
		sel.waitForPageToLoad("30000");
	
		sel.click("//input[@value='Akzeptieren']");
		sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Benutzer anlegen"));
		assertTrue(sel.isTextPresent("Zentrum suchen"));
		
		sel.type("zentrum_passwort1", "nch1!$knochen80");
	
		sel.click("bestaetigen1");
		sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Benutzer anlegen"));
		
		
		
	}
	
	
	
	 @AfterClass
	 public static void tearDownAfterClass() 
	   {
         //Stoppen von Selenium
	     sel.stop();
	   }
	
}//SeleniumTestLogin
