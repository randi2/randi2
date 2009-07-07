package de.randi2.model.randomization;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class BlockRandomizationTempData extends AbstractRandomizationTempData {

	private static final long serialVersionUID = -5150967612749185875L;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Map<Integer, Block> blocks = new HashMap<Integer, Block>();

	public Block getBlock(int hashCode) {
		return blocks.get(hashCode);
	}

	public void setBlock(int hashCode, Block currentBlock) {
		blocks.put(hashCode, currentBlock);
	}

}
