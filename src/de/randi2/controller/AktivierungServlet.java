package de.randi2.controller;

import java.io.IOException;
import java.util.NoSuchElementException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.Filter;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.AktivierungException;
import de.randi2.model.fachklassen.beans.AktivierungBean;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.utility.Config;
import de.randi2.utility.Jsp;

/**
 * Das Aktivierungservlet wird NUR angesprochen wenn der Benutzer den Aktivierungslink anklickt.
 * Es überprüft ob zu einem Aktivierungslink der zugehörige Eintrag in der Datenbank existiert.
 *@author Andreas Freudling [afreudli@stud.hs-heilbronn.de]
 *@version $Id$
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
		String aktivierung = (String) request.getParameter(Config.getProperty(Config.Felder.RELEASE_AKTIVIERUNG_ATTRIBUT));
		try {

			//Suchen ob Aktivierung vorhanden
			AktivierungBean sAktivierung = new AktivierungBean();
			sAktivierung.setAktivierungsLink(aktivierung);
			sAktivierung.setFilter(true);
			sAktivierung = DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(sAktivierung).firstElement();
			long versanddatumPlusGueltigkeit=sAktivierung.getVersanddatum().getTimeInMillis()+Integer.valueOf(Config.getProperty(Config.Felder.RELEASE_AKTIVIERUNG_GUELTIGKEIT).toString())*60*60*1000;
			long aktuell=System.currentTimeMillis();
			if(versanddatumPlusGueltigkeit<System.currentTimeMillis()){
				request.getRequestDispatcher(Jsp.NACH_AKTIVIERUNGSLINK_FEHLER).forward(request, response);
				
			}
			else{
				//Status Benutzerkonto umsetzen
				BenutzerkontoBean aBenutzerkonto=sAktivierung.getBenutzerkonto();
				aBenutzerkonto.setGesperrt(false);
				aBenutzerkonto.setBenutzerkontoLogging(Filter.getSystemdummy());
				aBenutzerkonto=DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(aBenutzerkonto);
				
				//Aktivierung loeschen
				sAktivierung.setBenutzerkontoLogging(Filter.getSystemdummy());
				DatenbankFactory.getAktuelleDBInstanz().loeschenObjekt(sAktivierung);
				
				request.getRequestDispatcher(Jsp.NACH_AKTIVIERUNGSLINK_OK).forward(request, response);
				
				
				
			}

		} catch (AktivierungException e1) {
			request.getRequestDispatcher(Jsp.NACH_AKTIVIERUNGSLINK_FEHLER).forward(request, response);
			e1.printStackTrace();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			request.getRequestDispatcher(Jsp.NACH_AKTIVIERUNGSLINK_FEHLER).forward(request, response);
		}
		//Aktivierung ist nicht in DB
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
			request.getRequestDispatcher(Jsp.NACH_AKTIVIERUNGSLINK_FEHLER).forward(request, response);
		}
	}
}