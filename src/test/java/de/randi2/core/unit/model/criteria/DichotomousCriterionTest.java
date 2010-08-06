/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2.core.unit.model.criteria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.testUtility.utility.AbstractDomainTest;
import de.randi2.unsorted.ContraintViolatedException;

/**
 *
 * @author jthoenes
 */

public class DichotomousCriterionTest extends AbstractDomainTest<DichotomousCriterion>{

	public DichotomousCriterionTest() {
		super(DichotomousCriterion.class);
	}

	private DichotomousCriterion criterion;
	

	@Before
	public void setUp()  {
		super.setUp();
		criterion = new DichotomousCriterion();
	}

	/**
	 * Test of getOption1 method, of class DichotomousCriterion.
	 */
	@Test
	public void testSimpleCase() {
		criterion.setOption1("Ja");
		criterion.setOption2("Nein");

		assertEquals(criterion.getOption1(), "Ja");
		assertEquals(criterion.getOption2(), "Nein");

		assertTrue(criterion.checkValue("Ja"));
		assertTrue(criterion.checkValue("Nein"));
		assertFalse(criterion.checkValue("Jap"));
		assertFalse(criterion.checkValue("Yes"));
		assertFalse(criterion.checkValue("Oui"));

		assertFalse(criterion.isInclusionCriterion());
	}

	@Test
	public void testWithInclusionCriterion() {
		criterion.setOption1("Ja");
		criterion.setOption2("Nein");

		try {
			criterion.setInclusionConstraint(new DichotomousConstraint(Arrays.asList(new String[]{"Ja"})));
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}

		assertTrue(criterion.checkValue("Ja"));
		assertFalse(criterion.checkValue("Nein"));
		assertTrue(criterion.isInclusionCriterion());

		try {
			criterion.setInclusionConstraint(new DichotomousConstraint(Arrays.asList(new String[]{"SHIT"})));
		} catch (ContraintViolatedException e) {
			assertNotNull(e);
		}

	}

	@Test
	public void testWithStratification() throws ContraintViolatedException {
		criterion.setOption1("Ja");
		criterion.setOption2("Nein");

		ArrayList<DichotomousConstraint> temp = new ArrayList<DichotomousConstraint>();
		temp.add(new DichotomousConstraint(Arrays.asList(new String[]{"Ja"})));
		temp.add(new DichotomousConstraint(Arrays.asList(new String[]{"Nein"})));

		criterion.setStrata(temp);

		assertEquals(temp.get(0), criterion.stratify("Ja"));
		assertEquals(temp.get(1), criterion.stratify("Nein"));

		try {
			criterion.stratify("LALALALA");
			fail("AGAIN -> WRONG!");
		} catch (ContraintViolatedException e) {
		}

	}

	@Test
	public void testConfiguredValues(){
		assertNull(criterion.getConfiguredValues());
		criterion.setOption1("Ja");
		criterion.setOption2("Nein");
		assertNotNull(criterion.getConfiguredValues());
		List<String> confValues = criterion.getConfiguredValues();
		confValues.contains("Ja");
		confValues.contains("Nein");
		
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


			sessionFactory.getCurrentSession().save(criterion);
			assertTrue(criterion.getId()>0);
			assertEquals(criterion.getInclusionConstraint().getId(), constraint.getId());
//			session.save(temp.get(0));
//			session.save(temp.get(1));
//			assertTrue(temp.get(0).getId() > 0);
//			assertTrue(temp.get(1).getId() > 0);
			criterion.setStrata(temp);
			sessionFactory.getCurrentSession().update(criterion);
			sessionFactory.getCurrentSession().flush();
			DichotomousCriterion dbCriterion = (DichotomousCriterion) sessionFactory.getCurrentSession().get(DichotomousCriterion.class,criterion.getId());
			assertEquals(criterion, dbCriterion);
			assertEquals(constraint.getId(), dbCriterion.getInclusionConstraint().getId());
			assertEquals(DichotomousConstraint.class, dbCriterion.getContstraintType());

		} catch (ContraintViolatedException e) {
			fail();
		}
	}
	
	@Test
	public void databaseIntegrationTestWithStata() {
		Session session = sessionFactory.openSession();
		criterion.setDescription("test");
		criterion.setOption1("Ja");
		criterion.setOption2("Nein");
		try {
			ArrayList<DichotomousConstraint> temp = new ArrayList<DichotomousConstraint>();
			temp.add(new DichotomousConstraint(Arrays.asList(new String[]{"Ja"})));
			temp.add(new DichotomousConstraint(Arrays.asList(new String[]{"Nein"})));
		
			DichotomousConstraint constraint = new DichotomousConstraint(Arrays.asList(new String[]{"Ja"}));
			session.save(constraint);
			assertTrue(constraint.getId()>0);
			criterion.setInclusionConstraint(constraint);

			session.save(criterion);
			assertTrue(criterion.getId()>0);
			assertEquals(criterion.getInclusionConstraint().getId(), constraint.getId());
			session.save(temp.get(0));
			session.save(temp.get(1));
			assertTrue(temp.get(0).getId() > 0);
			assertTrue(temp.get(1).getId() > 0);
			criterion.setStrata(temp);
			session.update(criterion);
			session.flush();
			session.close();
			session = sessionFactory.openSession();
			DichotomousCriterion dbCriterion = (DichotomousCriterion)session.get(DichotomousCriterion.class,criterion.getId());
			assertEquals(criterion, dbCriterion);
			assertEquals(constraint.getId(), dbCriterion.getInclusionConstraint().getId());
			assertEquals(DichotomousConstraint.class, dbCriterion.getContstraintType());
			assertEquals(2,dbCriterion.getStrata().size());

		} catch (ContraintViolatedException e) {
			fail();
		}
	}
}