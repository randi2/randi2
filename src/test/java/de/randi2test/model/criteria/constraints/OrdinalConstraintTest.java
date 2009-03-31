package de.randi2test.model.criteria.constraints;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.model.criteria.constraints.OrdinalConstraint;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2test.utility.AbstractDomainTest;

public class OrdinalConstraintTest extends
		AbstractDomainTest<OrdinalConstraint> {

	private OrdinalConstraint constraint;
	private List<String> elements = new ArrayList<String>();
	
	public OrdinalConstraintTest(){
		super(OrdinalConstraint.class);
	}
	
	@Before
	public void setUp() throws Exception {
		elements = new ArrayList<String>();
		elements.add("Value1");
		elements.add("Value2");
		elements.add("Value3");
		constraint = new OrdinalConstraint(elements);
	}
	
	@Test
	public void testConstructor(){
		List<String> elements = new ArrayList<String>();
		try {
			constraint = new OrdinalConstraint(elements);
			fail("the list of constraints should be not empty");
		} catch (ContraintViolatedException e) {}
		
		elements.add("Value1");
		try {
			constraint = new OrdinalConstraint(elements);
			assertTrue(constraint.getExpectedValues().contains("Value1"));
		} catch (ContraintViolatedException e) {
			fail("the list of constraints is ok");
		}
		elements.add("Value2");
		try {
			constraint = new OrdinalConstraint(elements);
			assertTrue(constraint.getExpectedValues().containsAll(elements));
			assertTrue(elements.containsAll(constraint.getExpectedValues()));
		} catch (ContraintViolatedException e) {
			fail("the list of constraints is ok");
		}
		
	}
	
	
	
	@Test
	public void testIsValueCorrect(){
		try {
			constraint.isValueCorrect(elements.get(0));
		} catch (ContraintViolatedException e) {
			fail("Value is correct");
		}
		try {
			constraint.isValueCorrect(elements.get(1));
		} catch (ContraintViolatedException e) {
			fail("Value is correct");
		}
		try {
			constraint.isValueCorrect(elements.get(2));
		} catch (ContraintViolatedException e) {
			fail("Value is correct");
		}
		try {
			constraint.isValueCorrect("ValueXYZ");
			fail("Value is not correct");
		} catch (ContraintViolatedException e) {		}
	}
	
	@Test
	public void testExpectedValues(){
		elements.add("Valu4");
		constraint.setExpectedValues(new HashSet<String>(elements));
		assertTrue(constraint.getExpectedValues().containsAll(elements));
		assertTrue(elements.containsAll(constraint.getExpectedValues()));
	}
	
	
	@Test
	public void testCheckValue(){
		assertTrue(constraint.checkValue(elements.get(0)));
		assertTrue(constraint.checkValue(elements.get(1)));
		assertTrue(constraint.checkValue(elements.get(2)));
		assertFalse(constraint.checkValue("ValueXYZ"));
		
	}
	
	@Test
	public void databaseIntegrationTest(){
		hibernateTemplate.persist(constraint);
		assertTrue(constraint.getId()>0);
		
		OrdinalConstraint dbConstraint = (OrdinalConstraint) hibernateTemplate.get(OrdinalConstraint.class, constraint.getId());
		assertEquals(constraint.getId(), dbConstraint.getId());
		assertEquals(constraint.getExpectedValues(), dbConstraint.getExpectedValues());
	}
}
