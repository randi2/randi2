package de.randi2.testUtility.utility;

import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;

import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.enumerations.Gender;

public class DomainObjectFactory {

	@Autowired
	private TestStringUtil testStringUtil;
	
	public Person getPerson(){
		Person p = new Person(); 
		p.setSurname(testStringUtil.getWithLength(Person.MAX_NAME_LENGTH));
		p.setFirstname(testStringUtil.getWithLength(Person.MAX_NAME_LENGTH));
		p.setEmail("abc@def.xy");
		p.setSex(Gender.MALE);
		p.setPhone("01234/6789");
		return p;
	}
	
	public Role getRole(){
		Role r = new Role(testStringUtil.getWithLength(20),
				false, true, true, true, false, false, true, true, true, true,
				false, true, false, true, true, false, true, false, true, false,
				true, true, false, false, false, true, false, false, null);
		return r;
	}
	
	public TrialSite getTrialSite(){
		TrialSite c = new TrialSite();
		c.setName(testStringUtil.getWithLength(10));
		c.setPassword(testStringUtil.getWithLength(Login.MAX_PASSWORD_LENGTH-2)+";2");
		c.setContactPerson(this.getPerson());
		return c;
	}

	public Trial getTrial() {
		Trial t = new Trial();
		t.setName(testStringUtil.getWithLength(10));
		t.setAbbreviation(testStringUtil.getWithLength(5));
		t.setSponsorInvestigator(this.getPerson());
		t.setStartDate(new GregorianCalendar(2006,0,1));
		t.setEndDate(new GregorianCalendar());
		t.setLeadingSite(this.getTrialSite());
		
		return t;
	}
	
	public Login getLogin(){
		Login l = new Login();
		l.setUsername(testStringUtil.getWithLength(Login.MIN_USERNAME_LENGTH)+ "@xyz.com");
		l.setPassword(testStringUtil.getWithLength(Login.MIN_PASSWORD_LENGTH)+".ada6");
		l.setPerson(getPerson());
		l.getPerson().setLogin(l);
		l.setLastLoggedIn(new GregorianCalendar());
		
		return l;
	}
	
	
}
