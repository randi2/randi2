package de.randi2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.commons.lang.StringEscapeUtils;

import de.randi2.controller.BenutzerServlet;
import de.randi2.model.fachklassen.Rolle;
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
 * @author BTheel [BTheel@stud.hs-heilbronn.de]
 * 
 */
@SuppressWarnings("serial")
public class DispatcherServlet extends javax.servlet.http.HttpServlet {
    // TODO alle Parameter auf Enums umstellen --BTheel
    // TODO LogLayout beim Sperren?--Btheel
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
        // if (istSystemGesperrt) {
        /*
         * Laed Mitteilung immer aus der Config, wird im Formular zur
         * Systemperrung als Vorbelegung angezeigt --Btheel
         */
        Logger.getLogger(this.getClass()).debug(
                "Lade Mitteilung (System gesperrt) aus Config");
        meldungSystemGesperrt = Config
                .getProperty(Felder.SYSTEMSPERRUNG_FEHLERMELDUNG);
        // }
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
        AKTION_SYSTEM_SPERREN,

        /**
         * Aufforderung, den Benutzer aus dem System abzumelden
         */
        AKTION_LOGOUT,
        
        /**
         * Aufforderung, einen Admin mit den gesendeten Daten anzulegen
         */
        AKTION_ADMIN_ANLEGEN
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
     * <source>index.jsp</source> aufgerufen.
     * </p>
     * <p>
     * Es ist erforderlich, das fuer einen gueltigen Aufruf sowohl eine
     * anfrage_id gesetzt als auch der Benutzer eingeloggt ist.
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

        if (isBenutzerAngemeldet(request) && id != null) {
            // wurde eine Id mitgesendet _und_ der benutzer ist angemeldet
            Logger.getLogger(this.getClass()).debug("[GET]anfrage_id: " + id);
            if (id.equals(DispatcherServlet.anfrage_id.JSP_SYSTEM_SPERREN
                    .name())) {
                weiterleitungSystemSperrung(request, response);
            } else if (id.equals(anfrage_id.AKTION_LOGOUT.name())) {
                Logger.getLogger(this.getClass()).fatal("Benutzer ausloggen");
                loggeBenutzerAus(request, response);
                return;
            }
        } else { /*
                     * keine anfrage_id empfangen oder der benutzer ist nicht
                     * angemeldet -> Weiterleitung auf den entsprechenden Index
                     */
            weiterleitungAufIndex(request, response);
        }

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

        if (id == null) {
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
            boolean bool = ((BenutzerkontoBean)request.getSession().getAttribute("aBenutzer")).getRolle()==Rolle.getSysop();
            // TODO nach Rolle des Benuzters checken! --BTheel
            if (isBenutzerAngemeldet(request)) {
                this.setSystemGesperrt(false);
                Logger.getLogger(this.getClass()).debug(
                        "Schalte System wieder frei");
                LogAktion a = new LogAktion("System wurde entsperrt",
                        (BenutzerkontoBean) request.getSession().getAttribute(
                                "aBenutzer"));
                Logger.getLogger(LogLayout.LOGIN_LOGOUT).info(a);
                request.getRequestDispatcher("/system_sperren.jsp").forward(
                        request, response);// TODO auf weiterleitung umlegen
                return;
            }
            Logger.getLogger(this.getClass()).warn(
                    "Benutzer war nicht angemeldet beim System entsperren!");
            return;
        } else if (id.equals(DispatcherServlet.anfrage_id.AKTION_SYSTEM_SPERREN
                .name())) {
            if (isBenutzerAngemeldet(request)) {
                this.setSystemGesperrt(true);
                
                
                String meldung = StringEscapeUtils.escapeHtml((String) request.getParameter(requestParameter.MITTEILUNG_SYSTEM_GESPERRT.name()));
                this.setMeldungSystemGesperrt(meldung);

                Logger.getLogger(this.getClass()).debug(
                        "Sperre System. Grund: '" + getMeldungSystemGesperrt()
                                + "'");
                LogAktion a = new LogAktion("System wurde gesperrt, Grund: "
                        + getMeldungSystemGesperrt(),
                        (BenutzerkontoBean) request.getSession().getAttribute(
                                "aBenutzer"));
                Logger.getLogger(LogLayout.LOGIN_LOGOUT).info(a);
                request.getRequestDispatcher("/system_sperren.jsp").forward(
                        request, response);// TODO auf weiterleitung umlegen
                return;
            }
            Logger.getLogger(this.getClass()).warn(
                    "Benutzer war nicht angemeldet beim Systemsperren!");
            return;
        } else if (id.equals(DispatcherServlet.anfrage_id.JSP_SYSTEM_SPERREN
                .name())) {
            weiterleitungSystemSperrung(request, response);
            return;
        }else if(id.equals(anfrage_id.AKTION_ADMIN_ANLEGEN.name())){
            Logger.getLogger(this.getClass()).debug("Leite Anfrage an BenutzerServlet weiter");
            request.setAttribute("anfrage_id", BenutzerServlet.anfrage_id.AKTION_BENUTZER_ANLEGEN.name());
            request.getRequestDispatcher("BenutzerServlet").forward(request, response);
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
                    "Kein Block in POST fuer die ID '" + id + "' gefunden");
            // TODO Hier muss noch entschieden werden,was passiert
        }
    }// doPost

    /**
     * 
     * Prueft, ob die ubermittelte SessionID noch gueltig ist und ob
     * an der Session ein Benutzer angehaengt ist.<br>
     * Ist dies der Fall, so ist der Benutzer im System angemeldet.
     * 
     * @param request
     * @return <code>true</code>, wenn der Benutzer asngemeldet ist,
     *         anderenfalls <code>false</code>.
     */
    private boolean isBenutzerAngemeldet(HttpServletRequest request) {
        // TODO Auslagerung nach BenutzerServlet --Btheel
        Logger.getLogger(this.getClass()).debug(
                "DispatcherServlet.isBenutzerAngemeldet()");
        boolean isSessionGueltig = request.isRequestedSessionIdValid();
        Logger.getLogger(this.getClass()).debug(
                "Pruefe: Session noch gueltg? " + isSessionGueltig);

        boolean isKontoangebunden = ((request.getSession()
                .getAttribute("aBenutzer")) != null);
        Logger.getLogger(this.getClass()).debug(
                "Pruefe: Benutzerkonto an Session gebunden? "
                        + isKontoangebunden);
        return (isSessionGueltig & isKontoangebunden);//
    }

    /**
     * Loggt den Benutzer aus dem System aus und ruft anschlieszend die Methode
     * {@link #weiterleitungAufIndex(HttpServletRequest, HttpServletResponse)}
     * auf
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void loggeBenutzerAus(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // TODO Auslagerung nach BenutzerServlet --Btheel
        Logger.getLogger(this.getClass()).debug(
                "DispatcherServlet.loggeBenutzerAus()");
        LogAktion a = new LogAktion("Benutzer hat sich ausgeloggt",
                (BenutzerkontoBean) request.getSession().getAttribute(
                        "aBenutzer"));
        Logger.getLogger(LogLayout.LOGIN_LOGOUT).info(a);
        request.getSession().invalidate(); // Alte session zerstoeren
        request.getSession(); // Neue session eroeffnetn
        weiterleitungAufIndex(request, response);// Weiterleitung
    }

    /**
     * Leitet die Anfrage auf den korrekten Index weiter<br>
     * Ist das System gesperrt, so wird die Datei 'index_gesperrt.jsp'
     * aufgerufen, anderenfalls die Datei 'index.jsp'
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void weiterleitungAufIndex(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        if (istSystemGesperrt) {// System gesperrt
            Logger
                    .getLogger(this.getClass())
                    .debug(
                            "System gesperrt, leite nach 'index_gesperrt.jsp' um (korrekter Ablauf) ");
            request.setAttribute(MITTEILUNG_SYSTEM_GESPERRT,
                    meldungSystemGesperrt);
            request.getRequestDispatcher("index_gesperrt.jsp").forward(request,
                    response);
            return;
        } else {// System offen
            Logger
                    .getLogger(this.getClass())
                    .debug(
                            "System offen, leite nach 'index.jsp' um' (korrekter Ablauf)");
            request.getRequestDispatcher("index.jsp")
                    .forward(request, response);
            return;
        }
    }

    /**
     * Leitet die Anfrage auf die Seite '/system_sperren_main.jsp' weiter und
     * bindet die dort benoetigten Werte an den Request.
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void weiterleitungSystemSperrung(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(requestParameter.IST_SYSTEM_GESPERRT.toString(),
                istSystemGesperrt);
        request.setAttribute(requestParameter.MITTEILUNG_SYSTEM_GESPERRT
                .toString(), meldungSystemGesperrt);
        request.getRequestDispatcher("/system_sperren_main.jsp").forward(
                request, response);
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
        Logger.getLogger(this.getClass()).debug(
                "System gesperrt geaendert nach " + istSystemGesperrt);
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
