package de.randi2.core.unit.model.criteria.constraints;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.criteria.constraints.DateConstraint;
import de.randi2.testUtility.utility.AbstractDomainTest;
import de.randi2.unsorted.ContraintViolatedException;

public class DateConstraintTest extends	AbstractDomainTest<DateConstraint> {

		private DateConstraint constraint;
		private GregorianCalendar firstDate;
		private GregorianCalendar secondDate;
		
		public DateConstraintTest(){
			super(DateConstraint.class);
		}
		
		@Before
		public void setUp(){
			firstDate = new GregorianCalendar(2001,10,10);
			secondDate =new GregorianCalendar(2002,10,22);
			try {
				constraint = new DateConstraint(Arrays.asList(new GregorianCalendar[]{firstDate,secondDate}));
			} catch (ContraintViolatedException e) {
				fail(e.getMessage());
			}
		}
		
		
		@Test
		public void testConstructorWithNull(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			try {
				constraint = new DateConstraint(elements);
				fail("the list of constraints should be not null");
			} catch (ContraintViolatedException e) {}
		}
		
		@Test
		public void testConstructorWithEmptyList(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			try {
				constraint = new DateConstraint(elements);
				fail("the list of constraints should be not empty");
			} catch (ContraintViolatedException e) {}
		}
		
		
		@Test
		public void testConstructorWithListContainsMoreThanTwoElements(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			elements.add(new GregorianCalendar());
			elements.add(new GregorianCalendar());
			elements.add(new GregorianCalendar());
			try {
				constraint = new DateConstraint(elements);
				fail("the list of constraints should not contain more than two elements");
			} catch (ContraintViolatedException e) {}
		}
		
		
		@Test
		public void testConstructorWithListContainsOneNullElement(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			elements.add(null);
			try {
				constraint = new DateConstraint(elements);
				fail("the list of constraints should not contain one element with the value null");
			} catch (ContraintViolatedException e) {}
		}
		
		@Test
		public void testConstructorWithListContainsOneCorrectElement(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			GregorianCalendar date = new GregorianCalendar();
			elements.add(date);
			try {
				constraint = new DateConstraint(elements);
				assertEquals(date, constraint.getFirstDate());
				assertNull(constraint.getSecondDate());
			} catch (ContraintViolatedException e) {
				fail("the list of constraints is ok");
			}
		}
		
		@Test
		public void testConstructorWithListContainsTwoNullElements(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			elements.add(null);
			elements.add(null);
			try {
				constraint = new DateConstraint(elements);
				fail("the list of constraints should not contain two elements with the value null");
			} catch (ContraintViolatedException e) {}
		}	
		
		@Test
		public void testConstructorWithListContainsTwoElements_CorrectValueAndNull(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			GregorianCalendar date = new GregorianCalendar();
			elements.add(date);
			elements.add(null);
			try {
				constraint = new DateConstraint(elements);
				assertEquals(date, constraint.getFirstDate());
				assertNull(constraint.getSecondDate());
			} catch (ContraintViolatedException e) {
				fail("the list of constraints is ok");
			}
		}	
		
		
		@Test
		public void testConstructorWithListContainsTwoElements_NullAndCorrectValue(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			GregorianCalendar date = new GregorianCalendar();
			elements.add(null);
			elements.add(date);
			try {
				constraint = new DateConstraint(elements);
				assertNull(constraint.getFirstDate());
				assertEquals(date, constraint.getSecondDate());
			} catch (ContraintViolatedException e) {
				fail("the list of constraints is ok");
			}
		}	
		
		@Test
		public void testConstructorWithListContainsTwoElements_TwoCorrectValues(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			GregorianCalendar date1 = new GregorianCalendar(2010,1,1);
			GregorianCalendar date2 = new GregorianCalendar(2011,1,1);
			elements.add(date1);
			elements.add(date2);
			try {
				constraint = new DateConstraint(elements);
				assertEquals(date1, constraint.getFirstDate());
				assertEquals(date2, constraint.getSecondDate());
			} catch (ContraintViolatedException e) {
				fail("the list of constraints is ok");
			}
		}	
		
		@Test
		public void testConstructorWithListContainsTwoElements_FirstDateAfterSecond(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			GregorianCalendar date1 = new GregorianCalendar(2011,1,1);
			GregorianCalendar date2 = new GregorianCalendar(2010,1,1);
			elements.add(date1);
			elements.add(date2);
			try {
				constraint = new DateConstraint(elements);
				fail("first date should be before the second one");
			} catch (ContraintViolatedException e) {
			}
		}	
		
		@Test
		public void testIsValueCorrectNull(){
			try {
				constraint.isValueCorrect(null);
				fail("Value is not correct"); 
			} catch (ContraintViolatedException e) {		}
		}
		
		@Test
		public void testIsValueCorrect_BetweenCorrect(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			GregorianCalendar date1 = new GregorianCalendar(2010,1,1);
			GregorianCalendar date2 = new GregorianCalendar(2011,1,1);
			elements.add(date1);
			elements.add(date2);
			try {
				constraint = new DateConstraint(elements);
			} catch (ContraintViolatedException e) {
				fail(e.getMessage()); 
			}
			try{
				constraint.isValueCorrect(new GregorianCalendar(2010,3,2));
				constraint.isValueCorrect(new GregorianCalendar(2010,5,2));
				constraint.isValueCorrect(date1);
				constraint.isValueCorrect(date2);
			}catch (ContraintViolatedException e) {
				fail("Value is correct"); 
			}
		}
		
		@Test
		public void testIsValueCorrect_BetweenIncorrect(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			GregorianCalendar date1 = new GregorianCalendar(2010,1,1);
			GregorianCalendar date2 = new GregorianCalendar(2011,1,1);
			elements.add(date1);
			elements.add(date2);
			try {
				constraint = new DateConstraint(elements);
			} catch (ContraintViolatedException e) {
				fail(e.getMessage()); 
			}
			try{
				constraint.isValueCorrect(new GregorianCalendar(2011,3,2));
				fail("Value is not correct"); 
			}catch (ContraintViolatedException e) {	}
			try{
				constraint.isValueCorrect(new GregorianCalendar(2001,3,2));
				fail("Value is not correct"); 
			}catch (ContraintViolatedException e) {	}
		}
		
		
		@Test
		public void testIsValueCorrect_OnlyFirstDate_CorrectValue(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			GregorianCalendar date = new GregorianCalendar(2010,1,1);
			elements.add(date);
			elements.add(null);
			try {
				constraint = new DateConstraint(elements);
			} catch (ContraintViolatedException e) {
				fail(e.getMessage()); 
			}
			try{
				constraint.isValueCorrect(new GregorianCalendar(2011,3,2));
			}catch (ContraintViolatedException e) {	
				fail("Value is correct");
				}
		}
		
		@Test
		public void testIsValueCorrect_OnlyFirstDate_IncorrectValue(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			GregorianCalendar date= new GregorianCalendar(2010,1,1);
			elements.add(date);
			elements.add(null);
			try {
				constraint = new DateConstraint(elements);
			} catch (ContraintViolatedException e) {
				fail(e.getMessage()); 
			}
			try{
				constraint.isValueCorrect(new GregorianCalendar(2009,3,2));
				fail("Value is not correct");
			}catch (ContraintViolatedException e) {	}
		}
		
		
		@Test
		public void testIsValueCorrect_OnlySecondDate_CorrectValue(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			GregorianCalendar date = new GregorianCalendar(2010,1,1);
			elements.add(null);
			elements.add(date);
			try {
				constraint = new DateConstraint(elements);
			} catch (ContraintViolatedException e) {
				fail(e.getMessage()); 
			}
			try{
				constraint.isValueCorrect(new GregorianCalendar(2001,3,2));
			}catch (ContraintViolatedException e) {	
				fail("Value is correct");
				}
		}
		
		@Test
		public void testIsValueCorrect_OnlySecondDate_IncorrectValue(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			GregorianCalendar date= new GregorianCalendar(2010,1,1);
			elements.add(null);
			elements.add(date);
			try {
				constraint = new DateConstraint(elements);
			} catch (ContraintViolatedException e) {
				fail(e.getMessage()); 
			}
			try{
				constraint.isValueCorrect(new GregorianCalendar(2011,3,2));
				fail("Value is not correct");
			}catch (ContraintViolatedException e) {	}
		}
		
		@Test
		public void testCheckValue(){
			assertTrue(constraint.checkValue(firstDate));
			assertFalse(constraint.checkValue(new GregorianCalendar(2003,14,12)));
		}
		
		@Test
		public void testUName_BothDates(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			GregorianCalendar date1 = new GregorianCalendar(2010,0,1);
			GregorianCalendar date2 = new GregorianCalendar(2011,0,1);
			elements.add(date1);
			elements.add(date2);
			try {
				constraint = new DateConstraint(elements);
			} catch (ContraintViolatedException e) {
				fail(e.getMessage()); 
			}
			
			assertEquals("01.01.2010 - 01.01.2011", constraint.getUIName());
		}
		
		@Test
		public void testUName_OnlyFirstDate(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			elements.add(new GregorianCalendar(2010,0,1));
			elements.add(null);
			try {
				constraint = new DateConstraint(elements);
			} catch (ContraintViolatedException e) {
				fail(e.getMessage()); 
			}
			
			assertEquals("> 01.01.2010", constraint.getUIName());
		}
		
		@Test
		public void testUName_OnlySecondDate(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			elements.add(null);
			elements.add(new GregorianCalendar(2010,0,1));
			try {
				constraint = new DateConstraint(elements);
			} catch (ContraintViolatedException e) {
				fail(e.getMessage()); 
			}
			
			assertEquals("< 01.01.2010", constraint.getUIName());
		}
		
		
		@Test
		public void testEqualsAndHashCode_SameObjects(){
			DateConstraint constraint1 = new DateConstraint();
			DateConstraint constraint2 = constraint1;
			assertEquals(constraint1.hashCode(), constraint2.hashCode());
			assertTrue(constraint1.equals(constraint2));
		}
		
		@Test
		public void testEqualsAndHashCode_Null(){
			DateConstraint constraint1 = new DateConstraint();
			DateConstraint constraint2 = null;
			assertFalse(constraint1.equals(constraint2));
		}
		
		@Test
		public void testEqualsAndHashCode_DifferentClasses(){
			DateConstraint constraint1 = new DateConstraint();
			String constraint2 = "";
			assertFalse(constraint1.equals(constraint2));
		}
		
		@Test
		public void testEqualsAndHashCode_FirstDateNull(){
			DateConstraint constraint1 = new DateConstraint();
			DateConstraint constraint2 = new DateConstraint();
			constraint1.setFirstDate(new GregorianCalendar(2010,0,1));
			constraint2.setFirstDate(null);
			assertFalse(constraint1.equals(constraint2));
		}
		
		@Test
		public void testEqualsAndHashCode_FirstDateUnequal(){
			DateConstraint constraint1 = new DateConstraint();
			DateConstraint constraint2 = new DateConstraint();
			constraint1.setFirstDate(new GregorianCalendar(2010,0,1));
			constraint2.setFirstDate(new GregorianCalendar(2010,1,1));
			assertFalse(constraint1.equals(constraint2));
		}
		
		@Test
		public void testEqualsAndHashCode_FirstDateEqual(){
			DateConstraint constraint1 = new DateConstraint();
			DateConstraint constraint2 = new DateConstraint();
			constraint1.setFirstDate(new GregorianCalendar(2010,0,1));
			constraint2.setFirstDate(new GregorianCalendar(2010,0,1));
			assertEquals(constraint1.hashCode(), constraint2.hashCode());
			assertTrue(constraint1.equals(constraint2));
		}
		
		
		@Test
		public void testEqualsAndHashCode_SecondDateNull(){
			DateConstraint constraint1 = new DateConstraint();
			DateConstraint constraint2 = new DateConstraint();
			constraint1.setSecondDate(new GregorianCalendar(2010,0,1));
			constraint2.setSecondDate(null);
			assertFalse(constraint1.equals(constraint2));
		}
		
		@Test
		public void testEqualsAndHashCode_SecondDateUnequal(){
			DateConstraint constraint1 = new DateConstraint();
			DateConstraint constraint2 = new DateConstraint();
			constraint1.setSecondDate(new GregorianCalendar(2010,0,1));
			constraint2.setSecondDate(new GregorianCalendar(2010,1,1));
			assertFalse(constraint1.equals(constraint2));
		}
		
		@Test
		public void testEqualsAndHashCode_SecondDateEqual(){
			DateConstraint constraint1 = new DateConstraint();
			DateConstraint constraint2 = new DateConstraint();
			constraint1.setSecondDate(new GregorianCalendar(2010,0,1));
			constraint2.setSecondDate(new GregorianCalendar(2010,0,1));
			assertEquals(constraint1.hashCode(), constraint2.hashCode());
			assertTrue(constraint1.equals(constraint2));
		}
		
		
		@Test
		public void testEqualsAndHashCode_BothDatesEqual(){
			DateConstraint constraint1 = new DateConstraint();
			DateConstraint constraint2 = new DateConstraint();
			constraint1.setFirstDate(new GregorianCalendar(2010,0,1));
			constraint2.setFirstDate(new GregorianCalendar(2010,0,1));
			constraint1.setSecondDate(new GregorianCalendar(2010,2,1));
			constraint2.setSecondDate(new GregorianCalendar(2010,2,1));
			assertEquals(constraint1.hashCode(), constraint2.hashCode());
			assertTrue(constraint1.equals(constraint2));
		}
		
}
