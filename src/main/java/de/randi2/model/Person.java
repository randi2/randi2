package de.randi2.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import de.randi2.model.enumerations.Gender;
import de.randi2.utility.validations.ContactPerson;
import de.randi2.utility.validations.EMailRANDI2;
import de.randi2.utility.validations.TelephonNumber;

@Entity
@Data
@EqualsAndHashCode(callSuper=true, exclude={"login"})
@ToString(exclude={"login","trialSite"})
public class Person extends AbstractDomainObject {

	private static final long serialVersionUID = 5713870200930075449L;
	
	public final static int MAX_NAME_LENGTH = 50;
	public final static int MAX_TITLE_LENGTH = 20;
	
	// Persons Data
	@NotEmpty
	@Length(max=MAX_NAME_LENGTH)
	private String surname = "";
	
	@NotEmpty
	@Length(max=MAX_NAME_LENGTH)
	private String firstname = "";
	
	@Length(max=MAX_TITLE_LENGTH)
	private String title = "";
	
	@Enumerated(value=EnumType.STRING)
	@NotNull
	private Gender sex = Gender.MALE;

	// Contact Data
	@EMailRANDI2
	@NotEmpty
	private String email = "";

	@TelephonNumber
	@NotEmpty
	private String phone = "";
	

	@TelephonNumber
	private String mobile = "";

	@TelephonNumber
	private String fax = "";
	
	// Institutional Data
	@ManyToOne(cascade=CascadeType.ALL)
	@ContactPerson
	private Person assistant;
	@ManyToOne
	private TrialSite trialSite;

	// Login data
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="person")
	private Login login;
	
	@Override
	public String getUIName() {
		return this.getSurname()+", "+this.getFirstname();
	}
}
