package de.randi2.model.fachklassen;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.NachrichtException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.fachklassen.beans.AktivierungBean;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.utility.Config;
import de.randi2.utility.NullKonstanten;
import de.randi2.utility.SystemException;
import de.randi2.utility.Config.Felder;
/*
 * So wurden alle EMailExceptions, die aus der Klasse rausgingen, in NachrichtException ueberfuehrt.
 * Habe diese Anpassungen auch hier uebernommen.
 * Leider entstanden dadurch Errors hier, da du in dem Const. EMailExceptions geworfen hast.
 * Ich habe die nach SystemException ueberfuehrt. da das ja keine Benutzerfehler sind, 
 * wenn die XMl- Geschichten fehlschlagen.
 * 
 * Meine Aenderungen sind mehr QuickFix als alles andere.
 * Naeheres Direkt an den Stellen (auch durch XXX gekennzeichnet)
 * 
 * Gruß Btheel
 */
/**
 * Die Klasse erweitert Nachricht um automatisch generierte Email Nachrichten
 * die in der Datei Nachrichtentexte.xml in conf\release\ liegen. Automatisch
 * generierte Emailnachrichten werden u. a. verschickt bei:
 * <ul>
 * <li>Aktivierung</li>
 * <li>Neues Passwort zuschicken</li>
 * <li>Benutzer sperren</li>
 * <li>Benutzer entsperren</li>
 * </ul>
 * 
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class AutomatischeNachricht extends Nachricht {

	
	
	/**
	 * Das neue Passwort des Nutzers
	 */
	private String neuesPasswort=null;
    /**
     * Auswahl der automatischen Nachrichten. Bitte Änderungen auch 1:1 in die
     * Datei Nachrichtentexte.xml übernehmen.
     * 
     * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
     * 
     */
    public static enum autoNachricht {

        /**
         * Aktivierungsnachricht
         */
        AKTIVIERUNG("aktivierung"),

        /**
         * Nachricht wenn ein Benutzer gesperrt wird
         */
        BENUTZER_SPERREN("bsperren"),

        /**
         * Nachricht wenn ein Benutzer entsperrt wird.
         */
        BENUTZER_ENTSPERREN("bentsperren"),

        /**
         * Nachricht wenn ein neues Passwort zugesendet wird.
         */
        NEUES_PASSWORT("passwort");

        /**
         * Korrespondierendes Attribut Name in der Datei
         * \config\release\Nachrichtentexte.xml
         */
        private String attributXmlDatei = "";

        /**
         * Setzt das XML-Attribut Name
         * 
         * @param attributXmlDatei
         *            Attribut name
         */
        private autoNachricht(String attributXmlDatei) {
            this.attributXmlDatei = attributXmlDatei;

        }

        /**
         * Liefert die String-Repreasentation des Enum-Namens
         * 
         * @return Attribut Name in Xml-Datei
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return this.attributXmlDatei;
        }

    }

    /**
     * Absenderdaten die beim Senden einer automatischen Nachricht gesetzt
     * werden.
     */
    private static PersonBean absenderRandi = null;
    
    /**
     * Konstruktor darf nur verwendet werden, wenn ein Neues Passwort zugesendet werden soll!!! 
     * Sonst bitte {@link AutomatischeNachricht#AutomatischeNachricht(PersonBean, de.randi2.model.fachklassen.AutomatischeNachricht.autoNachricht)} verwenden.
     * @param empfaenger Empfaenger
     * @param neuesPasswort Das neue Passwort, dass an den Emfaenger geschickt werden soll.
     * @throws SystemException 
     * @throws NachrichtException 
     */
    public AutomatischeNachricht(PersonBean empfaenger, String neuesPasswort) throws NachrichtException, SystemException{
    	super();
    	this.neuesPasswort=neuesPasswort;
    	konstruktorAusgelagert(empfaenger,autoNachricht.NEUES_PASSWORT);
    }
    

    /**
     * Erzeugt eine automatische Nachricht. Genauer Textinhalt etc. wird aus der
     * Datei conf/release/Nachrichtentexte.xml ausgelesen und dann mittels den
     * Empfaengerdaten dynamisch zusammengebaut.
     * 
     * @param empfaenger
     *            Empfaengerdaten
     * @param artAutomatischeNachricht
     *            Welche automatische Nachricht soll generiert werden
     * @throws SystemException 
     * @throws EmailException
     *             {@link Nachricht}
     */
    public AutomatischeNachricht(PersonBean empfaenger,
            autoNachricht artAutomatischeNachricht) throws NachrichtException, SystemException {
        super();
       konstruktorAusgelagert(empfaenger,artAutomatischeNachricht);
    }

    /**
     * Ersetzt den String #Absender# durch einen personalisierten Absender{@link AutomatischeNachricht#absenderRandi}.
     * 
     * @param text
     *            Nachrichtentext der #Absender# enthält
     * @return Nachrichtentext, indem nun #Absender# durch einen
     *         personalisierten Absender
     *         {@link AutomatischeNachricht#absenderRandi}ersetzt ist.
     */
    private String setzeAbsender(String text) {

        return text.replace("#Absender#", AutomatischeNachricht.absenderRandi
                .getNachname());
    }

    /**
     * Ersetzt den String #Anrede# durch eine personalisierte Anrede (abhängig
     * von Geschlecht, Name und Titel).
     * 
     * @param empfaenger
     *            Daten des Empfängers
     * @param text
     *            Nachrichtentext der #Anrede# enthält
     * @return Nachrichtentext, indem nun #Anrede# durch eine personalisierte
     *         Anrede ersetzt ist.
     */
    private String setzeAnrede(PersonBean empfaenger, String text) {
    	String titel="";
    	if(!empfaenger.getTitel().equals(PersonBean.Titel.KEIN_TITEL))
    	{
    		titel=empfaenger.getTitel().toString();
    	}
        if (empfaenger.getGeschlecht() == 'w') {
            return text.replace("#Anrede#", "Sehr geehrte Frau "
                    + titel + " "
                    + empfaenger.getNachname());
        } else {
            return text.replace("#Anrede#", "Sehr geehrter Herr "
                    + titel + " "
                    + empfaenger.getNachname());
        }
    }
    private void konstruktorAusgelagert(PersonBean empfaenger, autoNachricht artAutomatischeNachricht) throws NachrichtException, SystemException{
    initMail();
    String betreff = "";
    String nachrichtentext = "";
    // Systemabsender erstellen
    try {

        // XXX @Andi: Du brauchst nur Vorname,Nachname und EMail setzten,
        // den Rest brauche ich nicht. Das Bean prueft doch auch nicht auf
        // Vollstaendigkeit, oder? --Btheel
        absenderRandi = new PersonBean(NullKonstanten.NULL_LONG,
                NullKonstanten.NULL_LONG, "Randi2", "Randi2",
                PersonBean.Titel.KEIN_TITEL, 'm', Config.getProperty(Config.Felder.RELEASE_MAIL_RANDI2MAILADRESSE),
                "098098080", "09809809808", "089789797");

        String dateiname = AutomatischeNachricht.class.getResource(
                "/conf/release/Nachrichtentexte.xml").getPath();
        // String filename = "Nachrichtentexte.xml";
        Document doc = new SAXBuilder().build(dateiname);
        doc.getRootElement();
        Element root = doc.getRootElement();
        List<Element> liste = root.getChildren("email");
        Iterator<Element> listenIt = liste.listIterator();
        boolean nachrichtGefunden = false;

        while (listenIt.hasNext() && !nachrichtGefunden) {
            Element a = listenIt.next();
            String id = a.getAttributeValue("name");
            if (id.equals(artAutomatischeNachricht.toString())) {
                nachrichtGefunden = true;
                betreff = a.getChildText("betreff");
                nachrichtentext = a.getChildText("emailtext");
                nachrichtentext = this.setzeAbsender(nachrichtentext);
                nachrichtentext = this.setzeAnrede(empfaenger,
                        nachrichtentext);
                
                
                //Benutzerkonto zur Person suchen:

            	
                switch (artAutomatischeNachricht) {
                case AKTIVIERUNG:
                	//DatenbankFactory.getAktuelleDBInstanz().suchenMitgliederObjekte(empfaenger, kind)
                    empfaenger.setFilter(true);
                	BenutzerkontoBean sbean=new BenutzerkontoBean();
                	sbean.setGesperrt(true);
                	BenutzerkontoBean konto=DatenbankFactory.getAktuelleDBInstanz().suchenMitgliedEinsZuEins(empfaenger,sbean);
                	konto.setFilter(true);
                	String aktivierungslink=DatenbankFactory.getAktuelleDBInstanz().suchenMitgliedEinsZuEins(konto, new AktivierungBean()).getAktivierungsLink();
                    nachrichtentext=nachrichtentext.replace("#Aktivierungslink#",
                            Config.getProperty(Config.Felder.RELEASE_AKTIVIERUNG_LINK)
                                    + aktivierungslink);
                    empfaenger.setFilter(false);

                    break;
                case BENUTZER_ENTSPERREN:
                    // TODO--afreudli gibt es hie was dynamisches?!
                    break;
                case BENUTZER_SPERREN:
                    // TODO--afreudli gibt es hier was dynamisches
                    break;
                case NEUES_PASSWORT:
                	if(neuesPasswort!=null){
                		nachrichtentext=nachrichtentext.replace("#passwort#", this.neuesPasswort);
                	
                	}
                	else{
                	throw new NachrichtException("Zur Zusendung eines neuen Passworts bitte anderen Konstruktor verwenden");
                	}
                	break;
                	
                	
                default:
                   throw new NachrichtException("Autonachricht darf nicht NULL sein.");
                }

            }
        }
    } catch (PersonException e) {
        //throw new EmailException("Systemabsender falsch gesetzt");
        throw new SystemException("Systemabsender falsch gesetzt");
    } catch (JDOMException e) {
        // throw new EmailException("Fehler beim einlesen von Nachrichtentexte.xml", e);
        throw new SystemException("Fehler beim einlesen von Nachrichtentexte.xml: " + e.getMessage());
    } catch (IOException e) {
        //throw new EmailException("Fehler beim einlesen von Nachrichtentexte.xml", e);
        throw new SystemException("Fehler beim einlesen von Nachrichtentexte.xml: " + e.getMessage());
    }
    super.setAbsender(absenderRandi);
    super.addEmpfaenger(empfaenger);
    super.setBetreff(betreff);
    super.setText(nachrichtentext);
    Logger.getLogger(this.getClass()).debug("Email-Text ist: "+nachrichtentext);
    }
}
