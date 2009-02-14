package de.randi2.model.randomization;


import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Trial;
import de.randi2.randomization.RandomizationAlgorithm;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="RandomizationConfig")
public abstract class AbstractRandomizationConfig extends AbstractDomainObject{
	
	public abstract RandomizationAlgorithm<? extends AbstractRandomizationConfig> getAlgorithm(Trial trial);

	@Override
	public String toString() {
		return this.getClass().toString();
	}
}
