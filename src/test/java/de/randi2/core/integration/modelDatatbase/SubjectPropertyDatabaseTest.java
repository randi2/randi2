package de.randi2.core.integration.modelDatatbase;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.SubjectProperty;
import de.randi2.model.criteria.DateCriterion;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.FreeTextCriterion;
import de.randi2.model.criteria.OrdinalCriterion;
import de.randi2.testUtility.utility.AbstractDomainDatabaseTest;
import de.randi2.unsorted.ContraintViolatedException;


@SuppressWarnings("unchecked")
public class SubjectPropertyDatabaseTest extends AbstractDomainDatabaseTest<SubjectProperty>{

	public SubjectPropertyDatabaseTest() {
		super(SubjectProperty.class);
	}

	@Test
	@Transactional
	public void testDatabaseIntegrationSubjectDichotomousCriterion() {
		DichotomousCriterion criterion = new DichotomousCriterion();
		criterion.setOption1("1");
		criterion.setOption2("2");
		sessionFactory.getCurrentSession().persist(criterion);
		assertTrue(criterion.getId() > 0);
		criterion = (DichotomousCriterion) sessionFactory.getCurrentSession().get(
				DichotomousCriterion.class, criterion.getId());
		SubjectProperty<String> subjectString = new SubjectProperty<String>(
				criterion);
		try {
			subjectString.setValue(criterion.getOption1());
			assertEquals(criterion.getOption1(), subjectString.getValue());
		} catch (ContraintViolatedException e) {
			fail("Constraint violated: " + e.getMessage());
		}
		sessionFactory.getCurrentSession().persist(subjectString);
		assertTrue(subjectString.getId() > 0);
		SubjectProperty<String> dbSubjectProperty = (SubjectProperty<String>) sessionFactory.getCurrentSession()
				.get(SubjectProperty.class, subjectString.getId());
		assertEquals(subjectString, dbSubjectProperty);
		assertEquals(subjectString.getCriterion(), dbSubjectProperty
				.getCriterion());
	}

	@Test
	@Transactional
	public void testDatabaseIntegrationSubjectOrdinalCriterion() {
		OrdinalCriterion criterion = new OrdinalCriterion();
		List<String> elements = new ArrayList<String>();
		elements.add("Value1");
		elements.add("Value2");
		elements.add("Value3");

		criterion.setElements(elements);
		sessionFactory.getCurrentSession().persist(criterion);
		assertTrue(criterion.getId() > 0);
		criterion = (OrdinalCriterion) sessionFactory.getCurrentSession().get(
				OrdinalCriterion.class, criterion.getId());
		SubjectProperty<String> subjectString = new SubjectProperty<String>(
				criterion);
		try {
			subjectString.setValue(criterion.getElements().get(0));
			assertEquals(criterion.getElements().get(0), subjectString
					.getValue());
		} catch (ContraintViolatedException e) {
			fail("Constraint violated: " + e.getMessage());
		}
		sessionFactory.getCurrentSession().persist(subjectString);
		assertTrue(subjectString.getId() > 0);
		SubjectProperty<String> dbSubjectProperty = (SubjectProperty<String>) sessionFactory.getCurrentSession()
				.get(SubjectProperty.class, subjectString.getId());
		assertEquals(subjectString, dbSubjectProperty);
		assertEquals(subjectString.getCriterion(), dbSubjectProperty
				.getCriterion());
	}

	@Test
	@Transactional
	public void testDatabaseIntegrationSubjectDateCriterion() {
		DateCriterion criterion = new DateCriterion();
		criterion.setName("dsagdsagsd");
		sessionFactory.getCurrentSession().persist(criterion);
		assertTrue(criterion.getId() > 0);
		criterion = (DateCriterion) sessionFactory.getCurrentSession().get(DateCriterion.class,
				criterion.getId());
		SubjectProperty<GregorianCalendar> subject = new SubjectProperty<GregorianCalendar>(
				criterion);
		try {
			subject.setValue(new GregorianCalendar());
		} catch (ContraintViolatedException e) {
			fail("Constraint violated: " + e.getMessage());
		}
		sessionFactory.getCurrentSession().persist(subject);
		assertTrue(subject.getId() > 0);
		SubjectProperty<String> dbSubjectProperty = (SubjectProperty<String>) sessionFactory.getCurrentSession()
				.get(SubjectProperty.class, subject.getId());
		assertEquals(subject, dbSubjectProperty);
		assertEquals(subject.getCriterion(), dbSubjectProperty.getCriterion());
	}

	@Test
	@Transactional
	public void testDatabaseIntegrationSubjectFreeTextCriterion() {
		FreeTextCriterion criterion = new FreeTextCriterion();
		criterion.setName("dsagdsagsd");
		sessionFactory.getCurrentSession().persist(criterion);
		assertTrue(criterion.getId() > 0);
		criterion = (FreeTextCriterion) sessionFactory.getCurrentSession().get(
				FreeTextCriterion.class, criterion.getId());
		SubjectProperty<String> subject = new SubjectProperty<String>(criterion);
		try {
			subject.setValue("test");
		} catch (ContraintViolatedException e) {
			fail("Constraint violated: " + e.getMessage());
		}
		sessionFactory.getCurrentSession().persist(subject);
		assertTrue(subject.getId() > 0);
		SubjectProperty<String> dbSubjectProperty = (SubjectProperty<String>) sessionFactory.getCurrentSession()
				.get(SubjectProperty.class, subject.getId());
		assertEquals(subject, dbSubjectProperty);
		assertEquals("test", dbSubjectProperty.getValue());
		assertEquals(subject.getCriterion(), dbSubjectProperty.getCriterion());
	}
	
}
