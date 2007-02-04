package de.randi2.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import de.randi2.model.fachklassen.*;
import de.randi2.model.fachklassen.beans.*;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * Diese Klasse repraesentiert das ZENTRUMSERVLET, welches Aktionen an die
 * Zentrum-Fachklasse und an den DISPATCHER weiterleitet.
 * 
 * @version $Id: BenutzerServlet.java 1162 2007-02-01 21:35:46Z afreudli $
 * @author Andreas Freudling <afreudling@hs-heilbronn.de>
 * 
 */
 public class ZentrumServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public ZentrumServlet() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=(String)request.getParameter("anfrage_id");
		String idAttribute=(String)request.getAttribute("anfrage_id");
		if(idAttribute!=null)
		{
			id=idAttribute;
		}
		Logger.getLogger(this.getClass()).debug(id);
		
//		 Benutzer registrieren
		// Schritt 2.1
		if (id.equals("CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_ZWEI")) {
			//Nach allen vorhandenen Zentren suchen
			Vector<ZentrumBean> gZentrum=Zentrum.suchenZentrum(Zentrum.NULL_ZENTRUM);
			request.setAttribute("listeZentren",gZentrum);			
			//Schritt 2.1.3
			request.getRequestDispatcher("/benutzer_anlegen_zwei.jsp").forward(request,response);
		}
		//Schritt 3.1: ZENTRUMAUSWAHL: Filterung
//		Schritt 3.2 ZENTRUMAUSWAHL->BENUTZERDATEN_EINGEBEN
		else if (id.equals("CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_DREI"))
		{
			//Filterung
			if(((String)request.getParameter("Filtern"))!=null)
			{
				Vector<ZentrumBean> gZentrum=null;
				if(((String)request.getParameter("name_institution"))!=""&&((String)request.getParameter("name_abteilung"))!="")
				{
					ZentrumBean sZentrum= new ZentrumBean();
					sZentrum.setInstitution(request.getParameter("name_institution"));
					sZentrum.setAbteilung(request.getParameter("name_abteilung"));
					sZentrum.setFilter(true);
					gZentrum=Zentrum.suchenZentrum(sZentrum);
				}
				else
				{
					gZentrum=Zentrum.suchenZentrum(Zentrum.NULL_ZENTRUM);
				}

			
			request.setAttribute("listeZentren",gZentrum);
			request.getRequestDispatcher("/benutzer_anlegen_zwei.jsp").forward(request, response);
			}
			else
			{
				Vector<ZentrumBean> gZentrum=Zentrum.suchenZentrum(Zentrum.NULL_ZENTRUM);
			Iterator<ZentrumBean> itgZentrum=gZentrum.iterator();
			while(itgZentrum.hasNext())
			{
				ZentrumBean aZentrumBean=itgZentrum.next();
				String suche="bestaetigen"+aZentrumBean.getId();
				if(request.getParameter(suche)!=null)
				{
					Zentrum aZentrum=new Zentrum(aZentrumBean);
					//Zentrum Passwort richtig
					if(aZentrum.pruefenPasswort(request.getParameter("zentrum_passwort"+aZentrumBean.getId())))
					{
						request.getRequestDispatcher("/benutzer_anlegen_drei.jsp").forward(request, response);
					}
					//Zentrum Passwort falsch
					else
					{
						request.setAttribute("listeZentren",gZentrum);
						request.setAttribute("fehlernachricht", "Falsches Zentrumpasswort");
						request.getRequestDispatcher("/benutzer_anlegen_zwei.jsp").forward(request, response);
					}
				}
			}
			}
			
			//keine Filterung
			
		
		}
		// Schritt 2:
		else if (id.equals("CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_DREI")) {
			request.setAttribute("anfrage_id","CLASS_BENUTZERSERVLET_BENUTZER_REGISTRIEREN_DREI");
			// Hier noch jede Menge Logik
			request.getRequestDispatcher("DispatcherServlet").forward(request,response);

		}
		// Schritt 3:
		else if (id.equals("CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_VIER")) {
			request.setAttribute("anfrage_id","CLASS_BENUTZERSERVLET_BENUTZER_REGISTRIEREN_VIER");
			// Hier noch jede Menge Logik
			request.getRequestDispatcher("DispatcherServlet").forward(request,response);

		}
	}   	  	    
}