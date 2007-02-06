package de.randi2.junittests;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.randi2.datenbank.Filter;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.Zentrum;
import de.randi2.model.fachklassen.Rolle.Rollen;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;

/**
 * @author Nadine Zwink <nzwink@stud.hs-heilbronn.de>
 * @version $Id$
 */
public class BenutzerkontoBeanTest extends Filter{

    private BenutzerkontoBean aKonto, bKonto, cKonto;
    private GregorianCalendar ersterLogin, letzterLogin,ersterLoginB, letzterLoginB;
    private Rolle rolle, rolleB;
    private String benutzername, passwort,benutzernameB, passwortB;
	private PersonBean benutzer, ansprechpartner;
	private boolean gesperrt;
	private ZentrumBean zentrum;
	private Rollen name, nameB;
	   
    
    @Before
    public void setUp() throws Exception {
    	PropertyConfigurator.configure("/RANDI2/WebContent/WEB-INF/log4j.lcf");
        aKonto = new BenutzerkontoBean();
    }

    @After
    public void tearDown() throws Exception {
        aKonto=null;
    }

    
    @Test (expected=BenutzerkontoException.class)
    public final void testSetBenutzernameRichtig() throws BenutzerkontoException {       
        aKonto.setBenutzername("administrator");
        aKonto.setBenutzername("studienleiter");
        aKonto.setBenutzername("sa@randi2.de");
        aKonto.setBenutzername("systemoperator");
        aKonto.setBenutzername("statistiker");
    }
    
    
    @Test (expected=BenutzerkontoException.class)
    public final void testSetBenutzernameErlaubteZeichen() throws BenutzerkontoException {        
        	aKonto.setBenutzername("hanswursthausen");
    }
        
       
    @Test (expected=BenutzerkontoException.class)
    public final void testSetBenutzernameNull() throws BenutzerkontoException {            	
			aKonto.setBenutzername(null);	       
    }
    
    
    @Test (expected=BenutzerkontoException.class)
    public final void testSetBenutzernameLeer() throws BenutzerkontoException {    	
			aKonto.setBenutzername("");
    }
    
    
    @Test (expected=BenutzerkontoException.class)
    public final void testSetBenutzernameLaenge() throws BenutzerkontoException {        
          aKonto.setBenutzername("aaaaaaaaaaaaaaaaa"); //mehr als 14 Zeichen
          aKonto.setBenutzername("aaa"); //weniger als 4 Zeichen
    }

    
    @Test (expected=BenutzerkontoException.class)
    public final void testSetPasswortLaengeFalsch() throws BenutzerkontoException {
			aKonto.setPasswort("s");
    }
    
    
    @Test (expected=BenutzerkontoException.class)
    public final void testSetPasswortLaengeRichtig() throws BenutzerkontoException {    
        aKonto.setPasswort("sss");
    }
    
    
    @Test 
    public final void testSetPasswortErlaubteZeichen() {   
	    try {
	        aKonto.setPasswort(".*[A-Za-z].*");
	        aKonto.setPasswort(".*[0-9].*");
	     } catch (Exception e) {
	      	 fail("[FEHLER]testSetPasswortErlaubteZeichen()sollte keine Exception auslösen");
	     }     
    }
    
    @Test (expected=BenutzerkontoException.class)
    public final void testSetPasswortRichtig() throws BenutzerkontoException {   
	    	aKonto.setPasswort("aa");
	        aKonto.setPasswort("aaaa");  
    }
    
    
    @Test (expected=BenutzerkontoException.class)
    public final void testSetPasswortNull() throws BenutzerkontoException {
			aKonto.setPasswort(null);      
    }
    
    
    @Test (expected=BenutzerkontoException.class)
    public final void testSetPasswortLeer() throws BenutzerkontoException {  
			aKonto.setPasswort("");
    }

    
    @Test (expected=BenutzerkontoException.class)
    public final void testEqualsBenutzerkontoBeanGleich() throws PersonException, BenutzerkontoException {
    	String benutzername= "Hans";
    	String passwort="dddd";
    	name =Rollen.ADMIN;
    	rolle=Rolle.getAdmin();
		PersonBean benutzer=new PersonBean();
		PersonBean ansprechpartner=new PersonBean();
		boolean gesperrt=false;
		ZentrumBean zentrum=new ZentrumBean();
		String day = "1";
		int tagLetzterLogin = Integer.parseInt(day);
		String month ="2"; 
		int monatLetzterLogin = Integer.parseInt(month);
		String year = "2006";
		int jahrLetzterLogin = Integer.parseInt(year);
		letzterLogin = new GregorianCalendar(jahrLetzterLogin, monatLetzterLogin - 1, tagLetzterLogin);
		ersterLogin=new GregorianCalendar(jahrLetzterLogin, monatLetzterLogin - 1, tagLetzterLogin);
		
    	
    	aKonto = new BenutzerkontoBean(benutzername, passwort, rolle,
    			benutzer, ansprechpartner, gesperrt,zentrum, 
    			ersterLogin,letzterLogin);
  
        assertTrue(aKonto.equals(aKonto));
    }
    
    
    @Test (expected=BenutzerkontoException.class)
    public final void testEqualsBenutzerkontoBeanVerschieden() throws PersonException, BenutzerkontoException {
    	String benutzername= "Hans";
    	String passwort="dddd";
    	name =Rollen.ADMIN;
    	rolle=Rolle.getAdmin();
		PersonBean benutzer=new PersonBean();
		PersonBean ansprechpartner=new PersonBean();
		boolean gesperrt=false;
		ZentrumBean zentrum=new ZentrumBean();
		String day = "1";
		int tagLetzterLogin = Integer.parseInt(day);
		String month ="2"; 
		int monatLetzterLogin = Integer.parseInt(month);
		String year = "2006";
		int jahrLetzterLogin = Integer.parseInt(year);
		letzterLogin = new GregorianCalendar(jahrLetzterLogin, monatLetzterLogin - 1, tagLetzterLogin);
		ersterLogin=new GregorianCalendar(jahrLetzterLogin, monatLetzterLogin - 1, tagLetzterLogin);
		
		String benutzernameB= "Emma";
    	String passwortB="ddddccc";
    	nameB =Rollen.ADMIN;
    	rolleB=Rolle.getAdmin();
		PersonBean benutzerB=new PersonBean();
		PersonBean ansprechpartnerB=new PersonBean();
		boolean gesperrtB=false;
		ZentrumBean zentrumB=new ZentrumBean();
		String dayB = "1";
		int tagLetzterLoginB = Integer.parseInt(day);
		String monthB ="3"; 
		int monatLetzterLoginB = Integer.parseInt(month);
		String yearB = "2005";
		int jahrLetzterLoginB = Integer.parseInt(year);
		letzterLoginB = new GregorianCalendar(jahrLetzterLogin, monatLetzterLogin - 1, tagLetzterLogin);
		ersterLoginB=new GregorianCalendar(jahrLetzterLogin, monatLetzterLogin - 1, tagLetzterLogin);
		
		
    	aKonto = new BenutzerkontoBean(benutzername, passwort, rolle,
    			benutzer, ansprechpartner, gesperrt,zentrum, 
    			ersterLogin,letzterLogin);
    	bKonto = new BenutzerkontoBean(benutzernameB, passwortB, rolleB,
    			benutzerB, ansprechpartnerB, gesperrtB,zentrumB, 
    			ersterLoginB,letzterLoginB);
    	    
       assertFalse(aKonto.equals(bKonto));
    }
   
    
    @Test (expected=BenutzerkontoException.class)
    public final void testEqualsBenutzerkontoBeanNull() throws PersonException, BenutzerkontoException{
    	String benutzername= "Hans";
    	String passwort="dddd";
    	name =Rollen.ADMIN;
    	rolle=Rolle.getAdmin();
		PersonBean benutzer=new PersonBean();
		PersonBean ansprechpartner=new PersonBean();
		boolean gesperrt=false;
		ZentrumBean zentrum=new ZentrumBean();
		String day = "1";
		int tagLetzterLogin = Integer.parseInt(day);
		String month ="2"; 
		int monatLetzterLogin = Integer.parseInt(month);
		String year = "2006";
		int jahrLetzterLogin = Integer.parseInt(year);
		letzterLogin = new GregorianCalendar(jahrLetzterLogin, monatLetzterLogin - 1, tagLetzterLogin);
		ersterLogin=new GregorianCalendar(jahrLetzterLogin, monatLetzterLogin - 1, tagLetzterLogin);
		
    	//try {
			aKonto = new BenutzerkontoBean(benutzername, passwort, rolle,
					 benutzer, ansprechpartner, gesperrt,zentrum, 
					 ersterLogin,letzterLogin);
			cKonto = new BenutzerkontoBean("", "", null,null, null, false, null,
	    			 null, null);
	    
			assertFalse(aKonto.equals(cKonto));
			
	/**	} catch (BenutzerkontoException e) {
			e.printStackTrace();
		}*/
    }

    
	@Test
	public void testSetErsterLogin() {
		try {
			String day = "1";
			int tag = Integer.parseInt(day);
			String month ="2"; 
			int monat = Integer.parseInt(month);
			String year = "2006";
			int jahr = Integer.parseInt(year);
			ersterLogin = new GregorianCalendar(jahr, monat - 1, tag);

			assertFalse((new GregorianCalendar(Locale.GERMANY)).before(ersterLogin));
		} catch (Exception e) {
			fail("[testSetErsterLogin]Exception, wenn Zeit des ersten Login in der Zukunft liegt.");
		}
	}

	
	@Test
	public void testIsGesperrt() {
		//wird über testSetGesperrt geprüft
	}

	
	@Test
	public void testSetGesperrt() {
		aKonto.setGesperrt(true);
		assertTrue(aKonto.isGesperrt()==true);
	}


	@Test
	public void testSetLetzterLogin() {
		try {
			String day = "1";
			int tag = Integer.parseInt(day);
			String month ="2"; 
			int monat = Integer.parseInt(month);
			String year = "2006";
			int jahr = Integer.parseInt(year);
			letzterLogin = new GregorianCalendar(jahr, monat - 1, tag);

			assertFalse((new GregorianCalendar(Locale.GERMANY)).before(letzterLogin));
		} catch (Exception e) {
			fail("[testSetLetzterLogin]Exception, wenn Zeit des letzter Login in der Zukunft liegt.");
		}
	}


	@Test (expected=BenutzerkontoException.class)
	public void testSetRolleNull() throws BenutzerkontoException {
	      	aKonto.setRolle(null);
	}
	
	
	@Test
	public void testSetRolle() {		
	 try {		 
		   aKonto.setRolle(rolle.getAdmin());
		   aKonto.setRolle(rolle.getStatistiker());
		   aKonto.setRolle(rolle.getStudienarzt());
		   aKonto.setRolle(rolle.getStudienleiter());
		   aKonto.setRolle(rolle.getSysop());
		 } catch (Exception e) {
	       	fail("[FEHLER]testSetRolle() sollte keine Exception auslösen");
	     }
	}


	@Test
	public void testSetId() {
		long zahl=1;
	 try {
	    	aKonto.setId(zahl);
	     } catch (Exception e) {
	      	 fail("[FEHLER]testSetId() sollte keine Exception auslösen");
	     }  
	  }
	
	
	@Test
	public void testSetZentrum() {
	 try {
	    	aKonto.setZentrum(zentrum);
	     } catch (Exception e) {
	      	 fail("[FEHLER]testSetZentrum() sollte keine Exception auslösen");
	     }  
	  }
}

