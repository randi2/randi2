package de.randi2.core.integration.modelDatatbase.criteria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.criteria.OrdinalCriterion;
import de.randi2.model.criteria.constraints.OrdinalConstraint;
import de.randi2.testUtility.utility.AbstractDomainDatabaseTest;
import de.randi2.unsorted.ContraintViolatedException;

public class OrdinalCriterionDatabaseTest extends AbstractDomainDatabaseTest<OrdinalCriterion> {

	
	public OrdinalCriterionDatabaseTest(){
		super(OrdinalCriterion.class);
	}
   
	private OrdinalCriterion criterion;
	
	private EntityManager entityManager;
	
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	

	@Before
	public void setUp() {
		super.setUp();
		criterion = new OrdinalCriterion();
	}

	
	@Test
	@Transactional
	public void databaseIntegrationTestPlainOrdinal() {
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
			criterion.setInclusionConstraint(constraint);

			entityManager.persist(criterion);
			assertTrue(criterion.getId()>0);
			assertEquals(criterion.getInclusionConstraint().getId(), constraint.getId());
			entityManager.persist(temp.get(0));
			entityManager.persist(temp.get(1));
			assertTrue(temp.get(0).getId() > 0);
			assertTrue(temp.get(1).getId() > 0);
			criterion.setStrata(temp);
			criterion = entityManager.merge(criterion);
			entityManager.flush();
			entityManager.clear();
			OrdinalCriterion dbCriterion = entityManager.find(OrdinalCriterion.class,criterion.getId());
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
			entityManager.persist(constraint);
			assertTrue(constraint.getId()>0);
			criterion.setInclusionConstraint(constraint);

			
			ArrayList<OrdinalConstraint> tempS = new ArrayList<OrdinalConstraint>();
			tempS.add(new OrdinalConstraint(Arrays.asList(new String[]{elements.get(0), elements.get(1)})));
			tempS.add(new OrdinalConstraint(Arrays.asList(new String[]{elements.get(2),elements.get(3)})));
			entityManager.persist(tempS.get(0));
			entityManager.persist(tempS.get(1));
			
			criterion.setStrata(tempS);
			
			entityManager.persist(criterion);
			assertTrue(criterion.getId()>0);
			assertEquals(criterion.getInclusionConstraint().getId(), constraint.getId());
			assertEquals(2, criterion.getStrata().size());
			entityManager.persist(temp.get(0));
			entityManager.persist(temp.get(1));
			assertTrue(temp.get(0).getId() > 0);
			assertTrue(temp.get(1).getId() > 0);
			criterion.setStrata(temp);
			criterion = entityManager.merge(criterion);
			entityManager.flush();
			entityManager.clear();
			OrdinalCriterion dbCriterion = entityManager.find(OrdinalCriterion.class,criterion.getId());
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
