/**
 * 
 */
package de.randi2.junittests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.mail.EmailException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.model.fachklassen.Nachricht;
import de.randi2.utility.Log4jInit;

/**
 * @author Benjamin Theel <BTheel@stud.hs-heilbronn.de>
 * @version 1.0
 */
public class NachrichtTest {
    private Nachricht mail;
    
    /**
     * Initialisiert den Logger. Bitte log4j.lcf.pat in log4j.lcf umbenennen und es funktioniert.
     *
     */
    @BeforeClass
    public static void log(){
	Log4jInit.initDebug();
    }
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        mail = new Nachricht();
    }

    /**
     * Test method for {@link de.randi2.model.fachklassen.Nachricht#senden()}.
     */
    @Test
    public final void testSenden() {
        try {// versenden Leerer mails
            mail.senden();
            fail("Versenden leerer Mails sollte nicht moeglich sein");
        } catch (Exception e) {
            assertTrue(e instanceof EmailException);
        }

    }

    /**
     * Test method for {@link de.randi2.model.fachklassen.Nachricht#setAbsender(de.randi2.model.fachklassen.beans.PersonBean)}.
     */
    @Test
    public final void testSetAbsender() {
        try {
            mail.setAbsender(null);
            fail("Null als Absender sollte nicht moeglich sein");
        } catch (Exception e) {
            assertTrue(e instanceof EmailException);
        }
        
    }

    /**
     * Test method for {@link de.randi2.model.fachklassen.Nachricht#setBetreff(java.lang.String)}.
     */
    @Test
    public final void testSetBetreff() {
        try {
            mail.setBetreff(null);
            fail("Betreff == null sollte nicht moeglich sein");
        } catch (EmailException e) {       
        }
        
        try {
            mail.setBetreff("");
            fail("Betreff darf nicht leer sein.");
        } catch (EmailException e) {
        }
    }

    /**
     * Test method for {@link de.randi2.model.fachklassen.Nachricht#addEmpfaenger(de.randi2.model.fachklassen.beans.PersonBean)}.
     */
    @Test
    public final void testAddEmpfaengerPersonBean() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link de.randi2.model.fachklassen.Nachricht#addEmpfaenger(java.util.Collection)}.
     */
    @Test
    public final void testAddEmpfaengerCollectionOfPersonBean() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link de.randi2.model.fachklassen.Nachricht#setText(java.lang.String)}.
     */
    @Test
    public final void testSetText() {
        try {
            mail.setText(null);
            fail("Text == null nicht erlaubt");
        } catch (EmailException e) {
        }
        
        try {
            mail.setText("");
            fail("Text == \"\" nicht erlaubt");
        } catch (EmailException e) {
        }
    }

}
