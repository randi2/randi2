package de.randi2.datenbank;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.*;
import de.randi2.utility.NullKonstanten;
import java.util.*;

/**
 * @author Benjamin Theel <BTheel@stud.hs-heilbronn.de>
 * @version 1.0
 */
public class DatenbankDummyTest {

    private DatenbankSchnittstelle aDB;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        aDB = new DatenbankDummy();
    }

    @After
    public void tearDown() throws Exception {
        aDB = null;
    }

    /**
     * Test method for
     * {@link de.randi2.datenbank.DatenbankDummy#schreibenObjekt(java.lang.Object)}.
     */
    @Test
    public void testSchreibenObjekt() {

        // IllegalArgument test
        try {
            aDB.schreibenObjekt(null);
            fail("Sollte Exception werfen");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        BenutzerkontoBean bean;
        Object result;
        BenutzerkontoBean ergebisBean;
        // Benutzerbean schreiben
        try {
            bean = new BenutzerkontoBean();
            assertEquals(NullKonstanten.DUMMY_ID, bean.getId());
            result = aDB.schreibenObjekt(bean);
            assertTrue(result instanceof BenutzerkontoBean);
            ergebisBean = (BenutzerkontoBean) result;
            assertEquals(bean, ergebisBean);

            assertNotSame(NullKonstanten.DUMMY_ID, ergebisBean.getId());
        } catch (Exception e) {
            fail("Sollte keine Exception werfen");
        }

    }

    /**
     * Test method for
     * {@link de.randi2.datenbank.DatenbankDummy#suchenObjekt(java.lang.Object)}.
     */
    @Test
    public void testSuchenObjekt() {
        Vector<Object> ergebnisse;
        try { // Nulltest
            ergebnisse = aDB.suchenObjekt(null);
            assertEquals(0, ergebnisse.size());
            ergebnisse = null;

            ergebnisse = aDB.suchenObjekt(new Object());
            assertEquals(0, ergebnisse.size());
            ergebnisse = null;
        } catch (Exception e) {
            fail("Sollte keine Exception werfen");
        }
        try {
            String suchname = "stat";
            String passbrot = "stat";
            BenutzerkontoBean suchbean = new BenutzerkontoBean();
            suchbean.setBenutzername(suchname);

            ergebnisse = aDB.suchenObjekt(suchbean);
            assertEquals(1, ergebnisse.size());

            Object tmp = ergebnisse.firstElement();
            
            assertTrue(tmp instanceof BenutzerkontoBean);

            BenutzerkontoBean ergebnisBean = (BenutzerkontoBean) tmp;
            assertEquals(suchname, ergebnisBean.getBenutzername());
            assertEquals(Rolle.getStatistiker(), ergebnisBean.getRolle());
            assertEquals(passbrot, ergebnisBean.getPasswort());
        } catch (Exception e) {
            fail("Ao!");
        }

    }

}
