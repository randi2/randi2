package de.randi2.core.integration.modelDatatbase.criteria;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.criteria.DateCriterion;
import de.randi2.model.criteria.constraints.DateConstraint;
import de.randi2.testUtility.utility.AbstractDomainDatabaseTest;
import de.randi2.unsorted.ContraintViolatedException;

public class DateCriterionDatabaseTest extends AbstractDomainDatabaseTest<DateCriterion>{

	
	public DateCriterionDatabaseTest(){
		super(DateCriterion.class);
	}
	private DateCriterion criterion;
	
	@Before
	public void setUp(){
		super.setUp();
		criterion = new DateCriterion();
	}
	
	@Test
	@Transactional
	public void databaseIntegrationTest() {
		criterion.setName("name");
		criterion.setDescription("test");
		List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
		elements.add(new GregorianCalendar());
		elements.add(new GregorianCalendar());
		try {
			ArrayList<DateConstraint> temp = new ArrayList<DateConstraint>();
			temp.add(new DateConstraint(Arrays.asList(new GregorianCalendar[]{elements.get(0)})));
			temp.add(new DateConstraint(Arrays.asList(new GregorianCalendar[]{elements.get(1)})));
		
			DateConstraint constraint = new DateConstraint(Arrays.asList(elements.get(0)));
			sessionFactory.getCurrentSession().save(constraint);
			assertTrue(constraint.getId()>0);
			criterion.setInclusionConstraint(constraint);


			sessionFactory.getCurrentSession().save(criterion);
			assertTrue(criterion.getId()>0);
			assertEquals(criterion.getInclusionConstraint().getId(), constraint.getId());
			sessionFactory.getCurrentSession().save(temp.get(0));
			sessionFactory.getCurrentSession().save(temp.get(1));
			assertTrue(temp.get(0).getId() > 0);
			assertTrue(temp.get(1).getId() > 0);
			criterion.setStrata(temp);
			sessionFactory.getCurrentSession().update(criterion);
			DateCriterion dbCriterion = (DateCriterion) sessionFactory.getCurrentSession().get(DateCriterion.class,criterion.getId());
			assertEquals(criterion, dbCriterion);
			assertEquals(criterion.getName(), dbCriterion.getName());
			assertEquals(criterion.getDescription(), dbCriterion.getDescription());
			assertEquals(constraint.getId(), dbCriterion.getInclusionConstraint().getId());
			assertEquals(DateConstraint.class, dbCriterion.getContstraintType());

		} catch (ContraintViolatedException e) {
			//fail();
		}
	}

}
