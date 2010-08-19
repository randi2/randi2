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

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

import de.randi2.model.exceptions.ValidationException;

/**
 * The Class AbstractDomainObject.
 */
@MappedSuperclass
@Data
@EqualsAndHashCode(of={"id", "version"})
public abstract class AbstractDomainObject implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1394903092160914604L;

	/** The Constant NOT_YET_SAVED_ID. */
	public final static int NOT_YET_SAVED_ID = Integer.MIN_VALUE;
	
	/** The Constant MAX_VARCHAR_LENGTH. */
	public final static int MAX_VARCHAR_LENGTH = 255;
	
	/** The required fields. */
	@Transient
	private Map<String, Boolean> requiredFields = null;
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id = NOT_YET_SAVED_ID;
	
	/** The version. */
	@Version
	private int version = Integer.MIN_VALUE;
	
	/** The created at. */
	private GregorianCalendar createdAt = null;
	
	/** The updated at. */
	private GregorianCalendar updatedAt = null;

	/** The Constant random. */
	private static final Random random = new Random();

	/**
	 * Instantiates a new abstract domain object.
	 */
	public AbstractDomainObject(){
		int v = random.nextInt();
		this.version = (v < 0) ? v : -v;
	}


	/**
	 * Checks if is required.
	 * 
	 * @param f
	 *            the f
	 * 
	 * @return true, if is required
	 */
	private boolean isRequired(Field f) {
		return f.isAnnotationPresent(org.hibernate.validator.NotEmpty.class) || f.isAnnotationPresent(org.hibernate.validator.NotNull.class) || f.isAnnotationPresent(de.randi2.utility.validations.Password.class);
	}

	/**
	 * Check value.
	 * 
	 * @param field
	 *            the field
	 * @param value
	 *            the value
	 */
	@SuppressWarnings("unchecked")
	public void checkValue(String field, Object value) {
		ClassValidator val = new ClassValidator(this.getClass());
		InvalidValue[] invalids = val.getPotentialInvalidValues(field, value);

		if (invalids.length > 0) {
			throw new ValidationException(invalids);
		}
	}

	/**
	 * Gets the required fields.
	 * 
	 * @return the required fields
	 */
	public Map<String, Boolean> getRequiredFields() {
		if (requiredFields == null) {
			requiredFields = new HashMap<String, Boolean>();
			for(Field field : this.getClass().getDeclaredFields()){
				requiredFields.put(field.getName(), this.isRequired(field));
			}
		}
		return requiredFields;
	}



	/**
	 * Before update.
	 */
	public void beforeUpdate() {
		this.updatedAt = new GregorianCalendar();
	}

	/**
	 * Before create.
	 */
	public void beforeCreate() {
		this.createdAt = new GregorianCalendar();
	}

	/**
	 * This method provides a string object for the UI.
	 * 
	 * @return the UI name
	 */
	@Transient
	public String getUIName(){
		//FIXME It would be better to have this method one level deeper 
		return this.getClass().getCanonicalName();
	}
	
	
}
