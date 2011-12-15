package de.randi2.core.unit.model.criteria.constraints;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.testUtility.utility.AbstractDomainTest;
import de.randi2.unsorted.ConstraintViolatedException;

public class DichotomousConstraintTest extends AbstractDomainTest<DichotomousConstraint> {

	private DichotomousConstraint constraint;
	private List<String> elements = new ArrayList<String>();
	
	public DichotomousConstraintTest(){
		super(DichotomousConstraint.class);
	}
	
	
	@Before
	public void setUp(){
		elements = new ArrayList<String>();
		elements.add("Value1");
		try {
			constraint = new DichotomousConstraint(elements);
		} catch (ConstraintViolatedException e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	public void testConstructorWithListEqualsNull(){
		try {
			constraint = new DichotomousConstraint(null);
			fail("the list of constraints should not be null");
		} catch (ConstraintViolatedException e) {}
	}
	
	@Test
	public void testConstructorWithEmptyList(){
		List<String> elements = new ArrayList<String>();
		try {
			constraint = new DichotomousConstraint(elements);
			fail("the list of constraints should be not empty");
		} catch (ConstraintViolatedException e) {}
	}
	
	
	@Test
	public void testConstructorWithListContainsMoreThanOneElement(){
		List<String> elements = new ArrayList<String>();
		elements.add("1");
		elements.add("2");
		try {
			constraint = new DichotomousConstraint(elements);
			fail("the list of constraints should not contain more than two elements");
		} catch (ConstraintViolatedException e) {}
	}
	
	
	@Test
	public void testConstructorWithListContainsOneNullElement(){
		List<String> elements = new ArrayList<String>();
		elements.add(null);
		try {
			constraint = new DichotomousConstraint(elements);
			fail("the list of constraints should not contain one element with the value null");
		} catch (ConstraintViolatedException e) {}
	}
	
	@Test
	public void testConstructorWithListContainsOneCorrectElement(){
		List<String> elements = new ArrayList<String>();
		elements.add("a");
		try {
			constraint = new DichotomousConstraint(elements);
			assertEquals("a", constraint.getExpectedValue());
		} catch (ConstraintViolatedException e) {
			fail("the list of constraints is ok");
		}
	}
	
	
	@Test
	public void testExpectedValue(){
		assertEquals(elements.get(0), constraint.getExpectedValue());
		constraint.setExpectedValue("Value2");
		assertEquals("Value2", constraint.getExpectedValue());
	}
	
	
	@Test
	public void testIsValueCorrect_WithCorrectValue(){
		try {
			constraint.isValueCorrect(elements.get(0));
		} catch (ConstraintViolatedException e) {
			fail("Value is correct");
		}
	}
	
	@Test
	public void testIsValueCorrect_WithIncorrectValue(){
		try {
			constraint.isValueCorrect("ValueXYZ");
			fail("Value is not correct");
		} catch (ConstraintViolatedException e) {		}
	}
	
	@Test
	public void testCheckValue(){
		assertTrue(constraint.checkValue(elements.get(0)));
		assertFalse(constraint.checkValue("ValueXYZ"));
		
	}
	
	@Test
	public void testUiName(){
		assertEquals(elements.get(0), constraint.getUIName());
	}
	
	
	@Test
	public void testEqualsAndHashCode_SameObjects() throws ConstraintViolatedException{
		DichotomousConstraint constraint1 = new DichotomousConstraint(elements);
		DichotomousConstraint constraint2 = constraint1;
		assertEquals(constraint1.hashCode(), constraint2.hashCode());
		assertTrue(constraint1.equals(constraint2));
	}
	
	@Test
	public void testEqualsAndHashCode_Null() throws ConstraintViolatedException{
		DichotomousConstraint constraint1 = new DichotomousConstraint(elements);
		assertFalse(constraint1.equals(null));
	}
	
	@Test
	public void testEqualsAndHashCode_DifferentClasses() throws ConstraintViolatedException{
		DichotomousConstraint constraint1 = new DichotomousConstraint(elements);
		String constraint2 = "";
		assertFalse(constraint1.equals(constraint2));
	}
	
	@Test
	public void testEqualsAndHashCode_ExpectedCorrectAndValueNull() throws ConstraintViolatedException{
		DichotomousConstraint constraint1 = new DichotomousConstraint(elements);
		DichotomousConstraint constraint2 = new DichotomousConstraint(elements);
		constraint1.setExpectedValue("a");
		constraint2.setExpectedValue(null);
		assertFalse(constraint1.equals(constraint2));
	}
	
	@Test
	public void testEqualsAndHashCode_ExpectedNullAndValueNot() throws ConstraintViolatedException{
		DichotomousConstraint constraint1 = new DichotomousConstraint(elements);
		DichotomousConstraint constraint2 = new DichotomousConstraint(elements);
		constraint1.setExpectedValue(null);
		constraint2.setExpectedValue("a");
		assertFalse(constraint1.equals(constraint2));
	}
	
	@Test
	public void testEqualsAndHashCode_ExpectedAndValueUnequal() throws ConstraintViolatedException{
		DichotomousConstraint constraint1 = new DichotomousConstraint(elements);
		DichotomousConstraint constraint2 = new DichotomousConstraint(elements);
		constraint1.setExpectedValue("a");
		constraint2.setExpectedValue("b");
		assertFalse(constraint1.equals(constraint2));
	}
	
	@Test
	public void testEqualsAndHashCode_ExpectedAndValueEqual() throws ConstraintViolatedException{
		DichotomousConstraint constraint1 = new DichotomousConstraint(elements);
		DichotomousConstraint constraint2 = new DichotomousConstraint(elements);
		String[] values = { stringUtil.getWithLength(254),
				stringUtil.getWithLength(2), "adsagsda dsf",
				stringUtil.getWithLength(132) };
		for (String s : values) {
			constraint1.setExpectedValue(s);
			constraint2.setExpectedValue(s);
			assertEquals(constraint1.hashCode(), constraint2.hashCode());
			assertTrue(constraint1.equals(constraint2));
		}
	}
		
}
