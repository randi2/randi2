package de.randi2.core.integration.modelDatatbase.criteria.constraints;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.Arrays;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.criteria.constraints.DateConstraint;
import de.randi2.testUtility.utility.AbstractDomainDatabaseTest;
import de.randi2.unsorted.ContraintViolatedException;

public class DateConstaintDatabaseTest extends	AbstractDomainDatabaseTest<DateConstraint> {

	private DateConstraint constraint;
	private GregorianCalendar firstDate;
	private GregorianCalendar secondDate;
	
	public DateConstaintDatabaseTest(){
		super(DateConstraint.class);
	}
	
	@Before
	public void setUp(){
		super.setUp();
		firstDate = new GregorianCalendar(2001,10,10);
		secondDate =new GregorianCalendar(2002,10,22);
		try {
			constraint = new DateConstraint(Arrays.asList(new GregorianCalendar[]{firstDate,secondDate}));
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void databaseIntegrationTest(){
		sessionFactory.getCurrentSession().persist(constraint);
		assertTrue(constraint.getId()>0);
		
		DateConstraint dbConstraint = (DateConstraint) sessionFactory.getCurrentSession().get(DateConstraint.class, constraint.getId());
		assertEquals(constraint.getId(), dbConstraint.getId());
		assertEquals(constraint.getFirstDate(), dbConstraint.getFirstDate());
	}


}
