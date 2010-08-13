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

/**
 * The Class Person.
 */
@Entity

/**
 * Sets the login.
 * 
 * @param login
 *            the new login
 */
@Data
@EqualsAndHashCode(callSuper=true, exclude={"login","trialSite", "assistant"})
@ToString(exclude={"login","trialSite"})
public class Person extends AbstractDomainObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5713870200930075449L;
	
	/** The Constant MAX_NAME_LENGTH. */
	public final static int MAX_NAME_LENGTH = 50;
	
	/** The Constant MAX_TITLE_LENGTH. */
	public final static int MAX_TITLE_LENGTH = 20;
	
	// Persons Data
	/** The surname. */
	@NotEmpty
	@Length(max=MAX_NAME_LENGTH)
	private String surname = "";
	
	/** The firstname. */
	@NotEmpty
	@Length(max=MAX_NAME_LENGTH)
	private String firstname = "";
	
	/** The title. */
	@Length(max=MAX_TITLE_LENGTH)
	private String title = "";
	
	/** The sex. */
	@Enumerated(value=EnumType.STRING)
	@NotNull
	private Gender sex = Gender.MALE;

	// Contact Data
	/** The email. */
	@EMailRANDI2
	@NotEmpty
	private String email = "";

	/** The phone. */
	@TelephonNumber
	@NotEmpty
	private String phone = "";
	

	/** The mobile. */
	@TelephonNumber
	private String mobile = "";

	/** The fax. */
	@TelephonNumber
	private String fax = "";
	
	// Institutional Data
	/** The assistant. This person has no Login object. */
	@ManyToOne(cascade=CascadeType.ALL)
	@ContactPerson
	private Person assistant;
	
	/** The trial site. */
	@ManyToOne
	private TrialSite trialSite;

	// Login data
	/** The login. */
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="person")
	private Login login;
	
	/* (non-Javadoc)
	 * @see de.randi2.model.AbstractDomainObject#getUIName()
	 */
	@Override
	public String getUIName() {
		return this.getSurname()+", "+this.getFirstname();
	}
}
