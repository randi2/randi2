package de.randi2.jsf.backingBeans;

import javax.faces.event.ValueChangeEvent;

public class Strata {
	
	public void configurationChanged(ValueChangeEvent event){
		System.out.println(event.getNewValue());
	}

}
