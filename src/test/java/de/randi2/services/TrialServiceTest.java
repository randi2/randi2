package de.randi2.services;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import de.randi2.model.Login;
import de.randi2.model.Role;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.model.randomization.TruncatedBinomialDesignConfig;
import de.randi2.model.randomization.UrnDesignConfig;

public class TrialServiceTest extends AbstractServiceTest{

	@Autowired private TrialService service;
	@Autowired private UserService userService;

	private Trial validTrial;
	
	
	@Override
	public void setUp() {
		
		super.setUp();
		validTrial = factory.getTrial();
		validTrial.setLeadingSite(admin.getPerson().getTrialSite());
		validTrial.setSponsorInvestigator(admin.getPerson());
	}
	
	
	
	@Test
	public void testCreate(){
		service.create(validTrial);
		assertTrue(validTrial.getId()>0);
	}
	
	@Test
	public void testUpdate(){
		service.create(validTrial);
		assertTrue(validTrial.getId()>0);
		validTrial.setName("Trialname");
		service.update(validTrial);
		Trial dbTrial = (Trial) sessionFactory.getCurrentSession().get(Trial.class, validTrial.getId());
		assertNotNull(dbTrial);
		assertEquals(validTrial.getName(), dbTrial.getName());
		
	}
	
	@Test
	public void testGetAll(){
		List<Trial> trials = new ArrayList<Trial>();
		service.create(validTrial);
		trials.add(validTrial);
		for(int i=0;i<9;i++){
			Trial trial = factory.getTrial();
			trial.setLeadingSite(admin.getPerson().getTrialSite());
			trial.setSponsorInvestigator(admin.getPerson());
			service.create(trial);
		}
		List<Trial> dbTrials = service.getAll();
		assertTrue(dbTrials.size()>=10);
		assertTrue(dbTrials.containsAll(trials));
		
	}
	
	@Test
	public void testGetObject(){
		service.create(validTrial);
		assertTrue(validTrial.getId()>0);
		Trial dbTrial = service.getObject(validTrial.getId());
		assertNotNull(dbTrial);
		assertEquals(validTrial.getName(), dbTrial.getName());
		
	}
	
	@Test
	public void testRandomizeComplete(){
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setPlannedSubjects(50);
		arm1.setName("arm1");
		arm1.setTrial(validTrial);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setPlannedSubjects(50);
		arm2.setName("arm2");
		arm2.setTrial(validTrial);
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
	
		service.create(validTrial);
		validTrial.setTreatmentArms(arms);
		validTrial.setRandomizationConfiguration(new CompleteRandomizationConfig());
		service.update(validTrial);
		assertTrue(validTrial.getId()>0);
		assertEquals(2,validTrial.getTreatmentArms().size());
		for(int i=0;i<100;i++){
			TrialSubject subject = new TrialSubject();
			 subject.setIdentification("identification" + i);
			 subject.setTrialSite(validTrial.getLeadingSite());
			service.randomize(validTrial,subject );
		}
		
		Trial dbTrial = service.getObject(validTrial.getId());
		assertNotNull(dbTrial);
		assertEquals(validTrial.getName(), dbTrial.getName());
		assertEquals(2, dbTrial.getTreatmentArms().size());
		assertEquals(100, dbTrial.getTreatmentArms().get(0).getSubjects().size() + dbTrial.getTreatmentArms().get(1).getSubjects().size());
	}
	
	@Test
	public void testRandomizeBlock(){
		int blocksize = 4;
		int randomizations = 100;
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setPlannedSubjects(randomizations/2);
		arm1.setName("arm1");
		arm1.setTrial(validTrial);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setPlannedSubjects(randomizations/2);
		arm2.setName("arm2");
		arm2.setTrial(validTrial);
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
	
		service.create(validTrial);
		validTrial.setTreatmentArms(arms);
		BlockRandomizationConfig config =  new BlockRandomizationConfig();
		config.setMaximum(blocksize);
		config.setMinimum(blocksize);
		validTrial.setRandomizationConfiguration(config);
		service.update(validTrial);
		assertTrue(validTrial.getId()>0);
		assertEquals(2,validTrial.getTreatmentArms().size());
		for(int i=0;i<randomizations;i++){
			TrialSubject subject = new TrialSubject();
			 subject.setIdentification("identification" + i);
			 subject.setTrialSite(validTrial.getLeadingSite());
			service.randomize(validTrial,subject );
			if((i%blocksize)==(blocksize-1)){
			assertEquals(validTrial.getTreatmentArms().get(0).getSubjects().size() ,validTrial.getTreatmentArms().get(1).getSubjects().size());
			}
			
			int diff=validTrial.getTreatmentArms().get(0).getSubjects().size() -validTrial.getTreatmentArms().get(1).getSubjects().size();
			assertTrue((blocksize/2)>=diff && (-1)*(blocksize/2)<=diff);
		}
		
		Trial dbTrial = service.getObject(validTrial.getId());
		assertNotNull(dbTrial);
		assertEquals(validTrial.getName(), dbTrial.getName());
		assertEquals(2, dbTrial.getTreatmentArms().size());
		assertEquals(randomizations, dbTrial.getTreatmentArms().get(0).getSubjects().size() + dbTrial.getTreatmentArms().get(1).getSubjects().size());
		assertEquals(randomizations/2, dbTrial.getTreatmentArms().get(0).getSubjects().size());
		assertEquals(randomizations/2, dbTrial.getTreatmentArms().get(1).getSubjects().size());
	}
	
	
	@Test
	public void testRandomizeTruncated(){
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setPlannedSubjects(50);
		arm1.setName("arm1");
		arm1.setTrial(validTrial);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setPlannedSubjects(50);
		arm2.setName("arm2");
		arm2.setTrial(validTrial);
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
	
		service.create(validTrial);
		validTrial.setTreatmentArms(arms);
		validTrial.setRandomizationConfiguration(new TruncatedBinomialDesignConfig());
		service.update(validTrial);
		assertTrue(validTrial.getId()>0);
		assertEquals(2,validTrial.getTreatmentArms().size());
		for(int i=0;i<100;i++){
			TrialSubject subject = new TrialSubject();
			 subject.setIdentification("identification" + i);
			 subject.setTrialSite(validTrial.getLeadingSite());
			service.randomize(validTrial,subject );
		}
		
		Trial dbTrial = service.getObject(validTrial.getId());
		assertNotNull(dbTrial);
		assertEquals(validTrial.getName(), dbTrial.getName());
		assertEquals(2, dbTrial.getTreatmentArms().size());
		assertEquals(100, dbTrial.getTreatmentArms().get(0).getSubjects().size() + dbTrial.getTreatmentArms().get(1).getSubjects().size());
		assertEquals(50, dbTrial.getTreatmentArms().get(0).getSubjects().size());
		assertEquals(50, dbTrial.getTreatmentArms().get(1).getSubjects().size());
	}
	
	
	@Test
	public void testUrnRandomization(){
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setPlannedSubjects(50);
		arm1.setName("arm1");
		arm1.setTrial(validTrial);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setPlannedSubjects(50);
		arm2.setName("arm2");
		arm2.setTrial(validTrial);
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
	
		service.create(validTrial);
		validTrial.setTreatmentArms(arms);
		UrnDesignConfig conf = new UrnDesignConfig();
		conf.setInitializeCountBalls(4);
		conf.setCountReplacedBalls(2);
		validTrial.setRandomizationConfiguration(conf);
		
		service.update(validTrial);
		assertTrue(validTrial.getId()>0);
		assertEquals(2,validTrial.getTreatmentArms().size());
		for(int i=0;i<100;i++){
			TrialSubject subject = new TrialSubject();
			 subject.setIdentification("identification" + i);
			 subject.setTrialSite(validTrial.getLeadingSite());
			service.randomize(validTrial,subject );
		}
		
		Trial dbTrial = service.getObject(validTrial.getId());
		assertNotNull(dbTrial);
		assertEquals(validTrial.getName(), dbTrial.getName());
		assertEquals(2, dbTrial.getTreatmentArms().size());
		assertEquals(100, dbTrial.getTreatmentArms().get(0).getSubjects().size() + dbTrial.getTreatmentArms().get(1).getSubjects().size());
		assertTrue(dbTrial.getRandomizationConfiguration() instanceof UrnDesignConfig);
		assertTrue(((UrnDesignConfig)dbTrial.getRandomizationConfiguration()).getTempData() != null);
	}
	
	@Test
	public void testCreateAndRandomUrnDesign(){
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setPlannedSubjects(50);
		arm1.setName("arm1");
		arm1.setTrial(validTrial);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setPlannedSubjects(50);
		arm2.setName("arm2");
		arm2.setTrial(validTrial);
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
	
		service.create(validTrial);
		validTrial.setTreatmentArms(arms);
		UrnDesignConfig conf = new UrnDesignConfig();
		conf.setInitializeCountBalls(4);
		conf.setCountReplacedBalls(2);
		validTrial.setRandomizationConfiguration(conf);
		
		service.update(validTrial);
		assertTrue(validTrial.getId()>0);
		assertEquals(2,validTrial.getTreatmentArms().size());
		for(int i=0;i<100;i++){
			TrialSubject subject = new TrialSubject();
			 subject.setIdentification("identification" + i);
			 subject.setTrialSite(validTrial.getLeadingSite());
			service.randomize(validTrial,subject );
		}
		sessionFactory.getCurrentSession().clear();
		Trial dbTrial = service.getObject(validTrial.getId());
		assertNotNull(dbTrial);
		assertEquals(validTrial.getName(), dbTrial.getName());
		assertEquals(2, dbTrial.getTreatmentArms().size());
		assertEquals(100, dbTrial.getTreatmentArms().get(0).getSubjects().size() + dbTrial.getTreatmentArms().get(1).getSubjects().size());
		assertTrue(dbTrial.getRandomizationConfiguration() instanceof UrnDesignConfig);
		assertTrue(((UrnDesignConfig)dbTrial.getRandomizationConfiguration()).getTempData() != null);
	}
	
	@Test
	public void testGetSubjects(){
		/*
		 * Now creating another investigator
		 */
		//Login #1
		Login l = factory.getLogin();
		String e = "i@getsubjectstest.com";
		l.setUsername(e);
		l.getPerson().setEmail(e);
		l.addRole(Role.ROLE_INVESTIGATOR);
		l.getPerson().setTrialSite(admin.getPerson().getTrialSite());
		userService.create(l);
		//Login #2
		Login l2 = factory.getLogin();
		String e2 = "i2@getsubjectstest.com";
		l2.setUsername(e2);
		l2.getPerson().setEmail(e2);
		l2.addRole(Role.ROLE_INVESTIGATOR);
		l2.getPerson().setTrialSite(admin.getPerson().getTrialSite());
		userService.create(l2);
		/*
		 * First I need to create the trial and randomize some subjects.
		 */
		Trial t = factory.getTrial();
		t.setLeadingSite(admin.getPerson().getTrialSite());
		t.setSponsorInvestigator(admin.getPerson());
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setPlannedSubjects(25);
		arm1.setName("arm1");
		arm1.setTrial(t);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setPlannedSubjects(25);
		arm2.setName("arm2");
		arm2.setTrial(t);
		ArrayList<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
		service.create(t);
		t.setTreatmentArms(arms);
		l.setUsername(e);
		l.getPerson().setEmail(e);
		l.addRole(Role.ROLE_INVESTIGATOR);
		l.getPerson().setTrialSite(admin.getPerson().getTrialSite());
		userService.create(l);
		t.setRandomizationConfiguration(new CompleteRandomizationConfig());
		service.update(t);
		/*
		 * Checking if the trial saving and stuff went well. 
		 */
		assertTrue(t.getId()>0);
		assertEquals(2,t.getTreatmentArms().size());
		/*
		 * Randomizing the subjects using the service method.
		 */
		int expectedAmount = 50;
//		System.out.println("user: " + SecurityContextHolder.getContext().getAuthentication().getName() + " randomized in trial " +t.getName());
		for(int i=0;i<expectedAmount;i++){
			TrialSubject subject = new TrialSubject();
			subject.setIdentification("identification" + i);
			subject.setTrialSite(t.getLeadingSite());
			service.randomize(t,subject);
		}
		/*
		 * Trying to get the subjects for the admin user.
		 */
		List<TrialSubject> s = service.getSubjects(t,admin);
		assertNotNull(s);
		assertEquals(expectedAmount, s.size());
		//TODO The user creation should be done here! (lplotni)
		/*
		 * Signing in the newly created user.
		 */
		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				e, l, new ArrayList<GrantedAuthority>(l.getAuthorities()));
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);
		/*
		 * Randomizing another set of subjects
		 */
		int nextSet = 40;
//		System.out.println("user: " + SecurityContextHolder.getContext().getAuthentication().getName() + " randomized in trial " +t.getName());
		for(int i=0;i<nextSet;i++){
			TrialSubject subject = new TrialSubject();
			subject.setIdentification("anotherId" + i);
			subject.setTrialSite(t.getLeadingSite());
			service.randomize(t,subject);
		}
		/*
		 * Trying to get the second charge of the subjects.
		 */
		List<TrialSubject> s2 = service.getSubjects(t,l);
		assertNotNull(s2);
		assertEquals(nextSet, s2.size());
		/*
		 * Now testing some other scenarios. 
		 */
		List<TrialSubject> s3 = service.getSubjects(t, l2);
		assertNotNull(s3);
		assertEquals(0,s3.size());
	}
}
