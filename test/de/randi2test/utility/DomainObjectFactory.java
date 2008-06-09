package de.randi2test.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Center;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.PersonRole;
import de.randi2.model.Role;
import de.randi2.model.Trial;
import de.randi2.model.enumerations.Gender;
import de.randi2.utility.validations.Password;
import de.randi2.utility.validations.PasswordValidator;

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
	
	public Center getCenter(){
		Center c = new Center();
		c.setName(testStringUtil.getWithLength(10));
		c.setPassword(testStringUtil.getWithLength(Login.MAX_PASSWORD_LENGTH-2)+";2");
		c.setContactPerson(this.getPerson());
		return c;
	}

	public Trial getTrial() {
		Trial t = new Trial();
		t.setName(testStringUtil.getWithLength(10));
		return t;
	}
	
	public Login getLogin(){
		Login l = new Login();
		l.setUsername(testStringUtil.getWithLength(Login.MAX_USERNAME_LENGTH));
		l.setPassword(testStringUtil.getWithLength(Login.MIN_PASSWORD_LENGTH)+".ada6");
		l.setPerson(getPerson());
		
		return l;
	}

	public PersonRole getPersonRole() {
		PersonRole pr = new PersonRole();
		pr.setRole(this.getRole());
		pr.setPerson(this.getPerson());
		return pr;
	}

	public Role getRole() {
		Role r = new Role();
		return r;
	}
	
	
}
