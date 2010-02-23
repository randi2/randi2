/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Range;

/**
 * The Class TreatmentArm.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class TreatmentArm extends AbstractDomainObject{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1745930698279268352L;

	/** The name. */
	@NotNull()
	@NotEmpty()
	@Length(max = MAX_VARCHAR_LENGTH)
	private String name = null;

	/** The description. */
	@Lob
	private String description = null;
	
	/** The planned subjects. */
	@Range(min = 1, max = Integer.MAX_VALUE)
	private int plannedSubjects = 0;

	/** The trial. */
	@NotNull
	@ManyToOne
	private Trial trial = null;

	/** The subjects. */
	@OneToMany(mappedBy="arm")
	@OrderBy("createdAt ASC")
	private List<TrialSubject> subjects = new ArrayList<TrialSubject>();

	/**
	 * Adds the subject.
	 * 
	 * @param subject
	 *            the subject
	 */
	public void addSubject(TrialSubject subject){
		this.subjects.add(subject);
	}
	
	/**
	 * Gets the current subjects amount.
	 * 
	 * @return the current subjects amount
	 */
	@Transient
	public int getCurrentSubjectsAmount(){
		return getSubjects().size();
	}
	
	/**
	 * Gets the fill level.
	 * 
	 * @return the fill level
	 */
	@Transient
	public float getFillLevel(){
		return ((float)getCurrentSubjectsAmount() / getPlannedSubjects()) * 100;
	}
	
	/* (non-Javadoc)
	 * @see de.randi2.model.AbstractDomainObject#getUIName()
	 */
	@Override
	public String getUIName() {
		return getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (getId() ^ (getId() >>> 32));
		result = prime * result + plannedSubjects;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreatmentArm other = (TreatmentArm) obj;
		if (getId() != other.getId())
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (plannedSubjects != other.plannedSubjects)
			return false;
		return true;
	}
	
	

}
