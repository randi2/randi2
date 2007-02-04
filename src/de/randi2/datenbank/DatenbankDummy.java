package de.randi2.datenbank;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import org.apache.log4j.Logger;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.fachklassen.*;
import de.randi2.model.fachklassen.beans.*;
import de.randi2.utility.NullKonstanten;
import de.randi2.utility.PasswortUtil;

/**
 * @author Benjamin Theel <Btheel@stud.hs-heilbronn.de>
 * @author Daniel Haehn <dhaehn@stud.hs-heilbronn.de>
 * @version $Id$
 */
public class DatenbankDummy implements DatenbankSchnittstelle {
    /*
     * Changelog: 30.01.2007 BTheel: Klasse erstellt XXX Realisierung eines
     * Singletons durch ein Interface fraglich!XXX Workaround mit static instanz
     * der HashMap.
     * 
     * 04.02.2007 DHaehn: den Benutzerkonten Personen zugeordnet
     * 
     */
    Logger logger = Logger.getLogger(this.getClass());

    private static HashMap<String, BenutzerkontoBean> konten = null;

    private static HashMap<String, ZentrumBean> zentren = null;

    public DatenbankDummy() {
        if (konten == null) {// wenn null, dann erster Aufruf -> konten und
            // zentren init
            logger.debug("Init: DB-Dummy");
            konten = new HashMap<String, BenutzerkontoBean>();
            zentren = new HashMap<String, ZentrumBean>();
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
        	//PersonBean dummyPerson = null;
            BenutzerkontoBean kontoBean; // Arbeitsinstanz KontoBean
            ZentrumBean zentrumBean;// Arbeitsinstanz KontoBean
            PasswortUtil hashmich = PasswortUtil.getInstance(); // Passworthasher

            zentrumBean = new ZentrumBean();
            // Zentren Anlegen
            zentrumBean.setId(1);
            zentrumBean.setInstitution("Institut1");
            zentrumBean.setAbteilung("Abteilung1");
            zentrumBean.setPasswort(hashmich.hashPasswort("inst1-abt1"));
            zentren.put(zentrumBean.getInstitution() + "-"
                    + zentrumBean.getAbteilung(), zentrumBean);

            zentrumBean = new ZentrumBean();
            zentrumBean.setId(2);
            zentrumBean.setInstitution("Institut1");
            zentrumBean.setAbteilung("Abteilung2");
            zentrumBean.setPasswort(hashmich.hashPasswort("inst1-abt2"));
            zentren.put(zentrumBean.getInstitution() + "-"
                    + zentrumBean.getAbteilung(), zentrumBean);

            zentrumBean = new ZentrumBean();
            zentrumBean.setId(3);
            zentrumBean.setInstitution("Institut2");
            zentrumBean.setAbteilung("Abteilung1");
            zentrumBean.setPasswort(hashmich.hashPasswort("inst2-abt1"));
            zentren.put(zentrumBean.getInstitution() + "-"
                    + zentrumBean.getAbteilung(), zentrumBean);

            zentrumBean = new ZentrumBean();
            zentrumBean.setId(4);
            zentrumBean.setInstitution("Institut2");
            zentrumBean.setAbteilung("Abteilung2");
            zentrumBean.setPasswort(hashmich.hashPasswort("inst2-abt2"));
            zentren.put(zentrumBean.getInstitution() + "-"
                    + zentrumBean.getAbteilung(), zentrumBean);

            // Konten Anlegen
            kontoBean = new BenutzerkontoBean("statistiker", hashmich
                    .hashPasswort("1$statistiker"), new PersonBean("Kaese", "Irene", "", 'w', "stat@randi2.de", "022231130","0222333444111","0211212112"));
            kontoBean.setRolle(Rolle.getStatistiker());
            this.schreibenObjekt(kontoBean);

            kontoBean = new BenutzerkontoBean("systemoperator", hashmich
                    .hashPasswort("1$systemoperator"), new PersonBean("Maulwurf", "Hans", "", 'm', "sysop@randi2.de", "022231130","0222333444111","0211212112"));
            kontoBean.setRolle(Rolle.getSysop());
            this.schreibenObjekt(kontoBean);

            kontoBean = new BenutzerkontoBean("sa@randi2.de", hashmich
                    .hashPasswort("1$studienarzt"), new PersonBean("Wurst", "Hans", "", 'm', "sa@randi2.de", "022231130","0222333444111","0211212112"));
            kontoBean.setRolle(Rolle.getStudienarzt());
            this.schreibenObjekt(kontoBean);

            kontoBean = new BenutzerkontoBean("studienleiter", hashmich
                    .hashPasswort("1$studienleiter"), new PersonBean("Wurst", "Birgit", "", 'w', "sl@randi2.de", "022231130","0222333444111","0211212112"));
            kontoBean.setRolle(Rolle.getStudienleiter());
            this.schreibenObjekt(kontoBean);

            kontoBean = new BenutzerkontoBean("administrator", hashmich
                    .hashPasswort("1$administrator"), new PersonBean("Wurst", "Trude", "", 'w', "admin@randi2.de", "022231130","0222333444111","0211212112"));
            kontoBean.setRolle(Rolle.getAdmin());
            this.schreibenObjekt(kontoBean);
        } catch (Exception e) {
            logger.error("Exception: DatenbankDummy.generiereTestKonten()", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.randi2.datenbank.DatenbankSchnittstelle#schreibenObjekt(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public <T extends Filter> T schreibenObjekt(T zuSchreibendesObjekt)
            throws DatenbankFehlerException, IllegalArgumentException {
        logger.info("DatenbankDummy.schreibenObjekt()");
        if (zuSchreibendesObjekt == null)
            throw new IllegalArgumentException("Argument == null");
        // TODO Fehlerkonstante setzten
        if (zuSchreibendesObjekt instanceof BenutzerkontoBean) {
            logger.debug("Schreib-Objekt ist BenutzerkontoBean");
            BenutzerkontoBean aBean = (BenutzerkontoBean) zuSchreibendesObjekt;
            if (aBean.getId() != NullKonstanten.DUMMY_ID
                    && konten.containsKey(aBean.getBenutzername()))
                // Konto hat bereits eine ID und ist in der DB Vorhanden -> Obj.
                // ueberschreiben
                konten.put(aBean.getBenutzername(), aBean);
            else if (!konten.containsKey(aBean.getBenutzername())) {
                // Konto ist noch nicht in der DB vorhanden -> Obj
                // reinschreiben, ID setzten, OBj. zurueckliefern
                logger.debug("BenutzerkontoBean in DB-Dummy aufnehmen");
                aBean.setId(konten.size());
                konten.put(aBean.getBenutzername(), aBean);

                logger.debug("Konto-ID: " + aBean.getId());
            }
            return (T) aBean;
        } else
            return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.randi2.datenbank.DatenbankSchnittstelle#suchenObjekt(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public <T extends Filter> Vector<T> suchenObjekt(T zuSuchendesObjekt)
            throws DatenbankFehlerException {
        logger.info("DatenbankDummy.suchenObjekt()");

        if (!zuSuchendesObjekt.isFilter()) {
            logger.warn("Suchobject ist kein aktiver Filter");
            // TODO benachrichtigung an benunter, das filter nicht gesetzt
        }
        Vector<T> ergebnisse = new Vector<T>();

        if (zuSuchendesObjekt == null)
            // TODO Reicht das? ggf. IlligalArgumentException
            return ergebnisse;

        // suchen eines benutzerkontos:
        if (zuSuchendesObjekt instanceof BenutzerkontoBean) {
            logger.debug("Suchbean ist BenutzerkontoBean");
            String suchname = ((BenutzerkontoBean) zuSuchendesObjekt)
                    .getBenutzername();
            if (suchname.equals("")) {
                for (Iterator iter = konten.values().iterator(); iter.hasNext();) {
                    T element = (T) iter.next();
                    ergebnisse.add(element);

                }
            }

            else if (konten.containsKey(suchname)) {
                logger.debug("gesuchtes Konto in DB vorhanden");
                ergebnisse.add((T) konten.get(suchname));
            }
        } else if (zuSuchendesObjekt instanceof ZentrumBean) {
            logger.debug("Suchbean ist ZentrumBean");
            ZentrumBean aBean = (ZentrumBean) zuSuchendesObjekt;
            
            // Alle Zentren liefern
            if (aBean.getInstitution() == null& aBean.getAbteilung() == null) {
                for (Iterator iter = zentren.values().iterator(); iter.hasNext();) {
                    T element = (T) iter.next();
                    ergebnisse.add(element);

                }
            }
            // genau ein Zentrum liefern
            else if (zentren.containsKey(aBean.getInstitution() + "-"
                    + aBean.getAbteilung())) {
                logger.debug("gesuchtes Zentrum in DB vorhanden");
                ergebnisse.add((T) zentren.get(aBean.getInstitution() + "-"
                        + aBean.getAbteilung()));
            }
//            else if (zentren.containsKey(aBean.getInstitution())) {
//                logger.debug("gesuchtes Zentrum in DB vorhanden");
//                ergebnisse.add((T) zentren.get(aBean.getInstitution()));
//            }
        }

        return ergebnisse;
    }
}
