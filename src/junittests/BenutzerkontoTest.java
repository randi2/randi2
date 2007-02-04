package junittests;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.model.fachklassen.Rolle;

/**
 * @author Katharina Chruscz <kchruscz@stud.hs-heilbronn.de>
 * @version 1.0 (2007-02-03)
 * 
 */
public class BenutzerkontoTest {

	private static final char m = 0;

	private char geschlecht = m;

	private Benutzerkonto bKonto;

	private BenutzerkontoBean bKontoBean;

	private String benutzername, passwort;

	private Rolle rolle;

	private PersonBean benutzer, ansprechpartner;

	private boolean gesperrt;

	private ZentrumBean zentrum;

	private GregorianCalendar ersterLogin, letzterLogin;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		benutzername = "studienleiter";
		passwort = "1$studienleiter";
		rolle = Rolle.getStudienleiter();
		benutzer = new PersonBean("Mueller", "Hans", "Dr.", geschlecht,
				"blabla@bla.de", "032452342", "973423", "12345");
		ansprechpartner = new PersonBean("Schmidt", "Juergen", "Dr.",
				geschlecht, "blabla@bla.de", "032452342", "973423", "12345");
		gesperrt = false;

		zentrum = new ZentrumBean("institution", "abteilung", "ort", "plz",
				"strasse", "hausnr", ansprechpartner, "passwort");
		ersterLogin = new GregorianCalendar();
		letzterLogin = new GregorianCalendar();

		bKontoBean = new BenutzerkontoBean(benutzername, passwort, rolle,
				benutzer, ansprechpartner, gesperrt, zentrum, ersterLogin,
				letzterLogin);
		bKonto = new Benutzerkonto(bKontoBean);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		bKonto = null;
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#Benutzerkonto(de.randi2.model.fachklassen.beans.BenutzerkontoBean)}.
	 */
	@Test
	public void testBenutzerkonto() {
		fail("Not yet implemented");

	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#suchenBenutzer(de.randi2.model.fachklassen.beans.BenutzerkontoBean)}.
	 */
	@Test
	public void testSuchenBenutzer() {
		try {

		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#anlegenBenutzer(de.randi2.model.fachklassen.beans.BenutzerkontoBean)}.
	 */
	@Test
	public void testAnlegenBenutzer() {
		try {
			bKonto.anlegenBenutzer(bKontoBean);
		} catch (Exception e) {
			fail("Fehler aufgetreten bei testAnlegenBenutzer");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#getBenutzer(java.lang.String)}.
	 */
	@Test
	public void testGetBenutzer() {
		try {
			String benutzerSa = "sa@randi2.de";
			String benutzerSl = "studienleider";
			String benutzerSt = "statistiker";
			String benutzerAd = "administrator";
			String benutzerSy = "systemoperator";
			bKonto.getBenutzer(benutzerSa);
			bKonto.getBenutzer(benutzerSl);
			bKonto.getBenutzer(benutzerSt);
			bKonto.getBenutzer(benutzerAd);
			bKonto.getBenutzer(benutzerSy);
		} catch (Exception e) {
			fail("Fehler aufgetreten bei testGetBenutzer");
		}

	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#toString()}.
	 */
	@Test
	public void testToString() {
		try {
			bKonto.toString();

		} catch (Exception e) {
			fail("Fehler aufgetreten bei testToString");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#equals(de.randi2.model.fachklassen.Benutzerkonto)}.
	 */
	@Test
	public void testEqualsBenutzerkonto() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#getBenutzerkontobean()}.
	 */
	@Test
	public void testGetBenutzerkontobean() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#pruefenPasswort(java.lang.String)}.
	 */
	@Test
	public void testPruefenPasswortFalsch() {
		fail("Not yet implemented");
	}

	public void testPruefenPasswortRichtig() {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// wird erstmal nicht benötigt
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// wird erstmal nicht benötigt
	}

}
