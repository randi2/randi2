package de.randi2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import de.randi2.utility.Config;
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

    /**
     * 
     */
    private boolean istSystemGesperrt = true;

    private static String meldungSystemGesperrt = "Meldung des System ist gesperrt";

    /**
     * 
     */
    public static final String FEHLERNACHRICHT = "fehlernachricht";

    public static final String IST_SYSTEM_GESPERRT = "System gesperrt";

    /**
     * 
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
        if (istSystemGesperrt) {
            Logger.getLogger(this.getClass()).debug(
                    "System gesperrt, lade Mitteilung aus Config");
            meldungSystemGesperrt = Config
                    .getProperty(Felder.SYSTEMSPERRUNG_FEHLERMELDUNG);
        }
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
        JSP_BENUTZER_ANLEGEN_DREI_BENUTZER_REGISTRIEREN_VIER
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
            Logger.getLogger(this.getClass()).debug("anfrage_id: " + id);
            // logout ( FRAGE Logout wirklich an dieser Stelle?? oder in BenutzerServelet)
            if (id.equals(DispatcherServlet.anfrage_id.JSP_HEADER_LOGOUT)) {
                request.getSession().invalidate();
                request.getRequestDispatcher("index.jsp").forward(request,
                        response);
            }
            return;
        } else {// keine anfrage_id empfangen
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

    private void weiterleitungBenutzerAnmelden(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        Logger.getLogger(this.getClass()).debug(
                "DispatcherServlet.weiterleitungBenutzerAnmelden()");
        request.setAttribute("anfrage_id", "CLASS_DISPATCHERSERVLET_LOGIN1");
        request.setAttribute(IST_SYSTEM_GESPERRT, istSystemGesperrt);
        if (istSystemGesperrt){
            //Request verliert Attr. deshalb neu setzten
            request.setAttribute(MITTEILUNG_SYSTEM_GESPERRT, meldungSystemGesperrt);
            
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
        
        Logger.getLogger(this.getClass()).debug("anfrage_id: "+id);
        
        if (idAttribute != null) {
            /* XXX Frickelei unnoetig, da wenn Param null auch Attr  null ist
             * abfrage des Attr. ausreichend --BTheel
             */
            id = idAttribute;
        }else{ 
            /* ist keine ID gesetzt, so wird auf doGet umgeleitet
             * Weitere Logik dort --Btheel
             */
            doGet(request, response);
            Logger.getLogger(this.getClass()).debug("Anfrage-Id == null, Anfrage nach doGet umleiten");
        }

        
        // WEITERLEITUNGEN FUER BENUTZERSERVLET
        // [start]
        // Login
        if (id.equals(DispatcherServlet.anfrage_id.JSP_INDEX_LOGIN.name())) {
            Logger.getLogger(this.getClass()).debug(
                    "ID '" + id + "' korrekt erkannt");

            weiterleitungBenutzerAnmelden(request, response);
            return;
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

        }
        // [end]
        // WEITERLEITUNGEN FUER ZENTRUMSERVLET
        // [start]
        // [end]

        // SONSTIGE WEITERLEITUNGEN
        // Schwerer Fehler: Falscher Request
        else {
            System.out.println("Scheiße");
            // TODO Hier muss noch entschieden werden,was passiert
        }
    }// doPost

    /**
     * @return the istSystemGesperrt
     */
    public boolean isIstSystemGesperrt() {
        return istSystemGesperrt;
    }

    /**
     * @param istSystemGesperrt
     *            the istSystemGesperrt to set
     */
    public void setIstSystemGesperrt(boolean istSystemGesperrt) {
        this.istSystemGesperrt = istSystemGesperrt;
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
    public void setMeldungSystemGesperrt(String systemsperrungFehlermeldung) {
        meldungSystemGesperrt = systemsperrungFehlermeldung;
    }
}// DispatcherServlet
