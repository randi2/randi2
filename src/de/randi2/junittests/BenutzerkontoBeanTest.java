package de.randi2.junittests;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.randi2.datenbank.Filter;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.Rolle.Rollen;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;

/**
 * 
 * @author Nadine Zwink <nzwink@stud.hs-heilbronn.de>
 * @version $Id$
 */
public class BenutzerkontoBeanTest extends Filter {

	private BenutzerkontoBean aKonto, bKonto, cKonto;

	private GregorianCalendar ersterLogin, letzterLogin, ersterLoginB,
			letzterLoginB;

	private Rolle rolle, rolleB;

	private Rollen name, nameB;

	/**
	 * Method setUp() 
	 * Erzeugt eine neue Instanz der Klasse BenutzerkontoBean.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		aKonto = new BenutzerkontoBean();
	}

	/** 
	 * Method tearDown() 
	 * Weist dem BenutzerkontoBean-Objekt den Wert "null" zu.
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		aKonto = null;
	}

	/**
	 * Test method for setBenutzername()
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setBenutzername()}.
	 * 
	 * Ueberpruefung auf korrekt gesetzten Benutzername.
	 * Wenn Fehler, soll BenutzerkontoException geworfen werden.
	 * 
	 * @throws BenutzerkontoException
	 */
	@Test
	public final void testSetBenutzernameRichtig()
			throws BenutzerkontoException {
		aKonto.setBenutzername("administrator");
		assertTrue(aKonto.getBenutzername().equals("administrator"));
		aKonto.setBenutzername("studienleiter");
		assertTrue(aKonto.getBenutzername().equals("studienleiter"));
		aKonto.setBenutzername("sa@randi2.de");
		assertTrue(aKonto.getBenutzername().equals("sa@randi2.de"));
		aKonto.setBenutzername("systemoperator");
		assertTrue(aKonto.getBenutzername().equals("systemoperator"));
		aKonto.setBenutzername("statistiker");
		assertTrue(aKonto.getBenutzername().equals("statistiker"));
	}

	/**
	 * Test method for setBenutzername()
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setBenutzername()}.
	 * 
	 * Ueberpruefung auf erlaubte Zeichen im Benutzernamen.
	 * Wenn Fehler, soll BenutzerkontoException geworfen werden.
	 * 
	 * @throws BenutzerkontoException
	 */
	@Test
	public final void testSetBenutzernameErlaubteZeichen()
			throws BenutzerkontoException {
		aKonto.setBenutzername("hanswursthausen");
		assertTrue(aKonto.getBenutzername().equals("hanswursthausen"));
	}

	/**
	 * Test method for setBenutzename()
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setBenutzername()}.
	 * 
	 * Ueberpruefung, ob Benutzername gesetzt wurde.
	 * Wenn Fehler (Benutzername=Null), soll BenutzerkontoException 
	 * geworfen werden.
	 * 
	 * @throws BenutzerkontoException
	 */
	@Test(expected = BenutzerkontoException.class)
	public final void testSetBenutzernameNull() throws BenutzerkontoException {
		aKonto.setBenutzername(null);
	}


	/**
	 * Test method for setBenutzername()
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setBenutzername()}.
	 * 
	 * Ueberpruefung der Laenge des Benutzernamens.
	 * Fehler, wenn Benutzername kleiner als 6 bzw. mehr als 50 Zeichen hat.
	 * Wenn Fehler, soll BenutzerkontoException geworfen werden.
	 * 
	 * @throws BenutzerkontoException
	 */
	@Test(expected = BenutzerkontoException.class)
	public final void testSetBenutzernameLaenge() throws BenutzerkontoException {
		aKonto.setBenutzername("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"); // mehr als 50 Zeichen
		aKonto.setBenutzername("aaa"); // weniger als 6 Zeichen
	}

	/**
	 * Test method for setPasswort()
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setPasswort()}.
	 * 
	 * Ueberpruefung der Laenge des Passwortes.
	 * Fehler, wenn Passwort weniger als 6 Zeichen hat.
	 * Wenn Fehler, soll BenutzerkontoException geworfen werden.
	 * 
	 * @throws BenutzerkontoException
	 */
	@Test(expected = BenutzerkontoException.class)
	public final void testSetPasswortLaenge()
			throws BenutzerkontoException {
		aKonto.setPasswort("s");
	}



	/**
	 * Test method for setPasswort()
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setPasswort()}.
	 * 
	 * Ueberpruefung auf Korrektheit des Passwortes. Es muss mindestens
	 * 1 Ziffer oder 1 Sonderzeichen enthalten sein.
	 * Wenn Fehler (keine Ziffer oder kein Sonderzeichen vorhanden), 
	 * soll BenutzerkontoException geworfen werden.
	 * 
	 * @throws BenutzerkontoException
	 */
	@Test
	public final void testSetPasswortRichtig() throws BenutzerkontoException {
		aKonto.setPasswort("hans1wursthausen");
		assertTrue(aKonto.getPasswort().equals("hans1wursthausen"));
		aKonto.setPasswort("a§abcpasswort");
		assertTrue(aKonto.getPasswort().equals("a§abcpasswort"));
		aKonto.setPasswort("test2abc$abc");
		assertTrue(aKonto.getPasswort().equals("test2abc$abc"));
	}


	
	/**
	 * Test method for setPasswort()
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setPasswort()}.
	 * 
	 * Ueberpruefung, ob Passwort gesetzt wurde.
	 * Wenn Fehler (Passwort=Null), soll BenutzerkontoException 
	 * geworfen werden.
	 * 
	 * @throws BenutzerkontoException
	 */
	@Test(expected = BenutzerkontoException.class)
	public final void testSetPasswortNull() throws BenutzerkontoException {
		aKonto.setPasswort(null);
	}



	/**
	 * 
	 * Test method for equalsBenutzerkontoBean()
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#equalsBenutzerkontoBean()}.
	 * 
	 * Vergleich von zwei identischen BenutzerkontoBeans.
	 * Zwei Kontos sind identisch, wenn Benutzernamen identisch sind.
	 * Wenn Fehler, soll BenutzerkontoException bzw. PersonException
	 * geworfen werden.
	 * 
	 * @throws PersonException
	 * @throws BenutzerkontoException
	 */
	@Test
	public final void testEqualsBenutzerkontoBeanGleich()
			throws PersonException, BenutzerkontoException {
		String benutzername = "Hanswurst";
		String passwort = "dddd$dddsf1dfdsf";
		name = Rollen.ADMIN;
		rolle = Rolle.getAdmin();
		PersonBean benutzer = new PersonBean("wurst", "hans", null, 'm',
				"hans@wurst.de", "0222323333", "02222332333", null);
		PersonBean ansprechpartner = null;
		boolean gesperrt = false;
		ZentrumBean zentrum = new ZentrumBean();

		letzterLogin = new GregorianCalendar(2006, 11, 1);
		ersterLogin = new GregorianCalendar(2006, 11, 1);

		aKonto = new BenutzerkontoBean(benutzername, passwort, rolle, benutzer,
				ansprechpartner, gesperrt, zentrum, ersterLogin, letzterLogin);

		assertTrue(aKonto.equals(aKonto));
	}

	/**
	 * Test method for equalsBenutzerkontoBean()
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#equalsBenutzerkontoBean()}.
	 * 
	 * Vergleich von zwei verschiedenen BenutzerkontoBeans.
	 * Zwei Kontos sind nicht identisch, wenn Benutzernamen verschieden sind.
	 * Wenn Fehler, soll BenutzerkontoException bzw. PersonException
	 * geworfen werden.
	 * 
	 * @throws PersonException
	 * @throws BenutzerkontoException
	 */
	@Test
	public final void testEqualsBenutzerkontoBeanVerschieden()
			throws PersonException, BenutzerkontoException {
		String benutzername = "Hanswurst";
		String passwort = "dddd2323sfaf§f";
		name = Rollen.ADMIN;
		rolle = Rolle.getAdmin();
		PersonBean benutzer = new PersonBean();
		PersonBean ansprechpartner = new PersonBean();
		boolean gesperrt = false;
		ZentrumBean zentrum = new ZentrumBean();
		String day = "1";
		int tagLetzterLogin = Integer.parseInt(day);
		String month = "2";
		int monatLetzterLogin = Integer.parseInt(month);
		String year = "2006";
		int jahrLetzterLogin = Integer.parseInt(year);
		letzterLogin = new GregorianCalendar(jahrLetzterLogin,
				monatLetzterLogin - 1, tagLetzterLogin);
		ersterLogin = new GregorianCalendar(jahrLetzterLogin,
				monatLetzterLogin - 1, tagLetzterLogin);

		String benutzernameB = "EmmaWurst";
		String passwortB = "ddddccc4safsa§";
		nameB = Rollen.ADMIN;
		rolleB = Rolle.getAdmin();
		PersonBean benutzerB = new PersonBean();
		PersonBean ansprechpartnerB = new PersonBean();
		boolean gesperrtB = false;
		ZentrumBean zentrumB = new ZentrumBean();
		String dayB = "1";
		int tagLetzterLoginB = Integer.parseInt(dayB);
		String monthB = "3";
		int monatLetzterLoginB = Integer.parseInt(monthB);
		String yearB = "2005";
		int jahrLetzterLoginB = Integer.parseInt(yearB);
		letzterLoginB = new GregorianCalendar(jahrLetzterLoginB,
				monatLetzterLoginB - 1, tagLetzterLoginB);
		ersterLoginB = new GregorianCalendar(jahrLetzterLoginB,
				monatLetzterLoginB - 1, tagLetzterLoginB);

		aKonto = new BenutzerkontoBean(benutzername, passwort, rolle, benutzer,
				ansprechpartner, gesperrt, zentrum, ersterLogin, letzterLogin);
		bKonto = new BenutzerkontoBean(benutzernameB, passwortB, rolleB,
				benutzerB, ansprechpartnerB, gesperrtB, zentrumB, ersterLoginB,
				letzterLoginB);

		assertFalse(aKonto.equals(bKonto));
	}

	/**
	 * Test method for equalsBenutzerkontoBean()
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#equalsBenutzerkontoBean()}.
	 * 
	 * Ueberpruefung, ob Konto leer ist.
	 * Wenn Fehler (Konto leer), soll BenutzerkontoException bzw. PersonException
	 * geworfen werden.
	 *  
	 * @throws PersonException
	 * @throws BenutzerkontoException
	 */
	@Test
	public final void testEqualsBenutzerkontoBeanNull() throws PersonException,
			BenutzerkontoException {
		String benutzername = "Hanssdsadsd";
		String passwort = "dddd$sdas2";
		name = Rollen.ADMIN;
		rolle = Rolle.getAdmin();
		PersonBean benutzer = new PersonBean();
		PersonBean ansprechpartner = new PersonBean();
		boolean gesperrt = false;
		ZentrumBean zentrum = new ZentrumBean();
		String day = "1";
		int tagLetzterLogin = Integer.parseInt(day);
		String month = "2";
		int monatLetzterLogin = Integer.parseInt(month);
		String year = "2006";
		int jahrLetzterLogin = Integer.parseInt(year);
		letzterLogin = new GregorianCalendar(jahrLetzterLogin,
				monatLetzterLogin - 1, tagLetzterLogin);
		ersterLogin = new GregorianCalendar(jahrLetzterLogin,
				monatLetzterLogin - 1, tagLetzterLogin);

		aKonto = new BenutzerkontoBean(benutzername, passwort, rolle, benutzer,
				ansprechpartner, gesperrt, zentrum, ersterLogin, letzterLogin);
		cKonto = new BenutzerkontoBean("fsdfdsf", "sdfd$ss7sd", rolle, null,
				null, false, null, null, null);

		assertFalse(aKonto.equals(cKonto));
	}

	/**
	 * Test method for setErsterLogin()
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setErsterLogin()}.
	 * 
	 * Ueberpruefung Datum des ersten Logins.
	 * Wenn Fehler (Datum befindet sich in der Zukunft), soll 
	 * BenutzerkontoException geworfen werden.
	 */
	@Test
	public void testSetErsterLogin() {
		try {
			String day = "1";
			int tag = Integer.parseInt(day);
			String month = "2";
			int monat = Integer.parseInt(month);
			String year = "2006";
			int jahr = Integer.parseInt(year);
			ersterLogin = new GregorianCalendar(jahr, monat - 1, tag);
			aKonto.setErsterLogin(ersterLogin);
			assertTrue(aKonto.getErsterLogin().equals(ersterLogin));
		    assertFalse((new GregorianCalendar(Locale.GERMANY))
					.before(ersterLogin));
		} catch (Exception e) {
			fail("[testSetErsterLogin]Exception, wenn Zeit des ersten Login in der Zukunft liegt.");
		}
	}

	/**
	 * Test method for setGesperrt()
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setGesperrt()}.
	 * 
	 * Ueberpruefung, ob Konto gesperrt ist.
	 * Wenn Fehler, soll BenutzerkontoException geworfen werden.
	 */
	@Test
	public void testSetGesperrt() {
		aKonto.setGesperrt(true);
		assertTrue(aKonto.isGesperrt());
	}

	/**
	 * Test method for setLetzterLogin()
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setLetzterLogin()}.
	 * 
	 * Ueberpruefung Datum des letzten Logins.
	 * Wenn Fehler (Datum liegt in der Zukunft), soll 
	 * BenutzerkontoException geworfen werden.
	 */
	@Test
	public void testSetLetzterLogin() {
		try {		    
			String day = "1";
			int tag = Integer.parseInt(day);
			String month = "2";
			int monat = Integer.parseInt(month);
			String year = "2006";
			int jahr = Integer.parseInt(year);
			letzterLogin = new GregorianCalendar(jahr, monat - 1, tag);
			aKonto.setLetzterLogin(letzterLogin);
			assertTrue(aKonto.getLetzterLogin().equals(letzterLogin));
			assertFalse((new GregorianCalendar(Locale.GERMANY))
					.before(letzterLogin));
		} catch (Exception e) {
			fail("[testSetLetzterLogin]Exception, wenn Zeit des letzter Login in der Zukunft liegt.");
		}
	}

	/**
	 * Test method for setRolle()
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setRolle()}.
	 * 
	 * Ueberpruefung, ob Rolle gesetzt wurde.
	 * Wenn Fehler (Rolle=Null), soll BenutzerkontoException 
	 * geworfen werden.
	 * 
	 * @throws BenutzerkontoException
	 */
	@Test(expected = BenutzerkontoException.class)
	public void testSetRolleNull() throws BenutzerkontoException {
		aKonto.setRolle(null);
	}

	/**
	 * Test method for setRolle()
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setRolle()}.
	 * 
	 * Ueberpruefung, ob richtige Rollen gesetzt werden.
	 * Wenn Fehler, soll BenutzerkontoException geworfen werden.
	 */
	@Test
	public void testSetRolle() {
		try {
			aKonto.setRolle(rolle.getAdmin());
			aKonto.setRolle(rolle.getStatistiker());
			aKonto.setRolle(rolle.getStudienarzt());
			aKonto.setRolle(rolle.getStudienleiter());
			aKonto.setRolle(rolle.getSysop());
		} catch (Exception e) {
			fail("[FEHLER]testSetRolle() sollte keine Exception auslösen");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setId()}.
	 * 
	 * Ueberpruefung, ob ID des Kontos gesetzt wurde.
	 * Wenn Fehler, soll BenutzerkontoException geworfen werden.
	 */
	@Test
	public void testSetId() {
		try {
			aKonto.setId(aKonto.getId());
		} catch (Exception e) {
			fail("[FEHLER]testSetId() sollte keine Exception auslösen");
		}
	}

	/**
	 * Test method for setZentrum()
	 * {@link de.randi2.model.fachklassen.BenutzerkontoBean#setId()}.
	 * 
	 * Ueberpruefung, ob Zentrum gesetzt wurde.
	 * Wenn Fehler, soll BenutzerkontoException geworfen werden.
	 */
	@Test
	public void testSetZentrum() {
		try {
			aKonto.setZentrum(aKonto.getZentrum());
		} catch (Exception e) {
			fail("[FEHLER]testSetZentrum() sollte keine Exception auslösen");
		}
	}
}
