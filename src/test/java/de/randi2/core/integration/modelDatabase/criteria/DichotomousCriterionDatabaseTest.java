package de.randi2.core.integration.modelDatabase.criteria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.testUtility.utility.AbstractDomainDatabaseTest;
import de.randi2.unsorted.ContraintViolatedException;

public class DichotomousCriterionDatabaseTest extends AbstractDomainDatabaseTest<DichotomousCriterion>{

	public DichotomousCriterionDatabaseTest() {
		super(DichotomousCriterion.class);
	}

	private DichotomousCriterion criterion;
	

	@Before
	public void setUp()  {
		super.setUp();
		criterion = new DichotomousCriterion();
	}
	
	@Test
	@Transactional
	public void databaseIntegrationTest() {
		criterion.setDescription("test");
		criterion.setOption1("Ja");
		criterion.setOption2("Nein");
		try {
			ArrayList<DichotomousConstraint> temp = new ArrayList<DichotomousConstraint>();
			temp.add(new DichotomousConstraint(Arrays.asList(new String[]{"Ja"})));
			temp.add(new DichotomousConstraint(Arrays.asList(new String[]{"Nein"})));
		
			DichotomousConstraint constraint = new DichotomousConstraint(Arrays.asList(new String[]{"Ja"}));
//			session.save(constraint)
//			assertTrue(constraint.getId()>0);
			criterion.setInclusionConstraint(constraint);


			entityManager.persist(criterion);
			assertTrue(criterion.getId()>0);
			assertEquals(criterion.getInclusionConstraint().getId(), constraint.getId());
//			session.save(temp.get(0));
//			session.save(temp.get(1));
//			assertTrue(temp.get(0).getId() > 0);
//			assertTrue(temp.get(1).getId() > 0);
			criterion.setStrata(temp);
			entityManager.merge(criterion);
			entityManager.flush();
			entityManager.clear();
			DichotomousCriterion dbCriterion = entityManager.find(DichotomousCriterion.class,criterion.getId());
			assertEquals(criterion, dbCriterion);
			assertEquals(constraint.getId(), dbCriterion.getInclusionConstraint().getId());
			assertEquals(DichotomousConstraint.class, dbCriterion.getContstraintType());

		} catch (ContraintViolatedException e) {
			fail();
		}
	}
	
	@Test
	@Transactional
	public void databaseIntegrationTestWithStata() {
		criterion.setDescription("test");
		criterion.setOption1("Ja");
		criterion.setOption2("Nein");
		try {
			ArrayList<DichotomousConstraint> temp = new ArrayList<DichotomousConstraint>();
			temp.add(new DichotomousConstraint(Arrays.asList(new String[]{"Ja"})));
			temp.add(new DichotomousConstraint(Arrays.asList(new String[]{"Nein"})));
		
			DichotomousConstraint constraint = new DichotomousConstraint(Arrays.asList(new String[]{"Ja"}));
			criterion.setInclusionConstraint(constraint);

		    entityManager.persist(criterion);
			assertTrue(criterion.getId()>0);
			assertEquals(criterion.getInclusionConstraint().getId(), constraint.getId());
			entityManager.persist(temp.get(0));
			entityManager.persist(temp.get(1));
			assertTrue(temp.get(0).getId() > 0);
			assertTrue(temp.get(1).getId() > 0);
			criterion.setStrata(temp);
			entityManager.merge(criterion);
			entityManager.flush();
			entityManager.clear();
			DichotomousCriterion dbCriterion = entityManager.find(DichotomousCriterion.class,criterion.getId());
			assertEquals(criterion, dbCriterion);
			assertEquals(constraint.getId(), dbCriterion.getInclusionConstraint().getId());
			assertEquals(DichotomousConstraint.class, dbCriterion.getContstraintType());
			assertEquals(2,dbCriterion.getStrata().size());

		} catch (ContraintViolatedException e) {
			fail();
		}
	}

}
