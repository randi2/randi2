package de.randi2.junittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.GregorianCalendar;

import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.AktivierungException;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.beans.AktivierungBean;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.utility.KryptoUtil;
import de.randi2.utility.Log4jInit;

/**
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public class AktivierungBeanTest {

	/**
	 * Initialisiert den Logger. Bitte log4j.lcf.pat in log4j.lcf umbenennen und
	 * es funktioniert.
	 * 
	 */
	@BeforeClass
	public static void log() {
		Log4jInit.initDebug();
	}

	@Test(expected = AktivierungException.class)
	public void aktivierungslinkZuKurz() throws AktivierungException {
		AktivierungBean abean = new AktivierungBean();

		abean.setAktivierungsLink("a");

	}

	@Test
	public void aktivierungslinkOk() throws AktivierungException {
		AktivierungBean abean = new AktivierungBean();
		abean.setAktivierungsLink("aaaaaaaaaaaaaaaaaaaa");

	}

	@Test(expected = AktivierungException.class)
	public void aktivierungslinkZuLang() throws AktivierungException {
		AktivierungBean abean = new AktivierungBean();
		abean
				.setAktivierungsLink("faaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaadddddddddddddddddddddddddddddddddddddddddddd");
	}

	@Test(expected = AktivierungException.class)
	public void aktivierungslinkNull() throws AktivierungException {
		AktivierungBean abean = new AktivierungBean();
		abean.setAktivierungsLink(null);

	}

	@Test(expected = AktivierungException.class)
	public void benutzerkontoNull() throws AktivierungException {
		AktivierungBean abean = new AktivierungBean();
		abean.setBenutzerkonto(null);
	}

	@Test(expected = AktivierungException.class)
	public void benutzerkontoNichtGespeichert() throws AktivierungException {
		AktivierungBean abean = new AktivierungBean();
		abean.setBenutzerkonto(new BenutzerkontoBean());
	}

	@Test
	public void benutzerkontoOK() throws AktivierungException,
			BenutzerkontoException {
		AktivierungBean abean = new AktivierungBean();
		try {
			abean.setBenutzerkonto(new BenutzerkontoBean(12, "randiTester",
					KryptoUtil.getInstance().hashPasswort(
							KryptoUtil.getInstance().generatePasswort(10)), 13,
					Rolle.getStudienarzt(), 14, false, new GregorianCalendar(
							2007, 4, 20), new GregorianCalendar()));
		} catch (DatenbankExceptions e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void equalsOK() throws AktivierungException {
		AktivierungBean abean;

		AktivierungBean abean2;
		try {
			abean = new AktivierungBean(1, new GregorianCalendar(2007, 4, 22),
					12, "aaaaaaaaaaaaaaaaaaaa");
			abean2 = new AktivierungBean(1, new GregorianCalendar(2007, 4, 22),
					12, "aaaaaaaaaaaaaaaaaaaa");

			assertEquals(abean, abean2);
		} catch (DatenbankExceptions e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void equalsFalse() throws AktivierungException {
	
		try {
			AktivierungBean abean = new AktivierungBean(1, new GregorianCalendar(
					2007, 4, 22), 12, "aaaaaaaaaaaaaaaaaaaa");

			AktivierungBean abean2 = new AktivierungBean(1, new GregorianCalendar(
					2007, 4, 21), 12, "aaaaaaaaaaaaaaaaaaaa");
			assertFalse(abean.equals(abean2));
			abean2.setVersanddatum(new GregorianCalendar(2007, 4, 22));
			abean2.setId(2);
			assertFalse(abean.equals(abean2));
			abean2.setId(1);
			abean2.setBenutzerkontoId(2);
			assertFalse(abean.equals(abean2));
			abean2.setBenutzerkontoId(12);
			abean2.setAktivierungsLink("aaaaaaaaaaaaabaaaaaa");
			assertFalse(abean.equals(abean2));
		} catch (DatenbankExceptions e) {
			fail(e.getMessage());
		}
		
	}
}
