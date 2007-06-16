package de.randi2.controller;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.StudieException;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.utility.Jsp;

/**
 * Diese Klasse repraesentiert das STUDIESERVLET, welches Aktionen an die
 * Studie-Fachklasse, die JSPs und an den DISPATCHER weiterleitet.
 * 
 * @version $Id$
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * 
 */
public class StudieServlet extends javax.servlet.http.HttpServlet {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 7L;

	/**
	 * Die Anfrage_id zur Verwendung im Studie Servlet
	 */
	public enum anfrage_id {

		/**
		 * Eine Studie soll vom Benutzer ausgewaehlt werden.
		 */
		STUDIE_AUSWAEHLEN,

		/**
		 * Anlegen einer neuen Studie
		 */
		AKTION_STUDIEAUSWAEHLEN_NEUESTUDIE

	}

	/**
	 * Enhaelt die Parameternamen, die in dem Request gesetzt werden koennen
	 * 
	 */
	public static enum requestParameter {

		/**
		 * Liste der gefundenen Studien
		 */
		LISTE_DER_STUDIEN("listeStudien");

		/**
		 * String Version des Parameters
		 */
		private String parameter = null;

		/**
		 * Setzt die String Repraesentation des Parameters
		 * 
		 * @param parameter
		 *            String Repraesentation des Parameters
		 */
		private requestParameter(String parameter) {
			this.parameter = parameter;
		}

		/**
		 * Liefert die String Repraesentation des Parameters
		 * 
		 * @return String Repraesentation des Parameters
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return this.parameter;

		}
	}

	/**
	 * Konstruktor.
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public StudieServlet() {
		super();
	}

	/**
	 * Diese Methode nimmt HTTP-POST-Request gemaess HTTP-Servlet Definition
	 * entgegen. Hier werden Anfragen verarbeitet, die Zentren betreffen.
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
		String id = (String) request
				.getParameter(DispatcherServlet.requestParameter.ANFRAGE_Id
						.name());
		String idAttribute = (String) request
				.getAttribute(DispatcherServlet.requestParameter.ANFRAGE_Id
						.name());
		if (idAttribute != null) {
			id = idAttribute;
		}
		Logger.getLogger(this.getClass()).debug(id);

		if (id.equals(anfrage_id.STUDIE_AUSWAEHLEN.name())) {
			// Die studie_auswaehlen.jsp soll angezeigt werden.
			studieAuswaehlen(request, response);
		} else if (id.equals(anfrage_id.AKTION_STUDIEAUSWAEHLEN_NEUESTUDIE
				.name())) {

			request.getRequestDispatcher(Jsp.STUDIE_ANLEGEN).forward(request,
					response);

		}
	}

	/**
	 * Diese Methode kuemmert sich um die Logik, die mit dem Prozess "Studie
	 * auswaehlen" verbunden ist. Je nach der Rolle des eingeloggten Benutzers,
	 * werden entsprechende Studien geholt.
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void studieAuswaehlen(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		BenutzerkontoBean aBenutzer = (BenutzerkontoBean) request.getSession()
				.getAttribute("aBenutzer");
		Rolle aRolle = aBenutzer.getRolle();

		StudieBean leeresObjekt = new StudieBean();
		leeresObjekt.setFilter(true);
		
		Vector<StudieBean> listeStudien = null;

		if (aRolle == Rolle.getStudienarzt()) {
			// der eingeloggte Benutzer ist ein Studienarzt
			 listeStudien = DatenbankFactory
					.getAktuelleDBInstanz().suchenMitgliederObjekte(
							aBenutzer.getZentrum(), leeresObjekt);
			request.setAttribute(requestParameter.LISTE_DER_STUDIEN.name(),
					listeStudien);
			request.getRequestDispatcher(Jsp.STUDIE_AUSWAEHLEN).forward(
					request, response);
		} else if (aRolle == Rolle.getStatistiker()) {
			// der eingeloggte Benutzer ist ein Statistiker - hier muss keine
			// Auswahl erfolgen, wird zu seiner Studie geleitet
		} else if(aRolle == Rolle.getStudienleiter()){
			// der eingeloggte Benutzer ist ein Studienleiter
			leeresObjekt.setBenutzerkonto(aBenutzer);
			listeStudien = DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(leeresObjekt);
			request.setAttribute(requestParameter.LISTE_DER_STUDIEN.name(),
					listeStudien);
			request.getRequestDispatcher(Jsp.STUDIE_AUSWAEHLEN).forward(
					request, response);
			
		}
	}
}
