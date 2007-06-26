package de.randi2.controller;

import java.io.IOException;
import java.util.GregorianCalendar;
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
import de.randi2.model.fachklassen.beans.StudienarmBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.utility.Jsp;
import de.randi2.utility.Parameter;

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
	 * Meldung, wenn der Aenderungsvorgang einer Studie erfolgreich war.
	 */
	private static final String AENDERUNG_STUDIE_ERFOLGREICH = "Die Studie wurde erfolgreich geändert.";

	/**
	 * Meldung, wenn der Status erfolgreich geaendert wurde.
	 */
	public static final String STATUS_GEAENDERT = "Der Status wurde erfolgreich geaendert!";

	/**
	 * Die Anfrage_id zur Verwendung im Studie Servlet
	 */
	public enum anfrage_id {

		/**
		 * Eine Studie soll vom Benutzer ausgewaehlt werden.
		 */
		AKTION_STUDIE_AUSWAEHLEN,

		/**
		 * "Anlegen einer neuen Studie" auswaehlen
		 */
		AKTION_STUDIE_AUSWAEHLEN_NEUESTUDIE,

		/**
		 * Filtern der Studien.
		 */
		JSP_STUDIE_AUSWAEHLEN_FILTERN,

		/**
		 * Die Studie wurde auf der Seite studie_auswaehlen.jsp ausgewaehlt
		 */
		JSP_STUDIE_AUSWAEHLEN,

		/**
		 * Wenn eine Studie ausgewaehlt wurde und angezeigt werden soll
		 */
		AKTION_STUDIE_AUSGEWAEHLT,

		/**
		 * Eine Studie soll geaendert werden.
		 */
		JSP_STATUS_AENDERN,
		/**
		 * Status der Studie aendern.
		 */
		AKTION_STATUS_AENDERN,

		/**
		 * studie_aendern.jsp wurde gewaehlt.
		 */
		JSP_STUDIE_AENDERN,

		/**
		 * zentrum_anzeigen.jsp
		 */
		JSP_ZENTRUM_ANZEIGEN,

		/**
		 * Eine Studie wird pausiert.
		 */
		AKTION_STUDIE_PAUSIEREN,

		/**
		 * Eine pausierte Studie wird fortgesetzt.
		 */
		AKTION_STUDIE_FORTSETZEN,

		/**
		 * Aendert einer bereits vorhandenen Studie.
		 */
		AKTION_STUDIE_AENDERN,

		/**
		 * Neue Studie hinzufuegen
		 */
		AKTION_STUDIE_ANLEGEN
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
	 * Diese Methode nimmt HTTP-GET-Request gemaess HTTP-Servlet Definition
	 * entgegen. Die Anfragen werden direkt an doPost() weitergeleitet.
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
		this.doPost(request, response);

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
			if (id.equals(anfrage_id.AKTION_STUDIE_AUSWAEHLEN.toString())) {
				// Die studie_auswaehlen.jsp soll angezeigt werden.
				studieAuswaehlen(request, response);
			} else if (id.equals(anfrage_id.AKTION_STUDIE_AUSGEWAEHLT
					.toString())) {
				// Benutzer hat eine Studie ausgewaehlt
				try {
					// Erstmal alle vorhandenen Studien suchen
					StudieBean aStudie = new StudieBean();
					aStudie.setFilter(true);
					Iterator<StudieBean> iterator = Studie.sucheStudie(aStudie)
							.iterator();
					while (iterator.hasNext()) {
						aStudie = iterator.next();
						String suche = "aStudieId" + aStudie.getId();
						if (request.getParameter(suche) != null) {
							// Ausgewaehlte Studie wird an die Session gebunden
							Logger.getLogger(this.getClass()).debug(
									"Binde Studie an Session");
							request
									.getSession()
									.setAttribute(
											DispatcherServlet.sessionParameter.AKTUELLE_STUDIE
													.toString(), aStudie);
							request.setAttribute(
									DispatcherServlet.requestParameter.TITEL
											.toString(), "Studie ansehen");
							request.getRequestDispatcher(Jsp.STUDIE_ANSEHEN)
									.forward(request, response);
							break;
						}
					}
				} catch (DatenbankExceptions e) {
					// Fehler zurück!
					request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
							.getMessage());
					request.getRequestDispatcher("/studie_auswaehlen.jsp")
							.forward(request, response);
				}
			} else if (id.equals(anfrage_id.AKTION_STUDIE_AUSWAEHLEN_NEUESTUDIE
					.name())) {
				// Formular fuer neue Studie aufrufen
				request.getRequestDispatcher(Jsp.STUDIE_ANLEGEN).forward(
						request, response);
			} else if (id.equals(anfrage_id.AKTION_STUDIE_ANLEGEN.name())) {
				// Neue Studie anlegen
				// StudieBean aStudie = new
				// StudieBean(NullKonstanten.DUMMY_ID,);

				this.classDispatcherservletStudieAnlegen(request, response);

				return;

			} else if (id.equals(anfrage_id.AKTION_STUDIE_AENDERN.name())) {
				// Studie soll geaendert werden
				request.getRequestDispatcher(Jsp.STUDIE_AENDERN).forward(
						request, response);
			} else if (id.equals(anfrage_id.AKTION_STATUS_AENDERN.name())) {
				// Status aendern
				request.getRequestDispatcher(Jsp.STUDIE_PAUSIEREN_EINS)
						.forward(request, response);

			} else if (id.equals(anfrage_id.AKTION_STUDIE_FORTSETZEN.name())) {
				// Status aendern
				studieStatus(request, response, Studie.Status.AKTIV);
			} else if (id.equals(anfrage_id.AKTION_STUDIE_PAUSIEREN.name())) {
				// Status aendern
				studieStatus(request, response, Studie.Status.PAUSE);
			} else if (id.equals(anfrage_id.JSP_ZENTRUM_ANZEIGEN.name())) {
				request.setAttribute("zugehoerigeZentren", this
						.getZugehoerigeZentren(request, response));
				request.setAttribute("nichtZugehoerigeZentren", this
						.getNichtZugehoerigeZentren(request, response));
				if (((String) request.getParameter("Filtern")) != null) {
					request.getRequestDispatcher("ZentrumServlet").forward(
							request, response);
				} else {
					request.getRequestDispatcher(Jsp.ZENTRUM_ANZEIGEN).forward(
							request, response);
				}
			}

		} else if (id != null) {
			Logger.getLogger(this.getClass()).debug(id);
			if (id.equals(anfrage_id.JSP_STUDIE_AENDERN.name())) {
				// studieAendern.jsp soll angezeigt werden
				studieAendern(request, response);
			}

		} else {
			// TODO an dieser Stelle würde ich einfach auf index.jsp
			// weiterleiten; gibt's andere Vorschläge (lplotni 17. Jun)
			// request.getRequestDispatcher("DispatcherServlet").forward(request,
			// response);
			System.out.println("Die drei Fragezeichen beim Posten");

			
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
				.getAttribute(
						DispatcherServlet.sessionParameter.A_Benutzer
								.toString());
		Rolle aRolle = aBenutzer.getRolle();

		StudieBean leereStudie = new StudieBean();
		leereStudie.setFilter(true);

		Vector<StudieBean> listeStudien = null;

		if (aRolle == Rolle.getStudienarzt()) {
			// der eingeloggte Benutzer ist ein Studienarzt
			Logger.getLogger(this.getClass()).debug(
					"studieAuswahl - Studienarzt");

			if (request.getParameter(Parameter.filter) != null) {
				try {
					listeStudien = studieFiltern(request
							.getParameter(Parameter.studie.NAME.toString()),
							Studie.Status.parseStatus(request
									.getParameter(Parameter.studie.STATUS
											.toString())), aBenutzer
									.getZentrum());
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
			request.setAttribute(requestParameter.LISTE_DER_STUDIEN.toString(),
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
					sessionParameter.AKTUELLE_STUDIE.toString(), aStudie);
			request.getRequestDispatcher(Jsp.STUDIE_ANSEHEN).forward(request,
					response);
		} else if (aRolle == Rolle.getStudienleiter()) {
			// der eingeloggte Benutzer ist ein Studienleiter
			Logger.getLogger(this.getClass()).debug(
					"studieAuswahl - Studienleiter");
			if (request.getParameter(Parameter.filter) != null) {
				try {
					listeStudien = studieFiltern(request
							.getParameter(Parameter.studie.NAME.toString()),
							Studie.Status.parseStatus(request
									.getParameter(Parameter.studie.STATUS
											.toString())), aBenutzer
									.getZentrum());
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

			request.setAttribute(requestParameter.LISTE_DER_STUDIEN.toString(),
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
	 * @param aZentrum -
	 *            das ZentrumBean von dem eingeloggten Benutzer
	 * @return - Vector mit den gefundenen StudienBeans
	 * @throws DatenbankExceptions
	 *             wenn bei dem Vorgang Fehler in der DB auftraten
	 */
	private Vector<StudieBean> studieFiltern(String name, Studie.Status status,
			ZentrumBean aZentrum) throws DatenbankExceptions {

		Logger.getLogger(this.getClass()).debug("studieFiltern");

		StudieBean gStudie = new StudieBean();
		gStudie.setFilter(true);

		try {
			if (name != null) {
				gStudie.setName(name);
			}
			gStudie.setStatus(status);
			return DatenbankFactory.getAktuelleDBInstanz()
					.suchenMitgliederObjekte(aZentrum, gStudie);

		} catch (StudieException e) {
			// TODO Dem Benutzer muss eine Meldung angezeigt werden (lplotni
			// 17. Jun)
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Diese Methode nimmt HTTP-POST-Request gemaess HTTP-Servlet Definition
	 * entgegen. Hier werden Anfragen verarbeitet, die Zentren betreffen.
	 * 
	 * @param request
	 *            Der Request fuer das Servlet.
	 * @param response
	 *            Der Response Servlet.
	 * @param status
	 *            Status der Studie, der geaendert wird.
	 * @throws IOException
	 *             Falls Fehler in den E/A-Verarbeitung.
	 * @throws ServletException
	 *             Falls Fehler in der HTTP-Verarbeitung auftreten.
	 */
	private void studieStatus(HttpServletRequest request,
			HttpServletResponse response, Studie.Status status)
			throws ServletException, IOException {

		StudieBean aStudie = (StudieBean) request.getSession().getAttribute(
				DispatcherServlet.sessionParameter.AKTUELLE_STUDIE.toString());

		try {
			aStudie.setStatus(status);
			DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(aStudie);
			// Studie erfolgreich pausiert
			// TODO Meldung an den Benutzer
			request.setAttribute(DispatcherServlet.NACHRICHT_OK,
					this.STATUS_GEAENDERT);
			request.getRequestDispatcher("studie_ansehen.jsp").forward(request,
					response);
		} catch (Exception e) {
			request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
					.getMessage());
			request.getRequestDispatcher("studie_ansehen.jsp").forward(request,
					response);
		}

	}

	/**
	 * Realisiert das anlegen einer neuen Studie
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
	private void classDispatcherservletStudieAnlegen(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String aName = request.getParameter(Parameter.studie.NAME.name());
		String aBezeichnung = request
				.getParameter(Parameter.studie.BESCHREIBUNG.name());
		String aStartdatum = request.getParameter(Parameter.studie.STARTDATUM
				.name());
		String aEnddatum = request.getParameter(Parameter.studie.ENDDATUM
				.name());
		String aProtokoll = request
				.getParameter(Parameter.studie.STUDIENPROTOKOLL.name());
		BenutzerkontoBean aStudienleiter = ((BenutzerkontoBean) request
				.getSession().getAttribute(
						DispatcherServlet.sessionParameter.A_Benutzer
								.toString()));
		String aStatistikerAnlegen = request
				.getParameter(Parameter.studie.STATISTIKER_BOOL.name());
		int aAnzahl_Arme = (Integer.parseInt(request
				.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
						.name())));
		int aAnzahl_Strata = (Integer.parseInt(request
				.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
						.name())));
		String aAlgorithmus = request
				.getParameter(Parameter.studie.RANDOMISATIONSALGORITHMUS.name());
		int aBlockgroesse = (Integer.parseInt(request
				.getParameter(Parameter.studie.BLOCKGROESSE.name())));

		StudieBean aStudie = new StudieBean();

		this.weiterleitungBeiFehler("Fehler beim Anlegen!", request, response);
	}

	/**
	 * Weiterleitung der Anfrage bei Fehler
	 * <p>
	 * Leitet den Benutzer auf die entsprechende Indexseite weiter, abhaengig
	 * davon, ob das System gesperrt ist oder nicht.
	 * </p>
	 * <p>
	 * Die uebergebene Fehlermeldung wird an den Request als
	 * {@link DispatcherServlet#FEHLERNACHRICHT} angefuegt.
	 * </p>
	 * 
	 * @param fehlermeldungAnBenutzer
	 *            Fehlermeldung, die dem Benutzer angzeigt wird
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
	private void weiterleitungBeiFehler(String fehlermeldungAnBenutzer,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Fehlermeldung anfuegen
		request.setAttribute(DispatcherServlet.FEHLERNACHRICHT,
				fehlermeldungAnBenutzer);

		request.getRequestDispatcher(Jsp.STUDIE_ANLEGEN).forward(request,
				response);

	}

	/**
	 * Aenderbare Daten einer bereits in der Datenbank bestehenden Studie werden
	 * gesetzt.
	 * 
	 * @param request
	 *            Der request fuer das Servlet.
	 * @param response
	 *            Der Response des Servlets.
	 * @throws IOException
	 *             Falls Fehler in der E/A-Verarbeitung.
	 * @throws ServletException
	 *             Falls Fehler in der HTTP-Verarbeitung.
	 */
	private void studieAendern(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Alle aenderbaren Attribute des request inititalisieren
		String startDatum = request.getParameter((Parameter.studie.STARTDATUM)
				.name());
		String endDatum = request.getParameter((Parameter.studie.ENDDATUM)
				.name());
		String studienarme = request
				.getParameter((Parameter.studie.ARME_STUDIE).name());

		Studie aStudie = (Studie) (request.getSession())
				.getAttribute("aStudie");

		StudieBean aStudieBean = (StudieBean) (request.getSession())
				.getAttribute("aStudie");

		try {
			try {
				Vector<StudienarmBean> studienArme = aStudieBean
						.getStudienarme();
				GregorianCalendar aStartDatum = aStudieBean.getStartDatum();
				GregorianCalendar aEndDatum = aStudieBean.getEndDatum();
				aStudieBean.setStudienZeitraum(aStartDatum, aEndDatum);
				aStudieBean.setBeschreibung(request
						.getParameter((Parameter.studie.BESCHREIBUNG).name()));

				DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(
						aStudieBean);

			} catch (Exception e) {
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
						.getMessage());
			}

			request.setAttribute(DispatcherServlet.NACHRICHT_OK,
					this.AENDERUNG_STUDIE_ERFOLGREICH);
			request.getRequestDispatcher("studie_auswaehlen.jsp").forward(

			request, response);
		} catch (DatenbankExceptions e) {
			request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
					.getMessage());
		}

	}

	/**
	 * Diese Methode erstellt einen Vektor mit den Zentren, die der aktuellen
	 * Studie zugeordnet sind
	 * 
	 * @param request
	 * @param response
	 * @return Vektor der Zentren, die der Studie zugeordnet sind
	 */
	private Vector<ZentrumBean> getZugehoerigeZentren(
			HttpServletRequest request, HttpServletResponse response) {
		StudieBean aSession = (StudieBean) request.getSession().getAttribute(
				DispatcherServlet.sessionParameter.AKTUELLE_STUDIE.toString());
		Vector<ZentrumBean> zugehoerigeZentren = null;
		try {
			zugehoerigeZentren = aSession.getZentren();
		} catch (DatenbankExceptions e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return zugehoerigeZentren;

	}

	/**
	 * Diese Methode erstellt einen Vektor mit den Zentren, die der aktuellen
	 * Studie nicht zugeordnet sind
	 * 
	 * @param request
	 * @param response
	 * @return Vektor der Zentren, die der Studie nicht zugeordnet sind
	 */
	private Vector<ZentrumBean> getNichtZugehoerigeZentren(
			HttpServletRequest request, HttpServletResponse response) {
		ZentrumBean zb = new ZentrumBean();
		zb.setIstAktiviert(true);
		zb.setFilter(true);

		Vector<ZentrumBean> zentrenliste = null;
		try {
			zentrenliste = Zentrum.suchenZentrum(zb);
		} catch (DatenbankExceptions e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vector<ZentrumBean> zugehoerigeZentren = (Vector<ZentrumBean>) request
				.getAttribute("zugehoerigeZentren");

		// Vector<ZentrumBean> zugehoerigeZentren =
		// getZugehoerigeZentren(request, response);
		for (int y = 0; y < zentrenliste.size(); y++) {
			for (int x = 0; x < zugehoerigeZentren.size(); x++) {
				if (zentrenliste.elementAt(y).equals(
						zugehoerigeZentren.elementAt(x))) {
					zentrenliste.removeElementAt(y);
				}

			}
		}
		return zentrenliste;

	}
}