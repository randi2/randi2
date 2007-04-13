package de.randi2.utility;

import org.jdom.input.SAXBuilder;
import org.jdom.*;
import java.util.*;

/**
 * @author Andreas Freudling afreudling@stud.hs-heilbronn.de
 * @version $Id$
 * Parst die Automatisch generierten Nachrichten.
 *
 */
public class AutomatischerNachrichtendienst {
    //TODO noch Baustelle
    public static void main(String[] args) throws Exception {
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
    }
}