package de.randi2.junittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.GregorianCalendar;
import java.util.Vector;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.RandomisationsException;
import de.randi2.model.fachklassen.Patient;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PatientBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.StudienarmBean;
import de.randi2.randomisation.BlockRandomisation;
import de.randi2.utility.Log4jInit;

/**
 * <p>
 * Der JUnit Test für die BlockRandomisation Klasse
 * </p>
 * 
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @author Daniel Haehn [dhaehn@hs-heilbronn.de]
 * 
 * @version $Id: BlockRandomisationTest.java 2441 2007-05-07 08:08:23Z jthoenes $
 * 
 */
public class BlockRandomisationTest {
	
	StudieBean testStudieBean = null;

	StudienarmBean testArm1 = new StudienarmBean();

	StudienarmBean testArm2 = new StudienarmBean();

	StudienarmBean testArm3 = new StudienarmBean();
	StudienarmBean testArm4 = new StudienarmBean();

	PatientBean[] testPatienten = null;

	BlockRandomisation testBlockrandomisation = null;

	/**
	 * Initialisiert den Logger. Bitte log4j.lcf.pat in log4j.lcf umbenennen und
	 * es funktioniert.
	 * 
	 */
	@BeforeClass
	public static void log() {
		Log4jInit.initDebug();
	}

	@Before
	public void setUp() throws Exception {
		
		new Object(){
			public void randi2IstKeinBugEsIstEinFeature(){}
		}.randi2IstKeinBugEsIstEinFeature();
		
		BenutzerkontoBean bk = DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(1, new BenutzerkontoBean());
		
		testStudieBean = new StudieBean();
		testStudieBean.setName("Test Studie");
		testStudieBean.setId(2);
		testStudieBean.setBlockgroesse(3);
		Vector<StudienarmBean> studienarme = new Vector<StudienarmBean>();
		testArm1.setId(2);
		testArm2.setId(5);
		testArm3.setId(6);
		testArm4.setId(7);
		studienarme.add(testArm1);
		studienarme.add(testArm2);
		studienarme.add(testArm3);
		//studienarme.add(testArm4);
		testStudieBean.setStudienarme(studienarme);

		testBlockrandomisation = new BlockRandomisation(testStudieBean);

		testPatienten = new PatientBean[18];
		for (int i = 0; i < 18; i++) {
			testPatienten[i] = new PatientBean();
			testPatienten[i].setInitialen("TP" + i);
			testPatienten[i].setGeburtsdatum(new GregorianCalendar());
			if(i % 2 == 0){
				testPatienten[i].setGeschlecht('w');
			}
			else{
				testPatienten[i].setGeschlecht('m');
			}
			testPatienten[i].setDatumAufklaerung(new GregorianCalendar());
			testPatienten[i].setPerformanceStatus(1);
			testPatienten[i].setKoerperoberflaeche(125.1f);
			testPatienten[i].setBenutzerkonto(bk);
			testPatienten[i].setBenutzerkontoLogging(bk);
			
		}

	}

	@Test
	public void testRandomisierenPatient() {
		for (int i = 0; i < 18; i++) {
			try {
				testBlockrandomisation.randomisierenPatient(testPatienten[i]);
				Patient.speichern(testPatienten[i]);
			} catch (RandomisationsException e) {
				e.printStackTrace();
				fail(e.getMessage());
			} catch (DatenbankExceptions e1) {
				fail(e1.getMessage());
			}
		}
		try {
			System.out.println("Arm 1: ");
			for (int i = 0; i < testArm1.getPatienten().size(); i++) {

				System.out.print(testArm1.getPatienten().elementAt(i)
						.getInitialen()
						+ ",");

			}

			System.out.println("\r\nArm 2: ");
			for (int i = 0; i < testArm2.getPatienten().size(); i++) {

				System.out.print(testArm2.getPatienten().elementAt(i)
						.getInitialen()
						+ ",");

			}

			/*System.out.println("\r\nArm 3: ");
			for (int i = 0; i < testArm3.getPatienten().size(); i++) {

				System.out.print(testArm3.getPatienten().elementAt(i)
						.getInitialen()
						+ ",");

			}*/

			assertEquals(9, testArm1.getPatienten().size());
			assertEquals(9, testArm2.getPatienten().size());
			//assertEquals(6, testArm3.getPatienten().size());

		} catch (DatenbankExceptions e) {

			fail(e.getMessage());

		}

	}

}
