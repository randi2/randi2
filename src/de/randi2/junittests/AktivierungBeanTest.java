package de.randi2.junittests;

import org.junit.Test;

import de.randi2.model.exceptions.AktivierungException;
import de.randi2.model.fachklassen.beans.AktivierungBean;

/**
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * @version $Id$
 *
 */
public class AktivierungBeanTest {
    
    
    
    
    @Test(expected=AktivierungException.class)
    public void aktivierungslinkZuKurz()throws AktivierungException{
	AktivierungBean abean=new AktivierungBean();

	abean.setAktivierungsLink("a");

    }
    @Test
    public void aktivierungslinkOk()throws AktivierungException{
	AktivierungBean abean=new AktivierungBean();
	abean.setAktivierungsLink("aaaaaaaaaaaaaaaaaaaa");
	
    }
    @Test(expected=AktivierungException.class)
    public void aktivierungslinkZuOk()throws AktivierungException{
	AktivierungBean abean=new AktivierungBean();
	abean.setAktivierungsLink("faaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaadddddddddddddddddddddddddddddddddddddddddddd");	
    }
    @Test(expected=AktivierungException.class)
    public void aktivierungslinkNull() throws AktivierungException
    {
	AktivierungBean abean=new AktivierungBean();
	abean.setAktivierungsLink(null);
	
    }
    
    @Test(expected=AktivierungException.class)
    public void benutzerkontoNull() throws AktivierungException{
	AktivierungBean abean=new AktivierungBean();
	abean.setBenutzerkonto(null);
    }

}
