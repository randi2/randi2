package de.randi2test.model.util;

import org.springframework.beans.factory.annotation.Autowired;

import de.randi2.model.Center;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.PersonRole;
import de.randi2.model.Role;
import de.randi2.model.Trial;
import de.randi2test.utility.TestStringUtil;

public class ObjectFactory {

	@Autowired
	private TestStringUtil testStringUtil;
	
	public de.randi2.model.Person getPerson(){
		Person p = new Person();
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
