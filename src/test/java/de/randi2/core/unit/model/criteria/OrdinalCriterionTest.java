package de.randi2.core.unit.model.criteria;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.criteria.OrdinalCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.criteria.constraints.OrdinalConstraint;
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
		List<String> elements = new ArrayList<String>();
		elements.add("Value1");
		elements.add("Value2");
		elements.add("Value3");
		elements.add("Value4");
		criterion.setElements(elements);
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
			assertNotNull(e);
		}

	}
	
	@Test
	public void testConfiguredValue(){
		criterion = new OrdinalCriterion();
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
	public void testDescriptionNull(){
		criterion.setDescription(null);
		assertEquals(null, criterion.getDescription());
		assertValid(criterion);
	}
	
	@Test
	public void testDescriptionEmpty(){
		criterion.setDescription("");
		assertEquals("", criterion.getDescription());
		assertValid(criterion);
	}
	
	
	@Test
	public void testDescriptionOther(){
		String[] validValues= {"A","Abcsdafasd", "Title" , stringUtil.getWithLength(265) };
		for(String s :validValues){
			criterion.setDescription(s);
			assertEquals(s, criterion.getDescription());
			assertValid(criterion);
		}
	}
	
	
	@Test
	public void testNameNull(){
		criterion.setName(null);
		assertEquals(null, criterion.getName());
		assertValid(criterion);
	}
	
	@Test
	public void testNameEmpty(){
		criterion.setName("");
		assertEquals("", criterion.getName());
		assertValid(criterion);
	}
	
	
	@Test
	public void testNameOther(){
		String[] validValues= {"A","Abcsdafasd", "Title" , stringUtil.getWithLength(265) };
		for(String s :validValues){
			criterion.setName(s);
			assertEquals(s, criterion.getName());
			assertValid(criterion);
		}
	}

	@Test
	public void testAddStrata(){
		AbstractConstraint<String> constraintA = null;
		AbstractConstraint<String> constraintB = null;
		try {
			constraintA = new OrdinalConstraint(Arrays.asList(new String[]{"a"}));
			constraintB = new OrdinalConstraint(Arrays.asList(new String[]{"b"}));
		} catch (ContraintViolatedException e) {
			fail();
		}
		criterion.addStrata(constraintA);
		assertEquals(1,criterion.getStrata().size());
		assertTrue(criterion.getStrata().contains(constraintA));
		criterion.addStrata(constraintB);
		assertEquals(2,criterion.getStrata().size());
		assertTrue(criterion.getStrata().contains(constraintA));
		assertTrue(criterion.getStrata().contains(constraintB));
		
	}
	
	
	@Test
	public void testAddStrataNull(){
		ArrayList<OrdinalConstraint> list = new ArrayList<OrdinalConstraint>();
		criterion.setStrata(list);
		assertEquals(0,criterion.getStrata().size());
		criterion.addStrata(null);
		assertEquals(0,criterion.getStrata().size());
	}
	
	
	@Test
	public void testSetAndGetStrata(){
		ArrayList<OrdinalConstraint> list = new ArrayList<OrdinalConstraint>();
		assertNotSame(list, criterion.getStrata());
		criterion.setStrata(list);
		assertEquals(list, criterion.getStrata());
	}
	
	@Test
	public void testCheckValueCorrect(){
		assertTrue(criterion.checkValue("Value1"));
		assertTrue(criterion.checkValue("Value2"));
		assertTrue(criterion.checkValue("Value3"));
		assertTrue(criterion.checkValue("Value4"));
	}
	
	@Test
	public void testCheckValueIncorrect(){
		assertFalse(criterion.checkValue("x"));
	}
	
	
	@Test
	public void testGetConstraintType(){
		assertEquals(OrdinalConstraint.class, criterion.getContstraintType());
	}
	
	
	@Test
	public void testInclusionConstraintNull(){
		try {
			OrdinalConstraint constraint = new OrdinalConstraint(Arrays.asList(new String[]{"Value1"}));
			criterion.setInclusionConstraint(constraint);
			assertNotNull(criterion.getInclusionConstraint());
			criterion.setInclusionConstraint(null);
			assertNull(criterion.getInclusionConstraint());
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testInclusionConstraintOther(){
		try {
			OrdinalConstraint constraint = new OrdinalConstraint(Arrays.asList(new String[]{"Value1"}));
			criterion.setInclusionConstraint(constraint);
			assertEquals(constraint, criterion.getInclusionConstraint());
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testIsInclusionConstraintTrue(){
		try {
			OrdinalConstraint constraint = new OrdinalConstraint(Arrays.asList(new String[]{"Value1"}));
			criterion.setInclusionConstraint(constraint);
			assertTrue(criterion.isInclusionCriterion());
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testIsInclusionConstraintFalse(){
			try {
				criterion.setInclusionConstraint(null);
			} catch (ContraintViolatedException e) {
				fail(e.getMessage());
			}
			assertFalse(criterion.isInclusionCriterion());
	}
	
	
	@Test
	public void testStratifyConstraintException(){
		AbstractConstraint<String> constraintA = null;
		AbstractConstraint<String> constraintB = null;
		try {
			constraintA = new OrdinalConstraint(Arrays.asList(new String[]{"a"}));
			constraintB = new OrdinalConstraint(Arrays.asList(new String[]{"b"}));
		} catch (ContraintViolatedException e) {
			fail();
		}
		criterion.addStrata(constraintA);
		criterion.addStrata(constraintB);
		try {
			 criterion.stratify("c");
			 fail();
		} catch (ContraintViolatedException e) {
		}
	}
	
	
	@Test
	public void testStratify(){
		AbstractConstraint<String> constraintA = null;
		AbstractConstraint<String> constraintB = null;
		try {
			constraintA = new OrdinalConstraint(Arrays.asList(new String[]{"Value1"}));
			constraintB = new OrdinalConstraint(Arrays.asList(new String[]{"Value2"}));
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}
		criterion.addStrata(constraintA);
		criterion.addStrata(constraintB);
		try {
			assertEquals(constraintA, criterion.stratify("Value1"));
			assertEquals(constraintB, criterion.stratify("Value2"));
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}
	}
	
}
