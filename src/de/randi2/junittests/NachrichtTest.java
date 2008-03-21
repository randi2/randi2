/**
 * 
 */
package de.randi2.junittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.model.exceptions.NachrichtException;
import de.randi2.model.fachklassen.Nachricht;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.utility.Log4jInit;

/**
 * Test der Nachrichtenklasse. Es werden nur die Parameterueberpruefungen
 * getestet. Ein Test des Versandes ist nicht implementiert.
 * 
 * @author BTheel [BTheel@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class NachrichtTest {

    private Nachricht mail;

    private PersonBean dummy;

    /**
     * Initialisiert den Logger. Bitte log4j.lcf.pat in log4j.lcf umbenennen und
     * es funktioniert.
     * 
     */
    @BeforeClass
    public static void log() {
        Log4jInit.initDebug();
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        mail = new Nachricht();
        // DummyBean initialisieren
        dummy = new PersonBean();
        try {
            dummy.setNachname("Nachname");
            dummy.setVorname("Vorname");
            dummy.setEmail("email@example.com");
        } catch (Exception e) {
            fail("PersonBean Init fehlgeschlagen");
            System.err.println("PersonBean Init fehlgeschlagen");
        }
    }

    /**
     * TearDown, Objekte = <code>null</code>
     */
    @After
    public final void tearDown() {
        mail = null;
        dummy = null;
    }

    /**
     * Test method for {@link de.randi2.model.fachklassen.Nachricht#senden()}.
     */
    @Test
    public final void testLeereMailSenden() {
        try {// versenden Leerer mails
            mail.senden();
            fail("Versenden leerer Mails sollte nicht moeglich sein");
        } catch (Exception e) {
            assertTrue("Exception hat falsche Klasse",e instanceof NachrichtException);
            assertEquals("Exception hat falschen Msg",NachrichtException.ABSENDER_NULL, e.getMessage());
        }
    }

    /**
     * Test method for {@link de.randi2.model.fachklassen.Nachricht#senden()}.
     */
    @Test
    public final void testLeereMailSendenMitAbsender() {
        try {// versenden Leerer mails
            mail.setAbsender(dummy);
            mail.senden();
            fail("Versenden leerer Mails sollte nicht moeglich sein");
        } catch (Exception e) {
            assertTrue("Exception hat falsche Klasse",e instanceof NachrichtException);
            assertEquals("Exception hat falsche Msg",NachrichtException.LEERER_BETREFF, e.getMessage());
        }
    }

    /**
     * Test method for {@link de.randi2.model.fachklassen.Nachricht#senden()}.
     */
    @Test
    public final void testLeereMailSendenMitBetreff() {
        try {// versenden Leerer mails
            mail.setAbsender(dummy);
            mail.setBetreff("Dummy-Betreff");
            mail.senden();
            fail("Versenden leerer Mails sollte nicht moeglich sein");
        } catch (Exception e) {
           // assertTrue(e instanceof NachrichtException);
           // assertEquals(NachrichtException.EMPFEANGER_NULL, e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link de.randi2.model.fachklassen.Nachricht#setAbsender(de.randi2.model.fachklassen.beans.PersonBean)}.
     */
    @Test
    public final void testSetAbsender() {
        try {
            mail.setAbsender(null);
            fail("Null als Absender sollte nicht moeglich sein");
        } catch (Exception e) {
            assertTrue("Exception hat falsche Klasse",e instanceof NachrichtException);
            assertEquals("Exception hat falsche Msg",NachrichtException.ABSENDER_NULL, e.getMessage());
        }

        try {// Filter hinzufuegen
            dummy.setFilter(true);
            mail.addEmpfaenger(dummy);
            fail("Filter sollte nicht erlaubt sein");
        } catch (Exception e) {
            assertTrue("Exception hat falsche Klasse",e instanceof NachrichtException);
            assertEquals("Exception hat falsche Msg",NachrichtException.PERSONBEAN_IST_FILTER, e
                    .getMessage());
        }

    }

    /**
     * Test method for
     * {@link de.randi2.model.fachklassen.Nachricht#setBetreff(java.lang.String)}.
     */
    @Test
    public final void testSetBetreff() {
        try {
            mail.setBetreff(null);
            fail("Betreff == null sollte nicht moeglich sein");
        } catch (Exception e) {
            assertTrue("Exception hat falsche Klasse",e instanceof NachrichtException);
            assertEquals("Exception hat falsche Msg",NachrichtException.LEERER_BETREFF, e.getMessage());
        }

        try {
            mail.setBetreff("");
            fail("Betreff darf nicht leer sein.");
        } catch (Exception e) {
            assertTrue("Exception hat falsche Klasse",e instanceof NachrichtException);
            assertEquals("Exception hat falsche Msg",NachrichtException.LEERER_BETREFF, e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link de.randi2.model.fachklassen.Nachricht#addEmpfaenger(de.randi2.model.fachklassen.beans.PersonBean)}.
     */
    @Test
    public final void testAddEmpfaengerPersonBean() {
        try {// korrektes Bean setzten
            mail.addEmpfaenger(dummy);
        } catch (Exception e) {
            fail("EMail-Adresse sollte korrekt sein");
        }

        try {// Null setzten
            mail.addEmpfaenger((PersonBean) null);
            fail("null sollte nicht erlaubt sein");
        } catch (Exception e) {
            assertTrue(e instanceof NachrichtException);
            assertEquals(NachrichtException.EMPFAENGER_NULL, e.getMessage());
        }

        try {// Filter hinzufuegen
            dummy.setFilter(true);
            mail.addEmpfaenger(dummy);
            fail("Filter sollte nicht erlaubt sein");
        } catch (Exception e) {
            assertTrue(e instanceof NachrichtException);
            assertEquals(NachrichtException.PERSONBEAN_IST_FILTER, e
                    .getMessage());
        }

    }

    /**
     * Test method for
     * {@link de.randi2.model.fachklassen.Nachricht#addEmpfaenger(java.util.Collection)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public final void testAddEmpfaengerCollectionOfPersonBean() {
        try {
            mail.addEmpfaenger((Collection)null);
            fail("null sollte nicht erlaubt sein");
        } catch (NachrichtException e) {
        }
    }

    /**
     * Test method for
     * {@link de.randi2.model.fachklassen.Nachricht#setText(java.lang.String)}.
     */
    @Test
    public final void testSetText() {
        try {
            mail.setText(null);
            fail("Text == null nicht erlaubt");
        } catch (Exception e) {
            assertTrue("Exception hat falsche Klasse",e instanceof NachrichtException);
            assertEquals("Exception hat falsche Msg",NachrichtException.LEERER_TEXT, e.getMessage());
        }

        try {
            mail.setText("");
            fail("Text == \"\" nicht erlaubt");
        } catch (NachrichtException e) {
            assertTrue("Exception hat falsche Klasse",e instanceof NachrichtException);
            assertEquals("Exception hat falsche Msg",NachrichtException.LEERER_TEXT, e.getMessage());
        }
    }

}
