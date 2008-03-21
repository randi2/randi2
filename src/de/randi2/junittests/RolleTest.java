package de.randi2.junittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.model.exceptions.RechtException;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.Recht.Rechtenamen;
import de.randi2.utility.Log4jInit;
/**
 * JUnittest fuer die Klasse {@link Rolle}
 * 
 * @author BTheel [BTheel@stud.hs-heilbronn.de]
 * @version $Id: RolleTest.java 2428 2007-05-06 17:50:32Z btheel $
 */
public class RolleTest {

    private Rolle aRolle;

    private static final String NAME_STUDIENARZT = "STUDIENARZT";

    private static final String NAME_STUDIENLEITER = "STUDIENLEITER";

    private static final String NAME_ADMIN = "ADMIN";

    private static final String NAME_SYSOP = "SYSOP";

    private static final String NAME_STATISTIKER = "STATISTIKER";

    /**
     * Initialisiert den Logger. Bitte log4j.lcf.pat in log4j.lcf umbenennen und es funktioniert.
     *
     */
    @BeforeClass
    public static void log(){
	Log4jInit.initDebug();
    }
    /**
     * aRolle-><code>null</code>
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        aRolle = null;
    }

    /**
     * Holt sich die Instanz der Rolle, prueft gg. <code>null</code>,
     * vergleicht die Methoden toString() und getName() gg. den Rechenamen und
     * vergleight die Instanzen
     */
    @Test
    public void testGetStudienarzt() {
        aRolle = Rolle.getStudienarzt();
        assertNotNull(aRolle);
        assertEquals(NAME_STUDIENARZT, aRolle.toString());
        assertEquals(NAME_STUDIENARZT, aRolle.getName());
        assertSame(Rolle.getStudienarzt(), aRolle);

    }

    /**
     * Holt sich die Instanz der Rolle, prueft gg. <code>null</code>,
     * vergleicht die Methoden toString() und getName() gg. den Rechenamen und
     * vergleight die Instanzen
     */
    @Test
    public void testGetStudienleiter() {
        aRolle = Rolle.getStudienleiter();
        assertNotNull(aRolle);
        assertEquals(NAME_STUDIENLEITER, aRolle.toString());
        assertEquals(NAME_STUDIENLEITER, aRolle.getName());
        assertSame(Rolle.getStudienleiter(), aRolle);
    }

    /**
     * Holt sich die Instanz der Rolle, prueft gg. <code>null</code>,
     * vergleicht die Methoden toString() und getName() gg. den Rechenamen und
     * vergleight die Instanzen
     */
    @Test
    public void testGetAdmin() {
        aRolle = Rolle.getAdmin();
        assertNotNull(aRolle);
        assertEquals(NAME_ADMIN, aRolle.toString());
        assertEquals(NAME_ADMIN, aRolle.getName());
        assertSame(Rolle.getAdmin(), aRolle);
    }

    /**
     * Holt sich die Instanz der Rolle, prueft gg. <code>null</code>,
     * vergleicht die Methoden toString() und getName() gg. den Rechenamen und
     * vergleight die Instanzen
     */
    @Test
    public void testGetSysop() {

        aRolle = Rolle.getSysop();
        assertNotNull(aRolle);
        assertEquals(NAME_SYSOP, aRolle.toString());
        assertEquals(NAME_SYSOP, aRolle.getName());
        assertSame(Rolle.getSysop(), aRolle);
    }

    /**
     * Holt sich die Instanz der Rolle, prueft gg. <code>null</code>,
     * vergleicht die Methoden toString() und getName() gg. den Rechenamen und
     * vergleight die Instanzen
     */
    @Test
    public void testGetStatistiker() {

        aRolle = Rolle.getStatistiker();
        assertNotNull(aRolle);
        assertEquals(NAME_STATISTIKER, aRolle.toString());
        assertEquals(NAME_STATISTIKER, aRolle.getName());
        assertSame(Rolle.getStatistiker(), aRolle);
    }

    /**
     * Test method for
     * {@link de.randi2.model.fachklassen.Rolle#getRolle(String)}.
     */
    @Test
    public final void testGetRolle() {
        // Admin
        try {
            aRolle = Rolle.getRolle(NAME_ADMIN);
            assertEquals(Rolle.getAdmin(), aRolle);
        } catch (RechtException e) {
            fail("Rollentest fehlgeschlagen: Admin");
        }
        // Statistiker
        aRolle = null;
        try {
            aRolle = Rolle.getRolle(NAME_STATISTIKER);
            assertEquals(Rolle.getStatistiker(), aRolle);
        } catch (RechtException e) {
            fail("Rollentest fehlgeschlagen: Statistiker");
        }
        // Studienleiter
        aRolle = null;
        try {
            aRolle = Rolle.getRolle(NAME_STUDIENLEITER);
            assertEquals(Rolle.getStudienleiter(), aRolle);
        } catch (RechtException e) {
            fail("Rollentest fehlgeschlagen: Studienleiter");
        }
        // Studienarzt
        aRolle = null;
        try {
            aRolle = Rolle.getRolle(NAME_STUDIENARZT);
            assertEquals(Rolle.getStudienarzt(), aRolle);
        } catch (RechtException e) {
            fail("Rollentest fehlgeschlagen: Studienarzt");
        }
        // Sysop
        aRolle = null;
        try {
            aRolle = Rolle.getRolle(NAME_SYSOP);
            assertEquals(Rolle.getSysop(), aRolle);
        } catch (RechtException e) {
            fail("Rollentest fehlgeschlagen: Sysop");
        }
        // null
        try {
            Rolle.getRolle(null);
            fail("Test gg null missglueckt");
        } catch (Exception e) {
            assertTrue(e instanceof RechtException);
            assertEquals(RechtException.NULL_ARGUMENT, e.getMessage());

        }
        // wirres argument
        try {
            Rolle.getRolle("");
            fail("Pruefung gg ungueltiges Argument missglueckt");
        } catch (Exception e) {
            assertTrue(e instanceof RechtException);
            assertEquals(RechtException.UNGUELTIGES_ARGUMENT, e.getMessage());

        }
        // noch nen wirres argument
        try {
            Rolle.getRolle("Kann ganicht klappen");
            fail("Pruefung gg ungueltiges Argument missglueckt");
        } catch (Exception e) {
            assertTrue(e instanceof RechtException);
            assertEquals(RechtException.UNGUELTIGES_ARGUMENT, e.getMessage());

        }
    }
    
    @Test 
    public final void besitzenStudienarztRecht(){
        
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.ADMINACCOUNTS_VERWALTEN));
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.ARCHIV_EINSEHEN));
        assertTrue(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.BK_AENDERN));
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.BK_ANSEHEN));
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.BK_SPERREN));
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.GRUPPENNACHRICHT_VERSENDEN));
        assertTrue(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.RANDOMISATION_EXPORTIEREN));
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.STAT_EINSEHEN));
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.STUDIE_AENDERN));
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.STUDIE_ANLEGEN));
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.STUDIE_LOESCHEN));
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.STUDIE_PAUSIEREN));
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.STUDIE_SIMULIEREN));
        assertTrue(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.STUDIEN_EINSEHEN));
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.STUDIENARM_BEENDEN));
        assertTrue(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.STUDIENTEILNEHMER_HINZUFUEGEN));
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.STULEIACCOUNTS_VERWALTEN));
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.SYSTEM_SPERREN));
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.ZENTREN_ANZEIGEN));
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.ZENTRUM_AENDERN));
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.ZENTRUM_AKTIVIEREN));
        assertFalse(Rolle.getStudienarzt().besitzenRolleRecht(Rechtenamen.ZENTRUM_ANLEGEN));
        
    }
    
    @Test 
    public final void besitzenStratistikerRecht(){
        
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.ADMINACCOUNTS_VERWALTEN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.ARCHIV_EINSEHEN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.BK_AENDERN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.BK_ANSEHEN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.BK_SPERREN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.GRUPPENNACHRICHT_VERSENDEN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.RANDOMISATION_EXPORTIEREN));
        assertTrue(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.STAT_EINSEHEN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.STUDIE_AENDERN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.STUDIE_ANLEGEN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.STUDIE_LOESCHEN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.STUDIE_PAUSIEREN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.STUDIE_SIMULIEREN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.STUDIEN_EINSEHEN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.STUDIENARM_BEENDEN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.STUDIENTEILNEHMER_HINZUFUEGEN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.STULEIACCOUNTS_VERWALTEN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.SYSTEM_SPERREN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.ZENTREN_ANZEIGEN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.ZENTRUM_AENDERN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.ZENTRUM_AKTIVIEREN));
        assertFalse(Rolle.getStatistiker().besitzenRolleRecht(Rechtenamen.ZENTRUM_ANLEGEN));
        
    }
    
    @Test 
    public final void besitzenAdminRecht(){
        
        assertFalse(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.ADMINACCOUNTS_VERWALTEN));
        assertTrue(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.ARCHIV_EINSEHEN));
        assertTrue(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.BK_AENDERN));
        assertTrue(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.BK_ANSEHEN));
        assertTrue(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.BK_SPERREN));
        assertTrue(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.GRUPPENNACHRICHT_VERSENDEN));
        assertTrue(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.RANDOMISATION_EXPORTIEREN));
        assertTrue(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.STAT_EINSEHEN));
        assertFalse(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.STUDIE_AENDERN));
        assertFalse(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.STUDIE_ANLEGEN));
        assertTrue(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.STUDIE_LOESCHEN));
        assertFalse(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.STUDIE_PAUSIEREN));
        assertFalse(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.STUDIE_SIMULIEREN));
        assertTrue(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.STUDIEN_EINSEHEN));
        assertFalse(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.STUDIENARM_BEENDEN));
        assertFalse(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.STUDIENTEILNEHMER_HINZUFUEGEN));
        assertTrue(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.STULEIACCOUNTS_VERWALTEN));
        assertFalse(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.SYSTEM_SPERREN));
        assertTrue(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.ZENTREN_ANZEIGEN));
        assertTrue(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.ZENTRUM_AENDERN));
        assertTrue(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.ZENTRUM_AKTIVIEREN));
        assertTrue(Rolle.getAdmin().besitzenRolleRecht(Rechtenamen.ZENTRUM_ANLEGEN));
        
        
    }
    
    @Test 
    public final void besitzenSysopRecht(){
        
        assertTrue(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.ADMINACCOUNTS_VERWALTEN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.ARCHIV_EINSEHEN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.BK_AENDERN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.BK_ANSEHEN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.BK_SPERREN));
        assertTrue(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.GRUPPENNACHRICHT_VERSENDEN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.RANDOMISATION_EXPORTIEREN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.STAT_EINSEHEN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.STUDIE_AENDERN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.STUDIE_ANLEGEN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.STUDIE_LOESCHEN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.STUDIE_PAUSIEREN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.STUDIE_SIMULIEREN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.STUDIEN_EINSEHEN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.STUDIENARM_BEENDEN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.STUDIENTEILNEHMER_HINZUFUEGEN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.STULEIACCOUNTS_VERWALTEN));
        assertTrue(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.SYSTEM_SPERREN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.ZENTREN_ANZEIGEN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.ZENTRUM_AENDERN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.ZENTRUM_AKTIVIEREN));
        assertFalse(Rolle.getSysop().besitzenRolleRecht(Rechtenamen.ZENTRUM_ANLEGEN));
        
    }
    
    @Test 
    public final void besitzenStudienleiterRecht(){
        
        assertFalse(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.ADMINACCOUNTS_VERWALTEN));
        assertTrue(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.ARCHIV_EINSEHEN));
        assertTrue(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.BK_AENDERN));
        assertTrue(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.BK_ANSEHEN));
        assertFalse(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.BK_SPERREN));
        assertTrue(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.GRUPPENNACHRICHT_VERSENDEN));
        assertTrue(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.RANDOMISATION_EXPORTIEREN));
        assertTrue(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.STAT_EINSEHEN));
        assertTrue(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.STUDIE_AENDERN));
        assertTrue(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.STUDIE_ANLEGEN));
        assertFalse(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.STUDIE_LOESCHEN));
        assertTrue(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.STUDIE_PAUSIEREN));
        assertTrue(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.STUDIE_SIMULIEREN));
        assertTrue(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.STUDIEN_EINSEHEN));
        assertTrue(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.STUDIENARM_BEENDEN));
        assertFalse(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.STUDIENTEILNEHMER_HINZUFUEGEN));
        assertFalse(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.STULEIACCOUNTS_VERWALTEN));
        assertFalse(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.SYSTEM_SPERREN));
        assertTrue(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.ZENTREN_ANZEIGEN));
        assertFalse(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.ZENTRUM_AENDERN));
        assertFalse(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.ZENTRUM_AKTIVIEREN));
        assertFalse(Rolle.getStudienleiter().besitzenRolleRecht(Rechtenamen.ZENTRUM_ANLEGEN));
        
    }
}
