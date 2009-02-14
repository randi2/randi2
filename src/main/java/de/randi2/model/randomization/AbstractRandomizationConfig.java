package de.randi2.model.randomization;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Trial;
import de.randi2.randomization.RandomizationAlgorithm;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "RandomizationConfig")
public abstract class AbstractRandomizationConfig extends AbstractDomainObject {

	@OneToOne
	private Trial trial;
	@Transient
	private RandomizationAlgorithm<? extends AbstractRandomizationConfig> algorithm;

	public final RandomizationAlgorithm<? extends AbstractRandomizationConfig> getAlgorithm() {
		if (algorithm == null) {
			algorithm = createAlgorithm();
		}
		return algorithm;
	}

	public abstract RandomizationAlgorithm<? extends AbstractRandomizationConfig> createAlgorithm();

	@Override
	public String toString() {
		return this.getClass().toString();
	}

	public Trial getTrial() {
		return trial;
	}

	public void setTrial(Trial _trial) {
		this.trial = _trial;
		if (trial.getRandomizationConfiguration() == null) {
			trial.setRandomizationConfiguration(this);
		}
	}
}
