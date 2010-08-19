/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.testUtility.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.context.ManagedSessionContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.dao.SystemWideSaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

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
import de.randi2.utility.BoxedException;
import de.randi2.utility.security.RolesAndRights;

/**
 * This class insert two user and two trial sites (name: Trial Site 1 and Trial
 * Site 2: password per trial site 1$heidelberg). One test trial (trial site
 * stratified block randomization)
 * 
 * User Trial Site 1:
 * <ul>
 * <li>role: Administrator</li>
 * <li>login: admin@trialsite1.de</li>
 * <li>password: 1$heidelberg</li>
 *</ul>
 * 
 * <ul>
 * <li>role: Principal Investigator</li>
 * <li>login: p_investigator@trialsite1.de</li>
 * <li>password: 1$heidelberg</li>
 *</ul>
 * 
 ** <ul>
 * <li>role: Investigator</li>
 * <li>login: investigator@trialsite1.de</li>
 * <li>password: 1$heidelberg</li>
 *</ul>
 * 
 * *
 * <ul>
 * <li>role: Statistican</li>
 * <li>login: statistican@trialsite1.de</li>
 * <li>password: 1$heidelberg</li>
 *</ul>
 * 
 * <ul>
 * <li>role: Monitor</li>
 * <li>login: monitor@trialsite1.de</li>
 * <li>password: 1$heidelberg</li>
 *</ul>
 * 
 * User Trial Site 2
 * 
 * <ul>
 * <li>role: Principal Investigator</li>
 * <li>login: p_investigator@trialsite2.de</li>
 * <li>password: 1$heidelberg</li>
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
	private ReflectionSaltSource saltSourceUser;long time1 = System.nanoTime();
	private SystemWideSaltSource saltSourceTrialSite;
	private TrialService trialService;
	private UserService userService;

	public Bootstrap() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath:/META-INF/spring-bootstrap.xml");
		loginDao = (LoginDaoHibernate) ctx.getBean("loginDao");
		rolesAndRights = (RolesAndRights) ctx.getBean("rolesAndRights");
		trialSiteDao = (TrialSiteDaoHibernate) ctx.getBean("trialSiteDao");
		sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
		passwordEncoder = (PasswordEncoder) ctx.getBean("passwordEncoder");
		saltSourceUser = (ReflectionSaltSource) ctx.getBean("saltSourceUser");
		saltSourceTrialSite = (SystemWideSaltSource) ctx
				.getBean("saltSourceTrialSite");
		trialService = (TrialService) ctx.getBean("trialService");
		userService = (UserService) ctx.getBean("userService");

		init();
	
	}

	public void init() {
		long time1 = System.nanoTime();
		ManagedSessionContext.bind(sessionFactory.openSession());
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_ADMIN);
		Role roleAdmin = (Role) sessionFactory.getCurrentSession().get(Role.class, 1L);
		roleAdmin.getRolesToAssign().add(roleAdmin);
		sessionFactory.getCurrentSession().saveOrUpdate(roleAdmin);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_INVESTIGATOR);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_USER);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_STATISTICAN);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_MONITOR);
		sessionFactory.getCurrentSession().saveOrUpdate(
				Role.ROLE_P_INVESTIGATOR);
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
		adminL.setUsername("admin@trialsite1.de");
		adminL.setPassword(passwordEncoder.encodePassword("1$heidelberg",
				saltSourceUser.getSalt(adminL)));
		adminL.setPerson(adminP);
		adminL.setPrefLocale(Locale.GERMANY);
		adminL.addRole(Role.ROLE_ADMIN);
		adminL.setPrefLocale(Locale.GERMAN);
		sessionFactory.getCurrentSession().persist(adminL);

		TrialSite trialSite = new TrialSite();
		trialSite.setCity("Heidelberg");
		trialSite.setCountry("Germany");
		trialSite.setName("Trial Site 1");
		trialSite.setPostcode("69120");
		trialSite.setStreet("INF");
		trialSite.setPassword(passwordEncoder.encodePassword("1$heidelberg",
				saltSourceTrialSite.getSystemWideSalt()));
		trialSite.setContactPerson(cp1);
		rolesAndRights.registerPerson(adminL);
		rolesAndRights.grantRights(adminL, trialSite);

		sessionFactory.getCurrentSession().persist(trialSite);

		sessionFactory.getCurrentSession().refresh(adminP);
		adminP.setTrialSite(trialSite);
		sessionFactory.getCurrentSession().update(adminP);
		rolesAndRights.grantRights(trialSite, trialSite);

		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"anonymousUser", adminL, new ArrayList<GrantedAuthority>(
						adminL.getAuthorities()));
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
		userLInv.setUsername("investigator@trialsite1.de");
		userLInv.setPassword(passwordEncoder.encodePassword("1$heidelberg",
				saltSourceUser.getSalt(userLInv)));
		userLInv.setPerson(userPInv);
		userLInv.setPrefLocale(Locale.GERMANY);
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
		userLPInv.setUsername("p_investigator@trialsite1.de");
		userLPInv.setPassword(passwordEncoder.encodePassword("1$heidelberg",
				saltSourceUser.getSalt(userLPInv)));
		userLPInv.setPerson(userPPInv);
		userLPInv.setPrefLocale(Locale.GERMANY);
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
		userL.setUsername("monitor@trialsite1.de");
		userL.setPassword(passwordEncoder.encodePassword("1$heidelberg",
				saltSourceUser.getSalt(userL)));
		userL.setPerson(userP);
		userL.setPrefLocale(Locale.GERMANY);
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
		userL.setUsername("statistican@trialsite1.de");
		userL.setPassword(passwordEncoder.encodePassword("1$heidelberg",
				saltSourceUser.getSalt(userL)));
		userL.setPerson(userP);
		userL.setPrefLocale(Locale.GERMANY);
		userL.addRole(Role.ROLE_STATISTICAN);
		userL.setPrefLocale(Locale.GERMAN);
		loginDao.create(userL);

		TrialSite trialSite1 = new TrialSite();
		trialSite1.setCity("Heidelberg");
		trialSite1.setCountry("Germany");
		trialSite1.setName("Trial Site 2");
		trialSite1.setPostcode("69120");
		trialSite1.setStreet("INF");
		trialSite1.setPassword(passwordEncoder.encodePassword("1$heidelberg",
				saltSourceTrialSite.getSystemWideSalt()));
		trialSite1.setContactPerson(cp2);

		trialSiteDao.create(trialSite1);
		sessionFactory.getCurrentSession().flush();
		sessionFactory.getCurrentSession().flush();

		// create test trial
		ManagedSessionContext.unbind(sessionFactory);
		ManagedSessionContext.bind(sessionFactory.openSession());

		trialSite1 = trialSiteDao.get(trialSite1.getId());
		Person userPInv2 = new Person();
		userPInv2.setFirstname("Max");
		userPInv2.setSurname("Investigator");
		userPInv2.setEmail("randi2@action.ms");
		userPInv2.setPhone("1234567");
		userPInv2.setSex(Gender.MALE);
		userPInv2.setTrialSite(trialSite1);

		Login userLInv2 = new Login();
		userLInv2.setUsername("investigator@trialsite2.de");
		userLInv2.setPassword(passwordEncoder.encodePassword("1$heidelberg",
				saltSourceUser.getSalt(userLInv2)));
		userLInv2.setPerson(userPInv2);
		userLInv2.setPrefLocale(Locale.GERMANY);
		userLInv2.addRole(Role.ROLE_INVESTIGATOR);
		userLInv2.setPrefLocale(Locale.GERMAN);
		userService.create(userLInv2);

		// create test trial
		sessionFactory.getCurrentSession().flush();

		System.out.println("create user: " + (System.nanoTime() - time1)
				/ 1000000 + " ms");
		time1 = System.nanoTime();
		// create test trial
		ManagedSessionContext.unbind(sessionFactory);
		ManagedSessionContext.bind(sessionFactory.openSession());
		authToken = new AnonymousAuthenticationToken("anonymousUser",
				userLPInv, new ArrayList<GrantedAuthority>(userLPInv.getAuthorities()));
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);

		TrialSite site = trialSiteDao.get(trialSite.getId());
		Trial trial = new Trial();
		trial.setAbbreviation("bs");
		trial.setName("Block study");
		trial
				.setDescription("Block study with two treatment arms and blocksize 8, stratified by trial site");
		trial.setGenerateIds(true);
		trial.setStratifyTrialSite(true);
		trial.setSponsorInvestigator(site.getMembersWithSpecifiedRole(
				Role.ROLE_P_INVESTIGATOR).get(0).getPerson());
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

		trial.setTreatmentArms(Arrays.asList(arm1, arm2));

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
			BoxedException.throwBoxed(e);
		}

		trialService.create(trial);

		sessionFactory.getCurrentSession().flush();
		System.out.println("create trial: " + (System.nanoTime() - time1)
				/ 1000000 + " ms");
		time1 = System.nanoTime();
		ManagedSessionContext.unbind(sessionFactory);
		ManagedSessionContext.bind(sessionFactory.openSession());

		int countTS1 = 120;
		int countTS2 = 60;
		int countMo = (new GregorianCalendar()).get(GregorianCalendar.MONTH);
		int countAll = 0;
		// Objects for the while-loop
		Random rand = new Random();
		GregorianCalendar date;
		int runs;
		boolean tr1;
		int count;
		// ---
		while (countTS1 != 0 || countTS2 != 0) {
			countAll++;
			date = new GregorianCalendar(2009, countAll % countMo, 1);
			runs = 0;
			tr1 = false;
			count = 0;
			if (rand.nextInt(2) == 0 && countTS1 != 0) {
				count = countTS1;
				tr1 = true;
			} else if (countTS2 != 0) {
				count = countTS2;
			}
			if (count >= 10) {
				runs = rand.nextInt(10) + 1;
			} else if (count != 0) {
				runs = rand.nextInt(count) + 1;
			}
			// Authorizing the investigator for upcoming randomization
			AnonymousAuthenticationToken at = tr1 ? new AnonymousAuthenticationToken(
					"anonymousUser", userLInv, new ArrayList<GrantedAuthority>(userLInv.getAuthorities()))
					: new AnonymousAuthenticationToken("anonymousUser",
							userLInv2, new ArrayList<GrantedAuthority>(userLInv2.getAuthorities()));
			SecurityContextHolder.getContext().setAuthentication(at);
			SecurityContextHolder.getContext().getAuthentication()
					.setAuthenticated(true);
			// ---
			for (int i = 0; i < runs; i++) {
				initRandBS(trial, date, rand);
				if (tr1) {
					countTS1--;
				} else {
					countTS2--;
				}
			}

		}
		System.out.println("added trial subjects: "
				+ (System.nanoTime() - time1) / 1000000 + " ms");
	}

	private void initRandBS(Trial trial, GregorianCalendar date, Random rand) {
		// long time1 = System.nanoTime();
		trial = trialService.getObject(trial.getId());

		TrialSubject subject = new TrialSubject();
		SubjectProperty<Serializable> subprob = new SubjectProperty<Serializable>(
				trial.getCriteria().get(0));
		SubjectProperty<Serializable> subprob1 = new SubjectProperty<Serializable>(
				trial.getCriteria().get(1));
		SubjectProperty<Serializable> subprob2 = new SubjectProperty<Serializable>(
				trial.getCriteria().get(2));
		try {
			if (rand.nextInt(2) == 0) {
				subprob.setValue(DichotomousCriterion.class.cast(
						trial.getCriteria().get(0)).getOption1());
			} else {
				subprob.setValue(DichotomousCriterion.class.cast(
						trial.getCriteria().get(0)).getOption2());
			}

			if (rand.nextInt(2) == 0) {
				subprob1.setValue(DichotomousCriterion.class.cast(
						trial.getCriteria().get(1)).getOption1());
			} else {
				subprob1.setValue(DichotomousCriterion.class.cast(
						trial.getCriteria().get(1)).getOption2());
			}
			if (rand.nextInt(2) == 0) {
				subprob2.setValue(DichotomousCriterion.class.cast(
						trial.getCriteria().get(2)).getOption1());
			} else {
				subprob2.setValue(DichotomousCriterion.class.cast(
						trial.getCriteria().get(2)).getOption2());
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
		// System.out.println("time random before: " +
		// (System.nanoTime()-time1)/1000000 + " ms");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Bootstrap();
	}

}
