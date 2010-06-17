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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import de.randi2.utility.validations.ContactPerson;
import de.randi2.utility.validations.Password;

/**
 * The Class TrialSite.
 */
@Entity
@NamedQuery(name = "trialSite.findAllMembers", query = "select p from Person p where p.trialSite = :trialSite ")
@EqualsAndHashCode(callSuper=true, exclude={"trials", "members", "contactPerson"})
@ToString(exclude={"members", "trials"})
@Data
public class TrialSite extends AbstractDomainObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5501470090122500715L;

	/** The Constant MAX_LENGTH_POSTCODE. */
	public final static int MAX_LENGTH_POSTCODE = 10;

	/** The name. */
	@Column(unique = true)
	@NotEmpty
	@Length(max = MAX_VARCHAR_LENGTH)
	private String name = "";

	/** The street. */
	@Length(max = MAX_VARCHAR_LENGTH)
	@NotNull
	private String street = "";
	
	/** The postcode. */
	@Length(max = MAX_LENGTH_POSTCODE)
	@NotNull
	private String postcode = "";
	
	/** The city. */
	@Length(max = MAX_VARCHAR_LENGTH)
	@NotNull
	private String city = "";
	
	/** The country. */
	@Length(max = MAX_VARCHAR_LENGTH)
	private String country = "";
	
	/** The password. */
	@Password
	private String password = "";

	/** The contact person. */
	@OneToOne(cascade=CascadeType.ALL)
	@ContactPerson
	@NotNull
	private Person contactPerson = null;

	/** The members. */
	@OneToMany(mappedBy = "trialSite")
	private List<Person> members = new ArrayList<Person>();

	/** The trials. */
	@ManyToMany(mappedBy = "participatingSites")
	private List<Trial> trials = new ArrayList<Trial>();

	/**
	 * This method returns the trial site members with specified role.
	 * 
	 * @param role
	 *            - role of the searched members
	 * 
	 * @return the members with specified role
	 */
	@Transient
	public List<Login> getMembersWithSpecifiedRole(Role role) {
		List<Login> searchedMembers = new ArrayList<Login>();
		for (Person p : this.getMembers()) {
			if (p.getLogin().hasRole(role))
				searchedMembers.add(p.getLogin());
		}
		return searchedMembers;
	}
	
	/* (non-Javadoc)
	 * @see de.randi2.model.AbstractDomainObject#getUIName()
	 */
	@Override
	public String getUIName() {
		return this.getName();
	}
}
