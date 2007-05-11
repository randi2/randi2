package de.randi2.model.fachklassen;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import de.randi2.model.fachklassen.beans.PersonBean;

/**
 * @author BTheel [BTheel@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class AutomatischeNachricht extends Nachricht {

    public AutomatischeNachricht() {
        // TODO Auto-generated constructor stub
    }

    public AutomatischeNachricht(PersonBean absender, PersonBean empfaenger,
            String betreff, String text) throws EmailException {
        super(absender, empfaenger, betreff, text);
        // TODO Auto-generated constructor stub
    }

    public AutomatischeNachricht(PersonBean absender,
            Collection<PersonBean> empfaenger, String betreff, String text)
            throws EmailException {
        super(absender, empfaenger, betreff, text);
        // TODO Auto-generated constructor stub
        /* Quellcode zur Rettung 
        String filename = "Nachrichtentexte.xml";
        Document doc = new SAXBuilder().build(filename);
        doc.getRootElement();
        Element root = doc.getRootElement();
        List<Element> liste = root.getChildren("email");
        Iterator<Element> listenIt = liste.listIterator();
        while (listenIt.hasNext()) {
            Element a = listenIt.next();
            String id = a.getAttributeValue("name");
            String betreff = a.getChildText("betreff");
            String emailtext = a.getChildText("emailtext");
            String aktivierungslink = "Ich bin der dynamische Link zur Aktivierungsemail";
            String emailtext1 = emailtext.replace("#Aktivierungslink#", aktivierungslink);
            System.out.println(id + betreff + emailtext1);
        }
        */
    }

}
