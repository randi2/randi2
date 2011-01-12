package de.randi2.core.unit.model.criteria;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.mchange.util.AssertException;

import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.FreeTextCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.model.criteria.constraints.FreeTextConstraint;
import de.randi2.testUtility.utility.AbstractDomainTest;
import de.randi2.unsorted.ContraintViolatedException;

public class AbstractCriterionTest extends AbstractDomainTest<AbstractCriterion>{

	private DichotomousCriterion validCriterion;
	
	public AbstractCriterionTest() {
		super(AbstractCriterion.class);
	}

	@Before
	public void setUp(){
		validCriterion = new DichotomousCriterion();
		validCriterion.setOption1("a");
		validCriterion.setOption2("b");
		assertValid(validCriterion);
	}
	
	@Test
	public void testDescriptionNull(){
		validCriterion.setDescription(null);
		assertEquals(null, validCriterion.getDescription());
		assertValid(validCriterion);
	}
	
	@Test
	public void testDescriptionEmpty(){
		validCriterion.setDescription("");
		assertEquals("", validCriterion.getDescription());
		assertValid(validCriterion);
	}
	
	
	@Test
	public void testDescriptionOther(){
		String[] validValues= {"A","Abcsdafasd", "Title" , stringUtil.getWithLength(265) };
		for(String s :validValues){
			validCriterion.setDescription(s);
			assertEquals(s, validCriterion.getDescription());
			assertValid(validCriterion);
		}
	}
	
	
	@Test
	public void testNameNull(){
		validCriterion.setName(null);
		assertEquals(null, validCriterion.getName());
		assertValid(validCriterion);
	}
	
	@Test
	public void testNameEmpty(){
		validCriterion.setName("");
		assertEquals("", validCriterion.getName());
		assertValid(validCriterion);
	}
	
	
	@Test
	public void testNameOther(){
		String[] validValues= {"A","Abcsdafasd", "Title" , stringUtil.getWithLength(265) };
		for(String s :validValues){
			validCriterion.setName(s);
			assertEquals(s, validCriterion.getName());
			assertValid(validCriterion);
		}
	}

	@Test
	public void testAddStrata(){
		AbstractConstraint<String> constraintA = null;
		AbstractConstraint<String> constraintB = null;
		try {
			constraintA = new DichotomousConstraint(Arrays.asList(new String[]{"a"}));
			constraintB = new DichotomousConstraint(Arrays.asList(new String[]{"b"}));
		} catch (ContraintViolatedException e) {
			fail();
		}
		validCriterion.addStrata(constraintA);
		assertEquals(1,validCriterion.getStrata().size());
		assertTrue(validCriterion.getStrata().contains(constraintA));
		validCriterion.addStrata(constraintB);
		assertEquals(2,validCriterion.getStrata().size());
		assertTrue(validCriterion.getStrata().contains(constraintA));
		assertTrue(validCriterion.getStrata().contains(constraintB));
		
	}
	
	
	@Test
	public void testAddStrataNull(){
		ArrayList<DichotomousConstraint> list = new ArrayList<DichotomousConstraint>();
		validCriterion.setStrata(list);
		assertEquals(0,validCriterion.getStrata().size());
		validCriterion.addStrata(null);
		assertEquals(0,validCriterion.getStrata().size());
	}
	
	
	@Test
	public void testSetAndGetStrata(){
		ArrayList<DichotomousConstraint> list = new ArrayList<DichotomousConstraint>();
		assertNotSame(list, validCriterion.getStrata());
		validCriterion.setStrata(list);
		assertEquals(list, validCriterion.getStrata());
	}
	
	@Test
	public void testCheckValueCorrect(){
		assertTrue(validCriterion.checkValue("a"));
		assertTrue(validCriterion.checkValue("b"));
	}
	
	@Test
	public void testCheckValueIncorrect(){
		assertFalse(validCriterion.checkValue("x"));
	}
	
	
	@Test
	public void testGetConstraintType(){
		assertEquals(DichotomousConstraint.class, validCriterion.getContstraintType());
	}
	
	@Test
	public void testgetConfiguredValues(){
		assertEquals(2, validCriterion.getConfiguredValues().size());
		assertTrue(validCriterion.getConfiguredValues().contains("a"));
		assertTrue(validCriterion.getConfiguredValues().contains("b"));
	}
	
	
	@Test
	public void testInclusionConstraintNull(){
		try {
			DichotomousConstraint constraint = new DichotomousConstraint(Arrays.asList(new String[]{"a"}));
			validCriterion.setInclusionConstraint(constraint);
			assertNotNull(validCriterion.getInclusionConstraint());
			validCriterion.setInclusionConstraint(null);
			assertNull(validCriterion.getInclusionConstraint());
		} catch (ContraintViolatedException e) {
			fail();
		}
	}
	
	@Test
	public void testInclusionConstraintOther(){
		try {
			DichotomousConstraint constraint = new DichotomousConstraint(Arrays.asList(new String[]{"a"}));
			validCriterion.setInclusionConstraint(constraint);
			assertEquals(constraint, validCriterion.getInclusionConstraint());
		} catch (ContraintViolatedException e) {
			fail();
		}
	}
	
	@Test
	public void testIsInclusionConstraintTrue(){
		try {
			DichotomousConstraint constraint = new DichotomousConstraint(Arrays.asList(new String[]{"a"}));
			validCriterion.setInclusionConstraint(constraint);
			assertTrue(validCriterion.isInclusionCriterion());
		} catch (ContraintViolatedException e) {
			fail();
		}
	}
	
	@Test
	public void testIsInclusionConstraintFalse(){
			try {
				validCriterion.setInclusionConstraint(null);
			} catch (ContraintViolatedException e) {
				fail();
			}
			assertFalse(validCriterion.isInclusionCriterion());
	}
	
	
	@Test
	public void testStratifyConstraintException(){
		AbstractConstraint<String> constraintA = null;
		AbstractConstraint<String> constraintB = null;
		try {
			constraintA = new DichotomousConstraint(Arrays.asList(new String[]{"a"}));
			constraintB = new DichotomousConstraint(Arrays.asList(new String[]{"b"}));
		} catch (ContraintViolatedException e) {
			fail();
		}
		validCriterion.addStrata(constraintA);
		validCriterion.addStrata(constraintB);
		try {
			 validCriterion.stratify("c");
			 fail();
		} catch (ContraintViolatedException e) {
		}
	}
	
	
	@Test
	public void testStratify(){
		AbstractConstraint<String> constraintA = null;
		AbstractConstraint<String> constraintB = null;
		try {
			constraintA = new DichotomousConstraint(Arrays.asList(new String[]{"a"}));
			constraintB = new DichotomousConstraint(Arrays.asList(new String[]{"b"}));
		} catch (ContraintViolatedException e) {
			fail();
		}
		validCriterion.addStrata(constraintA);
		validCriterion.addStrata(constraintB);
		try {
			assertEquals(constraintA, validCriterion.stratify("a"));
			assertEquals(constraintB, validCriterion.stratify("b"));
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}
	}
}
