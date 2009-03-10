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
import org.hibernate.validator.Pattern;
import org.springframework.beans.factory.annotation.Configurable;

import de.randi2.model.enumerations.Gender;
import de.randi2.utility.validations.TelephonNumber;

@Entity
@Configurable
public class Person extends AbstractDomainObject {

	private static final long serialVersionUID = 5713870200930075449L;
	
	public final static int MAX_NAME_LENGTH = 50;
	public final static int MAX_TITLE_LENGTH = 20;
	
	// Persons Data
	private String surname = "";
	private String firstname = "";
	private String title = "";
	@Enumerated(value=EnumType.STRING)
	private Gender gender = null;

	// Contact Data
	private String eMail = "";
	private String phone = "";
	private String mobile = "";
	private String fax = "";
	
	// Institutional Data
	@ManyToOne(cascade=CascadeType.ALL)
	private Person assistant;
	@ManyToOne
	private TrialSite trialSite;

	// Login data
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="person")
	private Login login;
	
	
	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getTrialSite()
	 */
	public TrialSite getTrialSite() {
		return trialSite;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#setTrialSite(de.randi2.model.TrialSite)
	 */
	public void setTrialSite(TrialSite _trialSite) {
		this.trialSite = _trialSite;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getSurname()
	 */
	@NotEmpty
	@Length(max=MAX_NAME_LENGTH)
	public String getSurname() {
		return surname;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#setSurname(java.lang.String)
	 */
	public void setSurname(String surname) {
		if(surname == null){
			surname = "";
		}
		this.surname = surname;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getFirstname()
	 */
	@NotEmpty
	@Length(max=MAX_NAME_LENGTH)
	public String getFirstname() {
		return firstname;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#setFirstname(java.lang.String)
	 */
	public void setFirstname(String firstname) {
		if(firstname ==null) firstname ="";
		this.firstname = firstname;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getTitle()
	 */
	@Length(max=MAX_TITLE_LENGTH)
	public String getTitle() {
		return title;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		if(title==null) title ="";
		this.title = title;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getGender()
	 */
	@NotNull
	public Gender getGender() {
		return gender;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#setGender(de.randi2.model.enumerations.Gender)
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getEMail()
	 */
	@Pattern(regex="[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-]+(\\.)?)+\\.([a-zA-Z]){2,4}")
	@NotEmpty
	public String getEMail() {
		return eMail;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#setEMail(java.lang.String)
	 */
	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getPhone()
	 */
	@NotEmpty
	@TelephonNumber
	public String getPhone() {
		return phone;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#setPhone(java.lang.String)
	 */
	public void setPhone(String phone) {
		if(phone==null) phone="";
		this.phone = phone;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getMobile()
	 */
	@TelephonNumber
	public String getMobile() {
		return mobile;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#setMobile(java.lang.String)
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getFax()
	 */
	@TelephonNumber
	public String getFax() {
		return fax;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#setFax(java.lang.String)
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getAssistant()
	 */
	public Person getAssistant() {
		return assistant;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#setAssistant(de.randi2.model.PersonIF)
	 */
	public void setAssistant(Person assistant) {
		this.assistant = assistant;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getLogin()
	 */
	public Login getLogin() {
		return login;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#setLogin(de.randi2.model.Login)
	 */
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
