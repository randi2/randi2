package de.randi2.core.unit.services;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.GregorianCalendar;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.dao.TrialDao;
import de.randi2.model.Login;
import de.randi2.model.Trial;
import de.randi2.model.enumerations.TrialStatus;
import de.randi2.model.exceptions.TrialStateException;
import de.randi2.services.TrialService;
import de.randi2.testUtility.utility.DomainObjectFactory;

/**
 * Test class for the TrialService logic.</br> See the <a
 * href="http://www.randi2.org/retro/wiki/TCU-10%20Verify%20TrialService">test
 * specification</a> for more information.
 * 
 * @author Lukasz Plotnicki
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/service-mock.xml" })
public class TrialServiceTest {

	@Autowired
	TrialService service;
	@Autowired
	TrialDao dao;
	@Autowired
	DomainObjectFactory factory;

	@Before
	public void setUp() {
		/*
		 * Sadly we need to assure that an authToken can be found in the
		 * SecurityContext as the services are working with it for logging
		 * purposes
		 */
		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"admin@trialsite1.de",
				new Login(),
				Arrays.asList(new GrantedAuthority[] { new GrantedAuthorityImpl(
						"someRole") }));
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);
		EasyMock.reset(dao);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateNull() throws IllegalArgumentException, TrialStateException {
		service.update(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateNew() throws IllegalArgumentException, TrialStateException {
		Trial t = new Trial();
		service.update(t);
	}

	@Test
	public void testUpdateInPreparationChangeEverything() throws IllegalArgumentException, TrialStateException {
		/*
		 * Changing everything beside state
		 */
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.IN_PREPARATION);
		Trial changedT = factory.getTrial();
		changedT.setId(1);
		changedT.setStatus(TrialStatus.IN_PREPARATION);
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.expect(dao.update(changedT)).andReturn(changedT);
		EasyMock.replay(dao);
		service.update(changedT);
		EasyMock.verify(dao);
	}

	@Test
	public void testUpdateInPreparationToActive() throws IllegalArgumentException, TrialStateException {
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.IN_PREPARATION);
		/*
		 * Changing state to ACTIVE
		 */
		Trial t2 = clone(t);
		t2.setStatus(TrialStatus.ACTIVE);
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.expect(dao.update(t2)).andReturn(t2);
		EasyMock.replay(dao);
		service.update(t2);
		EasyMock.verify(dao);
	}

	@Test(expected = TrialStateException.class)
	public void testUpdateInPreparationToFinished() throws IllegalArgumentException, TrialStateException {
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.IN_PREPARATION);
		/*
		 * Changing state to FINISHED
		 */
		Trial t2 = clone(t);
		t2.setStatus(TrialStatus.FINISHED);
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.replay(dao);
		service.update(t2);
	}

	@Test(expected = TrialStateException.class)
	public void testUpdateInPreparationToPaused() throws IllegalArgumentException, TrialStateException {
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.IN_PREPARATION);
		/*
		 * Changing state to FINISHED
		 */
		Trial t2 = clone(t);
		t2.setStatus(TrialStatus.PAUSED);
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.replay(dao);
		service.update(t2);
	}

	@Test(expected = TrialStateException.class)
	public void testUpdateActiveChangeEverything() throws IllegalArgumentException, TrialStateException {
		/*
		 * Changing everything beside state
		 */
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.ACTIVE);
		Trial changedT = factory.getTrial();
		changedT.setId(1);
		changedT.setStatus(TrialStatus.ACTIVE);
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.replay(dao);
		service.update(changedT);
	}

	@Test
	public void testUpdateActiveChangeProtocol() throws IllegalArgumentException, TrialStateException {
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.ACTIVE);
		/*
		 * Changing the protocol
		 */
		Trial t2 = clone(t);
		t2.setStatus(TrialStatus.ACTIVE);
		t2.setProtocol(new File("PATH"));
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.expect(dao.update(t2)).andReturn(t2);
		EasyMock.replay(dao);
		service.update(t2);
		EasyMock.verify(dao);
	}
	
	@Test
	public void testUpdateActiveChangeEndDate() throws IllegalArgumentException, TrialStateException {
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.ACTIVE);
		/*
		 * Changing the endDate
		 */
		Trial t2 = clone(t);
		t2.setStatus(TrialStatus.ACTIVE);
		t2.setEndDate(new GregorianCalendar());
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.expect(dao.update(t2)).andReturn(t2);
		EasyMock.replay(dao);
		service.update(t2);
		EasyMock.verify(dao);
	}
	
	@Test
	public void testUpdateActiveChangeDesc() throws IllegalArgumentException, TrialStateException {
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.ACTIVE);
		/*
		 * Changing the description
		 */
		Trial t2 = clone(t);
		t2.setStatus(TrialStatus.ACTIVE);
		t2.setDescription("Something different120839012830123803813812");
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.expect(dao.update(t2)).andReturn(t2);
		EasyMock.replay(dao);
		service.update(t2);
		EasyMock.verify(dao);
	}
	
	@Test
	public void testUpdateActiveToPaused() throws IllegalArgumentException, TrialStateException {
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.ACTIVE);
		/*
		 * Changing state to PAUSED
		 */
		Trial t2 = clone(t);
		t2.setStatus(TrialStatus.PAUSED);
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.expect(dao.update(t2)).andReturn(t2);
		EasyMock.replay(dao);
		service.update(t2);
		EasyMock.verify(dao);
	}
	
	@Test
	public void testUpdateActiveToFinished() throws IllegalArgumentException, TrialStateException {
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.ACTIVE);
		/*
		 * Changing state to FINISHED
		 */
		Trial t2 = clone(t);
		t2.setStatus(TrialStatus.FINISHED);
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.expect(dao.update(t2)).andReturn(t2);
		EasyMock.replay(dao);
		service.update(t2);
		EasyMock.verify(dao);
	}
	
	@Test(expected = TrialStateException.class)
	public void testUpdateActiveToInPreparation() throws IllegalArgumentException, TrialStateException {
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.ACTIVE);
		/*
		 * Changing state to IN PREPARATION
		 */
		Trial t2 = clone(t);
		t2.setStatus(TrialStatus.IN_PREPARATION);
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.replay(dao);
		service.update(t2);
	}

	@Test(expected = TrialStateException.class)
	public void testUpdatePausedChangeEverything() throws IllegalArgumentException, TrialStateException {
		/*
		 * Changing everything beside state
		 */
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.PAUSED);
		Trial changedT = factory.getTrial();
		changedT.setId(1);
		changedT.setStatus(TrialStatus.PAUSED);
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.replay(dao);
		service.update(changedT);
	}
	
	@Test
	public void testUpdatePausedChangeProtocol() throws IllegalArgumentException, TrialStateException {
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.PAUSED);
		/*
		 * Changing the protocol
		 */
		Trial t2 = clone(t);
		t2.setStatus(TrialStatus.PAUSED);
		t2.setProtocol(new File("PATH"));
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.expect(dao.update(t2)).andReturn(t2);
		EasyMock.replay(dao);
		service.update(t2);
		EasyMock.verify(dao);
	}
	
	@Test
	public void testUpdatePausedChangeEndDate() throws IllegalArgumentException, TrialStateException {
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.PAUSED);
		/*
		 * Changing the endDate
		 */
		Trial t2 = clone(t);
		t2.setStatus(TrialStatus.PAUSED);
		t2.setEndDate(new GregorianCalendar());
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.expect(dao.update(t2)).andReturn(t2);
		EasyMock.replay(dao);
		service.update(t2);
		EasyMock.verify(dao);
	}
	
	@Test
	public void testUpdatePausedChangeDesc() throws IllegalArgumentException, TrialStateException {
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.PAUSED);
		/*
		 * Changing the description
		 */
		Trial t2 = clone(t);
		t2.setStatus(TrialStatus.PAUSED);
		t2.setDescription("Something different120839012830123803813812");
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.expect(dao.update(t2)).andReturn(t2);
		EasyMock.replay(dao);
		service.update(t2);
		EasyMock.verify(dao);
	}
	
	@Test
	public void testUpdatePausedToActive() throws IllegalArgumentException, TrialStateException {
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.PAUSED);
		/*
		 * Changing state to ACTIVE
		 */
		Trial t2 = clone(t);
		t2.setStatus(TrialStatus.ACTIVE);
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.expect(dao.update(t2)).andReturn(t2);
		EasyMock.replay(dao);
		service.update(t2);
		EasyMock.verify(dao);
	}
	
	@Test
	public void testUpdatePausedToFinished() throws IllegalArgumentException, TrialStateException {
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.PAUSED);
		/*
		 * Changing state to FINISHED
		 */
		Trial t2 = clone(t);
		t2.setStatus(TrialStatus.FINISHED);
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.expect(dao.update(t2)).andReturn(t2);
		EasyMock.replay(dao);
		service.update(t2);
		EasyMock.verify(dao);
	}
	
	@Test(expected = TrialStateException.class)
	public void testUpdatePausedToInPreparation() throws IllegalArgumentException, TrialStateException {
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.PAUSED);
		/* 
		 * Changing state to IN PREPARATION
		 */
		Trial t2 = clone(t);
		t2.setStatus(TrialStatus.IN_PREPARATION);
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.replay(dao);
		service.update(t2);
	}
	
	@Test(expected = TrialStateException.class)
	public void testUpdateFinishedChangeEverything() throws IllegalArgumentException, TrialStateException {
		/*
		 * Changing everything beside state
		 */
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.FINISHED);
		Trial changedT = factory.getTrial();
		changedT.setId(1);
		changedT.setStatus(TrialStatus.FINISHED);
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.replay(dao);
		service.update(changedT);
	}
	
	@Test(expected = TrialStateException.class)
	public void testUpdateFinishedToOtherState() throws IllegalArgumentException, TrialStateException {
		Trial t = factory.getTrial();
		t.setId(1);
		t.setStatus(TrialStatus.FINISHED);
		/*
		 * Changing state to IN PREPARATION
		 */
		Trial t2 = clone(t);
		t2.setStatus(TrialStatus.ACTIVE);
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.replay(dao);
		service.update(t2);
		EasyMock.reset(dao);
		t2.setStatus(TrialStatus.PAUSED);
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.replay(dao);
		service.update(t2);
		EasyMock.reset(dao);
		t2.setStatus(TrialStatus.IN_PREPARATION);
		EasyMock.expect(dao.get(1)).andReturn(t);
		EasyMock.replay(dao);
		service.update(t2);
	}

	/**
	 * Util method for cloning objects
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T clone(T o) {
		T clone = null;

		try {
			clone = (T) o.getClass().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		// Walk up the superclass hierarchy
		for (Class obj = o.getClass(); !obj.equals(Object.class); obj = obj
				.getSuperclass()) {
			Field[] fields = obj.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				try {
					// for each class/suerclass, copy all fields
					// from this object to the clone
					fields[i].set(clone, fields[i].get(o));
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				}
			}
		}
		return clone;
	}

}
