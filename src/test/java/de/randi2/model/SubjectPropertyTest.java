package de.randi2.model;

import static junit.framework.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import de.randi2.model.SubjectProperty;
import de.randi2.model.criteria.DateCriterion;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.FreeTextCriterion;
import de.randi2.model.criteria.OrdinalCriterion;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2.test.utility.AbstractDomainTest;

@SuppressWarnings("unchecked")
public class SubjectPropertyTest extends AbstractDomainTest<SubjectProperty> {

	public SubjectPropertyTest() {
		super(SubjectProperty.class);
	}


	@Test
	public void testDatabaseIntegrationSubjectDichotomousCriterion(){
		DichotomousCriterion criterion = new DichotomousCriterion();
		criterion.setOption1("1");
		criterion.setOption2("2");
		hibernateTemplate.persist(criterion);
		assertTrue(criterion.getId()>0);
		criterion = (DichotomousCriterion)hibernateTemplate.get(DichotomousCriterion.class, criterion.getId());
		SubjectProperty<String> subjectString = new SubjectProperty<String>(criterion);
		try {
			subjectString.setValue(criterion.getOption1());
			assertEquals(criterion.getOption1(),subjectString.getValue());
		} catch (ContraintViolatedException e) {
			fail("Constraint violated: " + e.getMessage());
		}
		hibernateTemplate.persist(subjectString);
		assertTrue(subjectString.getId()>0);
		SubjectProperty<String> dbSubjectProperty = (SubjectProperty<String>)hibernateTemplate.get(SubjectProperty.class, subjectString.getId());
		assertEquals(subjectString, dbSubjectProperty);
		assertEquals(subjectString.getCriterion(), dbSubjectProperty.getCriterion());
	}
	
	@Test
	public void testDatabaseIntegrationSubjectOrdinalCriterion(){
		OrdinalCriterion criterion = new OrdinalCriterion();
		List<String> elements = new ArrayList<String>();
		elements.add("Value1");
		elements.add("Value2");
		elements.add("Value3");
		
		criterion.setElements(elements);
		hibernateTemplate.persist(criterion);
		assertTrue(criterion.getId()>0);
		criterion = (OrdinalCriterion)hibernateTemplate.get(OrdinalCriterion.class, criterion.getId());
		SubjectProperty<String> subjectString = new SubjectProperty<String>(criterion);
		try {
			subjectString.setValue(criterion.getElements().get(0));
			assertEquals(criterion.getElements().get(0),subjectString.getValue());
		} catch (ContraintViolatedException e) {
			fail("Constraint violated: " + e.getMessage());
		}
		hibernateTemplate.persist(subjectString);
		assertTrue(subjectString.getId()>0);
		SubjectProperty<String> dbSubjectProperty = (SubjectProperty<String>)hibernateTemplate.get(SubjectProperty.class, subjectString.getId());
		assertEquals(subjectString, dbSubjectProperty);
		assertEquals(subjectString.getCriterion(), dbSubjectProperty.getCriterion());
	}
	
	
	@Test
	public void testDatabaseIntegrationSubjectDateCriterion(){
		DateCriterion criterion = new DateCriterion();
		criterion.setName("dsagdsagsd");
		hibernateTemplate.persist(criterion);
		assertTrue(criterion.getId()>0);
		criterion = (DateCriterion)hibernateTemplate.get(DateCriterion.class, criterion.getId());
		SubjectProperty<GregorianCalendar> subject = new SubjectProperty<GregorianCalendar>(criterion);
		try {
			subject.setValue(new GregorianCalendar());
		} catch (ContraintViolatedException e) {
			fail("Constraint violated: " + e.getMessage());
		}
		hibernateTemplate.persist(subject);
		assertTrue(subject.getId()>0);
		SubjectProperty<String> dbSubjectProperty = (SubjectProperty<String>)hibernateTemplate.get(SubjectProperty.class, subject.getId());
		assertEquals(subject, dbSubjectProperty);
		assertEquals(subject.getCriterion(), dbSubjectProperty.getCriterion());
	}

	
	@Test
	public void testDatabaseIntegrationSubjectFreeTextCriterion(){
		FreeTextCriterion criterion = new FreeTextCriterion();
		criterion.setName("dsagdsagsd");
		hibernateTemplate.persist(criterion);
		assertTrue(criterion.getId()>0);
		criterion = (FreeTextCriterion)hibernateTemplate.get(FreeTextCriterion.class, criterion.getId());
		SubjectProperty<String> subject = new SubjectProperty<String>(criterion);
		try {
			subject.setValue("test");
		} catch (ContraintViolatedException e) {
			fail("Constraint violated: " + e.getMessage());
		}
		hibernateTemplate.persist(subject);
		assertTrue(subject.getId()>0);
		SubjectProperty<String> dbSubjectProperty = (SubjectProperty<String>)hibernateTemplate.get(SubjectProperty.class, subject.getId());
		assertEquals(subject, dbSubjectProperty);
		assertEquals("test",dbSubjectProperty.getValue());
		assertEquals(subject.getCriterion(), dbSubjectProperty.getCriterion());
	}
	
	@Test
	public void testGetRequieredFields(){
		OrdinalCriterion criterion = new OrdinalCriterion();
		List<String> elements = new ArrayList<String>();
		elements.add("Value1");
		elements.add("Value2");
		elements.add("Value3");
		
		criterion.setElements(elements);
		hibernateTemplate.persist(criterion);
		criterion = (OrdinalCriterion)hibernateTemplate.get(OrdinalCriterion.class, criterion.getId());
		SubjectProperty<String> subjectString = new SubjectProperty<String>(criterion);
		Map<String, Boolean> map = subjectString.getRequiredFields();
		for(String key : map.keySet()){
			assertFalse(map.get(key));
		}
	}

}
