package de.randi2test.utility;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import de.randi2.model.AbstractDomainObject;

public final class RANDI2Assert {

	private RANDI2Assert(){
		
	}
	
	public static void assertNotSaved(AbstractDomainObject o){
		assertEquals(AbstractDomainObject.NOT_YET_SAVED_ID, o.getId());
	}
	
	public static void assertSaved(AbstractDomainObject o){
		assertNotSame(AbstractDomainObject.NOT_YET_SAVED_ID, o.getId());
	}
	
	public static void assertInList(Object o, List<Object> list){
		assertTrue(o.toString() + " is not in " + list.toString(), list.contains(o));
	}
	
	public static void assertInList(Object o, Object[] list){
		assertInList(o, Arrays.asList(list));
	}

	public static void assertAtLeast(int i, int j){
		assertTrue("Should be at least <"+i+"> but was <"+j+">",j >= i);
	}

	public static void assertAtMost(int i, int j){
		assertTrue("Should be at most <"+i+"> but was <"+j+">", j <= i);
	}
	
}
