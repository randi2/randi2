package de.randi2.datenbank;

/**
 * @author Benjamin Theel <BTheel@stud.hs-heilbronn.de>
 * @author Frederik Reifschneider <Reifschneider@stud.uni-heidelberg.de>
 * 
 * @version $Id$
 */
public class Filter {
	private boolean isFilter = false;

	public Filter() {

	}

	public Filter(boolean filter) {
		this.isFilter = filter;
	}

	public void setFilter(boolean filter) {
		this.isFilter = filter;
	}

	public boolean isFilter() {
		return this.isFilter;
	}

}
