package de.randi2.junittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.randi2.model.exceptions.RechtException;
import de.randi2.model.fachklassen.Recht;
import de.randi2.model.fachklassen.Recht.Rechtenamen;

/**
 * Testet die Funktionalitaet der Recht.java Klasse.
 * 
 * 
 * @author Nadine Zwink <nzwink@stud.hs-heilbronn.de> *
 * @author Daniel Haehn <dhaehn@stud.hs-heilbronn.de>
 * @version $Id$
 */
public class RechtTest {

    private Recht aRecht;

    private Rechtenamen rName;

    /**
     * Freeze der Rechte-ENUM vom 07.02.2007 21:58 Uhr
     * 
     * 
     */
    private String[] rechtenamenFrozen = { "BK_AENDERN", "BK_SPERREN",
            "BK_ANSEHEN", "ZENTRUM_ANLEGEN", "ZENTRUM_AENDERN",
            "ZENTREN_ANZEIGEN", "ZENTRUM_AKTIVIEREN",
            "GRUPPENNACHRICHT_VERSENDEN", "STUDIE_ANLEGEN", "STUDIE_AENDERN",
            "STUDIE_LOESCHEN", "STUDIE_PAUSIEREN", "STUDIENARM_BEENDEN",
            "STUDIE_SIMULIEREN", "ARCHIV_EINSEHEN", "STAT_EINSEHEN",
            "RANDOMISATION_EXPORTIEREN", "STUDIENTEILNEHMER_HINZUFUEGEN",
            "STUDIEN_EINSEHEN", "STUDIE_RANDOMISIEREN", "SYSTEM_SPERREN",
            "ADMINACCOUNTS_VERWALTEN", "STULEIACCOUNTS_VERWALTEN" };

    /**
     * HashMap, welche die Instanzen der einzelnen Rechte verwaltet.
     */
    // private static HashMap<Rechtenamen, Recht> rechte = new
    // HashMap<Rechtenamen, Recht>();
    /**
     * Initialisiert eine Testmethode.
     */
    @Before
    public void setUp() {

    }

    /**
     * Beendet eine Testmethode.
     */
    @After
    public void tearDown() throws Exception {

    }

    /**
     * Test method for
     * {@link de.randi2.model.fachklassen.Recht#getRecht(de.randi2.model.fachklassen.Recht.Rechtenamen)}
     * {@link de.randi2.model.fachklassen.Recht#getRecht(de.randi2.model.fachklassen.Recht.getRecht)}
     * {@link de.randi2.model.fachklassen.Recht#getRecht(de.randi2.model.fachklassen.Recht.toString)}
     * {@link de.randi2.model.fachklassen.Recht#getRecht(de.randi2.model.fachklassen.Recht.getName)}
     * {@link de.randi2.model.fachklassen.Recht#getRecht(de.randi2.model.fachklassen.Recht.getRechtname)}
     * 
     * Durchlaeuft alle vorhandenen Rechtenamen und prueft ob alle Rechte in der
     * "frozen" und "korrekten" Rechtetabelle enthalten sind.
     * 
     */
    @Test
    public void testDurchlaufFuerAlleRechte() {

        // alle Rechtenamen aus der ENUM auslesen
        for (Recht.Rechtenamen recht : Recht.Rechtenamen.values()) {

            rName = recht;

            boolean rechtInEnum = false;

            // prueft ob das aktuelle Recht in der frozen Rechtetabelle
            // enthalten ist
            for (int i = 0; i < rechtenamenFrozen.length; i++) {

                if (rechtenamenFrozen[i].equals(rName.toString())) {
                    rechtInEnum = true;
                }

            }
            assertTrue(rechtInEnum);

                aRecht = Recht.getRecht(recht);

                // pruefen ob toString fuer dieses Recht korrekt arbeitet
                assertTrue(aRecht.toString().equals(
                        "Recht: " + rName.toString()));

                // pruefen ob getName fuer dieses Recht korrekt arbeitet
                assertTrue(aRecht.getName().equals(rName.toString()));

                // pruefen ob getRechtname fuer dieses Recht korrekt arbeitet
                assertTrue(rName.equals(aRecht.getRechtname()));

        }

    }

}
