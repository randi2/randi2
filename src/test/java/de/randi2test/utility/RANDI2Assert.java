package de.randi2test.utility;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

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
	
}
