package de.randi2test.legacy;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Vector;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.RandomisationsException;
import de.randi2.model.exceptions.StudienarmException;
import de.randi2.model.fachklassen.Studie;
import de.randi2.model.fachklassen.beans.PatientBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.StudienarmBean;
import de.randi2.randomisation.Randomisation;
import de.randi2.randomisation.VollstaendigeRandomisation;
import de.randi2.utility.Log4jInit;

/**
 * Testet die vollstaendige Randomisation. ACHTUNG: Die vollstaendige
 * Randomisation kann nur sehr eingeschraenkt durch ein deterministisches
 * Testframework wie JUnit getestet werden, da die Ergebnisse vollstaendig
 * zufaellig sind.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public class VollstaendigeRandomisationTest {

	StudieBean testStudieBean = null;

	StudienarmBean testArm1 = null;

	StudienarmBean testArm2 = null;

	StudienarmBean testArm3 = null;

	PatientBean[] testPatienten = null;

	Randomisation testRandomisation = null;

	/**
	 * Initialisiert den Logger. Bitte log4j.lcf.pat in log4j.lcf umbenennen und
	 * es funktioniert.
	 * 
	 */
	@BeforeClass
	public static void log() {
		Log4jInit.initDebug();
	}

	/**
	 * Erstellt die wichtigen Variablen.
	 * 
	 * @throws Exception
	 *             Duerfte bei erfolgreichen Testlauf nicht auftreten.
	 */
	@Before
	public void setUp() throws Exception {
		GregorianCalendar heute = new GregorianCalendar();
		GregorianCalendar bald = new GregorianCalendar();
		bald.setTimeInMillis(heute.getTimeInMillis() + 1000 * 60 * 60 * 24
				* 365);

		testStudieBean = new StudieBean(1, "mutter","test studie",null,2, heute, bald,
				"./empty.txt",Studie.Status.AKTIV,2);
		testArm1 = new StudienarmBean(123, 1, Studie.Status.AKTIV, "ARM 1",
				"desc1");
		testArm1.setPatienten(new Vector<PatientBean>());
		testArm2 = new StudienarmBean(123, 1, Studie.Status.AKTIV, "ARM 2",
				"desc2");
		testArm2.setPatienten(new Vector<PatientBean>());
		testArm3 = new StudienarmBean(123, 1, Studie.Status.AKTIV, "ARM 3",
				"desc3");
		testArm3.setPatienten(new Vector<PatientBean>());
		Vector<StudienarmBean> studienarme = new Vector<StudienarmBean>();
		studienarme.add(testArm1);
		studienarme.add(testArm2);
		studienarme.add(testArm3);
		testStudieBean.setStudienarme(studienarme);

		testRandomisation = new VollstaendigeRandomisation(this.testStudieBean);

		testPatienten = new PatientBean[90];

	}

	/**
	 * Testet ob alle mit vollstaendige Randomisation randomisierten Patienten
	 * danach auch einem Studienarm zugeteilt sind.
	 * 
	 * @throws RandomisationsException
	 *             Duerfte bei erfolgreichen Testlauf nicht auftreten.
	 * @throws DatenbankExceptions
	 *             Duerfte bei erfolgreichen Testlauf nicht auftreten.
	 */
	@Test
	public void testRandomisierenPatient() throws RandomisationsException,
			DatenbankExceptions {
		for (int i = 0; i < 90; i++) {
			testPatienten[i] = new PatientBean();
			testPatienten[i].setInitialen("INI" + i);
			testRandomisation.randomisierenPatient(testPatienten[i]);
		}
			System.out.println("Arm 1: (" + testArm1.getPatienten().size()
					+ ")");
			Iterator<PatientBean> it = testArm1.getPatienten().iterator();
			while (it.hasNext()) {
				System.out.print(it.next().getInitialen() + ",");
			}
			System.out.println();
			System.out.println();

			System.out.println("Arm 2: (" + testArm2.getPatienten().size()
					+ ")");
			it = testArm2.getPatienten().iterator();
			while (it.hasNext()) {
				System.out.print(it.next().getInitialen() + ",");
			}
			System.out.println();
			System.out.println();

			System.out.println("Arm 3: (" + testArm3.getPatienten().size()
					+ ")");
			it = testArm3.getPatienten().iterator();
			while (it.hasNext()) {
				System.out.print(it.next().getInitialen() + ",");
			}
			System.out.println();
			System.out.println();

			int anzPat = testArm1.getPatienten().size()
					+ testArm2.getPatienten().size()
					+ testArm3.getPatienten().size();

			assertEquals(90, anzPat);

	}

}
