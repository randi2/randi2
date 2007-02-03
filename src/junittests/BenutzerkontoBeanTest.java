package junittests;

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
        	aKonto.setBenutzername("(\\w|\\d|[._-]|\\@){0,50}");
        }catch (Exception e) {
            fail("[FEHLER] testSetBenutzernameErlaubteZeichen sollte keine Exception werfen!!");
        }
    }
        
       
    @Test 
    public final void testSetBenutzernameNull() {
         
        try {
            aKonto.setBenutzername(null);            
        } catch (Exception e) {
        	fail("[testSetBenutzernameNull]Benutzename=null.Sollte Exception auslösen");
        }
    }
    
    @Test 
    public final void testSetBenutzernameLeer() {
    	
        try {
            aKonto.setBenutzername("");
        } catch (Exception e) {
        	fail("[testSetBenutzernameLeer]Benutzername leer.Sollte Exception auslösen");
        }
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

    @Test 
    public final void testSetPasswortLaengeFalsch() {
    try {
        aKonto.setPasswort("s");
     }
      catch (Exception e) {
      	 fail("[testSetPasswortLaengeFalsch]zu kurz.Sollte mindestens 2 Zeichen haben. Sollte Exception ausgelösen.");
     }
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
    
    @Test 
    public final void testSetPasswortNull() {
         
        try {
            aKonto.setPasswort(null);            
        } catch (Exception e) {
        	fail("[testSetPasswortNull]Passwort=null.Sollte Exception auslösen");
        }
    }
    
    @Test 
    public final void testSetPasswortLeer() {
    	
        try {
            aKonto.setPasswort("");
        } catch (Exception e) {
        	fail("[testSetPasswortLeer]Passwort leer.Sollte Exception auslösen");
        }
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
		aKonto.isGesperrt();
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


	@Test
	public void testSetRolleNull() {
	 try {
	         rolle.equals("");            
	     } catch (Exception e) {
	       	fail("[testSetRolleNull]Rolle=null.Sollte Exception auslösen");
	     }
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
