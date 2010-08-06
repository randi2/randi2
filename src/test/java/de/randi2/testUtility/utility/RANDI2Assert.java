package de.randi2.testUtility.utility;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.Arrays;
import java.util.List;

import de.randi2.model.AbstractDomainObject;
import static de.randi2.utility.IntegerIterator.upto;

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

	public static void assertOneOf(Object[] list, Object o){
		assertOneOf(Arrays.asList(list), o);
	}

	public static void assertOneOf(int list[], int i){
		for(int j: upto(list.length)){
			if(list[j]==i){
				return;
			}
		}
		fail(i + " not in list.");
	}
	
	public static void assertNoOneOf(int list[], int i){
		for(int j: upto(list.length)){
			if(list[j]==i){
				fail(i + " in list.");
			}
		}
		
	}

	public static void assertOneOf(List<Object> list, Object o){
		assertTrue(list.contains(o));
	}

	public static void assertAtLeast(int i, int j){
		assertTrue("Should be at least <"+i+"> but was <"+j+">",j >= i);
	}

	public static void assertAtMost(int i, int j){
		assertTrue("Should be at most <"+i+"> but was <"+j+">", j <= i);
	}
	
}
