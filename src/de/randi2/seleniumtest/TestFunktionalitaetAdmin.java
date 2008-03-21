package de.randi2.seleniumtest;

import com.thoughtworks.selenium.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Release 2, SWP3:  Seleniumtest fuer die Funktionalitaeten des 
 *                   Admins mit Aufruf der berechtigten Seite(n)
 *                   des Benutzers.
 *                   Es wird davon ausgegangen, dass der Administrator
 *                   erfolgreich am System mit folgenden Daten anmeldet:
 *                   administrator/1$administrator - Kombination.
 * @author Nadine Zwink
 * @date 15.05.2007
 */
public class TestFunktionalitaetAdmin{
	private static Selenium sel;
	
	@BeforeClass
	public static void setUpBeforeClass(){
         //Selenium wird gestartet
		 sel = new DefaultSelenium("localhost",4444, "*firefox C:/Programme/Mozilla Firefox/firefox.exe", "http://localhost:8080/RANDI2/");
		 sel.start();
	}	
	
	
	/**
	 * Pruefen, ob der Administrator auf die Seite Daten aendern 
	 * gelangt, wenn der den entsprechenden Manuepunkt waehlt.
	 * Pruefen, ob der Daten aendern kann. 
	 */
	@Test
    public void pruefeDatenAendern() {

		 //Seite oeffnen
		 sel.open("http://localhost:8080/RANDI2/index.jsp");
			 
		 //Überprüfung der Eingaben von Benutzername und Passwort
		 //für das Test-Szenarium: administrator/1$administrator - Kombination		 
		  
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
			
		 //Überprüfung, ob der Administartor auf die Seite mit 
		 //der Benutzer-Liste weitergeleitet wird.
		 sel.click("link=Benutzer anzeigen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Benutzer-Liste"));
		 		 
		 
		 //Überprüfung der Eingaben des zu suchenden Benutzers fuer das
		 //Test-Szenarium mit folgenden Eingabedaten:
		 //Nachname - ‚Maier’
		 //Vorname – ‚Hans’
		 //E-Mail – ‚blabla@hs-heilbronn.de’
		 //Institut - ‘Medizinische Informatik’
		 //Login-Name – ‘Einloggen’
		
		 /**
		 sel.type("name", "Maier");
		 sel.type("vorname", "Hans");
		 sel.type("loginname", "Einloggen");
		 sel.type("email", "blabla@hs-heilbronn.de");
		 sel.type("Institut", "Medizinische Informatik");
		 sel.click("Aktualisieren");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
		 */	 
		 
		 /**
		  * Pruefen, ob Funktionalitaeten der Benutzer-Liste korrekt
		  * funktionieren.
		  */
		 
		 //Benutzer anzeigen
		 sel.click("link=anzeigen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Admin-Liste"));
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
		 
		 sel.click("zurueck");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Benutzer-Liste"));
		 
		 
		 //Benutzer sperren
		 sel.click("link=sperren");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Benutzer sperren"));
		 assertTrue(sel.isTextPresent("System"));
		 		 
		 sel.click("entsp_ja");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Benutzer-Liste"));
		
		 /**sel.click("entsp_nein");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Benutzer-Liste"));
		 */
		 
		 //benutzer loeschen
		 sel.click("link=löschen");
		 sel.waitForPageToLoad("30000");
		 //assertTrue(sel.isTextPresent("Herzlich Willkommen")); 
		 
		 //Logout
		 sel.clickAt("logout_link", "Logout");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 
  	}//pruefeDatenAendern()
		
	
	
	/**
	 * Pruefen, ob der Administrator auf die Seite Benutzer anzeigen 
	 * gelangt, wenn der den entsprechenden Manuepunkt waehlt.
	 * 
	 * Pruefen, ob bei der Suche nach einem Benutzer die Dateneingaben
	 * korrekt sind. 
	 * 
	 * Pruefen, ob alle Daten zu einem bestimmten Benutzer angezeigt 
	 * werden.
	 * 
	 * Pruefen, ob ein Benutzer gesperrt werden kann.
	 * 
	 * Pruefen, ob ein Benutzer geloescht werden kann.
	 
	@Test
    public void pruefeBenutzerAnzeigen() {
 
		 //Seite oeffnen
		 sel.open("http://localhost:8080/RANDI2/global_welcome.jsp");
			 
		 	
		 //Überprüfung, ob der Administartor auf die Seite mit 
		 //der Benutzer-Liste weitergeleitet wird.
		 sel.click("link=Benutzer anzeigen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Benutzer-Liste"));
		 		 
		 
		 //Überprüfung der Eingaben des zu suchenden Benutzers fuer das
		 //Test-Szenarium mit folgenden Eingabedaten:
		 //Nachname - ‚Maier’
		 //Vorname – ‚Hans’
		 //E-Mail – ‚blabla@hs-heilbronn.de’
		 //Institut - ‘Medizinische Informatik’
		 //Login-Name – ‘Einloggen’
		
		 /**
		 sel.type("name", "Maier");
		 sel.type("vorname", "Hans");
		 sel.type("loginname", "Einloggen");
		 sel.type("email", "blabla@hs-heilbronn.de");
		 sel.type("Institut", "Medizinische Informatik");
		 sel.click("Aktualisieren");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
		 */	 
		 
		 /**
		  * Pruefen, ob Funktionalitaeten der Benutzer-Liste korrekt
		  * funktionieren.
		  
		 
		 //Benutzer anzeigen
		 sel.click("link=anzeigen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Admin-Liste"));
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
		 
		 sel.click("zurueck");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Benutzer-Liste"));
		 
		 
		 //Benutzer sperren
		 sel.click("link=sperren");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Benutzer sperren"));
		 assertTrue(sel.isTextPresent("System"));
		 		 
		 sel.click("entsp_ja");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Benutzer-Liste"));
		
		 sel.click("entsp_nein");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Benutzer-Liste"));
		 
		 
		 sel.click("link=löschen");
		 sel.waitForPageToLoad("30000");
		 //assertTrue(sel.isTextPresent("Herzlich Willkommen"));		 
		 
    }//pruefeBenutzerAnzeigen()
	*/
	
	/**
	 * Pruefen, ob der Administrator auf die Seite Zentren anzeigen 
	 * gelangt, wenn der den entsprechenden Manuepunkt waehlt.
	 */
	@Test
    public void pruefeZentrenAnzeigen() {
 /**
		//Seite oeffnen
		 sel.open("http://localhost:8080/RANDI2/index.jsp");
			 
		 //Überprüfung der Eingaben von Benutzername und Passwort
		 //für das Test-Szenarium: administrator/1$administrator - Kombination		 
		 
		 
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
		 selenium.click("link=Zentren anzeigen");
		 
		 //Logout
		 sel.clickAt("logout_link", "Logout");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
	*/	 
	}//pruefeZentrenAnzeigen()

	
	
	/**
	 * Pruefen, ob der Administrator auf die Seite Zentrum anlegen 
	 * gelangt, wenn der den entsprechenden Manuepunkt waehlt.
	 * 
	 * Pruefen, ob die Eingabe des Zentrums korrekt eingegeben wurden.
	 */
	@Test
    public void pruefeZentrumAnlegen() {

		//Seite oeffnen
		 sel.open("http://localhost:8080/RANDI2/index.jsp");
			 
		 //Überprüfung der Eingaben von Benutzername und Passwort
		 //für das Test-Szenarium: administrator/1$administrator - Kombination		 
		 
		 
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
		 
		 //Überprüfung, ob der Administartor auf die Seite mit 
		 //der Benutzer-Liste weitergeleitet wird.
		 sel.click("link=Zentrum anlegen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("neues Zentrum anlegen"));
		 
		 
		 //Überprüfung der Eingaben des neuen Zentrums fuer das
		 //Test-Szenarium mit folgenden Eingabedaten:
		 //Nachname - ‚Maier’
		 //Vorname – ‚Hans’
		 //Telefonnummer – ‚123456’
		 //Fax – ‚111111’
		 //E-Mail – ‚blabla@hs-heilbronn.de’
		 //Name der Institution – ‚EinInstitut’
		 //Name der genauen Abteilung – ‚Radiologie’
		 //Strasse – ‚Hauptstrasse’
		 //Hausnummer – ‚1’
		 //PLZ – ‚71711’
		 //Ort - ‚Murr’
		 
		 
		 //Überprüfung der korrekten Eingabe des Zentrums
		 /**sel.type("nachname", "Maier");
		 sel.type("vorname", "Hans");
		 sel.type("telefon", "123456");
		 sel.type("fax", "111111");
		 sel.type("Name_Institution", "EinInstitut");
		 sel.type("Zentrum_Abteilung", "Radiologie");
		 sel.type("email", "blabla@hs-heilbronn.de");
		 sel.type("strasse", "Hauptstrasse");
		 sel.type("hausnummer", "1");
		 sel.type("PLZ", "71711");
		 sel.type("Ort", "Murr");
		 sel.click("passwort_anfordern");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
		 
		 //Pflichtfelder sind leer
		 sel.type("nachname", "");
		 sel.type("vorname", "");
		 sel.type("telefon", "");
		 sel.type("fax", "111111");
		 sel.type("Name_Institution", "");
		 sel.type("Zentrum_Abteilung", "");
		 sel.type("email", "blabla@hs-heilbronn.de");
		 sel.type("strasse", "");
		 sel.type("hausnummer", "");
		 sel.type("PLZ", "");
		 sel.type("Ort", "");
		 sel.click("passwort_anfordern");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
		 
		 //Faxnummer leer
		 sel.type("nachname", "Maier");
		 sel.type("vorname", "Hans");
		 sel.type("telefon", "123456");
		 sel.type("fax", "");
		 sel.type("Name_Institution", "EinInstitut");
		 sel.type("Zentrum_Abteilung", "Radiologie");
		 sel.type("email", "blabla@hs-heilbronn.de");
		 sel.type("strasse", "Hauptstrasse");
		 sel.type("hausnummer", "1");
		 sel.type("PLZ", "71711");
		 sel.type("Ort", "Murr");
		 sel.click("passwort_anfordern");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
		 
		 //falsche Eingaben
		 sel.type("nachname", "N");
		 sel.type("vorname", "V");
		 sel.type("telefon", "07144");
		 sel.type("fax", "0711");
		 sel.type("Name_Institution", "I");
		 sel.type("Zentrum_Abteilung", "A");
		 sel.type("email", "abc@1");
		 sel.type("strasse", "S");
		 sel.type("hausnummer", "1");
		 sel.type("PLZ", "123");
		 sel.type("Ort", "O");
		 sel.click("passwort_anfordern");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
		 
		 //falsche Eingaben
		 sel.type("nachname", "Stephanie-Wurst-KeineAhnung-Stephanie-Wurst-KeineAhnung");
		 sel.type("vorname", "Hans-Peter-Wurst-KeineAhnung-Hans-Peter-Wurst-KeineAhnung");
		 sel.type("telefon", "0714407141123456789007246789");
		 sel.type("fax", "0711123456789007144071412345");
		 sel.type("Name_Institution", "Name einer Institution als neuen Eintrag erfassen. Neues Zentrum einfügen und speichern");
		 sel.type("Zentrum_Abteilung", "Abteilung für medizinische Biometrie und Epidemiologie sowie Abteilung für Bioinformatik");
		 sel.type("email", "‚abcdefghijklmnopqrstuvwzyz@1*1234567890@12345" +
		 		"678901abcdefghijklmnopqrstuvwzyz@1*1234567890@12345678901abcdefghijklm" +
		 		"nopqrstuvwzyz@1*1234567890@12345678901abcdefghijklmnopqrstuvwzyz@1*1" +
		 		"234567890@12345678901abcdefghijklmnopqrstuvwzyz@1*1234567890@123456" +
		 		"78901234567890");
		 sel.type("strasse", "Bahnhofsstrasse Bahnhofsstrasse Bahnhofsstrasse Bahnhofsstrasse");
		 sel.type("hausnummer", "1");
		 sel.type("PLZ", "71711");
		 sel.type("Ort", "Stuttgart in Baden-Württemberg Stuttgart in Baden-Württemberg");
		 sel.click("passwort_anfordern");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
		 
		 //falsche Eingaben
		 sel.type("nachname", "Maier");
		 sel.type("vorname", "Hans");
		 sel.type("telefon", "123456abc");
		 sel.type("fax", "abc0711");
		 sel.type("Name_Institution", "EinInstitut");
		 sel.type("Zentrum_Abteilung", "Radiologie");
		 sel.type("email", "blabla@hs-heilbronn.de");
		 sel.type("strasse", "Hauptstrasse");
		 sel.type("hausnummer", "1");
		 sel.type("PLZ", "5555cas");
		 sel.type("Ort", "Murr");
		 sel.click("passwort_anfordern");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
		 
		 //Pruefen Hausnummer
		 sel.type("nachname", "Maier");
		 sel.type("vorname", "Hans");
		 sel.type("telefon", "123456");
		 sel.type("fax", "111111");
		 sel.type("Name_Institution", "EinInstitut");
		 sel.type("Zentrum_Abteilung", "Radiologie");
		 sel.type("email", "blabla@hs-heilbronn.de");
		 sel.type("strasse", "Hauptstrasse");
		 sel.type("hausnummer", "3a");
		 sel.type("PLZ", "71711");
		 sel.type("Ort", "Murr");
		 sel.click("passwort_anfordern");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Willkommen Systemadministrator"));
		 
		 
		 sel.click("abbrechen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Willkommen Systemadministrator"));
		 */		 
		 
		 //Logout
		 sel.clickAt("logout_link", "Logout");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 
	}//pruefeZentrumAnlegen()
	
	
	/**
	 * Pruefen, ob der Administrator auf die Seite Studien anzeigen 
	 * gelangt, wenn der den entsprechenden Manuepunkt waehlt.
	 * 
	 * Pruefen, ob Studien angezeigt werden.
	 * 
	 * Pruefen, ob der Administrator eine Studie auswaehlen kann durch
	 * Eingabe der richtigen Eingabedaten.
	 * 
	 * Pruefen, ob der Administrator Daten einer bestimmten Studie 
	 * einsehen kann.
	 * 
	 * Pruefen, ob der Administrator eine Studie abbrechen kann.
	 */
	@Test
	public void testStudienAnzeigen(){
//		Seite oeffnen
		 sel.open("http://localhost:8080/RANDI2/index.jsp");
			 
		 //Überprüfung der Eingaben von Benutzername und Passwort
		 //für das Test-Szenarium: administrator/1$administrator - Kombination		 
		 
		 
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
		 
		sel.click("link=Studien anzeigen");
	    sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Studie auswählen"));
		
		//Überprüfung, ob Studie fuer das
		//Test-Szenarium mit folgenden Eingabedaten angezeigt wird:
		//Name – ‚Teststudie’
		//Status – ‚gestartet’
		//Zentrum – ‚Universitätsklinikum Heidelberg’
		
		//Überprüfung der korrekten Eingaben des zu suchenden Studie
		/** sel.type("name", "Teststudie");
		 sel.type("Status", "gestartet");
		 sel.type("Zentrum", "Universitätsklinikum Heidelberg");
		 sel.click("Aktualisieren");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));		
		*/
		
		//Pflichtfeld leer
		 /**sel.type("name", "");
		 //sel.type("Status", "gestartet");
		 //sel.type("Zentrum", "Universitätsklinikum Heidelberg");
		 sel.click("//input[@value='Aktualisieren']");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
		 
		 //Falsche Eingabe
		 sel.type("name", "A");
		 //sel.type("Status", "gestartet");
		 //sel.type("Zentrum", "Universitätsklinikum Heidelberg");
		 sel.click("Aktualisieren");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));	
		 
		 //falsche Eingabe
		 sel.type("name", "Untersuchung des Blutbildes auf weiße und rote Blutkörperchen");
		 //sel.type("Status", "gestartet");
		 //sel.type("Zentrum", "Universitätsklinikum Heidelberg");
		 sel.click("Aktualisieren");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));	
		 */
		
		 //Studie anzeigen
		 sel.click("link=Aspirin");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie ansehen"));
		 
		 
		 sel.click("statistik");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Auswertungsmethoden zu einer Statistik"));
		 		 
		 sel.click("auswertungsmethode_waehlen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
			
		
		 /**
		 //Studie abbrechen
		 sel.click("abbrechen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
		 
		 sel.click("Ja");
		 sel.waitForPageToLoad("3000");
		 assertTrue(sel.isTextPresent(""));
		 
		 sel.click("Nein");
		 sel.waitForPageToLoad("3000");
		 assertTrue(sel.isTextPresent(""));
		*/
		 
	}//testStudienAnzeigen
	
	
	/**
	 * Pruefen, ob der Administrator auf die Seite Studien anzeigen 
	 * gelangt, wenn der den entsprechenden Manuepunkt waehlt.
	 * 
	 * Pruefen, ob Studien angezeigt werden.
	 * 
	 * Pruefen, ob der Administrator eine Studie auswaehlen kann durch
	 * Eingabe der richtigen Eingabedaten.
	 * 
	 * Pruefen, ob der Administrator Daten einer bestimmten Studie 
	 * einsehen kann.
	 * 
	 * Pruefen, ob der Administrator eine Studie abbrechen kann.
	 */
	@Test
	public void testStudienAnzeigen4(){
//		Seite oeffnen
		 sel.open("http://localhost:8080/RANDI2/index.jsp");
			 
		 //Überprüfung der Eingaben von Benutzername und Passwort
		 //für das Test-Szenarium: administrator/1$administrator - Kombination		 
		 
		 
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
		 
		sel.click("link=Studien anzeigen");
	    sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Studie auswählen"));
		
		//Überprüfung, ob Studie fuer das
		//Test-Szenarium mit folgenden Eingabedaten angezeigt wird:
		//Name – ‚Teststudie’
		//Status – ‚gestartet’
		//Zentrum – ‚Universitätsklinikum Heidelberg’
		
		//Überprüfung der korrekten Eingaben des zu suchenden Studie
		/** sel.type("name", "Teststudie");
		 sel.type("Status", "gestartet");
		 sel.type("Zentrum", "Universitätsklinikum Heidelberg");
		 sel.click("Aktualisieren");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));		
		*/
		
		//Pflichtfeld leer
		 /**sel.type("name", "");
		 //sel.type("Status", "gestartet");
		 //sel.type("Zentrum", "Universitätsklinikum Heidelberg");
		 sel.click("//input[@value='Aktualisieren']");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
		 
		 //Falsche Eingabe
		 sel.type("name", "A");
		 //sel.type("Status", "gestartet");
		 //sel.type("Zentrum", "Universitätsklinikum Heidelberg");
		 sel.click("Aktualisieren");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));	
		 
		 //falsche Eingabe
		 sel.type("name", "Untersuchung des Blutbildes auf weiße und rote Blutkörperchen");
		 //sel.type("Status", "gestartet");
		 //sel.type("Zentrum", "Universitätsklinikum Heidelberg");
		 sel.click("Aktualisieren");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));	
		 */
		
		 //Studie anzeigen
		 sel.click("link=Aspirin");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie ansehen"));
		 
					
		 sel.click("statistik");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Auswertungsmethoden zu einer Statistik"));
				 
		 sel.click("entsp_ja");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie ansehen"));		 
		  
		 
		 /**
		 //Studie abbrechen
		 sel.click("abbrechen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
		 
		 sel.click("Ja");
		 sel.waitForPageToLoad("3000");
		 assertTrue(sel.isTextPresent(""));
		 
		 sel.click("Nein");
		 sel.waitForPageToLoad("3000");
		 assertTrue(sel.isTextPresent(""));
		*/
		 
		 //Logout
		 sel.clickAt("logout_link", "Logout");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 
	}//testStudienAnzeigen4
	/**
	 * Pruefen, ob der Administrator auf die Seite Studien anzeigen 
	 * gelangt, wenn der den entsprechenden Manuepunkt waehlt.
	 * 
	 * Pruefen, ob Studien angezeigt werden.
	 * 
	 * Pruefen, ob der Administrator eine Studie auswaehlen kann durch
	 * Eingabe der richtigen Eingabedaten.
	 * 
	 * Pruefen, ob der Administrator Daten einer bestimmten Studie 
	 * einsehen kann.
	 * 
	 * Pruefen, ob der Administrator eine Studie abbrechen kann.
	 */
	@Test
	public void testStudienAnzeigen2(){
//		Seite oeffnen
		 sel.open("http://localhost:8080/RANDI2/index.jsp");
			 
		 //Überprüfung der Eingaben von Benutzername und Passwort
		 //für das Test-Szenarium: administrator/1$administrator - Kombination		 
		 
		 
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
		 
		sel.click("link=Studien anzeigen");
	    sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Studie auswählen"));
		
		
		 //Studie anzeigen
		 sel.click("link=Aspirin");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie ansehen"));
		 
		 
		 sel.click("randomisation");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studienergebnisse"));
		 assertTrue(sel.isTextPresent("Export"));
		 
		 sel.click("exp_csv");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
		 
	
		 
		 /**
		 //Studie abbrechen
		 sel.click("abbrechen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
		 
		 sel.click("Ja");
		 sel.waitForPageToLoad("3000");
		 assertTrue(sel.isTextPresent(""));
		 
		 sel.click("Nein");
		 sel.waitForPageToLoad("3000");
		 assertTrue(sel.isTextPresent(""));
		*/
		 
		 //Logout
		 sel.clickAt("logout_link", "Logout");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		 
	}//testStudienAnzeigen2
	
	
	/**
	 * Pruefen, ob der Administrator auf die Seite Studien anzeigen 
	 * gelangt, wenn der den entsprechenden Manuepunkt waehlt.
	 * 
	 * Pruefen, ob Studien angezeigt werden.
	 * 
	 * Pruefen, ob der Administrator eine Studie auswaehlen kann durch
	 * Eingabe der richtigen Eingabedaten.
	 * 
	 * Pruefen, ob der Administrator Daten einer bestimmten Studie 
	 * einsehen kann.
	 * 
	 * Pruefen, ob der Administrator eine Studie abbrechen kann.
	 */
	@Test
	public void testStudienAnzeigen3(){
//		Seite oeffnen
		 sel.open("http://localhost:8080/RANDI2/index.jsp");
			 
		 //Überprüfung der Eingaben von Benutzername und Passwort
		 //für das Test-Szenarium: administrator/1$administrator - Kombination		 
		 
		 
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
		 
		sel.click("link=Studien anzeigen");
	    sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Studie auswählen"));
		
		
		 //Studie anzeigen
		 sel.click("link=Aspirin");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie ansehen"));
		 
		 
		 sel.click("randomisation");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studienergebnisse"));
		 assertTrue(sel.isTextPresent("Export"));
		 
		 sel.click("exp_xls");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
				 		 
				 
		/** sel.click("Zurück");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Studie ansehen"));		 
		  */
		 
		 /**
		 //Studie abbrechen
		 sel.click("abbrechen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
		 
		 sel.click("Ja");
		 sel.waitForPageToLoad("3000");
		 assertTrue(sel.isTextPresent(""));
		 
		 sel.click("Nein");
		 sel.waitForPageToLoad("3000");
		 assertTrue(sel.isTextPresent(""));
		*/
		 
		 //Logout
		 sel.clickAt("logout_link", "Logout");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
	}//testStudienAnzeigen3
	
	
	/**
	 * Pruefen, ob der Administrator auf die Seite Studienleiter 
	 * anlegen gelangt, wenn der den entsprechenden Manuepunkt waehlt.
	 * 
	 * Pruefen, ob die Eingaben des neuen Studienleiters korrekt 
	 * eingegeben wurden.
	 */
	@Test
	public void testStudienleiterAnlegen(){
//		Seite oeffnen
		 sel.open("http://localhost:8080/RANDI2/index.jsp");
			 
		 //Überprüfung der Eingaben von Benutzername und Passwort
		 //für das Test-Szenarium: administrator/1$administrator - Kombination		 
		 
		 
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
		 
		 
		sel.click("link=Studienleiter anlegen");
	    sel.waitForPageToLoad("30000");
		assertTrue(sel.isTextPresent("Studienleiter anlegen"));
		
		//Überprüfung der Eingaben des neuen Studienleiters fuer das
		//Test-Szenarium mit folgenden Eingabedaten:
		//Titel – ‚Dr.’
		//Vorname – ‚Peter’
		//Nachname – ‚Maier’
		//Login-Name – ‚Admin’
		//E-Mail Adresse – ‚admin@uni-heidelberg.de’
		//Fax – ‚0711207123’
		//Telefonnummer – ‚07549992’
		//Telefonnummer (Mobil) – ‚01781234’
		//Telefonnummer (Sekretärin) – ‚07541231’
		
		//Überprüfung der korrekten Eingaben des neuen Studienleiters
		 //sel.type("Titel", "Dr.");
		/** sel.type("Vorname", "Peter");
		 sel.type("Nachname", "Maier");
		 //sel.type("Login-Name", "Admin");
		 //sel.type("EMail", "admin@uni-heidelberg.de");
		 sel.type("Fax", "0711207123");
		 sel.type("Telefon", "07549992");
		 //sel.type("Handy", "01781234");
		 //sel.type("TelSek", "07541231");
		 sel.click("Bestätigen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));		
		
		 //Pflichtfelder sind leer
		 //sel.type("Titel", "Dr.");
		 sel.type("Vorname", "");
		 sel.type("Nachname", "");
		 //sel.type("Login-Name", "");
		 //sel.type("EMail", "admin@uni-heidelberg.de");
		 sel.type("Fax", "0711207123");
		 sel.type("Telefon", "");
		 //sel.type("Handy", "01781234");
		 //sel.type("TelSek", "");
		 sel.click("Bestätigen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));	
		 
		 //Titel, Faxnummer, Handynummer, E-Mail Adresse sind leer
		 //sel.type("Titel", "Dr.");
		 sel.type("Vorname", "Peter");
		 sel.type("Nachname", "Maier");
		 //sel.type("Login-Name", "Admin");
		 //sel.type("EMail", "");
		 sel.type("Fax", "");
		 sel.type("Telefon", "07549992");
		 //sel.type("Handy", "");
		 //sel.type("TelSek", "07541231");
		 sel.click("Bestätigen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));	
		 
		 //falsche Eingaben
		 //sel.type("Titel", "Dr.");
		 sel.type("Vorname", "N");
		 sel.type("Nachname", "V");
		 //sel.type("Login-Name", "A");
		 //sel.type("EMail", "abc@1");
		 sel.type("Fax", "0711");
		 sel.type("Telefon", "07144");
		 //sel.type("Handy", "0179");
		 //sel.type("TelSek", "0713");
		 sel.click("Bestätigen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));	
		 
		 //falsche Eingaben
		 //sel.type("Titel", "Dr.");
		 sel.type("Vorname", "Hans-Peter-Wurst-KeineAhnung-Hans-Peter-Wurst-KeineAhnung");
		 sel.type("Nachname", "Stephanie-Wurst-KeineAhnung-Stephanie-Wurst-KeineAhnung");
		 //sel.type("Login-Name", "NeuerStudienleiter");
		 //sel.type("EMail", "abcdefghijklmnopqrstuvwzyz@1*1234567890@12345" +
		 	//	"678901abcdefghijklmnopqrstuvwzyz@1*1234567890@12345678901abcdefghijklm" +
		 		//"nopqrstuvwzyz@1*1234567890@12345678901abcdefghijklmnopqrstuvwzyz@1*1" +
		 		//"234567890@12345678901abcdefghijklmnopqrstuvwzyz@1*1234567890@123456" +
		 		//"78901234567890");
		 sel.type("Fax", "0711123456789007144071412345");
		 sel.type("Telefon", "0714407141123456789007246789");
		 //sel.type("Handy", "017707141123456789007246789");
		 //sel.type("TelSek", "0711143121123456789007246789");
		 sel.click("Bestätigen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));	
		 
		 //falsche Eingaben
		 //sel.type("Titel", "Dr.");
		 sel.type("Vorname", "Peter");
		 sel.type("Nachname", "Maier");
		 //sel.type("Login-Name", "Admin");
		 //sel.type("EMail", "admin@uni-heidelberg.de");
		 sel.type("Fax", "abc0711");
		 sel.type("Telefon", "123456abc");
		 //sel.type("Handy", "01781abc");
		// sel.type("TelSek", "0711112abc");
		 sel.click("Bestätigen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));	
		 
		 sel.click("Abbrechen");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent(""));
		 */	
		 
		 //Logout
		 sel.clickAt("logout_link", "Logout");
		 sel.waitForPageToLoad("30000");
		 assertTrue(sel.isTextPresent("Herzlich Willkommen"));
		
	}//testStudienleiterAnlegen
	
	
	 @AfterClass
	 public static void tearDownAfterClass() 
	   {
         //Stoppen von Selenium
	     sel.stop();
	   }
	
}//SeleniumTestFunktionalitaetAdmin

