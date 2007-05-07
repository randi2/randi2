package de.randi2.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * Diese Filterklasse wird jedem Request vorgeschaltet. Aktivieren bzw. deaktivieren erfolgt im Deployment-Descriptor unter filter.
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * @version $Id$
 *
 */
public class FilterExtended implements Filter {

//    /**
//     * Genau Konfiguration des Filters.
//     */
//    private FilterConfig filterConfig;

    /** Initialisiert den Filter und trägt dies ins Log ein.
     * @param filterConfig Filterkonfiguration mit der, der Filter initialisiert wird.
     * @throws ServletException Fehlermeldung siehe ServletException
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
	Logger.getLogger(this.getClass()).info("Filter gestartet.");
	//this.filterConfig = filterConfig;
    }

    /** Der Filter wird beendet/zerstört.
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
	Logger.getLogger(this.getClass()).info("Filter beendet.");
	//this.filterConfig = null;
    }

    /** 
     * Hier werden die genauen Filter spezifiziert die durchgeführt werden.
     * Bisher: HttpServletRequestExtend
     * @param request Beschreibung siehe [at]see
     * @param response Beschreibung siehe [at]see
     * @param chain Beschreibung siehe [at]see
     * @throws IOException Beschreibung siehe [at]see
     * @throws ServletException Beschreibung siehe [at]see
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	chain.doFilter(new HttpServletRequestExtended((HttpServletRequest) request), response);
    }
}
