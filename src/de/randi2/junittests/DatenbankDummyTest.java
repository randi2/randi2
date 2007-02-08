package de.randi2.junittests;

import static org.junit.Assert.*;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.randi2.datenbank.DatenbankDummy;
import de.randi2.datenbank.DatenbankSchnittstelle;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.exceptions.ZentrumException;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.model.fachklassen.*;
import de.randi2.utility.NullKonstanten;
import de.randi2.utility.PasswortUtil;

import java.util.*;
/**
 * @author Benjamin Theel <BTheel@stud.hs-heilbronn.de>
 * @version $Id$
 */
public class DatenbankDummyTest {

    private DatenbankSchnittstelle aDB;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        PropertyConfigurator.configure("C:/Together/workspace_swp/RANDI2 J2EE/WebContent/WEB-INF/log4j.lcf");
    	aDB = new DatenbankDummy();
    }

    @After
    public void tearDown() throws Exception {
        aDB = null;
    }
    @Test
    public void testBla() throws BenutzerkontoException, PersonException {
       BenutzerkontoBean such = new BenutzerkontoBean();
       such.setBenutzername("statistiker");
       such.setFilter(true);
       BenutzerkontoBean bean;
    try {
        bean = (new Vector<BenutzerkontoBean>(aDB.suchenObjekt(such))).firstElement();
        System.out.println(bean.toString());
        PersonBean benutzer = new PersonBean();
        benutzer.setVorname("Heribert");
        benutzer.setNachname("Fassbinder");
        bean.setBenutzer(benutzer);
        
        aDB.schreibenObjekt(bean);
        bean = null;
        bean = (new Vector<BenutzerkontoBean>(aDB.suchenObjekt(such))).firstElement();
        System.out.println(bean.toString());
        System.out.println(bean.getBenutzer().toString());
        
    } catch (DatenbankFehlerException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        fail();
    }
       
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
            assertTrue("Exception sollte eine DatenbankFehlerException sein",e instanceof DatenbankFehlerException);
            assertEquals("Falsche Msg. wird angezeigt",DatenbankFehlerException.ARGUMENT_IST_NULL, e.getMessage());
        }

        BenutzerkontoBean bean;
        Object result;
        BenutzerkontoBean ergebnisBean;
        // Benutzerbean schreiben
        try {
            bean = new BenutzerkontoBean();
            assertEquals(NullKonstanten.DUMMY_ID, bean.getId());
            result = aDB.schreibenObjekt(bean);
            assertTrue(result instanceof BenutzerkontoBean);
            ergebnisBean = (BenutzerkontoBean) result;
            assertEquals(bean, ergebnisBean);

            assertNotSame(NullKonstanten.DUMMY_ID, ergebnisBean.getId());
        } catch (Exception e) {
            fail("Sollte keine Exception werfen");
        }

    }

    /**
     * Test method for
     * {@link de.randi2.datenbank.DatenbankDummy#suchenObjekt(java.lang.Object)}.
     */
    @Test
    public void testSuchenBenutzerkontoBean() {

        try {
        	
        	Vector<BenutzerkontoBean>  ergebnisseBeans = null;
            String suchname = "sa@randi2.de";
            String passbrot = "1$studienarzt";
            BenutzerkontoBean suchbean = new BenutzerkontoBean();
            suchbean.setBenutzername(suchname);
            suchbean.setFilter(true);

            ergebnisseBeans = aDB.suchenObjekt(suchbean);
            assertEquals(1, ergebnisseBeans.size());

            Object tmp = ergebnisseBeans.firstElement();
            
            assertTrue(tmp instanceof BenutzerkontoBean);

            BenutzerkontoBean ergebnisBean = (BenutzerkontoBean) tmp;
            assertEquals(suchname, ergebnisBean.getBenutzername());
            assertEquals(Rolle.getStudienarzt(), ergebnisBean.getRolle());
            assertEquals(PasswortUtil.getInstance().hashPasswort(passbrot), ergebnisBean.getPasswort());
        } catch (Exception e) {
            fail("Ao!");
        }

    }
    
    @Test 
    public void testSuchenZentrumBean() throws ZentrumException{
        Vector<ZentrumBean>  ergebnis = null;
        ZentrumBean suchbean = new ZentrumBean();
        suchbean.setFilter(true);
        
        try { //finde Alle Zentren
            ergebnis = aDB.suchenObjekt(suchbean);
            assertEquals(4, ergebnis.size());
        } catch (DatenbankFehlerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ergebnis = null;
        // Finde ein Zentrum
        String passbrot ="inst1-abt2AA" ;
        suchbean.setInstitution("Institut1");
        suchbean.setAbteilung("Abteilung2");
        
        try { //finde ein Zentrum
            ergebnis = aDB.suchenObjekt(suchbean);
            assertEquals(1, ergebnis.size());
            ZentrumBean gefundenesBean = ergebnis.firstElement();
            System.out.println(gefundenesBean.toString());
            assertEquals(suchbean.getInstitution(), gefundenesBean.getInstitution());
            assertEquals(suchbean.getAbteilung(), gefundenesBean.getAbteilung());
            assertEquals(PasswortUtil.getInstance().hashPasswort(passbrot), gefundenesBean.getPasswort());
        } catch (DatenbankFehlerException e) {
            e.printStackTrace();
            fail();
        }

        
    }
    @Test
    public void testSchreibenSuchen(){
        BenutzerkontoBean schreibBean,leseBean;
        BenutzerkontoBean ergebnisBean;
        String testname = "Testname";
        try {
            // schreiben
            schreibBean = new BenutzerkontoBean();
            schreibBean.setBenutzername(testname);
            ergebnisBean = (BenutzerkontoBean) aDB.schreibenObjekt(schreibBean);
            // lesen
            BenutzerkontoBean suchbean = new BenutzerkontoBean();
            suchbean.setFilter(true);
            suchbean.setBenutzername(testname);
            
            assertEquals(schreibBean.getBenutzername(), suchbean.getBenutzername());
            
            leseBean = (BenutzerkontoBean) (aDB.suchenObjekt(suchbean)).firstElement();
            assertEquals(schreibBean.getBenutzername(),leseBean.getBenutzername());
        }catch(Exception e){
            e.printStackTrace();
            fail("Sollte keine Exception werfen");
        }
        
    }
    

}
