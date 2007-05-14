package de.randi2.junittests;

import static org.junit.Assert.assertEquals;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.fachklassen.Studie;
import de.randi2.model.fachklassen.beans.PatientBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.StudienarmBean;
import de.randi2.randomisation.Randomisation;
import de.randi2.randomisation.VollstaendigeRandomisation;

public class VollstaendigeRandomisationTest {

	StudieBean testStudieBean = null;

	StudienarmBean testArm1 = null;

	StudienarmBean testArm2 = null;

	StudienarmBean testArm3 = null;

	PatientBean[] testPatienten = null;

	Randomisation testBlockrandomisation = null;

	@Before
	public void setUp() throws Exception {
		GregorianCalendar heute = new GregorianCalendar();
		GregorianCalendar bald = new GregorianCalendar();
		bald.setTimeInMillis(heute.getTimeInMillis() + 1000 * 60 * 60 * 24
				* 365);

		testStudieBean = new StudieBean(1, "mutter", heute, bald,
				"./empty.txt", 0);
		testArm1 = new StudienarmBean(123, 1, Studie.Status.AKTIV, "ARM 1",
				"desc1");
		testArm2 = new StudienarmBean(123, 1, Studie.Status.AKTIV, "ARM 2",
				"desc2");
		testArm3 = new StudienarmBean(123, 1, Studie.Status.AKTIV, "ARM 3",
				"desc3");
		Vector<StudienarmBean> studienarme = new Vector<StudienarmBean>();
		studienarme.add(testArm1);
		studienarme.add(testArm2);
		studienarme.add(testArm3);
		testStudieBean.setStudienarme(studienarme);

		testBlockrandomisation = new VollstaendigeRandomisation(
				this.testStudieBean);

		testPatienten = new PatientBean[90];

	}

	@Test
	public void testRandomisierenPatient() {
		for (int i = 0; i < 90; i++) {
			testPatienten[i] = new PatientBean();
			testPatienten[i].setInitialen("TP" + i);
		}

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

		int anzPat = testArm1.getPatienten().size()
				+ testArm2.getPatienten().size()
				+ testArm3.getPatienten().size();
		
		assertEquals(90, anzPat);
	}

}
