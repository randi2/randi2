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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;

import lombok.Data;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

import de.randi2.model.exceptions.ValidationException;

@MappedSuperclass
public abstract @Data class AbstractDomainObject implements Serializable {

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


	private boolean isRequired(Field f) {
		return f.isAnnotationPresent(org.hibernate.validator.NotEmpty.class) || f.isAnnotationPresent(org.hibernate.validator.NotNull.class) || f.isAnnotationPresent(de.randi2.utility.validations.Password.class);
	}

	@SuppressWarnings("unchecked")
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
			for(Field field : this.getClass().getDeclaredFields()){
				requiredFields.put(field.getName(), this.isRequired(field));
			}
		}
		return requiredFields;
	}



	@PreUpdate
	public void beforeUpdate() {
		this.updatedAt = new GregorianCalendar();
	}

	@PrePersist
	public void beforeCreate() {
		this.createdAt = new GregorianCalendar();
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
