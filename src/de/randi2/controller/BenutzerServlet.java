package de.randi2.controller;


import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.randi2.model.exceptions.BenutzerGesperrtException;
import de.randi2.model.exceptions.BenutzerNichtVorhandenException;
import de.randi2.model.exceptions.PasswortFalschException;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.utility.PasswortUtil;

/**
 * Diese Klasse repraesentiert das BENUTZERSERVLET, welches Aktionen an die
 * Benutzerkonto-Fachklasse und an den DISPATCHER weiterleitet.
 * 
 * @version $Id$
 * @author Daniel Haehn <dhaehn@stud.hs-heilbronn.de>
 * @author Lukasz Plotnicki <lplotni@stud.hs-heilbronn.de>
 * 
 */
public class BenutzerServlet extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public BenutzerServlet() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Hier wird je nach Aktion unterschieden, was passiert.
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id=(String)request.getAttribute("anfrage_id");
		if(id.equals("CLASS_DISPATCHERSERVLET_LOGIN1"))
		{
			System.out.println("Benutzerservlet");
			BenutzerkontoBean sBenutzer=new BenutzerkontoBean();
			sBenutzer.setBenutzername((String)request.getParameter("username"));
			sBenutzer.setPasswort(PasswortUtil.getInstance().hashPasswort((String)request.getParameter("password")));
			Vector<BenutzerkontoBean> gBenutzer=Benutzerkonto.suchenBenutzer(sBenutzer);
			if(gBenutzer.size()==1)
			{
				
				if (!gBenutzer.get(0).isGesperrt())
				{	
				request.getSession(true).setAttribute("aBenutzer", gBenutzer.get(0));
				request.setAttribute("anfrage_id", "CLASS_BENUTZERSERVLET_LOGIN_OK");
				}
			}
			else
			{
				request.setAttribute("fehlernachricht","Loginfehler");
				request.setAttribute("anfrage_id", "CLASS_BENUTZERSERVLET_LOGIN_ERROR");
				
			}
			
			//(String)request.getParameter("password");
			//(String)request.getParameter("")
			//BenutzerkontoBean=new BenutzerkontoBean()
			//Benutzerkonto.suchenBenutzer(sBenutzerkonto)
			//request.setParameter("anfrage_id", "CLASS_DISPATCHERSERVLET_LOGIN1");
			request.getRequestDispatcher("DispatcherServlet").forward(request, response);
		}
	}

	/**
	 * Diese Methode liest aus dem Request Benutzername und Passwort aus, sucht
	 * nach diesem Account und ueberprueft das eingegebene Passwort.
	 * 
	 * Wenn der Benutzer ordnungsgemaess authentifiziert wurde, wird das
	 * entsprechende BenutzerkontoBean zurueckgegeben.
	 * 
	 * @param request
	 *            das Request-Objekt
	 * @param response
	 *            das Response-Objekt
	 * @throws BenutzerNichtVorhandenException
	 *             wenn der Benutzer nicht vorhanden ist
	 * @throws BenutzerGesperrtException
	 *             wenn der Benutzer gesperrt ist
	 * @throws PasswortFalschException
	 *             wenn das Passwort nicht korrekt ist
	 * @return das entsprechende BenutzerkontoBean
	 */
	private BenutzerkontoBean einloggenBenutzer(HttpServletRequest request)
			throws BenutzerNichtVorhandenException, BenutzerGesperrtException,
			PasswortFalschException {

		// TODO
		return null;

	}

}