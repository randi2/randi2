package de.randi2test.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.randi2.model.SubjectProperty;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.OrdinalCriterion;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2test.utility.AbstractDomainTest;

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
		} catch (ContraintViolatedException e) {
			fail("Constraint violated: " + e.getMessage());
		}
		hibernateTemplate.persist(subjectString);
		assertTrue(subjectString.getId()>0);
		SubjectProperty<String> dbSubjectProperty = (SubjectProperty<String>)hibernateTemplate.get(SubjectProperty.class, subjectString.getId());
		assertEquals(subjectString, dbSubjectProperty);
		assertEquals(subjectString.getCriterion(), dbSubjectProperty.getCriterion());
	}

}
