package de.randi2.core.unit.model.exceptions;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.engine.ConstraintViolationImpl;
import org.junit.Before;
import org.junit.Test;

import de.randi2.model.exceptions.ValidationException;
import java.util.Arrays;

public class ValidationExceptionTest {

	private Set<ConstraintViolation<?>> invalidValues;
	private ValidationException e;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp(){
		invalidValues = new HashSet<ConstraintViolation<?>>();
		for(int i=0;i<10;i++){
			invalidValues.add(new ConstraintViolationImpl("Invalid Value" + i,"Invalid Value" + i, ValidationException.class,"property1",null, "value " + i, null, null, null));
		}
		e = new ValidationException(invalidValues);
		assertEquals(10, invalidValues.size());
	}
	
	@Test
	public void testGetInvalids(){
		assertEquals(10,e.getInvalids().size());
		assertTrue(e.getInvalids().containsAll(invalidValues));
		assertTrue(invalidValues.containsAll(e.getInvalids()));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetMessages(){
		assertEquals(10,e.getMessages().length);
		List<String> messages = new ArrayList<String>();
		for(ConstraintViolation<?> value:invalidValues){
			messages.add(value.getMessage());
		}
		assertTrue(Arrays.asList(e.getMessages()).containsAll(messages));
		assertTrue(messages.containsAll(Arrays.asList(e.getMessages())));
	}
}
