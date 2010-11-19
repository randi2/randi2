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
package de.randi2.jsf.supportBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import lombok.Data;

import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.Highlight;
@ManagedBean(name="dataExporter")
@SessionScoped
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
