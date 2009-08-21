package de.randi2.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Range;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class TreatmentArm extends AbstractDomainObject{

	private static final long serialVersionUID = -1745930698279268352L;

	@NotNull()
	@NotEmpty()
	@Length(max = MAX_VARCHAR_LENGTH)
	private String name = null;

	@Lob
	private String description = null;
	@Range(min = 1, max = Integer.MAX_VALUE)
	private int plannedSubjects = 0;

	@NotNull
	@ManyToOne
	private Trial trial = null;

	@OneToMany(mappedBy="arm")
	private List<TrialSubject> subjects = new ArrayList<TrialSubject>();

	public void addSubject(TrialSubject subject){
		this.subjects.add(subject);
	}
	
	@Transient
	public int getCurrentSubjectsAmount(){
		return getSubjects().size();
	}
	
	@Transient
	public float getFillLevel(){
		return ((float)getCurrentSubjectsAmount() / getPlannedSubjects()) * 100;
	}
	
	@Override
	public String getUIName() {
		return getName();
	}

}
