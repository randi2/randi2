package de.randi2.jsf.handlers;

import java.util.List;
import java.util.Vector;

import javax.faces.model.SelectItem;

import de.randi2.model.Center;

public class CenterHendler {

	private Vector<SelectItem> dummyCenters;
	
	private Vector<SelectItem> dummyMembers;
	
	public List<SelectItem> getDummyCenters(){
		if(dummyCenters==null){
			dummyCenters = new Vector<SelectItem>(3);
			for(int i=0;i<2;i++){
				Center temp =  new Center();
				temp.setName("Testcenter "+i+1);
				temp.setCity("Heidelberg");
				temp.setPostcode("69120");
				temp.setStreet("Im Neuenheimer Feld 1");
				dummyCenters.add(new SelectItem(i,temp.getName()));
			}
		}
		return dummyCenters;
	}
	
	public List<SelectItem> getDummyMembers(){
		if(dummyMembers==null){
			dummyMembers = new Vector<SelectItem>(10);
			for(int i=0;i<9;i++){
				dummyMembers.add(new SelectItem(i,"Memeber_"+i));
			}
		}
		return dummyMembers;
	}
}
