
package de.randi2.core.unit.model.randomization;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.randi2.model.randomization.ResponseAdaptiveRandomizationTempData;
import de.randi2.model.randomization.ResponseAdaptiveUrn;
 import de.randi2.testUtility.utility.AbstractDomainTest;


public class ResponseAdaptiveRandomizationTempDataTest extends AbstractDomainTest<ResponseAdaptiveRandomizationTempData> {
	
	public ResponseAdaptiveRandomizationTempDataTest(){
		super(ResponseAdaptiveRandomizationTempData.class);
	}
	
	@Test
	public void testSetAndGetStratum(){
		ResponseAdaptiveRandomizationTempData tempData = new ResponseAdaptiveRandomizationTempData();
		String[] strataIdentifier = new String[] { "a", "b", "c", "d" };
		ResponseAdaptiveUrn[] responseAdaptiveUrns = new ResponseAdaptiveUrn[] {
				new ResponseAdaptiveUrn(), new ResponseAdaptiveUrn(),
				new ResponseAdaptiveUrn(), new ResponseAdaptiveUrn() };
		for (int i = 0; i < responseAdaptiveUrns.length; i++) {
			tempData.setResponseAdaptiveUrn(strataIdentifier[i],
					responseAdaptiveUrns[i]);
			assertEquals(tempData.getResponseAdaptiveUrn(strataIdentifier[i]),
					responseAdaptiveUrns[i]);
		}
	}
}
