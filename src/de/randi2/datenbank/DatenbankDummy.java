package de.randi2.datenbank;

import java.util.HashMap;
import java.util.Vector;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;

import de.randi2.model.fachklassen.*;
import de.randi2.model.fachklassen.beans.*;

/**
 * @author Benjamin Theel <Btheel@stud.hs-heilbronn.de>
 * @version 1.0
 */
public class DatenbankDummy implements DatenbankSchnittstelle {
    /*
     * Changelog: 30.01.2007 BTheel: Klasse erstellt 
     * XXX Realisierung eines Singletons durch ein Interface fraglich!
     * Workaround mit static instanz der HashMap. 
     * 
     */
    private static HashMap<String, BenutzerkontoBean> konten = null;

    public DatenbankDummy() {
        if (konten == null){
        konten = new HashMap<String, BenutzerkontoBean>();
        generiereTestKonten();
        }
    }

    /**
     * Erzeugt die in dem Testprotokoll definierten Konten
     */
    private void generiereTestKonten() {
        // Dummy Person erzeugen
        try {
            PersonBean dummyPerson = null;
            /*
             * new PersonBean("Nachname Dummy", "Vorname Dummy", "Prof. Dr.",
             * 'm', "Dummy@Dummy-Land.de", "49.04-123456", "49.04-123456",
             * "49.04-123456");
             */

            BenutzerkontoBean kontoBean; // Arbeitsinstanz KontoBean

            kontoBean = new BenutzerkontoBean("stat", "stat",
                    dummyPerson);
            kontoBean.setRolle(Rolle.getStatistiker());
            konten.put("stat", kontoBean);

            kontoBean = new BenutzerkontoBean("sysop", "sysop",
                    dummyPerson);
            kontoBean.setRolle(Rolle.getSysop());
            konten.put("sysop", kontoBean);

            kontoBean = new BenutzerkontoBean("sa@randi2.de", "sa",
                    dummyPerson);
            kontoBean.setRolle(Rolle.getStudienarzt());
            konten.put("sa@randi2.de", kontoBean);

            kontoBean = new BenutzerkontoBean("sl", "sl", dummyPerson);
            kontoBean.setRolle(Rolle.getStudienleiter());
            konten.put("sl", kontoBean);

            kontoBean = new BenutzerkontoBean("admin", "admin",
                    dummyPerson);
            kontoBean.setRolle(Rolle.getAdmin());
            konten.put("admin", kontoBean);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.randi2.datenbank.DatenbankSchnittstelle#schreibenObjekt(java.lang.Object)
     */
    public Object schreibenObjekt(Object zuSchreibendesObjekt)
            throws DatenbankFehlerException, IllegalArgumentException {
        if (zuSchreibendesObjekt == null)
            throw new IllegalArgumentException("Argument == null");
        // TODO Fehlerkonstante setzten
        if (zuSchreibendesObjekt instanceof BenutzerkontoBean) {
            BenutzerkontoBean aBean = (BenutzerkontoBean) zuSchreibendesObjekt;

            if (!konten.containsKey(aBean.getBenutzername())) {
                konten.put(aBean.getBenutzername(), aBean);
                aBean.setId(konten.size());
            }
            return aBean;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.randi2.datenbank.DatenbankSchnittstelle#suchenObjekt(java.lang.Object)
     */
    public Vector<Object> suchenObjekt(Object zuSuchendesObjekt)
            throws DatenbankFehlerException {
        //XXX Passwoerter werden derzeit ungehashed gespeichert! 
        Vector<Object> ergebisse = new Vector<Object>();

        if (zuSuchendesObjekt == null)
            // TODO Reicht das? ggf. IlligalArgumentException
            return ergebisse;

        // suchen eines benutzerkontos:
        if (zuSuchendesObjekt instanceof BenutzerkontoBean) {
            String suchname = ((BenutzerkontoBean) zuSuchendesObjekt)
                    .getBenutzername();
            if (konten.containsKey(suchname)) {
                ergebisse.add(konten.get(suchname));
            }
        }
        return ergebisse;
    }

}
