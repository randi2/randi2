/**
 * 
 */
package de.randi2.junittests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.randi2.model.exceptions.PersonException;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.utility.Nachrichtendienst;
import de.randi2.utility.NachrichtendienstException;

/**
 * @author Benjamin Theel <BTheel@stud.hs-heilbronn.de>
 * @version $Id$
 */
public class NachrichtendienstTest {

    /**
     * Instanz des nachrichtendiensts.
     */
    private Nachrichtendienst aNachrichtendienst;

    /**
     * TestBean des Absenders
     */
    private PersonBean absender;

    private PersonBean empfaenger;

    /**
     * {@link Nachrichtendienst} init.
     * Absender und Emfaenger mit Werten fuellen
     * @throws NachrichtendienstException 
     * @throws PersonException 
     */
    @Before
    public final void setUp() throws NachrichtendienstException, PersonException {

            aNachrichtendienst = Nachrichtendienst.getInstance();

            absender = new PersonBean();
            absender.setEmail("EMail-Adresse@giga4U.de");
            absender.setNachname("JUniTest");
            absender.setVorname("Nachrichtendienst");
            
            empfaenger = new PersonBean();
            empfaenger.setEmail("E@Adresse.de");
            empfaenger.setNachname("Nachname");
            empfaenger.setVorname("Vorname");

    }
    
    /**
     * Absender und Emfaenger flushen
     */
    @After
    public final void tearDown(){
        absender= null;
        empfaenger= null;
    }

    /**
     * Test method for
     * {@link de.randi2.utility.Nachrichtendienst#sendenNachricht(de.randi2.model.fachklassen.beans.PersonBean, java.lang.String, java.lang.String, java.util.Vector)}.
     */
    @Test(expected = NachrichtendienstException.class)
    public final void testSendenNachrichtAbsenderIsNull() throws NachrichtendienstException {
        aNachrichtendienst.sendenNachricht(null, "TestBetreff", "Test-Nachricht", empfaenger);
    }
    
    /**
     * Test method for
     * {@link de.randi2.utility.Nachrichtendienst#sendenNachricht(de.randi2.model.fachklassen.beans.PersonBean, java.lang.String, java.lang.String, java.util.Vector)}.
     */
    @Test(expected = NachrichtendienstException.class)
    public final void testSendenNachrichtEmpfaengerIsNull() throws NachrichtendienstException {
        aNachrichtendienst.sendenNachricht(absender, "TestBetreff", "Test-Nachricht",(PersonBean[]) null);
    }
    
    /**
     * Test method for
     * {@link de.randi2.utility.Nachrichtendienst#sendenNachricht(de.randi2.model.fachklassen.beans.PersonBean, java.lang.String, java.lang.String, java.util.Vector)}.
     */
    @Test
    public final void testSendenNachricht() throws NachrichtendienstException {
        aNachrichtendienst.sendenNachricht(absender, "TestBetreff", "Test-Nachricht",empfaenger);
    }

}
