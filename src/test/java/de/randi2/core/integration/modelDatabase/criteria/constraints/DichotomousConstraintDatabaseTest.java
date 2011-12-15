package de.randi2.core.integration.modelDatabase.criteria.constraints;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.testUtility.utility.AbstractDomainDatabaseTest;
import de.randi2.unsorted.ConstraintViolatedException;

public class DichotomousConstraintDatabaseTest  extends AbstractDomainDatabaseTest<DichotomousConstraint> {

	private DichotomousConstraint constraint;
	private List<String> elements = new ArrayList<String>();
	
	public DichotomousConstraintDatabaseTest(){
		super(DichotomousConstraint.class);
	}
	
	
	@Before
	public void setUp(){
		super.setUp();
		elements = new ArrayList<String>();
		elements.add("Value1");
		try {
			constraint = new DichotomousConstraint(elements);
		} catch (ConstraintViolatedException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void databaseIntegrationTest(){
		entityManager.persist(constraint);
		assertTrue(constraint.getId()>0);
		
		DichotomousConstraint dbConstraint = entityManager.find(DichotomousConstraint.class, constraint.getId());
		assertEquals(constraint.getId(), dbConstraint.getId());
		assertEquals(constraint.getExpectedValue(), dbConstraint.getExpectedValue());
	}

}
