/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2.core.unit.model.criteria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.testUtility.utility.AbstractDomainTest;
import de.randi2.unsorted.ContraintViolatedException;

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
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}

		assertTrue(criterion.checkValue("Ja"));
		assertFalse(criterion.checkValue("Nein"));
		assertTrue(criterion.isInclusionCriterion());

		try {
			criterion.setInclusionConstraint(new DichotomousConstraint(Arrays.asList(new String[]{"SHIT"})));
		} catch (ContraintViolatedException e) {
			assertNotNull(e);
		}

	}

	@Test
	public void testWithStratification() throws ContraintViolatedException {
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
		} catch (ContraintViolatedException e) {
		}

	}

	@Test
	public void testConfiguredValues(){
		assertNull(criterion.getConfiguredValues());
		criterion.setOption1("Ja");
		criterion.setOption2("Nein");
		assertNotNull(criterion.getConfiguredValues());
		List<String> confValues = criterion.getConfiguredValues();
		confValues.contains("Ja");
		confValues.contains("Nein");
		
	}
	
}