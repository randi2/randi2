package de.randi2.core.integration.modelDatatbase.criteria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
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
	

	@Before
	public void setUp() {
		super.setUp();
		criterion = new OrdinalCriterion();
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
