package de.randi2.model.randomization;


import javax.persistence.Entity;
import javax.persistence.OneToMany;


import java.util.HashMap;
import java.util.Map;

@Entity
public class BlockRandomizationTempData extends AbstractRandomizationTempData {

	private static final long serialVersionUID = -5150967612749185875L;

	@OneToMany
	//@MapKey
	private Map<Integer, Block> blocks = new HashMap<Integer, Block>();

	public Block getBlock(int hashCode) {
		return blocks.get(hashCode);
	}

	public void setBlock(int hashCode, Block currentBlock) {
		blocks.put(hashCode, currentBlock);
	}

}
