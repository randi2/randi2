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

import de.randi2.model.criteria.OrdinalCriterion;
import de.randi2.model.criteria.constraints.OrdinalConstraint;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2.testUtility.utility.AbstractDomainTest;

public class OrdinalCriterionTest extends AbstractDomainTest<OrdinalCriterion> {

	
	public OrdinalCriterionTest(){
		super(OrdinalCriterion.class);
	}
private OrdinalCriterion criterion;
	

	@Before
	public void setUp() {
		super.setUp();
		criterion = new OrdinalCriterion();
	}
	
	@Test
	public void testSimpleCase(){
		criterion.setDescription("test");
		List<String> elements = new ArrayList<String>();
		elements.add("Value1");
		elements.add("Value2");
		elements.add("Value3");
		elements.add("Value4");
		criterion.setElements(elements);
		assertEquals(elements, criterion.getElements());
		
		assertTrue(criterion.checkValue("Value1"));
		assertTrue(criterion.checkValue("Value2"));
		assertTrue(criterion.checkValue("Value3"));
		assertTrue(criterion.checkValue("Value4"));
		assertFalse(criterion.checkValue("blablub"));
		assertFalse(criterion.checkValue("xyz"));
		assertFalse(criterion.checkValue("test"));

		assertFalse(criterion.isInclusionCriterion());
	}
	
	@Test
	public void testWithInclusionCriterion() {
		criterion.setDescription("test");
		List<String> elements = new ArrayList<String>();
		elements.add("Value1");
		elements.add("Value2");
		elements.add("Value3");
		elements.add("Value4");
		criterion.setElements(elements);

		try {
			criterion.setInclusionConstraint(new OrdinalConstraint(Arrays.asList(new String[]{"Value1"})));
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}

		assertTrue(criterion.checkValue("Value1"));
		assertTrue(criterion.checkValue("Value2"));
		assertTrue(criterion.checkValue("Value3"));
		assertTrue(criterion.checkValue("Value4"));
		assertTrue(criterion.isInclusionCriterion());

		try {
			criterion.setInclusionConstraint(new OrdinalConstraint(Arrays.asList(new String[]{"SHIT"})));
		} catch (ContraintViolatedException e) {
			e.printStackTrace();
			assertNotNull(e);
		}

	}
	
	@Test
	public void testConfiguredValue(){
		assertNull(criterion.getConfiguredValues());
		List<String> elements = new ArrayList<String>();
		elements.add("Value1");
		elements.add("Value2");
		elements.add("Value3");
		elements.add("Value4");
		criterion.setElements(elements);
		
		assertNotNull(criterion.getConfiguredValues());
		assertTrue(criterion.getConfiguredValues().contains("Value1"));
		assertTrue(criterion.getConfiguredValues().contains("Value2"));
		assertTrue(criterion.getConfiguredValues().contains("Value3"));
		assertTrue(criterion.getConfiguredValues().contains("Value4"));
		assertFalse(criterion.getConfiguredValues().contains("ValueXYZ"));
	}
	
	@Test
	public void testWithStratification() throws ContraintViolatedException {
		criterion.setDescription("test");
		List<String> elements = new ArrayList<String>();
		elements.add("Value1");
		elements.add("Value2");
		elements.add("Value3");
		elements.add("Value4");
		criterion.setElements(elements);

		ArrayList<OrdinalConstraint> temp = new ArrayList<OrdinalConstraint>();
		temp.add(new OrdinalConstraint(Arrays.asList(new String[]{"Value1"})));
		temp.add(new OrdinalConstraint(Arrays.asList(new String[]{"Value2"})));

		criterion.setStrata(temp);

		assertEquals(temp.get(0), criterion.stratify("Value1"));
		assertEquals(temp.get(1), criterion.stratify("Value2"));

		try {
			criterion.stratify("LALALALA");
			fail("AGAIN -> WRONG!");
		} catch (ContraintViolatedException e) {
		}

	}
	
	@Test
	public void databaseIntegrationTestPlainOrdinal() {
		Session session = sessionFactory.openSession();
		criterion.setName("name");
		criterion.setDescription("test");
		List<String> elements = new ArrayList<String>();
		elements.add("Value1");
		elements.add("Value2");
		elements.add("Value3");
		elements.add("Value4");
		criterion.setElements(elements);
		try {
			ArrayList<OrdinalConstraint> temp = new ArrayList<OrdinalConstraint>();
			temp.add(new OrdinalConstraint(Arrays.asList(new String[]{elements.get(0)})));
			temp.add(new OrdinalConstraint(Arrays.asList(new String[]{elements.get(1)})));
		
			OrdinalConstraint constraint = new OrdinalConstraint(Arrays.asList(elements.get(0)));
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
			OrdinalCriterion dbCriterion = (OrdinalCriterion) session.get(OrdinalCriterion.class,criterion.getId());
			assertEquals(criterion, dbCriterion);
			assertEquals(criterion.getName(), dbCriterion.getName());
			assertEquals(criterion.getDescription(), dbCriterion.getDescription());
			assertEquals(constraint.getId(), dbCriterion.getInclusionConstraint().getId());
			assertEquals(OrdinalConstraint.class, dbCriterion.getContstraintType());
			assertEquals(4, dbCriterion.getElements().size());
			assertTrue(dbCriterion.getElements().containsAll(elements));
			assertTrue(elements.containsAll(dbCriterion.getElements()));

		} catch (ContraintViolatedException e) {
			//fail();
		}
	}
	
	@Test
	@Transactional
	public void databaseIntegrationTestWithConstraintsAndStrata() {
		criterion.setName("name");
		criterion.setDescription("test");
		List<String> elements = new ArrayList<String>();
		elements.add("Value1");
		elements.add("Value2");
		elements.add("Value3");
		elements.add("Value4");
		criterion.setElements(elements);
		try {
			ArrayList<OrdinalConstraint> temp = new ArrayList<OrdinalConstraint>();
			temp.add(new OrdinalConstraint(Arrays.asList(new String[]{elements.get(0)})));
			temp.add(new OrdinalConstraint(Arrays.asList(new String[]{elements.get(1)})));
		
			OrdinalConstraint constraint = new OrdinalConstraint(Arrays.asList(elements.get(0)));
			sessionFactory.getCurrentSession().save(constraint);
			assertTrue(constraint.getId()>0);
			criterion.setInclusionConstraint(constraint);

			
			ArrayList<OrdinalConstraint> tempS = new ArrayList<OrdinalConstraint>();
			tempS.add(new OrdinalConstraint(Arrays.asList(new String[]{elements.get(0), elements.get(1)})));
			tempS.add(new OrdinalConstraint(Arrays.asList(new String[]{elements.get(2),elements.get(3)})));
			sessionFactory.getCurrentSession().save(tempS.get(0));
			sessionFactory.getCurrentSession().save(tempS.get(1));
			
			criterion.setStrata(tempS);
			
			sessionFactory.getCurrentSession().save(criterion);
			assertTrue(criterion.getId()>0);
			assertEquals(criterion.getInclusionConstraint().getId(), constraint.getId());
			assertEquals(2, criterion.getStrata().size());
			sessionFactory.getCurrentSession().save(temp.get(0));
			sessionFactory.getCurrentSession().save(temp.get(1));
			assertTrue(temp.get(0).getId() > 0);
			assertTrue(temp.get(1).getId() > 0);
			criterion.setStrata(temp);
			sessionFactory.getCurrentSession().update(criterion);
			sessionFactory.getCurrentSession().flush();
			OrdinalCriterion dbCriterion = (OrdinalCriterion) sessionFactory.getCurrentSession().get(OrdinalCriterion.class,criterion.getId());
			assertEquals(criterion, dbCriterion);
			assertEquals(criterion.getName(), dbCriterion.getName());
			assertEquals(criterion.getDescription(), dbCriterion.getDescription());
			assertEquals(constraint.getId(), dbCriterion.getInclusionConstraint().getId());
			assertEquals(OrdinalConstraint.class, dbCriterion.getContstraintType());
			assertEquals(4, dbCriterion.getElements().size());
			assertTrue(dbCriterion.getElements().containsAll(elements));
			assertTrue(elements.containsAll(dbCriterion.getElements()));
			assertEquals(2, dbCriterion.getStrata().size());
		} catch (ContraintViolatedException e) {
			//fail();
		}
	}
}
