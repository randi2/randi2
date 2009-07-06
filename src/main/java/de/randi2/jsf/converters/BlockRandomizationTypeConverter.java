package de.randi2.jsf.converters;

import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.model.randomization.BlockRandomizationConfig;

public class BlockRandomizationTypeConverter  implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if( value == null || value.length() < 1 ){ 
 			return null;
 		}
 		return findBlockTypeForl16edValue(value);
		
	}

	private BlockRandomizationConfig.TYPE findBlockTypeForl16edValue(String value) {
		ResourceBundle rb = ResourceBundle.getBundle( "de.randi2.jsf.i18n.algorithms",((LoginHandler) FacesContext.getCurrentInstance()
			    .getApplication().getELResolver().getValue(
			    	      FacesContext.getCurrentInstance().getELContext(), null,
			    	      "loginHandler")).getChosenLocale());
		for(String key : rb.keySet()){
			if(rb.getString(key).equals(value)){
				String[] results = key.split("\\.");
			return BlockRandomizationConfig.TYPE.valueOf(results[results.length-1]);
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		String result = null;
 		if( value != null && value instanceof BlockRandomizationConfig.TYPE ){
 			result = ResourceBundle.getBundle( "de.randi2.jsf.i18n.algorithms",((LoginHandler) FacesContext.getCurrentInstance()
 				    .getApplication().getELResolver().getValue(
 				    	      FacesContext.getCurrentInstance().getELContext(), null,
 				    	      "loginHandler")).getChosenLocale()).getString(BlockRandomizationConfig.class.getCanonicalName()+ "."+((BlockRandomizationConfig.TYPE)value).toString());
 		}
 		return result;
	}

}
