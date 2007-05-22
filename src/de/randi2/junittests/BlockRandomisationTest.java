package de.randi2.junittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Vector;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.RandomisationsException;
import de.randi2.model.exceptions.StudienarmException;
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

	StudienarmBean testArm1 = null;

	StudienarmBean testArm2 = null;

	StudienarmBean testArm3 = null;

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
		testStudieBean = new StudieBean();
		testStudieBean.setName("Test Studie");
		// FIXME Kommentare anpassen
		// testArm1 = new StudienarmBean(13212,testStudieBean,1,"Testarm
		// 1","Test Arm nr. 1",new Vector<PatientBean>());
		// testArm2 = new StudienarmBean(321,testStudieBean,1,"Testarm 2","Test
		// Arm nr. 2",new Vector<PatientBean>());
		// testArm3 = new StudienarmBean(21312,testStudieBean,1,"Testarm
		// 3","Test Arm nr. 3",new Vector<PatientBean>());
		Vector<StudienarmBean> studienarme = new Vector<StudienarmBean>();
		studienarme.add(testArm1);
		studienarme.add(testArm2);
		studienarme.add(testArm3);
		testStudieBean.setStudienarme(studienarme);

		testBlockrandomisation = new BlockRandomisation(testStudieBean, 9);

		testPatienten = new PatientBean[90];
		for (int i = 0; i < 90; i++) {
			testPatienten[i] = new PatientBean();
			testPatienten[i].setInitialen("TP" + i);
		}

	}

	@Test
	public void testRandomisierenPatient() {
		for (int i = 0; i < 90; i++) {
			try {
				testBlockrandomisation.randomisierenPatient(testPatienten[i]);
			} catch (RandomisationsException e) {
				fail(e.getMessage());
			} catch (DatenbankFehlerException e1) {
				fail(e1.getMessage());
			}
		}
		try {
			System.out.println("Arm 1: ");
			for (int i = 0; i < 30; i++) {

				System.out.print(testArm1.getPatienten().elementAt(i)
						.getInitialen()
						+ ",");

			}

			System.out.println("\r\nArm 2: ");
			for (int i = 0; i < 30; i++) {

				System.out.print(testArm2.getPatienten().elementAt(i)
						.getInitialen()
						+ ",");

			}

			System.out.println("\r\nArm 3: ");
			for (int i = 0; i < 30; i++) {

				System.out.print(testArm3.getPatienten().elementAt(i)
						.getInitialen()
						+ ",");

			}

			assertEquals(30, testArm1.getPatienten().size());
			assertEquals(30, testArm2.getPatienten().size());
			assertEquals(30, testArm3.getPatienten().size());

		} catch (StudienarmException e) {

			fail(e.getMessage());

		}

	}

}
