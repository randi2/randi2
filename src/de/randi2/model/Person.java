package de.randi2.model;

import javax.persistence.Entity;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

import de.randi2.model.enumerations.Gender;
 
@Entity
public class Person extends AbstractDomainObject {

	// Persons Data
	private String surname = "";
	private String firstname = "";
	private String title = "";
	private Gender gender = null;

	// Contact Data
	private String eMail = "";
	private String phone = "";
	private String mobile = "";
	private String fax = "";
	
	// Institutional Data
	private Person assistant;
	private Center center;
	
	// Login-Data
	private String login;

	public Center getCenter() {
		return center;
	}

	public void setCenter(Center center) {
		this.center = center;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@NotEmpty
	@Length(max=AbstractDomainObject.MAX_VARCHAR_LENGTH)
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getEMail() {
		return eMail;
	}

	public void setEMail(String email) {
		this.eMail = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

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


}
