package de.randi2.core.unit.model.criteria.constraints;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.criteria.constraints.OrdinalConstraint;
import de.randi2.testUtility.utility.AbstractDomainTest;
import de.randi2.unsorted.ContraintViolatedException;

public class OrdinalConstraintTest extends
		AbstractDomainTest<OrdinalConstraint> {

	private OrdinalConstraint constraint;
	private List<String> elements = new ArrayList<String>();
	
	public OrdinalConstraintTest(){
		super(OrdinalConstraint.class);
	}
	
	@Before
	public void setUp() {
		elements = new ArrayList<String>();
		elements.add("Value1");
		elements.add("Value2");
		elements.add("Value3");
		try {
			constraint = new OrdinalConstraint(elements);
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testConstructorWithListEqualsNull(){
		try {
			constraint = new OrdinalConstraint(null);
			fail("the list of constraints should not be null");
		} catch (ContraintViolatedException e) {}
	}
	
	@Test
	public void testConstructorWithEmptyList(){
		List<String> elements = new ArrayList<String>();
		try {
			constraint = new OrdinalConstraint(elements);
			fail("the list of constraints should be not empty");
		} catch (ContraintViolatedException e) {}
	}
	
	
	@Test
	public void testConstructorWithListContainsMoreThanOneElement(){
		
		try {
			List<String> elements = new ArrayList<String>();
			for(int i =1 ; i<= 100; i++){
			elements.add(i+"");
				constraint = new OrdinalConstraint(elements);
				assertEquals(i, constraint.getExpectedValues().size());
				for(int j =1 ; j<=i;j++){
					assertTrue(constraint.getExpectedValues().contains(j+""));
				}
			}
		} catch (ContraintViolatedException e) {
			fail("the list of constraints is ok");
		}
	}
	
	
	@Test
	public void testConstructorWithListContainsOneNullElement(){
		List<String> elements = new ArrayList<String>();
		elements.add(null);
		try {
			constraint = new OrdinalConstraint(elements);
			fail("the list of constraints should not contain one element with the value null");
		} catch (ContraintViolatedException e) {}
	}
	
	@Test
	public void testConstructorWithListContainsOneCorrectElement(){
		List<String> elements = new ArrayList<String>();
		elements.add("a");
		try {
			constraint = new OrdinalConstraint(elements);
			assertEquals(1, constraint.getExpectedValues().size());
			assertEquals("a", constraint.getExpectedValues().iterator().next());
		} catch (ContraintViolatedException e) {
			fail("the list of constraints is ok");
		}
	}
	
	
	@Test
	public void testExpectedValue(){
		Set<String> expectedValues = new HashSet<String>();
		expectedValues.add("a");
		constraint.setExpectedValues(expectedValues);
		assertEquals(expectedValues, constraint.getExpectedValues());
	}
	
	
	@Test
	public void testIsValueCorrect_WithCorrectValue(){
		try {
			constraint.isValueCorrect(elements.get(0));
		} catch (ContraintViolatedException e) {
			fail("Value is correct");
		}
	}
	
	@Test
	public void testIsValueCorrect_WithIncorrectValue(){
		try {
			constraint.isValueCorrect("ValueXYZ");
			fail("Value is not correct");
		} catch (ContraintViolatedException e) {		}
	}
	
	@Test
	public void testUiName(){
		StringBuilder expectedValue = new StringBuilder();
		for(String s: elements){
			expectedValue.append(s + "|");
		}
		
		assertEquals(expectedValue.toString(), constraint.getUIName());
	}
	
	
	@Test
	public void testEqualsAndHashCode_SameObjects() throws ContraintViolatedException{
		OrdinalConstraint constraint1 = new OrdinalConstraint(elements);
		OrdinalConstraint constraint2 = constraint1;
		assertEquals(constraint1.hashCode(), constraint2.hashCode());
		assertTrue(constraint1.equals(constraint2));
	}
	
	@Test
	public void testEqualsAndHashCode_Null() throws ContraintViolatedException{
		OrdinalConstraint constraint1 = new OrdinalConstraint(elements);
		assertFalse(constraint1.equals(null));
	}
	
	@Test
	public void testEqualsAndHashCode_DifferentClasses() throws ContraintViolatedException{
		OrdinalConstraint constraint1 = new OrdinalConstraint(elements);
		String constraint2 = "";
		assertFalse(constraint1.equals(constraint2));
	}
	
	@Test
	public void testEqualsAndHashCode_ExpectedCorrectAndValueNull() throws ContraintViolatedException{
		OrdinalConstraint constraint1 = new OrdinalConstraint(elements);
		OrdinalConstraint constraint2 = new OrdinalConstraint(elements);
		Set<String> expectesValues1 = new HashSet<String>();
		Set<String> expectesValues2 = new HashSet<String>();
		expectesValues1.add("a");
		expectesValues2.add(null);
		constraint1.setExpectedValues(expectesValues1);
		constraint2.setExpectedValues(expectesValues2);
		assertFalse(constraint1.equals(constraint2));
	}
	
	@Test
	public void testEqualsAndHashCode_ExpectedNullAndValueNot() throws ContraintViolatedException{
		OrdinalConstraint constraint1 = new OrdinalConstraint(elements);
		OrdinalConstraint constraint2 = new OrdinalConstraint(elements);
		Set<String> expectesValues1 = new HashSet<String>();
		Set<String> expectesValues2 = new HashSet<String>();
		expectesValues1.add(null);
		expectesValues2.add("a");
		constraint1.setExpectedValues(expectesValues1);
		constraint2.setExpectedValues(expectesValues2);
		assertFalse(constraint1.equals(constraint2));
	}
	
	@Test
	public void testEqualsAndHashCode_ExpectedAndValueUnequal() throws ContraintViolatedException{
		OrdinalConstraint constraint1 = new OrdinalConstraint(elements);
		OrdinalConstraint constraint2 = new OrdinalConstraint(elements);
		constraint1.getExpectedValues().add("a");
		constraint2.getExpectedValues().add("b");
		assertFalse(constraint1.equals(constraint2));
	}
	
	@Test
	public void testEqualsAndHashCode_ExpectedAndValueEqual() throws ContraintViolatedException{
		OrdinalConstraint constraint1 = new OrdinalConstraint(elements);
		OrdinalConstraint constraint2 = new OrdinalConstraint(elements);
		String[] values = { stringUtil.getWithLength(254),
				stringUtil.getWithLength(2), "adsagsda dsf",
				stringUtil.getWithLength(132) };
		for (String s : values) {
			constraint1.getExpectedValues().add(s);
			constraint2.getExpectedValues().add(s);
			assertEquals(constraint1.hashCode(), constraint2.hashCode());
			assertTrue(constraint1.equals(constraint2));
		}
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
	
}
