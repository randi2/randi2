package de.randi2.utility;

import java.io.Serializable;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.context.ManagedSessionContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;
import org.springframework.security.providers.dao.salt.SystemWideSaltSource;
import org.springframework.security.providers.encoding.PasswordEncoder;

import de.randi2.dao.LoginDaoHibernate;
import de.randi2.dao.TrialSiteDaoHibernate;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.model.SubjectProperty;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.TrialSubject;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.model.enumerations.Gender;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.BlockRandomizationConfig.TYPE;
import de.randi2.services.TrialService;
import de.randi2.services.UserService;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2.utility.security.RolesAndRights;

/**
 * This class insert two user and two trial sites (name: Trial Site 1 and Trial Site 2: password per trial site 1$heidelberg). 
 * One test trial (trial site stratified block randomization) 
 * 
 * User Trial Site 1:
 * <ul>
 * 	<li>role: Administrator</li>
 * 	<li>login: admin@trialsite1.de</li>
 * 	<li>password: 1$heidelberg</li>
 *</ul>
 *
 * <ul>
 * 	<li>role: Principal Investigator</li>
 * 	<li>login: p_investigator@trialsite1.de</li>
 * 	<li>password: 1$heidelberg</li>
 *</ul>
 *  
 ** <ul>
 * 	<li>role: Investigator</li>
 * 	<li>login: investigator@trialsite1.de</li>
 * 	<li>password: 1$heidelberg</li>
 *</ul> 
 * 
 * * <ul>
 * 	<li>role: Statistican</li>
 * 	<li>login: statistican@trialsite1.de</li>
 * 	<li>password: 1$heidelberg</li>
 *</ul>
 * 
 *  <ul>
 * 	<li>role: Monitor</li>
 * 	<li>login: monitor@trialsite1.de</li>
 * 	<li>password: 1$heidelberg</li>
 *</ul>
 * 
 * User Trial Site 2 
 *
 *  <ul>
 * 	<li>role: Principal Investigator</li>
 * 	<li>login: p_investigator@trialsite2.de</li>
 * 	<li>password: 1$heidelberg</li>
 *</ul>
 * 
 * @author Daniel Schrimpf <dschrimpf@users.sourceforge.net>
 * 
 */
public class Bootstrap {

	private RolesAndRights rolesAndRights;
	private LoginDaoHibernate loginDao;
	private TrialSiteDaoHibernate trialSiteDao;
	private SessionFactory sessionFactory;
	private PasswordEncoder passwordEncoder;
	private SystemWideSaltSource saltSource;
	private TrialService trialService;
	private UserService userService;
	
	
	public void init() {
		ManagedSessionContext.bind(sessionFactory.openSession());
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_ADMIN);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_INVESTIGATOR);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_USER);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_STATISTICAN);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_MONITOR);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_P_INVESTIGATOR);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_ANONYMOUS);
		Person cp1 = new Person();
		cp1.setFirstname("Contact");
		cp1.setSurname("Person");
		cp1.setEmail("randi2@action.ms");
		cp1.setPhone("1234567");
		cp1.setSex(Gender.MALE);
		
		Person cp2 = new Person();
		cp2.setFirstname("Contact");
		cp2.setSurname("Person");
		cp2.setEmail("randi2@action.ms");
		cp2.setPhone("1234567");
		cp2.setSex(Gender.MALE);
		
		
		
		Person adminP = new Person();
		adminP.setFirstname("Max");
		adminP.setSurname("Administrator");
		adminP.setEmail("randi2@action.ms");
		adminP.setPhone("1234567");
		adminP.setSex(Gender.MALE);

		Login adminL = new Login();
		adminL.setPassword(passwordEncoder.encodePassword("1$heidelberg",saltSource.getSystemWideSalt()));
		adminL.setPerson(adminP);
		adminL.setPrefLocale(Locale.GERMANY);
		adminL.setUsername("admin@trialsite1.de");
		adminL.addRole(Role.ROLE_ADMIN);
		adminL.setPrefLocale(Locale.GERMAN);
		sessionFactory.getCurrentSession().persist(adminL);

		TrialSite trialSite = new TrialSite();
		trialSite.setCity("Heidelberg");
		trialSite.setCountry("Germany");
		trialSite.setName("Trial Site 1");
		trialSite.setPostcode("69120");
		trialSite.setStreet("INF");
		trialSite.setPassword(passwordEncoder.encodePassword("1$heidelberg",saltSource.getSystemWideSalt()));
		trialSite.setContactPerson(cp1);
		rolesAndRights.registerPerson(adminL);
		rolesAndRights.grantRigths(adminL, trialSite);

		sessionFactory.getCurrentSession().persist(trialSite);

		sessionFactory.getCurrentSession().refresh(adminP);
		adminP.setTrialSite(trialSite);
		sessionFactory.getCurrentSession().update(adminP);
		rolesAndRights.grantRigths(trialSite, trialSite);

		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"anonymousUser", adminL, adminL.getAuthorities());
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);

		Person userPInv = new Person();
		userPInv.setFirstname("Maxi");
		userPInv.setSurname("Investigator");
		userPInv.setEmail("randi2@action.ms");
		userPInv.setPhone("1234567");
		userPInv.setTrialSite(trialSite);

		Login userLInv = new Login();
		userLInv.setPassword(passwordEncoder.encodePassword("1$heidelberg",saltSource.getSystemWideSalt()));
		userLInv.setPerson(userPInv);
		userLInv.setPrefLocale(Locale.GERMANY);
		userLInv.setUsername("investigator@trialsite1.de");
		userLInv.addRole(Role.ROLE_INVESTIGATOR);
		userLInv.setPrefLocale(Locale.GERMAN);
		loginDao.create(userLInv);
		
		
		Person userPPInv = new Person();
		userPPInv.setFirstname("Max");
		userPPInv.setSurname("PInvestigator");
		userPPInv.setEmail("randi2@action.ms");
		userPPInv.setPhone("1234567");
		userPPInv.setSex(Gender.MALE);
		userPPInv.setTrialSite(trialSite);

		Login userLPInv = new Login();
		userLPInv.setPassword(passwordEncoder.encodePassword("1$heidelberg",saltSource.getSystemWideSalt()));
		userLPInv.setPerson(userPPInv);
		userLPInv.setPrefLocale(Locale.GERMANY);
		userLPInv.setUsername("p_investigator@trialsite1.de");
		userLPInv.addRole(Role.ROLE_P_INVESTIGATOR);
		userLPInv.setPrefLocale(Locale.GERMAN);
		loginDao.create(userLPInv);
		

		Person userP = new Person();
		userP.setFirstname("Maxi");
		userP.setSurname("Monitor");
		userP.setEmail("randi2@action.ms");
		userP.setPhone("1234567");
		userP.setSex(Gender.FEMALE);
		userP.setTrialSite(trialSite);

		Login userL = new Login();
		userL.setPassword(passwordEncoder.encodePassword("1$heidelberg",saltSource.getSystemWideSalt()));
		userL.setPerson(userP);
		userL.setPrefLocale(Locale.GERMANY);
		userL.setUsername("monitor@trialsite1.de");
		userL.addRole(Role.ROLE_MONITOR);
		userL.setPrefLocale(Locale.GERMAN);
		loginDao.create(userL);
		
		 userP = new Person();
			userP.setFirstname("Max");
			userP.setSurname("Statistican");
			userP.setEmail("randi2@action.ms");
			userP.setPhone("1234567");
			userP.setSex(Gender.MALE);
			userP.setTrialSite(trialSite);

			 userL = new Login();
			userL.setPassword(passwordEncoder.encodePassword("1$heidelberg",saltSource.getSystemWideSalt()));
			userL.setPerson(userP);
			userL.setPrefLocale(Locale.GERMANY);
			userL.setUsername("statistican@trialsite1.de");
			userL.addRole(Role.ROLE_STATISTICAN);
			userL.setPrefLocale(Locale.GERMAN);
			loginDao.create(userL);

		TrialSite trialSite1 = new TrialSite();
		trialSite1.setCity("Heidelberg");
		trialSite1.setCountry("Germany");
		trialSite1.setName("Trial Site 2");
		trialSite1.setPostcode("69120");
		trialSite1.setStreet("INF");
		trialSite1.setPassword(passwordEncoder.encodePassword("1$heidelberg",saltSource.getSystemWideSalt()));
		trialSite1.setContactPerson(cp2);

		trialSiteDao.create(trialSite1);
		sessionFactory.getCurrentSession().flush();
		sessionFactory.getCurrentSession().flush();

		//create test trial
		ManagedSessionContext.unbind(sessionFactory);
		ManagedSessionContext.bind(sessionFactory.openSession());
		
		trialSite1= trialSiteDao.get(trialSite1.getId());
		Person userPInv2 = new Person();
		userPInv2.setFirstname("Max");
		userPInv2.setSurname("Investigator");
		userPInv2.setEmail("randi2@action.ms");
		userPInv2.setPhone("1234567");
		userPInv2.setSex(Gender.MALE);
		userPInv2.setTrialSite(trialSite1);

		Login userLInv2 = new Login();
		userLInv2.setPassword(passwordEncoder.encodePassword("1$heidelberg",saltSource.getSystemWideSalt()));
		userLInv2.setPerson(userPInv2);
		userLInv2.setPrefLocale(Locale.GERMANY);
		userLInv2.setUsername("investigator@trialsite2.de");
		userLInv2.addRole(Role.ROLE_INVESTIGATOR);
		userLInv2.setPrefLocale(Locale.GERMAN);
		userService.create(userLInv2);
		
		
		
	
		//create test trial
		sessionFactory.getCurrentSession().flush();

		//create test trial
		ManagedSessionContext.unbind(sessionFactory);
		ManagedSessionContext.bind(sessionFactory.openSession());
	authToken = new AnonymousAuthenticationToken(
				"anonymousUser", userLPInv, userLPInv.getAuthorities());
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);
		
		TrialSite site = trialSiteDao.get(trialSite.getId());
		Trial trial = new Trial();
		trial.setAbbreviation("bs");
		trial.setName("Block study");
		trial.setDescription("Block study with two treatment arms and blocksize 8, stratified by trial site");
		trial.setGenerateIds(true);
		trial.setStratifyTrialSite(true);
		trial.setSponsorInvestigator(site.getMembersWithSpecifiedRole(Role.ROLE_P_INVESTIGATOR).get(0).getPerson());
		trial.setLeadingSite(trialSite);
		trial.addParticipatingSite(trialSite);
		trial.addParticipatingSite(trialSite1);
		trial.setStartDate(new GregorianCalendar(2009, 0, 1));
		trial.setEndDate(new GregorianCalendar(2010, 11, 1));
		
		BlockRandomizationConfig randConf = new BlockRandomizationConfig();
		randConf.setMaximum(8);
		randConf.setMinimum(8);
		randConf.setType(TYPE.ABSOLUTE);
		
		trial.setRandomizationConfiguration(randConf);
		
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setDescription("First Treatment");
		arm1.setName("arm1");
		arm1.setPlannedSubjects(200);
		
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setDescription("Second Treatment");
		arm2.setName("arm2");
		arm2.setPlannedSubjects(200);
		
		trial.setTreatmentArms(Arrays.asList(arm1,arm2));
		
		DichotomousCriterion cr = new DichotomousCriterion();
		cr.setName("SEX");
		cr.setOption1("M");
		cr.setOption2("F");
		DichotomousCriterion cr1 = new DichotomousCriterion();
		cr1.setOption1("1");
		cr1.setOption2("2");
		cr1.setName("Tum.Status");
		DichotomousCriterion cr2 = new DichotomousCriterion();
		cr2.setOption1("1");
		cr2.setOption2("2");
		cr2.setName("Fit.Level");
		try {
			
			cr.addStrata(new DichotomousConstraint(Arrays
					.asList(new String[] { "M" })));

			cr.addStrata(new DichotomousConstraint(Arrays
					.asList(new String[] { "F" })));
		
			cr1.addStrata(new DichotomousConstraint(Arrays
					.asList(new String[] { "1" })));
			cr1.addStrata(new DichotomousConstraint(Arrays
					.asList(new String[] { "2" })));
			cr2.addStrata(new DichotomousConstraint(Arrays
					.asList(new String[] { "1" })));
			cr2.addStrata(new DichotomousConstraint(Arrays
					.asList(new String[] { "2" })));

			trial.addCriterion(cr);
			trial.addCriterion(cr1);
			trial.addCriterion(cr2);


		} catch (ContraintViolatedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		trialService.create(trial);

		sessionFactory.getCurrentSession().flush();
		

		ManagedSessionContext.unbind(sessionFactory);
		ManagedSessionContext.bind(sessionFactory.openSession());
		
	
		int countTS1 = 120;
		int countTS2 = 60;
		int countMo=(new GregorianCalendar()).get(GregorianCalendar.MONTH);
		int countAll =0;
		Random rand = new Random();
		while(countTS1 != 0 || countTS2 !=0){
			countAll++;
			GregorianCalendar date = new GregorianCalendar(2009, countAll%countMo, 1);
			int runs = 0;
			boolean tr1=false;
			int count =0;
			if(rand.nextInt(2)==0 && countTS1!=0){
				count=countTS1;
				tr1=true;
			}else if(countTS2!=0){
				count=countTS2;
			}
			if(count >=10){	runs = rand.nextInt(10)+1;
			}else if (count!=0){runs = rand.nextInt(count)+1;}
			for(int i =0; i <runs;i++){
				if(tr1){
					initRandBS(userLInv, trial, date,rand);
					countTS1--;
				}else{
					initRandBS(userLInv2, trial, date,rand);
					countTS2--;
				}
			}
			
		}
			
	}
	
	private void initRandBS(Login login, Trial trial, GregorianCalendar date, Random rand){
		//create test trial subjects	
//		login = userService.getObject(login.getId());
		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"anonymousUser", login, login.getAuthorities());
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);
		trial = trialService.getObject(trial.getId());
		
		TrialSubject subject = new TrialSubject();
		SubjectProperty<Serializable> subprob = new SubjectProperty<Serializable>(trial.getCriteria().get(0));
		SubjectProperty<Serializable> subprob1 = new SubjectProperty<Serializable>(trial.getCriteria().get(1));
		SubjectProperty<Serializable> subprob2 = new SubjectProperty<Serializable>(trial.getCriteria().get(2));
		try {
			if(rand.nextInt(2)==0){
				subprob.setValue(DichotomousCriterion.class.cast(trial.getCriteria().get(0)).getOption1());
			}else{
				subprob.setValue(DichotomousCriterion.class.cast(trial.getCriteria().get(0)).getOption2());
			}
			
			if(rand.nextInt(2)==0){
				subprob1.setValue(DichotomousCriterion.class.cast(trial.getCriteria().get(1)).getOption1());
			}else{
				subprob1.setValue(DichotomousCriterion.class.cast(trial.getCriteria().get(1)).getOption1());
			}
			if(rand.nextInt(2)==0){
				subprob2.setValue(DichotomousCriterion.class.cast(trial.getCriteria().get(2)).getOption1());
			}else{
				subprob2.setValue(DichotomousCriterion.class.cast(trial.getCriteria().get(2)).getOption1());
			}
		
		} catch (ContraintViolatedException e) {
			e.printStackTrace();
		}
		
		Set<SubjectProperty<?>> proberties = new HashSet<SubjectProperty<?>>();
		proberties.add(subprob);
		proberties.add(subprob1);
		proberties.add(subprob2);
		subject.setProperties(proberties);
		
		trialService.randomize(trial, subject);
		subject.setCreatedAt(date);
		sessionFactory.getCurrentSession().update(subject);
		sessionFactory.getCurrentSession().flush();
	}

	public Bootstrap() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath:/META-INF/spring.xml");
		loginDao = (LoginDaoHibernate) ctx.getBean("loginDAO");
		rolesAndRights = (RolesAndRights) ctx.getBean("rolesAndRights");
		trialSiteDao = (TrialSiteDaoHibernate) ctx.getBean("trialSiteDAO");
		sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
		passwordEncoder = (PasswordEncoder) ctx.getBean("passwordEncoder");
		saltSource = (SystemWideSaltSource) ctx.getBean("saltSource");
		trialService = (TrialService) ctx.getBean("trialService");
		userService = (UserService) ctx.getBean("userService");
		init();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Bootstrap();
	}

}
