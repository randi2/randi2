package de.randi2.model.criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.criteria.constraints.FreeTextConstraint;
import de.randi2.model.criteria.constraints.OrdinalConstraint;
import de.randi2.test.utility.AbstractDomainTest;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2.utility.Randi2Error;

import static junit.framework.Assert.*;

public class FreeTextCriterionTest extends AbstractDomainTest<FreeTextCriterion>{

	public FreeTextCriterionTest(){
		super(FreeTextCriterion.class);
	}
	private FreeTextCriterion criterion;
	
	@Before
	public void setUp() throws Exception {
		criterion = new FreeTextCriterion();
	}
	
	@Test
	public void testSimpleCase(){
		criterion.setDescription("test");
		assertTrue(criterion.checkValue("sdafasf"));
		assertTrue(criterion.checkValue("abcde"));
		assertFalse(criterion.checkValue(null));
		assertFalse(criterion.checkValue(""));
		
		assertFalse(criterion.isInclusionCriterion());
	}
	
	@Test
	public void testWithInclusionCriterion() {
		criterion.setDescription("test");
	
		try {
			criterion.setInclusionCriterion(new FreeTextConstraint(Arrays.asList(new String[]{"Value1"})));
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}

		assertTrue(criterion.checkValue("Value1"));
		assertFalse(criterion.checkValue("Value2"));
		assertFalse(criterion.checkValue("xyz"));
		assertTrue(criterion.isInclusionCriterion());

		try {
			criterion.setInclusionCriterion(new FreeTextConstraint(Arrays.asList(new String[]{"Test1", "Test2"})));
		} catch (ContraintViolatedException e) {
			assertNotNull(e);
		}

	}
	
	@Test
	public void testConfiguredValue(){
		assertNotNull(criterion.getConfiguredValues());
		assertTrue(criterion.getConfiguredValues().isEmpty());
	}
	
	@Test
	public void testWithStratification() throws ContraintViolatedException {
		criterion.setDescription("test");
		

		List<FreeTextConstraint> temp = new ArrayList<FreeTextConstraint>();
		temp.add(new FreeTextConstraint(Arrays.asList(new String[]{"Value1"})));
		temp.add(new FreeTextConstraint(Arrays.asList(new String[]{"Value2"})));

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
			hibernateTemplate.save(constraint);
			assertTrue(constraint.getId()>0);
			criterion.setInclusionCriterion(constraint);


			hibernateTemplate.save(criterion);
			assertTrue(criterion.getId()>0);
			assertEquals(criterion.getInclusionCriterion().getId(), constraint.getId());
			hibernateTemplate.save(temp.get(0));
			hibernateTemplate.save(temp.get(1));
			assertTrue(temp.get(0).getId() > 0);
			assertTrue(temp.get(1).getId() > 0);
			criterion.setStrata(temp);
			hibernateTemplate.update(criterion);
			FreeTextCriterion dbCriterion = (FreeTextCriterion) hibernateTemplate.get(FreeTextCriterion.class,criterion.getId());
			assertEquals(criterion, dbCriterion);
			assertEquals(criterion.getName(), dbCriterion.getName());
			assertEquals(criterion.getDescription(), dbCriterion.getDescription());
			assertEquals(constraint.getId(), dbCriterion.getInclusionCriterion().getId());
			assertEquals(FreeTextConstraint.class, dbCriterion.getContstraintType());

		} catch (ContraintViolatedException e) {
			//fail();
		}
	}
	
}
