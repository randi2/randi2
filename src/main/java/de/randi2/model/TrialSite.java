package de.randi2.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import de.randi2.utility.validations.Password;

@Entity
@NamedQuery(name = "trialSite.findAllMembers", query = "select p from Person p where p.trialSite = :trialSite ")
public class TrialSite extends AbstractDomainObject {

	private static final long serialVersionUID = -5501470090122500715L;

	public final static int MAX_LENGTH_POSTCODE = 10;

	@Column(unique = true)
	private String name = "";

	private String street = "";
	private String postcode = "";
	private String city = "";
	private String country = "";
	private String password = "";

	@OneToOne(cascade=CascadeType.ALL)
	private Person contactPerson = null;

	@OneToMany(mappedBy = "trialSite", fetch=FetchType.EAGER)
	private List<Person> members = new ArrayList<Person>();

	@ManyToMany(mappedBy = "participatingSites")
	private List<Trial> trials = new ArrayList<Trial>();

	@NotEmpty
	@Length(max = MAX_VARCHAR_LENGTH)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null) {
			name = "";
		}
		this.name = name;
	}

	@Length(max = MAX_VARCHAR_LENGTH)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		if (street == null) {
			street = "";
		}
		this.street = street;
	}

	@Length(max = MAX_LENGTH_POSTCODE)
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		if (postcode == null) {
			postcode = "";
		}
		this.postcode = postcode;
	}

	@Length(max = MAX_VARCHAR_LENGTH)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		if (city == null) {
			city = "";
		}
		this.city = city;
	}

	@Password
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotNull
	public Person getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(Person contactPerson) {
		this.contactPerson = contactPerson;
	}

	public List<Person> getMembers() {
		return members;
	}

	public void setMembers(List<Person> members) {
		this.members = members;
	}

	public List<Trial> getTrials() {
		return trials;
	}

	public void setTrials(List<Trial> trials) {
		this.trials = trials;
	}

	@Length(max = MAX_VARCHAR_LENGTH)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		if (country == null) {
			country = "";
		}
		this.country = country;
	}

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
	public String toString() {
		return this.getName();
	}
}
