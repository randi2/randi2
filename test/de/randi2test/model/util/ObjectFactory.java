package de.randi2test.model.util;

import org.springframework.beans.factory.annotation.Autowired;

import de.randi2.model.Center;
import de.randi2.model.Person;
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
	
	
}
