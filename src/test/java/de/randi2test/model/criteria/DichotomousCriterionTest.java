/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2test.model.criteria;

import de.randi2.model.criteria.*;
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

		criterion.defineConstraints(Arrays.asList(new String[]{"Ja"}));

		assertTrue(criterion.checkValue("Ja"));
		assertFalse(criterion.checkValue("Nein"));

		assertTrue(criterion.isInclusionCriterion());

	}
}