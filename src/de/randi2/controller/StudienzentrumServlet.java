package de.randi2.controller;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import de.randi2.controller.StudieServlet.anfrage_id;
import de.randi2.controller.StudieServlet.requestParameter;
import de.randi2.datenbank.DatenbankFactory;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.utility.Jsp;

/**
 * Diese Klasse repraesentiert das STUDIENZENTRUMSERVLET, um
 * Zentren einer Studie zu verwalten.
 * 
 * @version $Id: $
 * @author Thomas Ganszky, Katharina Chruscz
 * 
 */
public class StudienzentrumServlet extends javax.servlet.http.HttpServlet {



	/**
	 * Default Serial
	 */
	private static final long serialVersionUID = 3723410281193995084L;


	/**
	 * Enhaelt die Parameternamen, die in dem Request gesetzt werden koennen
	 */
	public static enum requestParameter {
		// TODO
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
		// TODO
		/*
		 * String id = (String) request.getParameter("anfrage_id"); String
		 * idAttribute = (String) request.getAttribute("anfrage_id"); if
		 * (idAttribute != null) { id = idAttribute; }
		 * Logger.getLogger(this.getClass()).debug(id);
		 * 
		 * if
		 * (request.getAttribute(DispatcherServlet.requestParameter.ANFRAGE_Id
		 * .name()) == anfrage_id.STUDIE_AUSWAEHLEN) { // Die
		 * studie_auswaehlen.jsp soll angezeigt werden. StudieBean leeresObjekt =
		 * new StudieBean(); leeresObjekt.setFilter(true); Vector<StudieBean>
		 * listeStudien = DatenbankFactory
		 * .getAktuelleDBInstanz().suchenObjekt(leeresObjekt);
		 * System.out.println(listeStudien.size());
		 * System.out.println(requestParameter.LISTE_DER_STUDIEN.name());
		 * request.setAttribute(requestParameter.LISTE_DER_STUDIEN.name(),
		 * listeStudien);
		 * request.getRequestDispatcher(Jsp.STUDIE_AUSWAEHLEN).forward(request,
		 * response); }
		 */
	}
	
	
	/**
	 * Konstruktor.
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public StudienzentrumServlet() {
		super();
	}
}
