package de.randi2.datenbank.junittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.GregorianCalendar;

import org.junit.Ignore;
import org.junit.Test;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.AktivierungException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.exceptions.ZentrumException;
import de.randi2.model.fachklassen.beans.AktivierungBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.utility.KryptoUtil;
import de.randi2.utility.NullKonstanten;

/**
 * Testet die Datebankfunktionalität für die Beans der Benutzer und
 * Zentrenverwaltung.
 * <ul>
 * <li>Aktivierungsbean</li>
 * <li>Benutzerkontobean</li>
 * <li>Personbean</li>
 * <li>Zentrumbean</li>
 * </ul>
 * 
 * @author Andreas Freudling [afreudling@web.de]
 * @version $Id$
 * 
 */
public class BenutzerZentrenDBJunittest {

    /**
         * Testet ob ein Aktivierungsbean erfolgreich in der Datenbank
         * gespeichert und gesucht werden kann.
         */
    @Ignore
    public void testAktivierungsbeanSpeichernSuchen() {
	testBenutzerkontobeanSpeichernSuchen();

	AktivierungBean bean = null;
	try {
	    // Fehler muss zurückkommen, keine Aktivierung ohne
	    // Benutzerkonto
	    bean = new AktivierungBean(NullKonstanten.NULL_LONG, new GregorianCalendar(), NullKonstanten.NULL_LONG, "23423424242");
	    DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(bean);
	} catch (AktivierungException e) {
	} catch (DatenbankFehlerException e) {
	    e.printStackTrace();
	}

    }

    /**
         * Testet ob ein vorhandenes Aktivierungsbean geändert werden kann.
         */
    @Ignore
    public void testAktivierungsbeanAendern() {

    }

    /**
         * Testet ob ein Benutzerkontobean erfolgreich in der Datenbank
         * gespeichert und gesucht werden kann.
         */
    @Ignore
    public void testBenutzerkontobeanSpeichernSuchen() {
	// BenutzerkontoBean

    }

    /**
         * Testet ob ein vorhandenes Benutzerkontobean geändert werden kann.
         */
    @Ignore
    public void testBenutzerkontobeanAendern() {
    }

    /**
         * Testet ob ein Personbean erfolgreich in der Datenbank gespeichert und
         * gesucht werden kann. Testet ob ein vorhandenes Personbean geändert
         * werden kann.
         * 
         * @throws PersonException
         *                 Fehler im Test
         * @throws DatenbankFehlerException
         *                 Fehler im Test
         */
    @Test
    public void testPersonbeanSpeichernSuchenAendern() throws PersonException, DatenbankFehlerException {

	PersonBean pBeanSchreiben = null;
	pBeanSchreiben = new PersonBean(NullKonstanten.NULL_LONG, NullKonstanten.NULL_LONG, "Nachname", "Vorname", PersonBean.Titel.KEIN_TITEL, 'w',
		"andreasd@web.de", "09878979", "097987987987", "0980809809809");
	pBeanSchreiben = DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(pBeanSchreiben);
	// Suchen Über id
	PersonBean pBeanSuchen = DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(pBeanSchreiben.getId(), new PersonBean());
	assertEquals(pBeanSchreiben, pBeanSuchen);

	// Suchen über Objekt
	pBeanSuchen = DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(pBeanSchreiben).firstElement();
	assertEquals(pBeanSchreiben, pBeanSuchen);

	// Ändern
	PersonBean pBeanAendern = DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(pBeanSchreiben.getId(), new PersonBean());
	// Stellvertreter is man selber
	pBeanAendern = new PersonBean(pBeanAendern.getId(), pBeanAendern.getId(), "Nachname1", "Vorname1", PersonBean.Titel.DR, 'm', "@wweb.de", "009878979",
		"0097987987987", "00980809809809");
	DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(pBeanAendern);
	PersonBean pBeanNachAenderung = DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(pBeanAendern.getId(), new PersonBean());
	assertFalse(pBeanNachAenderung.getEmail().equals(pBeanAendern.getEmail()));
	assertFalse(pBeanNachAenderung.getFax().equals(pBeanAendern.getEmail()));
	assertFalse(pBeanNachAenderung.getGeschlecht() == pBeanAendern.getGeschlecht());
	assertFalse(pBeanNachAenderung.getHandynummer().equals(pBeanAendern.getHandynummer()));
	assertFalse(pBeanNachAenderung.getNachname().equals(pBeanAendern.getNachname()));
	assertFalse(pBeanNachAenderung.getStellvertreterId() == pBeanAendern.getStellvertreterId());
	assertFalse(pBeanNachAenderung.getTitel().equals(pBeanAendern.getTitel()));
	assertFalse(pBeanNachAenderung.getVorname().equals(pBeanAendern.getVorname()));

    }

    /**
         * Testet ob ein Zentrumbean erfolgreich in der Datenbank gespeichert
         * und gesucht werden kann. Testet ob ein vorhandenes Zentrumbean
         * geändert werden kann.
     * @throws ZentrumException 
     * @throws DatenbankFehlerException 
         */
    @Test
    public void testZentrumbeanSpeichernSuchenAendern() throws ZentrumException, DatenbankFehlerException {
	ZentrumBean zBeanSchreiben = new ZentrumBean(NullKonstanten.NULL_LONG, "institution", "abteilung", "ort", "01234", "strasse", "2",
		NullKonstanten.NULL_LONG, KryptoUtil.getInstance().hashPasswort("passwort23423&$&§&§&§"), false);
	zBeanSchreiben=DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(zBeanSchreiben);
	//Suchen über ID
	ZentrumBean zBeanSuchen=DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(zBeanSchreiben.getId(), new ZentrumBean());
	assertEquals(zBeanSuchen,zBeanSchreiben);
	
	//Suchen über Objekt
	zBeanSuchen=DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(zBeanSchreiben).firstElement();
	assertEquals(zBeanSuchen,zBeanSchreiben);
	
	//Ändern
	ZentrumBean zBeanAendern=DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(zBeanSchreiben.getId(), new ZentrumBean());
	zBeanAendern=new ZentrumBean(zBeanAendern.getId(),"institution1","abteilung1","ort1","12345","strasse1","1",NullKonstanten.NULL_LONG,KryptoUtil.getInstance().hashPasswort("passwort1"),true);
	ZentrumBean zBeanNachAenderung=DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(zBeanAendern.getId(), new ZentrumBean());
	assertFalse(zBeanAendern.getAbteilung().equals(zBeanNachAenderung.getAbteilung()));
	assertFalse(zBeanAendern.getAnsprechpartnerId()==zBeanNachAenderung.getAnsprechpartnerId());
	assertFalse(zBeanAendern.getHausnr().equals(zBeanNachAenderung.getHausnr()));
	assertFalse(zBeanAendern.getInstitution().equals(zBeanNachAenderung.getInstitution()));
	assertFalse(zBeanAendern.getIstAktiviert()==zBeanNachAenderung.getIstAktiviert());
	assertFalse(zBeanAendern.getOrt().equals(zBeanNachAenderung.getOrt()));
	assertFalse(zBeanAendern.getPasswort().equals(zBeanNachAenderung.getPasswort()));
	assertFalse(zBeanAendern.getPlz().equals(zBeanNachAenderung.getPlz()));
	assertFalse(zBeanAendern.getStrasse().equals(zBeanNachAenderung.getStrasse()));
	
	

    }
}
