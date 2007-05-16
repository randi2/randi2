package de.randi2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.AktivierungException;
import de.randi2.model.fachklassen.beans.AktivierungBean;

/**
 * Das Aktivierungservlet wird NUR angesprochen wenn der Benutzer den Aktivierungslink anklickt.
 * Es überprüft ob zu einem Aktivierungslink der zugehörige Eintrag in der Datenbank existiert.
 *
 */
public class AktivierungServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

    /**
	 * Default Serial
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Diese Methode nimmt HTTP-Get-Requests gemaess HTTP-Servlet Definition
     * entgegen. Hier werden Anfragen abgearbeitet, die die Aktivierung des Benutzers  betreffen.
     * 
     * @param request
     *            Der Request fuer das Servlet.
     * @param response
     *            Der Response des Servlets.
     * @throws IOException
     *             Falls Fehler in den E/A-Verarbeitung.
     * @throws ServletException
     *             Falls Fehler in der HTTP-Verarbeitung auftreten.
     * 
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
     *      HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//Url zum Testen http://localhost:8080/RANDI2/Aktivierung?link=aaaaaaaaaaaaaaaaaaaa
	String id = (String) request.getParameter(AktivierungBean.ATTRIBUT_LINK);
	AktivierungBean beanZumSuchen=new AktivierungBean();
	beanZumSuchen.setFilter(true);
	try {
	    beanZumSuchen.setAktivierungsLink(id);
	    beanZumSuchen=DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(beanZumSuchen).firstElement();
	} catch (AktivierungException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	} catch (DatenbankFehlerException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}