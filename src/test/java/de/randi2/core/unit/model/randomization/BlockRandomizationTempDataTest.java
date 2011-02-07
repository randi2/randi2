package de.randi2.core.unit.model.randomization;

import static org.junit.Assert.*;
import org.junit.Test;

import de.randi2.model.randomization.Block;
import de.randi2.model.randomization.BlockRandomizationTempData;
import de.randi2.testUtility.utility.AbstractDomainTest;

public class BlockRandomizationTempDataTest extends AbstractDomainTest<BlockRandomizationTempData> {

	public BlockRandomizationTempDataTest(){
		super(BlockRandomizationTempData.class);
	}
	
	@Test
	public void testSetAndGetStratum(){
		BlockRandomizationTempData tempData = new BlockRandomizationTempData();
		String[] strataIdentifier = new String[]{"a", "b", "c", "d"};
		Block[] blocks = new Block[]{new Block(), new Block(), new Block(),new Block()};
		for(int i=0;i < blocks.length; i++){
				tempData.setBlock(strataIdentifier[i], blocks[i]);
				assertEquals(tempData.getBlock(strataIdentifier[i]), blocks[i]);
		}
	}
	
}
