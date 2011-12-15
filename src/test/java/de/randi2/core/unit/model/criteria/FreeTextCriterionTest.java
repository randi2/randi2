package de.randi2.core.unit.model.criteria;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.criteria.FreeTextCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.criteria.constraints.FreeTextConstraint;
import de.randi2.model.criteria.constraints.FreeTextConstraint;
import de.randi2.testUtility.utility.AbstractDomainTest;
import de.randi2.unsorted.ConstraintViolatedException;

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
		} catch (ConstraintViolatedException e) {
			fail(e.getMessage());
		}

		assertTrue(criterion.checkValue("Value1"));
		assertFalse(criterion.checkValue("Value2"));
		assertFalse(criterion.checkValue("xyz"));
		assertTrue(criterion.isInclusionCriterion());

		try {
			criterion.setInclusionConstraint(new FreeTextConstraint(Arrays.asList(new String[]{"Test1", "Test2"})));
		} catch (ConstraintViolatedException e) {
			assertNotNull(e);
		}

	}
	
	@Test
	public void testConfiguredValue(){
		assertNotNull(criterion.getConfiguredValues());
		assertTrue(criterion.getConfiguredValues().isEmpty());
	}
	
	@Test
	public void testWithStratification() throws ConstraintViolatedException {
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
		} catch (ConstraintViolatedException e) {
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
			constraintA = new FreeTextConstraint(Arrays.asList(new String[]{"a"}));
			constraintB = new FreeTextConstraint(Arrays.asList(new String[]{"b"}));
		} catch (ConstraintViolatedException e) {
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
		ArrayList<FreeTextConstraint> list = new ArrayList<FreeTextConstraint>();
		criterion.setStrata(list);
		assertEquals(0,criterion.getStrata().size());
		criterion.addStrata(null);
		assertEquals(0,criterion.getStrata().size());
	}
	
	
	@Test
	public void testSetAndGetStrata(){
		ArrayList<FreeTextConstraint> list = new ArrayList<FreeTextConstraint>();
		assertNotSame(list, criterion.getStrata());
		criterion.setStrata(list);
		assertEquals(list, criterion.getStrata());
	}
	
	@Test
	public void testCheckValueCorrect() throws ConstraintViolatedException{
		assertTrue(criterion.checkValue("asdafsadfasdf"));
		assertTrue(criterion.checkValue("b"));
		criterion.setInclusionConstraint(new FreeTextConstraint(Arrays.asList(new String[]{"a"})));
		assertTrue(criterion.checkValue("a"));
	}
	
	@Test
	public void testCheckValueIncorrect() throws ConstraintViolatedException{
		criterion.setInclusionConstraint(new FreeTextConstraint(Arrays.asList(new String[]{"a"})));
		assertFalse(criterion.checkValue("x"));
	}
	
	
	@Test
	public void testGetConstraintType(){
		assertEquals(FreeTextConstraint.class, criterion.getContstraintType());
	}
	
	
	@Test
	public void testInclusionConstraintNull(){
		try {
			FreeTextConstraint constraint = new FreeTextConstraint(Arrays.asList(new String[]{"a"}));
			criterion.setInclusionConstraint(constraint);
			assertNotNull(criterion.getInclusionConstraint());
			criterion.setInclusionConstraint(null);
			assertNull(criterion.getInclusionConstraint());
		} catch (ConstraintViolatedException e) {
			fail();
		}
	}
	
	@Test
	public void testInclusionConstraintOther(){
		try {
			FreeTextConstraint constraint = new FreeTextConstraint(Arrays.asList(new String[]{"a"}));
			criterion.setInclusionConstraint(constraint);
			assertEquals(constraint, criterion.getInclusionConstraint());
		} catch (ConstraintViolatedException e) {
			fail();
		}
	}
	
	@Test
	public void testIsInclusionConstraintTrue(){
		try {
			FreeTextConstraint constraint = new FreeTextConstraint(Arrays.asList(new String[]{"a"}));
			criterion.setInclusionConstraint(constraint);
			assertTrue(criterion.isInclusionCriterion());
		} catch (ConstraintViolatedException e) {
			fail();
		}
	}
	
	@Test
	public void testIsInclusionConstraintFalse(){
			try {
				criterion.setInclusionConstraint(null);
			} catch (ConstraintViolatedException e) {
				fail();
			}
			assertFalse(criterion.isInclusionCriterion());
	}
	
	
	@Test
	public void testStratifyConstraintException(){
		AbstractConstraint<String> constraintA = null;
		AbstractConstraint<String> constraintB = null;
		try {
			constraintA = new FreeTextConstraint(Arrays.asList(new String[]{"a"}));
			constraintB = new FreeTextConstraint(Arrays.asList(new String[]{"b"}));
		} catch (ConstraintViolatedException e) {
			fail();
		}
		criterion.addStrata(constraintA);
		criterion.addStrata(constraintB);
		try {
			 criterion.stratify("c");
			 fail();
		} catch (ConstraintViolatedException e) {
		}
	}
	
	
	@Test
	public void testStratify(){
		AbstractConstraint<String> constraintA = null;
		AbstractConstraint<String> constraintB = null;
		try {
			constraintA = new FreeTextConstraint(Arrays.asList(new String[]{"a"}));
			constraintB = new FreeTextConstraint(Arrays.asList(new String[]{"b"}));
		} catch (ConstraintViolatedException e) {
			fail();
		}
		criterion.addStrata(constraintA);
		criterion.addStrata(constraintB);
		try {
			assertEquals(constraintA, criterion.stratify("a"));
			assertEquals(constraintB, criterion.stratify("b"));
		} catch (ConstraintViolatedException e) {
			fail(e.getMessage());
		}
	}
	
	
}
