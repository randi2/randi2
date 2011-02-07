package de.randi2.core.unit.model.randomization;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.randi2.model.randomization.Urn;
import de.randi2.model.randomization.UrnDesignTempData;
import de.randi2.testUtility.utility.AbstractDomainTest;

public class UrnDesignTempDataTest extends AbstractDomainTest<UrnDesignTempData> {

	public UrnDesignTempDataTest(){
		super(UrnDesignTempData.class);
	}
	
	@Test
	public void testSetAndGetStratum(){
		UrnDesignTempData tempData = new UrnDesignTempData();
		String[] strataIdentifier = new String[]{"a", "b", "c", "d"};
		Urn[] urns = new Urn[]{new Urn(), new Urn(), new Urn(),new Urn()};
		for(int i=0;i < urns.length; i++){
				tempData.setUrn(strataIdentifier[i], urns[i]);
				assertEquals(tempData.getUrn(strataIdentifier[i]), urns[i]);
		}
	}

}
