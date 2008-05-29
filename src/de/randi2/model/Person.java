package de.randi2.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.springframework.beans.factory.annotation.Configurable;

import de.randi2.model.enumerations.Gender;

@Entity
@Configurable
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
	@ManyToOne(cascade=CascadeType.ALL)
	private Person assistant;
	@ManyToOne(cascade=CascadeType.ALL)
	private Center center;

	// Login data
	@OneToOne(cascade=CascadeType.ALL)
	private Login login;
	
	@OneToMany(mappedBy="person")
	private List<PersonRole> roles = new ArrayList<PersonRole>();
	
	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getCenter()
	 */
	//@NotNull
	public Center getCenter() {
		return center;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#setCenter(de.randi2.model.Center)
	 */
	public void setCenter(Center center) {
		this.center = center;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getSurname()
	 */
	@NotEmpty
	@Length(max=AbstractDomainObject.MAX_VARCHAR_LENGTH)
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
	public String getFirstname() {
		return firstname;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#setFirstname(java.lang.String)
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getTitle()
	 */
	public String getTitle() {
		return title;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getGender()
	 */
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
	// TODO @Email Should not be part of the programm 
	// until tested
	public String getEMail() {
		return eMail;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#setEMail(java.lang.String)
	 */
	public void setEMail(String email) {
		this.eMail = email;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getPhone()
	 */
	public String getPhone() {
		return phone;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#setPhone(java.lang.String)
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getMobile()
	 */
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

	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#setRoles(java.util.List)
	 */
	public void setRoles(List<PersonRole> _roles){
		this.roles = _roles;
	}
	
	/* (non-Javadoc)
	 * @see de.randi2.model.PersonIF#getRoles()
	 */
	public List<PersonRole> getRoles() {
		return this.roles;
	}

}
