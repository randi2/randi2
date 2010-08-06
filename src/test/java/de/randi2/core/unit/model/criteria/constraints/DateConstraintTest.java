package de.randi2.core.unit.model.criteria.constraints;

import static junit.framework.Assert.assertFalse;
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
		public void testConstructor(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			try {
				constraint = new DateConstraint(elements);
				fail("the list of constraints should be not empty");
			} catch (ContraintViolatedException e) {}
			
			elements.add(new GregorianCalendar(2000,10,10));
			try {
				constraint = new DateConstraint(elements);
				assertTrue(constraint.getFirstDate().equals(new GregorianCalendar(2000,10,10)));
			} catch (ContraintViolatedException e) {
				fail("the list of constraints is ok");
			}
			elements.add(new GregorianCalendar(2003,14,12));
			try {
				constraint = new DateConstraint(elements);
				assertTrue(constraint.getSecondDate().equals(new GregorianCalendar(2003,14,12)));
			} catch (ContraintViolatedException e) {
				fail("the list of constraints is ok");
			}
			elements.add(new GregorianCalendar(2005,14,12));
			try {
				constraint = new DateConstraint(elements);
				fail("the list of constraints has more than three objects");
			} catch (ContraintViolatedException e) {
			}
			try {
				constraint = new DateConstraint(null);
				fail("the list of constraints is null");
			} catch (ContraintViolatedException e) {
			}
			
		}
		
		
		
		@Test
		public void testIsValueCorrect(){
			try {
				constraint.isValueCorrect(firstDate);
			} catch (ContraintViolatedException e) {
				fail("Value is correct");
			}
			try {
				constraint.isValueCorrect(new GregorianCalendar(2003,14,12));
				fail("Value is not correct");
			} catch (ContraintViolatedException e) {		}
		}
		
		@Test
		public void testExpectedValues(){
			assertTrue(constraint.getFirstDate().equals(firstDate));
			GregorianCalendar test = new GregorianCalendar(2003,14,12);
			constraint.setFirstDate(test);
			assertTrue(constraint.getFirstDate().equals(test));
		}
		
		
		@Test
		public void testCheckValue(){
			assertTrue(constraint.checkValue(firstDate));
			assertFalse(constraint.checkValue(new GregorianCalendar(2003,14,12)));
			
		}
		
}
