package de.randi2.junittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.randi2.model.exceptions.RechtException;
import de.randi2.model.fachklassen.Rolle;

/**
 * JUnittest fuer die Klasse {@link Rolle}
 * 
 * @author $eyma Yazgan [syazgan@stud.hs-heilbronn.de]
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
            assertEquals(RechtException.UNGUELITGES_ARGUMENT, e.getMessage());

        }
        // noch nen wirres argument
        try {
            Rolle.getRolle("Kann ganicht klappen");
            fail("Pruefung gg ungueltiges Argument missglueckt");
        } catch (Exception e) {
            assertTrue(e instanceof RechtException);
            assertEquals(RechtException.UNGUELITGES_ARGUMENT, e.getMessage());

        }
    }
}
