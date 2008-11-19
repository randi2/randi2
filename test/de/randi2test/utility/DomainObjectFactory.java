package de.randi2test.utility;

import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.enumerations.Gender;

public class DomainObjectFactory {

	@Autowired
	private TestStringUtil testStringUtil;
	
	@Autowired
	private ApplicationContext context;
	
	public Person getPerson(){
		Person p = new Person(); 
		p.setSurname(testStringUtil.getWithLength(Person.MAX_NAME_LENGTH));
		p.setFirstname(testStringUtil.getWithLength(Person.MAX_NAME_LENGTH));
		p.setEMail("abc@def.xy");
		p.setGender(Gender.MALE);
		p.setPhone("01234/6789");
		return p;
	}
	
	public TrialSite getCenter(){
		TrialSite c = new TrialSite();
		c.setName(testStringUtil.getWithLength(10));
		c.setPassword(testStringUtil.getWithLength(Login.MAX_PASSWORD_LENGTH-2)+";2");
		c.setContactPerson(this.getPerson());
		return c;
	}

	public Trial getTrial() {
		Trial t = new Trial();
		t.setName(testStringUtil.getWithLength(10));
		t.setSponsorInvestigator(this.getPerson());
		t.setStartDate(new GregorianCalendar(2006,0,1));
		t.setEndDate(new GregorianCalendar());
		t.setLeadingSite(this.getCenter());
		
		return t;
	}
	
	public Login getLogin(){
		Login l = new Login();
		l.setUsername(testStringUtil.getWithLength(Login.MAX_USERNAME_LENGTH));
		l.setPassword(testStringUtil.getWithLength(Login.MIN_PASSWORD_LENGTH)+".ada6");
		l.setPerson(getPerson());
		l.setRegistrationDate(new GregorianCalendar(2006,0,1));
		l.setLastLoggedIn(new GregorianCalendar());
		
		return l;
	}

	
	
}
