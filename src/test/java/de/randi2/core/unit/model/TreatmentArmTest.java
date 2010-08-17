package de.randi2.core.unit.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.testUtility.utility.AbstractDomainTest;

public class TreatmentArmTest extends AbstractDomainTest<TreatmentArm> {

	private TreatmentArm validTreatmentArm;

	public TreatmentArmTest() {
		super(TreatmentArm.class);
	}

	@Before
	public void setUp() {
		validTreatmentArm = new TreatmentArm();
		validTreatmentArm.setDescription("description");
		validTreatmentArm.setName("arm");
		validTreatmentArm.setPlannedSubjects(10);
		validTreatmentArm.setTrial(factory.getTrial());
	}

	@Test
	public void testDescriptionNull() {
		validTreatmentArm.setDescription(null);
		assertNull(validTreatmentArm.getDescription());
		assertValid(validTreatmentArm);
	}

	@Test
	public void testDescriptionEmpty() {
		validTreatmentArm.setDescription("");
		assertEquals("", validTreatmentArm.getDescription());
		assertValid(validTreatmentArm);
	}

	@Test
	public void testDescriptionAnyLengt() {
		String[] validValues = { stringUtil.getWithLength(8941),
				stringUtil.getWithLength(2), stringUtil.getWithLength(10000000) };
		for (String s : validValues) {
			validTreatmentArm.setDescription(s);
			assertEquals(s, validTreatmentArm.getDescription());
			assertValid(validTreatmentArm);
		}
	}

	@Test
	public void testNameNotNull() {
		validTreatmentArm.setName(null);
		assertNull(validTreatmentArm.getName());
		assertInvalid(validTreatmentArm);
	}

	@Test
	public void testNameNotEmpty() {
		validTreatmentArm.setName("");
		assertEquals("", validTreatmentArm.getName());
		assertInvalid(validTreatmentArm);
	}

	@Test
	public void testNameLongerThan255() {
		String[] invalidValues = { stringUtil.getWithLength(256),
				stringUtil.getWithLength(650) };
		for (String s : invalidValues) {
			validTreatmentArm.setName(s);
			assertEquals(s, validTreatmentArm.getName());
			assertInvalid(validTreatmentArm);
		}
	}

	@Test
	public void testNameCorrect() {
		String[] validValues = { stringUtil.getWithLength(254),
				stringUtil.getWithLength(2), "Name",
				stringUtil.getWithLength(132) };
		for (String s : validValues) {
			validTreatmentArm.setName(s);
			assertEquals(s, validTreatmentArm.getName());
			assertValid(validTreatmentArm);
		}
	}

	@Test
	public void testPlannedSubjectSizeLessThan1() {
		int[] invalidValues = { 0, -125, -4694123, Integer.MIN_VALUE };
		for (int i : invalidValues) {
			validTreatmentArm.setPlannedSubjects(i);
			assertEquals(i, validTreatmentArm.getPlannedSubjects());
			assertInvalid(validTreatmentArm);
		}
	}

	@Test
	public void testPlannedSubjectCorrect() {
		int[] validValues = { 1, 123, 15834, Integer.MAX_VALUE, 2147483647 };
		for (int i : validValues) {
			validTreatmentArm.setPlannedSubjects(i);
			assertEquals(i, validTreatmentArm.getPlannedSubjects());
			assertValid(validTreatmentArm);
		}
	}

	@Test
	public void testSubjectsNull() {
		validTreatmentArm.setSubjects(null);
		assertValid(validTreatmentArm);
	}

	@Test
	public void testSubjects() {
		assertTrue(validTreatmentArm.getSubjects().isEmpty());
		assertValid(validTreatmentArm);
		TrialSubject subject1 = new TrialSubject();
		subject1.setIdentification("id1");
		TrialSubject subject2 = new TrialSubject();
		subject2.setIdentification("id2");
		List<TrialSubject> list = new ArrayList<TrialSubject>();
		list.add(subject1);
		list.add(subject2);
		validTreatmentArm.setSubjects(list);
		assertValid(validTreatmentArm);
		assertEquals(2, validTreatmentArm.getSubjects().size());
		assertTrue(validTreatmentArm.getSubjects().contains(subject1));
		assertTrue(validTreatmentArm.getSubjects().contains(subject2));

		list = new ArrayList<TrialSubject>();
		list.add(new TrialSubject());

		validTreatmentArm.setSubjects(list);
		assertEquals(list, validTreatmentArm.getSubjects());
	}

	@Test
	public void testSubjectsAdd() {
		assertTrue(validTreatmentArm.getSubjects().isEmpty());
		assertValid(validTreatmentArm);
		TrialSubject subject1 = new TrialSubject();
		subject1.setIdentification("id1");
		TrialSubject subject2 = new TrialSubject();
		subject2.setIdentification("id2");
		validTreatmentArm.addSubject(subject1);
		assertValid(validTreatmentArm);
		validTreatmentArm.addSubject(subject2);
		assertValid(validTreatmentArm);
		assertEquals(2, validTreatmentArm.getSubjects().size());
		assertTrue(validTreatmentArm.getSubjects().contains(subject1));
		assertTrue(validTreatmentArm.getSubjects().contains(subject2));
	}

	@Test
	public void testCurrentSubjectAmount() {
		for (int i = 1; i <= 100; i++) {
			validTreatmentArm.addSubject(new TrialSubject());
			assertEquals(i, validTreatmentArm.getCurrentSubjectsAmount());
		}
	}
	
	@Test
	public void testFillLevel() {
		validTreatmentArm.setPlannedSubjects(100);
		for (int i = 1; i <= 100; i++) {
			validTreatmentArm.addSubject(new TrialSubject());
			assertEquals(i, validTreatmentArm.getCurrentSubjectsAmount());
			DecimalFormat f = new DecimalFormat("#0.00000");
			assertEquals(f.format(i), f
					.format(validTreatmentArm.getFillLevel()));
		}
	}

	
	@Test 
	public void testTrialNotNull(){
		validTreatmentArm.setTrial(null);
		assertNull(validTreatmentArm.getTrial());
		assertInvalid(validTreatmentArm);
	}
	
	
	@Test
	public void testTrialCorrect() {
		Trial trial = factory.getTrial();
		validTreatmentArm.setTrial(trial);
		assertEquals(trial, validTreatmentArm.getTrial());
		assertValid(validTreatmentArm);
	}

	@Test
	public void testEqualsHashCode() {
		TreatmentArm arm1 = new TreatmentArm();
		TreatmentArm arm2 = new TreatmentArm();
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());

		arm1.setId(12);
		arm2.setId(12);
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());

		arm1.setName("name");
		arm2.setName("name");
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());

		arm1.setDescription("name");
		arm2.setDescription("name");
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());

		arm1.setPlannedSubjects(10);
		arm2.setPlannedSubjects(10);
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());

		arm1.setId(12);
		arm2.setId(13);
		assertFalse(arm1.equals(arm2));
		arm2.setId(12);
		assertTrue(arm1.equals(arm2));

		arm1.setName("name");
		arm2.setName("name1");
		assertFalse(arm1.equals(arm2));
		arm2.setName("name");
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());

		arm1.setDescription("name");
		arm2.setDescription("name1");
		assertFalse(arm1.equals(arm2));
		arm2.setDescription("name");
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());

		arm1.setPlannedSubjects(10);
		arm2.setPlannedSubjects(11);
		assertFalse(arm1.equals(arm2));
		arm2.setPlannedSubjects(10);
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());

		arm1.setVersion(256);
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());

		assertFalse(arm1.equals(null));
		assertFalse(arm1.equals(new Trial()));
	}

	@Test
	public void testGetRequieredFields() {
		Map<String, Boolean> map = (new TreatmentArm()).getRequiredFields();
		for (String key : map.keySet()) {
			if (key.equals("name")) {
				assertTrue(map.get(key));
			} else if (key.equals("description")) {
				assertFalse(map.get(key));
			} else if (key.equals("plannedSubjects")) {
				assertFalse(map.get(key));
			} else if (key.equals("trial")) {
				assertTrue(map.get(key));
			} else if (key.equals("subjects")) {
				assertFalse(map.get(key));
			} else if (key.equals("serialVersionUID")) {
				assertFalse(map.get(key));
			} else if (key.equals("$VRc")) {
				assertFalse(map.get(key));
			} else
				fail(key + " not checked");
		}
	}

	@Test
	public void testToString() {
		assertNotNull(validTreatmentArm.toString());
	}

	@Test
	public void testUiName() {
		validTreatmentArm.setName("valid name");
		assertEquals("valid name", validTreatmentArm.getUIName());
	}


}
