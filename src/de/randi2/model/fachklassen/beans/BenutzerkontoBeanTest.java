package de.randi2.model.fachklassen.beans;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Benjamin Theel <Btheel@stud.hs-heilbronn.de>

 * @version 0.0
 */
public class BenutzerkontoBeanTest {

    private BenutzerkontoBean aKonto;
    
    @Before
    public void setUp() throws Exception {
        aKonto = new BenutzerkontoBean();
    }

    @After
    public void tearDown() throws Exception {
        aKonto=null;
    }

    @Test
    public final void testSetBenutzername() {
        //TODO
        try{
        aKonto.setBenutzername("a2");
        aKonto.setBenutzername("a2de");
        aKonto.setBenutzername("a2@3456.de");
        }catch (Exception e) {
            fail("Sollte Keine Exception werfen!!");
        }
        try {
            // leer
            aKonto.setBenutzername("");
            aKonto.setBenutzername("a");
            fail("Sollte Exception auslösen");
        } catch (Exception e) {
        }
        try {
            // null
            aKonto.setBenutzername(null);
            fail("Sollte Exception auslösen");
        } catch (Exception e) {
        }
        try {
            // zu lang
            aKonto.setBenutzername("123456789012345");
            fail("Sollte Exception auslösen");
        } catch (Exception e) {
        }
    }

    @Test
    public final void testSetPasswort() {
    try {
        aKonto.setPasswort("sd");
    } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
    }
        
    
    }

    @Test
    public final void testEqualsBenutzerkontoBean() {
       fail("Not yet implemented"); // TODO
    }

}
