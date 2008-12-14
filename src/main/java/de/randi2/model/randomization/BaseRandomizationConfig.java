package de.randi2.model.randomization;

import java.lang.reflect.Constructor;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Trial;
import de.randi2.randomization.RandomizationAlgorithm;
import de.randi2.utility.Randi2Error;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="RandomizationConfig")
public class BaseRandomizationConfig extends AbstractDomainObject{
	

	private Class<? extends RandomizationAlgorithm<?, ?>> algorithmClass;
	
	public RandomizationAlgorithm<?, ?> getAlgorithm(Trial trial){
		try {
			return algorithmClass.getConstructor(Trial.class).newInstance(trial);
		} catch (Exception e) {
			throw new Randi2Error(e);
		}
	}

	public Class<? extends RandomizationAlgorithm<?, ?>> getAlgorithmClass() {
		return algorithmClass;
	}

	public void setAlgorithmClass(
			Class<? extends RandomizationAlgorithm<?, ?>> algorithmClass) {
		this.algorithmClass = algorithmClass;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getClass().toString();
	}
}
