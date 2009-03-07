/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2test.model.criteria;

import de.randi2.model.criteria.*;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.unsorted.ContraintViolatedException;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jthoenes
 */
public class DichotomousCriterionTest {

	private DichotomousCriterion criterion;

	@Before
	public void setUp() throws Exception {
		criterion = new DichotomousCriterion();
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
			criterion.setInclusionCriterion(new DichotomousConstraint(Arrays.asList(new String[]{"Ja"})));
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}
		
		assertTrue(criterion.checkValue("Ja"));
		assertFalse(criterion.checkValue("Nein"));
		assertTrue(criterion.isInclusionCriterion());
		
		try {
			criterion.setInclusionCriterion(new DichotomousConstraint(Arrays.asList(new String[]{"SHIT"})));
		} catch (ContraintViolatedException e) {
			assertNotNull(e);
		}

	}
	
	@Test
	public void testWithStratification() throws ContraintViolatedException{
		criterion.setOption1("Ja");
		criterion.setOption2("Nein");
		
		ArrayList<DichotomousConstraint> temp = new ArrayList<DichotomousConstraint>();
		temp.add(new DichotomousConstraint(Arrays.asList(new String[]{"Ja"})));
		temp.add(new DichotomousConstraint(Arrays.asList(new String[]{"Nein"})));
		
		criterion.setStrata(temp);
		
		assertEquals(temp.get(0), criterion.stratify("Ja"));
		assertEquals(temp.get(1), criterion.stratify("Nein"));
		
		try{
			criterion.stratify("LALALALA");
			fail("AGAIN -> WRONG!");
		}catch(ContraintViolatedException e){
			
		}
		
	}
}