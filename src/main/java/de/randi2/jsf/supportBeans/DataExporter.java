package de.randi2.jsf.supportBeans;

import javax.faces.event.ValueChangeEvent;

import lombok.Data;

import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.Highlight;

@Data
public class DataExporter {

	    private Effect changeEffect;
	    private String type;
        private boolean showDownload = false;
	    
	    public DataExporter() {
	        changeEffect = new Highlight("#fda505");
	        changeEffect.setFired(true);
	        type = "exel";
	    }
	    
	    public void setType(String type){
	    	if(type != null && (type.equals("excel") || type.equals("csv"))){
	    		
	    		showDownload = true;
	    	}else{
	    		showDownload = false;
	    	}
	    	this.type = type;
	    }
	  
	    public void typeChangeListener(ValueChangeEvent event){
	        this.changeEffect.setFired(false);
	    }
}
