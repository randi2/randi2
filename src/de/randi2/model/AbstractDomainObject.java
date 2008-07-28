package de.randi2.model;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.exceptions.ValidationException;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
public abstract class AbstractDomainObject implements Serializable {

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
	
	@Transient
	private List<DateChange> changes = new ArrayList<DateChange>();

	public long getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}

	private void setId(long _id) {
		this.id = _id;
	}

	private void setVersion(int _version) {
		this.version = _version;
	}

	public Boolean isRequired(Method m) {

		return m.isAnnotationPresent(org.hibernate.validator.NotEmpty.class)
				|| m.isAnnotationPresent(org.hibernate.validator.NotNull.class);

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
			for (Method m : this.getClass().getMethods()) {
				if (m.getName().substring(0, 3).equals("get")) {
					requiredFields.put(m.getName().substring(3, 4)
							.toLowerCase()
							+ m.getName().substring(4), this.isRequired(m));
				}
			}
		}
		return requiredFields;
	}

	/**
	 * This method checks if two object identical by matching their id's.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof AbstractDomainObject) {
			AbstractDomainObject randi2Object = (AbstractDomainObject) o;
			if (randi2Object.id == this.id)
				return true;
			return false;
		} else
			return false;
	}

	
	public List<DateChange> getChanges() {
		return this.changes;
	}
	
	public void clearChanges(){
		this.changes.clear();
	}

	protected void addChange(String field, Object currentO, Object newO) {
		if (newO != null && !newO.equals(currentO)) {
			for(DateChange dc : this.changes){
				if(dc.getField().equals(field)){
					dc.setAfterValue(newO);
					return;
				}
			}		
			this.changes.add(new DateChange(this.getClass(), field, currentO.getClass(), currentO, newO));
		}
	}
	
	@PreUpdate
	public void beforeUpdate(){
		this.updatedAt = new GregorianCalendar();
	}
	
	@PrePersist
	public void beforeCreate(){
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
	
	

}
