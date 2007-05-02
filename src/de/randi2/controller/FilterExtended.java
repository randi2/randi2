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
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * @version $Id$
 *
 */
public class FilterExtended implements Filter {
  private FilterConfig filterConfig;

  public void init(FilterConfig filterConfig) throws
    ServletException {
    Logger.getLogger(this.getClass()).info("Filter gestartet");
    this.filterConfig = filterConfig;
  }

  public void destroy() {
    System.out.println("Filter destroyed");
    this.filterConfig = null;
  }

  public void doFilter(ServletRequest request, ServletResponse response,
    FilterChain chain)
    throws IOException, ServletException {
    chain.doFilter( new HttpServletRequestExtended((HttpServletRequest) request), response);
  }
}
