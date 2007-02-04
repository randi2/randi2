package de.randi2.junittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.randi2.model.fachklassen.beans.PersonBean;

/**
 * Die JUnit Klasse PersonBeanTest testet alle SetterMethoden der Klasse PersonBean. Auf ein Testen
 * der getter-Methoden kann verzichtet werden, da dortige Fehler auf Kompilierebene liegen m�ssten.
 * 
 * @author Tino Noack [tino.noack@web.de]
 * @version $Id $
 */
public class PersonBeanTest {
	
	private PersonBean testPB; //Objekt der Klasse PersonBean

	/**
	 * Erzeugt eine neue Instanz der Klasse PersonBean.
	 */
	@Before
	public void setUp() throws Exception {
		
		testPB = new PersonBean();
	}

	/**
	 * Weist dem PersonBean-Objekt den Wert "null" zu.
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		
		testPB=null;
	}

	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Emailparameter null ist (da Pflichtfeld).
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testSetEmailNull() {
		
		testPB.setEmail(null);
	}

	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Emailparameter ein leerer String ist.
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testSetEmailLeer() {
		
		testPB.setEmail("");
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Emailparameter laenger als in der Definition vereinbart ist.
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testSetEmailUeberlang() {
		
		testPB.setEmail("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa."+
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaa"+
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+
				"aaaaaaaaaaaaaaaaaaaa");
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Emailparameter nicht per Definition zugelassene Zeichen enthaelt.
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testSetEmailSonderzeichen() {
		
		testPB.setEmail("���.�@&$.---");
	}
	/**
	 * Testet, ob eine Exception geworfen wird, obwohl der 
	 * Emailparameter korrekt per Definition ist.
	 */
	@Test
	public void testSetEmailKorrekt() {
		
		try{
		testPB.setEmail("bla.blub@bla-blub.net");
		testPB.setEmail("a.b@d.de");
		
		}
		catch(Exception e){
			fail("Exception geworfen bei korrekter Emailadresse.");}
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Faxparameter ein leerer String ist.
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testSetFaxLeer() {
		testPB.setFax("");
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Faxparameter mehr oder weniger Zeichen enthaelt als per Definition vereinbart.
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testSetFaxLaenge() {
	
		testPB.setFax("1/1");
		testPB.setFax("1/1234");
		testPB.setFax("1/1234567890123456");
		testPB.setFax("123/1");
		testPB.setFax("123/1234567890123456");
		testPB.setFax("12345678901/1");
		testPB.setFax("12345678901/1234");
		testPB.setFax("12345678901/1234567890123456");
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Faxparameter nicht per Definition zugelassene Zeichen enthaelt.
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testSetFaxSonderzeichen() {
		testPB.setFax("we/ergfe");
		testPB.setFax("�$�%.)(9");
		testPB.setFax("0908/56477-");
	}
	/**
	 * Testet, ob eine Exception geworfen wird, obwohl der 
	 * Faxparameter korrekt per Definition ist.
	 */
	@Test
	public void testSetFaxKorrekt() {
		try{
			testPB.setFax(null);
			testPB.setFax("012/123456");
			testPB.setFax("23/35682345678");
			
			}
			catch(Exception e){
				fail("Exception geworfen bei korrekter Faxnummer.");}
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Geschlechtsparameter nicht per Definition zugelassen ist.
	 */
	@Test (expected= IllegalArgumentException.class)
	public void testSetGeschlechtFalsch() {
		
			testPB.setGeschlecht(' ');
			testPB.setGeschlecht('0');
	}
	/**
	 * Testet, ob eine Exception geworfen wird, obwohl der 
	 * Geschlechtsparameter korrekt per Definition ist.
	 */
	@Test 
	public void testSetGeschlechtKorrekt() {
		
		try{
			testPB.setGeschlecht('w');
			testPB.setGeschlecht('m');
			
			}
			catch(Exception e){
				fail("Exception geworfen bei korrekter Geschlechtseingabe.");}
	}
	
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Handynummerparameter ein leerer String ist.
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testSetHandynummerLeer() {
		testPB.setHandynummer("");
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Handynummerparameter laenger oder kuerzer als per Definition vereinbart ist.
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testSetHandynummerLaenge() {
	
		testPB.setHandynummer("1/1");
		testPB.setHandynummer("1/1234");
		testPB.setHandynummer("1/1234567890123456");
		testPB.setHandynummer("123/1");
		testPB.setHandynummer("123/1234567890123456");
		testPB.setHandynummer("12345678901/1");
		testPB.setHandynummer("12345678901/1234");
		testPB.setHandynummer("12345678901/1234567890123456");
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Handynummerparameter nicht zugelassene Sonderzeichen enthealt.
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testSetHandynummerSonderzeichen() {
		testPB.setHandynummer("we/ergfe");
		testPB.setHandynummer("�$�%.)(9");
		testPB.setHandynummer("0908/56477-");
	}
	/**
	 * Testet, ob eine Exception geworfen wird, obwohl der 
	 * Handynummerparameter korrekt per Definition ist.
	 */
	@Test
	public void testSetHandynummerKorrekt() {
		try{
			testPB.setHandynummer(null);
			testPB.setHandynummer("012/123456");
			testPB.setHandynummer("23678/35682345678");
			
			}
			catch(Exception e){
				fail("Exception geworfen bei korrekter Faxnummer.");}
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Nachnameparameter null ist (da Pflichtfeld).
	 */
	@Test (expected= IllegalArgumentException.class)
	public void testSetNachnameNull() {
		testPB.setNachname(null);
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Nachnameparameter ein leerer String ist.
	 */
	@Test (expected= IllegalArgumentException.class)
	public void testSetNachnameLeer() {
		testPB.setNachname("");
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Nachnameparameter laenger oder kuerzer als per Definition vereinbart ist.
	 */
	@Test (expected= IllegalArgumentException.class)
	public void testSetNachnameLaenge() {
		testPB.setNachname("X");
		testPB.setNachname("abcdefghijklmopqrstuvwxyzabcdefghijklmopqrstuvwxyzabcdefghijklmopqrstuvwxyz");
	}
	/**
	 * Testet, ob eine Exception geworfen wird, obwohl der 
	 * Nachnameparameter korrekt per Definition ist.
	 */
	@Test
	public void testSetNachnameKorrekt() {
		
		try{
			testPB.setNachname("Jameson");
			testPB.setNachname("Engelen-Kefer");
			testPB.setNachname("Anding Rost");}
			catch(Exception e){
				fail("Exception aufgetreten trotz richtiger Parameter.");
			}
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Telefonnummerparameter null ist (da Pflichtfeld).
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testSetTelefonnummerNull() {
		testPB.setTelefonnummer(null);
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Telefonnummerparameter ein leerer String ist.
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testSetTelefonnummerLeer() {
		testPB.setTelefonnummer("");
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Telefonnummerparameter laenger oder kuerzer als per Definition vereinbart ist.
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testSetTelefonnummerLaenge() {
	
		testPB.setTelefonnummer("1/1");
		testPB.setTelefonnummer("1/1234");
		testPB.setTelefonnummer("1/1234567890123456");
		testPB.setTelefonnummer("123/1");
		testPB.setTelefonnummer("123/1234567890123456");
		testPB.setTelefonnummer("12345678901/1");
		testPB.setTelefonnummer("12345678901/1234");
		testPB.setTelefonnummer("12345678901/1234567890123456");
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Telefonnummerparameter nicht zugelassene Sonderzeichen enthealt.
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testSetTelefonnummerSonderzeichen() {
		testPB.setTelefonnummer("we/ergfe");
		testPB.setTelefonnummer("�$�%.)(9");
		testPB.setTelefonnummer("0908/56477-");
	}
	/**
	 * Testet, ob eine Exception geworfen wird, obwohl der 
	 * Telefonnummerparameter korrekt per Definition ist.
	 */
	@Test
	public void testSetTelefonnummerKorrekt() {
		try{
			testPB.setTelefonnummer("012/123456");
			testPB.setTelefonnummer("23/35682345678");
			
			}
			catch(Exception e){
				fail("Exception geworfen bei korrekter Faxnummer.");}
	}

	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Titelparameter nicht der Definition entspricht.
	 */
	@Test (expected= IllegalArgumentException.class)
	public void testSetTitelFalsch() {
		
		testPB.setTitel("");
		testPB.setTitel("123");
	}
	/**
	 * Testet, ob eine Exception geworfen wird, obwohl der 
	 * Titelparameter korrekt per Definition ist.
	 */
	@Test
	public void testSetTitelKorrekt() {
		
		try{
		testPB.setTitel(null);
		testPB.setTitel("Prof.");
		testPB.setTitel("Dr.");
		testPB.setTitel("Prof. Dr.");}
		catch(Exception e){
			fail("Exception aufgetreten trotz richtiger Parameter.");
		}
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Vornameparameter null ist (da Pflichtfeld).
	 */
	@Test (expected= IllegalArgumentException.class)
	public void testSetVornameNull() {
		testPB.setVorname(null);
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Vornameparameter ein leerer String ist.
	 */
	@Test (expected= IllegalArgumentException.class)
	public void testSetVornameLeer() {
		testPB.setVorname("");
	}
	/**
	 * Testet, ob eine IllegalArgumentException geworfen wird, wenn der 
	 * Vornameparameter laenger oder kuerzer als per Definition vereinbart ist.
	 */
	@Test (expected= IllegalArgumentException.class)
	public void testSetVornameLaenge() {
		testPB.setVorname("X");
		testPB.setVorname("abcdefghijklmopqrstuvwxyzabcdefghijklmopqrstuvwxyzabcdefghijklmopqrstuvwxyz");
	}
	/**
	 * Testet, ob eine Exception geworfen wird, obwohl der 
	 * Vornameparameter korrekt per Definition ist.
	 */
	@Test
	public void testSetVornameKorrekt() {
		
		try{
			testPB.setVorname("Markus");
			testPB.setVorname("Jan Josef");
			testPB.setVorname("Elisabeth-Sophie");}
			catch(Exception e){
				fail("Exception aufgetreten trotz richtiger Parameter.");
			}
	}

}
