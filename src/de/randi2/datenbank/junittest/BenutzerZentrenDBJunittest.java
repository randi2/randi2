package de.randi2.datenbank.junittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.GregorianCalendar;

import org.junit.Ignore;
import org.junit.Test;

import de.randi2.controller.AktivierungServlet;
import de.randi2.datenbank.Datenbank;
import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.AktivierungException;
import de.randi2.model.exceptions.BenutzerException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.exceptions.ZentrumException;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.Rolle.Rollen;
import de.randi2.model.fachklassen.beans.AktivierungBean;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.utility.KryptoUtil;
import de.randi2.utility.NullKonstanten;
import de.randi2.utility.SystemException;

/**
 * ACHTUNG AUF GRUND DEr REFERENZIELLEN INTEGRITÄT TEST MINDESTENS 2mal DURCHFUEHREN
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
 * @version $Id: BenutzerZentrenDBJunittest.java 2575 2007-05-09 19:09:10Z
 *          afreudli $
 * 
 */
public class BenutzerZentrenDBJunittest {

    /**
         * Testet ob ein Aktivierungsbean erfolgreich in der Datenbank
         * gespeichert und gesucht werden kann.
         */
    @Test
    public void testAktivierungsbeanSpeichernSuchenAendern() throws BenutzerException,DatenbankFehlerException{

	AktivierungBean bean =new AktivierungBean(NullKonstanten.NULL_LONG, new GregorianCalendar(), 1, "23423424242");
	    bean=DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(bean);
	    
	    //Suchen ueber id:
	    AktivierungBean beanNachSuche=DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(bean.getAktivierungsId(), new AktivierungBean());
	    assertEquals(bean,beanNachSuche);
	    //Suchen ueber Werte:
	    bean.setFilter(true);
	    beanNachSuche=DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(bean).firstElement();
	    assertEquals(bean,beanNachSuche); 
	    
	    //Bean aendern
	    AktivierungBean beanAendern=new AktivierungBean(bean.getAktivierungsId(),new GregorianCalendar(2000,1,1),1,"1232323232");
	    DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(beanAendern);
	    assertFalse(bean.equals(beanAendern));
	    
    }

    /**
         * Testet ob ein Benutzerkontobean erfolgreich in der Datenbank
         * gespeichert und gesucht werden kann.
         */
    @Test
    public void testBenutzerkontobeanSpeichernSuchenAendern() throws BenutzerException,SystemException{
	BenutzerkontoBean benutzerbean=new BenutzerkontoBean(NullKonstanten.NULL_LONG,"benutzername",
		KryptoUtil.getInstance().hashPasswort("Passwort").toString(),Rolle.getAdmin(),1,false,null,null);
	benutzerbean=DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(benutzerbean);
	//Suchen ueber id:
	benutzerbean.setFilter(true);
	BenutzerkontoBean beanNachsuche=DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(benutzerbean.getBenutzerId(), new BenutzerkontoBean());
	assertEquals(benutzerbean,beanNachsuche);
	
	//Suchen ueber Werte
	beanNachsuche=DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(benutzerbean).firstElement();
	assertEquals(benutzerbean,beanNachsuche);
	
	
	//Bean Aendern
	BenutzerkontoBean benutzerAendern=new BenutzerkontoBean(benutzerbean.getBenutzerId(),"benutzername123",
		KryptoUtil.getInstance().hashPasswort("Passwort12").toString(),Rolle.getStudienarzt(),1,true,new GregorianCalendar(),new GregorianCalendar());
	    DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(benutzerAendern);
	    assertFalse(benutzerbean.equals(benutzerAendern));
	

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
	pBeanSchreiben = new PersonBean(NullKonstanten.NULL_LONG, NullKonstanten.NULL_LONG, "Nachname", "Vorname", PersonBean.Titel.PROF_DR, 'w', "andreasd@web.de", "09878979", "097987987987",
		"0980809809809");
	pBeanSchreiben = DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(pBeanSchreiben);
	// Suchen Über id
	PersonBean pBeanSuchen = DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(pBeanSchreiben.getId(), new PersonBean());
	// assertEquals(pBeanSchreiben, pBeanSuchen);

	// Suchen über Objekt
	pBeanSchreiben.setFilter(true);
	pBeanSuchen = DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(pBeanSchreiben).firstElement();
	assertEquals(pBeanSchreiben, pBeanSuchen);

	// Ändern
	PersonBean pBeanAendern = DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(pBeanSchreiben.getId(), new PersonBean());
	// Stellvertreter is man selber
	pBeanAendern = new PersonBean(pBeanAendern.getId(), pBeanAendern.getId(), "Nachname1", "Vorname1", PersonBean.Titel.DR, 'm', "@wweb.de", "009878979", "0097987987987", "00980809809809");
	DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(pBeanAendern);
	PersonBean pBeanNachAenderung = DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(pBeanAendern.getId(), new PersonBean());
	assertFalse(pBeanAendern.equals(pBeanNachAenderung));

    }

    /**
         * Test funktioniert nur nach erfolgreichem Personentest! Testet ob ein
         * Zentrumbean erfolgreich in der Datenbank gespeichert und gesucht
         * werden kann. Testet ob ein vorhandenes Zentrumbean geändert werden
         * kann.
         * 
         * @throws ZentrumException
         * @throws DatenbankFehlerException
         * @throws PersonException
         */
    @Test
    public void testZentrumbeanSpeichernSuchenAendern() throws ZentrumException, DatenbankFehlerException, PersonException {

	ZentrumBean zBeanSchreiben = new ZentrumBean(NullKonstanten.NULL_LONG, "institution", "abteilung", "ort", "01234", "strasse", "2", 1, KryptoUtil.getInstance().hashPasswort(
		"passwort23423&$&§&§&§"), false);
	zBeanSchreiben = DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(zBeanSchreiben);
	// Suchen über ID
	ZentrumBean zBeanSuchen = DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(zBeanSchreiben.getId(), new ZentrumBean());
	assertEquals(zBeanSuchen, zBeanSchreiben);

	// Suchen über Objekt
	zBeanSuchen = DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(zBeanSchreiben).firstElement();
	assertEquals(zBeanSuchen, zBeanSchreiben);

	// Ändern
	ZentrumBean zBeanAendern = DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(zBeanSchreiben.getId(), new ZentrumBean());
	zBeanAendern = new ZentrumBean(zBeanAendern.getId(), "institution1", "abteilung1", "ort1", "12345", "strasse1", "1", NullKonstanten.NULL_LONG, KryptoUtil.getInstance().hashPasswort(
		"passwort1"), true);
	ZentrumBean zBeanNachAenderung = DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(zBeanAendern.getId(), new ZentrumBean());
	assertFalse(zBeanAendern.equals(zBeanNachAenderung));

    }
}
