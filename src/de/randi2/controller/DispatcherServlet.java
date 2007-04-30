package de.randi2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.utility.Config;
import de.randi2.utility.LogAktion;
import de.randi2.utility.LogLayout;
import static de.randi2.utility.Config.Felder;

/**
 * <p>
 * Diese Klasse repraesentiert den DISPATCHER (== Weiterleiter). Dieser wird von
 * jeder Anfrage im Projekt angesprochen und leitet diese dann an die
 * entsprechenden Unterservlets bzw. direkt an JSPs weiter.
 * </p>
 * 
 * @version $Id$
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * 
 */
@SuppressWarnings("serial")
public class DispatcherServlet extends javax.servlet.http.HttpServlet {
    // TODO alle Parameter auf Enums umstellen --BTheel
    //TODO LogLayout beim Sperren?--Btheel
    /**
     * 
     */
    private boolean istSystemGesperrt = true;

    private String meldungSystemGesperrt = "Meldung des System ist gesperrt";

    /**
     * Mitteilung an den Benutzer beim Loginvorgang, welche Fehler aufgetreten
     * sind
     */
    public static final String FEHLERNACHRICHT = "fehlernachricht";

    public static final String IST_SYSTEM_GESPERRT = "Hurz";

    /**
     * Haelt die Begruendung der Systemsperrung
     */
    public static final String MITTEILUNG_SYSTEM_GESPERRT = "Systemmitteilung gesperrt";

    /**
     * Konstruktor.
     * 
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public DispatcherServlet() {
        super();
        istSystemGesperrt = Boolean.valueOf(Config
                .getProperty(Felder.SYSTEMSPERRUNG_SYSTEMSPERRUNG));
        //if (istSystemGesperrt) {
        /*
         * Laed Mitteilung immer aus der Config, wird im Formular zur Systemperrung als Vorbelegung angezeigt --Btheel
         */
            Logger.getLogger(this.getClass()).debug(
                    "Lade Mitteilung (System gesperrt) aus Config");
            meldungSystemGesperrt = Config
                    .getProperty(Felder.SYSTEMSPERRUNG_FEHLERMELDUNG);
        //}
    }

    /**
     * 
     * 
     */
    public enum anfrage_id {
        /**
         * 
         */
        JSP_HEADER_LOGOUT,
        /**
         * 
         */
        JSP_INDEX_LOGIN,
        /**
         * 
         */
        JSP_INDEX_BENUTZER_REGISTRIEREN_EINS,
        /**
         * 
         */
        JSP_BENUTZER_ANLEGEN_EINS_BENUTZER_REGISTRIEREN_ZWEI,
        /**
         * 
         */
        JSP_BENUTZER_ANLEGEN_ZWEI_BENUTZER_REGISTRIEREN_DREI,
        /**
         * 
         */
        JSP_BENUTZER_ANLEGEN_DREI_BENUTZER_REGISTRIEREN_VIER,

        /**
         * Leitet den Forward der system_sperren.jsp weiter (kommt als get)
         */
        JSP_SYSTEM_SPERREN,

        /**
         * Aufforderung das System zu Entsperren
         */
        AKTION_SYSTEM_ENTSPERREN,
        /**
         * Aufforderung das System zu Sperren
         */
        AKTION_SYSTEM_SPERREN
    }

    /**
     * Enhaelt die Parameternamen, die in der Session gesetzt werden koennen
     * 
     */
    public static enum sessionParameter {
        /**
         * Konto des Benutzers (BenutzerkontoBean)
         */
        A_Benutzer
    }

    /**
     * Enhaelt die Parameternamen, die in dem Request gesetzt werden koennen
     * 
     */
    public static enum requestParameter {

        /**
         * ID der Anfrage an den Dispatcher
         */
        ANFRAGE_ID("anfrage_id"),

        /**
         * Systemstatus gesperrt[true|false] (boolean)
         */
        IST_SYSTEM_GESPERRT("system_gesperrt"),

        /**
         * Haelt die Begruendung der Systemsperrung (String)
         */
        MITTEILUNG_SYSTEM_GESPERRT("mitteilung_system_gesperrt");

        String parameter = null;

        private requestParameter(String parameter) {
            this.parameter = parameter;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return this.parameter;
        }

    }

    // TODO Bitte Kommentar ueberpruefen und ggf. anpassen.
    /**
     * Diese Methode nimmt HTTP-GET-Request gemaess HTTP-Servlet Definition
     * entgegen.
     * <p>
     * Ist das System geperrt, so wird die Anfrage auf die Seite
     * <source>index_gesperrt.jsp</source> umgeleitet, ansonsten wird die Seite
     * <source>index.jsp</source> aufgerufen. </>
     * 
     * @param request
     *            Der Request fuer das Servlet.
     * @param response
     *            Der Response Servlet.
     * @throws IOException
     *             Falls Fehler in den E/A-Verarbeitung.
     * @throws ServletException
     *             Falls Fehler in der HTTP-Verarbeitung auftreten.
     * 
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
     *      HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        Logger.getLogger(this.getClass()).debug("Request, Typ 'GET' empfangen");

        String id = (String) request.getParameter("anfrage_id");

        if (id != null) {// wurde eine Id mitgesendet
            Logger.getLogger(this.getClass()).debug("[GET]anfrage_id: " + id);
            // logout ( FRAGE Logout wirklich an dieser Stelle?? oder in
            // BenutzerServelet)
            if (id
                    .equals(DispatcherServlet.anfrage_id.JSP_HEADER_LOGOUT
                            .name())) {
                loggeBenutzerAus(request, response);
            } else if (id
                    .equals(DispatcherServlet.anfrage_id.JSP_SYSTEM_SPERREN
                            .name())) {
                // FIXME Benutzer Muss eingeloggt sein! Sicherstellen --Btheel
                weiterleitungSystemSperrung(request, response);
            }
            return;
        } else {// keine anfrage_id empfangen -> Weiterleitung auf den entsprechenden Index
            if (istSystemGesperrt) {// System gesperrt
                Logger
                        .getLogger(this.getClass())
                        .debug(
                                "System gesperrt, leite nach 'index_gesperrt.jsp' um (korrekter Ablauf) ");
                request.setAttribute(MITTEILUNG_SYSTEM_GESPERRT,
                        meldungSystemGesperrt);
                request.getRequestDispatcher("index_gesperrt.jsp").forward(
                        request, response);
                return;
            } else {// System offen
                Logger
                        .getLogger(this.getClass())
                        .debug(
                                "System offen, leite nach 'index.jsp' um' (korrekter Ablauf)");
                request.getRequestDispatcher("index.jsp").forward(request,
                        response);
                return;

            }
        }

    }

    /**
     * Leitet die Anfrage auf die Seite '/system_sperren_main.jsp' weiter und bindet die dort benoetigten Werte an den Request.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void weiterleitungSystemSperrung(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        request.setAttribute(requestParameter.IST_SYSTEM_GESPERRT
                .toString(), istSystemGesperrt);
        request.setAttribute(
                    requestParameter.MITTEILUNG_SYSTEM_GESPERRT
                            .toString(), meldungSystemGesperrt);
        request.getRequestDispatcher("/system_sperren_main.jsp")
                .forward(request, response);
        return;
        
    }

    /**
     * Leitet die Anfrage nach Authentifikation an das BenutzerServlet weiter
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void weiterleitungBenutzerAnmelden(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        Logger.getLogger(this.getClass()).debug(
                "DispatcherServlet.weiterleitungBenutzerAnmelden()");
        request.setAttribute("anfrage_id", "CLASS_DISPATCHERSERVLET_LOGIN1");
        // request.setAttribute(requestParameter.IST_SYSTEM_GESPERRT.toString(),
        // istSystemGesperrt);
        request.setAttribute(IST_SYSTEM_GESPERRT, istSystemGesperrt);
        if (istSystemGesperrt) {
            // Request verliert Attr. deshalb neu setzten
            request.setAttribute(MITTEILUNG_SYSTEM_GESPERRT,
                    meldungSystemGesperrt);

        }
        request.getRequestDispatcher("BenutzerServlet").forward(request,
                response);

    }

    // TODO Bitte Kommentar ueberpruefen und ggf. anpassen.
    /**
     * Diese Methode nimmt HTTP-POST-Request gemaess HTTP-Servlet Definition
     * entgegen.
     * 
     * @param request
     *            Der Request fuer das Servlet.
     * @param response
     *            Der Response Servlet.
     * @throws IOException
     *             Falls Fehler in den E/A-Verarbeitung.
     * @throws ServletException
     *             Falls Fehler in der HTTP-Verarbeitung auftreten.
     * 
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
     *      HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String id = (String) request.getParameter("anfrage_id");
        String idAttribute = (String) request.getAttribute("anfrage_id");

        Logger.getLogger(this.getClass()).debug("[POST]anfrage_id: " + id);
        
        String meldung = (String) request.getAttribute("lol");
        System.out.println(meldung);
        
        if (id==null) {
            /*
             * ist keine ID gesetzt, so wird auf doGet umgeleitet Weitere Logik
             * dort --Btheel
             */
            doGet(request, response);
            Logger.getLogger(this.getClass()).debug(
                    "Anfrage-Id == null, Anfrage nach doGet umleiten");
        }
        if (idAttribute != null) {
            /*
             * XXX Frickelei unnoetig, da wenn Param null auch Attr null ist
             * abfrage des Attr. ausreichend --BTheel
             */
            id = idAttribute;
        } 


        // WEITERLEITUNGEN FUER BENUTZERSERVLET
        // [start]
        // Login
        if (id.equals(DispatcherServlet.anfrage_id.JSP_INDEX_LOGIN.name())) {
            weiterleitungBenutzerAnmelden(request, response);
        }

        // Benutzer registrieren
        // Schritt 1.1: STARTSEITE->DISCLAIMER
        else if (id
                .equals(DispatcherServlet.anfrage_id.JSP_INDEX_BENUTZER_REGISTRIEREN_EINS
                        .name())) {

            request.getRequestDispatcher("/benutzer_anlegen_eins.jsp").forward(
                    request, response);

        }
        // Schritt 2.1:DISCLAIMER->ZENTRUMAUSWAHL
        else if (id
                .equals(DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_EINS_BENUTZER_REGISTRIEREN_ZWEI
                        .name())) {

            request.setAttribute("anfrage_id",
                    "CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_ZWEI");
            request.getRequestDispatcher("ZentrumServlet").forward(request,
                    response);
        }

        // Schritt 3.1: ZENTRUMAUSWAHL: Filterung
        // Schritt 3.2 ZENTRUMAUSWAHL->BENUTZERDATEN_EINGEBEN
        else if (id
                .equals(DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_ZWEI_BENUTZER_REGISTRIEREN_DREI
                        .name())) {
            request.setAttribute("anfrage_id",
                    "CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_DREI");
            request.getRequestDispatcher("ZentrumServlet").forward(request,
                    response);

        }

        // Schritt 4: BENUTZERDATEN_EINGEBEN->
        else if (id
                .equals(DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_DREI_BENUTZER_REGISTRIEREN_VIER
                        .name())) {
            request.setAttribute("anfrage_id",
                    "CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_VIER");
            request.getRequestDispatcher("BenutzerServlet").forward(request,
                    response);

        } else if (id
                .equals(DispatcherServlet.anfrage_id.AKTION_SYSTEM_ENTSPERREN
                        .name())) {
            if (isBenutzerAngemeldet(request)) {
                this.setSystemGesperrt(false);
                Logger.getLogger(this.getClass()).debug(
                        "Schalte System wieder frei");
                LogAktion a = new LogAktion("System wurde entsperrt",
                        (BenutzerkontoBean) request.getSession().getAttribute("aBenutzer"));
                Logger.getLogger(LogLayout.LOGIN_LOGOUT).info(a);
                request.getRequestDispatcher("/system_sperren.jsp").forward(request, response);//TODO auf weiterleitung umlegen
                return;
            }
            Logger.getLogger(this.getClass()).warn(
            "Benutzer war nicht angemeldet beim freischalten!");
            return;
        }else if (id
                .equals(DispatcherServlet.anfrage_id.AKTION_SYSTEM_SPERREN
                        .name())) {
            if (isBenutzerAngemeldet(request)) {
                this.setSystemGesperrt(true);
               /*
                 String meldung = (String)request.getAttribute("lol");
                System.out.println(meldung);
                */
                this.setMeldungSystemGesperrt(meldung);
                
                
                Logger.getLogger(this.getClass()).debug(
                        "Sperre System. Grund: '"+getMeldungSystemGesperrt()+"'");
                LogAktion a = new LogAktion("System wurde gesperrt, Grund: "+getMeldungSystemGesperrt(),
                        (BenutzerkontoBean) request.getSession().getAttribute("aBenutzer"));
                Logger.getLogger(LogLayout.LOGIN_LOGOUT).info(a);
                request.getRequestDispatcher("/system_sperren.jsp").forward(request, response);//TODO auf weiterleitung umlegen
                return;
            }
            Logger.getLogger(this.getClass()).warn(
            "Benutzer war nicht angemeldet beim freischalten!");
            return;
        } else if (id
                .equals(DispatcherServlet.anfrage_id.JSP_SYSTEM_SPERREN
                        .name())) {
            weiterleitungSystemSperrung(request, response);
            return;
        }
        // [end]
        // WEITERLEITUNGEN FUER ZENTRUMSERVLET
        // [start]
        // [end]

        // SONSTIGE WEITERLEITUNGEN
        // Schwerer Fehler: Falscher Request
        else {
            System.out.println("Scheiße");
            Logger.getLogger(this.getClass()).debug(
                    "Kein Block in POST fuer die ID '"+id+"' gefunden");
            // TODO Hier muss noch entschieden werden,was passiert
        }
    }// doPost

    /**
     * Prueft, ob an der Session ein Benutzer angehaengt ist.<br>
     * Ist dies der Fall, so ist der Benutzer im System angemeldet.
     * 
     * @param request
     * @return <code>true</code>, wenn der Benutzer asngemeldet ist, anderenfalls <code>false</code>.
     */
    private boolean isBenutzerAngemeldet(HttpServletRequest request) {
        Logger.getLogger(this.getClass()).debug(
                "DispatcherServlet.isBenutzerAngemeldet()");
        boolean tmp =((request.getSession().getAttribute("aBenutzer")) != null);
        Logger.getLogger(this.getClass()).debug("Benutzer angemeldet (aBenuzter an Session? )"+tmp);
        // FIXME bei entfernen des Logeintrages die temp. Var. entfernen, aufruf direkt an den return setzten --Btheel
        return tmp;
    }

    private void loggeBenutzerAus(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        /*
         * request.getRequestDispatcher("index.jsp").forward(request, response);
         */
        // gefrickelt
        request.getSession().setAttribute("aBenutzer", null);
        request.getSession().invalidate();
        request.getSession(true);
        this.doGet(request, response);
    }

    /**
     * @return the istSystemGesperrt
     */
    public boolean istSystemGesperrt() {
        return istSystemGesperrt;
    }

    /**
     * @param istSystemGesperrt
     *            the istSystemGesperrt to set
     */
    public void setSystemGesperrt(boolean istSystemGesperrt) {
        this.istSystemGesperrt = istSystemGesperrt;
        Logger.getLogger(this.getClass()).debug("System gesperrt geaendert nach "+istSystemGesperrt);
        // TODO hier aenderung an Config uebergeben --BTheel
    }

    /**
     * @return the systemsperrungFehlermeldung
     */
    public String getMeldungSystemGesperrt() {
        return meldungSystemGesperrt;
    }

    /**
     * @param systemsperrungFehlermeldung
     *            the systemsperrungFehlermeldung to set
     */
    public void setMeldungSystemGesperrt(String meldungSystemGesperrt) {
        this.meldungSystemGesperrt = meldungSystemGesperrt;
        // TODO hier aenderung an Config uebergeben --BTheel
    }
}// DispatcherServlet
