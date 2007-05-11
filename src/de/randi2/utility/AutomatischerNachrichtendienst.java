package de.randi2.utility;

import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * Parst die Automatisch generierten Nachrichten.
 * @author Andreas Freudling afreudling@stud.hs-heilbronn.de
 * @version $Id: AutomatischerNachrichtendienst.java 2398 2007-05-04 12:32:09Z jthoenes $
 * 
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