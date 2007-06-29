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

		new Object() {
			public void randi2IstKeinBugEsIstEinFeature() {
			}
		}.randi2IstKeinBugEsIstEinFeature();

		BenutzerkontoBean bk = DatenbankFactory.getAktuelleDBInstanz()
				.suchenObjektId(1, new BenutzerkontoBean());

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
		// studienarme.add(testArm4);
		testStudieBean.setStudienarme(studienarme);

		testBlockrandomisation = new BlockRandomisation(testStudieBean);

		testPatienten = new PatientBean[18];
		for (int i = 0; i < 18; i++) {
			testPatienten[i] = new PatientBean();
			testPatienten[i].setInitialen("TP" + i);
			testPatienten[i].setGeburtsdatum(new GregorianCalendar());
			if (i % 2 == 0) {
				testPatienten[i].setGeschlecht('w');
			} else {
				testPatienten[i].setGeschlecht('m');
			}
			testPatienten[i].setDatumAufklaerung(new GregorianCalendar());
			testPatienten[i].setPerformanceStatus(1);
			testPatienten[i].setKoerperoberflaeche(125.1f);
			testPatienten[i].setBenutzerkonto(bk);
			testPatienten[i].setBenutzerkontoLogging(bk);

		}

	}

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

			/*
			 * System.out.println("\r\nArm 3: "); for (int i = 0; i <
			 * testArm3.getPatienten().size(); i++) {
			 * 
			 * System.out.print(testArm3.getPatienten().elementAt(i)
			 * .getInitialen() + ","); }
			 */

			assertEquals(9, testArm1.getPatienten().size());
			assertEquals(9, testArm2.getPatienten().size());
			// assertEquals(6, testArm3.getPatienten().size());

		} catch (DatenbankExceptions e) {

			fail(e.getMessage());

		}

	}

	@Test
	public void testErzeugeBlock1() {

		for (int i = 2; i <= 12; i++) {
			for (int j = 1; j <= 6; j++) {
				int block[] = BlockRandomisation.getErzeugtenBlock(i, i * j);
				this.checkBlock(block, i);
			}
		}
	}

	// 379316842

	@Test
	public void testErzuegeBlock2() {
		int blocks[][] = new int[1000][8];
		int grades[] = new int[6];

		int quantil0_05 = 147;
		int quantil0_95 = 185;

		for (int i = 0; i < blocks.length; i++) {
			int block[] = BlockRandomisation.getErzeugtenBlock(2, 4);
			grades[this.grade(block, 4)]++;
		}

		for (int i = 0; i < grades.length; i++) {
			if (grades[i] < quantil0_05) {
				fail("Unterschreitung des 0.05 Quantils durch Komb. " + i
						+ "= " + grades[i]);
			}

			if (grades[i] > quantil0_95) {
				fail("Ueberschreiten des 0.95 Quantils durch Komb. " + i + "= "
						+ grades[i]);
			}
			System.out.println(i + ": " + grades[i]);
		}

	}
	
	@Test
	public void testGrade(){
		int block0[] = {0,0,1,1};
		assertEquals(0, grade(block0, 2));
		int block1[] = {0,1,0,1};
		assertEquals(1, grade(block1, 2));
		int block2[] = {0,1,1,0};
		assertEquals(2, grade(block2, 2));
		int block3[] = {1,0,0,1};
		assertEquals(3, grade(block3, 2));
		int block4[] = {1,0,1,0};
		assertEquals(4, grade(block4, 2));
		int block5[] = {1,1,0,0};
		assertEquals(5, grade(block5, 2));
	}

	private int grade(int block[], int anzArme) {
		
		if (block[0] == 0) {
			if (block[1] == 0) {
				if (block[2] == 1) {
					if (block[3] == 1) {
						return 0;
					}
				}
			} else {
				if (block[2] == 0) {
					if (block[3] == 1) {
						return 1;
					}
				} else {
					if (block[3] == 0) {
						return 2;
					}
				}
			}
		} else {
			if (block[1] == 0) {
				if (block[2] == 0) {
					if (block[3] == 1) {
						return 3;
					}
				}
				else{
					if(block[3] == 0){
						return 4;
					}
				}
			} else {
				if (block[2] == 0) {
					if (block[3] == 0) {
						return 5;
					}
				}
			}
		}
		fail("Falscher Block");
		return -1;
	}

	private void checkBlock(int[] block, int anzArme) {
		int sollGr = block.length / anzArme;
		int anzahlen[] = new int[anzArme];

		for (int i = 0; i < block.length; i++) {
			anzahlen[block[i]]++;
		}

		for (int i = 0; i < anzahlen.length; i++) {
			assertEquals(anzahlen[i], sollGr);
		}

	}

}
