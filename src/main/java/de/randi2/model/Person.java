package de.randi2.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import de.randi2.model.enumerations.Gender;
import de.randi2.utility.validations.ContactPerson;
import de.randi2.utility.validations.EMailRANDI2;
import de.randi2.utility.validations.TelephonNumber;

@Entity
public class Person extends AbstractDomainObject {

	private static final long serialVersionUID = 5713870200930075449L;
	
	public final static int MAX_NAME_LENGTH = 50;
	public final static int MAX_TITLE_LENGTH = 20;
	
	// Persons Data
	private String surname = "";
	private String firstname = "";
	private String title = "";
	@Enumerated(value=EnumType.STRING)
	private Gender sex = Gender.MALE;

	// Contact Data
	private String email = "";
	private String phone = "";
	private String mobile = "";
	private String fax = "";
	
	// Institutional Data
	@ManyToOne
	@ContactPerson
	private Person assistant;
	@ManyToOne
	private TrialSite trialSite;

	// Login data
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="person")
	private Login login;
	
	public TrialSite getTrialSite() {
		return trialSite;
	}

	public void setTrialSite(TrialSite _trialSite) {
		this.trialSite = _trialSite;
	}
	
	@NotNull
	public Gender getSex() {
		return sex;
	}

	public void setSex(Gender gender) {
		this.sex = gender;
	}

	@NotEmpty
	@Length(max=MAX_NAME_LENGTH)
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		if(surname == null){
			surname = "";
		}
		this.surname = surname;
	}

	@NotEmpty
	@Length(max=MAX_NAME_LENGTH)
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		if(firstname ==null) firstname ="";
		this.firstname = firstname;
	}

	@Length(max=MAX_TITLE_LENGTH)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if(title==null) title ="";
		this.title = title;
	}

	@EMailRANDI2
	@NotEmpty
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotEmpty
	@TelephonNumber
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if(phone==null) phone="";
		this.phone = phone;
	}

	@TelephonNumber
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@TelephonNumber
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Person getAssistant() {
		return assistant;
	}

	public void setAssistant(Person assistant) {
		this.assistant = assistant;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
	
	@Override
	public String toString(){
		return this.surname+" "+this.firstname;
	}
	
	@Override
	public String getUIName() {
		return this.getSurname()+", "+this.getFirstname();
	}
}
