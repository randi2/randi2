package de.randi2test.legacy;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import de.randi2.model.fachklassen.Studie;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.StudienarmBean;
import de.randi2.utility.Log4jInit;

/**
 * Diese Klasse stellt einen Test fuer die Studienarm-Fachklasse zur Verfuegung.
 * 
 * 
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public class StudienarmTest {

	// das zu testende Objekt
	private StudienarmBean aStudienarmBean = null;

	
	 /**
	     * Initialisiert den Logger. Bitte log4j.lcf.pat in log4j.lcf umbenennen und es funktioniert.
	     *
	     */
	    @BeforeClass
	    public static void log(){
		Log4jInit.initDebug();
	    }
	/**
	 * Method setUp() Erzeugt eine neue Instanz der Klasse StudieBean.
	 * 
	 * @throws Exception,
	 *             Fehler, wenn keine Instanz der Klasse StudieBean erzeugt
	 *             werden kann.
	 */
	@Before
	public void setUp() throws Exception {
		aStudienarmBean = new StudienarmBean();

		aStudienarmBean.setId(21424);
		aStudienarmBean.setBeschreibung("Testbeschreibung");
		aStudienarmBean.setBezeichnung("Testbezeichnung");
		aStudienarmBean.setStatus(Studie.Status.AKTIV);
		aStudienarmBean.setStudieId(3434);
		aStudienarmBean.setStudie(new StudieBean());
	}

	/**
	 * Method tearDown() Dem StudieBean-Objekt wird der Wert "null" zugewiesen.
	 * 
	 * @throws Exception,
	 *             wenn die Testklasse nicht beendet werden kann.
	 */
	@After
	public void tearDown() throws Exception {
		aStudienarmBean = null;
	}


}
