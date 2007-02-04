package de.randi2.junittests;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.randi2.datenbank.Filter;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;

/**
 * @author Nadine Zwink <nzwink@stud.hs-heilbronn.de>

 * @version $Id: BenutzerkontoBeanTest.java 1135 2007-02-03 16:38:05Z nzwink $
 */
public class BenutzerkontoBeanTest extends Filter{

    private BenutzerkontoBean aKonto, zuvergleichendesObjekt;
    private GregorianCalendar ersterLogin, letzterLogin;
    private Rolle rolle;
    private static Rolle STUDIENARZT, ADMIN, STUDIENLEITER, STATISTIKER,SYSOP;
    
    
    @Before
    public void setUp() throws Exception {
        aKonto = new BenutzerkontoBean();
    }

    @After
    public void tearDown() throws Exception {
        aKonto=null;
    }

    @Test 
    public final void testSetBenutzernameRichtig() {
        
        try{
        aKonto.setBenutzername("administrator");
        aKonto.setBenutzername("studienleiter");
        aKonto.setBenutzername("sa@randi2.de");
        aKonto.setBenutzername("systemoperator");
        aKonto.setBenutzername("statistiker");
        
        }catch (Exception e) {
            fail("[FEHLER] testSetBenutzernameRichtig sollte keine Exception werfen!!");
        }
    }
    
    @Test 
    public final void testSetBenutzernameErlaubteZeichen() {
        
        try{
        	aKonto.setBenutzername("hanswursthausen");
        }catch (Exception e) {
            fail("[FEHLER] testSetBenutzernameErlaubteZeichen sollte keine Exception werfen!!");
        }
    }
        
       
    @Test (expected=IllegalArgumentException.class)
    public final void testSetBenutzernameNull() {
         
            aKonto.setBenutzername(null);            

    }
    
    @Test (expected=IllegalArgumentException.class)
    public final void testSetBenutzernameLeer() {
    	
            aKonto.setBenutzername("");

    }
    
    @Test 
    public final void testSetBenutzernameLaenge() {
        
       try {
            aKonto.setBenutzername("aaaaaaaaaaaaaaaaa"); //mehr als 14 Zeichen
            aKonto.setBenutzername("aaa"); //weniger als 4 Zeichen
           
        } catch (Exception e) {
        	 fail("[testSetBenutzernameLaenge]Zeichen liegen zwischen 4-14.Sollte Exception auslösen");
        }
    }

    @Test (expected=IllegalArgumentException.class)
    public final void testSetPasswortLaengeFalsch() {

        aKonto.setPasswort("s");

    }
    
    @Test 
    public final void testSetPasswortLaengeRichtig() {
    try {
        aKonto.setPasswort("sss");
     }
      catch (Exception e) {
      	 fail("[FEHLER]testSetPasswortLaengeRichtig()sollte keine Exception ausgelösen.");
     }
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
    
    @Test 
    public final void testSetPasswortRichtig() {
   
    try {
    	aKonto.setPasswort("aa");
        aKonto.setPasswort("aaaa");
     } catch (Exception e) {
      	 fail("[FEHLER]testSetPasswortRichtig() sollte keine Exception auslösen");
     }    
    }
    
    @Test (expected=IllegalArgumentException.class)
    public final void testSetPasswortNull() {
         

            aKonto.setPasswort(null);            

    }
    
    @Test (expected=IllegalArgumentException.class)
    public final void testSetPasswortLeer() {
  
            aKonto.setPasswort("");

    }

    @Test 
    public final void testEqualsBenutzerkontoBean() {
    	
   /** 	try {
    		    fail("[testEqualsBenutzerKontoBean]Identische Benuetzrkonten");  
    		}
    			
        } catch (Exception e) {
          	 fail("[FEHLER]testEqualsBenutzerKontoBean()");
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


	@Test (expected=IllegalArgumentException.class)
	public void testSetRolleNull() {

	         aKonto.setRolle(null);    

	}
	
	@Test
	public void testSetRolle() {
		
	 try {
		 
		 
		   rolle.getName().equals("ADMIN");
		   rolle.getName().equals("STATISTIKER");
		   rolle.getName().equals("STUDIENLEITER");
		   rolle.getName().equals("STUDIENARZT");
		   rolle.getName().equals("SYSOP");
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
	
}
