package de.randi2.core.integration.modelDatatbase.criteria;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.criteria.FreeTextCriterion;
import de.randi2.model.criteria.constraints.FreeTextConstraint;
import de.randi2.testUtility.utility.AbstractDomainDatabaseTest;
import de.randi2.unsorted.ContraintViolatedException;

public class FreeTextCriterionDatabaseTest extends AbstractDomainDatabaseTest<FreeTextCriterion>{

	public FreeTextCriterionDatabaseTest(){
		super(FreeTextCriterion.class);
	}
	private FreeTextCriterion criterion;
	
	@Before
	public void setUp(){
		super.setUp();
		criterion = new FreeTextCriterion();
	}
	
	@Test
	@Transactional
	public void databaseIntegrationTest() {
		criterion.setName("name");
		criterion.setDescription("test");
		List<String> elements = new ArrayList<String>();
		elements.add("Value1");
		elements.add("Value2");
		try {
			ArrayList<FreeTextConstraint> temp = new ArrayList<FreeTextConstraint>();
			temp.add(new FreeTextConstraint(Arrays.asList(new String[]{elements.get(0)})));
			temp.add(new FreeTextConstraint(Arrays.asList(new String[]{elements.get(1)})));
		
			FreeTextConstraint constraint = new FreeTextConstraint(Arrays.asList(elements.get(0)));
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
			FreeTextCriterion dbCriterion = (FreeTextCriterion) sessionFactory.getCurrentSession().get(FreeTextCriterion.class,criterion.getId());
			assertEquals(criterion, dbCriterion);
			assertEquals(criterion.getName(), dbCriterion.getName());
			assertEquals(criterion.getDescription(), dbCriterion.getDescription());
			assertEquals(constraint.getId(), dbCriterion.getInclusionConstraint().getId());
			assertEquals(FreeTextConstraint.class, dbCriterion.getContstraintType());

		} catch (ContraintViolatedException e) {
			fail();
		}
	}

}
