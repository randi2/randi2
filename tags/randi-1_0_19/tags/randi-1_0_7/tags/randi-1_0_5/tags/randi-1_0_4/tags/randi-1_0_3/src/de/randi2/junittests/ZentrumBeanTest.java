package de.randi2.junittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.model.exceptions.ZentrumException;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;

/**
 * @author Katharina Chruscz <kchruscz@stud.hs-heilbronn.de>
 * @version $Id: $
 * 
 */
public class ZentrumBeanTest {

	private String abteilung, hausnr, institution, ort, passwort, plz, strasse;

	private int id;

	private PersonBean ansprechpartner;

	private ZentrumBean zentrum = new ZentrumBean();

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#ZentrumBean(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, de.randi2.model.fachklassen.beans.PersonBean, java.lang.String)}.
	 */
	@Test
	public void testZentrumBeanIntStringStringStringStringStringStringPersonBeanString() {
		try {
			id = 1;
			institution = "institution";
			abteilung = "abteilung";
			ort = "ort";
			plz = "plz";
			strasse = "strasse";
			hausnr = "hausnr";
			ansprechpartner = new PersonBean("nachname", "vorname", "Prof.",
					'm', "user@hs-heilbronn.de", "01760099334", "017600972487",
					"01760427424");
			passwort = "passwort";

			new ZentrumBean(id, institution, abteilung, ort, plz, strasse,
					hausnr, ansprechpartner, passwort);

		} catch (Exception e) {
			fail("Fehler bei testZentrumBeanIntStringStringStringStringStringStringPersonBeanString()");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#ZentrumBean()}.
	 */
	@Test
	public void testZentrumBean() {
		try {
			new ZentrumBean();
		} catch (Exception e) {
			fail("Fehler aufgetreten bei testZentrumBean()");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#getAbteilung()}.
	 */
	@Test
	public void testGetAbteilung() {
		try {
			abteilung = zentrum.getAbteilung();
		} catch (Exception e) {
			fail("Fehler aufgetreten bei testGetAbteilung");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setAbteilung(java.lang.String)}.
	 */
	@Test
	public void testSetAbteilungNormal() {
		try {
			zentrum.setAbteilung("abteilung1");
		} catch (Exception e) {
			fail("Sollte keine Exception ausloesen - richtiger Abteilungsname");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setAbteilung(java.lang.String)}.
	 */
	@Test
	public void testSetAbteilungMaximal() {
		try {
			zentrum
					.setAbteilung("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		} catch (Exception e) {
			fail("Sollte KEINE exception ausloesen!");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setAbteilung(java.lang.String)}.
	 * 
	 * @throws ZentrumException
	 */
	@Test(expected = ZentrumException.class)
	public void testSetAbteilungZuKurz() throws ZentrumException {
		zentrum.setAbteilung("b");
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setAbteilung(java.lang.String)}.
	 * 
	 * @throws ZentrumException
	 * @throws
	 */
	@Test(expected = ZentrumException.class)
	public void testSetAbteilungZuLang() throws ZentrumException {
		zentrum
				.setAbteilung("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#getAnsprechpartner()}.
	 */
	@Test
	public void testGetAnsprechpartner() {
		try {
			ansprechpartner = zentrum.getAnsprechpartner();
		} catch (Exception e) {
			fail("Fehler aufgetreten bei testGetAnsprechpartner");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setAnsprechpartner(de.randi2.model.fachklassen.beans.PersonBean)}.
	 */
	@Test
	public void testSetAnsprechpartner() {
		try {
			zentrum.setAnsprechpartner(ansprechpartner);
		} catch (Exception e) {
			fail("sollte keine Exception auftreten, da Daten von PersonBean schon getestet sind");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#getHausnr()}.
	 */
	@Test
	public void testGetHausnr() {
		try {
			hausnr = zentrum.getHausnr();
		} catch (Exception e) {
			fail("Fehler aufgetreten bei testGetHausnr");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setHausnr(java.lang.String)}.
	 */
	@Test
	public void testSetHausnr() {
		try {
			zentrum.setHausnr("453");
		} catch (Exception e) {
			fail("Hausnummer - erlaubte Zeichen");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setHausnr(java.lang.String)}.
	 * 
	 * @throws ZentrumException
	 */
	@Test(expected = ZentrumException.class)
	public void testSetHausnrFalsch() throws ZentrumException {
		zentrum.setHausnr("{3s209>");
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#getInstitution()}.
	 */
	@Test
	public void testGetInstitution() {
		try {
			institution = zentrum.getInstitution();
		} catch (Exception e) {
			fail("Fehler aufgetreten bei testGetInstitution");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setInstitution(java.lang.String)}.
	 */
	@Test
	public void testSetInstitutionNormal() {
		try {
			zentrum.setInstitution("institution1");
		} catch (Exception e) {
			fail("Sollte keine Exception auslösen - richtiger Institutionsname");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setInstitution(java.lang.String)}.
	 */
	@Test
	public void testSetInstitutionMaxLaenge() {
		try {
			zentrum
					.setInstitution("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		} catch (Exception e) {
			fail("Sollte KEINE exception auslösen!");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setInstitution(java.lang.String)}.
	 * 
	 * @throws ZentrumException
	 */
	@Test(expected = ZentrumException.class)
	public void testSetInstitutionZuKurz() throws ZentrumException {
		zentrum.setInstitution("ab");
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setInstitution(java.lang.String)}.
	 * 
	 * @throws ZentrumException
	 */
	@Test(expected = ZentrumException.class)
	public void testSetInstitutionZuLang() throws ZentrumException {
		zentrum
				.setInstitution("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#getOrt()}.
	 */
	@Test
	public void testGetOrt() {
		try {
			ort = zentrum.getOrt();
		} catch (Exception e) {
			fail("Fehler aufgetreten bei testGetAbteilung");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setOrt(java.lang.String)}.
	 */
	@Test
	public void testSetOrtNormal() {
		try {
			zentrum.setOrt("ort");
		} catch (ZentrumException e) {
			fail("Fehler aufgetreten bei korrekter Eingabe des Ortes");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setOrt(java.lang.String)}.
	 */
	@Test
	public void testSetOrtMaxLaenge() {
		try {
			zentrum
					.setOrt("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		} catch (ZentrumException e) {
			fail("Fehler aufgetreten bei korrekter Eingabe des Ortes");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setOrt(java.lang.String)}.
	 * 
	 * @throws ZentrumException
	 */
	@Test(expected = ZentrumException.class)
	public void testSetOrtZuKurz() throws ZentrumException {
		zentrum.setOrt("a");
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setOrt(java.lang.String)}.
	 * @throws ZentrumException 
	 */
	@Test(expected = ZentrumException.class)
	public void testSetOrtZuLang() throws ZentrumException {
			zentrum.setOrt("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#getPasswort()}.
	 */
	@Test
	public void testGetPasswort() {
		try {
			passwort = zentrum.getPasswort();
		} catch (Exception e) {
			fail("Fehler aufgetreten bei testGetPasswort");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setPasswort(java.lang.String)}.
	 */
	@Test
	public void testSetPasswort() {
		try {
			zentrum.setPasswort(passwort);
		} catch (Exception e) {
			fail("Fehler aufgetreten bei setPasswort");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#getPlz()}.
	 */
	@Test
	public void testGetPlz() {
		try {
			plz = zentrum.getPlz();
		} catch (Exception e) {
			fail("Fehler aufgetreten bei testGetPlz");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setPlz(java.lang.String)}.
	 */
	@Test
	public void testSetPlzRichtig() {
		// TODO: PLZ := \d{5}, 5 Zeichen (ergibt sich)
		// (expected=IllegalArgumentException.class)
		try {
			zentrum.setPlz("11111");
		} catch (Exception e) {
			fail("Fehler aufgetreten bei richtiger testSetPlz");
		}
	}	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setPlz(java.lang.String)}.
	 * @throws ZentrumException 
	 */
	@Test (expected=ZentrumException.class)
	public void testSetPlzFalsch() throws ZentrumException {
			zentrum.setPlz("11342234111");
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#getStrasse()}.
	 */
	@Test
	public void testGetStrasse() {
		try {
			strasse = zentrum.getStrasse();
		} catch (Exception e) {
			fail("Fehler aufgetreten bei testGetStrasse");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setStrasse(java.lang.String)}.
	 */
	@Test
	public void testSetStrasseNormal() {
		try {
			zentrum.setStrasse("Strasse");
		} catch (Exception e) {
			fail("Fehler aufgetreten bei testSetStrasse");
		}
	}	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setStrasse(java.lang.String)}.
	 * @throws ZentrumException 
	 */
	@Test 
	public void testSetStrasseMaxLaenge() throws ZentrumException {
			zentrum.setStrasse("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	
	}	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setStrasse(java.lang.String)}.
	 * @throws ZentrumException 
	 */
	@Test (expected = ZentrumException.class)
	public void testSetStrasseZuKurz() throws ZentrumException {
			zentrum.setStrasse("a");
		
	}	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setStrasse(java.lang.String)}.
	 * @throws ZentrumException 
	 */
	@Test(expected = ZentrumException.class)
	public void testSetStrasseZuLang() throws ZentrumException {
			zentrum.setStrasse("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#getId()}.
	 */
	@Test
	public void testGetId() {
		try {
			id = zentrum.getId();
		} catch (Exception e) {
			fail("Fehler aufgetreten bei testGetId");
		}
	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.ZentrumBean#setId(int)}.
	 */
	@Test
	public void testSetId() {
		// Gibt es hier irgendwelche Beschr�nkungen?
		try {
			zentrum.setId(id);
		} catch (Exception e) {
			fail("Fehler aufgetreten bei testSetId");
		}
	}

}
