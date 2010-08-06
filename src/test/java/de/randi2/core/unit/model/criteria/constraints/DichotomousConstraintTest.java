package de.randi2.core.unit.model.criteria.constraints;


import static junit.framework.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2.testUtility.utility.AbstractDomainTest;

public class DichotomousConstraintTest extends AbstractDomainTest<DichotomousConstraint> {

	private DichotomousConstraint constraint;
	private List<String> elements = new ArrayList<String>();
	
	public DichotomousConstraintTest(){
		super(DichotomousConstraint.class);
	}
	
	
	@Before
	public void setUp(){
		super.setUp();
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
	
	@Test
	@Transactional
	public void databaseIntegrationTest(){
		sessionFactory.getCurrentSession().persist(constraint);
		assertTrue(constraint.getId()>0);
		
		DichotomousConstraint dbConstraint = (DichotomousConstraint) sessionFactory.getCurrentSession().get(DichotomousConstraint.class, constraint.getId());
		assertEquals(constraint.getId(), dbConstraint.getId());
		assertEquals(constraint.getExpectedValue(), dbConstraint.getExpectedValue());
	}
}
