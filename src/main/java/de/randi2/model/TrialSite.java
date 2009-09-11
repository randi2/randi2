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

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import de.randi2.utility.validations.ContactPerson;
import de.randi2.utility.validations.Password;

@Entity
@NamedQuery(name = "trialSite.findAllMembers", query = "select p from Person p where p.trialSite = :trialSite ")
@EqualsAndHashCode(callSuper=true, exclude={"trials", "members", "contactPerson"})
@Data
public class TrialSite extends AbstractDomainObject {

	private static final long serialVersionUID = -5501470090122500715L;

	public final static int MAX_LENGTH_POSTCODE = 10;

	@Column(unique = true)
	@NotEmpty
	@Length(max = MAX_VARCHAR_LENGTH)
	private String name = "";

	@Length(max = MAX_VARCHAR_LENGTH)
	@NotNull
	private String street = "";
	
	@Length(max = MAX_LENGTH_POSTCODE)
	@NotNull
	private String postcode = "";
	
	@Length(max = MAX_VARCHAR_LENGTH)
	@NotNull
	private String city = "";
	
	@Length(max = MAX_VARCHAR_LENGTH)
	private String country = "";
	
	@Password
	private String password = "";

	@OneToOne(cascade=CascadeType.ALL)
	@ContactPerson
	@NotNull
	private Person contactPerson = null;

	@OneToMany(mappedBy = "trialSite")
	private List<Person> members = new ArrayList<Person>();

	@ManyToMany(mappedBy = "participatingSites")
	private List<Trial> trials = new ArrayList<Trial>();

	/**
	 * This method returns the trial site members with specified role
	 * 
	 * @param role -
	 *            role of the searched members
	 * @return
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
	
	@Override
	public String getUIName() {
		return this.getName();
	}
}
