package de.randi2.junittests;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.utility.KryptoUtil;
import de.randi2.utility.Log4jInit;
import de.randi2.utility.NullKonstanten;

/**
 * Testklasse fuer die Klasse Benutzerkonto.
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

	private boolean gesperrt;

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
	 * Füllt ein BenutzerkontoBean mit Daten.
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
			bKontoBean = new BenutzerkontoBean(NullKonstanten.DUMMY_ID, benutzername, passwort, 4, rolle,
					1, gesperrt, ersterLogin, letzterLogin);
		} catch (BenutzerkontoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (DatenbankFehlerException e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Setzt das BenutzerkontoBean <code>null</code>.
	 */
	@After
	public void tearDown() {
		bKontoBean = null;
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#Benutzerkonto(de.randi2.model.fachklassen.beans.BenutzerkontoBean)}.
	 */
	@Test
	public void testBenutzerkonto() {
		new Benutzerkonto(bKontoBean);
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#suchenBenutzer(de.randi2.model.fachklassen.beans.BenutzerkontoBean)}.
	 */
	@Test
	public void testSuchenBenutzer() {
		Vector<BenutzerkontoBean> benuV = new Vector<BenutzerkontoBean>();
		Benutzerkonto dummyBenutzerkonto;
		try {
			dummyBenutzerkonto = Benutzerkonto.anlegenBenutzer(bKontoBean);
			dummyBenutzerkonto.getBenutzerkontobean().setFilter(true);
			benuV = Benutzerkonto.suchenBenutzer(dummyBenutzerkonto.getBenutzerkontobean());
		} catch (DatenbankFehlerException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		assertEquals(benuV.elementAt(0).getBenutzername(), bKontoBean.getBenutzername());
		assertEquals(benuV.elementAt(0).getRolle().toString(), bKontoBean.getRolle().toString());
		assertEquals(benuV.elementAt(0).getPasswort(), bKontoBean.getPasswort());
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#anlegenBenutzer(de.randi2.model.fachklassen.beans.BenutzerkontoBean)}.
	 */
	@Test
	public void testAnlegenBenutzer() {
		try {
			Benutzerkonto.anlegenBenutzer(bKontoBean);
		} catch (DatenbankFehlerException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#get(java.lang.String)}.
	 */
	@Test
	public void testGet() {
		Benutzerkonto bKonto;
		BenutzerkontoBean bean = new BenutzerkontoBean();
		try {
			bKonto = Benutzerkonto.anlegenBenutzer(bKontoBean);
			bean = Benutzerkonto.get(bKonto.getBenutzerkontobean().getId());
			assertEquals(bKontoBean.getBenutzername(), bean.getBenutzername());
			assertEquals(bKontoBean.getRolle().toString(), bean.getRolle().toString());
		} catch (DatenbankFehlerException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}		
		
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
			anderesKontoBean = new BenutzerkontoBean(12, benutzername2, passwort2, 1,
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
		Benutzerkonto bKonto = new Benutzerkonto(bKontoBean);
		assertTrue(bKonto.getBenutzerkontobean().equals(bKontoBean));
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.Benutzerkonto#pruefenPasswort(java.lang.String)}.
	 */
	@Test
	public void testPruefenPasswort() {
		String pass = bKontoBean.getPasswort();
		Benutzerkonto dummyBenu = new Benutzerkonto(bKontoBean);
		dummyBenu.pruefenPasswort(pass);
	}

}
