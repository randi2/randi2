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
package de.randi2.jsf.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.icesoft.faces.component.selectinputtext.SelectInputText;

import de.randi2.model.AbstractDomainObject;
import de.randi2.services.AbstractService;

public class AutoCompleteObject<O extends AbstractDomainObject> {

	public static final Comparator<SelectItem> LABEL_COMPERATOR = new Comparator<SelectItem>() {
		@Override
		public int compare(SelectItem s1, SelectItem s2) {
			return s1.getLabel().compareToIgnoreCase(s2.getLabel());
		}
	};

	private AbstractService<O> service = null;
	
	private List<O> objects = null;

	/**
	 * List with all trial sites as SelectItems
	 */
	private List<SelectItem> objectList = null;

	/**
	 * List with the matched trial sites as SelectItems
	 */
	private List<SelectItem> matchesList = null;

	private O selectedObject = null;
	
	private String objectRepresentation;

	public AutoCompleteObject(AbstractService<O> _service) {
		service = _service;
	}
	
	public AutoCompleteObject(List<O> _objects){
		objects = _objects;
	}

	public List<O> getObjects() {
		if(objects!=null)
			return objects;
		return service.getAll();
	}

	public List<SelectItem> getObjectList() {
		if (matchesList == null) {
			if (objectList == null) {
				objectList = new ArrayList<SelectItem>(getObjects().size());
				for (O el : getObjects()) {
					objectList.add(new SelectItem(el, el.getUIName()));
				}
				Collections.sort(objectList, LABEL_COMPERATOR);
			}
			return objectList;
		}
		return matchesList;
	}

	/**
	 * Eventlistener for autocomplete widget.
	 * 
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	public void updateObjectList(ValueChangeEvent event) {
		SelectItem searchObject = new SelectItem("", (String) event
				.getNewValue());
		int maxMatches = ((SelectInputText) event.getComponent()).getRows();
		List<SelectItem> matchList = new ArrayList<SelectItem>(maxMatches);
		int insert = Collections.binarySearch(getObjectList(), searchObject,
				LABEL_COMPERATOR);
		if (insert < 0) {
			insert = Math.abs(insert) - 1;
		}
		for (int i = 0; i < maxMatches; i++) {
			// quit the match list creation if the index is larger then
			// max entries in the dictionary if we have added maxMatches.
			if ((insert + i) >= objectList.size() || i >= maxMatches) {
				break;
			}
			matchList.add(objectList.get(insert + i));
		}
		// assign new matchesList
		if (this.matchesList != null) {
			this.matchesList.clear();
			this.matchesList = null;
		}
		this.matchesList = matchList;

		// Get the auto complete component from the event and assing
		if (event.getComponent() instanceof SelectInputText) {
			SelectInputText autoComplete = (SelectInputText) event
					.getComponent();
			selectedObject = null;
			// if no selected item then return the previously selected item.
			if (autoComplete.getSelectedItem() != null) {
				selectedObject = (O) autoComplete.getSelectedItem()
						.getValue();
			}
			// otherwise if there is a selected item get the value from the
			// match list
			else {
				if (matchesList != null) {
					for (SelectItem si : matchesList) {
						if (si.getLabel().equals(
								autoComplete.getValue().toString()))
							selectedObject = (O) autoComplete
									.getSelectedItem().getValue();
					}
				}
			}
		}
	}

	public O getSelectedObject() {
		return selectedObject;
	}
	
	public String getObjectRepresentation() {
		return objectRepresentation;
	}
	
	public void setObjectRepresentation(String objectRepresentation) {
		this.objectRepresentation = objectRepresentation;
	}
	
	public boolean isObjectSelected(){
		return selectedObject!=null;
	}
}
