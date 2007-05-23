package de.randi2.junittests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.GregorianCalendar;
import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.exceptions.RechtException;
import de.randi2.model.exceptions.ZentrumException;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.utility.KryptoUtil;
import de.randi2.utility.Log4jInit;
import de.randi2.utility.NullKonstanten;

/**
 * TODO sobald in der Datenbank gespeichert werden kann, sind die
 * testGetBenutzer.. Methoden Unsinn, da diese Benutzer nicht mehr vorhanden
 * sein werden. Vielmehr sollte dann einmal ein Benutzerkonto in der Datenbank
 * gespeichert werden und dieses dann via Benutzerkonto.getBenutzer(..)
 * ausgelesen werden und kontrolliert werden, ob diese dann identisch sind
 * 
 * 
 * @author Katharina Chruscz [kchruscz@stud.hs-heilbronn.de]
 * @version $Id: BenutzerkontoTest.java 2429 2007-05-06 17:51:23Z twillert $
 * 
 */
public class BenutzerkontoTest {

	private BenutzerkontoBean bKontoBean;

	private String benutzername, passwort;

	private Rolle rolle;

	// TODO Spaeter wird Titel als eine Enum realisiert werden - deswegen muss
	// man das auch danach anpassen
	private PersonBean benutzer;

	private PersonBean ansprechpartner;

	private boolean gesperrt;

	private ZentrumBean zentrum;

	private GregorianCalendar ersterLogin, letzterLogin;

	
	 /**
	     * Initialisiert den Logger. Bitte log4j.lcf.pat in log4j.lcf umbenennen und es funktioniert.
	     *
	     */
	    @BeforeClass
	    public static void log(){
		Log4jInit.initDebug();
	    }
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		benutzername = "administrator";
		passwort = KryptoUtil.getInstance().hashPasswort("1$administrator");

		gesperrt = false;
		rolle = Rolle.getStudienleiter();

		ersterLogin = new GregorianCalendar(2006, 10, 20);
		letzterLogin = new GregorianCalendar(2006, 11, 30);

		try {
			zentrum = new ZentrumBean(1, "institution", "abteilung", "Ort",
					"11111", "Strasse", "12", 1, KryptoUtil.getInstance().hashPasswort("Passwort"), false);
		} catch (ZentrumException e1) {
			fail(e1.getMessage());
		} catch (DatenbankFehlerException e){
			fail(e.getMessage());
		}

		try {

			benutzer = new PersonBean(0, 0, "nachname", "vorname",
					PersonBean.Titel.PROF, 'm', "user@hs-heilbronn.de",
					"01760099334", "017600972487", "01760427424");

			ansprechpartner = new PersonBean(0, 0, "nachname", "vorname",
					PersonBean.Titel.PROF, 'm', "user@hs-heilbronn.de",
					"01760099334", "017600972487", "01760427424");

			bKontoBean = new BenutzerkontoBean(NullKonstanten.DUMMY_ID, benutzername, passwort, 1, rolle,
					0, gesperrt, ersterLogin, letzterLogin);
		} catch (BenutzerkontoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (PersonException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (DatenbankFehlerException e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		bKontoBean = null;
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#Benutzerkonto(de.randi2.model.fachklassen.beans.BenutzerkontoBean)}.
	 */
	@Test
	public void testBenutzerkonto() throws Exception {
		new Benutzerkonto(bKontoBean);
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#suchenBenutzer(de.randi2.model.fachklassen.beans.BenutzerkontoBean)}.
	 * 
	 * @throws DatenbankFehlerException
	 */
	@Test
	public void testSuchenBenutzer() throws DatenbankFehlerException {
		Vector suchErgebnisse = new Vector();
		bKontoBean.setFilter(true);
		suchErgebnisse = Benutzerkonto.suchenBenutzer(bKontoBean);
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#anlegenBenutzer(de.randi2.model.fachklassen.beans.BenutzerkontoBean)}.
	 * 
	 * @throws DatenbankFehlerException
	 * @throws BenutzerkontoException 
	 */
	@Test
	public void testAnlegenBenutzer() throws DatenbankFehlerException, BenutzerkontoException {
		ersterLogin = new GregorianCalendar(2006, 10, 20);
		letzterLogin = new GregorianCalendar(2006, 11, 30);
		passwort = KryptoUtil.getInstance().hashPasswort("user3PW");
		bKontoBean = new BenutzerkontoBean(0,"userChef", passwort,
					0,Rolle.getAdmin(),0,false, ersterLogin, letzterLogin);
		Benutzerkonto.anlegenBenutzer(bKontoBean);
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#getBenutzer(java.lang.String)}.
	 * 
	 * @throws DatenbankFehlerException
	 * @throws BenutzerkontoException
	 */
	@Test
	public void testGetBenutzer() throws BenutzerkontoException,
			DatenbankFehlerException {
		Benutzerkonto dummyBenutzerkonto = Benutzerkonto.anlegenBenutzer(bKontoBean);
		BenutzerkontoBean benu2 = Benutzerkonto.getBenutzer(dummyBenutzerkonto.getBenutzerkontobean().getBenutzerId());
		assertTrue(dummyBenutzerkonto.getBenutzerkontobean().equals(benu2));
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#toString()}.
	 */
	@Test
	public void testToString() {
		Benutzerkonto bKonto = new Benutzerkonto(bKontoBean);
		String sollWert = "Benutzerkontoname: " + bKontoBean.getBenutzername()
				+ "(Last LogIn: " + bKontoBean.getLetzterLogin() + ")";
		String istWert = bKonto.toString();
		assertTrue(sollWert.equals(istWert));
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#equals(de.randi2.model.fachklassen.Benutzerkonto)}.
	 */
	@Test
	public void testEqualsBenutzerkonto() {
		bKontoBean.setFilter(true);
		Benutzerkonto aKonto = new Benutzerkonto(bKontoBean);
		Benutzerkonto bKonto = new Benutzerkonto(bKontoBean);
		assertTrue(aKonto.equals(bKonto));

		String benutzername2 = "Statistiker";
		String passwort2 = "1$statistiker";
		BenutzerkontoBean anderesKontoBean;

		try {
			anderesKontoBean = new BenutzerkontoBean(0, benutzername2, passwort2, 1,
					rolle, 0, gesperrt, ersterLogin, letzterLogin);
			anderesKontoBean.setFilter(true);
			Benutzerkonto cKonto = new Benutzerkonto(anderesKontoBean);
			if (aKonto.equals(cKonto)) {
				fail("Vergleich von zwei verschiedenen Benutzerkonten liefert ein true zurück");
			}
		} catch (BenutzerkontoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (DatenbankFehlerException e){
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#getBenutzerkontobean()}.
	 */
	@Test
	public void testGetBenutzerkontobean() {
		try {
			Benutzerkonto bKonto = new Benutzerkonto(bKontoBean);
			assertTrue(bKonto.getBenutzerkontobean().equals(bKontoBean));
		} catch (Exception e) {
			fail("Fehler bei testGetBenutzerkontobean");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#pruefenPasswort(java.lang.String)}.
	 * 
	 * @throws DatenbankFehlerException
	 * @throws BenutzerkontoException
	 */
	@Test
	public void testPruefenPasswort() throws BenutzerkontoException,
			DatenbankFehlerException {
		String pass = bKontoBean.getPasswort();
		Benutzerkonto dummyBenu = new Benutzerkonto(bKontoBean);
		dummyBenu.pruefenPasswort(pass);
	}

}
