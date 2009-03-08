package de.randi2.model;

import static de.randi2.utility.ReflectionUtil.getGetters;
import static de.randi2.utility.ReflectionUtil.getPropertyName;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

import de.randi2.model.exceptions.ValidationException;
import java.util.Random;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@MappedSuperclass
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

	public AbstractDomainObject(){
		int v = random.nextInt();
		this.version = (v < 0) ? v : -v;
	}

	public long getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}

	public void setId(long _id) {
		this.id = _id;
	}

	public void setVersion(int _version) {
		this.version = _version;
	}

	private boolean isRequired(Method m) {
		return m.isAnnotationPresent(org.hibernate.validator.NotEmpty.class) || m.isAnnotationPresent(org.hibernate.validator.NotNull.class) || m.isAnnotationPresent(de.randi2.utility.validations.Password.class);
	}

	public void checkValue(String field, Object value) {
		ClassValidator val = new ClassValidator(this.getClass());
		InvalidValue[] invalids = val.getPotentialInvalidValues(field, value);

		if (invalids.length > 0) {
			throw new ValidationException(invalids);
		}
	}

	public Map<String, Boolean> getRequiredFields() {
		if (requiredFields == null) {
			requiredFields = new HashMap<String, Boolean>();
			for (Method method : getGetters(this)) {
				requiredFields.put(getPropertyName(method), this.isRequired(method));
			}
		}
		return requiredFields;
	}

	/**
	 * This method checks if two object identical by matching their id's.
	 */
	// FIXME This method only checks for the version and does not check,
	// if other values are the same. This ones have to be implemented
	// in some other way.
	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}

		if (other instanceof AbstractDomainObject) {
			AbstractDomainObject dO = (AbstractDomainObject) other;
			return new EqualsBuilder().append(id, dO.id).
					append(version, dO.version).
					append(version, dO.version).
					isEquals();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).append(version).toHashCode();
	}

	@PreUpdate
	public void beforeUpdate() {
		this.updatedAt = new GregorianCalendar();
	}

	@PrePersist
	public void beforeCreate() {
		this.createdAt = new GregorianCalendar();
	}

	public GregorianCalendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(GregorianCalendar createdAt) {
		this.createdAt = createdAt;
	}

	public GregorianCalendar getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(GregorianCalendar updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	/**
	 * This method provides a string object for the UI.
	 * @return
	 */
	@Transient
	public String getUIName(){
		//FIXME It would be better to have this method one level deeper 
		return this.getClass().getCanonicalName();
	}
}
