package de.randi2.model;

public class DateChange extends AbstractDomainObject {
	
	
	private Class<? extends AbstractDomainObject> entity;
	private String field;
	private Class fieldtype;
	
	private Object beforeValue;
	private Object afterValue;
	
	private LogAction logAction;

	protected DateChange(Class<? extends AbstractDomainObject> entity,
			String field, Class fieldtype, Object beforeValue, Object afterValue) {
		super();
		this.entity = entity;
		this.field = field;
		this.fieldtype = fieldtype;
		this.beforeValue = beforeValue;
		this.afterValue = afterValue;
	}

	public Class<? extends AbstractDomainObject> getEntity() {
		return entity;
	}

	public void setEntity(Class<? extends AbstractDomainObject> entity) {
		this.entity = entity;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Class getFieldtype() {
		return fieldtype;
	}

	public void setFieldtype(Class fieldtype) {
		this.fieldtype = fieldtype;
	}

	public Object getBeforeValue() {
		return beforeValue;
	}

	public void setBeforeValue(Object beforeValue) {
		this.beforeValue = beforeValue;
	}

	public Object getAfterValue() {
		return afterValue;
	}

	public void setAfterValue(Object afterValue) {
		this.afterValue = afterValue;
	}

	public LogAction getLogAction() {
		return logAction;
	}

	public void setLogAction(LogAction logAction) {
		this.logAction = logAction;
	}
}
