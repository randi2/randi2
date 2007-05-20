package de.randi2.model.fachklassen;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import de.randi2.model.exceptions.NachrichtException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.fachklassen.beans.AktivierungBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.utility.NullKonstanten;
import de.randi2.utility.SystemException;

/* XXX @Andi Ich habe an der Nachricht.java rumgeschraubt, was auch Auswirkungen auf diese Klasse hat.
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
        String betreff = "";
        String nachrichtentext = "";
        // Systemabsender erstellen
        try {

            // XXX @Andi: Du brauchst nur Vorname,Nachname und EMail setzten,
            // den Rest brauche ich nicht. Das Bean prueft doch auch nicht auf
            // Vollstaendigkeit, oder? --Btheel
            absenderRandi = new PersonBean(NullKonstanten.NULL_LONG,
                    NullKonstanten.NULL_LONG, "Randi2", "Randi2",
                    PersonBean.Titel.KEIN_TITEL, 'm', "randi2@randi2.de",
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
                    switch (artAutomatischeNachricht) {
                    case AKTIVIERUNG:
                        // FIXME--afreudli Zu klären wie 1:1 Beziehungen
                        // umgesetzt werden (Fachklasse?!)
                        nachrichtentext.replace("#Aktivierungslink#",
                                AktivierungBean.PRAEFIX_LINK
                                        + "Hier kommt dann der Link");
                        break;
                    case BENUTZER_ENTSPERREN:
                        // TODO--afreudli gibt es hie was dynamisches?!
                        break;
                    case BENUTZER_SPERREN:
                        // TODO--afreudli gibt es hier was dynamisches
                        break;
                    case NEUES_PASSWORT:
                        break;
                    default:
                        // TODO--afreudli passiert hier was?!
                    }
                }
            }
            // XXX @Andi Hab hier SystemExc. gesetzt, macht mMn mehr sin, da das Fehler
            // sind, mit dem der Benutzer nix anfangen kann --Btheel (Quickfix)
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
        String rueckgabe = text;
        if (empfaenger.getGeschlecht() == 'w') {
            rueckgabe.replace("#Anrede#", "Sehr geehrte Frau "
                    + empfaenger.getTitel().toString() + " "
                    + empfaenger.getNachname());
        } else {
            rueckgabe.replace("#Anrede#", "Sehr geehrter Herr "
                    + empfaenger.getTitel().toString() + " "
                    + empfaenger.getNachname());
        }
        return rueckgabe;
    }
}
