/**
 * 
 */
package de.randi2.junittests;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.model.fachklassen.Recht;
import de.randi2.model.fachklassen.Recht.Rechtenamen;

/**
 * @author Nadine Zwink <nzwink@stud.hs-heilbronn.de>
 * @version $Id $
 */
public class RechtTest {

	   
    private Recht recht;
	private Rechtenamen rName;
	
	/**
     * HashMap, welche die Instanzen der einzelnen Rechte verwaltet.
     */
    private static HashMap<Rechtenamen, Recht> rechte = new HashMap<Rechtenamen, Recht>();

	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		recht = new Recht(rName);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		recht=null;
	}

	
	/**
	 * Test method for {@link de.randi2.model.fachklassen.Recht#getRecht(de.randi2.model.fachklassen.Recht.Rechtenamen)}.
	 */
	@Test
	public void testGetRecht() {
	  try{		
		
	    if(rechte.equals("")){
			fail("Ungueltiges Argument: 'null'");
		}
	  }
	  catch(Exception e){
		  fail("[FEHLER] testGetRecht()");
	  }
	}

	/**
	 * Test method for {@link de.randi2.model.fachklassen.Recht#getName()}.
	 */
	@Test
	public void testGetName() {
		fail("Not yet defined");
		//muss dies getestet werden?
	}

	/**
	 * Test method for {@link de.randi2.model.fachklassen.Recht#getRechtname()}.
	 */
	@Test
	public void testGetRechtname() {
		  fail("Not yet defined");
		  //muss dies getestet werden?
	}

}
