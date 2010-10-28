package de.randi2.core.unit.model.exceptions;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.exceptions.ValidationException;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring-test.xml"})
public class ValidationExceptionTest {

	private List<InvalidValue> invalidValues;
	private ValidationException e;
	
	@Before
	public void setUp(){
		invalidValues = new ArrayList<InvalidValue>();
		for(int i=0;i<10;i++){
			invalidValues.add(new InvalidValue("Invalid Value1",ValidationException.class,"property1","value1", "bean1"));
		}
		e = new ValidationException(invalidValues.toArray(new InvalidValue[0]));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetInvalids(){
		assertEquals(10,e.getInvalids().length);
		assertTrue(Arrays.asList(e.getInvalids()).containsAll(invalidValues));
		assertTrue(invalidValues.containsAll(Arrays.asList(e.getInvalids())));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetMessages(){
		assertEquals(10,e.getMessages().length);
		List<String> messages = new ArrayList<String>();
		for(InvalidValue value:invalidValues){
			messages.add(value.getMessage());
		}
		assertTrue(Arrays.asList(e.getMessages()).containsAll(messages));
		assertTrue(messages.containsAll(Arrays.asList(e.getMessages())));
	}
}
