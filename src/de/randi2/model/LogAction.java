package de.randi2.model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class LogAction extends AbstractDomainObject {
	
	
	private GregorianCalendar time;
	private Trial trial;	
	private Person person;
	private String message;
	
	private List<DateChange> changes;
	
	
	
	public LogAction(){
		this.time = new GregorianCalendar();
	}
	
}
