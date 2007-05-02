package de.randi2.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;
/**
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * @version $Id$
 *
 */
public final class HttpServletRequestExtended extends
  HttpServletRequestWrapper {
  public HttpServletRequestExtended(HttpServletRequest servletRequest) {
    super(servletRequest);
  }

  public String[] getParameterValues(String parameter) {
    String[] results = super.getParameterValues(parameter);
    if (results==null)
      return null;
    int count = results.length;

    String[] trimResults = new String[count];
    for (int i=0; i<count; i++) {
      trimResults[i] = results[i].trim();
    }
    return trimResults;
  }
  @Override
  public String getParameter(String param){
      Logger.getLogger(this.getClass()).info("Get Parameter wurde benutzt");
      String rueckgabewert=super.getParameter(param);
      if(rueckgabewert==null)
      {
	  return null;
      }
      rueckgabewert.trim();
      if(rueckgabewert.equals(""))
      {
	  rueckgabewert=null;
      }
      return  rueckgabewert;
  }
}
