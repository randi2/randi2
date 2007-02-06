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
 * TODO wenn einzelne Set-Methode ueberprueft werden, darf der Filter nicht
 * gesetzt sein, da sonst keine Validierung der Parameter in diesen Methoden
 * stattfindet und der Setter dann alle Parameter akzeptiert. Desweiteren
 * koennen zB bei den setPasswort Tests einige zusammengefasst werden.
 * 
 * TODO alle Test-Methoden die OK sind, sind so markiert, sollten aber ordenltich kommentiert werden!
 * 
 * TODO unmarkierte Methoden bitte pruefen!
 * 
 * @author Nadine Zwink <nzwink@stud.hs-heilbronn.de>
 * @version $Id$
 */
public class BenutzerkontoBeanTest extends Filter {

	private BenutzerkontoBean aKonto, bKonto, cKonto;

	private GregorianCalendar ersterLogin, letzterLogin, ersterLoginB,
			letzterLoginB;

	private Rolle rolle, rolleB;

	private String benutzername, passwort, benutzernameB, passwortB;

	private PersonBean benutzer, ansprechpartner;

	private boolean gesperrt;

	private ZentrumBean zentrum;

	private Rollen name, nameB;

	/**
	 * OK!
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		aKonto = new BenutzerkontoBean();
	}

	/**
	 * OK!
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		aKonto = null;
	}

	/**
	 * OK!
	 * 
	 * @throws BenutzerkontoException
	 */
	@Test
	public final void testSetBenutzernameRichtig()
			throws BenutzerkontoException {
		aKonto.setFilter(true);
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
	 * OK
	 * 
	 * 
	 * @throws BenutzerkontoException
	 */
	@Test
	public final void testSetBenutzernameErlaubteZeichen()
			throws BenutzerkontoException {
		aKonto.setFilter(true);
		aKonto.setBenutzername("hanswursthausen");
		assertTrue(aKonto.getBenutzername().equals("hanswursthausen"));
	}

	/**
	 * OK!
	 * 
	 * @throws BenutzerkontoException
	 */
	@Test(expected = BenutzerkontoException.class)
	public final void testSetBenutzernameNull() throws BenutzerkontoException {
		aKonto.setBenutzername(null);
	}

	/**
	 * OK!
	 * 
	 * @throws BenutzerkontoException
	 */
	@Test
	public final void testSetBenutzernameLeer() throws BenutzerkontoException {
		aKonto.setFilter(true);
		aKonto.setBenutzername(null);
	}

	/**
	 * OK!
	 * 
	 * @throws BenutzerkontoException
	 */
	@Test(expected = BenutzerkontoException.class)
	public final void testSetBenutzernameLaenge() throws BenutzerkontoException {
		aKonto.setFilter(false);
		aKonto.setBenutzername("aaaaaaaaaaaaaaaaa"); // mehr als 14 Zeichen
		aKonto.setBenutzername("aaa"); // weniger als 4 Zeichen
	}

	/**
	 * 
	 * @throws BenutzerkontoException
	 */
	@Test(expected = BenutzerkontoException.class)
	public final void testSetPasswortLaengeFalsch()
			throws BenutzerkontoException {
		aKonto.setFilter(false);
		aKonto.setPasswort("s");
	}

	@Test(expected = BenutzerkontoException.class)
	public final void testSetPasswortLaengeRichtig()
			throws BenutzerkontoException {
		aKonto.setFilter(false);
		aKonto.setPasswort("sss");
	}

	/**
	 * falsch
	 * 
	 */
	@Test
	public final void testSetPasswortErlaubteZeichen() {
		aKonto.setFilter(true);
		try {
			aKonto.setPasswort(".*[A-Za-z].*");
			aKonto.setPasswort(".*[0-9].*");
		} catch (Exception e) {
			fail("[FEHLER]testSetPasswortErlaubteZeichen()sollte keine Exception auslösen");
		}
	}

	@Test
	public final void testSetPasswortRichtig() throws BenutzerkontoException {
		aKonto.setFilter(true);
		aKonto.setPasswort("aa");
		aKonto.setPasswort("aaaa");
	}

	@Test(expected = BenutzerkontoException.class)
	public final void testSetPasswortNull() throws BenutzerkontoException {
		aKonto.setPasswort(null);
	}

	@Test(expected = BenutzerkontoException.class)
	public final void testSetPasswortLeer() throws BenutzerkontoException {
		aKonto.setFilter(false);
		aKonto.setPasswort(null);
	}

	/**
	 * OK!
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
	 * umstaendlich (mit dem ParseInt...) aber OK!
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
	 * OK! aber wie gesagt umstaendlich..
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
		cKonto = new BenutzerkontoBean("fsdfdsf", "sdfd$ss7sd", rolle, null, null, false, null,
				null, null);

		assertFalse(aKonto.equals(cKonto));
	}

	/**
	 * NICHT OK! hier wird das eigene Datum getestet, aber nichts von der
	 * BenutzerkontoBean... hier sollte wenigstens die setErsterLogin() Methode
	 * aufgerufen werden
	 * 
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

			assertFalse((new GregorianCalendar(Locale.GERMANY))
					.before(ersterLogin));
		} catch (Exception e) {
			fail("[testSetErsterLogin]Exception, wenn Zeit des ersten Login in der Zukunft liegt.");
		}
	}

	/**
	 * OK!
	 *
	 */
	@Test
	public void testSetGesperrt() {
		aKonto.setGesperrt(true);
		assertTrue(aKonto.isGesperrt());
	}
	
	/**
	 * NICHT OK! hier wird das eigene Datum getestet, aber nichts von der
	 * BenutzerkontoBean... hier sollte wenigstens die setLetzterLogin() Methode
	 * aufgerufen werden
	 * 
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

			assertFalse((new GregorianCalendar(Locale.GERMANY))
					.before(letzterLogin));
		} catch (Exception e) {
			fail("[testSetLetzterLogin]Exception, wenn Zeit des letzter Login in der Zukunft liegt.");
		}
	}

	/**
	 * OK!
	 * 
	 * @throws BenutzerkontoException
	 */
	@Test(expected = BenutzerkontoException.class)
	public void testSetRolleNull() throws BenutzerkontoException {
		aKonto.setRolle(null);
	}

	/**
	 * OK!
	 *
	 */
	@Test
	public void testSetRolle() {
		aKonto.setFilter(true);
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
	 * OK!
	 *
	 */
	@Test
	public void testSetId() {
		long zahl = 1;
		try {
			aKonto.setId(zahl);
		} catch (Exception e) {
			fail("[FEHLER]testSetId() sollte keine Exception auslösen");
		}
	}

	/**
	 * OK!
	 *
	 */
	@Test
	public void testSetZentrum() {
		try {
			aKonto.setZentrum(zentrum);
		} catch (Exception e) {
			fail("[FEHLER]testSetZentrum() sollte keine Exception auslösen");
		}
	}
}
