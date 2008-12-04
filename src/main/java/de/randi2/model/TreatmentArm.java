package de.randi2.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Range;

@Entity
public class TreatmentArm extends AbstractDomainObject{

	private String name = null;

	@Lob
	private String description = null;
	@Range(min = 1, max = Integer.MAX_VALUE)
	private int plannedSubjects = Integer.MIN_VALUE;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private Trial trial = null;

	@NotNull()
	@NotEmpty()
	@Length(max = MAX_VARCHAR_LENGTH)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPlannedSubjects() {
		return plannedSubjects;
	}

	public void setPlannedSubjects(int plannedSubjects) {
		this.plannedSubjects = plannedSubjects;
	}
	
	public Trial getTrial() {
		return trial;
	}

	public void setTrial(Trial trial) {
		this.trial = trial;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TO_STRING_NOT_IMPLEMENTED";
	}

}
