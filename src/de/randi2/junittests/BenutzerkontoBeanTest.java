package de.randi2.junittests;

import static org.junit.Assert.*;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import de.randi2.datenbank.Filter;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.Rolle.Rollen;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.utility.KryptoUtil;

/**
 * Testklasse fuer die Klasse BenutzerkontoBean.
 * 
 * @author Nadine Zwink [nzwink@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class BenutzerkontoBeanTest extends Filter {

	/**
	 * Zugehörige BenutzerkontoBean-Objekte.
	 */
	private BenutzerkontoBean aKonto, bKonto, cKonto;

	/**
	 * GregorianCalender-Objekt
	 */
	private GregorianCalendar ersterLogin, letzterLogin, ersterLoginB,
			letzterLoginB;

	/**
	 * Zugehörige Rolle-Objekte.
	 */
	private Rolle rolle, rolleB;

	/**
	 * Uugehörige Rollen-Objekte.
	 */
	private Rollen name, nameB;

	
	/**
	 * Method setUp() 
	 * Erzeugt eine neue Instanz der Klasse BenutzerkontoBean.
	 * 
	 * @throws Exception,  Fehler, wenn keine Instanz der Klasse
	 *                     BenutzerkontoBean erzeugt werden kann. 
	 */
	@Before
	public void setUp() throws Exception {
		aKonto = new BenutzerkontoBean();
	}

	/** 
	 * Method tearDown() 
	 * Weist dem BenutzerkontoBean-Objekt den Wert "null" zu.
	 * 
	 * @throws Exception, wenn die Testklasse nicht beendet werden kann.
	 */
	@After
	public void tearDown() throws Exception {
		aKonto = null;
	}

	/**
	 * Test method for setBenutzername()
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setBenutzername()}.
	 * 
	 * Ueberpruefung auf korrekt gesetzten Benutzername.
	 *
	 * @throws BenutzerkontoException darf bei korrektem Benutzenamen
	 *                                nicht geworfen werden.
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
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setBenutzername()}.
	 * 
	 * Ueberpruefung auf erlaubte Zeichen im Benutzernamen.
	 * 
	 * @throws BenutzerkontoException, wenn bei der Eingabe des Benutzernamens
	 *                                 unerlaubte Zeichen gesetzt werden.
	 */
	@Test
	public final void testSetBenutzernameErlaubteZeichen()
			throws BenutzerkontoException {
		aKonto.setBenutzername("hanswursthausen");
		assertTrue(aKonto.getBenutzername().equals("hanswursthausen"));
	}

	/**
	 * Test method for setBenutzename()
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setBenutzername()}.
	 * 
	 * Ueberpruefung, ob Benutzername gesetzt wurde.
	 * 
	 * @throws BenutzerkontoException, wenn keine Benutzername gesetzt wird
	 *                                (Benutzername = Null)
	 */
	@Test(expected = BenutzerkontoException.class)
	public final void testSetBenutzernameNull() throws BenutzerkontoException {
		aKonto.setBenutzername(null);
	}


	/**
	 * Test method for setBenutzername()
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setBenutzername()}.
	 * 
	 * Ueberpruefung der Laenge des Benutzernamens.
	 * 
	 * @throws BenutzerkontoException, wenn Benutzername kleiner als 6 bzw. 
	 *                                 mehr als 50 Zeichen enthält.
     */
	@Test(expected = BenutzerkontoException.class)
	public final void testSetBenutzernameLaenge() throws BenutzerkontoException {
		aKonto.setBenutzername("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"); // mehr als 50 Zeichen
		aKonto.setBenutzername("aaa"); // weniger als 6 Zeichen
	}

	/**
	 * Test method for setPasswort()
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setPasswort()}.
	 * 
	 * Ueberpruefung der Laenge des Passwortes.
	 * 
	 * @throws BenutzerkontoException, wenn Passwort weniger als 
	 *                                 6 Zeichen enthält.
	 */
	@Test(expected = BenutzerkontoException.class)
	public final void testSetPasswortLaenge()
			throws BenutzerkontoException {
		aKonto.setFilter(false);
		aKonto.setPasswortKlartext("s");		
	}


	/**
	 * Test method for setPasswort()
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setPasswort()}.
	 * 
	 * Ueberpruefung auf Korrektheit des Passwortes. Es muss mindestens
	 * 1 Ziffer oder 1 Sonderzeichen enthalten sein.
	 * 
	 * @throws BenutzerkontoException, wenn keine Ziffer oder kein 
	 *                                 Sonderzeichen vorhanden ist.
	 */
	@Test 
	public final void testSetPasswortRichtig() throws BenutzerkontoException {
        aKonto.setPasswortKlartext("123456abc%");
        assertTrue(aKonto.getPasswort().equals(KryptoUtil.getInstance().hashPasswort("123456abc%")));
        aKonto.setPasswortKlartext("hans1$wursthausen");
		assertTrue(aKonto.getPasswort().equals(KryptoUtil.getInstance().hashPasswort("hans1$wursthausen")));
		aKonto.setPasswortKlartext("a§abc1passwort");
		assertTrue(aKonto.getPasswort().equals(KryptoUtil.getInstance().hashPasswort("a§abc1passwort")));
		aKonto.setPasswortKlartext("test2abc$abc");
		assertTrue(aKonto.getPasswort().equals(KryptoUtil.getInstance().hashPasswort("test2abc$abc")));        
	}


	
	/**
	 * Test method for setPasswort()
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setPasswort()}.
	 * 
	 * Ueberpruefung, ob Passwort gesetzt wurde.
	 * 
	 * @throws BenutzerkontoException, wenn kein Passwort gesetzt wird
	 *                                 (Passwort=Null) 
	 */
	@Test(expected = BenutzerkontoException.class)
	public final void testSetPasswortNull() throws BenutzerkontoException {
		aKonto.setPasswort(null);
	}



	/**
	 * 
	 * Test method for equalsBenutzerkontoBean()
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#equalsBenutzerkontoBean()}.
	 * 
	 * Vergleich von zwei identischen BenutzerkontoBeans.
	 * Zwei Konten sind identisch, wenn die Benutzernamen identisch sind.
	 * 
	 * @throws BenutzerkontoException, wenn Fehler beim BenutzerkontoBean
	 */
	@Test
	public final void testEqualsBenutzerkontoBeanGleich()
			throws BenutzerkontoException {
		String benutzername = "Hanswurst";
		String passwort = "dddd$dddsf1dfdsf";
		long benutzerId=2;
		long zentrumId=3;
		name = Rollen.ADMIN;
		rolle = Rolle.getAdmin();
		boolean gesperrt = false;
		
		letzterLogin = new GregorianCalendar(2006, 11, 1);
		ersterLogin = new GregorianCalendar(2006, 11, 1);

		aKonto = new BenutzerkontoBean(benutzername, passwort, rolle, benutzerId,
				gesperrt, zentrumId,ersterLogin, letzterLogin);

		assertTrue(aKonto.equals(aKonto));
	}

	/**
	 * Test method for equalsBenutzerkontoBean()
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#equalsBenutzerkontoBean()}.
	 * 
	 * Vergleich von zwei verschiedenen BenutzerkontoBeans.
	 * Zwei Konten sind nicht identisch, wenn die Benutzernamen verschieden sind.
	 * 
	 * @throws BenutzerkontoException, wenn Fehler beim BenutzerkontoBean
	 */
	@Test
	public final void testEqualsBenutzerkontoBeanVerschieden()
			throws BenutzerkontoException {
		String benutzername = "Hanswurst";
		String passwort = "dddd2323sfaf§f";
		long benutzerId=1;
		long zentrumId=2;
		name = Rollen.ADMIN;
		rolle = Rolle.getAdmin();
		boolean gesperrt = false;
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
		long benutzerIdB=4;
		long zentrumIdB=2;
		nameB = Rollen.ADMIN;
		rolleB = Rolle.getAdmin();
		boolean gesperrtB = false;
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

		aKonto = new BenutzerkontoBean(benutzername, passwort, rolle, benutzerId,
				gesperrt, zentrumId,ersterLogin, letzterLogin);
	    bKonto = new BenutzerkontoBean(benutzernameB, passwortB, rolleB,
				benutzerIdB, gesperrtB, zentrumIdB, ersterLoginB,
				letzterLoginB);

		assertFalse(aKonto.equals(bKonto));
	}

	
	/**
	 * Test method for equalsBenutzerkontoBean()
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#equalsBenutzerkontoBean()}.
	 * 
	 * Ueberpruefung, ob BenutzerkontoBean leer ist.
	 *
	 * @throws BenutzerkontoException, wenn kein BenutzerkontoBean gesetzt wird.
	 */
	@Test
	public final void testEqualsBenutzerkontoBeanNull() 
	                     throws BenutzerkontoException {
		String benutzername = "Hanssdsadsd";
		String passwort = "dddd$sdas2";
		long benutzerId=1;
		long zentrumId=2;
		name = Rollen.ADMIN;
		rolle = Rolle.getAdmin();
		boolean gesperrt = false;
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

		aKonto = new BenutzerkontoBean(benutzername, passwort, rolle, benutzerId,
				gesperrt, zentrumId, ersterLogin, letzterLogin);
		cKonto = new BenutzerkontoBean("Abctest", "abc@cdef", null, benutzerId,
				false, zentrumId, null, null);

		assertFalse(aKonto.equals(cKonto));
	}

	/**
	 * Test method for setErsterLogin()
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setErsterLogin()}.
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
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setGesperrt()}.
	 * 
	 * Ueberpruefung, ob Benutzerkonto gesperrt ist.
	 */
	@Test
	public void testSetGesperrt() {
		aKonto.setGesperrt(true);
		assertTrue(aKonto.isGesperrt());
	}

	/**
	 * Test method for setLetzterLogin()
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setLetzterLogin()}.
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
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setRolle()}.
	 * 
	 * Ueberpruefung, ob Rolle gesetzt wurde.
	 * 
	 * @throws BenutzerkontoException, wenn keine Rolle gesetzt wird
	 *                                 (Rolle=Null)
	 */
	@Test(expected = BenutzerkontoException.class)
	public void testSetRolleNull() throws BenutzerkontoException {
		aKonto.setRolle(null);
	}

	/**
	 * Test method for setRolle()
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setRolle()}.
	 * 
	 * Ueberpruefung, ob richtige Rollen gesetzt werden.
	 */
	@Test
	public void testSetRolle() {
		try {
			aKonto.setRolle(Rolle.getAdmin());
			aKonto.setRolle(Rolle.getStatistiker());
			aKonto.setRolle(Rolle.getStudienarzt());
			aKonto.setRolle(Rolle.getStudienleiter());
			aKonto.setRolle(Rolle.getSysop());
		} catch (Exception e) {
			fail("[FEHLER]testSetRolle() sollte keine Exception auslösen");
		}
	}

	/**
	 * Test method for setId()
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setId()}.
	 * 
	 * Ueberpruefung, ob ID des Kontos gesetzt wurde.
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
	 * {@link de.randi2.randomisation.fachklassen.BenutzerkontoBean#setId()}.
	 * 
	 * Ueberpruefung, ob Zentrum gesetzt wurde.
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
