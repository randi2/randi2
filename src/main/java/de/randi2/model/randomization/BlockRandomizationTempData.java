package de.randi2.model.randomization;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

import org.hibernate.annotations.CollectionOfElements;

@Entity
public class BlockRandomizationTempData extends AbstractRandomizationTempData {

	private static final long serialVersionUID = -5150967612749185875L;

	
	 @CollectionOfElements(targetElement = Block.class)
	 @org.hibernate.annotations.MapKey(targetElement = String.class)
	 private Map<String, Block> blocks = new HashMap<String, Block>();
	 
	public Map<String, Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(Map<String, Block> blocks) {
		this.blocks = blocks;
	}

	public Block getBlock(String stratum) {
		return blocks.get(stratum);
	}

	public void setBlock(String stratum, Block currentBlock) {
		blocks.put(stratum, currentBlock);
		
	}

}
