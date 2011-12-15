/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2.core.unit.model.criteria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.testUtility.utility.AbstractDomainTest;
import de.randi2.unsorted.ConstraintViolatedException;

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
		criterion = new DichotomousCriterion();
		criterion.setOption1("a");
		criterion.setOption2("b");
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
		} catch (ConstraintViolatedException e) {
			fail(e.getMessage());
		}

		assertTrue(criterion.checkValue("Ja"));
		assertFalse(criterion.checkValue("Nein"));
		assertTrue(criterion.isInclusionCriterion());

		try {
			criterion.setInclusionConstraint(new DichotomousConstraint(Arrays.asList(new String[]{"SHIT"})));
		} catch (ConstraintViolatedException e) {
			assertNotNull(e);
		}

	}

	@Test
	public void testWithStratification() throws ConstraintViolatedException {
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
		} catch (ConstraintViolatedException e) {
		}

	}

	@Test
	public void testConfiguredValues(){
		criterion = new DichotomousCriterion();
		assertNull(criterion.getConfiguredValues());
		criterion.setOption1("Ja");
		criterion.setOption2("Nein");
		assertNotNull(criterion.getConfiguredValues());
		List<String> confValues = criterion.getConfiguredValues();
		confValues.contains("Ja");
		confValues.contains("Nein");
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
			constraintA = new DichotomousConstraint(Arrays.asList(new String[]{"a"}));
			constraintB = new DichotomousConstraint(Arrays.asList(new String[]{"b"}));
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
		ArrayList<DichotomousConstraint> list = new ArrayList<DichotomousConstraint>();
		criterion.setStrata(list);
		assertEquals(0,criterion.getStrata().size());
		criterion.addStrata(null);
		assertEquals(0,criterion.getStrata().size());
	}
	
	
	@Test
	public void testSetAndGetStrata(){
		ArrayList<DichotomousConstraint> list = new ArrayList<DichotomousConstraint>();
		assertNotSame(list, criterion.getStrata());
		criterion.setStrata(list);
		assertEquals(list, criterion.getStrata());
	}
	
	@Test
	public void testCheckValueCorrect(){
		assertTrue(criterion.checkValue("a"));
		assertTrue(criterion.checkValue("b"));
	}
	
	@Test
	public void testCheckValueIncorrect(){
		assertFalse(criterion.checkValue("x"));
	}
	
	
	@Test
	public void testGetConstraintType(){
		assertEquals(DichotomousConstraint.class, criterion.getContstraintType());
	}
	
		
	@Test
	public void testInclusionConstraintNull(){
		try {
			DichotomousConstraint constraint = new DichotomousConstraint(Arrays.asList(new String[]{"a"}));
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
			DichotomousConstraint constraint = new DichotomousConstraint(Arrays.asList(new String[]{"a"}));
			criterion.setInclusionConstraint(constraint);
			assertEquals(constraint, criterion.getInclusionConstraint());
		} catch (ConstraintViolatedException e) {
			fail();
		}
	}
	
	@Test
	public void testIsInclusionConstraintTrue(){
		try {
			DichotomousConstraint constraint = new DichotomousConstraint(Arrays.asList(new String[]{"a"}));
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
			constraintA = new DichotomousConstraint(Arrays.asList(new String[]{"a"}));
			constraintB = new DichotomousConstraint(Arrays.asList(new String[]{"b"}));
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
			constraintA = new DichotomousConstraint(Arrays.asList(new String[]{"a"}));
			constraintB = new DichotomousConstraint(Arrays.asList(new String[]{"b"}));
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