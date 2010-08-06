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

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.criteria.OrdinalCriterion;
import de.randi2.model.criteria.constraints.OrdinalConstraint;
import de.randi2.testUtility.utility.AbstractDomainTest;
import de.randi2.unsorted.ContraintViolatedException;

public class OrdinalCriterionTest extends AbstractDomainTest<OrdinalCriterion> {

	
	public OrdinalCriterionTest(){
		super(OrdinalCriterion.class);
	}
private OrdinalCriterion criterion;
	

	@Before
	public void setUp() {
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
	
}
