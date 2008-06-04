package de.randi2test.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import de.randi2.model.Center;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.PersonRole;
import de.randi2.model.Role;
import de.randi2.model.Trial;

public class DomainObjectFactory {

	@Autowired
	private TestStringUtil testStringUtil;
	
	@Autowired
	private ApplicationContext context;
	
	public Person getPerson(){
		Person p = new Person(); 
		p.setCenter(this.getCenter());
		return p;
	}
	
	public Center getCenter(){
		Center c = new Center();
		c.setName(testStringUtil.getWithLength(10));
		
		return c;
	}

	public Trial getTrial() {
		Trial t = new Trial();
		t.setName(testStringUtil.getWithLength(10));
		return t;
	}
	
	public Login getLogin(){
		Login l = new Login();
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