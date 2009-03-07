/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2test.model.criteria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.unsorted.ContraintViolatedException;

/**
 *
 * @author jthoenes
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring.xml" })
public class DichotomousCriterionTest {

	private DichotomousCriterion criterion;
	
	@Autowired
	private HibernateTemplate template;

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
	
	@Test
	public void databaseIntegrationTest(){
		criterion.setDescription("test");
		criterion.setOption1("Ja");
		criterion.setOption2("Nein");
		try{
		ArrayList<DichotomousConstraint> temp = new ArrayList<DichotomousConstraint>();
		temp.add(new DichotomousConstraint(Arrays.asList(new String[]{"Ja"})));
		temp.add(new DichotomousConstraint(Arrays.asList(new String[]{"Nein"})));
		
//		criterion.setStrata(temp);
		
		DichotomousConstraint constraint =new DichotomousConstraint(Arrays.asList(new String[]{"Ja"}));
		template.save(constraint);
		criterion.setInclusionCriterion(constraint);
		

//		
		template.save(criterion);
	
		template.save(temp.get(0));
		template.save(temp.get(1));
		assertTrue(temp.get(0).getId()>0);
		assertTrue(temp.get(1).getId()>0);
		criterion.setStrata(temp);
	
		template.update(criterion);
	
		}catch (Exception e) {
			fail();
		}
	}
}