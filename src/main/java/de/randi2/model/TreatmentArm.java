package de.randi2.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import javax.persistence.OneToMany;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Range;

@Entity
public class TreatmentArm extends AbstractDomainObject{

	private static final long serialVersionUID = -1745930698279268352L;

	private String name = null;

	@Lob
	private String description = null;
	@Range(min = 1, max = Integer.MAX_VALUE)
	private int plannedSubjects = Integer.MIN_VALUE;

	@NotNull
	@ManyToOne
	private Trial trial = null;

	@OneToMany(mappedBy="arm")
	private List<TrialSubject> subjects = new ArrayList<TrialSubject>();

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

	public List<TrialSubject> getSubjects(){
		return this.subjects;
	}

	void addSubject(TrialSubject subject){
		this.subjects.add(subject);
	}

}
