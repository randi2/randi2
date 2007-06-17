package de.randi2.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import de.randi2.controller.DispatcherServlet.sessionParameter;
import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.StudieException;
import de.randi2.model.exceptions.ZentrumException;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.Studie;
import de.randi2.model.fachklassen.Zentrum;
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
		AKTION_STUDIE_AUSWAEHLEN,

		/**
		 * Anlegen einer neuen Studie
		 */
		AKTION_STUDIEAUSWAEHLEN_NEUESTUDIE,

		/**
		 * Filtern der Studien.
		 */
		JSP_STUDIE_AUSWAEHLEN_FILTERN,
		
		/**
		 * Die Studie wurde auf der Seite studie_auswaehlen.jsp ausgewaehlt
		 */
		JSP_STUDIE_AUSWAEHLEN;

	}

	/**
	 * Enhaelt die Parameternamen, die in dem Request gesetzt werden koennen
	 * 
	 */
	public static enum requestParameter {
		/**
		 * Liste der im System gespeicherten Zentren
		 */
		LISTE_DER_ZENTREN("listeZentren"),
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
			Logger.getLogger(this.getClass()).debug(id);
			if (id.equals(anfrage_id.AKTION_STUDIE_AUSWAEHLEN.name())) {
				// Die studie_auswaehlen.jsp soll angezeigt werden.
				studieAuswaehlen(request, response);
			} else if (id.equals(anfrage_id.AKTION_STUDIEAUSWAEHLEN_NEUESTUDIE
					.name())) {
				// Neue Studie soll angelegt werden
				request.getRequestDispatcher(Jsp.STUDIE_ANLEGEN).forward(
						request, response);

			}
		} else if (id != null) {
			Logger.getLogger(this.getClass()).debug(id);
			if (id.equals(anfrage_id.JSP_STUDIE_AUSWAEHLEN_FILTERN.name())) {
				// Die studie_auswaehlen.jsp wird erneut angezeigt
				studieAuswaehlen(request, response);
			}else if(id.equals(anfrage_id.JSP_STUDIE_AUSWAEHLEN.name())){
				//Benutzer hat eine Studie ausgewaehlt
				try {
					// Erstmal alle vorhandenen Zentren suchen
					StudieBean aStudie = new StudieBean();
					aStudie.setFilter(true);
					Iterator<StudieBean> iterator = Studie.sucheStudie(aStudie).iterator();
					while (iterator.hasNext()) {
						aStudie = iterator.next();
						String suche = "aStudieId" + aStudie.getId();
						if (request.getParameter(suche) != null) {
							//Ausgewaehlte Studie wird an die Session gebunden
							Logger.getLogger(this.getClass()).debug("Binde Studie an Session");
							request.getSession().setAttribute(DispatcherServlet.sessionParameter.AKTUELLE_STUDIE.name(), aStudie);
							request.getRequestDispatcher(Jsp.STUDIE_ANSEHEN).forward(request, response);
							break;
						}
					}
				} catch (DatenbankExceptions e) {
					// Fehler zurück!
					request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
							.getMessage());
					request.getRequestDispatcher("/benutzer_anlegen_zwei.jsp")
							.forward(request, response);
				}
			}
		} else {
			// TODO an dieser Stelle würde ich einfach auf index.jsp
			// weiterleiten; gibt's andere Vorschläge (lplotni 17. Jun)
			// request.getRequestDispatcher("DispatcherServlet").forward(request,
			// response);
			System.out.println("???");
		}
	}

	/**
	 * Diese Methode kuemmert sich um die Logik, die mit dem Prozess "Studie
	 * auswaehlen" verbunden ist. Je nach der Rolle des eingeloggten Benutzers,
	 * werden entsprechende Studien geholt.
	 * 
	 * @param request
	 *            {@link HttpServlet}
	 * @param response
	 *            {@link HttpServlet}
	 * 
	 * @throws IOException
	 *             {@link HttpServlet}
	 * @throws ServletException
	 *             {@link HttpServlet}
	 */
	private void studieAuswaehlen(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		BenutzerkontoBean aBenutzer = (BenutzerkontoBean) request.getSession()
				.getAttribute("aBenutzer");
		Rolle aRolle = aBenutzer.getRolle();

		StudieBean leereStudie = new StudieBean();
		leereStudie.setFilter(true);

		ZentrumBean leeresZentrum = new ZentrumBean();
		leeresZentrum.setFilter(true);
		leeresZentrum.setIstAktiviert(true);

		Vector<StudieBean> listeStudien = null;
		Vector<ZentrumBean> listeZentren = null;

		if (aRolle == Rolle.getStudienarzt()) {
			// der eingeloggte Benutzer ist ein Studienarzt
			Logger.getLogger(this.getClass()).debug(
					"studieAuswahl - Studienarzt");
			listeZentren = Zentrum.suchenZentrum(leeresZentrum);
			request.setAttribute(requestParameter.LISTE_DER_ZENTREN.name(),
					listeZentren);
			if (request.getParameter("filtern") != null) {
				try {
					listeStudien = studieFiltern(request.getParameter("name"),
							Studie.Status.parseStatus(request
									.getParameter("status")), request
									.getParameter("zentrum"));
				} catch (StudieException e) {
					// TODO Dem Benutzer muss eine Meldung angezeigt werden
					// (lplotni
					// 17. Jun)
					e.printStackTrace();
				}
			} else {
				listeStudien = DatenbankFactory.getAktuelleDBInstanz()
						.suchenMitgliederObjekte(aBenutzer.getZentrum(),
								leereStudie);
			}
			request.setAttribute(requestParameter.LISTE_DER_STUDIEN.name(),
					listeStudien);
			request.getRequestDispatcher(Jsp.STUDIE_AUSWAEHLEN).forward(
					request, response);
		} else if (aRolle == Rolle.getStatistiker()) {
			// der eingeloggte Benutzer ist ein Statistiker - hier muss keine
			// Auswahl erfolgen; die Studie, die er ansehen darf, wird aus der
			// DB geholt und an die Session gebunden; studie_ansehen.jsp wird
			// automatisch aufgerufen.
			Logger.getLogger(this.getClass()).debug(
					"studieAuswahl - Statistiker");
			try {
				leereStudie.setBenutzerkonto(aBenutzer);
			} catch (StudieException e) {
				// TODO Dem Benutzer muss eine Meldung angezeigt werden (lplotni
				// 17. Jun)
				e.printStackTrace();
			}
			listeStudien = Studie.sucheStudie(leereStudie);
			StudieBean aStudie = listeStudien.firstElement();
			request.getSession().setAttribute(
					sessionParameter.AKTUELLE_STUDIE.name(), aStudie);
			request.getRequestDispatcher(Jsp.STUDIE_ANSEHEN).forward(request,
					response);
		} else if (aRolle == Rolle.getStudienleiter()) {
			// der eingeloggte Benutzer ist ein Studienleiter
			Logger.getLogger(this.getClass()).debug(
					"studieAuswahl - Studienleiter");
			listeZentren = Zentrum.suchenZentrum(leeresZentrum);
			request.setAttribute(requestParameter.LISTE_DER_ZENTREN.name(),
					listeZentren);
			if (request.getParameter("filtern") != null) {
				try {
					listeStudien = studieFiltern(request.getParameter("name"),
							Studie.Status.parseStatus(request
									.getParameter("status")), request
									.getParameter("zentrum"));
				} catch (StudieException e) {
					// TODO Dem Benutzer muss eine Meldung angezeigt werden
					// (lplotni
					// 17. Jun)
					e.printStackTrace();
				}
			} else {
				try {
					leereStudie.setBenutzerkonto(aBenutzer);
				} catch (StudieException e) {
					// TODO Dem Benutzer muss eine Meldung angezeigt werden
					// (lplotni
					// 17. Jun)
					e.printStackTrace();
				}
				listeStudien = Studie.sucheStudie(leereStudie);
			}

			request.setAttribute(requestParameter.LISTE_DER_STUDIEN.name(),
					listeStudien);
			request.getRequestDispatcher(Jsp.STUDIE_AUSWAEHLEN).forward(
					request, response);

		}
	}

	/**
	 * Diese Methode filtert gemaess den Kriterien die Liste der Studien.
	 * 
	 * @param name -
	 *            Name der gesuchten Studie
	 * @param status -
	 *            Status der gesuchten Studie
	 * @param zentrumString -
	 *            das leitende Zentrum der gesuchten Studie //TODO Hier ensteht
	 *            ein Problem, da wir nirgendswo diese Eigenschaft speichern
	 *            (lplotni 17. Juni)
	 * @return - Vector mit den gefundenen StudienBeans
	 * @throws DatenbankExceptions wenn bei dem Vorgang Fehler in der DB auftraten
	 */
	private Vector<StudieBean> studieFiltern(String name, Studie.Status status,
			String zentrumString) throws DatenbankExceptions {
		Logger.getLogger(this.getClass()).debug("studieFiltern");
		StudieBean gStudie = new StudieBean();
		gStudie.setFilter(true);
		ZentrumBean gZentrum = new ZentrumBean();
		gZentrum.setFilter(true);
		gZentrum.setIstAktiviert(true);

		StringBuffer tempString = new StringBuffer(zentrumString);

		try {
			gZentrum.setInstitution(tempString.toString().substring(0,
					tempString.indexOf(" / ")));
			gZentrum.setAbteilung(tempString.toString().substring(
					tempString.indexOf(" / ") + 3, tempString.length()));
			gZentrum = Zentrum.suchenZentrum(gZentrum).firstElement();
			System.out.println(gZentrum.toString());
			if (name != null) {
				gStudie.setName(name);
			}
			gStudie.setStatus(status);
			return DatenbankFactory.getAktuelleDBInstanz()
					.suchenMitgliederObjekte(gZentrum, gStudie);

		} catch (StudieException e) {
			// TODO Dem Benutzer muss eine Meldung angezeigt werden (lplotni
			// 17. Jun)
			e.printStackTrace();
		} catch (ZentrumException e) {
			// TODO Dem Benutzer muss eine Meldung angezeigt werden (lplotni
			// 17. Jun)
			e.printStackTrace();
		}
		return null;
	}
}
