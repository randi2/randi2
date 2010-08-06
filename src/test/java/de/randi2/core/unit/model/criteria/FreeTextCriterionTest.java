package de.randi2.core.unit.model.criteria;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.criteria.FreeTextCriterion;
import de.randi2.model.criteria.constraints.FreeTextConstraint;
import de.randi2.testUtility.utility.AbstractDomainTest;
import de.randi2.unsorted.ContraintViolatedException;

public class FreeTextCriterionTest extends AbstractDomainTest<FreeTextCriterion>{

	public FreeTextCriterionTest(){
		super(FreeTextCriterion.class);
	}
	private FreeTextCriterion criterion;
	
	@Before
	public void setUp(){
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
			criterion.setInclusionConstraint(new FreeTextConstraint(Arrays.asList(new String[]{"Value1"})));
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}

		assertTrue(criterion.checkValue("Value1"));
		assertFalse(criterion.checkValue("Value2"));
		assertFalse(criterion.checkValue("xyz"));
		assertTrue(criterion.isInclusionCriterion());

		try {
			criterion.setInclusionConstraint(new FreeTextConstraint(Arrays.asList(new String[]{"Test1", "Test2"})));
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
	
	
}
