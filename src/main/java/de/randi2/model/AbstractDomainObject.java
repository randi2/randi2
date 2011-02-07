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

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.NotEmpty;

import de.randi2.model.exceptions.ValidationException;


/**
 * The super class for all persistent classes.
 * It contains all necessary field to save and check the objects. 
 * 
 * @author Daniel Schrimpf <ds@randi2.de>
 *
 */
@MappedSuperclass
@Data
@EqualsAndHashCode(of = { "id", "version" })
public abstract class AbstractDomainObject implements Serializable {

	private static final long serialVersionUID = -1394903092160914604L;
	public final static int NOT_YET_SAVED_ID = Integer.MIN_VALUE;
	public final static int MAX_VARCHAR_LENGTH = 255;
	
	@Transient
	private Map<String, Boolean> requiredFields = null;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id = NOT_YET_SAVED_ID;
	@Version
	private int version = Integer.MIN_VALUE;
	private GregorianCalendar createdAt = null;
	private GregorianCalendar updatedAt = null;
	
	private static final Random random = new Random();

	public AbstractDomainObject() {
		//TODO ds: find out if the random initialization of the field version is necessary
		int v = random.nextInt();
		this.version = (v < 0) ? v : -v;
	}

	/**
	 * Checks if the value of the field is correct, if not a ValidationException is thrown.
	 */
	@SuppressWarnings("unchecked")
	public void checkValue(String fieldName, Object value) throws ValidationException {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<?> invalids = validator.validateValue(this.getClass(), fieldName, value);

		if (!invalids.isEmpty()) {
			throw new ValidationException((Set<ConstraintViolation<?>>) invalids);
		}
	}

	/**
	 * Gets the required fields (a field with the annotation NotEmpty, NotNull
	 * or Password).
	 * 
	 * @return the required fields
	 */
	public Map<String, Boolean> getRequiredFields() {
		if (requiredFields == null) 
			initializeRequiredFields();
		return requiredFields;
	}
	
	private void initializeRequiredFields(){
		requiredFields = new HashMap<String, Boolean>();
		for (Field field : this.getClass().getDeclaredFields()) {
			requiredFields.put(field.getName(), this.isRequired(field));
		}
	}
	
	private boolean isRequired(Field field) {
		return field.isAnnotationPresent(NotEmpty.class)
				|| field.isAnnotationPresent(NotNull.class)
				|| field.isAnnotationPresent(de.randi2.utility.validations.Password.class);
	}


	@PreUpdate
	public void beforeUpdate() {
		this.updatedAt = new GregorianCalendar();
	}

	@PrePersist
	public void beforeCreate() {
		this.createdAt = new GregorianCalendar();
		this.updatedAt = new GregorianCalendar();
	}

	/**
	 * This method provides a string object for the UI.
	 * 
	 * @return the UI name
	 */
	@Transient
	public String getUIName() {
		// FIXME It would be better to have this method one level deeper
		return this.getClass().getCanonicalName();
	}

}
