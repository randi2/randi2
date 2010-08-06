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
import de.randi2.unsorted.ContraintViolatedException;

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
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testConstructor(){
		List<String> elements = new ArrayList<String>();
		try {
			constraint = new DichotomousConstraint(elements);
			fail("the list of constraints should be not empty");
		} catch (ContraintViolatedException e) {}
		
		elements.add("Value1");
		try {
			constraint = new DichotomousConstraint(elements);
			assertEquals("Value1", constraint.getExpectedValue());
		} catch (ContraintViolatedException e) {
			fail("the list of constraints is ok");
		}
		elements.add("Value2");
		try {
			constraint = new DichotomousConstraint(elements);
			fail("the list of constraints is to long");
		} catch (ContraintViolatedException e) {}
		
	}
	
	@Test
	public void testExpectedValue(){
		assertEquals(elements.get(0), constraint.getExpectedValue());
		constraint.setExpectedValue("Value2");
		assertEquals("Value2", constraint.getExpectedValue());
	}
	
	@Test
	public void testIsValueCorrect(){
		try {
			constraint.isValueCorrect(elements.get(0));
		} catch (ContraintViolatedException e) {
			fail("Value is correct");
		}
		try {
			constraint.isValueCorrect("ValueXYZ");
			fail("Value is not correct");
		} catch (ContraintViolatedException e) {		}
	}
	
	@Test
	public void testCheckValue(){
		assertTrue(constraint.checkValue(elements.get(0)));
		assertFalse(constraint.checkValue("ValueXYZ"));
		
	}
	
}
