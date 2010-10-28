package de.randi2.core.integration.modelDatatbase.criteria.constraints;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.criteria.constraints.FreeTextConstraint;
import de.randi2.testUtility.utility.AbstractDomainDatabaseTest;
import de.randi2.unsorted.ContraintViolatedException;

public class FreeTextConstraintDatabaseTest extends	AbstractDomainDatabaseTest<FreeTextConstraint> {

	private FreeTextConstraint constraint;
	private String element;
	
	public FreeTextConstraintDatabaseTest(){
		super(FreeTextConstraint.class);
	}
	
	@Before
	public void setUp(){
		super.setUp();
		element="value";
		try {
			constraint = new FreeTextConstraint(Arrays.asList(new String[]{element}));
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void databaseIntegrationTest(){
		sessionFactory.getCurrentSession().persist(constraint);
		assertTrue(constraint.getId()>0);
		
		FreeTextConstraint dbConstraint = (FreeTextConstraint) sessionFactory.getCurrentSession().get(FreeTextConstraint.class, constraint.getId());
		assertEquals(constraint.getId(), dbConstraint.getId());
		assertEquals(constraint.getExpectedValue(), dbConstraint.getExpectedValue());
	}

}
