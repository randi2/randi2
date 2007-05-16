package de.randi2.junittests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.randi2.model.fachklassen.Studie;
import de.randi2.model.fachklassen.beans.PatientBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.StudienarmBean;

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
