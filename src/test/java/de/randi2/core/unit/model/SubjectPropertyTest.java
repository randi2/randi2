package de.randi2.core.unit.model;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.SubjectProperty;
import de.randi2.model.TreatmentArm;
import de.randi2.model.criteria.FreeTextCriterion;
import de.randi2.model.criteria.OrdinalCriterion;
import de.randi2.model.criteria.constraints.OrdinalConstraint;
import de.randi2.testUtility.utility.AbstractDomainTest;
import de.randi2.unsorted.ContraintViolatedException;

@SuppressWarnings("unchecked")
public class SubjectPropertyTest extends AbstractDomainTest<SubjectProperty> {

	public SubjectPropertyTest(){
		super(SubjectProperty.class);
	}

	

	@Test
	@Transactional
	public void testGetRequieredFields() {
		OrdinalCriterion criterion = new OrdinalCriterion();
		List<String> elements = new ArrayList<String>();
		elements.add("Value1");
		elements.add("Value2");
		elements.add("Value3");

		criterion.setElements(elements);
		SubjectProperty<String> subjectString = new SubjectProperty<String>(
				criterion);
		Map<String, Boolean> map = subjectString.getRequiredFields();
		for (String key : map.keySet()) {
			assertFalse(map.get(key));
		}
	}

	@Test
	public void testCriterion() {
		OrdinalCriterion criterion = new OrdinalCriterion();
		List<String> elements = new ArrayList<String>();
		elements.add("Value1");
		elements.add("Value2");
		elements.add("Value3");
		criterion.setElements(elements);

		SubjectProperty<String> subjectString = new SubjectProperty<String>(
				criterion);
		assertEquals(criterion, subjectString.getCriterion());

		FreeTextCriterion criterion1 = new FreeTextCriterion();
		subjectString = new SubjectProperty<String>(
				criterion1);
		criterion1.setName("dsagdsagsd");
		assertEquals(criterion1, subjectString.getCriterion());
	}

	@Test
	public void testValue() {
		OrdinalCriterion criterion = new OrdinalCriterion();
		List<String> elements = new ArrayList<String>();
		elements.add("Value1");
		elements.add("Value2");
		elements.add("Value3");
		criterion.setElements(elements);

		SubjectProperty<String> subjectString = new SubjectProperty<String>(
				criterion);
		try {
			subjectString.setValue("Value1");
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}
		assertEquals("Value1", subjectString.getValue());
		try {
			subjectString.setValue("ValueXYZ");
			fail("no exception");
		} catch (ContraintViolatedException e) {
		}
	}

	@Test
	public void testStratum() {
		OrdinalCriterion criterion = new OrdinalCriterion();
		List<String> elements = new ArrayList<String>();
		elements.add("Value1");
		elements.add("Value2");
		elements.add("Value3");
		criterion.setElements(elements);
		OrdinalConstraint constraint1 = null;
		OrdinalConstraint constraint2 = null;
		try {
			constraint1 = new OrdinalConstraint(Arrays
					.asList(new String[] { "Value1" }));
			constraint1.setId(123);
			constraint2 = new OrdinalConstraint(Arrays
					.asList(new String[] { "Value2" }));
			constraint2.setId(345);
		} catch (ContraintViolatedException e1) {
			fail();
		}
		criterion.addStrata(constraint1);
		criterion.addStrata(constraint2);
		SubjectProperty<String> subjectString = new SubjectProperty<String>(
				criterion);

		try {
			subjectString.getStratum();
			fail();
		} catch (ContraintViolatedException e) {
			assertNotNull(e);
		}

		try {
			subjectString.setValue("Value1");
			assertEquals(123, subjectString.getStratum());
		} catch (ContraintViolatedException e) {
			fail();
		}

		try {
			subjectString.setValue("Value2");
			assertEquals(345, subjectString.getStratum());
		} catch (ContraintViolatedException e) {
			fail();
		}
	}

	@Test
	public void testEqualsHashCode() {
		FreeTextCriterion criterion1 = new FreeTextCriterion();
		criterion1.setName("dsagdsagsd");
		SubjectProperty<String> subject1 = new SubjectProperty<String>(
				criterion1);
		FreeTextCriterion criterion2 = new FreeTextCriterion();
		criterion2.setName("dsagdsagsd2");
		SubjectProperty<String> subject2 = new SubjectProperty<String>(
				criterion1);
		SubjectProperty<String> subject3 = new SubjectProperty<String>(
				criterion2);

		subject1.setId(0);
		subject2.setId(0);
		subject3.setId(0);
		subject1.setVersion(0);
		subject2.setVersion(0);
		subject3.setVersion(0);
		assertEquals(subject1, subject2);
		assertEquals(subject1.hashCode(), subject2.hashCode());
		subject1.setId(1);

		assertFalse(subject1.equals(subject2));
		subject1.setId(0);
		assertEquals(subject1, subject2);
		assertEquals(subject1.hashCode(), subject2.hashCode());

		subject1.setVersion(1);
		assertFalse(subject1.equals(subject2));
		subject1.setVersion(0);
		assertEquals(subject1, subject2);
		assertEquals(subject1.hashCode(), subject2.hashCode());

		try {
			subject1.setValue("test");
			assertFalse(subject1.equals(subject2));
			subject2.setValue("test");
			assertEquals(subject1, subject2);
			assertEquals(subject1.hashCode(), subject2.hashCode());
		} catch (ContraintViolatedException e) {
			fail();
		}
		assertFalse(subject1.equals(subject3));

		assertFalse(subject1.equals(null));
		assertFalse(subject1.equals(new TreatmentArm()));
	}

}
