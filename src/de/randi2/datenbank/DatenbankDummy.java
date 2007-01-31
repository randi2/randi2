package de.randi2.datenbank;

import java.util.HashMap;
import java.util.Vector;
import org.apache.log4j.Logger;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.fachklassen.*;
import de.randi2.model.fachklassen.beans.*;
import de.randi2.utility.PasswortUtil;

/**
 * @author Benjamin Theel <Btheel@stud.hs-heilbronn.de>
 * @version $Id$
 */
public class DatenbankDummy implements DatenbankSchnittstelle {
    /*
     * Changelog: 30.01.2007 BTheel: Klasse erstellt XXX Realisierung eines
     * Singletons durch ein Interface fraglich!XXX Workaround mit static instanz
     * der HashMap.
     * 
     */
    Logger logger = Logger.getLogger(this.getClass());

    private static HashMap<String, BenutzerkontoBean> konten = null;

    public DatenbankDummy() {
        if (konten == null) {
            logger.debug("Init: DB-Dummy");
            konten = new HashMap<String, BenutzerkontoBean>();
            generierenTestKonten();
        } else
            logger.warn("erneuter Init-Versuch des DB-Dummies (ohne Effekt!)");
    }

    /**
     * Erzeugt die in dem Testprotokoll definierten Konten
     */
    private void generierenTestKonten() {
        logger.info("DatenbankDummy.generiereTestKonten()");
        // Dummy Person erzeugen
        try {
            PersonBean dummyPerson = null;
            /*
             * new PersonBean("Nachname Dummy", "Vorname Dummy", "Prof. Dr.",
             * 'm', "Dummy@Dummy-Land.de", "49.04-123456", "49.04-123456",
             * "49.04-123456");
             */

            BenutzerkontoBean kontoBean; // Arbeitsinstanz KontoBean
            PasswortUtil hashmich = PasswortUtil.getInstance();

            kontoBean = new BenutzerkontoBean("stat", hashmich
                    .hashPasswort("stat"), dummyPerson);
            kontoBean.setRolle(Rolle.getStatistiker());
            konten.put("stat", kontoBean);

            kontoBean = new BenutzerkontoBean("sysop", hashmich
                    .hashPasswort("sysop"), dummyPerson);
            kontoBean.setRolle(Rolle.getSysop());
            konten.put("sysop", kontoBean);

            kontoBean = new BenutzerkontoBean("sa@randi2.de", hashmich
                    .hashPasswort("sa"), dummyPerson);
            kontoBean.setRolle(Rolle.getStudienarzt());
            konten.put("sa@randi2.de", kontoBean);

            kontoBean = new BenutzerkontoBean("sl",
                    hashmich.hashPasswort("sl"), dummyPerson);
            kontoBean.setRolle(Rolle.getStudienleiter());
            konten.put("sl", kontoBean);

            kontoBean = new BenutzerkontoBean("admin", hashmich
                    .hashPasswort("admin"), dummyPerson);
            kontoBean.setRolle(Rolle.getAdmin());
            konten.put("admin", kontoBean);
        } catch (Exception e) {
            logger.error("Exception: DatenbankDummy.generiereTestKonten()", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.randi2.datenbank.DatenbankSchnittstelle#schreibenObjekt(java.lang.Object)
     */
    public Object schreibenObjekt(Object zuSchreibendesObjekt)
            throws DatenbankFehlerException, IllegalArgumentException {
        logger.info("DatenbankDummy.schreibenObjekt()");
        if (zuSchreibendesObjekt == null)
            throw new IllegalArgumentException("Argument == null");
        // TODO Fehlerkonstante setzten
        if (zuSchreibendesObjekt instanceof BenutzerkontoBean) {
            logger.debug("Schreib-Objekt ist BenutzerkontoBean");
            BenutzerkontoBean aBean = (BenutzerkontoBean) zuSchreibendesObjekt;

            if (!konten.containsKey(aBean.getBenutzername())) {
                logger.debug("BenutzerkontoBean in DB-Dummy aufnehmen");
                konten.put(aBean.getBenutzername(), aBean);
                aBean.setId(konten.size());
                logger.debug("Konto-ID: " + aBean.getId());
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
        logger.info("DatenbankDummy.suchenObjekt()");
        // XXX Passwoerter werden derzeit ungehashed gespeichert!
        Vector<Object> ergebnisse = new Vector<Object>();

        if (zuSuchendesObjekt == null)
            // TODO Reicht das? ggf. IlligalArgumentException
            return ergebnisse;

        // suchen eines benutzerkontos:
        if (zuSuchendesObjekt instanceof BenutzerkontoBean) {
            logger.debug("Suchbean ist BenutzerkontoBean");
            String suchname = ((BenutzerkontoBean) zuSuchendesObjekt)
                    .getBenutzername();
            if (konten.containsKey(suchname)) {
                logger.debug("gesuchtes Konto in DB vorhanden");
                ergebnisse.add(konten.get(suchname));
            }
        }
        return ergebnisse;
    }

}
